package by.senla.training.bulyha.carservice.model;

import by.senla.training.bulyha.carservice.model.enums.PlacesStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "places")
public class Place extends IdEntity {

    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PlacesStatus status;

    public Place() {
    }

    public Place(int id) {
        this.id = id;
    }

    public Place(int id, String name, PlacesStatus placeInGarageStatus) {
        this.id = id;
        this.name = name;
        this.status = placeInGarageStatus;
    }

    public Place(String name, PlacesStatus status) {
        this.name = name;
        this.status = status;
    }

    public Place(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlacesStatus getStatus() {
        return status;
    }

    public void setStatus(PlacesStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", placesStatus=" + status +
                '}';
    }
}
