package com.alex.common2.database.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class BasicEntity {
  public UUID id;

  public LocalDateTime createdAt;

  public LocalDateTime updatedAt;
}
