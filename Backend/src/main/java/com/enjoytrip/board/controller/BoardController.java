package com.enjoytrip.board.controller;

import com.enjoytrip.board.model.dto.BoardListDto;
import com.enjoytrip.board.model.dto.BoardReadDto;
import com.enjoytrip.board.model.dto.BoardUpdateDto;
import com.enjoytrip.board.model.dto.BoardWritingDto;
import com.enjoytrip.board.model.service.BoardService;
import com.enjoytrip.boardLike.model.service.BoardLikeService;
import com.enjoytrip.response.ResponseMessage;
import com.enjoytrip.response.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardLikeService boardLikeService;

    @PostMapping
    public ResponseEntity<ResponseMessage> writeBoard(@Valid @RequestBody BoardWritingDto boardWritingDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            int boardId = boardService.writeBoard(boardWritingDto);
            responseMessage.setStatus(StatusEnum.OK);
            responseMessage.setMessage("게시물이 작성되었습니다.");
            responseMessage.setData("boardId", boardId);

        } catch (IllegalArgumentException e) {
            responseMessage.setStatus(StatusEnum.FAIL);
            responseMessage.setMessage(e.getMessage());

            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ResponseMessage> readBoard(@PathVariable int boardId) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            BoardReadDto boardReadDto = boardService.readBoard(boardId);
            responseMessage.setStatus(StatusEnum.OK);
            responseMessage.setMessage(null);
            responseMessage.setData("boardReadDto", boardReadDto);
        } catch (NoSuchElementException e) {
            responseMessage.setStatus(StatusEnum.FAIL);
            responseMessage.setMessage(e.getMessage());

            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<ResponseMessage> updateBoard(@PathVariable int boardId, @Valid @RequestBody BoardUpdateDto boardUpdateDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            boardService.updateBoard(boardId, boardUpdateDto);
            responseMessage.setStatus(StatusEnum.OK);
            responseMessage.setMessage("게시물이 수정되었습니다.");
        } catch (RuntimeException e) {
            responseMessage.setStatus(StatusEnum.FAIL);
            responseMessage.setMessage(e.getMessage());

            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<ResponseMessage> deleteBoard(@PathVariable int boardId) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            boardService.deleteBoard(boardId);
            responseMessage.setStatus(StatusEnum.OK);
            responseMessage.setMessage("게시물이 삭제되었습니다.");
        } catch (NoSuchElementException e) {
            responseMessage.setStatus(StatusEnum.FAIL);
            responseMessage.setMessage(e.getMessage());
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<ResponseMessage> getBoardList(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "8") int pageSize) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            List<BoardListDto> boardList = boardService.getBoardList(pageNo, pageSize);
            responseMessage.setStatus(StatusEnum.OK);
            responseMessage.setMessage("게시물 목록을 불러왔습니다.");
            responseMessage.setData("boardList", boardList);
        } catch (RuntimeException e) {
            responseMessage.setStatus(StatusEnum.FAIL);
            responseMessage.setMessage(e.getMessage());
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseMessage> searchBoard(@RequestParam String searchType, @RequestParam String keyword,
                                                       @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
                                                       @RequestParam(name = "pageSize", defaultValue = "8") int pageSize) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            List<BoardListDto> searchList = boardService.searchBoard(searchType, keyword, pageNo, pageSize);
            responseMessage.setStatus(StatusEnum.OK);
            responseMessage.setMessage("검색이 성공적으로 수행되었습니다");
            responseMessage.setData("searchList", searchList);
        } catch (RuntimeException e) {
            responseMessage.setStatus(StatusEnum.FAIL);
            responseMessage.setMessage(e.getMessage());
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @PutMapping("/{boardId}/like")
    public ResponseEntity<String> likeBoard(@PathVariable int boardId, @RequestParam String memberId) {

        if (!boardService.existsBoard(boardId)) {
            return new ResponseEntity<>("게시물이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        String authorId = boardService.readBoard(boardId).getNickname();
        if (authorId.equals(memberId)) {
            return new ResponseEntity<>("추천할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        if (boardLikeService.hasUserLikedBoard(memberId, boardId)) {
            boardLikeService.unlikeBoard(boardId, memberId);
            return new ResponseEntity<>("추천을 취소하였습니다.", HttpStatus.OK);
        } else {
            boardLikeService.likeBoard(boardId, memberId);
            return new ResponseEntity<>("추천하였습니다.", HttpStatus.OK);
        }
    }

    @GetMapping("{boardId}/likes")
    public int getLikesCount(@PathVariable int boardId) {
        return boardLikeService.getLikesCount(boardId);
    }
}
