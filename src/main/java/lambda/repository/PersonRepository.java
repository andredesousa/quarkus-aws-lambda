package lambda.repository;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lambda.config.DynamoDbConfig;
import lambda.entity.Person;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@ApplicationScoped
public class PersonRepository {

    @Inject
    protected transient DynamoDbConfig db;

    @PostConstruct
    protected void createTable() {
        db.createTable(Person.class);
    }

    public List<Person> findAll() {
        return db.getTable(Person.class).scan().items().stream().collect(Collectors.toList());
    }

    public Person findById(Long id) {
        return db.getTable(Person.class).getItem(Key.builder().partitionValue(id).build());
    }

    public void insert(Person person) {
        db.getTable(Person.class).putItem(person);
    }

    public void update(Person person) {
        db.getTable(Person.class).updateItem(person);
    }

    public void delete(Long id) {
        db.getTable(Person.class).deleteItem(findById(id));
    }
}
