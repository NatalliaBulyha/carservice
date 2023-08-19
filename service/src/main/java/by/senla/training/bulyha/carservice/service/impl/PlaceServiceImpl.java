package by.senla.training.bulyha.carservice.service.impl;

import by.senla.training.bulyha.carservice.csvconverter.PlaceCsvConverter;
import by.senla.training.bulyha.carservice.dao.OrderDao;
import by.senla.training.bulyha.carservice.dao.PlaceDao;
import by.senla.training.bulyha.carservice.dto.PrintPlacesDto;
import by.senla.training.bulyha.carservice.exception.CarServiceBackEndException;
import by.senla.training.bulyha.carservice.exception.InternalException;
import by.senla.training.bulyha.carservice.mapper.PlaceMapper;
import by.senla.training.bulyha.carservice.model.Order;
import by.senla.training.bulyha.carservice.model.Place;
import by.senla.training.bulyha.carservice.model.enums.PlacesStatus;
import by.senla.training.bulyha.carservice.printwriter.FileReader;
import by.senla.training.bulyha.carservice.service.PlaceService;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PlaceServiceImpl implements PlaceService {

    private PlaceDao placeDao;
    private OrderDao orderDao;
    private PlaceCsvConverter placeConverter;
    private PlaceMapper placeMapper;
    @Value("${PlaceServiceImpl.possibilityAddPlace:true}")
    private boolean possibilityAddPlace;
    @Value("${PlaceServiceImpl.possibilityDeletePlace:true}")
    private boolean possibilityDeletePlace;
    private static final Logger LOG = LoggerFactory.getLogger(PlaceServiceImpl.class);

    @Autowired
    public PlaceServiceImpl(PlaceDao placeDao, OrderDao orderDao, PlaceCsvConverter placeConverter, PlaceMapper placeMapper) {
        this.placeDao = placeDao;
        this.orderDao = orderDao;
        this.placeConverter = placeConverter;
        this.placeMapper = placeMapper;
    }

    @Transactional
    public List<PrintPlacesDto> getAll() {
        try {
            List<Place> placeList = placeDao.getAll();
            return placeMapper.mappingPlacesListByPrintPlaceDtoList(placeList);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public void addPlace(String name) {
        try {
            Place place;
            try {
                place = placeDao.getByName(name);
            } catch (NoResultException e) {
                place = null;
            }
            if (checkThereIsPlaceByName(place)) {
                throw new CarServiceBackEndException("Place already in list!");
            }
            Place newPlace = new Place(name, PlacesStatus.USED);
            placeDao.add(newPlace);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public void deletePlace(int placeId) {
        if (!possibilityDeletePlace) {
            throw new CarServiceBackEndException("It is forbidden to deleted place!");
        }
        try {
            Place place = getByIdPlace(placeId);
            if (place.getStatus().equals(PlacesStatus.DELETE)) {
                throw new CarServiceBackEndException("Place already was deleted! id=" + placeId + "!");
            }
            place.setStatus(PlacesStatus.DELETE);
            placeDao.update(place);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public PrintPlacesDto getById(int placeId) {
        Place place = placeDao.getById(placeId);
        if (checkThereIsPlaceById(place)) {
            throw new CarServiceBackEndException("There is no such place! id=" + placeId + "!");
        }
        return placeMapper.mappingPlaceByPrintPlaceDto(place);
    }

    private Place getByIdPlace(int placeId) {
        Place place = placeDao.getById(placeId);
        if (checkThereIsPlaceById(place)) {
            throw new CarServiceBackEndException("There is no such place! id=" + placeId + "!");
        }
        return place;
    }

    @Transactional
    public List<PrintPlacesDto> showFreePlaces() {
        List<Place> freePlaceList;
        try {
            freePlaceList = showFreePlacesOnDate(LocalDate.now());
            return placeMapper.mappingPlacesListByPrintPlaceDtoList(freePlaceList);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    private List<Place> showFreePlacesOnDate(LocalDate date) {
        List<Order> ordersTodayProcessedList = orderDao.showTodayProcessedOrdersList();
        List<Place> placeNowWorkList = new ArrayList<>();
        for (Order order : ordersTodayProcessedList) {
            Place place = placeDao.getById(order.getPlace().getId());
            placeNowWorkList.add(place);
        }
        List<Place> placeAllUsedList = placeDao.getAllWorkPlace(PlacesStatus.USED);
        List<Place> freePlaceList = new ArrayList<>();
        if (placeNowWorkList.size() == 0) {
            return placeAllUsedList;
        }
        for (Place allWorkPlace : placeAllUsedList) {
            for (Place nowWorkPlace : placeNowWorkList)
                if (nowWorkPlace.getId() != allWorkPlace.getId()) {
                    freePlaceList.add(allWorkPlace);
                }
        }
        return freePlaceList;
    }

    @Transactional
    public int countFreePlacesOnDate(LocalDate date) {
        int countFreePlace = 0;
        if (date.isBefore(LocalDate.now()) || date.isAfter(LocalDate.now().plusDays(30))) {
            throw new CarServiceBackEndException("Incorrect date input!");
        }
        try {
            List<Place> freePlace = showFreePlacesOnDate(date);
            countFreePlace = freePlace.size();
            return countFreePlace;
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public LocalDate showNearestFreeDate() {
        List<Place> freePlacesOnDate;
        LocalDate nearestFreeDate = null;
        try {
            for (LocalDate date = LocalDate.now(); date.isBefore(date.plusDays(30)); date = date.plusDays(1)) {
                freePlacesOnDate = showFreePlacesOnDate(date);
                if (freePlacesOnDate.size() > 0) {
                    nearestFreeDate = date;
                    break;
                }
            }
            return nearestFreeDate;
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    private boolean checkThereIsPlaceById(Place place) {
        if (place.getName() == null) {
            return true;
        }
        return false;
    }

    private boolean checkThereIsPlaceByName(Place place) {
        if (Objects.isNull(place)) {
            return false;
        }
        return true;
    }

    @Transactional
    public void importPlaceList() {
        try {
            File file = new File("controller/src/main/resources/csvfiles/places.csv");
            List<Place> placesList = placeConverter.convertStringToPlacesList(FileReader.dataImport(file));
            for (Place places : placesList) {
                placeDao.add(places);
            }
        } catch (HibernateException | FileNotFoundException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public void exportPlace() {
        try {
            List<Place> placeList = placeDao.getAll();
            File file = new File("controller/src/main/resources/csvfiles/exportPlace.csv");
            FileReader.dataExport(placeConverter.convertPlacesListToString(placeList), file);
        } catch (HibernateException | FileNotFoundException e) {
            throw new InternalException(e.getMessage());
        }
    }
}


