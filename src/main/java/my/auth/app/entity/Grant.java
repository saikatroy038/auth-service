package my.auth.app.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import my.auth.app.wrapper.AccessType;

import javax.persistence.*;

/**
 * @author github.com/saikatroy038
 */
@Entity
@Setter
@Getter
@EqualsAndHashCode
public class Grant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "grant_id")
    private Long grantId;

    @OneToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @Column(name = "access_type")
    private AccessType accessType;
}
