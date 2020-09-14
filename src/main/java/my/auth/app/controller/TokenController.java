package my.auth.app.controller;

import io.jsonwebtoken.Claims;
import my.auth.app.service.TokenService;
import my.auth.app.wrapper.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author github.com/saikatroy038
 */
@RestController
@RequestMapping("token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Claims claims(@RequestHeader("token") String token) {
        return tokenService.getClaims(token);
    }

    @GetMapping(value = "refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public TokenDto refreshToken(@RequestHeader("refresh-token") String token) {
        return tokenService.refreshToken(token);
    }
}
