package by.senla.training.bulyha.carservice.service;

import by.senla.training.bulyha.carservice.dto.PrintPlacesDto;

import java.time.LocalDate;
import java.util.List;

public interface PlaceService {

    void addPlace(String name);

    PrintPlacesDto getById(int placeId);

    void deletePlace(int id);

    List<PrintPlacesDto> showFreePlaces();

    int countFreePlacesOnDate(LocalDate date);

    LocalDate showNearestFreeDate();

    List<PrintPlacesDto> getAll();

    void importPlaceList();

    void exportPlace();
}

