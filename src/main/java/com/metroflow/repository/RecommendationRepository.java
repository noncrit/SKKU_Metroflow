package com.metroflow.repository;

import com.metroflow.model.dto.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    @Query(value = "SELECT rec" +
            " FROM Recommendation rec" +
            " WHERE rec.user.userId = :userId AND rec.board.boardNo = :boardNo")
    // Recommendation 객체 select문(userId와 boardNo를 파라미터로 받음)
    Optional<Recommendation> findRecommendationByUserAndBoard(String userId, Long boardNo);

    @Modifying
    @Query(value = "update Recommendation rec" +
            " set rec.isThumbsUp = :isThumbsUp, rec.isThumbsDown = :isThumbsDown" +
            " where rec.user.userId = :userId and rec.board.boardNo = :boardNo")
    // Recommendation 객체 업데이트문(세션의 isThumbsUp, 세션의 isThumbsDown,
    // 세션의 userId, 해당 보드의 boardNo을 파라미터로 받음)
    void updateRecommendationByUserAndBoard(@Param("isThumbsUp") Boolean isThumbsUp,
                                                           @Param("isThumbsDown") Boolean isThumbsDown,
                                                           @Param("userId") String userId,
                                                           @Param("boardNo") Long boardNo);
}
