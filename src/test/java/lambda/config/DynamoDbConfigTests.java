package lambda.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import lambda.entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;

@DisplayName("DynamoDbConfig")
@ExtendWith(MockitoExtension.class)
public class DynamoDbConfigTests {

    @Mock
    transient DynamoDbClient dbClient;

    @Mock
    transient DynamoDbEnhancedClient enhancedClient;

    @InjectMocks
    transient DynamoDbConfig config;

    @Test
    @DisplayName("#createTable creates the table")
    void createTable() {
        when(dbClient.listTables()).thenReturn(mock(ListTablesResponse.class, RETURNS_DEEP_STUBS));
        when(enhancedClient.table(anyString(), any(TableSchema.class))).thenReturn(mock(DynamoDbTable.class));

        config.createTable(Person.class);

        verify(enhancedClient).table(anyString(), any(TableSchema.class));
    }

    @Test
    @DisplayName("#getTable returns the table")
    void getTable() {
        when(enhancedClient.table(anyString(), any(TableSchema.class))).thenReturn(mock(DynamoDbTable.class));

        assertThat(config.getTable(Person.class)).isInstanceOf(DynamoDbTable.class);
    }
}
