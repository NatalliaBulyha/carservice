package by.senla.training.bulyha.carservice.service.impl;

import by.senla.training.bulyha.carservice.exception.CarServiceBackEndException;
import by.senla.training.bulyha.carservice.exception.InternalException;
import by.senla.training.bulyha.carservice.model.enums.Status;
import by.senla.training.bulyha.carservice.model.User;
import by.senla.training.bulyha.carservice.security.UserDao;
import by.senla.training.bulyha.carservice.service.UserService;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public List<User> getAll() {
        List<User> result = userDao.getAll();
        LOG.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Transactional
    public User findByUsername(String username) {
        User result = userDao.findByName(username, "userName");
        LOG.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Transactional
    public User findById(Integer id) {
        User result = userDao.getById(id);
        if (result == null) {
            LOG.warn("User with id {} not found!", id);
            throw new CarServiceBackEndException("");
        }
        LOG.info("IN findById - user: {} found by id: {}!", result, id);
        return result;
    }

    @Transactional
    public void delete(Integer id) {
        try {
            User user = userDao.getById(id);
            if (user.getStatus().equals(Status.NOT_ACTIVE)) {
                LOG.info("User with id: {} was deleted earlier!", id);
                throw new CarServiceBackEndException("User was deleted earlier! id=" + id);
            }
            user.setStatus(Status.NOT_ACTIVE);
            userDao.update(user);
        } catch (HibernateException e) {
            throw new InternalException(e.getMessage());
        }
    }
}
