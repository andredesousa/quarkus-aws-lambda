package lambda.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import lambda.entity.Person;
import lambda.entity.User;
import lambda.mapper.UserToPersonMapper;
import lambda.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("ProcessingService")
@ExtendWith(MockitoExtension.class)
public class ProcessingServiceTests {

    @Mock
    transient UserService userService;

    @Mock
    transient PersonRepository personRepository;

    @Spy
    transient UserToPersonMapper mapper = Mappers.getMapper(UserToPersonMapper.class);

    @InjectMocks
    transient ProcessingService service;

    @Test
    @DisplayName("#process inserts a Person and returns the number of results")
    void process() {
        when(userService.getById(1L)).thenReturn(new User());
        when(personRepository.findAll()).thenReturn(List.of(new Person()));

        assertThat(service.process(1L, null)).isEqualTo(1L);
        verify(personRepository).insert(any(Person.class));
    }
}
