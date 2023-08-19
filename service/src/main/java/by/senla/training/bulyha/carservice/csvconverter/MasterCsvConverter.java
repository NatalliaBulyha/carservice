package by.senla.training.bulyha.carservice.csvconverter;

import by.senla.training.bulyha.carservice.exception.ValidatorsException;
import by.senla.training.bulyha.carservice.model.Master;
import by.senla.training.bulyha.carservice.model.enums.MastersQualification;
import by.senla.training.bulyha.carservice.model.enums.MastersStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class MasterCsvConverter {

    public List<Master> convertStringToMastersList(List<String> stringMastersList) throws ValidatorsException {
        List<Master> mastersList = new ArrayList<>();
        for (String string : stringMastersList) {
            String[] masters = string.split(";");
            Master master = new Master(masters[1], MastersQualification.valueOf(masters[2].toUpperCase()),
                    Integer.parseInt(masters[3]), MastersStatus.valueOf(masters[4].toUpperCase()));
            mastersList.add(master);
        }

        return mastersList;
    }

    public List<String> convertMastersListToString(List<Master> mastersList) {
        List<String> stringMastersList = new ArrayList<>();
        for (Master master : mastersList) {
            stringMastersList.add(String.valueOf(master.getId()) + ";" + String.valueOf(master.getName()) + ";" + String.valueOf(master.getQualification()) +
                    ";" + String.valueOf(master.getAge()) + ";" + String.valueOf(master.getStatus()));
        }
        return stringMastersList;
    }
}
