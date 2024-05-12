package Store.Lumia.controller;

import Store.Lumia.entity.AuthenticationResponse;
import Store.Lumia.entity.RefreshTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Store.Lumia.entity.User;
import Store.Lumia.service.IAuthenticationServices;

import java.util.HashMap;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final IAuthenticationServices authenticationServices;

  @PostMapping("/login")
  public AuthenticationResponse login(@RequestBody User user) {
    return authenticationServices.login(user.getMatricule(), user.getMot2passe());
  }

  @PostMapping("/refreshToken")
  public AuthenticationResponse refreshToken(@RequestBody RefreshTokenRequest refreshToken) {
    return authenticationServices.refreshToken(refreshToken);
  }

  @PostMapping("/forgetpassword")
  public HashMap<String,String> forgetPassword(@RequestParam String email){
    return authenticationServices.forgetPassword(email);
  }

  @PostMapping("/resetPassword/{passwordResetToken}")
  public HashMap<String,String> resetPassword(@PathVariable String passwordResetToken, String newPassword){
    return authenticationServices.resetPassword(passwordResetToken, newPassword);
  }
}
