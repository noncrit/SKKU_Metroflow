package com.metroflow.repository;

import com.metroflow.model.dto.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    @Query(value = "SELECT rec" +
            " FROM Recommendation rec" +
            " WHERE rec.user.userId = :userId AND rec.board.boardNo = :boardNo")
    Optional<Recommendation> findRecommendationByUserAndBoard(String userId, Long boardNo);
}
