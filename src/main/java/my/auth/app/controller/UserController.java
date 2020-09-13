package my.auth.app.controller;

import my.auth.app.service.UserService;
import my.auth.app.wrapper.LoginDto;
import my.auth.app.wrapper.TokenDto;
import my.auth.app.wrapper.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author github.com/saikatroy038
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TokenDto login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
    }
}
