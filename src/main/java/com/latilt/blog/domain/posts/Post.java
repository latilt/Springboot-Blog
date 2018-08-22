package com.latilt.blog.domain.posts;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Post {

	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	private String title;
	
	@Lob
	@NotNull
	private String content;
	
	@Lob
	private String code;
	
	@Enumerated(EnumType.STRING)
	private PostStatus status;
	
	private LocalDataTime regDate;
	
	Post() {
		
	}
}
