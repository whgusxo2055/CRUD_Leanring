package DClab.CRUD_Learn.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardRequestDto {
    Long boardID;
    String boardTitle;
    String boardContent;
}
