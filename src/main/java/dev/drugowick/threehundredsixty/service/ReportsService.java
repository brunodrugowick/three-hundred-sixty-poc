package dev.drugowick.threehundredsixty.service;

import dev.drugowick.threehundredsixty.domain.dto.QuestionPositionScore;

import java.util.List;

public interface ReportsService {

    public List<QuestionPositionScore> getUserReport(String username);
}
