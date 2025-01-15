package shahzayb.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import shahzayb.quiz.config.QuizConfig;
import shahzayb.quiz.entity.Question;
import shahzayb.quiz.entity.Quiz;
import shahzayb.quiz.entity.Subject;
import shahzayb.quiz.entity.User;
import shahzayb.quiz.exception.ResourceNotFoundException;
import shahzayb.quiz.repository.QuestionRepository;
import shahzayb.quiz.repository.QuizRepository;
import shahzayb.quiz.repository.SubjectRepository;

import java.util.List;

@Service
public class QuizService {

    private SubjectRepository subjectRepository;
    private QuizRepository quizRepository;
    private QuestionRepository questionRepository;

    private QuizConfig quizConfig;

    @Autowired
    public QuizService(SubjectRepository subjectRepository, QuizRepository quizRepository,
                       QuestionRepository questionRepository, QuizConfig quizConfig) {
        this.subjectRepository = subjectRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.quizConfig = quizConfig;
    }

    public Quiz getQuiz(String subjectCode) {
        Subject subject = subjectRepository.getSubjectBySubjectCode(subjectCode);

        if (subject == null) {
            throw new ResourceNotFoundException("Following resource is not available at the moment");
        }

        List<Question> questions = questionRepository.findRandomQuestionsBySubjectAndSize(
                subject.getId(), quizConfig.getTotalQuestions());

        if (questions.size() < quizConfig.getTotalQuestions()) {
            throw new ResourceNotFoundException("Following resource is not available at the moment");
        }

        Quiz quiz = new Quiz();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        quiz.setUser(user);
        quiz.setSubject(subject);
        quiz.setQuestions(questions);

        return quiz;
    }

    public void saveQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public List<Quiz> getQuizzesByUser(User user) {
        return quizRepository.getByUser(user, Sort.by("instant").descending());
    }
}
