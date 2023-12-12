package com.enjoytrip.board.service;

import com.enjoytrip.board.dto.BoardReadDto;
import com.enjoytrip.board.dto.BoardWriteDto;
import com.enjoytrip.board.mapper.BoardMapper;
import com.enjoytrip.board.repository.BoardRepository;
import com.enjoytrip.domain.exception.BoardNotFoundException;
import com.enjoytrip.domain.model.entity.Board;
import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void writeBoard(BoardWriteDto boardWriteDto) {
        Member member = memberRepository.findById(boardWriteDto.getMemberId())
                        .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));
        boardRepository.save(BoardMapper.toEntity(boardWriteDto, member));
    }

    @Override
    public BoardReadDto readBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()->new BoardNotFoundException("게시글을 찾을 수 없습니다."));
        return new BoardReadDto(board);
    }
}
