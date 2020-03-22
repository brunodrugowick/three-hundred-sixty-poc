package dev.drugowick.threehundredsixty.domain.repository;

import dev.drugowick.threehundredsixty.domain.entity.BaseQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseQuestionRepository extends JpaRepository<BaseQuestion, Long> {

    List<BaseQuestion> findAllByPosition(String position);
}
