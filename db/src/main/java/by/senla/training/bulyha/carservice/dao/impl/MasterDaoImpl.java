package by.senla.training.bulyha.carservice.dao.impl;

import by.senla.training.bulyha.carservice.dao.AbstractDao;
import by.senla.training.bulyha.carservice.dao.MasterDao;
import by.senla.training.bulyha.carservice.model.Master;
import by.senla.training.bulyha.carservice.model.MasterColumnName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class MasterDaoImpl extends AbstractDao<Master> implements MasterDao  {
    private static final Logger log = LoggerFactory.getLogger(MasterDaoImpl.class);
    public MasterDaoImpl() {
        this.type = Master.class;
    }

    public List<Master> showMastersListSortedBy(MasterColumnName nameParameter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Master> criteriaQuery = builder.createQuery(Master.class);
        Root<Master> root = criteriaQuery.from(Master.class);
        criteriaQuery.orderBy(
                builder.asc(root.get(nameParameter.getName().toString().toLowerCase())));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Master> showMastersListSortedByTwoParameters(MasterColumnName parameter1, MasterColumnName parameter2) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Master> criteriaQuery = builder.createQuery(Master.class);
        Root<Master> root = criteriaQuery.from(Master.class);
        criteriaQuery.orderBy((builder.asc(root.get(parameter1.getName().toString().toLowerCase()))),
                (builder.asc(root.get(parameter2.getName().toString().toLowerCase()))));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}

