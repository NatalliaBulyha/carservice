package by.senla.training.bulyha.carservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderDto {

    @FutureOrPresent
    private LocalDate plannedCompletionDate;

    @Positive(message = "price should not be negative")
    @DecimalMin("1.00")
    private BigDecimal price;

    @NotEmpty(message = "masterId cannot be null or empty")
    private Integer masterId;

    @NotEmpty(message = "placeId cannot be null or empty")
    private Integer placeId;

    public OrderDto(LocalDate plannedCompletionDate, BigDecimal price, int masterId, int placeId) {
        this.plannedCompletionDate = plannedCompletionDate;
        this.price = price;
        this.masterId = masterId;
        this.placeId = placeId;
    }

    public LocalDate getPlannedCompletionDate() {
        return plannedCompletionDate;
    }

    @JsonFormat
    public void setPlannedCompletionDate(LocalDate plannedCompletionDate) {
        this.plannedCompletionDate = plannedCompletionDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "plannedCompletionDate=" + plannedCompletionDate +
                ", price=" + price +
                ", masterId=" + masterId +
                ", placeId=" + placeId +
                '}';
    }
}
