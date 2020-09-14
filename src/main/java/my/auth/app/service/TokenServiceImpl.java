package my.auth.app.service;

import io.jsonwebtoken.Claims;
import my.auth.app.exception.InvalidTokenException;
import my.auth.app.service.util.JwtUtil;
import my.auth.app.wrapper.JwtType;
import my.auth.app.wrapper.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author github.com/saikatroy038
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Claims getClaims(String jwt) {
        return jwtUtil.extractAllClaims(jwt);
    }

    @Override
    public TokenDto refreshToken(String token) {
        Claims claims = jwtUtil.extractAllClaims(token);

        if (!JwtType.REFRESH_TOKEN.name().equals(claims.get("typ"))) {
            throw new InvalidTokenException();
        }

        String accessToken = jwtUtil.buildJwt(null, claims, JwtType.ACCESS_TOKEN);
        String refreshToken = jwtUtil.buildJwt(null, claims, JwtType.REFRESH_TOKEN);
        return new TokenDto(accessToken, refreshToken);
    }
}
