package jakarta.ee.present.dto;

import jakarta.ee.present.Present;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PresentWrapperRequest {
    private Present present;
    private String dedication;
    private double price;
}
