package jakarta.ee.santaclaus.dto;

import jakarta.ee.santaclaus.SantaClaus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * GET Santa Claus response. Contains only field's which can be displayed on frontend. Santa Claus is defined in
 * {@link jakarta.ee.santaclaus.SantaClaus}.
 */
@Builder
@Getter
public class GetSantaClausResponse {
    private final Long id;
    private final String name;
    private final double moveSpeed;
    private final int elves;

    public static Function<SantaClaus, GetSantaClausResponse> entityToDtoMapper() {
        return santaClaus -> GetSantaClausResponse.builder()
                .id(santaClaus.getId())
                .name(santaClaus.getName())
                .moveSpeed(santaClaus.getMoveSpeed())
                .elves(santaClaus.getElves())
                .build();
    }

    public static Function<List<SantaClaus>, List<GetSantaClausResponse>> entitiesToDtoMapper() {
        return santaClauses -> santaClauses
                .stream()
                .map(santaClaus -> entityToDtoMapper().apply(santaClaus))
                .collect(Collectors.toList());
    }
}
