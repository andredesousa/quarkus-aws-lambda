package lambda.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import lambda.config.DynamoDbConfig;
import lambda.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@DisplayName("PersonRepository")
@ExtendWith(MockitoExtension.class)
public class PersonRepositoryTests {

    transient DynamoDbTable<Person> dbTable;

    @Spy
    transient DynamoDbConfig db = mock(DynamoDbConfig.class);

    @InjectMocks
    transient PersonRepository repository;

    @BeforeEach
    void beforeEach() {
        dbTable = mock(DynamoDbTable.class, RETURNS_DEEP_STUBS);
        doReturn(dbTable).when(db).getTable(Person.class);
    }

    @Test
    @DisplayName("#createTable calls #createTable from DynamoDbConfig")
    void createTable() {
        repository.createTable();

        verify(db).createTable(Person.class);
    }

    @Test
    @DisplayName("#findAll returns a list of Persons")
    void findAll() {
        when(db.getTable(Person.class).scan().items().stream().collect(any())).thenReturn(List.of());

        assertThat(repository.findAll()).isInstanceOf(List.class);
    }

    @Test
    @DisplayName("#findById returns a Person")
    void findById() {
        when(dbTable.getItem(any(Key.class))).thenReturn(new Person());

        assertThat(repository.findById(1L)).isInstanceOf(Person.class);
    }

    @Test
    @DisplayName("#insert inserts a Person")
    void insert() {
        repository.insert(new Person());

        verify(dbTable).putItem(new Person());
    }

    @Test
    @DisplayName("#update updates the Person")
    void update() {
        repository.update(new Person());

        verify(dbTable).updateItem(new Person());
    }

    @Test
    @DisplayName("#delete deletes the Person by ID")
    void delete() {
        when(dbTable.getItem(any(Key.class))).thenReturn(new Person());

        repository.delete(1L);

        verify(dbTable).deleteItem(new Person());
    }
}
