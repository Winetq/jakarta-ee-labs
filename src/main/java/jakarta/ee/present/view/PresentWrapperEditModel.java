package jakarta.ee.present.view;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PresentWrapperEditModel {
    private String present;
    private String santaClausName;
    private String dedication;
    private String price;
}
