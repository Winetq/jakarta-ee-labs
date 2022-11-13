package jakarta.ee.present.dto;

import jakarta.ee.present.Present;
import jakarta.ee.present.PresentWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.BiFunction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PresentWrapperRequest {
    private Present present;
    private String dedication;
    private double price;

    public static BiFunction<PresentWrapper, PresentWrapperRequest, PresentWrapper> dtoToEntityMapper() {
        return (presentWrapper, presentWrapperRequest) -> PresentWrapper.builder()
                .id(presentWrapper.getId())
                .present(presentWrapperRequest.getPresent())
                .santaClaus(presentWrapper.getSantaClaus())
                .user(presentWrapper.getUser())
                .dedication(presentWrapperRequest.getDedication())
                .price(presentWrapperRequest.getPrice())
                .build();
    }
}
