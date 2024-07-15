package com.movie.rock.file.controller;

import com.movie.rock.file.data.BoardFileDownloadResponseDto;
import com.movie.rock.file.data.BoardFileUploadResponseDto;
import com.movie.rock.file.service.BoardFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardFileController {
    private final BoardFileService boardFileService;
    
    //업로드
    @PostMapping("/admin/boardUpload/{boardId}")
    public ResponseEntity<List<BoardFileUploadResponseDto>> boardUpload(
            @PathVariable("boardId") Long boardId, @RequestParam("files") List<MultipartFile> files) throws IOException {
        List<BoardFileUploadResponseDto> saveFiles = boardFileService.boardFileUpload(boardId, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveFiles);
    }

    //다운로드
    @GetMapping("/admin/boardDownload")
    public ResponseEntity<Resource> boardDownLoad(
            @RequestParam("boardFileId") Long boardFileId) throws IOException{
        BoardFileDownloadResponseDto downFile = boardFileService.boardDownload(boardFileId);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(downFile.getBoardFileType()))//헤더를 설정 파일형식
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + downFile.getBoardFileName() + "\"")
                .body(new ByteArrayResource(downFile.getContext()));

    }

    //삭제
    @DeleteMapping("admin/boardDelete")
    public ResponseEntity<Long> boardDelete(@RequestParam("boardFileId") Long boardFileId) {
        boardFileService.delete(boardFileId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
