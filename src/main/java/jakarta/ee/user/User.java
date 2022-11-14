package jakarta.ee.user;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    private Long id;

    private String name;

    private String surname;

    private LocalDate birthday;

    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Setter
    private String avatarFileName;

    @Setter
    private byte[] avatar;
}
