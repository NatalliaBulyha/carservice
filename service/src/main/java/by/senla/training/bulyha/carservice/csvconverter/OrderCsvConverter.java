package by.senla.training.bulyha.carservice.csvconverter;

import by.senla.training.bulyha.carservice.exception.ValidatorsException;
import by.senla.training.bulyha.carservice.model.Order;
import by.senla.training.bulyha.carservice.model.enums.OrdersStatus;
import by.senla.training.bulyha.carservice.validator.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderCsvConverter {
    private OrderValidator validator;

    @Autowired
    public OrderCsvConverter(OrderValidator validator) {
        this.validator = validator;
    }

    public List<Order> convertStringToOrdersList(List<String> stringOrdersList) throws ValidatorsException {
        List<Order> ordersList = new ArrayList<>();
        for (String string : stringOrdersList) {
            String[] orders = string.split(",");
            validator.isRealDate(LocalDate.parse(orders[1]));
            validator.isRealDate(LocalDate.parse(orders[2]));
            validator.isRealDate(LocalDate.parse(orders[3]));
            validator.isRealPrice(BigDecimal.valueOf(Double.parseDouble(orders[4])));
            Order order = new Order(LocalDate.parse(orders[1]), LocalDate.parse(orders[2]),
                    LocalDate.parse(orders[3]), BigDecimal.valueOf(Double.parseDouble(orders[4])),
                    OrdersStatus.valueOf(orders[5].toUpperCase()));
            ordersList.add(order);
        }
        return ordersList;
    }

    public List<String> convertOrdersListToString(List<Order> ordersList) {
        List<String> stringOrdersList = new ArrayList<>();
        for (Order order : ordersList) {
            stringOrdersList.add(String.valueOf(order.getId()) + ";" + String.valueOf(order.getSubmissionDate()) + ";" + String.valueOf(order.getPlannedCompletionDate()) +
                    ";" + String.valueOf(order.getCompletionDate()) + ";" + String.valueOf(order.getPrice()) + ";" + String.valueOf(order.getStatus()) +
                    ";" + String.valueOf(order.getMaster().getId()) + ";" + String.valueOf(order.getPlace().getId()));
        }
        return stringOrdersList;
    }
}
