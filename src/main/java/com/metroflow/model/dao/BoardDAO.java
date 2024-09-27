package com.metroflow.model.dao;

import com.metroflow.model.dto.Board;
import com.metroflow.model.dto.Recommendation;
import com.metroflow.model.dto.User;
import com.metroflow.model.service.BoardService;
import com.metroflow.model.service.UserService;
import com.metroflow.repository.BoardRepository;
import com.metroflow.repository.RecommendationRepository;
import com.metroflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Transactional
public class BoardDAO {

    private final BoardRepository BOARDREPOSITORY;
    private final BoardService BOARDSERVICE;
    private final UserRepository USERREPOSITORY;
    private final UserService USERSERVICE;
    private final RecommendationRepository RECOMMENDATIONREPOSITORY;

    // 보드 Insert
    public void writeBoard(Board board) {
//        System.out.println("writeBoard 호출됨");
        board.setUser(USERREPOSITORY.findByUserId(board.getUser().getUserId()).get());
        board.setCreatedTime(LocalDateTime.now()); // 현재 시간 설정
        board.setThumbsUp(0L);
        board.setThumbsDown(0L);
        board.setView(0L);
        BOARDREPOSITORY.save(board);
    }

    public void insertRecommendation(Long no) {
        Board board = BOARDSERVICE.getInfo(no); // 해당 게시물의 보드 정보
        User user = USERSERVICE.getUserObject();
        if (RECOMMENDATIONREPOSITORY.findRecommendationByUserAndBoard(user.getUserId(), no).isPresent()) {
            return;
        } else {
            Recommendation rec = new Recommendation();
            rec.setUser(user);
            rec.setBoard(board);
            rec.setThumbsUp(false);
            rec.setThumbsDown(false);
            RECOMMENDATIONREPOSITORY.save(rec);
        }
    }

    public void updateRecommendation(Long no, boolean up, boolean down, boolean priorUp, boolean priorDown) {
        String userId = USERSERVICE.getUserObject().getUserId();
        RECOMMENDATIONREPOSITORY.updateRecommendationByUserAndBoard(up, down, userId, no);
        if (up && priorUp) {
            return;
        } else if (up && priorDown) {
            BOARDREPOSITORY.updateThumbs(1, -1, no);
        } else if (down && priorDown) {
            return;
        } else if (down && priorUp) {
            BOARDREPOSITORY.updateThumbs(-1, 1, no);
        } else if (up) {
            BOARDREPOSITORY.updateThumbs(1, 0, no);
        } else if (down) {
            BOARDREPOSITORY.updateThumbs(0, 1, no);
        } else if (up == false && priorUp) {
            BOARDREPOSITORY.updateThumbs(-1, 0, no);
        } else if (down == false && priorDown) {
            BOARDREPOSITORY.updateThumbs(0, -1, no);
        }
    }
}
