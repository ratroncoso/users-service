package cl.rtroncoso.users_service.controller;

import cl.rtroncoso.users_service.entity.User;
import cl.rtroncoso.users_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
    @RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> getUserById() {
        return userService.findAllUsers();
    }

    @GetMapping(path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable UUID id) {
        return userService.findUserById(id);
    }

}
