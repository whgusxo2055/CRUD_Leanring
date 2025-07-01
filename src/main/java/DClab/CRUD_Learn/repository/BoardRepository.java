package DClab.CRUD_Learn.repository;

import DClab.CRUD_Learn.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByBoardTitleContaining(String title);
    List<Board> findByBoardContentContaining(String content);

}
