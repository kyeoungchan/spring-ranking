package springschool.ranking.student.repository;

import springschool.ranking.student.domain.Student;

import java.util.List;

public interface StudentRepository {
    void save(Student student);

    Student findById(Long studentId);

    List<Student> findAll();
}
