package jakarta.ee.santaclaus.view;

import jakarta.ee.santaclaus.SantaClaus;
import jakarta.ee.santaclaus.SantaClausService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@RequestScoped
@Named
public class SantaClausList implements Serializable {

    private SantaClausService service;

    private List<SantaClaus> santaClauses;

    @EJB
    public void setService(SantaClausService service) {
        this.service = service;
    }

    public List<SantaClaus> getSantaClauses() {
        if (santaClauses == null) {
            santaClauses = service.findAll();
        }
        return santaClauses;
    }

    public String deleteAction(SantaClaus santaClaus) {
        service.delete(santaClaus);
        return "santaclaus_list?faces-redirect=true";
    }
}
