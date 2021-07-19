package com.rest.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

public class ValidateResponseTimeTest {
    @Test
    public void responseTimeUsingResponseOptions() {
        String payload = "{\"username\" : \"admin\",\"password\" : \"password123\"}";
        //default request specification
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.baseUri("https://restful-booker.herokuapp.com/auth");
        request.body(payload);
        Response response = request.post();
        long responseTime1 = response.getTime();
        System.out.println("Response time in ms using getTime():" + responseTime1);
        long responseTimeInSeconds = response.getTimeIn(TimeUnit.SECONDS);
        System.out.println("Response time in seconds using getTimeIn():" + responseTimeInSeconds);
    }

    @Test
    public void responseTimeUsingValidatableResponseOptionsMethods() {
        String payload = "{\"username\" : \"admin\",\"password\" : \"password123\"}";
        //default request specification
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.baseUri("https://restful-booker.herokuapp.com/auth");
        request.body(payload);
        Response response = request.post();
        ValidatableResponse valRes = response.then();
        valRes.time(Matchers.lessThan(1000L));
    }
}