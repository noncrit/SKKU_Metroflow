package com.metroflow.model.service;

import com.metroflow.model.dto.Board;
import com.metroflow.model.dto.NoticeBoard;
import com.metroflow.repository.NoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeBoardService {

    private final NoticeBoardRepository NOTICEBOARDREPOSITORY;
    private final BoardService BOARDSERVICE;

    public String modifyBoardText(Long boardNo) {
        Board board = BOARDSERVICE.getInfo(boardNo);
        String boardText = board.getBoardText();
        return boardText.length() < 15 ? boardText : boardText.substring(0, 14) + "..." ;
    }
}
