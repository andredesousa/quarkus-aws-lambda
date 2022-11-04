package lambda.mapper;

import api.model.User;
import lambda.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserToPersonMapper {
    public Person userToPerson(User user);
}
