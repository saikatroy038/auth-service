package my.auth.app.service;

import io.jsonwebtoken.Claims;
import my.auth.app.service.util.JwtUtil;
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
}
