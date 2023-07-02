package springschool.ranking.student.service;

import springschool.ranking.student.domain.Student;

public interface StudentService {
    void register(Student student);

    Student findStudent(Long studentId);
}
