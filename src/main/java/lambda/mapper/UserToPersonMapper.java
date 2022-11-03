package lambda.mapper;

import lambda.entity.Person;
import lambda.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserToPersonMapper {
    public Person userToPerson(User user);

    public User personToUser(Person person);
}
