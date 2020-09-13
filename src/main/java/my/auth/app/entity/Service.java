package my.auth.app.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author github.com/saikatroy038
 */
@Setter
@Getter
@Entity
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "service_id")
    private Long serviceId;

    @Column(unique = true)
    private String name;
}
