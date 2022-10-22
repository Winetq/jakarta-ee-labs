package jakarta.ee.santaclaus.view;

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
import java.util.Optional;

@SessionScoped
@Named
public class SantaClausView implements Serializable {

    private final SantaClausService santaClausService;

    private final PresentWrapperService presentWrapperService;

    @Getter
    private SantaClaus santaClaus;

    @Setter
    @Getter
    private Long id;

    @Inject
    public SantaClausView(SantaClausService santaClausService, PresentWrapperService presentWrapperService) {
        this.santaClausService = santaClausService;
        this.presentWrapperService = presentWrapperService;
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

    public String deleteAction(PresentWrapper present) {
        santaClaus.deletePresent(present);
        presentWrapperService.delete(present);
        return "santaclaus_view.xhtml?id=" + id + "&faces-redirect=true";
    }
}
