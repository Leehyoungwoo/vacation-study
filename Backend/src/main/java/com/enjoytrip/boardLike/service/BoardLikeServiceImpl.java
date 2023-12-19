package com.enjoytrip.boardLike.service;

import static com.enjoytrip.domain.exception.ExceptionMessage.BOARDLIKE_NOT_FOUND;
import static com.enjoytrip.domain.exception.ExceptionMessage.BOARD_NOT_FOUND;
import static com.enjoytrip.domain.exception.ExceptionMessage.MEMBER_NOT_FOUND;

import com.enjoytrip.board.repository.BoardRepository;
import com.enjoytrip.boardLike.dto.BoardLikeRequestDto;
import com.enjoytrip.boardLike.mapper.BoardLikeMapper;
import com.enjoytrip.boardLike.repository.BoardLikeRepository;
import com.enjoytrip.domain.exception.BoardLikeNotFoundException;
import com.enjoytrip.domain.exception.BoardNotFoundException;
import com.enjoytrip.domain.exception.MemberNotFoundException;
import com.enjoytrip.domain.model.entity.Board;
import com.enjoytrip.domain.model.entity.BoardLike;
import com.enjoytrip.domain.model.entity.BoardLikeId;
import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardLikeServiceImpl implements BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Override
    public Integer getLikeCount(Long boardId) {
        return boardLikeRepository.countByBoardId(boardId);
    }

    @Override
    @Transactional
    public boolean checkLikeStatus(BoardLikeRequestDto requestDto) {
        BoardLikeId boardLikeId = BoardLikeMapper.toBoardLikeId(requestDto);

        return boardLikeRepository.findById(boardLikeId)
                                  .map(BoardLike::isLiked)
                                  .orElse(false);
    }

    @Override
    @Transactional
    public void likeBoard(BoardLikeRequestDto requestDto) {
        // 유효성 검사
        Member member = memberRepository.findById(requestDto.getMemberId())
                                        .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        Board board = boardRepository.findById(requestDto.getBoardId())
                                     .orElseThrow(() -> new BoardNotFoundException(BOARD_NOT_FOUND));

        BoardLikeId boardLikeId = BoardLikeMapper.toBoardLikeId(requestDto);
        BoardLike boardLike = boardLikeRepository.findById(boardLikeId)
                                                 .orElseGet(() -> boardLikeRepository.save(
                                                         BoardLike.builder()
                                                                  .boardLikeId(boardLikeId)
                                                                  .member(member)
                                                                  .board(board)
                                                                  .isLiked(false)
                                                                  .build()
                                                 ));

        boardLike.like();
    }

    @Override
    @Transactional
    public void unlikeBoard(BoardLikeRequestDto requestDto) {
        BoardLikeId boardLikeId = BoardLikeMapper.toBoardLikeId(requestDto);
        BoardLike boardLike = boardLikeRepository.findById(boardLikeId)
                                                 .orElseThrow(
                                                         () -> new BoardLikeNotFoundException(BOARDLIKE_NOT_FOUND));

        boardLike.unlike();
    }
}
