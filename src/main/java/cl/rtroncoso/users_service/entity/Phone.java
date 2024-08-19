package cl.rtroncoso.users_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@Entity
@Table(name = "phone")
public class Phone {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @NotNull
    private String number;
    @NotNull
    private String citycode;
    @NotNull
    private String countrycode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter (AccessLevel.NONE)
    private User user;
}