package by.senla.training.bulyha.carservice.model;

import by.senla.training.bulyha.carservice.model.enums.OrdersStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order extends IdEntity {

    @Column(name = "submission_date")
    private LocalDate submissionDate;
    @Column(name = "planned_completion_date")
    private LocalDate plannedCompletionDate;
    @Column(name = "completion_date")
    private LocalDate completionDate;
    @Column(name = "price")
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrdersStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id")
    private Master master;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    public Order() {
    }

    public Order(LocalDate submissionDate, LocalDate plannedCompletionDate, LocalDate completionDate, BigDecimal price, OrdersStatus status, Master master, Place place) {
        this.submissionDate = submissionDate;
        this.plannedCompletionDate = plannedCompletionDate;
        this.completionDate = completionDate;
        this.price = price;
        this.status = status;
        this.master = master;
        this.place = place;
    }

    public Order(int id, LocalDate submissionDate, LocalDate plannedCompletionDate, LocalDate completionDate, BigDecimal price, OrdersStatus status, Master master, Place place) {
        this.id = id;
        this.submissionDate = submissionDate;
        this.plannedCompletionDate = plannedCompletionDate;
        this.completionDate = completionDate;
        this.price = price;
        this.status = status;
        this.master = master;
        this.place = place;
    }

    public Order(LocalDate submissionDate, LocalDate plannedCompletionDate, LocalDate completionDate, BigDecimal price, OrdersStatus status) {
        this.submissionDate = submissionDate;
        this.plannedCompletionDate = plannedCompletionDate;
        this.completionDate = completionDate;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public LocalDate getPlannedCompletionDate() {
        return plannedCompletionDate;
    }

    public void setPlannedCompletionDate(LocalDate plannedCompletionDate) {
        this.plannedCompletionDate = plannedCompletionDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public OrdersStatus getStatus() {
        return status;
    }

    public void setStatus(OrdersStatus status) {
        this.status = status;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", submissionDate=" + submissionDate +
                ", plannedCompletionDate=" + plannedCompletionDate +
                ", completionDate=" + completionDate +
                ", price=" + price +
                ", status=" + status +
                ", master's id=" + master.getId() +
                ", place's id=" + place.getId() +
                '}';
    }
}
