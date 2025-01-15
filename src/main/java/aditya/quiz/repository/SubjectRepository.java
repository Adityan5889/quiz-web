package aditya.quiz.repository;

import aditya.quiz.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject getSubjectBySubjectCode(String subjectCode);
}
