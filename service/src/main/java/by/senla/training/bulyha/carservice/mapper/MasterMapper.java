package by.senla.training.bulyha.carservice.mapper;

import by.senla.training.bulyha.carservice.dto.ComparisonWithDBMasterDto;
import by.senla.training.bulyha.carservice.dto.MasterColumnNameDto;
import by.senla.training.bulyha.carservice.dto.MasterDto;
import by.senla.training.bulyha.carservice.dto.PrintMasterDto;
import by.senla.training.bulyha.carservice.model.Master;
import by.senla.training.bulyha.carservice.model.MasterColumnName;
import by.senla.training.bulyha.carservice.model.enums.MasterColumnNameEnum;
import by.senla.training.bulyha.carservice.model.enums.MastersQualification;
import by.senla.training.bulyha.carservice.model.enums.MastersStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MasterMapper {

    public Master mappingMasterDtoByMaster(MasterDto masterDto) throws IllegalArgumentException {
        Master master = new Master();
        master.setName(masterDto.getName());
        master.setQualification(MastersQualification.valueOf(masterDto.getQualification()));
        master.setAge(masterDto.getAge());
        master.setStatus(MastersStatus.WORK);
        return master;
    }

    public ComparisonWithDBMasterDto mappingMasterByComparisonWithDBMasterDto(Master master) throws IllegalArgumentException {
        return new ComparisonWithDBMasterDto(master.getName(), master.getQualification().toString(),
                master.getAge());
    }

    public List<PrintMasterDto> mappingMastersListByPrintMasterDtoList(List<Master> masterList) {
        List<PrintMasterDto> masterDtoList = new ArrayList<>();
        for (Master master : masterList) {
            PrintMasterDto masterDto = mappingMasterByPrintMasterDto(master);
            masterDtoList.add(masterDto);
        }
        return masterDtoList;
    }

    public PrintMasterDto mappingMasterByPrintMasterDto(Master master) {
        PrintMasterDto m = new PrintMasterDto(master.getId(), master.getName(), master.getQualification().toString(),
                master.getAge(), master.getStatus().toString());
        return m;
    }

    public MasterColumnName mappingMasterByPrintMasterDto(MasterColumnNameDto columnNameDto) {
        MasterColumnName name = new MasterColumnName(MasterColumnNameEnum.valueOf(columnNameDto.getMastersColumnName().toUpperCase()));
        return name;
    }
}
