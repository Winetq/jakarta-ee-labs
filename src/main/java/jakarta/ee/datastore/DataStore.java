package jakarta.ee.datastore;

import jakarta.ee.present.PresentWrapper;
import jakarta.ee.santaclaus.SantaClaus;
import jakarta.ee.user.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class DataStore {
    private final Set<User> users = new HashSet<>();
    private final Set<SantaClaus> santaClauses = new HashSet<>();
    private final Set<PresentWrapper> presents = new HashSet<>();

    public synchronized Optional<User> findUser(Long id) {
        return users
                .stream()
                .filter(user -> user.getId().longValue() == id.longValue())
                .findFirst();
    }

    public synchronized List<User> findUsers() {
        return new ArrayList<>(users);
    }

    public synchronized void createUser(User entity) {
        users.add(entity);
    }

    public synchronized Optional<SantaClaus> findSantaClaus(Long id) {
        return santaClauses
                .stream()
                .filter(santaClaus -> santaClaus.getId().longValue() == id.longValue())
                .findFirst();
    }

    public synchronized List<SantaClaus> findSantaClauses() {
        return new ArrayList<>(santaClauses);
    }

    public synchronized void createSantaClaus(SantaClaus entity) {
        santaClauses.add(entity);
    }

    public synchronized void deleteSantaClaus(SantaClaus entity) {
        santaClauses.remove(entity);
    }

    public synchronized Optional<PresentWrapper> findPresent(Long id) {
        return presents
                .stream()
                .filter(present -> present.getId().longValue() == id.longValue())
                .findFirst();
    }

    public synchronized List<PresentWrapper> findPresents() {
        return new ArrayList<>(presents);
    }

    public synchronized void createPresent(PresentWrapper entity) {
        presents.add(entity);
    }

    public synchronized void deletePresent(PresentWrapper entity) {
        presents.remove(entity);
    }
}
