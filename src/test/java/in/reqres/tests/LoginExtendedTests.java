package in.reqres.tests;

import in.reqres.models.LoginBodyModel;
import in.reqres.models.LoginResponseModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginExtendedTests {
    /*
    1.Make POST request to https://reqres.in/api/login
    with body: { "email": "eve.holt@reqres.in", "password": "cityslicka" }
    2.Get response {"token": "QpwL5tke4Pnpja7X4"}
    3.Check token is QpwL5tke4Pnpja7X4
     */

    @Test
    void successfulBadPracticeLoginTest() {
        String authData = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }"; //BAD PRACTICE

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void negative415Test() {

        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(415);
    }

    @Test
    void negative400Test() {
        String authData = "{ \"email\": \"eve.holt@reqs.in\", \"password\": \"cityslicka\" }"; //BAD PRACTICE

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400);
    }

    @Test
    void missingPasswordTest() {
        String authData = "{ \"email\": \"eve.holt@reqs.in\", \"password\": \"\" }"; //BAD PRACTICE

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void missingMailTest() {
        String authData = "{ \"email\": \"\", \"password\": \"cityslicka\" }";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void successfulLoginWithPojoModelsTest() {
        //String authData = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }"; //BAD PRACTICE
        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseModel loginResponse = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken());

    }
}
