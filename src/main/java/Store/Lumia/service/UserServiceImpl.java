package Store.Lumia.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import Store.Lumia.entity.User;
import Store.Lumia.repository.UserRepository;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService(){
            @Override
            public UserDetails loadUserByUsername(String s) {
                return (UserDetails) userRepository.findByUsername(s).orElseThrow(() -> new RuntimeException("User not found"));
            }
        };
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<User> getUserByusername(String matricule) {
        return userRepository.findByUsername(matricule);
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User updateUser(User user) {
        
        if (userRepository.existsById(user.getId())) {
            return userRepository.save(user); 
        } else {
            return null; 
        }
    }
}
