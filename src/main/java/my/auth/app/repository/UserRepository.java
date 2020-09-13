package my.auth.app.repository;

import my.auth.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author github.com/saikatroy038
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
