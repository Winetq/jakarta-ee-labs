package jakarta.ee.santaclaus;

import jakarta.ee.present.PresentWrapper;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        this.presents = new ArrayList<>();
    }

    public void addPresent(PresentWrapper present) {
        presents.add(present);
    }

    public void deletePresent(PresentWrapper present) {
        presents.remove(present);
    }

    public void update(double updatedMoveSpeed, int updatedElves) {
        moveSpeed = updatedMoveSpeed;
        elves = updatedElves;
    }

    public Optional<PresentWrapper> getPresents(Long presentWrapperId) {
        return presents
                .stream()
                .filter(presentWrapper -> presentWrapper.getId().longValue() == presentWrapperId.longValue())
                .findFirst();
    }
}
