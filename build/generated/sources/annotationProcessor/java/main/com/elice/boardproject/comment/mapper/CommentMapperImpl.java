package com.elice.boardproject.comment.mapper;

import com.elice.boardproject.comment.entity.Comment;
import com.elice.boardproject.comment.entity.CommentDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-05T10:12:41+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment commentDtoToComment(CommentDto commentDto) {
        if ( commentDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setContent( commentDto.getContent() );

        return comment;
    }
}
