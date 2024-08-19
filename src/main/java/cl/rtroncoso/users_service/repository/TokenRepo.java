package cl.rtroncoso.users_service.repository;

import cl.rtroncoso.users_service.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, String> {
    @Query(value = "select t.id, t.token token, t.token_type, t.revoked, t.expired " +
            "from token t " +
            "inner join usuario u " +
            "on u.id = t.user_id " +
            "where u.id = :id " +
            "and (t.expired = false or t.revoked = false)"
            , nativeQuery = true)
    List<Token> findAllValidTokenByUser(String id);

    Optional<Token> findByToken(String token);
}
