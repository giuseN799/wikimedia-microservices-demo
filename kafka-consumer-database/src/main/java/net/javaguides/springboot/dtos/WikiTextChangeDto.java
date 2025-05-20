package net.javaguides.springboot.dtos;

import lombok.Data;

@Data
public class WikiTextChangeDto {
  private String diffURL;
  private String textChange;
  private String domain;
  private String uuid;
  private String transactionId;

  public WikiTextChangeDto(WikiChangeDto changeDto, String uuid){
    this.diffURL = changeDto.notifyUrl;
    this.domain = changeDto.meta.domain;
    this.textChange = changeDto.comment;
    this.transactionId = changeDto.meta.requestId;
    this.uuid = uuid;
  }
}
