package jakarta.ee.santaclaus;

import jakarta.ee.datastore.DataStore;
import jakarta.ee.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class SantaClausRepository implements Repository<SantaClaus, Long> {
    private DataStore store;

    @Inject
    public SantaClausRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<SantaClaus> find(Long id) {
        return store.findSantaClaus(id);
    }

    public Optional<SantaClaus> find(String name) {
        return store.findSantaClaus(name);
    }

    @Override
    public List<SantaClaus> findAll() {
        return store.findSantaClauses();
    }

    @Override
    public void create(SantaClaus entity) {
        store.createSantaClaus(entity);
    }

    public void delete(SantaClaus entity) {
        store.deleteSantaClaus(entity);
    }
}
