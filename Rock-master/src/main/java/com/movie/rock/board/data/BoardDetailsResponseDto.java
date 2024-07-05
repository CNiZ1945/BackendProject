package com.movie.rock.board.data;

import com.movie.rock.file.data.BoardDetailsFileResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//상세보기
@Getter
@Setter
@NoArgsConstructor
public class BoardDetailsResponseDto {
    //상세보기 정보
    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private int boardViewCount;
    private String createDate; //작성 날짜 BaseTimeEntity랑 변수명이 같아야함
    private String modifieDate; //수정 날짜 BaseTimeEntity랑 변수명이 같아야함

    //파일 list = 여러개의 파일을 uploac할 수있어서
    private List<BoardDetailsFileResponseDto> files;
    
    //생성자 초기화
    @Builder
    public BoardDetailsResponseDto(Long boardId, String boardTitle, String boardContent,
                                   int boardViewCount, String createDate, String modifieDate, List<BoardDetailsFileResponseDto> files) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardViewCount = boardViewCount;
        this.createDate = createDate;
        this.modifieDate = modifieDate;
        this.files = files;
    }
    
    //생성자에 들어갈 데이터
    public static BoardDetailsResponseDto fromEntity(BoardEntity boardEntity) {
        return BoardDetailsResponseDto.builder()
                .boardId(boardEntity.getBoardId())
                .boardTitle(boardEntity.getBoardTitle())
                .boardContent(boardEntity.getBoardContent())
                .boardViewCount(boardEntity.getBoardViewCount())
                .createDate(boardEntity.getCreateDate())
                .modifieDate(boardEntity.getModifieDate())
                .files(boardEntity.getFiles().stream().map(BoardDetailsFileResponseDto::fromEntity).collect(Collectors.toList()))
                .build();
        // boardEntity.getFiles()는 FileEntity 객체들의 리스트인 List<FileEntity>를 반환
        // .stream()을 통해 리스트를 스트림으로 변환하고, .map(BoardDetailsFileResponseDto::formEntity)를 사용하여
        // 각 FileEntity 객체를 BoardDetailsFileResponseDto로 변환
        //.collect(Collectors.toList())를 사용하여 변환된 결과를 List<BoardDetailsFileResponseDto>로 수집

    }


}
