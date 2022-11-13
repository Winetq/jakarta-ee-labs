package jakarta.ee.present;

import jakarta.ee.santaclaus.SantaClaus;
import jakarta.ee.santaclaus.SantaClausRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class PresentWrapperService {

    private PresentWrapperRepository presentWrapperRepository;

    private SantaClausRepository santaClausRepository;

    @Inject
    public PresentWrapperService(PresentWrapperRepository presentWrapperRepository,
                                 SantaClausRepository santaClausRepository) {
        this.presentWrapperRepository = presentWrapperRepository;
        this.santaClausRepository = santaClausRepository;
    }

    public Optional<PresentWrapper> find(Long id) {
        return presentWrapperRepository.find(id);
    }

    public List<PresentWrapper> findAll() {
        return presentWrapperRepository.findAll();
    }

    public Optional<List<PresentWrapper>> findAllBySantaClausId(Long santaClausId) {
        Optional<SantaClaus> santaClaus = santaClausRepository.find(santaClausId);
        if (santaClaus.isPresent()) {
            return Optional.of(presentWrapperRepository.findAllBySantaClausId(santaClausId));
        }
        return Optional.empty();
    }

    @Transactional
    public void create(PresentWrapper present) {
        presentWrapperRepository.create(present);
    }

    @Transactional
    public void delete(PresentWrapper present) {
        presentWrapperRepository.delete(present);
    }

    @Transactional
    public void update(PresentWrapper present) {
        presentWrapperRepository.update(present);
    }

    public long getMaxId(List<PresentWrapper> presentWrappers) {
        return presentWrappers.stream().mapToLong(PresentWrapper::getId).max().orElse(0);
    }
}
