package jakarta.ee.present.view;

import jakarta.ee.present.Present;
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
import java.util.NoSuchElementException;
import java.util.Optional;

@SessionScoped
@Named
public class PresentWrapperEdit implements Serializable {

    private PresentWrapperService presentWrapperService;

    private SantaClausService santaClausService;

    private PresentWrapper present;

    @Getter
    private PresentWrapperEditModel model;

    @Setter
    @Getter
    private Long id;

    public PresentWrapperEdit() {}

    @EJB
    public void setPresentWrapperService(PresentWrapperService presentWrapperService) {
        this.presentWrapperService = presentWrapperService;
    }

    @EJB
    public void setSantaClausService(SantaClausService santaClausService) {
        this.santaClausService = santaClausService;
    }

    public void init() throws IOException {
        Optional<PresentWrapper> present = presentWrapperService.find(id);
        if (present.isPresent()) {
            this.present = present.get();
            initModel();
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Present not found!");
        }
    }

    private void initModel() {
        this.model = PresentWrapperEditModel.builder()
                .present(present.getPresent().name())
                .santaClausName(present.getSantaClaus().getName())
                .dedication(present.getDedication())
                .price(Double.toString(present.getPrice()))
                .build();
    }

    public String updateAction() {
        try {
            Present updatedPresent = Present.of(model.getPresent());
            Optional<SantaClaus> maybeUpdatedSantaClaus = santaClausService.find(model.getSantaClausName());
            SantaClaus updatedSantaClaus = maybeUpdatedSantaClaus.orElse(present.getSantaClaus());
            String updatedDedication = model.getDedication();
            double updatedPrice = Double.parseDouble(model.getPrice());
            presentWrapperService.update(PresentWrapper.builder()
                    .id(present.getId())
                    .present(updatedPresent)
                    .santaClaus(updatedSantaClaus)
                    .user(present.getUser())
                    .dedication(updatedDedication)
                    .price(updatedPrice)
                    .build()
            );
            return "present_edit.xhtml?id=" + id + "&faces-redirect=true";
        } catch (IllegalArgumentException | NoSuchElementException | NullPointerException e) {
            return "present_edit.xhtml?id=" + id + "&faces-redirect=true";
        }
    }
}
