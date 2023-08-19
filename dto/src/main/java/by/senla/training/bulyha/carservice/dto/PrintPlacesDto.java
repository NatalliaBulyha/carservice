package by.senla.training.bulyha.carservice.dto;

public class PrintPlacesDto {

    private Integer id;
    private String name;
    private String placesStatus;

    public PrintPlacesDto(int id, String name, String placesStatus) {
        this.id = id;
        this.name = name;
        this.placesStatus = placesStatus;
    }

    public Integer getId() {
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

    public String getPlacesStatus() {
        return placesStatus;
    }

    public void setPlacesStatus(String placesStatus) {
        this.placesStatus = placesStatus;
    }

    @Override
    public String toString() {
        return "PlacesDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", placesStatus=" + placesStatus +
                '}';
    }
}
