package springschool.ranking.student.service;

import springschool.ranking.student.domain.Student;
import springschool.ranking.student.domain.StudentUpdateDto;

import java.util.List;

public interface StudentService {
    void register(Student student);

    Student edit(Long studentId, StudentUpdateDto updateDto);

    Student findStudent(Long studentId);

    List<Student> findStudentList();
}
