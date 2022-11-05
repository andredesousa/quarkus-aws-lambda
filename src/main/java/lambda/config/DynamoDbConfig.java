package lambda.config;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@ApplicationScoped
public class DynamoDbConfig {

    @Inject
    protected transient DynamoDbClient dbClient;

    @Inject
    protected transient DynamoDbEnhancedClient enhancedClient;

    /**
     * Creates a DynamoDb table if it doesn't exist.
     * @param clazz must not be null.
     */
    public <T> void createTable(Class<T> clazz) {
        if (!dbClient.listTables().tableNames().contains(clazz.getSimpleName())) {
            enhancedClient.table(clazz.getSimpleName(), TableSchema.fromClass(clazz)).createTable();
        }
    }

    /**
     * Retrieves a DynamoDb table by its class.
     * @param clazz must not be null.
     * @return the DynamoDb table instance.
     */
    public <T> DynamoDbTable<T> getTable(Class<T> clazz) {
        return enhancedClient.table(clazz.getSimpleName(), TableSchema.fromClass(clazz));
    }
}
