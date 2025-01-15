package aditya.quiz.repository;

import aditya.quiz.entity.Quiz;
import aditya.quiz.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> getByUser(User user, Sort sort);
}
