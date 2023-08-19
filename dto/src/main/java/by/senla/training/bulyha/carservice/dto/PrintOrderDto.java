package by.senla.training.bulyha.carservice.dto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PrintOrderDto {

    private Integer id;
    private LocalDate submissionDate;
    private LocalDate plannedCompletionDate;
    private LocalDate completionDate;
    private BigDecimal price;
    private String status;
    private Integer masterId;
    private Integer placeId;

    public PrintOrderDto(int id, LocalDate submissionDate, LocalDate plannedCompletionDate, LocalDate completionDate,
                         BigDecimal price, String status, int masterId, int placeId) {
        this.id = id;
        this.submissionDate = submissionDate;
        this.plannedCompletionDate = plannedCompletionDate;
        this.completionDate = completionDate;
        this.price = price;
        this.status = status;
        this.masterId = masterId;
        this.placeId = placeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    @JsonFormat
    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public LocalDate getPlannedCompletionDate() {
        return plannedCompletionDate;
    }

    @JsonFormat
    public void setPlannedCompletionDate(LocalDate plannedCompletionDate) {
        this.plannedCompletionDate = plannedCompletionDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    @JsonFormat
    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return "PrintOrderDto{" +
                "id=" + id +
                ", submissionDate=" + submissionDate +
                ", plannedCompletionDate=" + plannedCompletionDate +
                ", completionDate=" + completionDate +
                ", price=" + price +
                ", status=" + status +
                ", masterId=" + masterId +
                ", placeId=" + placeId +
                '}';
    }
}
