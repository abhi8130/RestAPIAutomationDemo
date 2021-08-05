package com.rest.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CreateJsonObjectUsingListTest {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass(){
        //using Request Spec Builder
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://run.mocky.io/v3/0c821291-85b6-4e77-8fb0-84ed9b6a03a1");
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

        //using Response Spec Builder
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void jsonObjectUsingList(){
        Map<String,Object> firstEmployee = new HashMap<>();
        firstEmployee.put("id",1);
        firstEmployee.put("first_name","Gale");
        firstEmployee.put("last_name","Thornebarrow");
        firstEmployee.put("email","gthornebarrow0@gizmodo.com");
        firstEmployee.put("gender","female");

        Map<String,Object> secondEmployee = new HashMap<>();
        secondEmployee.put("id",2);
        secondEmployee.put("first_name","Arleta");
        secondEmployee.put("last_name","Inde");
        secondEmployee.put("email","ainde1@dion.ne.jp");
        secondEmployee.put("gender","male");

        List<Map<String,Object>> jsonArrayList = Arrays.asList(firstEmployee,secondEmployee);

        given().
                spec(requestSpecification).
                body(jsonArrayList).
        when().
                post("/booking").
        then().
                log().all();
    }
}
