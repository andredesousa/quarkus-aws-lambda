package lambda.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import lambda.entity.User;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/users")
@RegisterRestClient
public interface UserService {
    @GET
    @Path("/{id}")
    User getById(@PathParam("id") Long id);
}
