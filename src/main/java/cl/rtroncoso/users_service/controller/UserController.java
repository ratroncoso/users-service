package cl.rtroncoso.users_service.controller;

import cl.rtroncoso.users_service.model.dto.RegisterUserDTO;
import cl.rtroncoso.users_service.model.entities.User;
import cl.rtroncoso.users_service.exception.BadRequestException;
import cl.rtroncoso.users_service.service.UserService;
import cl.rtroncoso.users_service.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordUtils passwordUtils;

    @GetMapping("")
    public List<User> getUserById() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) {
        return userService.findUserById(id);
    }

    @PostMapping(path = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody @Valid RegisterUserDTO dto) {

        if (!passwordUtils.validatePassword(dto.getPassword())) {
            throw new BadRequestException(passwordUtils.getPasswordErrorMessage());
        }
        return userService.registerNewUser(dto);
    }

    @PostMapping(path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@PathVariable String email, String password) {
        return userService.validateLogin(email, password);
    }
}
