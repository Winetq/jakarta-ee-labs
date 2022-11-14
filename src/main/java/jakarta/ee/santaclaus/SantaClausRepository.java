package jakarta.ee.santaclaus;

import jakarta.ee.repository.Repository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class SantaClausRepository implements Repository<SantaClaus, Long> {

    private EntityManager em;

    @PersistenceContext(unitName = "PersistenceUnit")
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<SantaClaus> find(Long id) {
        return Optional.ofNullable(em.find(SantaClaus.class, id));
    }

    public SantaClaus find(String name) throws NoResultException {
        return em.createQuery("select sc from SantaClaus sc where sc.name = :name", SantaClaus.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<SantaClaus> findAll() {
        return em.createQuery("select sc from SantaClaus sc", SantaClaus.class).getResultList();
    }

    @Override
    public void create(SantaClaus entity) {
        em.persist(entity);
    }

    public void delete(SantaClaus entity) {
        em.remove(em.find(SantaClaus.class, entity.getId()));
    }

    @Override
    public void update(SantaClaus entity) {
        em.merge(entity);
    }
}
