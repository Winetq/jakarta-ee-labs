package jakarta.ee.user;

import jakarta.ee.datastore.DataStore;
import jakarta.ee.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class UserRepository implements Repository<User, Long> {
    private DataStore store;

    @Inject
    public UserRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<User> find(Long id) {
        return store.findUser(id);
    }

    @Override
    public List<User> findAll() {
        return store.findUsers();
    }

    @Override
    public void create(User entity) {
        store.createUser(entity);
    }
}
