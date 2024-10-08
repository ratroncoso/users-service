package cl.rtroncoso.users_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import cl.rtroncoso.users_service.utils.Constants;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDTO {

    @NotEmpty(message = "El nombre es requerido.")
    private String name;
    @NotEmpty(message = "El email es requerido.")
    @Email(regexp = Constants.EMAIL_PATTERN, message = "El email es invalido.")
    private String email;
    @NotEmpty(message = "El password es requerido.")
    private String password;

    @Valid
    private List<PhoneDTO> phones;
}
