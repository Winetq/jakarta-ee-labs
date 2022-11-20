package jakarta.ee.present;

import jakarta.ee.present.dto.GetPresentWrapperResponse;
import jakarta.ee.present.dto.PresentWrapperRequest;
import jakarta.ee.user.User;
import jakarta.ee.user.UserRoleType;
import jakarta.ee.user.UserService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/users/{userId}/presents")
public class UserPresentsController {

    private PresentWrapperService presentWrapperService;

    private UserService userService;

    /**
     * JAX-RS requires no-args constructor.
     */
    public UserPresentsController() {}

    @EJB
    public void setPresentWrapperService(PresentWrapperService presentWrapperService) {
        this.presentWrapperService = presentWrapperService;
    }

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPresents(@PathParam("userId") Long userId) {
        Optional<List<PresentWrapper>> userPresents = presentWrapperService.findAllByUserId(userId);
        if (userPresents.isPresent()) {
            return Response
                    .ok(GetPresentWrapperResponse.entitiesToDtoMapper().apply(userPresents.get()))
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{presentWrapperId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPresent(@PathParam("userId") Long userId, @PathParam("presentWrapperId") Long presentWrapperId) {
        Optional<List<PresentWrapper>> userPresents = presentWrapperService.findAllByUserId(userId);
        if (userPresents.isPresent()) {
            Optional<PresentWrapper> present = getUserPresent(userPresents.get(), presentWrapperId);
            if (present.isPresent()) {
                return Response
                        .ok(GetPresentWrapperResponse.entityToDtoMapper().apply(present.get()))
                        .build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{presentWrapperId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUserPresent(@PathParam("userId") Long userId, @PathParam("presentWrapperId") Long presentWrapperId) {
        Optional<List<PresentWrapper>> userPresents = presentWrapperService.findAllByUserId(userId);
        if (userPresents.isPresent()) {
            Optional<PresentWrapper> present = getUserPresent(userPresents.get(), presentWrapperId);
            if (present.isPresent()) {
                presentWrapperService.delete(present.get());
                return Response.status(Response.Status.NO_CONTENT).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{presentWrapperId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUserPresent(@PathParam("userId") Long userId,
                                            @PathParam("presentWrapperId") Long presentWrapperId,
                                            PresentWrapperRequest request) {
        Optional<List<PresentWrapper>> userPresents = presentWrapperService.findAllByUserId(userId);
        if (userPresents.isPresent()) {
            Optional<PresentWrapper> present = getUserPresent(userPresents.get(), presentWrapperId);
            if (present.isPresent()) {
                presentWrapperService.update(PresentWrapperRequest.dtoToEntityMapper().apply(present.get(), request));
                return Response.status(Response.Status.NO_CONTENT).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed(UserRoleType.USER)
    public Response createUserPresent(@PathParam("userId") Long userId, PresentWrapperRequest request) {
        Optional<User> user = userService.find(userId);
        if (user.isPresent()) {
            presentWrapperService.create(new PresentWrapper(
                    presentWrapperService.getMaxId(presentWrapperService.findAll()) + 1,
                    request.getPresent(),
                    null,
                    user.get(),
                    request.getDedication(),
                    request.getPrice())
            );
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    private Optional<PresentWrapper> getUserPresent(List<PresentWrapper> usersPresents, Long presentWrapperId) {
        return usersPresents
                .stream()
                .filter(presentWrapper -> presentWrapper.getId().longValue() == presentWrapperId.longValue())
                .findFirst();
    }
}
