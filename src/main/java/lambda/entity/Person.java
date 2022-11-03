package lambda.entity;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@DynamoDbBean
public class Person {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private String website;

    @DynamoDbPartitionKey
    public Long getId() {
        return this.id;
    }
}
