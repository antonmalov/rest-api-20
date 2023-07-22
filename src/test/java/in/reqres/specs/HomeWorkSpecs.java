package in.reqres.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static in.reqres.helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;

public class HomeWorkSpecs {

    public static RequestSpecification getUserRequestSpec = with()
            .log().uri()
            .log().method()
            .log().body()
            .filter(withCustomTemplates())
            .baseUri("https://reqres.in")
            .basePath("/api");

    public static ResponseSpecification getUserResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath("schemas/get-user-schema.json"))
            .expectBody("data.id", notNullValue())
            .build();

    public static ResponseSpecification getUsersListResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath("schemas/get-user-list-schema.json"))
            .expectBody("data.id", notNullValue())
            .build();

    public static RequestSpecification userRequestSpec = with()
            .log().uri()
            .log().method()
            .log().body()
            .contentType(JSON)
            .filter(withCustomTemplates())
            .baseUri("https://reqres.in")
            .basePath("/api");

    public static ResponseSpecification createUserResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(201)
            .expectBody(matchesJsonSchemaInClasspath("schemas/create-user-schema.json"))
            .expectBody("id", notNullValue())
            .expectBody("createdAt", notNullValue())
            .build();

    public static ResponseSpecification updateUserResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath("schemas/update-user-schema.json"))
            .expectBody("updatedAt", notNullValue())
            .build();

    public static ResponseSpecification registerResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath("schemas/register-schema.json"))
            .expectBody("id", notNullValue())
            .expectBody("token", notNullValue())
            .build();

    public static ResponseSpecification missingPassword400Spec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath("schemas/register-unsuccessfull-schema.json"))
            .expectBody("error", notNullValue())
            .build();
}
