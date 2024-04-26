package com.elice.boardproject;

import com.elice.boardproject.repository.BoardRepository;
import com.elice.boardproject.repository.CommentRepository;
import com.elice.boardproject.repository.PostRepository;
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
	public DataInit stubDataInit(BoardRepository boardRepository, PostRepository postRepository, CommentRepository commentRepository) {
		return new DataInit(boardRepository, postRepository, commentRepository);
	}

}
