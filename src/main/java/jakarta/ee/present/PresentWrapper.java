package jakarta.ee.present;

import jakarta.ee.santaclaus.SantaClaus;
import jakarta.ee.user.User;
import lombok.Getter;

@Getter
public class PresentWrapper {
    private Long id;
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
}
