package cl.rtroncoso.users_service.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@Data
public class PasswordUtils {

    @Value("${validation.password.regexp}")
    private String passwordRegEx;
    @Value("${validation.password.errorMessage}")
    private String passwordErrorMessage;

    public Boolean validatePassword(String password) {
        return Pattern.matches(passwordRegEx, password);
    }
}
