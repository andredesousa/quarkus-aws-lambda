package lambda.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import api.model.User;
import api.service.UserApi;
import java.util.List;
import lambda.entity.Person;
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
    transient UserApi userService;

    @Mock
    transient PersonRepository personRepository;

    @Spy
    transient UserToPersonMapper mapper = Mappers.getMapper(UserToPersonMapper.class);

    @InjectMocks
    transient ProcessingService service;

    @Test
    @DisplayName("#process inserts a Person and returns the number of results")
    void process() throws Exception {
        when(userService.findById(1)).thenReturn(new User());
        when(personRepository.findAll()).thenReturn(List.of(new Person()));

        Integer result = service.process(1, null);

        assertThat(result).isEqualTo(1);
        verify(mapper).userToPerson(any(User.class));
        verify(personRepository).insert(any(Person.class));
        verify(personRepository).findAll();
    }
}
