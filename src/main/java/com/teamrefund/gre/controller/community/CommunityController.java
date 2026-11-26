package com.teamrefund.gre.controller.community;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api/community")
public class CommunityController {
  @GetMapping("/preview")
  public ResponseEntity<Void> getPreview() {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @GetMapping("/{boardType}/posts")
  public ResponseEntity<Void> listPosts(@PathVariable("boardType") String boardType) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @PostMapping("/{boardType}/posts")
  public ResponseEntity<Void> createPost(
      @PathVariable("boardType") String boardType, @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @GetMapping("/{boardType}/posts/{postId}")
  public ResponseEntity<Void> getPost(
      @PathVariable("boardType") String boardType, @PathVariable("postId") Long postId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @PutMapping("/{boardType}/posts/{postId}")
  public ResponseEntity<Void> updatePost(
      @PathVariable("boardType") String boardType,
      @PathVariable("postId") Long postId,
      @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @DeleteMapping("/{boardType}/posts/{postId}")
  public ResponseEntity<Void> deletePost(
      @PathVariable("boardType") String boardType, @PathVariable("postId") Long postId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @PutMapping("/questions/posts/{postId}/status")
  public ResponseEntity<Void> updateQuestionStatus(
      @PathVariable("postId") Long postId, @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @GetMapping("/{boardType}/posts/{postId}/comments")
  public ResponseEntity<Void> listComments(
      @PathVariable("boardType") String boardType,
      @PathVariable("postId") Long postId,
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "size", defaultValue = "10") Integer size) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @PostMapping("/{boardType}/posts/{postId}/comments")
  public ResponseEntity<Void> createComment(
      @PathVariable("boardType") String boardType,
      @PathVariable("postId") Long postId,
      @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @PutMapping("/comments/{commentId}")
  public ResponseEntity<Void> updateComment(
      @PathVariable("commentId") Long commentId, @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @DeleteMapping("/comments/{commentId}")
  public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }
}
