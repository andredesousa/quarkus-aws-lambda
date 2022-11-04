package lambda.service;

import api.model.User;
import api.service.ApiException;
import api.service.UserApi;
import com.amazonaws.services.lambda.runtime.Context;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import lambda.entity.Person;
import lambda.mapper.UserToPersonMapper;
import lambda.repository.PersonRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class ProcessingService {

    @Inject
    @RestClient
    protected transient UserApi userService;

    @Inject
    protected transient UserToPersonMapper mapper;

    @Inject
    protected transient PersonRepository personRepository;

    public Integer process(Integer id, Context context) throws ProcessingException, ApiException {
        User user = userService.findById(id);
        Person person = mapper.userToPerson(user);

        personRepository.insert(person);

        return personRepository.findAll().size();
    }
}
