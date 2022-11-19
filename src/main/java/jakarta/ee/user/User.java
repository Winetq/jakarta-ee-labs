package jakarta.ee.user;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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

    @Column(nullable = false, unique = true)
    private String login;

    private String password;

    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user", referencedColumnName = "login"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> userRoles;

    @Setter
    private String avatarFileName;

    @Setter
    private byte[] avatar;
}
