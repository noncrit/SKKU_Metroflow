package com.metroflow.model.dao;

import com.metroflow.model.dto.Board;
import com.metroflow.repository.BoardRepository;
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
    private final UserRepository USERREPOSITORY;

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
}
