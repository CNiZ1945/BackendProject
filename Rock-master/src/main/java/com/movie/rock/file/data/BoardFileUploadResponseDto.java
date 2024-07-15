package com.movie.rock.file.data;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardFileUploadResponseDto {
    //업로드 파일
    public Long boardFileId;

    public String boardOriginFileName;

    public String boardFilePath;

    public String boardFileType;
    
    //생성자 초기화
    @Builder
    public BoardFileUploadResponseDto(Long boardFileId, String boardOriginFileName, String boardFilePath, String boardFileType) {
        this.boardFileId = boardFileId;
        this.boardOriginFileName = boardOriginFileName;
        this.boardFilePath = boardFilePath;
        this.boardFileType = boardFileType;
    }
    
    //초기화한 생성자에 들어갈 데이터
    public static BoardFileUploadResponseDto fromEntity(BoardFileEntity boardFileEntity) {
        return BoardFileUploadResponseDto.builder()
                .boardFileId(boardFileEntity.getBoardFileId())
                .boardOriginFileName(boardFileEntity.getBoardOriginFileName())
                .boardFilePath(boardFileEntity.getBoardFilePath())
                .boardFileType(boardFileEntity.getBoardFileType())
                .build();
    }
}
