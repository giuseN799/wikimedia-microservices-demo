package net.javaguides.springboot.dtos;

import lombok.Data;

@Data
public class NoteRequest {
    private Note note;
    private String uuid;
}
