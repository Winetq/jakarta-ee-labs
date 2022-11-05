package jakarta.ee.santaclaus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PutSantaClausRequest {
    private double moveSpeed;
    private int elves;
}
