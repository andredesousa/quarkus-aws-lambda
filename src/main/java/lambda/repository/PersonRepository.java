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

    /**
     * Retrieves all instances of the type.
     * @return all entities.
     */
    public List<Person> findAll() {
        return db.getTable(Person.class).scan().items().stream().collect(Collectors.toList());
    }

    /**
     * Retrieves an entity by its id.
     * @param id must not be null.
     * @return the entity with the given id.
     */
    public Person findById(Long id) {
        return db.getTable(Person.class).getItem(Key.builder().partitionValue(id).build());
    }

    /**
     * Saves a given entity.
     * @param person must not be null.
     */
    public void insert(Person person) {
        db.getTable(Person.class).putItem(person);
    }

    /**
     * Updates a given entity.
     * @param person must not be null.
     */
    public void update(Person person) {
        db.getTable(Person.class).updateItem(person);
    }

    /**
     * Deletes the entity with the given id.
     * @param id id must not be null.
     */
    public void deleteById(Long id) {
        db.getTable(Person.class).deleteItem(findById(id));
    }
}
