package com.rest.tests;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RequestResponseSpecificationTest {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    public static String getToken;

    @BeforeClass
    public void beforeClass(){
                requestSpecification = given().
                baseUri("https://restful-booker.herokuapp.com").
                contentType(ContentType.JSON).
                log().all();

                responseSpecification = RestAssured.expect().
                        contentType(ContentType.JSON).
                        statusCode(200).
                        logDetail(LogDetail.ALL);
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
        Response response = requestSpecification.get("/booking").
        then().
                extract().
                response();
        assertThat(response.path("bookingid[0]").toString(),equalTo("4"));
    }

    @Test
    public void partialUpdateBooking(){
        String payload = "{\n" +
                "    \"firstname\" : \"Abhishek\",\n" +
                "    \"lastname\" : \"Chauhan\"\n" +
                "}";
        Response response = given().spec(requestSpecification).
                header("Cookie","token="+ getToken).
                body(payload).
        when().
                patch("/booking/15").
        then().
                extract().response();
        System.out.println("Extract Response: " + response.asString());
    }
}
