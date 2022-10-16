package jakarta.ee.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
public class User {
    @Getter
    private final Long id;

    @Getter
    private String name;

    @Getter
    private String surname;

    @Getter
    private final LocalDate birthday;

    @Getter
    private String login;

    private String password;

    @Getter
    private Role role;

    @Getter
    @Setter
    private String avatarFileName;

    @Getter
    @Setter
    private byte[] avatar;
}
