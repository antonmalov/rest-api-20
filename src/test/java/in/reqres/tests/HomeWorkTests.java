package in.reqres.tests;

import in.reqres.models.lombok.homeworkmodel.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static in.reqres.specs.HomeWorkSpecs.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class HomeWorkTests {

    @Test
    void getUserWithId2() {
        UserData userResponse = step("Make request", () ->
                given(getUserRequestSpec)
                .when()
                .get("/users/2")
                .then()
                .spec(getUserResponseSpec)
                .extract().as(UserData.class));

        step("check response", () -> {
            assertEquals(2, userResponse.getData().getId());
            assertEquals("Janet", userResponse.getData().getFirstName());
            assertEquals("Weaver", userResponse.getData().getLastName());
            assertEquals("https://reqres.in/img/faces/2-image.jpg", userResponse.getData().getAvatar());
        });
    }

    @Test
    void getListUsers() {

        List<Integer> actualSize = step("Make request", () ->
                given(getUserRequestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(getUserResponseSpec)
                .extract().path("data.id"));

        step("Check response", () ->
        assertEquals(6, actualSize.size()));
    }

    @Test
    void creteUserSuccess() {
        RequestUserModel requestData = new RequestUserModel();
        requestData.setName("morpheus");
        requestData.setJob("leader");

        ResponseCreateUserModel responseUser = step("Make request", () ->
                given(userRequestSpec)
                .body(requestData)
                .when()
                .post("/users")
                .then()
                .spec(createUserResponseSpec)
                .extract().as(ResponseCreateUserModel.class));

        step("check response", () -> {
            assertEquals("morpheus", responseUser.getName());
            assertEquals("leader", responseUser.getJob());
        });
    }

    @Test
    void updateUserSuccess() {
        RequestUserModel requestData = new RequestUserModel();
        requestData.setName("morpheus");
        requestData.setJob("zion resident");

        ResponseUpdateUserModel responseUser = step("Make request", () ->
                given(userRequestSpec)
                .body(requestData)
                .when()
                .put("/users/2")
                .then()
                .spec(updateUserResponseSpec)
                .extract().as(ResponseUpdateUserModel.class));

        step("check response", () -> {
            assertEquals("morpheus", responseUser.getName());
            assertEquals("zion resident", responseUser.getJob());
        });

    }

    @Test
    void registerSuccessful() {
        RegisterRequestModel registerData = new RegisterRequestModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");

        RegisterResponseModel response = step("Make request", () ->
                given(userRequestSpec)
                .body(registerData)
                .when()
                .post("/register")
                .then()
                .spec(registerResponseSpec)
                .extract().as(RegisterResponseModel.class));

        step("check response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));

    }

    @Test
    void registerUnsuccessful() {
        RegisterRequestModel registerData = new RegisterRequestModel();
        registerData.setEmail("sydney@fife");

        RegisterResponseErrorModel registerResponse = step("Make request", () ->
                given(userRequestSpec)
                .body(registerData)
                .when()
                .post("/register")
                .then()
                .spec(missingPassword400Spec)
                .extract().as(RegisterResponseErrorModel.class));

        step("check response", () ->
                assertEquals("Missing password", registerResponse.getError()));
    }
}
