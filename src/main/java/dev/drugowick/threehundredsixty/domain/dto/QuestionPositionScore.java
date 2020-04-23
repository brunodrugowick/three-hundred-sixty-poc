package dev.drugowick.threehundredsixty.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class QuestionPositionScore {

    private String title;
    private String description;
    private String position;
    private Double positionAverageScore;
}
