package lk.ijse.gdse68.hotelbookingsystem.repository;

import lk.ijse.gdse68.hotelbookingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existByEmail(String email);
    void deleteByEmail(String email);
    Optional<User> findByEmail(String email);
}
