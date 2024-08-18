package cl.rtroncoso.users_service.service;

import cl.rtroncoso.users_service.exception.UserNotFoundException;
import cl.rtroncoso.users_service.model.dto.PhoneDTO;
import cl.rtroncoso.users_service.model.dto.RegisterUserDTO;
import cl.rtroncoso.users_service.model.entities.Phone;
import cl.rtroncoso.users_service.model.entities.User;
import cl.rtroncoso.users_service.repository.PhoneRepo;
import cl.rtroncoso.users_service.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PhoneRepo phoneRepo;

    public List<User> findAllUsers(){
        return userRepo.findAll();
    }
    public User findUserById(UUID id) throws UserNotFoundException {
        Optional<User> userOptional = userRepo.findById(id);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("Usuario no encontrado");
        }
        return userOptional.get();
    }

    public User registerNewUser (RegisterUserDTO dto) {

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setCreated(LocalDateTime.now());
        //user.setToken(jwtUtils.generateJwtToken(user.getEmail()));
        user.setLast_login(LocalDateTime.now());
        user.setActive(Boolean.TRUE);

        List<Phone> phoneList = new ArrayList<Phone>();
        for(PhoneDTO phoneDTO : dto.getPhones()) {
            Phone phone = new Phone();
            phone.setNumber(phoneDTO.getNumber());
            phone.setCountrycode(phoneDTO.getCountrycode());
            phone.setCitycode(phoneDTO.getCitycode());

            phoneList.add(phone);
        }
        user.setPhones(phoneList);

        return userRepo.save(user);
    }

    public String validateLogin(String email, String password) {
        User userLogin = new User();
        userLogin.setEmail(email);
        userLogin.setPassword(password);
        Example<User> criteria = Example.of(userLogin);
        Optional<User> userOptional = userRepo.findOne(criteria);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("Usuario no encontrado");
        }

        User user = userOptional.get();
        user.setLast_login(LocalDateTime.now());
        //user.setToken(jwtUtils.generateJwtToken(user.getEmail()));
        userRepo.save(user);

        return user.getToken();
    }
}
