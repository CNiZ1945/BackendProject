package com.movie.rock.board.controller;

import com.movie.rock.board.data.BoardDetailsResponseDto;
import com.movie.rock.board.data.BoardListResponseDto;
import com.movie.rock.board.data.BoardSearchRequestData;
import com.movie.rock.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardUserController {

    private final BoardService boardService;

    //리스트보기 페이지 이동
    @GetMapping("/user/board")
    public String adminBoardPage() {
        return "noticeUser";
    }

    //글작성하기 페이지 이동
    @GetMapping("/user/write")
    public String adminBoardWritePage() {
        return "post_write";
    }

    //글상세페이지 이동
    @GetMapping("/user/{boardId}/Detail")
    public String adminBoardDetailPage() {
        return "post_view_user";
    }

    //리스트보기 회원
    @GetMapping("/user/boardList")
    public ResponseEntity<Page<BoardListResponseDto>> userBoardList (
            @PageableDefault(size = 10, sort = "boardId", direction = Sort.Direction.DESC) Pageable pageable){
        Page<BoardListResponseDto> userListDto = boardService.getAllBoards(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(userListDto);
    }

    //해당 게시글 상세보기(회원)
    @GetMapping("/user/{boardId}")
    public ResponseEntity<BoardDetailsResponseDto> adminBoardDetail(@PathVariable("boardId") Long boardId) {
        BoardDetailsResponseDto boardFind = boardService.boardDetail(boardId);

        return ResponseEntity.status(HttpStatus.OK).body(boardFind);
    }

    //검색
    @GetMapping("/user/boardSearch")
    public ResponseEntity<Page<BoardListResponseDto>> boardSearch(
            @PageableDefault(size = 5, sort = "boardId", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam("boardTitle") String boardTitle,
            @RequestParam("boardContent") String boardContent) {
        BoardSearchRequestData boardSearchData = BoardSearchRequestData.BoardSearchData(boardTitle,boardContent);
        Page<BoardListResponseDto> boardSearch = boardService.boardSearch(boardSearchData, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(boardSearch);
    }
}
