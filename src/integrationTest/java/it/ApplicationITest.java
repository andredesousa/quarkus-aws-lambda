package it;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DisplayName("Integration Tests")
class ApplicationITest {

    @Container
    static GenericContainer<?> app = new GenericContainer<>("localstack/localstack:1.2.0")
        .withExposedPorts(4566)
        .withEnv("DEBUG", "1")
        .withEnv("SERVICES", "dynamodb")
        .waitingFor(Wait.forLogMessage(".*Ready.*\\n", 1));

    @Test
    @DisplayName("(POST)")
    public void testAWSLambda() {
        given().body(1).when().post().then().statusCode(200);
    }
}
