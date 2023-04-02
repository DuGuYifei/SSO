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
public class ListResult<E> {
    @Nullable
    public int count;

    @Nullable
    public List<E> data;

    @Nullable
    public Map<String, Object> meta;
}
