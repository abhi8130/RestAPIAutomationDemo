package com.rest.tests;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RequestResponseBuilderTest {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    public static String getToken;

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
    public void authToken(){
        String payload = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";
        Response response =  given().spec(requestSpecification).
                body(payload).
        when().
                post("/auth").
        then().
                extract().response();
        getToken = response.jsonPath().getString("token");
        System.out.println("Token: " + getToken);
    }

    @Test
    public void getBookingIds(){
        Response response = given().spec(requestSpecification).get("/booking").
                then().
                extract().
                response();
        assertThat(response.path("bookingid[0]").toString(),equalTo("8"));
    }

    @Test
    public void sendPayloadAsFile(){
        File payload = new File("src/main/resources/SendPayloadAsFile.json");
        Response response = given().spec(requestSpecification).
                header("Cookie","token="+ getToken).
                body(payload).
        when().
                patch("/booking/15").
        then().
                extract().response();
    }

    @Test
    public void sendJSONObjectAsMap(){
        HashMap<String,String> payload = new HashMap<String,String>();
        payload.put("firstname","Nisha");
        payload.put("lastname","Chauhan");

        given().spec(requestSpecification).
                header("Cookie","token="+ getToken).
                body(payload).
        when().
                patch("/booking/15").
        then().log().all().
        body("lastname",equalTo("Chauhan"),
                "depositpaid",equalTo(true));
    }

    @Test
    public void validateJsonSchema(){
        File payload = new File("src/main/resources/SendPayloadAsFile.json");
        ValidatableResponse cookie = given().spec(requestSpecification).
                header("Cookie", "token=" + getToken).
                body(payload).
                when().
                patch("/booking/15").
                then().log().all().body(matchesJsonSchemaInClasspath("JsonSchemaValidator.json"));
    }
}
