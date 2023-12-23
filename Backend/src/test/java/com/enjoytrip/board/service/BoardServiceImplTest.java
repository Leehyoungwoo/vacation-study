package com.enjoytrip.board.service;

import com.enjoytrip.board.dto.BoardReadDto;
import com.enjoytrip.board.dto.BoardUpdateDto;
import com.enjoytrip.board.dto.BoardWriteDto;
import com.enjoytrip.board.repository.BoardRepository;
import com.enjoytrip.domain.model.entity.Board;
import com.enjoytrip.domain.model.entity.Member;
import com.enjoytrip.domain.model.type.Role;
import com.enjoytrip.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BoardServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardServiceImpl boardService;

    @Mock
    private Member member;

    @Mock
    private Board board;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .id(1L)
                .username("test@test.com")
                .password("password123")
                .name("Test Member")
                .nickname("TestNick")
                .role(Role.USER)
                .isDeleted(false)
                .build();

        when(board.getId()).thenReturn(1L);
        when(board.getTitle()).thenReturn("Test Title");
        when(board.getContent()).thenReturn("Test Content");
        when(board.getMember()).thenReturn(member);
        when(board.isDeleted()).thenReturn(false);
    }

    @Test
    void readBoard() {
        // given
        Long boardId = 1L;
        when(boardRepository.findByIdAndIsDeletedFalse(boardId)).thenReturn(Optional.of(board));

        // when
        BoardReadDto foundBoard = boardService.readBoard(boardId);

        // then
        assertEquals("Test Title", foundBoard.getTitle());
        assertEquals("Test Content", foundBoard.getContent());
    }

    @Test
    void getBoardPage() {
        // given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        // Let's assume we have 3 boards.
        List<Board> boards = new ArrayList<>();
        for (long i = 1; i <= 3; i++) {
            Board board = Board.builder()
                    .title("Test Title " + i)
                    .content("Test Content " + i)
                    .member(member)
                    .build();
            boards.add(board);
        }

        Page<Board> boardPage = new PageImpl<>(boards, pageable, boards.size());

        Mockito.when(boardRepository.findByIsDeletedFalse(pageable)).thenReturn(boardPage);

        // when
        Page<BoardReadDto> foundPage = boardService.getBoardPage(pageable);

        // then
        assertEquals(3, foundPage.getTotalElements());
        assertEquals("Test Title 1", foundPage.getContent().get(0).getTitle());
        assertEquals("Test Content 1", foundPage.getContent().get(0).getContent());
    }


    @Test
    void writeBoard() {
        // given
        String title = "Test Title";
        String content = "Test Content";
        Long memberId = 1L;
        Member member = this.member;

        BoardWriteDto writeDto = BoardWriteDto.builder()
                .memberId(memberId)
                .title(title)
                .content(content)
                .build();

        Mockito.when(memberRepository.findByIdAndIsDeletedFalse(memberId))
                .thenReturn(Optional.of(member));

        Mockito.when(boardRepository.save(Mockito.any(Board.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        // when
        boardService.writeBoard(writeDto);

        // then
        Mockito.verify(boardRepository, Mockito.times(1)).save(Mockito.any(Board.class));
    }

    @Test
    void updateBoard() {
        // given
        Long boardId = 1L;
        String updatedTitle = "Updated Title";
        String updatedContent = "Updated Content";

        BoardUpdateDto updateDto = BoardUpdateDto.builder()
                .title(updatedTitle)
                .content(updatedContent)
                .build();

        when(boardRepository.findByIdAndIsDeletedFalse(boardId)).thenReturn(Optional.of(board));

        // when
        boardService.updateBoard(boardId, updateDto);

        // then
        Mockito.verify(board, Mockito.times(1)).update(updateDto);
    }

    @Test
    void deleteBoard() {
        // given
        Long boardId = 1L;
        when(boardRepository.findByIdAndIsDeletedFalse(boardId)).thenReturn(Optional.of(board));

        // when
        boardService.deleteBoard(boardId);

        // then
        Mockito.verify(board, Mockito.times(1)).markAsDeleted();
    }
}