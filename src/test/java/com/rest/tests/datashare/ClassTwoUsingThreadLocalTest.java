package com.rest.tests.datashare;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class ClassTwoUsingThreadLocalTest {
    @Test(priority = 1)
    public void createBooking() {
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
        ThreadLocalDataStore.setValue(Constants.BOOKING_ID,bookingId);
        System.out.println("Thread ID: " + Thread.currentThread().getId() + " "
                + "Created Booking ID: " + ThreadLocalDataStore.getValue(Constants.BOOKING_ID));
    }

    @Test(priority = 2)
    public void getBookingDetails() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("Thread ID: " + Thread.currentThread().getId() + " "
                + "Get Booking ID: " + ThreadLocalDataStore.getValue(Constants.BOOKING_ID));

        int id = (int) ThreadLocalDataStore.getValue(Constants.BOOKING_ID);
        Response response = given().
                baseUri("https://restful-booker.herokuapp.com").
                contentType("application/json").
                when().
                get("/booking/" + id).
                then().
                log().all().extract().response();
    }
}
