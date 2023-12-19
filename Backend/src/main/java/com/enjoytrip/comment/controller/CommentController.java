package com.enjoytrip.comment.controller;

import com.enjoytrip.comment.dto.CommentReadDto;
import com.enjoytrip.comment.dto.CommentWriteDto;
import com.enjoytrip.comment.dto.UpdateCommentDto;
import com.enjoytrip.comment.service.CommentService;
import com.enjoytrip.domain.model.entity.Comment;
import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{boardId}/comment")
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CommentReadDto> getCommentByBoardId(@PathVariable Long boardId) {
        List<Comment> comments = commentService.getCommentByBoardId(boardId);
        return comments.stream().map(comment -> {
            String nickname = memberService.getNicknameByMember(comment.getMember()); // nickname을 얻습니다.
            return new CommentReadDto(nickname, comment.getContent()); // CommentReadDto를 생성합니다.
        }).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void writeComment(@PathVariable Long boardId,
                               @RequestBody @Valid CommentWriteDto commentWriteDto,
                               @AuthenticationPrincipal Member member) {
        commentWriteDto.setBoardId(boardId);
        commentService.writeComment(commentWriteDto, member);
    }

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@authService.authorizeToUpdateComment(#userDetails.getId(), #commentId)")
    public void updateComment(@PathVariable Long commentId,
                              @RequestBody UpdateCommentDto updateCommentDto,
                              @AuthenticationPrincipal Member userDetails) {
        updateCommentDto.setId(commentId);
        commentService.updateComment(updateCommentDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@authService.authorizeToUpdateComment(#userDetails.getId(), #commentId)")
    public void deleteComment(@PathVariable Long commentId,
                              @AuthenticationPrincipal Member userDetails) {
        commentService.deleteComment(commentId);
    }
}
