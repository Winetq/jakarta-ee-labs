package jakarta.ee.configuration;

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

    @Inject
    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private synchronized void init() {
        userService.create(new User(1L, "User", "One", LocalDate.of(1999, 9, 9),
                "admin", "admin", Role.USER, getResourceAsByteArray("avatar/calvian.png")));
        userService.create(new User(2L, "User", "Two", LocalDate.of(1989, 3, 27),
                "admin", "admin", Role.USER, getResourceAsByteArray("avatar/uhlbrecht.png")));
        userService.create(new User(3L, "User", "Three", LocalDate.of(1998, 12, 12),
                "admin", "admin", Role.USER, getResourceAsByteArray("avatar/eloise.png")));
        userService.create(new User(4L, "User", "Four", LocalDate.of(2000, 2, 24),
                "admin", "admin", Role.USER, getResourceAsByteArray("avatar/zereni.png")));
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        }
    }
}
