package jakarta.ee.user;

import jakarta.ee.user.dto.GetUserResponse;
import jakarta.ee.user.dto.PostUserRequest;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/users")
public class UserController {

    private UserService service;

    /**
     * JAX-RS requires no-args constructor.
     */
    public UserController() {}

    @EJB
    public void setService(UserService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        return Response
                .ok(GetUserResponse.entitiesToDtoMapper().apply(service.findAll()))
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") Long id) {
        Optional<User> user = service.find(id);
        if (user.isPresent()) {
            return Response
                    .ok(GetUserResponse.entityToDtoMapper().apply(user.get()))
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(PostUserRequest request) {
        service.create(new User(
                getMaxId(service.findAll()) + 1,
                request.getName(),
                request.getSurname(),
                request.getBirthday(),
                request.getLogin(),
                request.getPassword(),
                request.getUserRoles(),
                "",
                new byte[0]
        ));
        return Response.status(Response.Status.CREATED).build();
    }

    private long getMaxId(List<User> users) {
        return users.stream().mapToLong(User::getId).max().orElse(0);
    }
}
