package net.javaguides.springboot;

import java.sql.Timestamp;

import org.jetbrains.annotations.NotNull;

import lombok.Data;

@Data
public class WikiKafkaInfo {
  
  @NotNull
  String topic;

  Integer partition;

  Timestamp timestamp;

  Integer offset;
}
