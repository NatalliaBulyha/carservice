package by.senla.training.bulyha.carservice.controller;

import by.senla.training.bulyha.carservice.dto.MasterColumnNameDto;
import by.senla.training.bulyha.carservice.dto.MasterDto;
import by.senla.training.bulyha.carservice.dto.PrintMasterDto;
import by.senla.training.bulyha.carservice.service.MasterService;
import by.senla.training.bulyha.carservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/masters")
public class MasterController {

    private MasterService masterService;
    private OrderService orderService;
    private static final Logger LOG = LoggerFactory.getLogger(MasterController.class);

    @Autowired
    public MasterController(MasterService masterService, OrderService orderService) {
        this.masterService = masterService;
        this.orderService = orderService;
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public void addMaster(@Valid @RequestBody MasterDto masterDto) {
        masterService.addMaster(masterDto);
        LOG.info("Master added!");
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/{id}")
    public void updateMasterStatus(@Positive(message = "Id should not be negative") @PathVariable("id") int id) {
        masterService.updateStatusMaster(id);
        LOG.info("Master deleted!");
    }

    @GetMapping
    public List<PrintMasterDto> showMastersList(@RequestParam(required = false) MasterColumnNameDto sort,
                                                @RequestParam(required = false) MasterColumnNameDto sort2,
                                                @RequestParam(required = false) Integer masterId,
                                                @RequestParam(required = false) Integer orderId) {
        List<PrintMasterDto> masterDtos = new ArrayList<>();
        if (sort != null && sort2 == null) {
            masterDtos = masterService.showMastersListSortedByParameter(sort);
        } else if (sort != null) {
            masterDtos = masterService.showMastersListSortedByTwoParameters(sort, sort2);
        } else if (masterId != null) {
            masterDtos.add(masterService.getById(masterId));
        } else if (orderId != null) {
            masterDtos.add(orderService.showMasterByOrder(orderId));
        } else {
            masterDtos = masterService.getAll();
        }
        return masterDtos;
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/import")
    public void importMasterList() {
            masterService.importMasterList();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/export")
    public void exportMaster() {
            masterService.exportMaster();
    }
}
