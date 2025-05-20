package net.javaguides.springboot;

import java.util.List;

import lombok.Data;
import net.javaguides.springboot.dtos.WikiChangeDto;

@Data
public class WikiChangeFull {
  String event;
  WikiChangeDto change;
}
