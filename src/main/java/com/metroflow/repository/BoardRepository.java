package com.metroflow.repository;

import com.metroflow.model.dto.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional // select문이 아닌 쿼리에서는 사용해야 함
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Modifying // Select문이 아닌 쿼리에서는 사용해야함
    @Query(value = "update Board board" + // JPQL 사용 시 @Query 작성
            " set board.view = board.view + 1" +
            " where board.boardNo = :no")
    void plusView(@Param("no") Long boardNo);

    // 보드 수정
    @Modifying
    @Query(value = "update Board board" +
            " set board.boardText = :boardText, board.stationLine = :stationLine," +
            " board.stationName = :stationName, board.title = :title" +
            " where board.boardNo = :boardNo")
    void updateBoard(@Param("boardText") String boardText, @Param("stationLine") String stationLine,
                     @Param("stationName") String stationName, @Param("title") String title,
                     @Param("boardNo") Long boardNo);
    // 보드의 좋아요 - 1, 싫어요 + 1
    // resultThumbsUp : 추가 될 좋아요
    // resultThumbsDown : 추가 될 싫어요
    // boardNo : 해당 게시물의 번호
    @Modifying
    @Query(value = "update Board board" +
            " set board.thumbsUp = board.thumbsUp + :resultThumbsUp," +
            " board.thumbsDown = board.thumbsDown + :resultThumbsDown" +
            " where board.boardNo = :boardNo")
    void updateThumbs(@Param("resultThumbsUp") int resultThumbsUp,
                      @Param("resultThumbsDown") int resultThumbsDown,
                      @Param("boardNo") Long boardNo);
}
