package by.senla.training.bulyha.carservice.security;

import by.senla.training.bulyha.carservice.dao.AbstractDao;
import by.senla.training.bulyha.carservice.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {
    public RoleDaoImpl() {
        this.type = Role.class;
    }
}
