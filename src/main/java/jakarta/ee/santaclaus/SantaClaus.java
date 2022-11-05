package jakarta.ee.santaclaus;

import jakarta.ee.present.PresentWrapper;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SantaClaus {
    private final Long id;
    private final String name;
    private double moveSpeed;
    private int elves;
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
}
