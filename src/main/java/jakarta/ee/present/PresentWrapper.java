package jakarta.ee.present;

import jakarta.ee.santaclaus.SantaClaus;
import jakarta.ee.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
}
