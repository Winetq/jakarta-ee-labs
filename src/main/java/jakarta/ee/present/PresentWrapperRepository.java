package jakarta.ee.present;

import jakarta.ee.datastore.DataStore;
import jakarta.ee.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class PresentWrapperRepository implements Repository<PresentWrapper, Long> {
    private DataStore store;

    @Inject
    public PresentWrapperRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<PresentWrapper> find(Long id) {
        return store.findPresent(id);
    }

    @Override
    public List<PresentWrapper> findAll() {
        return store.findPresents();
    }

    @Override
    public void create(PresentWrapper entity) {
        store.createPresent(entity);
    }

    public void delete(PresentWrapper entity) {
        store.deletePresent(entity);
    }
}
