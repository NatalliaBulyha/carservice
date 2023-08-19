package by.senla.training.bulyha.carservice.dao.impl;

import by.senla.training.bulyha.carservice.dao.AbstractDao;
import by.senla.training.bulyha.carservice.dao.PlaceDao;
import by.senla.training.bulyha.carservice.model.Place;
import by.senla.training.bulyha.carservice.model.Place_;
import by.senla.training.bulyha.carservice.model.enums.PlacesStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class PlaceDaoImpl extends AbstractDao<Place> implements PlaceDao {
    private static final Logger log = LoggerFactory.getLogger(PlaceDaoImpl.class);
    public PlaceDaoImpl() {
        this.type = Place.class;
    }

    public List<Place> getAllWorkPlace(PlacesStatus status) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Place> criteriaQuery = builder.createQuery(Place.class);
        Root<Place> root = criteriaQuery.from(Place.class);
        criteriaQuery.select(root).where(builder.equal(root.get(Place_.STATUS), status));
        return (List<Place>) entityManager.createQuery(criteriaQuery).getResultList();
    }

    public Place getByName(String name) throws NoResultException {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Place> criteriaQuery = builder.createQuery(Place.class);
        Root<Place> root = criteriaQuery.from(Place.class);
        criteriaQuery.select(root).where(builder.equal(root.get(Place_.NAME), name));
        return (Place) entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}
