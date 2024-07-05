package com.movie.rock.board.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardSearchRequestData {
    //검색 DTO
    //글제목, 글내용, 글쓴이 검색조건
    private String boardTitle;
    private String boardContent;

    //초기화
    @Builder
    public BoardSearchRequestData(String boardTitle, String boardContent) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }

    //검색할 떄 쓰는 데이터
    public static BoardSearchRequestData BoardSearchData(String boardTitle, String boardContent) {
        return BoardSearchRequestData.builder()
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .build();
    }

}
