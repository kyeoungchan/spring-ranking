package springschool.ranking.student;

import java.util.List;

public interface StudentRepository {
    void save(Student student);

    Student findById(Long studentId);

    List<Student> findAll();
}
