package jakarta.ee.present;

import jakarta.ee.santaclaus.SantaClaus;
import jakarta.ee.user.User;
import lombok.Getter;

@Getter
public class PresentWrapper {
    private final Long id;
    private Present present;
    private SantaClaus santaClaus;
    private User user;
    private String dedication;
    private double price;

    public PresentWrapper(Long id, Present present, SantaClaus santaClaus, User user, String dedication, double price) {
        this.id = id;
        this.present = present;
        this.santaClaus = santaClaus;
        this.user = user;
        this.dedication = dedication;
        this.price = price;
        this.santaClaus.addPresent(this);
    }

    public void update(Present updatedPresent, SantaClaus updatedSantaClaus, String updatedDedication, double updatedPrice) {
        present = updatedPresent;
        updateSantaClaus(updatedSantaClaus);
        dedication = updatedDedication;
        price = updatedPrice;
    }

    private void updateSantaClaus(SantaClaus updatedSantaClaus) {
        santaClaus.deletePresent(this);
        santaClaus = updatedSantaClaus;
        santaClaus.addPresent(this);
    }
}
