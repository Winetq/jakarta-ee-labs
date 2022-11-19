package jakarta.ee.santaclaus;

import jakarta.ee.present.PresentWrapperRepository;
import jakarta.ee.user.UserRoleType;
import lombok.NoArgsConstructor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
@NoArgsConstructor
@RolesAllowed(UserRoleType.USER)
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

    @RolesAllowed(UserRoleType.ADMIN)
    public void create(SantaClaus santaClaus) {
        santaClausRepository.create(santaClaus);
    }

    @RolesAllowed(UserRoleType.ADMIN)
    public void delete(SantaClaus santaClaus) {
        presentWrapperRepository.findAllBySantaClausId(santaClaus.getId()).forEach(presentWrapper -> presentWrapperRepository.delete(presentWrapper));
        santaClausRepository.delete(santaClaus);
    }

    public void update(SantaClaus santaClaus) {
        santaClausRepository.update(santaClaus);
    }
}
