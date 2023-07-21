package in.reqres.tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class LoginTests {
    /*
    1.Make POST request to https://reqres.in/api/login
    with body: { "email": "eve.holt@reqres.in", "password": "cityslicka" }
    2.Get response {"token": "QpwL5tke4Pnpja7X4"}
    3.Check token is QpwL5tke4Pnpja7X4
     */

    @Test
    void successfulLoginTest() {
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


}
