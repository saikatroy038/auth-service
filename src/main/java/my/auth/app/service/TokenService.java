package my.auth.app.service;

import io.jsonwebtoken.Claims;

/**
 * @author github.com/saikatroy038
 */
public interface TokenService {
    Claims getClaims(String jwt);
}
