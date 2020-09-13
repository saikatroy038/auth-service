package my.auth.app.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.auth.app.wrapper.UserDto;

import javax.persistence.*;
import java.util.Set;

/**
 * @author github.com/saikatroy038
 */
@Setter
@Getter
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Column(unique = true)
    private String username;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String salt;

    private boolean isLocked;

    @ManyToMany
    @JoinTable(
            name = "user_grant_mapping",
            joinColumns = { @JoinColumn(name = "user") },
            inverseJoinColumns = { @JoinColumn(name = "grant") }
    )
    private Set<Grant> grants;

    public User(UserDto userDto) {
        this.username = userDto.getUsername();
        this.email = userDto.getEmail();
        this.isLocked = false;
    }
}
