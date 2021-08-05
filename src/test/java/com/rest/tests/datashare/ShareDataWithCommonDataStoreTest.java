package com.rest.tests.datashare;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class ShareDataWithCommonDataStoreTest {
    @Test(priority = 1)
    public void createBooking() {
        File payload = new File("src/main/resources/createRecord.json");
        DataStore.bookingId = given().
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
                get("/booking/" + DataStore.bookingId).
        then().
                log().all().extract().response();
    }
}
