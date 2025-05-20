package net.javaguides.springboot.dtos;

import lombok.Data;


@Data
public class ModerationRequestDto {
  String diffURL;
  String textChange;
  String uuid;
  String transactionId;
  String status;
}
