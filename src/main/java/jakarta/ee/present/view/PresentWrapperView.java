package jakarta.ee.present.view;

import jakarta.ee.present.PresentWrapper;
import jakarta.ee.present.PresentWrapperService;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

@RequestScoped
@Named
public class PresentWrapperView implements Serializable {

    private PresentWrapperService service;

    @Getter
    private PresentWrapper present;

    @Setter
    @Getter
    private Long id;

    @EJB
    public void setService(PresentWrapperService service) {
        this.service = service;
    }

    public void init() throws IOException {
        Optional<PresentWrapper> present = service.find(id);
        if (present.isPresent()) {
            this.present = present.get();
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Present not found!");
        }
    }
}
