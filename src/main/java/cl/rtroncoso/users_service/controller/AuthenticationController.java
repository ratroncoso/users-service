package cl.rtroncoso.users_service.controller;

import cl.rtroncoso.users_service.dto.AuthenticationResponse;
import cl.rtroncoso.users_service.dto.AutheticationRequest;
import cl.rtroncoso.users_service.dto.UserDTO;
import cl.rtroncoso.users_service.exception.BadRequestException;
import cl.rtroncoso.users_service.dto.RegisterUserDTO;
import cl.rtroncoso.users_service.entity.User;
import cl.rtroncoso.users_service.service.UserService;
import cl.rtroncoso.users_service.utils.PasswordUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordUtils passwordUtils;

    @PostMapping(path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody @Valid RegisterUserDTO dto) {

        if (!passwordUtils.validatePassword(dto.getPassword())) {
            throw new BadRequestException(passwordUtils.getPasswordErrorMessage());
        }
        return ResponseEntity.ok(userService.registerNewUser(dto));
    }

    @PostMapping(path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AutheticationRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PostMapping(path = "/logout",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> logout(@RequestBody UserDTO userDTO) {
        userService.logout(userDTO);
        return ResponseEntity.ok().build();
    }
}
