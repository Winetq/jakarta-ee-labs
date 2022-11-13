package jakarta.ee.present;

import jakarta.ee.present.dto.GetPresentWrapperResponse;
import jakarta.ee.present.dto.PresentWrapperRequest;
import jakarta.ee.santaclaus.SantaClaus;
import jakarta.ee.santaclaus.SantaClausService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
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
        Optional<List<PresentWrapper>> santaClausPresents = presentWrapperService.findAllBySantaClausId(santaClausId);
        if (santaClausPresents.isPresent()) {
            return Response
                    .ok(GetPresentWrapperResponse.entitiesToDtoMapper().apply(santaClausPresents.get()))
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
        Optional<List<PresentWrapper>> santaClausPresents = presentWrapperService.findAllBySantaClausId(santaClausId);
        if (santaClausPresents.isPresent()) {
            Optional<PresentWrapper> present = santaClausPresents.get().stream()
                    .filter(presentWrapper -> presentWrapper.getId().longValue() == presentWrapperId.longValue())
                    .findFirst();
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
        Optional<List<PresentWrapper>> santaClausPresents = presentWrapperService.findAllBySantaClausId(santaClausId);
        if (santaClausPresents.isPresent()) {
            Optional<PresentWrapper> present = santaClausPresents.get().stream()
                    .filter(presentWrapper -> presentWrapper.getId().longValue() == presentWrapperId.longValue())
                    .findFirst();
            if (present.isPresent()) {
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

    @PUT
    @Path("{presentWrapperId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSantaClausPresent(@PathParam("santaClausId") Long santaClausId,
                                            @PathParam("presentWrapperId") Long presentWrapperId,
                                            PresentWrapperRequest request) {
        Optional<SantaClaus> santaClaus = santaClausService.find(santaClausId);
        if (santaClaus.isPresent()) {
            Optional<PresentWrapper> present = santaClaus.get().getPresents(presentWrapperId);
            if (present.isPresent()) {
                present.get().update(request.getPresent(), request.getDedication(), request.getPrice());
                return Response
                        .status(Response.Status.NO_CONTENT)
                        .build();
            }
        }
        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSantaClausPresent(@PathParam("santaClausId") Long santaClausId, PresentWrapperRequest request) {
        Optional<SantaClaus> santaClaus = santaClausService.find(santaClausId);
        if (santaClaus.isPresent()) {
            presentWrapperService.create(new PresentWrapper(
                    presentWrapperService.getMaxId(presentWrapperService.findAll()) + 1,
                    request.getPresent(),
                    santaClaus.get(),
                    null,
                    request.getDedication(),
                    request.getPrice())
            );
            return Response
                    .status(Response.Status.CREATED)
                    .build();
        }
        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }
}
