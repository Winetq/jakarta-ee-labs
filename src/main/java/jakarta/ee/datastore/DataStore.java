package jakarta.ee.datastore;

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
}
