package com.rest.tests.datashare;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ShareDataTest {
    @Test
    public void getBookingDetails() {
            File payload = new File("src/main/resources/createRecord.json");
            int bookingId = given().
                    baseUri("https://restful-booker.herokuapp.com").
                    contentType("application/json").
                    body(payload).
            when().
                    post("/booking").
            then().
                    log().all().
                    extract().jsonPath().getInt("bookingid");

        Response response = given().
                baseUri("https://restful-booker.herokuapp.com").
                contentType("application/json").
        when().
                get("/booking/" + bookingId).
        then().
                log().all().extract().response();
    }
}


