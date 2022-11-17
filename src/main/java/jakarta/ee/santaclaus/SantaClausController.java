package jakarta.ee.santaclaus;

import jakarta.ee.santaclaus.dto.GetSantaClausResponse;
import jakarta.ee.santaclaus.dto.PostSantaClausRequest;
import jakarta.ee.santaclaus.dto.PutSantaClausRequest;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/santaclauses")
public class SantaClausController {

    private SantaClausService service;

    /**
     * JAX-RS requires no-args constructor.
     */
    public SantaClausController() {}

    @EJB
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
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSantaClaus(@PathParam("id") Long id) {
        Optional<SantaClaus> santaClaus = service.find(id);
        if (santaClaus.isPresent()) {
            service.delete(santaClaus.get());
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSantaClaus(@PathParam("id") Long id, PutSantaClausRequest request) {
        Optional<SantaClaus> santaClaus = service.find(id);
        if (santaClaus.isPresent()) {
            service.update(PutSantaClausRequest.dtoToEntityMapper().apply(santaClaus.get(), request));
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSantaClaus(PostSantaClausRequest request) {
        service.create(new SantaClaus(
                getMaxId(service.findAll()) + 1,
                request.getName(),
                request.getMoveSpeed(),
                request.getElves())
        );
        return Response.status(Response.Status.CREATED).build();
    }

    private long getMaxId(List<SantaClaus> santaClauses) {
        return santaClauses.stream().mapToLong(SantaClaus::getId).max().orElse(0);
    }
}
