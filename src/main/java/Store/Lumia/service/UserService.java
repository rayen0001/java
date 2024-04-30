package Store.Lumia.service;

import java.util.List;

import Store.Lumia.entity.User;

public interface UserService {
    User saveUser(User user);
    User getUserById(Long id);
    User getUserByMatricule(String matricule);
    void deleteUser(Long id);
    List<User> getAllUsers(); 
    User updateUser(User user);
}
