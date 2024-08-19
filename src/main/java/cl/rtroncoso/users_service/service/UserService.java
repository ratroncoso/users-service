package cl.rtroncoso.users_service.service;

import cl.rtroncoso.users_service.dto.*;
import cl.rtroncoso.users_service.entity.*;
import cl.rtroncoso.users_service.exception.EmailDuplicatedException;
import cl.rtroncoso.users_service.exception.UserNotFoundException;
import cl.rtroncoso.users_service.repository.TokenRepo;
import cl.rtroncoso.users_service.repository.UserRepo;
import cl.rtroncoso.users_service.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private TokenRepo tokenRepo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

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
        userRepo.findByEmail(dto.getEmail())
                .ifPresent(e -> { throw new EmailDuplicatedException("El correo ya se encuentra registrado.", HttpStatus.CONFLICT.value()); });

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setLast_login(LocalDateTime.now());
        user.setActive(Boolean.TRUE);
        user.setRole(Role.USER);

        List<Phone> phoneList = new ArrayList<Phone>();
        for(PhoneDTO phoneDTO : dto.getPhones()) {
            Phone phone = new Phone();
            phone.setNumber(phoneDTO.getNumber());
            phone.setCountrycode(phoneDTO.getCountrycode());
            phone.setCitycode(phoneDTO.getCitycode());

            phoneList.add(phone);
        }
        user.setPhones(phoneList);

        User savedUser = userRepo.save(user);
        String jwtToken = jwtService.generateToken(savedUser);
        saveUserToken(savedUser, jwtToken);
        return savedUser;
    }

    public AuthenticationResponse authenticate(AutheticationRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        user.setLast_login(LocalDateTime.now());
        userRepo.save(user);

        return AuthenticationResponse
                .builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    public void logout(UserDTO userDTO) {
        revokeAllUserTokens(userRepo.findById(UUID.fromString(userDTO.getId())).orElseThrow());
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        user.setTokens(List.of(token));
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepo.findAllValidTokenByUser(String.valueOf(user.getId()));
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }
}
