package com.latilt.blog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.latilt.blog.domain.posts.Post;
import com.latilt.blog.domain.posts.PostRepository;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	
	public Post createPost(Post post) {
		post.setRegDate(locaDataTime.now());
		return postRepository.save(post);
	}
	
	public Post updatePost(Long id, Post post) {
		Post oldPost = postRepository.findByIdAndStatus(id, PostStatus.Y);
		if(oldPost == null) {
			throw new NotFoundException(id + " not found");
		}
		
		oldPost.setContent(post.getCode());
		oldPost.setCode(post.getCode());
		oldPost.setTitle(post.getTitle());
		return oldPost;
	}
	
	public void deletePost(Long id) {
		Post oldPost = postRepository.findByIdAndStatus(id, PostStatus.Y);
		if(oldPost == null) {
			throw new NotFoundException(id + " not found");
		}
		oldPost.setStatus(PostStatus.N);
	}
	
	public Post findByIdAndStatus(Long id, PostStatus status) {
		Post post = postRepository.findByIdAndStatus(id, status);
		if(post == null) {
			throw new NotFoundException(id + " not found");
		}
		return post;
	}
}
