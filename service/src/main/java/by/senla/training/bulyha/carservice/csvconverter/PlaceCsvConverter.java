package by.senla.training.bulyha.carservice.csvconverter;

import by.senla.training.bulyha.carservice.model.Place;
import by.senla.training.bulyha.carservice.model.enums.PlacesStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlaceCsvConverter {

    public List<Place> convertStringToPlacesList(List<String> stringPlacesList) {
        List<Place> placesList = new ArrayList<>();
        for (String string : stringPlacesList) {
            String[] places = string.split(",");
            Place place = new Place(Integer.parseInt(places[0]), places[1], PlacesStatus.valueOf(places[2].toUpperCase()));
            placesList.add(place);
        }
        return placesList;
    }

    public List<String> convertPlacesListToString(List<Place> placesList) {
        List<String> stringPlacesList = new ArrayList<>();
        for (Place place : placesList) {
            stringPlacesList.add(String.valueOf(place.getId()) + ";" + String.valueOf(place.getName()) + ";"
                    + String.valueOf(place.getStatus()));
        }
        return stringPlacesList;
    }
}
