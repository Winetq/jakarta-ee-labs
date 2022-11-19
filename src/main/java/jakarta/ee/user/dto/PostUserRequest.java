package jakarta.ee.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserRequest {
    private String name;
    private String surname;
    private LocalDate birthday;
    private String login;
    private String password;
    private List<String> userRoles;
}
