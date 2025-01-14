package com.studyBoard.board.board.service;

import com.studyBoard.board.board.domain.Board;
import com.studyBoard.board.board.repository.JdbcBoardRepository;
import com.studyBoard.board.board.repository.JpaBoardRepository;
import com.studyBoard.board.post.domain.Post;
import com.studyBoard.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final JdbcBoardRepository jdbcBoardRepository;
    private final JpaBoardRepository jpaBoardRepository;
    private final PostRepository postRepository;


    @Autowired
    public BoardService(JdbcBoardRepository jdbcBoardRepository, JpaBoardRepository jpaBoardRepository, PostRepository postRepository) {
        this.jdbcBoardRepository = jdbcBoardRepository;
        this.jpaBoardRepository = jpaBoardRepository;
        this.postRepository = postRepository;
    }

    /**
     * Create Board
     */
    public void saveBoard(String title, String description) {
        Board board = Board.builder()
                .title(title)
                .description(description)
                .build();
        jpaBoardRepository.save(board);
    }

    /**
     * Find all Boards
     */
    public List<Board> getAllBoards() {
        return jdbcBoardRepository.findAll();
    }

    /**
     * Find Board By id
     */
    public Board getBoardById(Long id) {
        return jdbcBoardRepository.findById(id).orElse(null);
    }

    /**
     * Update Board
     */
    public void updateBoard(Long id, String newTitle, String newDescription) {
        Optional<Board> originBoard = jdbcBoardRepository.findById(id);
        originBoard.ifPresent(board -> {
            board.setTitle(newTitle);
            board.setDescription(newDescription);
            jdbcBoardRepository.update(board);
        });
    }

    /**
     * Delete Board
     */
    public void deleteBoard(Long boardId) {
        List<Post> posts = postRepository.findByBoardId(boardId);
        postRepository.deleteAll(posts);

        jdbcBoardRepository.deleteById(boardId);
    }
}
