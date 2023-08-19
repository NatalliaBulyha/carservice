package by.senla.training.bulyha.carservice.dao;

import by.senla.training.bulyha.carservice.model.Master;
import by.senla.training.bulyha.carservice.model.MasterColumnName;

import java.util.List;

public interface MasterDao extends GenericDao<Master> {

    List<Master> showMastersListSortedBy(MasterColumnName nameParameter);

    List<Master> showMastersListSortedByTwoParameters(MasterColumnName parameter1, MasterColumnName parameter2);
}
