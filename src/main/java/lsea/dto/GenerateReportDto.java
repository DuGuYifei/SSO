package lsea.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.*;

/**
 * Data transfer object for generating a report.
 */
@Data
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class GenerateReportDto {

  /**
   * The number of threads to be used for analysis
   */
  @Min(1)
  @Max(40)
  private int numThreads;

  /**
   * The number of iterations to be used for analysis
   */
  @Min(1)
  @Max(200)
  private int iterations;
}
