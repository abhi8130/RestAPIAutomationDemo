package com.rest.tests;

import io.restassured.common.mapper.TypeRef;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class ConvertJsonObjectToJavaListTest {

    @Test
    public void deserialization_JsonObject_ResponseToList(){
        List<Object> jsonResponseAsList = given().
                baseUri("https://run.mocky.io/v3/d5beeb06-6724-4e64-96c0-2bc3d93d5b95").
        when().
                get().
        then().
                log().all().
                extract().
                as(List.class);
        Map<String,Object> getEmp = (Map<String,Object>) jsonResponseAsList.get(0);
        System.out.println("ID: " + getEmp.get("id"));
    }

    @Test
    public void deserialization_JsonObject_ResponseToList_With_Generics(){
        List<Map<String,Object>> jsonResponseAsList = given().
                baseUri("https://run.mocky.io/v3/d5beeb06-6724-4e64-96c0-2bc3d93d5b95").
        when().
                get().
        then().
                log().all().
                extract().
                as(new TypeRef<List<Map<String,Object>>>() {});

        Map<String,Object> getEmp = (Map<String,Object>) jsonResponseAsList.get(0);
        System.out.println("FirstName: " + getEmp.get("first_name"));
    }
}
