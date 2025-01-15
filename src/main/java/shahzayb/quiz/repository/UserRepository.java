package shahzayb.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shahzayb.quiz.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
