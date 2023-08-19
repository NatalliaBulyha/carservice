package by.senla.training.bulyha.carservice.service.impl;

import by.senla.training.bulyha.carservice.csvconverter.MasterCsvConverter;
import by.senla.training.bulyha.carservice.dao.MasterDao;
import by.senla.training.bulyha.carservice.dto.MasterColumnNameDto;
import by.senla.training.bulyha.carservice.dto.MasterDto;
import by.senla.training.bulyha.carservice.dto.PrintMasterDto;
import by.senla.training.bulyha.carservice.exception.CarServiceBackEndException;
import by.senla.training.bulyha.carservice.exception.InternalException;
import by.senla.training.bulyha.carservice.mapper.MasterMapper;
import by.senla.training.bulyha.carservice.model.Master;
import by.senla.training.bulyha.carservice.model.MasterColumnName;
import by.senla.training.bulyha.carservice.model.enums.MastersStatus;
import by.senla.training.bulyha.carservice.printwriter.FileReader;
import by.senla.training.bulyha.carservice.service.MasterService;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Service
public class MasterServiceImpl implements MasterService {

    private MasterDao masterDao;
    private MasterMapper masterMapper;
    private MasterCsvConverter masterCsvConverter;
    private static final Logger LOG = LoggerFactory.getLogger(MasterServiceImpl.class);

    @Autowired
    public MasterServiceImpl(MasterDao masterDao, MasterMapper masterMapper, MasterCsvConverter masterCsvConverter) {
        this.masterDao = masterDao;
        this.masterMapper = masterMapper;
        this.masterCsvConverter = masterCsvConverter;
    }

    @Transactional
    public PrintMasterDto getById(int id) {
        Master master = masterDao.getById(id);
            if (checkThereIsMaster(master)) {
                throw new CarServiceBackEndException("There is no such master! id = " + id + "!");
            }
        return masterMapper.mappingMasterByPrintMasterDto(master);
    }

    @Transactional
    public void addMaster(MasterDto masterDto) {
        try {
            Master master = masterMapper.mappingMasterDtoByMaster(masterDto);
            List<Master> mastersList = masterDao.getAll();
            for (Master m : mastersList) {
                if (m.getName().equals(master.getName()) && m.getAge() == master.getAge()
                        && m.getStatus().equals(master.getStatus())) {
                    throw new CarServiceBackEndException("Master is already in the list of masters! " + masterDto.toString());
                }
            }
            masterDao.add(master);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public void updateStatusMaster(int id) {
        try {
            Master master = masterDao.getById(id);
            if (master.getStatus().equals(MastersStatus.DELETE)) {
                throw new CarServiceBackEndException("Master was deleted earlier! id = " + id + "!");
            }
            master.setStatus(MastersStatus.DELETE);
            masterDao.update(master);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public List<PrintMasterDto> getAll() {
        try {
            return masterMapper.mappingMastersListByPrintMasterDtoList(masterDao.getAll());
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public List<PrintMasterDto> showMastersListSortedByTwoParameters(MasterColumnNameDto sort1, MasterColumnNameDto sort2) {
        try {
            MasterColumnName param1 = masterMapper.mappingMasterByPrintMasterDto(sort1);
            MasterColumnName param2 = masterMapper.mappingMasterByPrintMasterDto(sort2);
            List<Master> masterList = masterDao.showMastersListSortedByTwoParameters(param1, param2);
            return masterMapper.mappingMastersListByPrintMasterDtoList(masterList);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public List<PrintMasterDto> showMastersListSortedByParameter(MasterColumnNameDto sort) {
        try {
            MasterColumnName parameter = masterMapper.mappingMasterByPrintMasterDto(sort);
            List<Master> mastersList = masterDao.showMastersListSortedBy(parameter);
            return masterMapper.mappingMastersListByPrintMasterDtoList(mastersList);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }

    private boolean checkThereIsMaster(Master master) {
        if (master.getName() == null) {
            return true;
        }
        return false;
    }

    @Transactional
    public void importMasterList() {
        try {
            File file = new File("controller/src/main/resources/csvfiles/masters.csv");
            List<Master> mastersList = masterCsvConverter.convertStringToMastersList(FileReader.dataImport(file));
            for (Master master : mastersList) {
                masterDao.add(master);
            }
        } catch (HibernateException | FileNotFoundException e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Transactional
    public void exportMaster() {
        try {
            File file = new File("controller/src/main/resources/csvfiles/exportMaster.csv");
            List<Master> masterList = masterDao.getAll();
            FileReader.dataExport(masterCsvConverter.convertMastersListToString(masterList), file);
        } catch (HibernateException | FileNotFoundException e) {
            throw new InternalException(e.getMessage());
        }
    }
}
