package springschool.ranking.student.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import springschool.ranking.student.domain.Student;

public interface StudentJpaRepository extends JpaRepository<Student, Long> {
}
