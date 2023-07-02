package springschool.ranking.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

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
    public Student findStudent(Long studentId) {
        return studentRepository.findById(studentId);
    }
}
