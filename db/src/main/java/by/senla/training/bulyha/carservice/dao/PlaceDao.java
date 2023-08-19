package by.senla.training.bulyha.carservice.dao;

import by.senla.training.bulyha.carservice.model.Place;
import by.senla.training.bulyha.carservice.model.enums.PlacesStatus;

import javax.persistence.NoResultException;
import java.util.List;

public interface PlaceDao extends GenericDao<Place> {

    List<Place> getAllWorkPlace(PlacesStatus status);

    Place getByName(String name) throws NoResultException;
}
