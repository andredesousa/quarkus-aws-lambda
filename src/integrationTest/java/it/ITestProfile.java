package it;

import io.quarkus.test.junit.QuarkusTestProfile;
import java.time.Duration;
import java.util.Map;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

public class ITestProfile implements QuarkusTestProfile {

    static DockerImageName image = DockerImageName.parse("localstack/localstack:0.14.5");

    static LocalStackContainer localStackContainer = new LocalStackContainer(image)
        .withExposedPorts(4566)
        .withServices(Service.DYNAMODB)
        .withEnv("DEBUG", "1")
        .withEnv("SKIP_SSL_CERT_DOWNLOAD", "1")
        .waitingFor(Wait.forLogMessage(".*plugin localstack.hooks.on_infra_ready.*\\n", 1))
        .withStartupTimeout(Duration.ofSeconds(120));

    public ITestProfile() {
        localStackContainer.start();
    }

    public Map<String, String> getConfigOverrides() {
        String address = "http://localhost:" + localStackContainer.getMappedPort(4566);

        return Map.of("quarkus.dynamodb.endpoint-override", address);
    }
}
