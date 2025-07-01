package DClab.CRUD_Learn.src.main.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardCreateRequestDto {
    private String boardTitle;
    private String boardContent;
    private Long userId;
}
