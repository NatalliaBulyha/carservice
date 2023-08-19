package by.senla.training.bulyha.carservice.dto;

import java.time.LocalDate;

public class FreePlacesDto {

    private LocalDate completionDate;
    private String placeName;


    public FreePlacesDto(LocalDate completionDate, String placeName) {
        this.completionDate = completionDate;
        this.placeName = placeName;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    @Override
    public String toString() {
        return "FreePlacesDto{" +
                "Date=" + completionDate +
                ", Place='" + placeName + '\'' +
                '}';
    }
}
