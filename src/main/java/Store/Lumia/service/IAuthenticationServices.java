package Store.Lumia.service;

import Store.Lumia.entity.AuthenticationResponse;
import Store.Lumia.entity.RefreshTokenRequest;

import java.util.HashMap;

public interface IAuthenticationServices {

    AuthenticationResponse login(String email, String password);
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshToken);
    HashMap<String,String> forgetPassword(String email);
    HashMap<String,String> resetPassword(String passwordResetToken, String newPassword);
}
