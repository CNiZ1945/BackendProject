package com.movie.rock.admin.service;


//import com.movie.rock.admin.data.AdminMovieListRepository;
import com.movie.rock.admin.data.request.RequestAdminMoiveUpdateDto;
import com.movie.rock.admin.data.response.ResponseMovieDetailsDto;
import com.movie.rock.admin.data.response.ResponseMovieListDto;
import com.movie.rock.admin.data.response.ResponseMovieWriteDto;
import com.movie.rock.member.data.MemberEntity;
import com.movie.rock.movie.data.entity.MovieEntity;
import com.movie.rock.movie.data.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminMovieListService {

//    private final MovieRepository movieRepository;
//
//    //페이징 리스트
//    public Page<ResponseMovieListDto> getAllBoards(Pageable pageable) {
//        Page<MovieEntity> boards = movieRepository.findAll(pageable);
//        List<ResponseMovieListDto> list = boards.getContent().stream()
//                .map(ResponseMovieListDto::fromEntity) //DTO에 fromEntity 객체가없어서 오류뜨는중
//                .collect(Collectors.toList());
//        return new PageImpl<>(list, pageable, boards.getTotalElements());
//    }
//
//    //게시글 등록
//    public ResponseMovieWriteDto write(BoardWriteDto boardDTO) {
//        Board board = BoardWriteDto.ofEntity(boardDTO);
//        Board saveBoard = AdminMovieListRepository.save(board);
//        return ResponseMovieWriteDto.fromEntity(saveBoard); //작성자 이름은 필요없기때문에 제외
//    }
//
//    //게시글 상세보기
//    public ResponseMovieDetailsDto detail(Long BoardId) {
//        Board findBoard = AdminMovieListRepository.findAll().orElseThrow(
//                () = > new ResourceNotFoundException("Board", "Board Id", String.valueOf(boardId))
//        );
//        return ResponseMovieDetailsDto.fromEntity(findBoard);
//    }
//
//    //게시글 수정
//    public ResponseMovieDetailsDto update(Long BoardId, RequestAdminMoiveUpdateDto boardDTO) {
//        Board UpdateBoard = AdminMovieListRepository.findAll(boardId).orElseThrow(() ->
//                new ResourceNotFounException("Board", "Board Id", String.valueOf(boardId))
//        );
//        updateBoard.update(boardDTO.getTitle(), boardDTO.getContent());
//        return ResponseMovieDetailsDto.fromEntity(updateBoard);
//    }
//
//    //게시글 삭제
//    public void delete(Long boardId){
//        AdminMovieListRepository.deleteById(boardId);
//    }

}






