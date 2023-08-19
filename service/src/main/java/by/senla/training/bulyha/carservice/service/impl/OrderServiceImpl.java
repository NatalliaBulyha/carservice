package by.senla.training.bulyha.carservice.service.impl;

import by.senla.training.bulyha.carservice.csvconverter.OrderCsvConverter;
import by.senla.training.bulyha.carservice.dao.MasterDao;
import by.senla.training.bulyha.carservice.dao.OrderDao;
import by.senla.training.bulyha.carservice.dto.OrderDto;
import by.senla.training.bulyha.carservice.dto.PrintMasterDto;
import by.senla.training.bulyha.carservice.dto.PrintOrderDto;
import by.senla.training.bulyha.carservice.exception.CarServiceBackEndException;
import by.senla.training.bulyha.carservice.exception.InternalException;
import by.senla.training.bulyha.carservice.mapper.MasterMapper;
import by.senla.training.bulyha.carservice.mapper.OrderMapper;
import by.senla.training.bulyha.carservice.model.Master;
import by.senla.training.bulyha.carservice.model.Order;
import by.senla.training.bulyha.carservice.model.enums.OrdersStatus;
import by.senla.training.bulyha.carservice.printwriter.FileReader;
import by.senla.training.bulyha.carservice.service.OrderService;
import by.senla.training.bulyha.carservice.validator.OrderValidator;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private MasterDao masterDao;
    private OrderMapper orderMapper;
    private OrderValidator orderValidator;
    private OrderCsvConverter orderConverter;
    private MasterMapper masterMapper;
    @Value("${OrderServiceImpl.possibilityDeleteOrder:true}")
    private boolean possibilityDeleteOrder;
    @Value("${OrderServiceImpl.possibilityShiftOrdersLeadTime:true}")
    private boolean possibilityShiftOrdersLeadTime;
    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, MasterDao masterDao, OrderMapper orderMapper, OrderValidator orderValidator, OrderCsvConverter orderConverter, MasterMapper masterMapper) {
        this.orderDao = orderDao;
        this.masterDao = masterDao;
        this.orderMapper = orderMapper;
        this.orderValidator = orderValidator;
        this.orderConverter = orderConverter;
        this.masterMapper = masterMapper;
    }

    @Transactional
    public void addOrder(OrderDto orderDto) {
        try {
            orderValidator.isRealDate(orderDto.getPlannedCompletionDate());
            orderValidator.isRealPrice(orderDto.getPrice());
            List<Order> orderList = orderDao.getAll();
            List<OrderDto> orderDtoList = orderMapper.mappingOrderListByOrderDTOList(orderList);
            if (orderDtoList.contains(orderDto)) {
                throw new CarServiceBackEndException("Order is already in the list of orders: " + orderDto.toString());
            }
            Order order = orderMapper.mappingOrderDtoByOrder(orderDto);
            orderDao.add(order);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public void changeOrdersStatus(int id, String ordersStatus) {
        String status = ordersStatus.toLowerCase();
        switch (status) {
            case "deleted":
                deleteOrder(id);
                break;
            case "closed":
                closeOrder(id);
                break;
            case "cancelled":
                cancelOrder(id);
                break;
            default:
                throw new CarServiceBackEndException("Error! Incorrectly entered status.");
        }
    }

    private void deleteOrder(int id) {
        try {
            if (!possibilityDeleteOrder) {
                throw new CarServiceBackEndException("It is forbidden to delete Order!");
            }
            Order order = getOrderById(id);
            if (order.getStatus().equals(OrdersStatus.DELETED)) {
                throw new CarServiceBackEndException("Order was deleted earlier! id=" + id);
            }
            order.setStatus(OrdersStatus.DELETED);
            orderDao.update(order);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public PrintOrderDto getById(int id) {
        Order order = orderDao.getById(id);
        if (checkThereIsOrder(order)) {
            throw new CarServiceBackEndException("There is no such order! id=" + id);
        }
        return orderMapper.mappingOrderByPrintOrderDto(order);
    }

    private Order getOrderById(int id) {
        Order order = orderDao.getById(id);
        if (checkThereIsOrder(order)) {
            throw new CarServiceBackEndException("There is no such order! id=" + id);
        }
        return order;
    }


    private void closeOrder(int id) {
        try {
            Order order = getOrderById(id);
            if (order.getStatus().equals(OrdersStatus.CLOSED)) {
                throw new CarServiceBackEndException("Order was closed earlier! id=" + id);
            }
            order.setStatus(OrdersStatus.CLOSED);
            orderDao.update(order);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    private void cancelOrder(int id) {
        try {
            Order order = getOrderById(id);
            if (order.getStatus().equals(OrdersStatus.CANCELLED)) {
                throw new CarServiceBackEndException("Order was closed earlier! id=" + id);
            }
            order.setStatus(OrdersStatus.CANCELLED);
            orderDao.update(order);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public List<PrintOrderDto> getAll() {
        try {
            List<Order> orderList = orderDao.getAll();
            return orderMapper.mappingOrdersListByPrintOrderDtoList(orderList);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public PrintMasterDto showMasterByOrder(int orderId) {
        try {
            Order order = getOrderById(orderId);
            Master master = masterDao.getById(order.getMaster().getId());
            return masterMapper.mappingMasterByPrintMasterDto(master);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public List<PrintOrderDto> showOrdersByMaster(int masterId) {
        try {
            List<Order> orderListByMaster = orderDao.showOrdersByMaster(masterId);
            return orderMapper.mappingOrdersListByPrintOrderDtoList(orderListByMaster);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public void shiftOrdersLeadTime(int orderId) {
        if (!possibilityShiftOrdersLeadTime) {
            throw new CarServiceBackEndException("It is forbidden to shift orders lead time!");
        }
        try {
            Order order = getOrderById(orderId);
            List<Order> ordersList = orderDao.showOrdersIdProcessed(order.getMaster().getId(), order.getPlace().getId());

            for (Order ord : ordersList) {
                ord.setCompletionDate(ord.getCompletionDate().plusDays(1));
                orderDao.update(ord);
            }
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public List<PrintOrderDto> showOrderListSortedBy(String parameter) {
        try {
            List<Order> ordersList = orderDao.showOrdersListSortedByString(parameter);
            return orderMapper.mappingOrdersListByPrintOrderDtoList(ordersList);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public List<PrintOrderDto> showProcessedOrdersList() {
        try {
            List<Order> ordersList = orderDao.showProcessedOrdersList();
            return orderMapper.mappingOrdersListByPrintOrderDtoList(ordersList);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public List<PrintOrderDto> showFilteredOrdersListSortedByParameter(String status, String parameter) {
        try {
            List<Order> ordersList = orderDao.showStatusOrdersListSortedByParameter(OrdersStatus.valueOf(status.toUpperCase()), parameter);
            return orderMapper.mappingOrdersListByPrintOrderDtoList(ordersList);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public List<PrintOrderDto> showFilteredOrdersListForPeriod(String status, LocalDate startedDate, LocalDate finishDate) {
        checkRightPeriod(startedDate, finishDate);
        try {
            List<Order> ordersList = orderDao.showStatusOrdersListForPeriod(OrdersStatus.valueOf(status.toUpperCase()), startedDate, finishDate);
            return orderMapper.mappingOrdersListByPrintOrderDtoList(ordersList);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public List<PrintOrderDto> showFilteredOrdersListSortedByParameterForPeriod(String status, String parameter, LocalDate startedDate, LocalDate finishDate) {
        checkRightPeriod(startedDate, finishDate);
        try {
            List<Order> ordersList = orderDao.showStatusOrdersListForPeriodSortedBy(OrdersStatus.valueOf(status.toUpperCase()), parameter, startedDate, finishDate);
            return orderMapper.mappingOrdersListByPrintOrderDtoList(ordersList);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }


    private boolean checkThereIsOrder(Order order) {
        if (order.getId() == 0) {
            return true;
        }
        return false;
    }

    private void checkRightPeriod(LocalDate startedDate, LocalDate finishDate) {
        if (startedDate.isAfter(finishDate)) {
            throw new CarServiceBackEndException("Start date was before finish date! You input: " + startedDate + " and " + finishDate);
        }
    }

    @Transactional
    public void importOrderList() {
        try {
            File file = new File("controller/src/main/resources/csvfiles/orders.csv");
            List<Order> ordersList = orderConverter.convertStringToOrdersList(FileReader.dataImport(file));
            for (Order order : ordersList) {
                orderDao.add(order);
            }
        } catch (HibernateException | FileNotFoundException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public void exportOrder() {
        try {
            List<Order> orderList = orderDao.getAll();
            File file = new File("controller/src/main/resources/csvfiles/exportOrder.csv");
            FileReader.dataExport(orderConverter.convertOrdersListToString(orderList), file);
        } catch (HibernateException | FileNotFoundException e) {
            throw new InternalException(e.getMessage());
        }
    }
}
