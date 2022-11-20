package jakarta.ee.present;

import jakarta.ee.present.dto.GetPresentWrapperResponse;
import jakarta.ee.present.dto.PresentWrapperRequest;
import jakarta.ee.user.UserRoleType;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/presents")
@RolesAllowed(UserRoleType.ADMIN)
public class PresentWrapperController {

    private PresentWrapperService service;

    /**
     * JAX-RS requires no-args constructor.
     */
    public PresentWrapperController() {}

    @EJB
    public void setService(PresentWrapperService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPresents() {
        return Response
                    .ok(GetPresentWrapperResponse.entitiesToDtoMapper().apply(service.findAll()))
                    .build();
    }

    @GET
    @Path("{presentWrapperId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPresent(@PathParam("presentWrapperId") Long presentWrapperId) {
        Optional<PresentWrapper> present = service.find(presentWrapperId);
        if (present.isPresent()) {
            return Response
                        .ok(GetPresentWrapperResponse.entityToDtoMapper().apply(present.get()))
                        .build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{presentWrapperId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePresent(@PathParam("presentWrapperId") Long presentWrapperId) {
        Optional<PresentWrapper> present = service.find(presentWrapperId);
        if (present.isPresent()) {
            service.delete(present.get());
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{presentWrapperId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePresent(@PathParam("presentWrapperId") Long presentWrapperId, PresentWrapperRequest request) {
        Optional<PresentWrapper> present = service.find(presentWrapperId);
        if (present.isPresent()) {
            service.update(PresentWrapperRequest.dtoToEntityMapper().apply(present.get(), request));
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
