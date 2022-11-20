package jakarta.ee.present;

import jakarta.ee.santaclaus.SantaClaus;
import jakarta.ee.santaclaus.SantaClausRepository;
import jakarta.ee.user.User;
import jakarta.ee.user.UserRepository;
import lombok.NoArgsConstructor;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
@NoArgsConstructor
public class PresentWrapperService {

    private PresentWrapperRepository presentWrapperRepository;

    private SantaClausRepository santaClausRepository;

    private UserRepository userRepository;

    @Inject
    public PresentWrapperService(PresentWrapperRepository presentWrapperRepository,
                                 SantaClausRepository santaClausRepository,
                                 UserRepository userRepository) {
        this.presentWrapperRepository = presentWrapperRepository;
        this.santaClausRepository = santaClausRepository;
        this.userRepository = userRepository;
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

    public Optional<List<PresentWrapper>> findAllByUserId(Long userId) {
        Optional<User> user = userRepository.find(userId);
        if (user.isPresent()) {
            return Optional.of(presentWrapperRepository.findAllByUserId(userId));
        }
        return Optional.empty();
    }

    public void create(PresentWrapper present) {
        presentWrapperRepository.create(present);
    }

    public void delete(PresentWrapper present) {
        presentWrapperRepository.delete(present);
    }

    public void update(PresentWrapper present) {
        presentWrapperRepository.update(present);
    }

    public long getMaxId(List<PresentWrapper> presentWrappers) {
        return presentWrappers.stream().mapToLong(PresentWrapper::getId).max().orElse(0);
    }
}
