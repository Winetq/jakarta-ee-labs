package jakarta.ee.present;

import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class PresentWrapperService {

    private PresentWrapperRepository repository;

    @Inject
    public PresentWrapperService(PresentWrapperRepository repository) {
        this.repository = repository;
    }

    public Optional<PresentWrapper> find(Long id) {
        return repository.find(id);
    }

    public List<PresentWrapper> findAll() {
        return repository.findAll();
    }

    public void create(PresentWrapper present) {
        repository.create(present);
    }

    public void delete(PresentWrapper present) {
        repository.delete(present);
    }
}
