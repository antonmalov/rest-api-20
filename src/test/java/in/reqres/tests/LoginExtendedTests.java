package in.reqres.tests;

import in.reqres.models.lombok.LoginBodyLombokModel;
import in.reqres.models.pojo.LoginBodyPojoModel;
import in.reqres.models.lombok.LoginResponseLombokModel;
import in.reqres.models.pojo.LoginResponsePojoModel;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.Test;

import static in.reqres.specs.LoginSpec.loginRequestSpec;
import static in.reqres.specs.LoginSpec.loginResponseSpec;
import static io.qameta.allure.Allure.step;
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
        LoginBodyPojoModel authData = new LoginBodyPojoModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponsePojoModel loginResponse = given()
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
                .extract().as(LoginResponsePojoModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken());
    }

    @Test
    void successfulLoginWithLombokModelsTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel loginResponse = given()
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
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken());
    }

    @Test
    void successfulLoginWithAllureTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel loginResponse = given()
                .log().uri()
                .log().method()
                .log().body()
                .filter(new AllureRestAssured())
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken());
    }

    @Test
    void successfulLoginWithCustomAllureTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel loginResponse = given()
                .log().uri()
                .log().method()
                .log().body()
                .filter(new AllureRestAssured())
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken());
    }

    @Test
    void successfulLoginWithStepsTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");


        LoginResponseLombokModel loginResponse = step("Make request", () ->
                    given()
                            .log().uri()
                            .log().method()
                            .log().body()
                            .filter(new AllureRestAssured())
                            .contentType(JSON)
                            .body(authData)
                            .when()
                            .post("https://reqres.in/api/login")
                            .then()
                            .log().status()
                            .log().body()
                            .statusCode(200)
                            .extract().as(LoginResponseLombokModel.class));


        step("check response", () ->
                    assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken()));
    }

    @Test
    void successfulLoginWithSpecsTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");


        LoginResponseLombokModel loginResponse = step("Make request", () ->
                given(loginRequestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(loginResponseSpec)
                        .extract().as(LoginResponseLombokModel.class));


        step("check response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken()));
    }
}
