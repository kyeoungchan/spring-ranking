package springschool.ranking.student.repository;

import springschool.ranking.student.domain.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    void save(Student student);

    Optional<Student> findById(Long studentId);

    List<Student> findAll();

    List<Student> findAllByDto(StudentSearch studentSearch);
}
