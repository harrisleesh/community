package org.kiworkshop.community.comment.dto;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponseDto {
  private Long id;
  private String username;
  private String content;
  private Long parentId;
  private int ordinal;
  private boolean active;
  private List<CommentResponseDto> children;
  private ZonedDateTime createdAt;

  @Builder
  public CommentResponseDto(
      Long id,
      String username,
      String content,
      Long parentId,
      int ordinal,
      boolean active,
      ZonedDateTime createdAt
  ) {
    this.id = id;
    this.username = username;
    this.content = content;
    this.parentId = parentId;
    this.ordinal = ordinal;
    this.active = active;
    this.children = new ArrayList<>();
    this.createdAt = createdAt;
  }

  public void addChild(CommentResponseDto comment) {
    children.add(comment);
  }
}

