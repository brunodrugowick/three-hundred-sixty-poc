package dev.drugowick.threehundredsixty.service;

import dev.drugowick.threehundredsixty.domain.entity.Feedback;
import dev.drugowick.threehundredsixty.domain.entity.FeedbackState;
import dev.drugowick.threehundredsixty.domain.entity.Question;
import dev.drugowick.threehundredsixty.domain.repository.BaseQuestionRepository;
import dev.drugowick.threehundredsixty.domain.repository.FeedbackRepository;
import dev.drugowick.threehundredsixty.domain.repository.QuestionRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {


    private BaseQuestionRepository baseQuestionRepository;
    private QuestionRepository questionRepository;
    private FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(BaseQuestionRepository baseQuestionRepository, QuestionRepository questionRepository, FeedbackRepository feedbackRepository) {
        this.baseQuestionRepository = baseQuestionRepository;
        this.questionRepository = questionRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public void generateFeedbackQuestions(Feedback feedback) {
        try {
            baseQuestionRepository.findAllByPosition(feedback.getEvaluated().getPosition()).forEach(baseQuestion -> {
                Question question = new Question(
                        baseQuestion.getPosition(),
                        baseQuestion.getCategory(),
                        baseQuestion.getDescription(),
                        feedback.getEvaluated(),
                        feedback.getEvaluator());
                questionRepository.save(question);
            });
            feedback.setState(FeedbackState.NOT_STARTED);
            feedbackRepository.save(feedback);
        } catch (DataIntegrityViolationException exception) {
            exception.printStackTrace();
            throw new RuntimeException("Erro ao gerar Avaliação. Verifique se a relação entre os dois profissionais já " +
                    "existe. Não é possível modificar uma relação, você deve remover a Avaliação anterior e criar uma nova.");
        }
    }
}
