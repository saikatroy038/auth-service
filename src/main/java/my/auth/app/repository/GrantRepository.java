package my.auth.app.repository;

import my.auth.app.entity.Grant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author github.com/saikatroy038
 */
public interface GrantRepository extends JpaRepository<Grant, Long> {
}
