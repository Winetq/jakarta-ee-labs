package jakarta.ee.present.dto;

import jakarta.ee.present.Present;
import jakarta.ee.present.PresentWrapper;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * GET Present Wrapper response. Contains only field's which can be displayed on frontend. Present Wrapper is defined in
 * {@link jakarta.ee.present.PresentWrapper}.
 */
@Builder
@Getter
public class GetPresentWrapperResponse {
    private final Long id;
    private final Present present;
    private final String dedication;
    private final double price;

    public static Function<PresentWrapper, GetPresentWrapperResponse> entityToDtoMapper() {
        return presentWrapper -> GetPresentWrapperResponse.builder()
                .id(presentWrapper.getId())
                .present(presentWrapper.getPresent())
                .dedication(presentWrapper.getDedication())
                .price(presentWrapper.getPrice())
                .build();
    }

    public static Function<List<PresentWrapper>, List<GetPresentWrapperResponse>> entitiesToDtoMapper() {
        return presentWrappers -> presentWrappers
                .stream()
                .map(presentWrapper -> entityToDtoMapper().apply(presentWrapper))
                .collect(Collectors.toList());
    }
}
