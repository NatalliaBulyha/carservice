package by.senla.training.bulyha.carservice.service.impl;

import by.senla.training.bulyha.carservice.csvconverter.PlaceCsvConverter;
import by.senla.training.bulyha.carservice.dao.OrderDao;
import by.senla.training.bulyha.carservice.dao.PlaceDao;
import by.senla.training.bulyha.carservice.dto.PrintPlacesDto;
import by.senla.training.bulyha.carservice.exception.CarServiceBackEndException;
import by.senla.training.bulyha.carservice.mapper.PlaceMapper;
import by.senla.training.bulyha.carservice.model.Master;
import by.senla.training.bulyha.carservice.model.Order;
import by.senla.training.bulyha.carservice.model.Place;
import by.senla.training.bulyha.carservice.model.enums.MastersQualification;
import by.senla.training.bulyha.carservice.model.enums.MastersStatus;
import by.senla.training.bulyha.carservice.model.enums.OrdersStatus;
import by.senla.training.bulyha.carservice.model.enums.PlacesStatus;
import by.senla.training.bulyha.carservice.service.PlaceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlaceServiceImplTest {

    private PlaceService placeService;

    @Mock
    private PlaceDao placeDao;
    @Mock
    private OrderDao orderDao;
    @Mock
    private PlaceCsvConverter placeConverter;
    @Mock
    private PlaceMapper placeMapper;


    @Before
    public void setUp() {
        placeService = new PlaceServiceImpl(placeDao, orderDao, placeConverter, placeMapper);
    }

    @Test
    public void placeServiceImpl_getById() {
        PrintPlacesDto mockPlace = new PrintPlacesDto(1, "5y", "used");
        Place place = new Place(1, "5y", PlacesStatus.USED);

        when(placeDao.getById(mockPlace.getId())).thenReturn(place);
        PrintPlacesDto returnPlace = placeService.getById(place.getId());
        assertEquals(placeMapper.mappingPlaceByPrintPlaceDto(place), returnPlace);
    }

    @Test
    public void placeServiceImpl_getById_NullExc() {
        int placeId = 200;
        when(placeDao.getById(placeId)).thenReturn(null);
        assertThrows(RuntimeException.class, () -> {
            placeService.getById(placeId);
        });
    }

    @Test
    public void placeServiceImpl_getAll() {
        List<Place> placesList = new ArrayList();
        placesList.add(new Place(1, "5y", PlacesStatus.USED));
        placesList.add(new Place(2, "e7", PlacesStatus.USED));
        placesList.add(new Place(3, "a6", PlacesStatus.USED));

        when(placeDao.getAll()).thenReturn(placesList);
        List<PrintPlacesDto> returnPlacesList = placeService.getAll();

        Assertions.assertEquals(returnPlacesList, placeMapper.mappingPlacesListByPrintPlaceDtoList(placesList));
    }

    @Test
    public void placeServiceImpl_addPlace() {
        Place place = new Place("5y", PlacesStatus.USED);

        doNothing().when(placeDao).add(place);

        placeService.addPlace("5y");
    }

    @Test
    public void placeServiceImpl_showFreePlaces() {
        List<Order> ordersList = new ArrayList();
        Order order1 = new Order( LocalDate.now(), LocalDate.now(),
                LocalDate.now(), BigDecimal.valueOf(234.10), OrdersStatus.PROCESSED,
                new Master(2, "Goga", MastersQualification.HIGH, 34, MastersStatus.WORK),
                new Place(3, "a6", PlacesStatus.USED));
        Order order2 = new Order( LocalDate.now(), LocalDate.now(),
                LocalDate.now(), BigDecimal.valueOf(224.10), OrdersStatus.CLOSED,
                new Master(1, "Wowa", MastersQualification.HIGH, 44, MastersStatus.WORK),
                new Place(2, "e7", PlacesStatus.USED));
        Order order3 = new Order( LocalDate.now(), LocalDate.now(),
                LocalDate.now(), BigDecimal.valueOf(554.10), OrdersStatus.PROCESSED,
                new Master(3, "Roma", MastersQualification.HIGH, 33, MastersStatus.WORK),
                new Place(1, "5y", PlacesStatus.USED));

        Place place1 = new Place(1, "5y", PlacesStatus.USED);
        Place place2 = new Place(2, "e7", PlacesStatus.USED);
        Place place3 = new Place(3, "a6", PlacesStatus.USED);
        List<Place> placesList = new ArrayList();
        placesList.add(place1);
        placesList.add(place2);
        placesList.add(place3);

        when(orderDao.showTodayProcessedOrdersList()).thenReturn(ordersList);
        when(placeDao.getById(order1.getPlace().getId())).thenReturn(place3);
        when(placeDao.getById(order3.getPlace().getId())).thenReturn(place1);
        when(placeDao.getAllWorkPlace(PlacesStatus.USED)).thenReturn(placesList);

        List<PrintPlacesDto> returnFreePlacesList = placeService.showFreePlaces();

        for(int i = 0; i < returnFreePlacesList.size(); i++) {
            Assertions.assertEquals(returnFreePlacesList.get(i), placeMapper.mappingPlacesListByPrintPlaceDtoList(placesList).get(i));
        }
    }

    @Test
    public void placeServiceImpl_showNearestFreeDate() {
        List<Order> ordersList = new ArrayList();
        Order order1 = new Order( LocalDate.now(), LocalDate.now(),
                LocalDate.now(), BigDecimal.valueOf(234.10), OrdersStatus.PROCESSED,
                new Master(2, "Goga", MastersQualification.HIGH, 34, MastersStatus.WORK),
                new Place(3, "a6", PlacesStatus.USED));
        Order order2 = new Order( LocalDate.now(), LocalDate.now(),
                LocalDate.now(), BigDecimal.valueOf(224.10), OrdersStatus.CLOSED,
                new Master(1, "Wowa", MastersQualification.HIGH, 44, MastersStatus.WORK),
                new Place(2, "e7", PlacesStatus.USED));
        Order order3 = new Order( LocalDate.now(), LocalDate.now(),
                LocalDate.now(), BigDecimal.valueOf(554.10), OrdersStatus.PROCESSED,
                new Master(3, "Roma", MastersQualification.HIGH, 33, MastersStatus.WORK),
                new Place(1, "5y", PlacesStatus.USED));

        Place place1 = new Place(1, "5y", PlacesStatus.USED);
        Place place2 = new Place(2, "e7", PlacesStatus.USED);
        Place place3 = new Place(3, "a6", PlacesStatus.USED);
        List<Place> placesList = new ArrayList();
        placesList.add(place1);
        placesList.add(place2);
        placesList.add(place3);

        when(orderDao.showTodayProcessedOrdersList()).thenReturn(ordersList);
        when(placeDao.getById(order1.getPlace().getId())).thenReturn(place3);
        when(placeDao.getById(order3.getPlace().getId())).thenReturn(place1);
        when(placeDao.getAllWorkPlace(PlacesStatus.USED)).thenReturn(placesList);

        LocalDate returnFreePlace = placeService.showNearestFreeDate();

            Assertions.assertEquals(returnFreePlace, LocalDate.now());
    }

    @Test
    public void placeServiceImpl_countFreePlacesOnDate() {
        List<Order> ordersList = new ArrayList();
        Order order1 = new Order( LocalDate.now(), LocalDate.now(),
                LocalDate.now(), BigDecimal.valueOf(234.10), OrdersStatus.PROCESSED,
                new Master(2, "Goga", MastersQualification.HIGH, 34, MastersStatus.WORK),
                new Place(3, "a6", PlacesStatus.USED));
        Order order2 = new Order( LocalDate.now(), LocalDate.now(),
                LocalDate.now(), BigDecimal.valueOf(224.10), OrdersStatus.CLOSED,
                new Master(1, "Wowa", MastersQualification.HIGH, 44, MastersStatus.WORK),
                new Place(2, "e7", PlacesStatus.USED));
        Order order3 = new Order( LocalDate.now(), LocalDate.now(),
                LocalDate.now(), BigDecimal.valueOf(554.10), OrdersStatus.PROCESSED,
                new Master(3, "Roma", MastersQualification.HIGH, 33, MastersStatus.WORK),
                new Place(1, "5y", PlacesStatus.USED));

        orderDao.add(order1);
        orderDao.add(order2);
        orderDao.add(order3);

        Place place1 = new Place(1, "5y", PlacesStatus.USED);
        Place place2 = new Place(2, "e7", PlacesStatus.USED);
        Place place3 = new Place(3, "a6", PlacesStatus.USED);
        List<Place> placesList = new ArrayList();
        placesList.add(place1);
        placesList.add(place2);
        placesList.add(place3);

        when(orderDao.showTodayProcessedOrdersList()).thenReturn(ordersList);
        when(placeDao.getById(order1.getPlace().getId())).thenReturn(place3);
        when(placeDao.getById(order3.getPlace().getId())).thenReturn(place1);
        when(placeDao.getAllWorkPlace(PlacesStatus.USED)).thenReturn(placesList);

        int returnFreePlace = placeService.countFreePlacesOnDate(LocalDate.now());

        Assertions.assertSame(returnFreePlace, 3);
    }

    @Test
    public void placeServiceImpl_countFreePlacesOnDate_Exc() {
        LocalDate date = LocalDate.of(2020, 3, 20);

        Exception exception = assertThrows(CarServiceBackEndException.class, () -> {
            placeService.countFreePlacesOnDate(date);
        });
    }
}
