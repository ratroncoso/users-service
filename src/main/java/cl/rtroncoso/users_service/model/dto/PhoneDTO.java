package cl.rtroncoso.users_service.model.dto;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
public class PhoneDTO {

    @NotEmpty(message = "El numero es requerido.")
    private String number;
    @NotEmpty(message = "El codigo ciudad es requerido.")
    private String citycode;
    @NotEmpty(message = "El codigo pais es requerido.")
    private String countrycode;
}
