package by.senla.training.bulyha.carservice.service;

import by.senla.training.bulyha.carservice.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User findByUsername(String username);

    User findById(Integer id);

    void delete(Integer id);
}
