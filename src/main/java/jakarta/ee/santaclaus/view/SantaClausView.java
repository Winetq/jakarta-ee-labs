package jakarta.ee.santaclaus.view;

import jakarta.ee.present.PresentWrapper;
import jakarta.ee.present.PresentWrapperService;
import jakarta.ee.santaclaus.SantaClaus;
import jakarta.ee.santaclaus.SantaClausService;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SessionScoped
@Named
public class SantaClausView implements Serializable {

    private SantaClausService santaClausService;

    private PresentWrapperService presentWrapperService;

    @Getter
    private SantaClaus santaClaus;

    @Setter
    @Getter
    private Long id;

    @EJB
    public void setSantaClausService(SantaClausService santaClausService) {
        this.santaClausService = santaClausService;
    }

    @EJB
    public void setPresentWrapperService(PresentWrapperService presentWrapperService) {
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

    public List<PresentWrapper> getSantaClausPresents() {
        return presentWrapperService.findAllBySantaClausId(santaClaus.getId()).orElse(new ArrayList<>());
    }

    public void deleteAction(PresentWrapper present) {
        presentWrapperService.delete(present);
    }
}
