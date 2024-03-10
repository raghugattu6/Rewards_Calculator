package com.retailer;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RewardPointsCalculatorApplicationTests {

    @BeforeEach
    public void setUp() {
        //  URI 
        RestAssured.baseURI = "http://localhost:8080/customers/1001/rewards";
    }

    @Test
    public void testApi() {
        // Perform the HTTP request and get the response
        io.restassured.response.Response response = RestAssured
            .given()
            .param("customerId", 1001)
            .when()
            .get("http://localhost:8080/customers/1001/rewards");  
        // Validate the response status code
        assertEquals(200, response.getStatusCode(), "Unexpected status code");

        // Validate the response body
        String responseBody = response.getBody().asString();
        String expectedResponseBody = "{\"customerId\":1001,\"lastMonthRewardPoints\":90,\"lastSecondMonthRewardPoints\":35,\"lastThirdMonthRewardPoints\":35,\"totalRewards\":160}";
        assertEquals(expectedResponseBody, responseBody, "Unexpected response body");
        System.out.println(responseBody);
    }

}
