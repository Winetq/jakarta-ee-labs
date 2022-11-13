package jakarta.ee.present;

import jakarta.ee.santaclaus.SantaClaus;
import jakarta.ee.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@Entity
@Table(name = "presents")
public class PresentWrapper {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private Present present;

    @ManyToOne(fetch = FetchType.LAZY)
    private SantaClaus santaClaus;

    @ManyToOne(fetch = FetchType.LAZY)
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
