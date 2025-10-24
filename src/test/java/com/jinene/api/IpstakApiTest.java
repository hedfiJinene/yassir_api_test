package com.jinene.api;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class IpstakApiTest {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://api.ipstack.com";
    }

    @Test
    public void testIpGeolocation() {
        String accessKey = "29e3eb616a16193e8777df6f80f84cc2";
        String ip = "134.201.250.155";

        Response response = RestAssured
                .given()
                .queryParam("access_key", accessKey)
                .when()
                .get("/" + ip)
                .then()
                .extract().response();

        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("ip"), ip);
        Assert.assertNotNull(response.jsonPath().getString("country_name"));
    }

}
