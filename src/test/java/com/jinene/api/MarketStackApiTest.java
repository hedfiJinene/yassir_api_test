package com.jinene.api;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class MarketStackApiTest {

    @BeforeClass
    public void setup() {
        // L'endpoint principal pour MarketStack intraday
        RestAssured.baseURI = "http://api.marketstack.com/v2";
    }

    @Test
    public void testMarketStackIntraday() {
        // ✅ Clé d'accès MarketStack
        String accessKey = "ea04482fdc8e6760e7d858afb777cf7a";
        String symbols = "AAPL"; // Apple Inc.

        // Envoi de la requête
        Response response = RestAssured
                .given()
                    .queryParam("access_key", accessKey)
                    .queryParam("symbols", symbols)
                .when()
                    .get("/eod")
                .then()
                    .extract().response();

        // Affiche la réponse JSON
        System.out.println(response.asPrettyString());

        // Vérifie que le code HTTP est 200
        Assert.assertEquals(response.statusCode(), 200, "Le statut HTTP doit être 200");

        // Vérifie qu'il n'y a pas d'erreur dans la réponse
        if (response.jsonPath().get("error") != null) {
            Assert.fail("Erreur API : " + response.jsonPath().getMap("error").toString());
        }

        // Vérifie qu'on a reçu des données
        int dataSize = response.jsonPath().getList("data").size();
        Assert.assertTrue(dataSize > 0, "La liste 'data' ne doit pas être vide");

        // Vérifie que le symbole du premier enregistrement correspond à AAPL
        String returnedSymbol = response.jsonPath().getString("data[0].symbol");
        Assert.assertEquals(returnedSymbol, "AAPL", "Le symbole retourné doit être AAPL");
    }



}

