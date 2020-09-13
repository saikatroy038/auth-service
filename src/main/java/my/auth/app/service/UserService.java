package my.auth.app.service;

import my.auth.app.wrapper.LoginDto;
import my.auth.app.wrapper.TokenDto;
import my.auth.app.wrapper.UserDto;

/**
 * @author github.com/saikatroy038
 */
public interface UserService {
    void createUser(UserDto userDto);
    TokenDto login(LoginDto loginDto);

    // TODO: logout
    // invalidate jwt on logout by storing them in redis. While validating check if jwt belongs to invalidated jwts.
    // clear jwt from redis on expiry.
}
