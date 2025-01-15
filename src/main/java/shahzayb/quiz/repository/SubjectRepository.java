package shahzayb.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shahzayb.quiz.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject getSubjectBySubjectCode(String subjectCode);
}
