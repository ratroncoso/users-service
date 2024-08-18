package cl.rtroncoso.users_service.repository;

import cl.rtroncoso.users_service.model.entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhoneRepo extends JpaRepository<Phone, UUID> {
}
