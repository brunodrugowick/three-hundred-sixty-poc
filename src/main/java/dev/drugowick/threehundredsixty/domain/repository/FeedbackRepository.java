package dev.drugowick.threehundredsixty.domain.repository;

import dev.drugowick.threehundredsixty.domain.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findAllByUserUsername(String username);
}
