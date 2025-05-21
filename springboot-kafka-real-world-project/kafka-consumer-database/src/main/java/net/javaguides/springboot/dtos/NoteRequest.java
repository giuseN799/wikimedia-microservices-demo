package net.javaguides.springboot.dtos;

import jakarta.persistence.Embedded;
import lombok.Data;

@Data
public class NoteRequest {
    private String uuid;

    @Embedded
    private Note note;
}


