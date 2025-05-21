package net.javaguides.springboot.entities;


import java.sql.Timestamp;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaguides.springboot.dtos.Meta;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WikiChange {
  private TransactionStatus status;

  // @Embedded
  // private Meta meta;

  @Id
  private String uuid;

  private String schema;

  private Long id;
  private String type;
  private Integer namespace;
  private String title;

  private String titleUrl;

  private String comment;
  private Timestamp timestamp;
  private String userName;
  private Boolean bot;

  private String notifyUrl;

  private Boolean minor;
  private Boolean patrolled;

  private String serverUrl;

  private String serverName;

  private String serverScriptPath;

  private String wiki;

  private String parsedcomment;
}
