package DClab.CRUD_Learn.src.main.service;

import DClab.CRUD_Learn.src.main.domain.Board;
import DClab.CRUD_Learn.src.main.dto.request.BoardCreateRequestDto;
import DClab.CRUD_Learn.src.main.dto.request.BoardRequestDto;
import DClab.CRUD_Learn.src.main.dto.response.BoardResponseDto;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    BoardResponseDto createBoard(BoardCreateRequestDto boardRequestDto);
    Board saveBoard(Board board);
    Optional<BoardResponseDto> findBoard(Long boardId);
    List<BoardResponseDto> findAllBoards();
    List<BoardResponseDto> findAllBoardsByTitle(String title);
    List<BoardResponseDto> findAllBoardsByContent(String content);
    Board updateBoard(BoardRequestDto boardUpdateRequestDto);
    void deleteBoard(Long boardId);

}
