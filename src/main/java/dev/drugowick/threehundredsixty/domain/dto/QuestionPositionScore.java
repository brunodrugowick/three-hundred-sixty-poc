package dev.drugowick.threehundredsixty.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class QuestionPositionScore {

    private int id;
    private String title;
    private String description;
    private String position;
    private Double positionAverageScore;

    private List<String> examples = new ArrayList<>();
    private List<String> improvements = new ArrayList<>();

    public QuestionPositionScore(String title, String description, String position, Double positionAverageScore) {
        this.title = title;
        this.description = description;
        this.position = position;
        this.positionAverageScore = positionAverageScore;
    }
}
