package springschool.ranking.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springschool.ranking.student.domain.Grade;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.domain.StudentUpdateDto;
import springschool.ranking.student.repository.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public void register(Student student) {
        studentRepository.save(student);
    }

    public Student edit(Long studentId, StudentUpdateDto updateDto) {

        Student updatedMember = studentRepository.findById(studentId);
        updatedMember.setName(updateDto.getName());
        updatedMember.setScore(updateDto.getScore());
        updatedMember.setGrade(Grade.valueOf(updateDto.getGrade()));
        updatedMember.setRate(updateDto.getRate());

        return updatedMember;
    }

    public Student findStudent(Long studentId) {
        return studentRepository.findById(studentId);
    }

    public List<Student> findStudentList() {
        return studentRepository.findAll();
    }
}
