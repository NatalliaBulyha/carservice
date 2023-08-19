package by.senla.training.bulyha.carservice.controller;

import by.senla.training.bulyha.carservice.dto.PrintPlacesDto;
import by.senla.training.bulyha.carservice.service.PlaceService;
import by.senla.training.bulyha.carservice.util.LocalDateDeserialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private PlaceService placeService;
    private static final Logger LOG = LoggerFactory.getLogger(PlaceController.class);

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_MASTER"})
    @GetMapping
    public List<PrintPlacesDto> showPlaces(@RequestParam(required = false) Integer placeId) {
        List<PrintPlacesDto> placesList = new ArrayList<>();
        if (placeId == null) {
            placesList = placeService.getAll();
        } else {
            placesList.add(placeService.getById(placeId));
        }
        return placesList;
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/{name}")
    public void addPlace(@PathVariable("name") String name) {
        placeService.addPlace(name);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/{id}")
    public void updateStatusPlace(@PathVariable("id") Integer placeId) {
        placeService.deletePlace(placeId);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/freePlacesOnDate")
    public ResponseEntity<String> countFreePlaceToOrderOnDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok().body("Free place on " + date + ": " + placeService.countFreePlacesOnDate(date));
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/freePlaces")
    public List<PrintPlacesDto> showFreePlaces() {
        return placeService.showFreePlaces();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/nearestFreeDate")
    public ResponseEntity<String> showNearestFreeDate() {
        return ResponseEntity.ok().body(LocalDateDeserialize.parsFromLocalDate(placeService.showNearestFreeDate()));
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/export")
    public void exportPlace() {
            placeService.exportPlace();
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/import")
    public void importPlaceList() {
            placeService.importPlaceList();
    }
}

