package lambda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import api.service.ApiException;
import io.quarkus.logging.Log;
import lambda.service.ProcessingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("AWSLambda")
@ExtendWith(MockitoExtension.class)
public class AWSLambdaTests {

    static MockedStatic<Log> logs;

    @Mock
    transient ProcessingService processingService;

    @InjectMocks
    transient AWSLambda lambda;

    @BeforeAll
    static void beforeAll() {
        logs = mockStatic(Log.class);
    }

    @Test
    @DisplayName("#handleRequest returns a number")
    void handleRequest() throws Exception {
        when(processingService.process(1, null)).thenReturn(1);

        assertThat(lambda.handleRequest(1, null)).isEqualTo(1);
    }

    @Test
    @DisplayName("#handleRequest catch an exception")
    void handleException() throws Exception {
        when(processingService.process(1, null)).thenThrow(new ApiException());

        assertThat(lambda.handleRequest(1, null)).isEqualTo(-1);
    }
}
