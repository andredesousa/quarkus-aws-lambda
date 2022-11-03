package lambda.service;

import com.amazonaws.services.lambda.runtime.Context;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lambda.entity.Person;
import lambda.entity.User;
import lambda.mapper.UserToPersonMapper;
import lambda.repository.PersonRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class ProcessingService {

    @Inject
    @RestClient
    protected transient UserService userService;

    @Inject
    protected transient UserToPersonMapper mapper;

    @Inject
    protected transient PersonRepository personRepository;

    public Long process(Long id, Context context) {
        User user = userService.getById(id);
        Person person = mapper.userToPerson(user);

        personRepository.insert(person);

        return Long.valueOf(personRepository.findAll().size());
    }
}
