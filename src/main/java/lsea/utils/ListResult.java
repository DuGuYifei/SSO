package lsea.utils;

import java.util.List;
import java.util.Map;
import lombok.*;
import org.springframework.lang.Nullable;

/**
 * Represents a result containing a list of data, along with metadata and a
 * count of the number of items in the list.
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListResult {

  /**
   * The number of items in the list.
   */
  private int count;

  /**
   * The list of data.
   */
  @Nullable
  private List<Object> data;

  /**
   * Additional metadata associated with the result.
   */
  @Nullable
  private Map<String, Object> meta;
}
