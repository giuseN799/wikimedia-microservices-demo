package net.javaguides.springboot.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Embeddable;

@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {
    public String uri;

    @JsonProperty("request_id")
    public String requestId;

    // public String id;
    public String dt;
    public String domain;
    public String stream;
    public String topic;
    public Integer partition;
    public Long offset;
}
