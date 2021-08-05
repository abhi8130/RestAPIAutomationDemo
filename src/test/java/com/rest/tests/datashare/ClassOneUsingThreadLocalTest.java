package com.rest.tests.datashare;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
/*        Exception while running tests in parallel -> org.testng.TestNGException:
        TestNG by default disables loading DTD from unsecured Urls.
        If you need to explicitly load the DTD from a http url,
        please do so by using the JVM argument [-Dtestng.dtd.http=true]
        https://stackoverflow.com/questions/57299606/testng-by-default-disables-loading-dtd-from-unsecure-urls*/


public class ClassOneUsingThreadLocalTest {
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
        Thread.sleep(1000);
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
