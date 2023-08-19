package by.senla.training.bulyha.carservice.validator;

import by.senla.training.bulyha.carservice.exception.ValidatorsException;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface OrderValidator {

    void isRealPrice(BigDecimal price) throws ValidatorsException;

    void isRealDate(LocalDate date) throws ValidatorsException;
}
