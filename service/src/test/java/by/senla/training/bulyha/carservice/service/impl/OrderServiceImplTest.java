package by.senla.training.bulyha.carservice.service.impl;

import by.senla.training.bulyha.carservice.csvconverter.OrderCsvConverter;
import by.senla.training.bulyha.carservice.dao.MasterDao;
import by.senla.training.bulyha.carservice.dao.OrderDao;
import by.senla.training.bulyha.carservice.dto.PrintMasterDto;
import by.senla.training.bulyha.carservice.dto.PrintOrderDto;
import by.senla.training.bulyha.carservice.mapper.MasterMapper;
import by.senla.training.bulyha.carservice.mapper.OrderMapper;
import by.senla.training.bulyha.carservice.model.Master;
import by.senla.training.bulyha.carservice.model.Order;
import by.senla.training.bulyha.carservice.model.Place;
import by.senla.training.bulyha.carservice.model.enums.MastersQualification;
import by.senla.training.bulyha.carservice.model.enums.MastersStatus;
import by.senla.training.bulyha.carservice.model.enums.OrdersStatus;
import by.senla.training.bulyha.carservice.model.enums.PlacesStatus;
import by.senla.training.bulyha.carservice.service.OrderService;
import by.senla.training.bulyha.carservice.validator.OrderValidator;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    private OrderService orderService;

    @Mock
    private OrderDao orderDao;
    @Mock
    private MasterDao masterDao;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private OrderValidator orderValidator;
    @Mock
    private OrderCsvConverter orderConverter;
    @Mock
    private MasterMapper masterMapper;


    @Before
    public void setUp() {
        orderService = new OrderServiceImpl(orderDao, masterDao, orderMapper, orderValidator, orderConverter, masterMapper);
    }

    @Test
    public void orderServiceImpl_getById() {
        PrintOrderDto mockOrder = new PrintOrderDto(1, LocalDate.now(), LocalDate.now(),
                LocalDate.now(), BigDecimal.valueOf(234.10), "PROCESSED", 2,3);
        Order order = new Order(1, LocalDate.now(), LocalDate.now(),
                LocalDate.now(), BigDecimal.valueOf(234.10), OrdersStatus.PROCESSED,
                new Master(2, "Goga", MastersQualification.HIGH, 34, MastersStatus.WORK),
                new Place(3, "e4", PlacesStatus.USED));

        when(orderDao.getById(mockOrder.getId())).thenReturn(order);
        PrintOrderDto returnOrder = orderService.getById(order.getId());
        assertEquals(orderMapper.mappingOrderByPrintOrderDto(order), returnOrder);
    }

    @Test
    public void orderServiceImpl_getById_NullExc() {
        int orderId = 2222;
        when(orderDao.getById(orderId)).thenReturn(null);
        assertThrows(RuntimeException.class, () -> {
            orderService.getById(orderId);
        });
    }

    @Test
    public void orderServiceImpl_getAll() {
        List<Order> ordersList = new ArrayList();
        ordersList.add(new Order( LocalDate.now(), LocalDate.now(),
                LocalDate.now(), BigDecimal.valueOf(234.10), OrdersStatus.PROCESSED,
                new Master(2, "Goga", MastersQualification.HIGH, 34, MastersStatus.WORK),
                new Place(3, "e4", PlacesStatus.USED)));
        ordersList.add(new Order( LocalDate.now(), LocalDate.now(),
                LocalDate.now(), BigDecimal.valueOf(224.10), OrdersStatus.PROCESSED,
                new Master(1, "Wowa", MastersQualification.HIGH, 44, MastersStatus.WORK),
                new Place(2, "ew", PlacesStatus.USED)));
        ordersList.add(new Order( LocalDate.now(), LocalDate.now(),
                LocalDate.now(), BigDecimal.valueOf(554.10), OrdersStatus.PROCESSED,
                new Master(3, "Roma", MastersQualification.HIGH, 33, MastersStatus.WORK),
                new Place(1, "ee", PlacesStatus.USED)));

        when(orderDao.getAll()).thenReturn(ordersList);
        List<PrintOrderDto> returnOrdersList = orderService.getAll();

        for(int i = 0; i < returnOrdersList.size(); i++) {
            Assertions.assertEquals(returnOrdersList.get(i), orderMapper.mappingOrdersListByPrintOrderDtoList(ordersList).get(i));
        }
    }

    @Test
    public void orderServiceImpl_showMasterByOrder() {
        PrintOrderDto mockOrder = new PrintOrderDto(1, LocalDate.now(), LocalDate.now(),
                LocalDate.now(), BigDecimal.valueOf(234.10), "PROCESSED", 2,3);
        Order order = new Order(1, LocalDate.now(), LocalDate.now(),
                LocalDate.now(), BigDecimal.valueOf(234.10), OrdersStatus.PROCESSED,
                new Master(2, "Goga", MastersQualification.HIGH, 34, MastersStatus.WORK),
                new Place(3, "e4", PlacesStatus.USED));
        Master master = new Master(2, "Goga", MastersQualification.HIGH, 34, MastersStatus.WORK);

        when(orderDao.getById(mockOrder.getId())).thenReturn(order);
        when(masterDao.getById(mockOrder.getMasterId())).thenReturn(master);
        PrintMasterDto returnMaster = orderService.showMasterByOrder(order.getId());
        assertEquals(masterMapper.mappingMasterByPrintMasterDto(master), returnMaster);
    }

    @Test
    public void orderServiceImpl_showFilteredOrdersListSortedByParameter() {
        List<Order> ordersList = new ArrayList();
        Order order1 = new Order( LocalDate.now(), LocalDate.now(),
                LocalDate.now(), BigDecimal.valueOf(234.10), OrdersStatus.PROCESSED,
                new Master(2, "Goga", MastersQualification.HIGH, 34, MastersStatus.WORK),
                new Place(3, "e4", PlacesStatus.USED));
        Order order2 = new Order( LocalDate.now(), LocalDate.now(),
                LocalDate.now(), BigDecimal.valueOf(224.10), OrdersStatus.CLOSED,
                new Master(1, "Wowa", MastersQualification.HIGH, 44, MastersStatus.WORK),
                new Place(2, "ew", PlacesStatus.USED));
        Order order3 = new Order( LocalDate.now(), LocalDate.now(),
                LocalDate.now(), BigDecimal.valueOf(554.10), OrdersStatus.PROCESSED,
                new Master(3, "Roma", MastersQualification.HIGH, 33, MastersStatus.WORK),
                new Place(1, "ee", PlacesStatus.USED));

        ordersList.add(order1);
        ordersList.add(order2);
        ordersList.add(order3);

        List<PrintOrderDto> processedListSortedByPrice = new ArrayList<>();
        processedListSortedByPrice.add(orderMapper.mappingOrderByPrintOrderDto(order3));
        processedListSortedByPrice.add(orderMapper.mappingOrderByPrintOrderDto(order1));

        when(orderDao.showStatusOrdersListSortedByParameter(OrdersStatus.PROCESSED, "price")).thenReturn(ordersList);
        List<PrintOrderDto> returnOrdersList = orderService.showFilteredOrdersListSortedByParameter("processed", "price");

        for(int i = 0; i < returnOrdersList.size(); i++) {
            Assertions.assertEquals(returnOrdersList.get(i), processedListSortedByPrice.get(i));
        }
    }
}
