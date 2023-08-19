package by.senla.training.bulyha.carservice.dao.impl;

import by.senla.training.bulyha.carservice.dao.AbstractDao;
import by.senla.training.bulyha.carservice.dao.OrderDao;
import by.senla.training.bulyha.carservice.model.Order;
import by.senla.training.bulyha.carservice.model.Order_;
import by.senla.training.bulyha.carservice.model.enums.OrdersStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

@Component
public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {
    private static final Logger log = LoggerFactory.getLogger(OrderDaoImpl.class);
    public OrderDaoImpl() {
        this.type = Order.class;
    }

    public List<Order> showOrdersByMaster(int masterId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(builder.equal(root.get(Order_.MASTER), masterId));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Order> showOrdersListSortedByString(String nameParameter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.orderBy(
                builder.asc(root.get(nameParameter)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Order> showProcessedOrdersList() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(builder.equal(root.get(Order_.STATUS), OrdersStatus.PROCESSED));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Order> showOrdersIdProcessed(int masterId, int placeId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        Predicate[] predicates = new Predicate[3];
        predicates[0] = builder.equal(root.get(Order_.MASTER), masterId);
        predicates[1] = builder.equal(root.get(Order_.PLACE), masterId);
        predicates[2] = builder.equal(root.get(Order_.STATUS), OrdersStatus.PROCESSED);
        criteriaQuery.select(root).where(predicates);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Order> showStatusOrdersListSortedByParameter(OrdersStatus ordersStatus, String columnBySorted) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        criteriaQuery.where(builder.equal(root.get(Order_.STATUS), ordersStatus));
        criteriaQuery.orderBy(builder.asc(root.get(columnBySorted)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Order> showStatusOrdersListForPeriod(OrdersStatus ordersStatus, LocalDate startedDate, LocalDate finishDate) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        criteriaQuery.where(builder.and((builder.equal(root.get(Order_.STATUS), ordersStatus))),
                (builder.between(root.get(Order_.COMPLETION_DATE), startedDate, finishDate)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Order> showStatusOrdersListForPeriodSortedBy(OrdersStatus ordersStatus, String columnBySorted, LocalDate startedDate, LocalDate finishDate) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        criteriaQuery.where(builder.and((builder.equal(root.get(Order_.STATUS), ordersStatus))),
                ((builder.between(root.get(Order_.COMPLETION_DATE), startedDate, finishDate))));
        criteriaQuery.orderBy(builder.asc(root.get(columnBySorted)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Order> showTodayProcessedOrdersList() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(builder.and(builder.equal(root.get(Order_.STATUS), OrdersStatus.PROCESSED)),
                (builder.equal(root.get(Order_.COMPLETION_DATE), LocalDate.now())));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
