package Store.Lumia.service;

import Store.Lumia.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTServices {
    String extractUsername(String token);
    String generateToken(User user);
    boolean isTokenValid(String token, UserDetails userDetails);
    String generateRefreshToken(Map<String,Object> extraClaims, UserDetails userDetails);
}
