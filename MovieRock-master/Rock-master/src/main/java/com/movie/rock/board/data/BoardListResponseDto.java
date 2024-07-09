package com.movie.rock.board.data;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardListResponseDto {
    //게시글 리스트 response
    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private int boardViewCount;
    private String createDate; //작성 날짜 BaseTimeEntity랑 변수명이 같아야함
    private String modifieDate; //수정 날짜 BaseTimeEntity랑 변수명이 같아야함

    //초기화
    @Builder
    public BoardListResponseDto(Long boardId, String boardTitle, String boardContent,
                                int boardViewCount, String createDate, String modifieDate) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardViewCount = boardViewCount;
        this.createDate = createDate;
        this.modifieDate = modifieDate;
    }

    //초기화된 BoardListResponseDto에 받을 데이터 반환
    public static BoardListResponseDto fromEntity(BoardEntity boardEntity) {
        return BoardListResponseDto.builder()
                .boardId(boardEntity.getBoardId())
                .boardTitle(boardEntity.getBoardTitle())
                .boardContent(boardEntity.getBoardContent())
                .boardViewCount(boardEntity.getBoardViewCount())
                .createDate(boardEntity.getCreateDate()) 
                .modifieDate(boardEntity.getModifieDate())
                .build();
        //createDate,modifieDate는 BoardEntity에 없지만 BoardEntity가 BaseTimeEntity를 상속받기 떄문에 쓸수 있음

    }

}
