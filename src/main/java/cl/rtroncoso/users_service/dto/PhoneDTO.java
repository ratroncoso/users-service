package cl.rtroncoso.users_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {

    @NotEmpty(message = "El numero es requerido.")
    private String number;
    @NotEmpty(message = "El codigo ciudad es requerido.")
    private String citycode;
    @NotEmpty(message = "El codigo pais es requerido.")
    private String countrycode;
}
