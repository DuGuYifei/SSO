package lsea.utils;

import lombok.*;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListResult {

    @Nullable
    private int count;

    @Nullable
    private List<Object> data;

    @Nullable
    private Map<String, Object> meta;

}
