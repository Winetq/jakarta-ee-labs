package jakarta.ee.santaclaus.view;

import jakarta.ee.santaclaus.SantaClaus;
import jakarta.ee.santaclaus.SantaClausService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequestScoped
@Named
public class SantaClausView {

    private final SantaClausService service;

    @Getter
    private SantaClaus santaClaus;

    @Setter
    @Getter
    private Long id;

    @Inject
    public SantaClausView(SantaClausService service) {
        this.service = service;
    }

    public void init() throws IOException {
        Optional<SantaClaus> santaClaus = service.find(id);
        if (santaClaus.isPresent()) {
            this.santaClaus = santaClaus.get();
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Santa Claus not found!");
        }
    }
}
