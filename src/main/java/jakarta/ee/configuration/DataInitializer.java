package jakarta.ee.configuration;

import jakarta.ee.santaclaus.SantaClaus;
import jakarta.ee.santaclaus.SantaClausService;
import jakarta.ee.user.Role;
import jakarta.ee.user.User;
import jakarta.ee.user.UserService;
import lombok.SneakyThrows;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.InputStream;
import java.time.LocalDate;

@ApplicationScoped
public class DataInitializer {
    private final UserService userService;
    private final SantaClausService santaClausService;

    @Inject
    public DataInitializer(UserService userService, SantaClausService santaClausService) {
        this.userService = userService;
        this.santaClausService = santaClausService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private synchronized void init() {
        userService.create(new User(1L, "User", "One", LocalDate.of(1999, 9, 9),
                "admin", "admin", Role.USER, "calvian.png", getResourceAsByteArray("avatar/calvian.png")));
        userService.create(new User(2L, "User", "Two", LocalDate.of(1989, 3, 27),
                "admin", "admin", Role.USER, "uhlbrecht.png", getResourceAsByteArray("avatar/uhlbrecht.png")));
        userService.create(new User(3L, "User", "Three", LocalDate.of(1998, 12, 12),
                "admin", "admin", Role.USER, "eloise.png", getResourceAsByteArray("avatar/eloise.png")));
        userService.create(new User(4L, "User", "Four", LocalDate.of(2000, 2, 24),
                "admin", "admin", Role.USER, "zereni.png", getResourceAsByteArray("avatar/zereni.png")));
        santaClausService.create(new SantaClaus(1L, "SantaClaus1", 4.25, 4));
        santaClausService.create(new SantaClaus(2L, "SantaClaus2", 2.55, 3));
        santaClausService.create(new SantaClaus(3L, "SantaClaus3", 3.75, 1));
        santaClausService.create(new SantaClaus(4L, "SantaClaus4", 1.05, 2));
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        }
    }
}
