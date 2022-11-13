package jakarta.ee.present;

import jakarta.ee.repository.Repository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class PresentWrapperRepository implements Repository<PresentWrapper, Long> {

    private EntityManager em;

    @PersistenceContext(unitName = "PersistenceUnit")
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<PresentWrapper> find(Long id) {
        return Optional.ofNullable(em.find(PresentWrapper.class, id));
    }

    @Override
    public List<PresentWrapper> findAll() {
        return em.createQuery("select pw from PresentWrapper pw", PresentWrapper.class).getResultList();
    }

    public List<PresentWrapper> findAllBySantaClausId(Long santaClausId) {
        return em.createQuery("select pw from PresentWrapper pw where pw.santaClaus.id = :santaClausId", PresentWrapper.class)
                .setParameter("santaClausId", santaClausId)
                .getResultList();
    }

    @Override
    public void create(PresentWrapper entity) {
        em.persist(entity);
    }

    public void delete(PresentWrapper entity) {
        em.remove(em.find(PresentWrapper.class, entity.getId()));
    }

    public void update(PresentWrapper entity) {
        em.merge(entity);
    }
}
