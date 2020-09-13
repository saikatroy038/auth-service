package my.auth.app.repository;

import my.auth.app.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author github.com/saikatroy038
 */
public interface ServiceRepository extends JpaRepository<Service, Long> {
}
