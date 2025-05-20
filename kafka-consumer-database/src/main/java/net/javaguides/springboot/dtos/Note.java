package net.javaguides.springboot.dtos;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Note {
    private String author;
    private String content;
}