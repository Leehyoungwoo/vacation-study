package com.enjoytrip.comment.controller;

import com.enjoytrip.board.model.service.BoardService;
import com.enjoytrip.comment.model.dto.CommentDto;
import com.enjoytrip.comment.model.dto.CommentUpdateDto;
import com.enjoytrip.comment.model.service.CommentService;
import com.enjoytrip.commentLike.model.dto.CommentLikeDto;
import com.enjoytrip.commentLike.model.service.CommentLikeService;
import com.enjoytrip.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.enjoytrip.response.StatusEnum;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/board/{boardId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;
    private final CommentLikeService commentLikeService;

    @PostMapping()
    public ResponseEntity<ResponseMessage> writeComment(@Valid @RequestBody CommentDto writeCommentDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            commentService.writeComment(writeCommentDto);
            responseMessage.setStatus(StatusEnum.OK);
            responseMessage.setMessage("댓글이 작성되었습니다.");
        } catch (IllegalArgumentException e) {
            responseMessage.setStatus(StatusEnum.FAIL);
            responseMessage.setMessage(e.getMessage());

            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<ResponseMessage> getCommentList(@PathVariable int boardId) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            responseMessage.setStatus(StatusEnum.OK);
            responseMessage.setMessage("댓글을 불러왔습니다.");
            responseMessage.setData("commentList", commentService.getListByBoardId(boardId));
        } catch (NoSuchElementException e) {
            responseMessage.setStatus(StatusEnum.FAIL);
            responseMessage.setMessage(e.getMessage());

            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    // 댓글 수정 기능 프로젝트에서 제외했음
    @PutMapping()
    public ResponseEntity<ResponseMessage> updateComment(@RequestBody CommentUpdateDto updateCommentDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        CommentDto commentDto = commentService.getCommentDto(updateCommentDto.getCommentId());
        if (commentDto == null) {
            responseMessage.setStatus(StatusEnum.FAIL);
            responseMessage.setMessage("댓글이 존재하지 않습니다.");
        } else {
            responseMessage.setStatus(StatusEnum.OK);
            responseMessage.setMessage("댓글이 수정되었습니다.");
            commentService.updateComment(updateCommentDto);
        }

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<ResponseMessage> deleteComment(@PathVariable int commentId) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            commentService.deleteComment(commentId);
            responseMessage.setStatus(StatusEnum.OK);
            responseMessage.setMessage("댓글이 삭제되었습니다");
        } catch (NoSuchElementException e) {
            responseMessage.setStatus(StatusEnum.FAIL);
            responseMessage.setMessage(e.getMessage());

            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(responseMessage, HttpStatus.ACCEPTED);
    }

    @PutMapping("/like")
    public ResponseEntity<ResponseMessage> likeComment(@RequestBody CommentLikeDto commentLikeDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            if (commentLikeService.hasUserLikeComment(commentLikeDto)) {
                commentLikeService.unlikeComment(commentLikeDto);
                responseMessage.setMessage("추천이 취소되었습니다.");
                responseMessage.setStatus(StatusEnum.OK);
            } else {
                commentLikeService.likeComment(commentLikeDto);
                responseMessage.setMessage("추천하였습니다.");
                responseMessage.setStatus(StatusEnum.OK);
            }
        } catch (RuntimeException e) {
            responseMessage.setStatus(StatusEnum.FAIL);
            responseMessage.setMessage(e.getMessage());

            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("{commentId}/likes")
    public ResponseEntity<ResponseMessage> getLikesCount(@PathVariable int commentId) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            responseMessage.setStatus(StatusEnum.OK);
            responseMessage.setMessage("좋아요 개수를 성공적으로 불러왔습니다.");
            responseMessage.setData("commentLikesCount", commentLikeService.getLikesCount(commentId));
        } catch (NoSuchElementException e) {
            responseMessage.setStatus(StatusEnum.FAIL);
            responseMessage.setMessage(e.getMessage());

            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
}
