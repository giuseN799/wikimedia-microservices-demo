package net.javaguides.springboot.entities;


import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaguides.springboot.dtos.Meta;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class WikiChange {

    private TransactionStatus status;
    @Id
    private String uuid; 

    private String schema;

    private Long id;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "meta_id")),
        @AttributeOverride(name = "offset", column = @Column(name = "meta_offset")),
        @AttributeOverride(name = "requestId", column = @Column(name = "meta_request_id"))
    })
    private Meta meta;

    private String type;
    private Integer namespace;    
    private String title;

    private String titleUrl;

    private String comment;
    private Long timestamp;
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
