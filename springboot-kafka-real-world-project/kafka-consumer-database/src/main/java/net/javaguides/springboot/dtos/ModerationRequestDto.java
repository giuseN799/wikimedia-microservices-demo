package net.javaguides.springboot.dtos;

import lombok.Data;

@Data
public class ModerationRequestDto {
  String diffURL;
  String textChange;
  String uuid;
  String transactionId;
  String status;

  public ModerationRequestDto(WikiChangeDto changeDto, String uuid){
    this.diffURL = changeDto.notifyUrl;
    this.textChange = changeDto.comment;
    // this.transactionId = changeDto.meta.requestId;
    this.uuid = uuid;
    this.status = "PENDING";
  }
}
