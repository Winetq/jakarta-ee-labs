package jakarta.ee.configuration;

import jakarta.ee.present.Present;
import jakarta.ee.present.PresentWrapper;
import jakarta.ee.santaclaus.SantaClaus;
import jakarta.ee.user.User;
import jakarta.ee.user.UserRoleType;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

/**
 * EJB singleton can be forced to start automatically when application starts.
 */
@Singleton
@Startup
public class DataInitializer {

    private EntityManager em;

    @PersistenceContext(unitName = "PersistenceUnit")
    public void setEm(EntityManager em) {
        this.em = em;
    }

    private Pbkdf2PasswordHash pbkdf;

    @Inject
    public void setPbkdf(Pbkdf2PasswordHash pbkdf) {
        this.pbkdf = pbkdf;
    }

    @PostConstruct
    private synchronized void init() {
        User user1 = new User(1L, "User", "One", LocalDate.of(1999, 9, 9),
                "user1", pbkdf.generate("user1".toCharArray()), List.of(UserRoleType.USER), "calvian.png", getResourceAsByteArray("avatar/calvian.png"));
        em.persist(user1);
        User user2 = new User(2L, "User", "Two", LocalDate.of(1989, 3, 27),
                "user2", pbkdf.generate("user2".toCharArray()), List.of(UserRoleType.USER), "uhlbrecht.png", getResourceAsByteArray("avatar/uhlbrecht.png"));
        em.persist(user2);
        User user3 = new User(3L, "User", "Three", LocalDate.of(1998, 12, 12),
                "user3", pbkdf.generate("user3".toCharArray()), List.of(UserRoleType.USER, UserRoleType.ADMIN), "eloise.png", getResourceAsByteArray("avatar/eloise.png"));
        em.persist(user3);
        User user4 = new User(4L, "User", "Four", LocalDate.of(2000, 2, 24),
                "user4", pbkdf.generate("user4".toCharArray()), List.of(UserRoleType.USER), "zereni.png", getResourceAsByteArray("avatar/zereni.png"));
        em.persist(user4);

        SantaClaus santaClaus1 = new SantaClaus(1L, "SantaClaus1", 4.25, 4);
        em.persist(santaClaus1);
        SantaClaus santaClaus2 = new SantaClaus(2L, "SantaClaus2", 2.55, 3);
        em.persist(santaClaus2);
        SantaClaus santaClaus3 = new SantaClaus(3L, "SantaClaus3", 3.75, 1);
        em.persist(santaClaus3);
        SantaClaus santaClaus4 = new SantaClaus(4L, "SantaClaus4", 1.05, 2);
        em.persist(santaClaus4);

        em.persist(new PresentWrapper(1L, Present.PC, santaClaus1, user1, "for son", 9999.99));
        em.persist(new PresentWrapper(2L, Present.XBOX, santaClaus1, user1, "for dad", 2499.99));
        em.persist(new PresentWrapper(3L, Present.STICK, santaClaus2, user2, "for daughter", 0));
        em.persist(new PresentWrapper(4L, Present.SHOES, santaClaus2, user2, "for mom", 999.49));
        em.persist(new PresentWrapper(5L, Present.BOOK, santaClaus3, user3, "for daughter", 49.99));
        em.persist(new PresentWrapper(6L, Present.PLAYSTATION, santaClaus3, user3, "for son", 2499.99));
        em.persist(new PresentWrapper(7L, Present.BALL, santaClaus4, user4, "for son", 99.49));
        em.persist(new PresentWrapper(8L, Present.CAR, santaClaus4, user4, "for dad", 99999.99));
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        }
    }
}
