package aditya.quiz.repository;

import aditya.quiz.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM question q WHERE q.subject = :subjectId ORDER BY RAND() LIMIT :total")
    List<Question> findRandomQuestionsBySubjectAndSize(
            @Param("subjectId") Long subjectId,
            @Param("total") Integer total);
}
