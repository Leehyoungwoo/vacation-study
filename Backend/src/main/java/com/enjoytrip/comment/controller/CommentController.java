package com.enjoytrip.comment.controller;

import com.enjoytrip.comment.dto.CommentWriteDto;
import com.enjoytrip.comment.dto.UpdateCommentDto;
import com.enjoytrip.comment.service.CommentService;
import com.enjoytrip.domain.model.entity.Comment;
import com.enjoytrip.domain.model.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{boardId}/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> getCommentByBoardId(@PathVariable Long boardId) {
        return commentService.getCommentByBoardId(boardId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String writeComment(@PathVariable Long boardId,
                               @RequestBody @Valid CommentWriteDto commentWriteDto,
                               @AuthenticationPrincipal Member member) {
        commentWriteDto.setBoardId(boardId);
        commentService.writeComment(commentWriteDto, member);
        return "댓글이 작성되었습니다.";
    }

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public String updateComment(@PathVariable Long commentId, @RequestBody UpdateCommentDto updateCommentDto) {
        updateCommentDto.setId(commentId);
        commentService.updateComment(updateCommentDto);
        return "댓글이 수정되었습니다.";
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return "댓글이 삭제되었습니다.";
    }
}
