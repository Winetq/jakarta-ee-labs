package jakarta.ee.present.view;

import jakarta.ee.present.Present;
import jakarta.ee.present.PresentWrapper;
import jakarta.ee.present.PresentWrapperService;
import jakarta.ee.santaclaus.SantaClaus;
import jakarta.ee.santaclaus.SantaClausService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Optional;

@SessionScoped
@Named
public class PresentWrapperCreate implements Serializable {

    private final SantaClausService santaClausService;

    private final PresentWrapperService presentWrapperService;

    private SantaClaus santaClaus;

    @Getter
    private PresentWrapperCreateModel model;

    @Setter
    @Getter
    private Long id;

    @Inject
    public PresentWrapperCreate(SantaClausService santaClausService, PresentWrapperService presentWrapperService) {
        this.santaClausService = santaClausService;
        this.presentWrapperService = presentWrapperService;
        this.model = new PresentWrapperCreateModel();
    }

    public void init() throws IOException {
        Optional<SantaClaus> santaClaus = santaClausService.find(id);
        if (santaClaus.isPresent()) {
            this.santaClaus = santaClaus.get();
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Santa Claus not found!");
        }
    }

    public String saveAction() {
        try {
            presentWrapperService.create(new PresentWrapper(
                    presentWrapperService.getMaxId(presentWrapperService.findAll()) + 1,
                    Present.of(model.getPresent()),
                    santaClaus,
                    null,
                    model.getDedication(),
                    Double.parseDouble(model.getPrice()))
            );
            return "santaclaus_view.xhtml?id=" + id + "&faces-redirect=true";
        } catch (IllegalArgumentException | NoSuchElementException | NullPointerException e) {
            return "present_create.xhtml?id=" + id + "&faces-redirect=true";
        }
    }
}
