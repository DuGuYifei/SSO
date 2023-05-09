package lsea.utils;

import javax.annotation.Nullable;
import lombok.*;

/**
 * Represents a standard error result.
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResult {

  /**
   * The error message.
   */
  @Nullable
  private String message;

  /**
   * The HTTP status code of the response.
   */
  private final boolean success = false;

  /**
   * The HTTP status code of the response.
   */
  private int status;
}
