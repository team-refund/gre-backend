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
  // 게시판별 최신 게시글을 미리 조회합니다.
  @GetMapping("/preview")
  public ResponseEntity<Void> getPreview() {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 게시판의 게시글 목록을 조회하거나 검색합니다.
  @GetMapping("/{boardType}/posts")
  public ResponseEntity<Void> listPosts(@PathVariable("boardType") String boardType) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 선택한 게시판에 글을 작성합니다.
  @PostMapping("/{boardType}/posts")
  public ResponseEntity<Void> createPost(
      @PathVariable("boardType") String boardType, @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 게시글 상세 정보를 조회합니다.
  @GetMapping("/{boardType}/posts/{postId}")
  public ResponseEntity<Void> getPost(
      @PathVariable("boardType") String boardType, @PathVariable("postId") Long postId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 본인이 작성한 게시글을 수정합니다.
  @PutMapping("/{boardType}/posts/{postId}")
  public ResponseEntity<Void> updatePost(
      @PathVariable("boardType") String boardType,
      @PathVariable("postId") Long postId,
      @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 본인이 작성한 게시글을 삭제합니다.
  @DeleteMapping("/{boardType}/posts/{postId}")
  public ResponseEntity<Void> deletePost(
      @PathVariable("boardType") String boardType, @PathVariable("postId") Long postId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 질문의 해결 상태와 채택 댓글을 변경합니다.
  @PutMapping("/questions/posts/{postId}/status")
  public ResponseEntity<Void> updateQuestionStatus(
      @PathVariable("postId") Long postId, @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 게시글의 댓글 목록을 조회합니다.
  @GetMapping("/{boardType}/posts/{postId}/comments")
  public ResponseEntity<Void> listComments(
      @PathVariable("boardType") String boardType,
      @PathVariable("postId") Long postId,
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "size", defaultValue = "10") Integer size) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 게시글에 댓글을 작성합니다.
  @PostMapping("/{boardType}/posts/{postId}/comments")
  public ResponseEntity<Void> createComment(
      @PathVariable("boardType") String boardType,
      @PathVariable("postId") Long postId,
      @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 본인이 작성한 댓글을 수정합니다.
  @PutMapping("/comments/{commentId}")
  public ResponseEntity<Void> updateComment(
      @PathVariable("commentId") Long commentId, @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 본인이 작성한 댓글을 삭제합니다.
  @DeleteMapping("/comments/{commentId}")
  public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }
}
