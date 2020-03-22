package dev.drugowick.threehundredsixty.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDto {
    private Long id;

    private String title;
    private String description;

    private String evaluation;
    private String example;
    private String improvement;
}
