package com.latilt.blog.web;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.latilt.blog.domain.posts.Post;
import com.latilt.blog.service.PostService;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

  private final PostService postService;

  @GetMapping("/{id}")
  public String findByPost(@PathVariable Long id, Model model) {
    Post post = postService.findByIdAndStatus(id, PostStatus.Y);
    if(post == null){
      throw new NotFoundException(id + " not found");
    }
    model.addAttribute("post", post);
    return "post/post";
  }

  @GetMapping("/new")
  public String newPost(PostDto postDto) {
    return "post/new";
  }

  @GetMapping("/edit/{id}")
  public String editPost(@PathVariable Long id, Model model) {
    Post post = postService.findByIdAndStatus(id, PostStatus.Y);
    if(post == null){
      throw new NotFoundException(id + " not found");
    }
    PostDto createPost = new PostDto();
    createPost.setTitle(post.getTitle());
    createPost.setCode(post.getCode());
    createPost.setContent(post.getContent());
    createPost.setId(id);
    model.addAttribute("editPost", createPost);
    return "post/edit";
  }

  @PostMapping
  public String createPost(@ModelAttribute @Valid PostDto createPost, BindingResult bindingResult, Model model) {
    if(bindingResult.hasErrors()){
      return "post/new";
    }
    Post post = new Post(createPost.getTitle(),
      createPost.getContent(),
      createPost.getCode(),
      PostStatus.Y);
    Post newPost = postService.createPost(post);
    model.addAttribute("post", newPost);
    return "redirect:/posts/" +  newPost.getId();
  }

  @PostMapping("/{id}/edit")
  public String modifyPost(@PathVariable Long id, @ModelAttribute("editPost") @Valid PostDto createPost, BindingResult bindingResult) {
    if(bindingResult.hasErrors()){
      return "post/edit";
    }
    postService.updatePost(id, new Post(
      createPost.getTitle(),
      createPost.getContent(),
      createPost.getCode(),
      PostStatus.Y
    ));
    return "redirect:/posts/" +  id;
  }

  @PostMapping("{id}/delete")
  public String deletePost(@PathVariable Long id){
    postService.deletePost(id);
    return "redirect:/#/";
  }
}