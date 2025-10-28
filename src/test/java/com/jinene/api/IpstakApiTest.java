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
        
    	String accessKey = System.getenv("IPSTACK_KEY");
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
    } @Test
    public void testIpGeolocation_InvalidAccessKey() {
        String invalidKey = "INVALID_KEY";
        String ip = "134.201.250.155";

        Response response = RestAssured
                .given()
                .queryParam("access_key", invalidKey)
                .when()
                .get("/" + ip)
                .then()
                .extract().response();

        System.out.println("=== Réponse clé API invalide ===");
        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.statusCode(), 200, "Même avec une clé invalide, le code est souvent 200");
        Assert.assertTrue(response.asString().contains("error"), "La réponse doit contenir un message d’erreur");
    }
    @Test
    public void testIpGeolocation_InvalidIpAddress() {
    	String accessKey = "29e3eb616a16193e8777df6f80f84cc2";
        String invalidIp = "999.999.999.999";

        Response response = RestAssured
                .given()
                .queryParam("access_key", accessKey)
                .when()
                .get("/" + invalidIp)
                .then()
                .extract().response();

        System.out.println("=== Réponse IP invalide ===");
        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.statusCode(), 200, "Le code HTTP doit être 200 même pour une IP invalide");
        Assert.assertTrue(response.asString().contains("error") || response.jsonPath().getString("ip") == null,
                "La réponse doit contenir une erreur ou une IP nulle");
    }
}
