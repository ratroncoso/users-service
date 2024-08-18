package cl.rtroncoso.users_service.model.entities;

import cl.rtroncoso.users_service.utils.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "id", "email"
        })
})
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    @Email(regexp = Constants.EMAIL_PATTERN, message = "El email es invalido.")
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String token;

    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime last_login;

    private Boolean active;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private List<Phone> phones;
}
