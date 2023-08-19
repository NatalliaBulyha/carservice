package by.senla.training.bulyha.carservice.security;

import by.senla.training.bulyha.carservice.dao.AbstractDao;
import by.senla.training.bulyha.carservice.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    public UserDaoImpl() {
        this.type = User.class;
    }
}
