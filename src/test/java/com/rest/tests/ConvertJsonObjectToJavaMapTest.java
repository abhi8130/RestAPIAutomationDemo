package com.rest.tests;

import io.restassured.common.mapper.TypeRef;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ConvertJsonObjectToJavaMapTest {

    @Test
    public void deserialization_JsonObject_ResponseToMap(){
        Map jsonResponseAsMap = given().
                baseUri("https://run.mocky.io/v3/fb495488-b0c1-4ca2-bc94-dde0a6b3ca8c").
        when().
                get().
        then().
                log().all().
                extract().
                as(Map.class);

        String firstName = (String) jsonResponseAsMap.get("first_name");
        System.out.println("First Name: " + firstName);

        //print all the keys using keySet()
        jsonResponseAsMap.keySet().forEach(k -> System.out.println(k));
    }

    @Test
    public void deserialization_ComplexJsonObject_ResponseToMap(){
        Map jsonResponseAsMap = given().
                baseUri("https://run.mocky.io/v3/fb495488-b0c1-4ca2-bc94-dde0a6b3ca8c").
        when().
                get().
        then().
                log().all().
                extract().
                as(Map.class);
        Map<String,String> department = (Map<String,String>) jsonResponseAsMap.get("department");
        String deptName = (String) department.get("name");
        System.out.println("Department Name: " + deptName);
    }

    @Test
    public void deserialization_ComplexJsonObject_ResponseToMap_With_Generics(){
        Map jsonResponseAsMap = given().
                baseUri("https://run.mocky.io/v3/fb495488-b0c1-4ca2-bc94-dde0a6b3ca8c").
        when().
                get().
        then().
                log().all().
                extract().
                as(new TypeRef<Map<String,Object>>() {});
        String firstName = (String) jsonResponseAsMap.get("first_name");
        System.out.println("First Name: " + firstName);

        //print all the keys using keySet()
        jsonResponseAsMap.keySet().forEach(k -> System.out.println(k));
    }
}
