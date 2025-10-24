
package com.jinene.api;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class WeatherstackApiTest {

    @BeforeClass
    public void setup() {
        
        RestAssured.baseURI = "http://api.weatherstack.com";
    }

    @Test
    public void testCurrentWeather() {
        // clé d'accès Weatherstack
        String accessKey = "6f6b1093859e10effcb0c0dceb3aa0c4";
        String query = "New York";

        // Envoi de la requête GET vers /current avec les bons paramètres
        Response response = RestAssured
                .given()
                    .queryParam("access_key", accessKey)
                    .queryParam("query", query)
                    .queryParam("units", "m")       // optionnel (m: métrique)
                  
                .when()
                    .get("/current")
                .then()
                    .extract().response();

        // Afficher la réponse complète dans la console
        System.out.println(response.asPrettyString());

        // Vérifier le code HTTP
        Assert.assertEquals(response.statusCode(), 200, "Le statut HTTP doit être 200");

        // Vérifier si l'API a retourné une erreur (clé invalide, requête vide, etc.)
        if (response.jsonPath().get("error") != null) {
            Assert.fail("Erreur API : " + response.jsonPath().getMap("error").toString());
        }

        // Extraire et vérifier des champs importants de la réponse JSON
        String locationName = response.jsonPath().getString("location.name");
        String countryName = response.jsonPath().getString("location.country");
        String temperature = response.jsonPath().getString("current.temperature");

        Assert.assertNotNull(locationName, "Le champ location.name ne doit pas être null");
        Assert.assertNotNull(countryName, "Le champ location.country ne doit pas être null");
        Assert.assertNotNull(temperature, "Le champ current.temperature ne doit pas être null");

        // Vérification que la ville retournée correspond à celle demandée
        Assert.assertEquals(locationName, "New York", "La ville retournée doit être New York");
    }
}