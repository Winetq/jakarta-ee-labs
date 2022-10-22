package jakarta.ee.santaclaus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SantaClaus {
    private Long id;
    private String name;
    private double moveSpeed;
    private int elves;
}
