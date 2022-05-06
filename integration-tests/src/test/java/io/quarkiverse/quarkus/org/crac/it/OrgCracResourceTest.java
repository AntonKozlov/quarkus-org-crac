package io.quarkiverse.quarkus.org.crac.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class OrgCracResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/org-crac")
                .then()
                .statusCode(200)
                .body(is("Hello org-crac"));
    }
}
