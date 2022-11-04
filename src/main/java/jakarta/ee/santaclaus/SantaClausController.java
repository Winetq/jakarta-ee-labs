package jakarta.ee.santaclaus;

import jakarta.ee.santaclaus.dto.GetSantaClausResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/santaclauses")
public class SantaClausController {

    private SantaClausService service;

    /**
     * JAX-RS requires no-args constructor.
     */
    public SantaClausController() {}

    @Inject
    public void setService(SantaClausService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSantaClauses() {
        return Response
                .ok(GetSantaClausResponse.entitiesToDtoMapper().apply(service.findAll()))
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSantaClaus(@PathParam("id") Long id) {
        Optional<SantaClaus> santaClaus = service.find(id);
        if (santaClaus.isPresent()) {
            return Response
                    .ok(GetSantaClausResponse.entityToDtoMapper().apply(santaClaus.get()))
                    .build();
        }
        return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSantaClaus(@PathParam("id") Long id) {
        Optional<SantaClaus> santaClaus = service.find(id);
        if (santaClaus.isPresent()) {
            service.delete(santaClaus.get());
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        }
        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }
}
