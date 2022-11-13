package jakarta.ee.santaclaus;

import jakarta.ee.present.PresentWrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@Table(name = "santa_clauses")
public class SantaClaus {

    @Id
    private Long id;

    private String name;

    private double moveSpeed;

    private int elves;

    @OneToMany(mappedBy = "santaClaus")
    private List<PresentWrapper> presents;

    public SantaClaus(Long id, String name, double moveSpeed, int elves) {
        this.id = id;
        this.name = name;
        this.moveSpeed = moveSpeed;
        this.elves = elves;
    }
}
