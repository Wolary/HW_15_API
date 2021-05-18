import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;


public class ApiTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void notFound() {
        given()
                .when()
                .get("/api/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    void updateUser() {
        given()
                .contentType(JSON)
                .body("{ \"name\": \"morpheus\", " +
                        "\"job\": \"zion resident\" }")
                .when()
                .put("/api/users")
                .then()
                .statusCode(200)
                .body("job", is("zion resident"));
    }

    @Test
    void singleUser() {
        given()
                .when()
                .get("/api/users/2")
                .then()
                .statusCode(200)
                .body("data.id", is(2));
    }

    @Test
    void listResource() {
        given()
                .when()
                .get("/api/unknown")
                .then()
                .statusCode(200)
                .body("total", is(12))
                .body("data", not(nullValue()))
                .body("support.text", is("To keep ReqRes free, " + "contributions towards server costs are appreciated!"));

    }

    @Test
    void loginUser() {
        given()
                .contentType(JSON)
                .body("{ \"email\": \"eve.holt@reqres.in\", " +
                        "\"password\": \"cityslicka\" }")
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
}

