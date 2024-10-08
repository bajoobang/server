package bajoobang.bajoobang_spring.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlusAnswerForm {

    private List<String> answers;
    private List<Integer> fileCounts;
}
