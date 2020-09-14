package my.auth.app.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author github.com/saikatroy038
 */
@NoArgsConstructor
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class InvalidUsernamePasswordException extends RuntimeException {
}
