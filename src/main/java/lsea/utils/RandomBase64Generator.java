package lsea.utils;

import java.security.SecureRandom;
import java.util.Base64;

/* Requirement 2.1 */
/**
 * The RandomBase64Generator class provides methods for generating random base64
 * arrays of bytes.
 */
public class RandomBase64Generator {

  /**
   * The length of the short random base64 encoded array of bytes.
   */
  private static Integer SHORT_SECRET_LENGTH = 32;

  /**
   * The length of the long random base64 encoded array of bytes.
   */
  private static Integer LONG_SECRET_LENGTH = 128;

  /**
   * The random number generator.
   */
  public static SecureRandom random = new SecureRandom();

  /**
   * Generates a random base64 encoded array of bytes of length 128.
   *
   * @return a random base64 encoded array of bytes of length 128.
   */
  public static String generateLong() {
    byte[] bytes = new byte[LONG_SECRET_LENGTH];
    random.nextBytes(bytes);
    return Base64.getEncoder().encodeToString(bytes);
  }

  /**
   * Generates a random base64 encoded array of bytes of length 32.
   *
   * @return a random base64 encoded array of bytes of length 32.
   */
  public static String generateShort() {
    byte[] bytes = new byte[SHORT_SECRET_LENGTH];
    random.nextBytes(bytes);
    return Base64.getEncoder().encodeToString(bytes);
  }

  /**
   * Generates a random base64 encoded array of bytes of length length.
   *
   * @param length the length of the random base64 encoded array of bytes.
   * @return a random base64 encoded array of bytes of length length.
   */
  public static String generateCustom(int length) {
    byte[] bytes = new byte[length];
    random.nextBytes(bytes);
    return Base64.getEncoder().encodeToString(bytes);
  }

  /**
   * Refreshes the random number generator with the given seed.
   *
   * @param seed the seed to refresh the random number generator with.
   */
  public static void refreshRandom(Integer seed) {
    random = new SecureRandom();
    random.setSeed(seed);
  }
}
