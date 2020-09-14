package my.auth.app.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author github.com/saikatroy038
 */
@Getter
@Setter
@AllArgsConstructor
public class TokenDto {
    /**
     * Used to access any API. Has smaller expiry time. <br />
     * When accessToken expires, user will get new accessToken using refreshToken.
     *
     */
    private String accessToken;

    /**
     * Used to get new access token when older access token expires. Has larger expiry time. <br />
     * When refreshToken expires, user need to provide credentials to login again.
     */
    private String refreshToken;
}
