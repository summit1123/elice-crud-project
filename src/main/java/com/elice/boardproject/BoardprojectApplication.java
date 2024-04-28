package com.elice.boardproject;
import com.elice.boardproject.service.BoardService;
import com.elice.boardproject.service.CommentService;
import com.elice.boardproject.service.PostService;
import com.elice.boardproject.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BoardprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardprojectApplication.class, args);
	}

	@Bean
	@Profile("local")
	public DataInit stubDataInit(BoardService boardService, PostService postservice, CommentService commentService, UserService userService) {
		return new DataInit(boardService, postservice, commentService, userService);
	}

}
