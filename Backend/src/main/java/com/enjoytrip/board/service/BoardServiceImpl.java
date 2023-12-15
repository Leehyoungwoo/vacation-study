package com.enjoytrip.board.service;

import com.enjoytrip.board.dto.BoardReadDto;
import com.enjoytrip.board.dto.BoardUpdateDto;
import com.enjoytrip.board.dto.BoardWriteDto;
import com.enjoytrip.board.mapper.BoardMapper;
import com.enjoytrip.board.repository.BoardRepository;
import com.enjoytrip.domain.exception.BoardNotFoundException;
import com.enjoytrip.domain.model.entity.Board;
import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long writeBoard(BoardWriteDto boardWriteDto) {
        Member member = memberRepository.findById(boardWriteDto.getMemberId())
                                        .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));
        Board saveBoard = boardRepository.save(BoardMapper.toEntity(boardWriteDto, member));
        return saveBoard.getId();
    }

    @Override
    public BoardReadDto readBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                                     .orElseThrow(() -> new BoardNotFoundException("게시글을 찾을 수 없습니다."));
        return new BoardReadDto(board);
    }

    @Override
    public Page<BoardReadDto> getBoardPage(Pageable pageable) {
        return boardRepository.findByIsDeletedFalse(pageable)
                              .map(BoardReadDto::new);
    }

    @Override
    @Transactional
    public String updateBoard(Long boardId, BoardUpdateDto updateDto, Long memberId) {
        Board board = boardRepository.findById(boardId)
                                     .orElseThrow(() -> new BoardNotFoundException("게시글을 찾을 수 없습니다."));
        board.update(updateDto);
        return "게시글이 성공적으로 수정되었습니다.";
    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId, Long memberId) {
        Board board = boardRepository.findById(boardId)
                                     .orElseThrow(() -> new BoardNotFoundException("게시글을 찾을 수 없습니다."));
        board.delete();
    }
}
