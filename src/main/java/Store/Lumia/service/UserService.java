package Store.Lumia.service;

import java.util.List;
import java.util.Optional;

import Store.Lumia.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    User saveUser(User user);
    User getUserById(Long id);
    Optional<User> getUserByMatricule(String matricule);
    void deleteUser(Long id);
    List<User> getAllUsers(); 
    User updateUser(User user);
    UserDetailsService userDetailsService();
}
