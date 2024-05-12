package Store.Lumia.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import Store.Lumia.config.EmailService;
import Store.Lumia.entity.*;

import Store.Lumia.repository.UserRepository;

import java.util.HashMap;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class IAuthenticationServicesImp implements IAuthenticationServices {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final IJWTServices jwtServices;
    private final EmailService emailService;


    public AuthenticationResponse login(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        var user = userRepository.findByMatricule(email).orElseThrow(() -> new RuntimeException("User not found"));
        var jwt = jwtServices.generateToken((UserDetails) user);
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
        dto.setId(user.getId());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setEmail(user.getEmail());
        dto.setMot2passe(user.getMot2passe());
        dto.setRole(user.getRole());
        return dto;
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshToken) {
        String userEmail = jwtServices.extractUsername(refreshToken.getRefreshToken());
        User user = userRepository.findByMatricule(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        if(jwtServices.isTokenValid(refreshToken.getRefreshToken(), (UserDetails) user)) {
            var jwt = jwtServices.generateToken((UserDetails) user);

            AuthenticationResponse authenticationResponse = new AuthenticationResponse();

            authenticationResponse.setAccessToken(jwt);
            authenticationResponse.setRefreshToken(refreshToken.getRefreshToken());
            return authenticationResponse;
        }
        return null;
    }

    @Override
    public HashMap<String, String> forgetPassword(String email) {
        HashMap message = new HashMap();

        User userexisting = userRepository.findByMatricule(email).orElseThrow(() -> new RuntimeException("User not found"));

        UUID token = UUID.randomUUID();
        userexisting.setPasswordResetToken(token.toString());
        userexisting.setId(userexisting.getId());

        Mail mail = new Mail();

        mail.setSubject("Reset Password");
        mail.setTo(userexisting.getEmail());
        mail.setContent("Votre nouveau TOKEN est : " + "http://localhost:4200/resetpassword/"+userexisting.getPasswordResetToken());
        emailService.sendSimpleEmail(mail);
        userRepository.save(userexisting);
        message.put("user","user FOUND and email is Sent");
        return message;
    }

    @Override
    public HashMap<String,String> resetPassword(@PathVariable String passwordResetToken, String newPassword){
        User userexisting = userRepository.findByPasswordResetToken(passwordResetToken).orElseThrow(() -> new RuntimeException("User not found"));
        HashMap message = new HashMap();
        if (userexisting != null) {
            userexisting.setId(userexisting.getId());
            userexisting.setMot2passe(new BCryptPasswordEncoder().encode(newPassword));
            userexisting.setPasswordResetToken(null);
            userRepository.save(userexisting);
            message.put("resetpassword","succès");
            return message;
        }else
        {
            message.put("resetpassword","Échoué ");
            return message;
        }
    }
}
