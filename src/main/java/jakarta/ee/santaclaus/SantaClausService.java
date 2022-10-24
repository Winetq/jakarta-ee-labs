package jakarta.ee.santaclaus;

import jakarta.ee.present.PresentWrapperRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class SantaClausService {

    private SantaClausRepository santaClausRepository;

    private PresentWrapperRepository presentWrapperRepository;

    @Inject
    public SantaClausService(SantaClausRepository santaClausRepository, PresentWrapperRepository presentWrapperRepository) {
        this.santaClausRepository = santaClausRepository;
        this.presentWrapperRepository = presentWrapperRepository;
    }

    public Optional<SantaClaus> find(Long id) {
        return santaClausRepository.find(id);
    }

    public Optional<SantaClaus> find(String name) {
        return santaClausRepository.find(name);
    }

    public List<SantaClaus> findAll() {
        return santaClausRepository.findAll();
    }

    public void create(SantaClaus santaClaus) {
        santaClausRepository.create(santaClaus);
    }

    public void delete(SantaClaus santaClaus) {
        santaClaus.getPresents().forEach(present -> presentWrapperRepository.delete(present));
        santaClausRepository.delete(santaClaus);
    }
}
