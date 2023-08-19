package by.senla.training.bulyha.carservice.validator.impl;

import by.senla.training.bulyha.carservice.exception.ValidatorsException;
import by.senla.training.bulyha.carservice.validator.OrderValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class OrderValidatorImpl implements OrderValidator {

    public void isRealPrice(BigDecimal price) throws ValidatorsException {
        if ((price.compareTo(BigDecimal.ZERO) < 0.00)) {
            throw new ValidatorsException("Price cannot be negative: " + price + ". Master not added!");
        }
    }

    public void isRealDate(LocalDate date) throws ValidatorsException {
        if (date.isBefore(LocalDate.now())) {
            throw new ValidatorsException("Planned completion date should not be earlier than submission date: "
                    + date + ". Master not added!");
        }
    }
}
