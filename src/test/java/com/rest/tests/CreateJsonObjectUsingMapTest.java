package com.rest.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CreateJsonObjectUsingMapTest {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass(){
        //using Request Spec Builder
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://restful-booker.herokuapp.com");
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
    public void jsonObjectUsingMap(){
        Map<String,String> bookingDatesMap = new HashMap<String,String>();
        bookingDatesMap.put("checkin","2018-01-01");
        bookingDatesMap.put("checkout","2019-01-01");

        Map<String,Object> parentMap = new HashMap<String,Object>();
        parentMap.put("firstname","Abhishek");
        parentMap.put("lastname","Chauhan");
        parentMap.put("totalprice",111);
        parentMap.put("depositpaid",true);
        parentMap.put("bookingdates",bookingDatesMap);
        parentMap.put("additionalneeds","Breakfast");

        given().
                spec(requestSpecification).
                body(parentMap).
        when().
                post("/booking").
        then().
                log().all();
    }

}
