package my.auth.app.service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import my.auth.app.InvalidTokenException;
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

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 1; // 5mins
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 2; // 2 hrs

    @Value("${jwt.secret}")
    private String secret = "test";

    public String generateAccessJwt(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("typ", JwtType.ACCESS_TOKEN.name());

        for (Grant grant : user.getGrants()) {
            claims.put(grant.getService().getName(), grant.getAccessType());
        }

        return Jwts.builder().setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME))
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
