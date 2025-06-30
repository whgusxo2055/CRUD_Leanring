package DClab.CRUD_Learn.service;

import DClab.CRUD_Learn.domain.Board;
import DClab.CRUD_Learn.dto.request.BoardCreateRequestDto;
import DClab.CRUD_Learn.dto.request.BoardRequestDto;
import DClab.CRUD_Learn.dto.response.BoardResponseDto;

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
