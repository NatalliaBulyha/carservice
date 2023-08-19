package by.senla.training.bulyha.carservice.mapper;

import by.senla.training.bulyha.carservice.dto.PrintPlacesDto;
import by.senla.training.bulyha.carservice.model.Place;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlaceMapper {

    public List<PrintPlacesDto> mappingPlacesListByPrintPlaceDtoList(List<Place> placeList) {
        List<PrintPlacesDto> placeDtoList = new ArrayList<>();
        for (Place place : placeList) {
            PrintPlacesDto placesDto = mappingPlaceByPrintPlaceDto(place);
            placeDtoList.add(placesDto);
        }
        return placeDtoList;
    }

    public PrintPlacesDto mappingPlaceByPrintPlaceDto(Place place) {
        return new PrintPlacesDto(place.getId(), place.getName(), place.getStatus().toString());
    }
}
