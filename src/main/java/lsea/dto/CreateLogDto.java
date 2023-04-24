package lsea.dto;


import lombok.*;
import lsea.utils.LogType;

import javax.validation.constraints.*;

/**
 * Data transfer object for creating a new log.
 */
@Data
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class CreateLogDto {

    /**
     * The data of the log entry.
     */
    @NotBlank
    @Size(min = 3, max = 512)
    private String data;

    /**
     * The type of the log entry.
     */
    @NotNull
    @Min(0)
    @Max(3)
    private Integer logType;

}