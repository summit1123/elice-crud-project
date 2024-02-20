package com.elice.boardproject.board.mapper;

import com.elice.boardproject.board.entity.Board;
import com.elice.boardproject.board.entity.BoardPostDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-04T16:53:30+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class BoardMapperImpl implements BoardMapper {

    @Override
    public Board boardPostDtoToBoard(BoardPostDto boardPostDto) {
        if ( boardPostDto == null ) {
            return null;
        }

        Board.BoardBuilder board = Board.builder();

        board.name( boardPostDto.getName() );
        board.description( boardPostDto.getDescription() );

        return board.build();
    }
}
