package by.senla.training.bulyha.carservice.service;

import by.senla.training.bulyha.carservice.dto.MasterColumnNameDto;
import by.senla.training.bulyha.carservice.dto.MasterDto;
import by.senla.training.bulyha.carservice.dto.PrintMasterDto;

import java.util.List;

public interface MasterService {

    PrintMasterDto getById(int id);

    void addMaster(MasterDto masterDto);

    void updateStatusMaster(int id);

    List<PrintMasterDto> getAll();

    List<PrintMasterDto> showMastersListSortedByParameter(MasterColumnNameDto parameter);

    List<PrintMasterDto> showMastersListSortedByTwoParameters(MasterColumnNameDto param1, MasterColumnNameDto param2);

    void importMasterList();

    void exportMaster();
}