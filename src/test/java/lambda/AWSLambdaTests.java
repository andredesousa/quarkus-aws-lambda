package lambda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import lambda.service.ProcessingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("AWSLambda")
@ExtendWith(MockitoExtension.class)
public class AWSLambdaTests {

    @Mock
    transient ProcessingService processingService;

    @InjectMocks
    transient AWSLambda lambda;

    @Test
    @DisplayName("#handleRequest returns a number")
    void handleRequest() {
        when(processingService.process(1L, null)).thenReturn(1L);

        assertThat(lambda.handleRequest(1L, null)).isEqualTo(1L);
    }
}
