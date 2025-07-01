package DClab.CRUD_Learn.src.main.service;

import DClab.CRUD_Learn.src.main.domain.Board;
import DClab.CRUD_Learn.src.main.domain.User;
import DClab.CRUD_Learn.src.main.dto.request.BoardCreateRequestDto;
import DClab.CRUD_Learn.src.main.dto.request.BoardRequestDto;
import DClab.CRUD_Learn.src.main.dto.response.BoardResponseDto;
import DClab.CRUD_Learn.src.main.repository.BoardRepository;
import DClab.CRUD_Learn.src.main.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 게시글 저장
    @Transactional
    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }

    @Override
    @Transactional
    public BoardResponseDto createBoard(BoardCreateRequestDto RequestDto) {
        // 사용자 조회
        User user = userRepository.findById(RequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + RequestDto.getUserId()));

        // DTO -> Entity 변환
        Board board = Board.builder()
                .boardTitle(RequestDto.getBoardTitle())
                .boardTitle(RequestDto.getBoardContent())
                .user(user)
                .build();

        Board savedBoard = boardRepository.save(board);
        return BoardResponseDto.from(savedBoard);
    }

    // 게시글 단건 조회
    @Override
    public Optional<BoardResponseDto> findBoard(Long boardId) {

        // Repository에서 Board 엔티티 조회
        Optional<Board> boardOptional = boardRepository.findById(boardId);

        // Entity -> DTO 변환 후 Optional로 반환
        return boardOptional.map(BoardResponseDto::from);
    }

    // 모든 게시글 조회 (최신순)
    @Override
    public List<BoardResponseDto> findAllBoards() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        List<Board> boards = boardRepository.findAll(sort);
        return boards.stream().map(BoardResponseDto::from).collect(Collectors.toList());
    }

    @Override
    public List<BoardResponseDto> findAllBoardsByTitle(String title) {
        // 입력 검증
        if (title == null || title.trim().isEmpty()) {
            return Collections.emptyList();
        }

        // Repository에서 Board 엔티티 리스트 조회
        List<Board> boards = boardRepository.findByBoardTitleContaining(title);

        // Entity -> DTO 변환
        return boards.stream()
                .map(BoardResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<BoardResponseDto> findAllBoardsByContent(String content) {
        // 입력 검증
        if (content == null || content.trim().isEmpty()) {
            return Collections.emptyList();
        }

        // Repository에서 Board 엔티티 리스트 조회
        List<Board> boards = boardRepository.findByBoardContentContaining(content);

        // Entity -> DTO 변환
        return boards.stream()
                .map(BoardResponseDto::from)
                .collect(Collectors.toList());
    }


    // 게시글 수정
    @Transactional
    @Override
    public Board updateBoard(BoardRequestDto boardRequestDto) {
        Long boardId=boardRequestDto.getBoardID();
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            board.update(boardRequestDto.getBoardTitle(), boardRequestDto.getBoardContent()); // Board 엔티티에 update 메서드 필요
            return boardRepository.save(board);
        }
        throw new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + boardId);
    }

    // 게시글 삭제
    @Transactional
    public void deleteBoard(Long boardId) {
        if (boardRepository.existsById(boardId)) {
            boardRepository.deleteById(boardId);
        } else {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + boardId);
        }
    }

}
