package my.auth.app.service;

import my.auth.app.entity.Grant;
import my.auth.app.entity.User;
import my.auth.app.exception.InvalidUsernamePasswordException;
import my.auth.app.repository.GrantRepository;
import my.auth.app.repository.UserRepository;
import my.auth.app.service.util.HashUtil;
import my.auth.app.service.util.JwtUtil;
import my.auth.app.wrapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author github.com/saikatroy038
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GrantRepository grantRepository;

    @Autowired
    private HashUtil hashUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void createUser(UserDto userDto) {
        User user = new User(userDto);

        HashedValue hashedValue = hashUtil.getHash(userDto.getPassword());
        user.setPassword(hashedValue.getHashed());
        user.setSalt(hashedValue.getSalt());

        Set<Grant> grants = new HashSet<>();
        for (Long grantId : userDto.getGrants()) {
            Optional<Grant> optionalGrant = grantRepository.findById(grantId);
            if (!optionalGrant.isPresent()) {
                throw new RuntimeException("Grant id " + grantId + " does not exists");
            }
            grants.add(optionalGrant.get());
        }
        user.setGrants(grants);

        userRepository.save(user);
    }

    @Override
    public TokenDto login(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername());
        if (user == null) {
            throw new InvalidUsernamePasswordException();
        }

        // convert password to hash value and compare
        if (!hashUtil.matchWithHash(user.getSalt(), loginDto.getPassword(), user.getPassword())) {
            throw new InvalidUsernamePasswordException();
        }

        // generate access and refresh token
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        return new TokenDto(accessToken, refreshToken);
    }
}
