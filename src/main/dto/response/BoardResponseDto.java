package DClab.CRUD_Learn.src.main.dto.response;

import DClab.CRUD_Learn.src.main.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponseDto {

    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private String userName;
    private Long userId;

    // Entity -> DTO 변환 생성자
    public static BoardResponseDto from(Board board) {
        return BoardResponseDto.builder()
                .boardId(board.getBoardId())
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .userName(board.getUser().getName())
                .userId(board.getUser().getUserId())
                .build();
    }
}