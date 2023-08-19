package by.senla.training.bulyha.carservice.service;

import by.senla.training.bulyha.carservice.dto.OrderDto;
import by.senla.training.bulyha.carservice.dto.PrintMasterDto;
import by.senla.training.bulyha.carservice.dto.PrintOrderDto;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    void addOrder(OrderDto orderDto);

    void changeOrdersStatus(int id, String status);

    List<PrintOrderDto> getAll();

    PrintOrderDto getById(int id);

    void shiftOrdersLeadTime(int orderId);

    PrintMasterDto showMasterByOrder(int orderId);

    List<PrintOrderDto> showOrdersByMaster(int masterId);

    List<PrintOrderDto> showOrderListSortedBy(String parameter);

    List<PrintOrderDto> showProcessedOrdersList();

    List<PrintOrderDto> showFilteredOrdersListSortedByParameter(String status, String parameter);

    List<PrintOrderDto> showFilteredOrdersListForPeriod(String status, LocalDate startedDate, LocalDate finishDate);

    List<PrintOrderDto> showFilteredOrdersListSortedByParameterForPeriod(String status, String parameter, LocalDate startedDate, LocalDate finishDate);

    void importOrderList();

    void exportOrder();
}
