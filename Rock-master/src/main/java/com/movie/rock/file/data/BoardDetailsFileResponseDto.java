package com.movie.rock.file.data;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDetailsFileResponseDto {
    //상세보기 파일처리
    public Long boardFileId;
    public String boardOriginFileName;
    public String boardFileType;
    
    //생성자 초기화
    @Builder
    public BoardDetailsFileResponseDto(Long boardFileId, String boardOriginFileName, String boardFileType) {
        this.boardFileId = boardFileId;
        this.boardOriginFileName = boardOriginFileName;
        this.boardFileType = boardFileType;
    }

    //생성자에 입력할 데이터
    public static BoardDetailsFileResponseDto fromEntity(BoardFileEntity boardFileEntity) {
        return BoardDetailsFileResponseDto.builder()
                .boardFileId(boardFileEntity.boardFileId)
                .boardOriginFileName(boardFileEntity.boardOriginFileName)
                .boardFileType(boardFileEntity.boardFileType)
                .build();
    }
}
