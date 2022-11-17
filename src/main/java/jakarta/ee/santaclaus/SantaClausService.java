package jakarta.ee.santaclaus;

import jakarta.ee.present.PresentWrapperRepository;
import lombok.NoArgsConstructor;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
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
        try {
            return Optional.of(santaClausRepository.find(name));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<SantaClaus> findAll() {
        return santaClausRepository.findAll();
    }

    public void create(SantaClaus santaClaus) {
        santaClausRepository.create(santaClaus);
    }

    public void delete(SantaClaus santaClaus) {
        presentWrapperRepository.findAllBySantaClausId(santaClaus.getId()).forEach(presentWrapper -> presentWrapperRepository.delete(presentWrapper));
        santaClausRepository.delete(santaClaus);
    }

    public void update(SantaClaus santaClaus) {
        santaClausRepository.update(santaClaus);
    }
}
