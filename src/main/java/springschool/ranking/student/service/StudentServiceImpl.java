package springschool.ranking.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.domain.StudentUpdateDto;
import springschool.ranking.student.repository.StudentRepository;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void register(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student edit(Long studentId, StudentUpdateDto updateDto) {

        Student updatedMember = studentRepository.findById(studentId);
        updatedMember.setName(updateDto.getName());
        updatedMember.setScore(updateDto.getScore());
        updatedMember.setGrade(updateDto.getGrade());
        updatedMember.setRate(updateDto.getRate());

        return updatedMember;
    }

    @Override
    public Student findStudent(Long studentId) {
        return studentRepository.findById(studentId);
    }

    @Override
    public List<Student> findStudentList() {
        return studentRepository.findAll();
    }
}
