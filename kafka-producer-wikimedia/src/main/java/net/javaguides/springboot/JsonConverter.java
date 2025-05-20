package net.javaguides.springboot;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.javaguides.springboot.dtos.WikiChangeDto;

import java.io.IOException;

public class JsonConverter {
    public static WikiChangeDto convert(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        WikiChangeDto myData = null;
        try {
            myData = objectMapper.readValue(jsonString, WikiChangeDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myData;
    }
}