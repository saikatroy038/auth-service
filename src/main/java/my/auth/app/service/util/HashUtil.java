package my.auth.app.service.util;

import my.auth.app.wrapper.HashedValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;

/**
 * @author github.com/saikatroy038
 */
@Component
public class HashUtil {

    /**
     * peppers are substrings of randomString of length 20.
     */
    @Value("${pepper.random.value}")
    private String randomString;

    /**
     * List of 50 peppers. Pepper for each value is chosen randomly from this List.
     */
    private List<String> peppers;

    @PostConstruct
    public void initialisePeppers() {
        peppers = new ArrayList<>();
        for (int i = 0; i < randomString.length() - 20; i++) {
            peppers.add(randomString.substring(i, i + 20));
        }
    }

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

        // choose a random pepper from the list of peppers
        String pepper = peppers.get(new Random().nextInt(peppers.size()));
        value += pepper;

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
     * Check if given value can be hashed to given hash using given saltString
     * @param saltString
     * @param value
     * @param hash
     * @return true is possible else false
     */
    public boolean matchWithHash(String saltString, String value, String hash) {
        byte[] salt = DatatypeConverter.parseHexBinary(saltString);
        // generate hash using all peppers and match with given hash
        for (String pepper : peppers) {
            byte[] hashByte = getHash(value + pepper, salt);
            String hashedValue = DatatypeConverter.printHexBinary(hashByte);
            if (hashedValue.equals(hash)) {
                return true;
            }
        }
        return false;
    }
}
