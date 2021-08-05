package com.rest.tests.datashare;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class ShareDataAmongMethodsTest {
    private int bookingId;
    @Test(priority = 1)
    public void createBooking() {
        File payload = new File("src/main/resources/createRecord.json");
         bookingId = given().
                baseUri("https://restful-booker.herokuapp.com").
                contentType("application/json").
                body(payload).
         when().
                post("/booking").
         then().
                log().all().
                extract().jsonPath().getInt("bookingid");
    }

    @Test(priority = 2)
    public void getBookingDetails(){
        Response response = given().
                baseUri("https://restful-booker.herokuapp.com").
                contentType("application/json").
        when().
                get("/booking/" + bookingId).
        then().
                log().all().extract().response();
    }
}
