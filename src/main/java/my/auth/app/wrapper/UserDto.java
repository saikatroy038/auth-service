package my.auth.app.wrapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author github.com/saikatroy038
 */
@Getter
@Setter
@ToString
public class UserDto {
    private String name;
    private String username;
    private String email;
    private String password;
    private List<Long> grants;
}
