package by.senla.training.bulyha.carservice.mapper;

import by.senla.training.bulyha.carservice.dto.OrderDto;
import by.senla.training.bulyha.carservice.dto.PrintOrderDto;
import by.senla.training.bulyha.carservice.exception.CarServiceBackEndException;
import by.senla.training.bulyha.carservice.model.Master;
import by.senla.training.bulyha.carservice.model.Order;
import by.senla.training.bulyha.carservice.model.Place;
import by.senla.training.bulyha.carservice.model.enums.OrdersStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    public Order mappingOrderDtoByOrder(OrderDto orderDto) throws CarServiceBackEndException {
        LocalDate submissionDate = LocalDate.now();
        OrdersStatus status = OrdersStatus.PROCESSED;
        LocalDate completionDate = orderDto.getPlannedCompletionDate();
        Order order = new Order(submissionDate, orderDto.getPlannedCompletionDate(), completionDate,
                orderDto.getPrice(), status, new Master(orderDto.getMasterId()), new Place(orderDto.getPlaceId()));
        return order;
    }

    public List<OrderDto> mappingOrderListByOrderDTOList(List<Order> ordersList) {
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order order : ordersList) {
            OrderDto orderDto = new OrderDto(order.getPlannedCompletionDate(), order.getPrice(),
                    order.getMaster().getId(), order.getPlace().getId());
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }

    public PrintOrderDto mappingOrderByPrintOrderDto(Order order) {
        return new PrintOrderDto(order.getId(), order.getSubmissionDate(), order.getPlannedCompletionDate(),
                order.getCompletionDate(), order.getPrice(), order.getStatus().toString(), order.getMaster().getId(),
                order.getPlace().getId());
    }

    public List<PrintOrderDto> mappingOrdersListByPrintOrderDtoList(List<Order> orderList) {
        List<PrintOrderDto> orderDtoList = new ArrayList<>();
        for (Order order : orderList) {
            PrintOrderDto orderDto = mappingOrderByPrintOrderDto(order);
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }
}
