package jakarta.ee.santaclaus;

import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class SantaClausService {

    private SantaClausRepository repository;

    @Inject
    public SantaClausService(SantaClausRepository repository) {
        this.repository = repository;
    }

    public Optional<SantaClaus> find(Long id) {
        return repository.find(id);
    }

    public List<SantaClaus> findAll() {
        return repository.findAll();
    }

    public void create(SantaClaus santaClaus) {
        repository.create(santaClaus);
    }

    public void delete(SantaClaus santaClaus) {
        repository.delete(santaClaus);
    }
}
