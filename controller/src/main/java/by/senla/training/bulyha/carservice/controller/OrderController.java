package by.senla.training.bulyha.carservice.controller;

import by.senla.training.bulyha.carservice.dto.OrderDto;
import by.senla.training.bulyha.carservice.dto.PrintOrderDto;
import by.senla.training.bulyha.carservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/newOrder")
    public void addOrder(@Valid @RequestBody OrderDto orderDto) {
        orderService.addOrder(orderDto);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public void updateStatusOrder(@RequestParam Integer orderId,
                                  @RequestParam(required = false) String status) {
        if (status == null) {
            orderService.shiftOrdersLeadTime(orderId);
        } else {
            orderService.changeOrdersStatus(orderId, status);
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_MASTER"})
    @GetMapping
    public List<PrintOrderDto> showOrdersList(@RequestParam(required = false) Integer masterId,
                                              @RequestParam(required = false) Integer orderId,
                                              @RequestParam(required = false) String sort,
                                              @RequestParam(required = false) String status,
                                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate finishDate) {
        List<PrintOrderDto> ordersList = new ArrayList<>();
        if (masterId != null) {
            ordersList = orderService.showOrdersByMaster(masterId);
        } else if (orderId != null) {
            ordersList.add(orderService.getById(orderId));
        } else if (status == null && sort != null && startDate == null && finishDate == null) {
            ordersList = orderService.showOrderListSortedBy(sort);
        } else if (status != null && sort != null && startDate == null && finishDate == null) {
            ordersList = orderService.showFilteredOrdersListSortedByParameter(status, sort);
        } else if (status != null && sort == null && startDate != null && finishDate != null) {
            ordersList = orderService.showFilteredOrdersListForPeriod(status, startDate, finishDate);
        } else if (status != null && sort != null && startDate != null && finishDate != null) {
            ordersList = orderService.showFilteredOrdersListSortedByParameterForPeriod(status, sort, startDate, finishDate);
        }  else {
            ordersList = orderService.getAll();
        }
        return ordersList;
    }

    @Secured({"ROLE_ADMIN", "ROLE_MASTER"})
    @GetMapping("/processedOrdersList")
    public List<PrintOrderDto> showProcessedOrdersList() {
            return orderService.showProcessedOrdersList();
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/import")
    public void importOrderList() {
            orderService.importOrderList();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/export")
    public void exportOrder() {
            orderService.exportOrder();
    }
}
