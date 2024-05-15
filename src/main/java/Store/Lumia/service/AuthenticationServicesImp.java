package Store.Lumia.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import Store.Lumia.config.EmailService;
import Store.Lumia.entity.*;

import Store.Lumia.repository.UserRepository;

import java.util.HashMap;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServicesImp implements AuthenticationServices {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTServices jwtServices;
    private final EmailService emailService;


    public AuthenticationResponse login(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        var user = userRepository.findByUsername(email).orElseThrow(() -> new RuntimeException("User not found"));
        var jwt = jwtServices.generateToken(user);
        var refreshToken = jwtServices.generateRefreshToken(new HashMap<>(), (UserDetails) user);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        authenticationResponse.setAccessToken(jwt);
        authenticationResponse.setRefreshToken(refreshToken);

        User userDetails = convertToUserDto(user);
        authenticationResponse.setUserDetails(userDetails);

        return authenticationResponse;
    }

    private User convertToUserDto(User user) {
        User dto = new User();
        dto.setUsername(user.getUsername());
        dto.setId(user.getId());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        return dto;
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshToken) {
        String userEmail = jwtServices.extractUsername(refreshToken.getRefreshToken());
        User user = userRepository.findByUsername(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        if(jwtServices.isTokenValid(refreshToken.getRefreshToken(), (UserDetails) user)) {
            var jwt = jwtServices.generateToken(user);

            AuthenticationResponse authenticationResponse = new AuthenticationResponse();

            authenticationResponse.setAccessToken(jwt);
            authenticationResponse.setRefreshToken(refreshToken.getRefreshToken());
            return authenticationResponse;
        }
        return null;
    }
    @Override
    public HashMap<String, String> forgetPassword(String email) {
        HashMap<String, String> message = new HashMap<>();

        User userExisting = userRepository.findByUsername(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UUID token = UUID.randomUUID();
        userExisting.setPasswordResetToken(token.toString());
        userRepository.save(userExisting);

        Mail mail = new Mail();
        mail.setSubject("Reset Password");
        mail.setTo(userExisting.getEmail());
        mail.setContent("Votre nouveau TOKEN est : http://localhost:4200/resetpassword/" + token.toString());

        emailService.sendSimpleEmail(mail); // Assuming EmailService is properly configured

        message.put("user", "User found, and email is sent");
        return message;
    }

    @Override
    public HashMap<String, String> resetPassword(String passwordResetToken, String newPassword) {
        User userExisting = userRepository.findByPasswordResetToken(passwordResetToken)
                .orElseThrow(() -> new RuntimeException("User not found"));

        HashMap<String, String> message = new HashMap<>();
        if (userExisting != null) {
            userExisting.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userExisting.setPasswordResetToken(null);
            userRepository.save(userExisting);
            message.put("resetpassword", "Success");
        } else {
            message.put("resetpassword", "Failed");
        }
        return message;
    }

}
