package by.senla.training.bulyha.carservice.dao;

import by.senla.training.bulyha.carservice.model.Order;
import by.senla.training.bulyha.carservice.model.enums.OrdersStatus;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao extends GenericDao<Order> {

    List<Order> showOrdersByMaster(int masterId);

    List<Order> showOrdersListSortedByString(String nameParameter);

    List<Order> showProcessedOrdersList();

    List<Order> showTodayProcessedOrdersList();

    List<Order> showOrdersIdProcessed(int masterId, int placeId);

    List<Order> showStatusOrdersListSortedByParameter(OrdersStatus ordersStatus, String columnBySorted);

    List<Order> showStatusOrdersListForPeriod(OrdersStatus ordersStatus, LocalDate startedDate, LocalDate finishDate);

    List<Order> showStatusOrdersListForPeriodSortedBy(OrdersStatus ordersStatus, String parameterBySorted, LocalDate startedDate, LocalDate finishDate);
}
