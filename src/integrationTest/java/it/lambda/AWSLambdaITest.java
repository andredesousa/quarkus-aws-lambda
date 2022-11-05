package it.lambda;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.response.Response;
import it.ITestProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestProfile(ITestProfile.class)
@DisplayName("Integration Tests")
class AWSLambdaITest {

    @Test
    @DisplayName("(POST)")
    public void testAWSLambda() {
        Response response = given().body(1).when().post().thenReturn();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.as(Integer.class)).isEqualTo(1);
    }
}
