package net.javaguides.springboot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ModerationRequest {
  String diffURL;
  String textChange;
  @Id
  String uuid;
  String transactionId;
  String status;
}
