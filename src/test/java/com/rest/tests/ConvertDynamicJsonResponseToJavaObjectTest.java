package com.rest.tests;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class ConvertDynamicJsonResponseToJavaObjectTest {
/*  Exception:
    com.fasterxml.jackson.databind.exc.MismatchedInputException:
    Cannot deserialize value of type `java.util.LinkedHashMap<java.lang.Object,java.lang.Object>`
    from Array value (token `JsonToken.START_ARRAY`)*/

    @Test
    public void deserializationJsonArrayUsingMap(){
        Response response =  RestAssured.get("https://run.mocky.io/v3/8b63e084-7cd5-408b-b9ec-b021f92060db");
        Map responseList = response.as(Map.class);
        System.out.println(responseList.size());
    }

/*  Exception:
    com.fasterxml.jackson.databind.exc.MismatchedInputException:
    Cannot deserialize value of type `java.util.ArrayList<java.lang.Object>`
    from Object value (token `JsonToken.START_OBJECT`)*/

    @Test
    public void deserializationJsonArrayUsingList(){
        Response response =  RestAssured.get("https://run.mocky.io/v3/cff03fa7-d643-44d9-87e3-89c2069de9c5");
        List responseList = response.as(List.class);
        System.out.println(responseList.size());
    }
    //using Instance of operator, so that we can ignore the expected 'MismatchedInputException' exception
    @Test
    public void handleDynamicResponseUsingInstanceOf(){
        Response response =  RestAssured.get("https://run.mocky.io/v3/8b63e084-7cd5-408b-b9ec-b021f92060db");
        Object responseAsObject = response.as(Object.class);

        if(responseAsObject instanceof List){
         List responseAsList = (List) responseAsObject;
         System.out.println(responseAsList.size());
        }
        else if(responseAsObject instanceof  Map){
            Map responseAsMap = (Map) responseAsObject;
            System.out.println(responseAsMap.keySet());
        }
    }
}
