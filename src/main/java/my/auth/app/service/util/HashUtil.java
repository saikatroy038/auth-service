package my.auth.app.service.util;

import my.auth.app.wrapper.HashedValue;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * @author github.com/saikatroy038
 */
@Component
public class HashUtil {

    /**
     * Get String hashed value and String salt. <br />
     * Random salt is used to create hash and is returned in HashedValue wrapper along with hashed value.
     *
     * @param value to be hashed
     * @return {hashedValue, salt}
     */
    public HashedValue getHash(String value) {
        // create random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // get Hash
        byte[] hash = getHash(value, salt);

        // convert hash and salt to String
        String hashString = DatatypeConverter.printHexBinary(hash);
        String saltString = DatatypeConverter.printHexBinary(salt);

        HashedValue hashedValue = new HashedValue(hashString, saltString);
        return hashedValue;
    }

    /**
     * Get byte[] hashed value using given byte[] salt.
     * @param value
     * @param salt
     * @return hash
     */
    private byte[] getHash(String value, byte[] salt) {
        // create hash using the salt
        KeySpec spec = new PBEKeySpec(value.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return hash;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error in hashing");
        }
    }

    /**
     * Get String hash using given String salt.
     * @param saltString
     * @param value
     * @return
     */
    public String getValue(String saltString, String value) {
        byte[] salt = DatatypeConverter.parseHexBinary(saltString);
        byte[] hash = getHash(value, salt);
        return DatatypeConverter.printHexBinary(hash);
    }
}
