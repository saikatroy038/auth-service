package my.auth.app.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author github.com/saikatroy038
 */
@Setter
@Getter
@AllArgsConstructor
public class HashedValue {
    private String hashed;
    private String salt;
}
