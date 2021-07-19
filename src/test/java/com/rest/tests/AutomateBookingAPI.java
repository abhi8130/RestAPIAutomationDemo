package com.rest.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class AutomateBookingAPI {

    public String getToken;

    @Test
    public void authToken(){
        String payload = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";
        Response response =  given().
                baseUri("https://restful-booker.herokuapp.com").
                contentType("application/json").
                body(payload).
        when().
                post("/auth").
        then().
                log().all().
                extract().response();
         getToken = response.jsonPath().getString("token");
         System.out.println("Token: " + getToken);
    }

    //get all booking ids
    @Test
    public void getBookingIds(){
        int bookingID = given().
                baseUri("https://restful-booker.herokuapp.com").
                contentType("application/json").
        when().
                get("/booking").
        then().
                extract().
                response().
                path("bookingid[0]");
        System.out.println("BookingId: " + bookingID);
        //assert using Hamcrest
        assertThat(bookingID,equalTo(14));
        //assert using TestNG
        Assert.assertEquals(bookingID,14);
    }

    //create booking and validate response body using assertion
    @Test
    public void createBooking(){
        String payload = "{\n" +
                "    \"firstname\" : \"Jim\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";
        given().
                baseUri("https://restful-booker.herokuapp.com").
                contentType("application/json").
                body(payload).
        when().
                post("/booking").
        then().
                log().all().
                assertThat().
                statusCode(200).
                body("booking.firstname",equalTo("Jim"),
                        "booking.lastname",equalTo("Brown"),
                        "booking.totalprice",equalTo(111));
    }

    @Test
    public void updateBooking(){
        String payload = "{\n" +
                "    \"firstname\" : \"James\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 444,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";
        given().
                baseUri("https://restful-booker.herokuapp.com").
                contentType("application/json").
                body(payload).
                header("Cookie","token="+ getToken).
                log().all().
        when().
                put("/booking/1").
                then().
                log().all().
                assertThat().
                statusCode(200);
    }

    //partial update booking
    @Test
    public void partialUpdateBooking(){
        String payload = "{\n" +
                "    \"firstname\" : \"Abhishek\",\n" +
                "    \"lastname\" : \"Chauhan\"\n" +
                "}";
        given().
                baseUri("https://restful-booker.herokuapp.com").
                contentType("application/json").
                header("Cookie","token="+ getToken).
                body(payload).
                log().all().
        when().
                patch("/booking/15").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void deleteBooking(){
        given().
                baseUri("https://restful-booker.herokuapp.com").
                contentType("application/json").
                header("Cookie","token="+ getToken).
                log().all().
        when().
                delete("/booking/10").
        then().
                log().all().
                assertThat().
                statusCode(201);
    }

    @Test
    public void validateStatusCode(){
        given().
                baseUri("https://restful-booker.herokuapp.com").
                contentType("application/json").
       when().
                get("/booking").
       then().
                assertThat().
                statusCode(200);
    }






















}
