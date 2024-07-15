package com.movie.rock.board.data;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardWriteRequestDto {
    //글 작성할때 게시글 등록 정보 요청

    private String boardTitle;
    private String boardContent;

    public BoardWriteRequestDto(String boardTitle, String boardContent) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }

    @Builder
    public static BoardEntity ofEntity(BoardWriteRequestDto boardWriteRequestDto) {
        return BoardEntity.builder()
                .boardTitle(boardWriteRequestDto.boardTitle)
                .boardContent(boardWriteRequestDto.boardContent)
                .build();
    }

}
