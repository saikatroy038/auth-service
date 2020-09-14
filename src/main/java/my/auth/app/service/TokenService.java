package my.auth.app.service;

import io.jsonwebtoken.Claims;
import my.auth.app.wrapper.TokenDto;

/**
 * @author github.com/saikatroy038
 */
public interface TokenService {
    Claims getClaims(String jwt);
    TokenDto refreshToken(String token);
}
