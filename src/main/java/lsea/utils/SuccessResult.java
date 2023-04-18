package lsea.utils;

import javax.annotation.Nullable;

import lombok.*;

/**
 * Represents a standard generic success result.
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SuccessResult {
    /**
     * The error message.
     */
    @Nullable
    private String data;

    /**
     * The HTTP status code of the response.
     */
    private final boolean success = true;

    /**
     * The HTTP status code of the response.
     */
    private int status;
}
