package ru.practicum.yandex.config;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Config {
    public static String URL = "https://stellarburgers.nomoreparties.site/api/";

    protected RequestSpecification getSpec(){
        return given().log().all()
                .header("Content-type", "application/json")
                .baseUri(URL);
    }
}
