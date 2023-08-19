package by.senla.training.bulyha.carservice.service.impl;


import by.senla.training.bulyha.carservice.csvconverter.MasterCsvConverter;
import by.senla.training.bulyha.carservice.dao.MasterDao;
import by.senla.training.bulyha.carservice.dto.MasterColumnNameDto;
import by.senla.training.bulyha.carservice.dto.PrintMasterDto;
import by.senla.training.bulyha.carservice.mapper.MasterMapper;
import by.senla.training.bulyha.carservice.model.Master;
import by.senla.training.bulyha.carservice.model.enums.MastersQualification;
import by.senla.training.bulyha.carservice.model.enums.MastersStatus;
import by.senla.training.bulyha.carservice.service.MasterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MasterServiceImplTest {

    private MasterService masterService;

    @Mock
    private MasterDao masterDao;
    @Mock
    private MasterMapper masterMapper;
    @Mock
    private MasterCsvConverter masterCsvConverter;

    @Before
    public void setUp() {
        masterService = new MasterServiceImpl(masterDao, masterMapper, masterCsvConverter);
    }

    @Test
    public void masterServiceImpl_getById() {
        PrintMasterDto mockMaster = new PrintMasterDto(1, "Fedor", "high", 26, "work");
        Master master = new Master(1, "Fedor", MastersQualification.HIGH, 26, MastersStatus.WORK);

        when(masterDao.getById(mockMaster.getId())).thenReturn(master);
        PrintMasterDto returnMaster = masterService.getById(master.getId());
        assertEquals(masterMapper.mappingMasterByPrintMasterDto(master), returnMaster);
    }

    @Test
    public void masterServiceImpl_getById_NullExc() {
        int masterId = 2222;
        when(masterDao.getById(masterId)).thenReturn(null);
        assertThrows(RuntimeException.class, () -> {
            masterService.getById(masterId);
        });
    }

    @Test
    public void masterServiceImpl_getAll() {
        List<Master> mastersList = new ArrayList();
        mastersList.add(new Master(1, "Fedor", MastersQualification.HIGH, 26, MastersStatus.WORK));
        mastersList.add(new Master(2, "Egor", MastersQualification.HIGH, 28, MastersStatus.WORK));
        mastersList.add(new Master(3, "Mark", MastersQualification.HIGH, 36, MastersStatus.WORK));

        when(masterDao.getAll()).thenReturn(mastersList);
        List<PrintMasterDto> returnMastersList = masterService.getAll();

        for(int i = 0; i < returnMastersList.size(); i++) {
            assertEquals(returnMastersList.get(i), masterMapper.mappingMastersListByPrintMasterDtoList(mastersList).get(i));
        }
    }

    @Test
    public void masterServiceImpl_showMastersListSortedByParameter() {
        List<Master> mastersList = new ArrayList();
        Master master1 = new Master(1, "Fedor", MastersQualification.HIGH, 26, MastersStatus.WORK);
        Master master2 = new Master(2, "Egor", MastersQualification.HIGH, 28, MastersStatus.WORK);
        Master master3 = new Master(3, "Mark", MastersQualification.HIGH, 36, MastersStatus.WORK);

        mastersList.add(master1);
        mastersList.add(master2);
        mastersList.add(master3);

        List<Master> sortedByName = new ArrayList<>();
        sortedByName.add(master2);
        sortedByName.add(master1);
        sortedByName.add(master3);

        when(masterDao.getAll()).thenReturn(mastersList);
        List<PrintMasterDto> returnMastersList = masterService.showMastersListSortedByParameter(new MasterColumnNameDto("name"));

        for(int i = 0; i < returnMastersList.size(); i++) {
            assertEquals(returnMastersList.get(i), masterMapper.mappingMastersListByPrintMasterDtoList(sortedByName).get(i));
        }
    }

    @Test
    public void masterServiceImpl_showMastersListSortedByParameter_isNotNull() {
        List<Master> mastersList = new ArrayList();
        Master master1 = new Master(1, "Fedor", MastersQualification.HIGH, 26, MastersStatus.WORK);
        Master master2 = new Master(2, "Egor", MastersQualification.HIGH, 28, MastersStatus.WORK);
        Master master3 = new Master(3, "Mark", MastersQualification.HIGH, 36, MastersStatus.WORK);

        mastersList.add(master1);
        mastersList.add(master2);
        mastersList.add(master3);

        when(masterDao.getAll()).thenReturn(mastersList);
        List<PrintMasterDto> returnMastersList = masterService.showMastersListSortedByParameter(new MasterColumnNameDto("name"));

        assertNotNull(returnMastersList);
    }

    @Test
    public void masterServiceImpl_showMastersListSortedByTwoParameters() {
        List<Master> mastersList = new ArrayList();
        Master master1 = new Master(1, "Mark", MastersQualification.HIGH, 26, MastersStatus.WORK);
        Master master2 = new Master(2, "Egor", MastersQualification.HIGH, 28, MastersStatus.WORK);
        Master master3 = new Master(3, "Mark", MastersQualification.HIGH, 36, MastersStatus.WORK);

        mastersList.add(master1);
        mastersList.add(master2);
        mastersList.add(master3);

        List<Master> sortedByName = new ArrayList<>();
        sortedByName.add(master2);
        sortedByName.add(master1);
        sortedByName.add(master3);

        when(masterDao.getAll()).thenReturn(mastersList);
        List<PrintMasterDto> returnMastersList = masterService.showMastersListSortedByTwoParameters(new MasterColumnNameDto("name"),
                new MasterColumnNameDto("age"));

        for(int i = 0; i < returnMastersList.size(); i++) {
            assertEquals(returnMastersList.get(i), masterMapper.mappingMastersListByPrintMasterDtoList(sortedByName).get(i));
        }
    }
}
