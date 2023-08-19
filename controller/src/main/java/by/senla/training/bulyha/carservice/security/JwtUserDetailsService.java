package by.senla.training.bulyha.carservice.security;

import by.senla.training.bulyha.carservice.model.User;
import by.senla.training.bulyha.carservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("JwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private static final Logger LOG = LoggerFactory.getLogger(JwtUserDetailsService.class);

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        LOG.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
        return jwtUser;
    }
}
