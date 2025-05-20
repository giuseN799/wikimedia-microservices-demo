package net.javaguides.springboot.dtos;

import lombok.Data;

@Data
public class WikiChangeFull {
  String event;
  WikiChangeDto change;
}
