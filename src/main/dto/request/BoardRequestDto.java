package DClab.CRUD_Learn.src.main.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardRequestDto {
    Long boardID;
    String boardTitle;
    String boardContent;
}
