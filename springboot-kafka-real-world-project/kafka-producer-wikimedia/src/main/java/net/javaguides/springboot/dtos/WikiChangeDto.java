package net.javaguides.springboot.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WikiChangeDto {

    @JsonProperty("$schema")
    public String schema;

    // public Meta meta;

    public Long id;
    public String type;
    public Integer namespace;
    public String title;

    @JsonProperty("title_url")
    public String titleUrl;

    public String comment;
    public Long timestamp;
    public String user;
    public Boolean bot;

    @JsonProperty("notify_url")
    public String notifyUrl;

    public Boolean minor;
    public Boolean patrolled;

    @JsonProperty("server_url")
    public String serverUrl;

    @JsonProperty("server_name")
    public String serverName;

    @JsonProperty("server_script_path")
    public String serverScriptPath;

    public String wiki;

    public String parsedcomment;

    public String commenthidden;
  }

@JsonIgnoreProperties(ignoreUnknown = true)
class Meta {
    public String uri;

    @JsonProperty("request_id")
    public String requestId;

    public String id;
    public String dt;
    public String domain;
    public String stream;
    public String topic;
    public Integer partition;
    public Long offset;
}

