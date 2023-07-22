package in.reqres.tests;

import in.reqres.models.lombok.UserData;
import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class HomeWorkTests {

    @Test
    void getUserWithId2() {
        UserData userResponse = step("Make request", () ->

                given()
                .log().uri()
                .log().method()
                .log().body().filter(new AllureRestAssured())
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(UserData.class));

        step("check response", () -> {
            assertEquals(2, userResponse.getData().getId());
            assertEquals("Janet", userResponse.getData().getFirstName());
            assertEquals("Weaver", userResponse.getData().getLastName());
            assertEquals("https://reqres.in/img/faces/2-image.jpg", userResponse.getData().getAvatar());
        });
    }



//    .body("data.id", is(2))
//            .body("data.first_name", is("Janet"))
//            .body("data.last_name", is("Weaver"))
//            .body("data.last_name", is("Weaver"))
//            .body("data.avatar", is("https://reqres.in/img/faces/2-image.jpg"))

    @Test
    void getListUsers() {

        List<Integer> actualSize = given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().path("data.id");

        assertEquals(6, actualSize.size());
    }

    @Test
    void creteUserSuccess() {
        String requestData = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(requestData)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void updateUserSuccess() {
        String requestData = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(requestData)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    void registerSuccessful() {
        String registerData = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(registerData)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void registerUnsuccessful() {
        String registerData = "{ \"email\": \"sydney@fife\" }";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(registerData)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void getUserNotFound() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }
}
