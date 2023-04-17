package lsea.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomBase64Generator {
    public static String generate() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[128];
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
}
