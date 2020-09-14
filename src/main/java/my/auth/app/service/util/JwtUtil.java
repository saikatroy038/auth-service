package my.auth.app.service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import my.auth.app.exception.InvalidTokenException;
import my.auth.app.entity.Grant;
import my.auth.app.entity.User;
import my.auth.app.wrapper.JwtType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author github.com/saikatroy038
 */
@Component
public class JwtUtil {

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 5; // 5 min
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 2; // 2 hrs

    @Value("${jwt.secret}")
    private String secret;

    public String generateAccessToken(User user) {
        return generateJwt(user, JwtType.ACCESS_TOKEN);
    }

    public String generateAccessToken(User user, Claims claims) {
        return buildJwt(user, claims, JwtType.ACCESS_TOKEN);
    }

    public String generateRefreshToken(User user, Claims claims) {
        return buildJwt(user, claims, JwtType.REFRESH_TOKEN);
    }

    public String generateRefreshToken(User user) {
        return generateJwt(user, JwtType.REFRESH_TOKEN);
    }

    private String generateJwt(User user, JwtType jwtType) {
        Map<String, Object> claims = new HashMap<>();
        for (Grant grant : user.getGrants()) {
            claims.put(grant.getService().getName(), grant.getAccessType());
        }
        return buildJwt(user, claims, jwtType);
    }

    public String buildJwt(User user, Map<String, Object> claims, JwtType jwtType) {
        long expiry = 0l;
        if (JwtType.ACCESS_TOKEN.equals(jwtType)) {
            expiry = ACCESS_TOKEN_EXPIRE_TIME;
        } else if (JwtType.REFRESH_TOKEN.equals(jwtType)) {
            expiry = REFRESH_TOKEN_EXPIRE_TIME;
        }

        claims.put("typ", jwtType.name());
        return Jwts.builder().setClaims(claims)
                .setSubject((user != null) ? user.getUsername() : claims.get("sub").toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiry))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Claims extractAllClaims(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            validate(claims);
            return claims;
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    private void validate(Claims claims) {
        Date expiry = claims.getExpiration();
        if (expiry.before(new Date())) {
            throw new InvalidTokenException();
        }
    }
}
