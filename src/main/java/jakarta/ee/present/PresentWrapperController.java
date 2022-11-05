package jakarta.ee.present;

import jakarta.ee.present.dto.GetPresentWrapperResponse;
import jakarta.ee.santaclaus.SantaClaus;
import jakarta.ee.santaclaus.SantaClausService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/santaclauses/{santaClausId}/presents")
public class PresentWrapperController {

    private PresentWrapperService presentWrapperService;

    private SantaClausService santaClausService;

    /**
     * JAX-RS requires no-args constructor.
     */
    public PresentWrapperController() {}

    @Inject
    public void setPresentWrapperService(PresentWrapperService presentWrapperService) {
        this.presentWrapperService = presentWrapperService;
    }

    @Inject
    public void setSantaClausService(SantaClausService santaClausService) {
        this.santaClausService = santaClausService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSantaClausPresents(@PathParam("santaClausId") Long santaClausId) {
        Optional<SantaClaus> santaClaus = santaClausService.find(santaClausId);
        if (santaClaus.isPresent()) {
            return Response
                    .ok(GetPresentWrapperResponse.entitiesToDtoMapper().apply(santaClaus.get().getPresents()))
                    .build();
        }
        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }

    @GET
    @Path("{presentWrapperId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSantaClausPresent(@PathParam("santaClausId") Long santaClausId, @PathParam("presentWrapperId") Long presentWrapperId) {
        Optional<SantaClaus> santaClaus = santaClausService.find(santaClausId);
        if (santaClaus.isPresent()) {
            Optional<PresentWrapper> present = santaClaus.get().getPresents(presentWrapperId);
            if (present.isPresent()) {
                return Response
                        .ok(GetPresentWrapperResponse.entityToDtoMapper().apply(present.get()))
                        .build();
            }
        }
        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }

    @DELETE
    @Path("{presentWrapperId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSantaClausPresent(@PathParam("santaClausId") Long santaClausId, @PathParam("presentWrapperId") Long presentWrapperId) {
        Optional<SantaClaus> santaClaus = santaClausService.find(santaClausId);
        if (santaClaus.isPresent()) {
            Optional<PresentWrapper> present = santaClaus.get().getPresents(presentWrapperId);
            if (present.isPresent()) {
                santaClaus.get().deletePresent(present.get());
                presentWrapperService.delete(present.get());
                return Response
                        .status(Response.Status.NO_CONTENT)
                        .build();
            }
        }
        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }
}
