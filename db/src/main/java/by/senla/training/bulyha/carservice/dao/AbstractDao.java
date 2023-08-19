package by.senla.training.bulyha.carservice.dao;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractDao<T> implements GenericDao<T> {
    protected Class<T> type;

    @PersistenceContext
    protected EntityManager entityManager;

    public List<T> getAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root);
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public void update(T object) {
        entityManager.merge(object);
    }

    public void add(T entity) {
        entityManager.persist(entity);
    }

    public T getById(Integer pk) {
        T object;
        object = entityManager.find(type, pk);
        Hibernate.initialize(object);
        return object;
    }

    public T findByName(String name, String column) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root).where(builder.equal(root.get(column), name));
        return entityManager.createQuery(criteriaQuery).getResultList().get(0);
    }

    public void delete(Integer id) {
        entityManager.detach(id);
    }
}
