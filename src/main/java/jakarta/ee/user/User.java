package jakarta.ee.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Getter
    @Id
    private Long id;

    @Getter
    private String name;

    @Getter
    private String surname;

    @Getter
    private LocalDate birthday;

    @Getter
    private String login;

    private String password;

    @Getter
    @Enumerated(EnumType.STRING)
    private Role role;

    @Getter
    @Setter
    @Transient
    private String avatarFileName;

    @Getter
    @Setter
    @Transient
    private byte[] avatar;
}
