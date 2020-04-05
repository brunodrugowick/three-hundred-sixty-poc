package dev.drugowick.threehundredsixty.controller.util;

import dev.drugowick.threehundredsixty.domain.entity.FeedbackRelationship;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeedbackInput {

    private String evaluatorUsername;
    private FeedbackRelationship relationship;
    private String evaluatedUsername;
}
