package aditya.quiz.service;

import aditya.quiz.config.QuizConfig;
import aditya.quiz.entity.Question;
import aditya.quiz.entity.Quiz;
import aditya.quiz.entity.Subject;
import aditya.quiz.entity.User;
import aditya.quiz.exception.ResourceNotFoundException;
import aditya.quiz.repository.QuestionRepository;
import aditya.quiz.repository.QuizRepository;
import aditya.quiz.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
