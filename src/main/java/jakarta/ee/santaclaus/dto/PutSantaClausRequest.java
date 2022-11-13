package jakarta.ee.santaclaus.dto;

import jakarta.ee.santaclaus.SantaClaus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.BiFunction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PutSantaClausRequest {
    private double moveSpeed;
    private int elves;

    public static BiFunction<SantaClaus, PutSantaClausRequest, SantaClaus> dtoToEntityMapper() {
        return (santaClaus, putSantaClausRequest) -> SantaClaus.builder()
                .id(santaClaus.getId())
                .name(santaClaus.getName())
                .moveSpeed(putSantaClausRequest.getMoveSpeed())
                .elves(putSantaClausRequest.getElves())
                .build();
    }
}
