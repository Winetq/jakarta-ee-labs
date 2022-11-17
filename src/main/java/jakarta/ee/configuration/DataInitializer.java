package jakarta.ee.configuration;

import jakarta.ee.present.Present;
import jakarta.ee.present.PresentWrapper;
import jakarta.ee.present.PresentWrapperService;
import jakarta.ee.santaclaus.SantaClaus;
import jakarta.ee.santaclaus.SantaClausService;
import jakarta.ee.user.Role;
import jakarta.ee.user.User;
import jakarta.ee.user.UserService;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import java.io.InputStream;
import java.time.LocalDate;

/**
 * EJB singleton can be forced to start automatically when application starts.
 */
@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class DataInitializer {
    private UserService userService;
    private SantaClausService santaClausService;
    private PresentWrapperService presentWrapperService;

    public DataInitializer() {}

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @EJB
    public void setSantaClausService(SantaClausService santaClausService) {
        this.santaClausService = santaClausService;
    }

    @EJB
    public void setPresentWrapperService(PresentWrapperService presentWrapperService) {
        this.presentWrapperService = presentWrapperService;
    }

    @PostConstruct
    private synchronized void init() {
        User user1 = new User(1L, "User", "One", LocalDate.of(1999, 9, 9),
                "admin", "admin", Role.USER, "calvian.png", getResourceAsByteArray("avatar/calvian.png"));
        userService.create(user1);
        User user2 = new User(2L, "User", "Two", LocalDate.of(1989, 3, 27),
                "admin", "admin", Role.USER, "uhlbrecht.png", getResourceAsByteArray("avatar/uhlbrecht.png"));
        userService.create(user2);
        User user3 = new User(3L, "User", "Three", LocalDate.of(1998, 12, 12),
                "admin", "admin", Role.ADMIN, "eloise.png", getResourceAsByteArray("avatar/eloise.png"));
        userService.create(user3);
        User user4 = new User(4L, "User", "Four", LocalDate.of(2000, 2, 24),
                "admin", "admin", Role.USER, "zereni.png", getResourceAsByteArray("avatar/zereni.png"));
        userService.create(user4);

        SantaClaus santaClaus1 = new SantaClaus(1L, "SantaClaus1", 4.25, 4);
        santaClausService.create(santaClaus1);
        SantaClaus santaClaus2 = new SantaClaus(2L, "SantaClaus2", 2.55, 3);
        santaClausService.create(santaClaus2);
        SantaClaus santaClaus3 = new SantaClaus(3L, "SantaClaus3", 3.75, 1);
        santaClausService.create(santaClaus3);
        SantaClaus santaClaus4 = new SantaClaus(4L, "SantaClaus4", 1.05, 2);
        santaClausService.create(santaClaus4);

        presentWrapperService.create(new PresentWrapper(1L, Present.PC, santaClaus1, user1, "for son", 9999.99));
        presentWrapperService.create(new PresentWrapper(2L, Present.XBOX, santaClaus1, user1, "for dad", 2499.99));
        presentWrapperService.create(new PresentWrapper(3L, Present.STICK, santaClaus2, user2, "for daughter", 0));
        presentWrapperService.create(new PresentWrapper(4L, Present.SHOES, santaClaus2, user2, "for mom", 999.49));
        presentWrapperService.create(new PresentWrapper(5L, Present.BOOK, santaClaus3, user3, "for daughter", 49.99));
        presentWrapperService.create(new PresentWrapper(6L, Present.PLAYSTATION, santaClaus3, user3, "for son", 2499.99));
        presentWrapperService.create(new PresentWrapper(7L, Present.BALL, santaClaus4, user4, "for son", 99.49));
        presentWrapperService.create(new PresentWrapper(8L, Present.CAR, santaClaus4, user4, "for dad", 99999.99));
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        }
    }
}
