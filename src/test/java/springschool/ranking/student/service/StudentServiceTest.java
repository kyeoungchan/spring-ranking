package springschool.ranking.student.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springschool.ranking.student.domain.Grade;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.MemoryStudentRepository;
import springschool.ranking.student.repository.StudentRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StudentServiceTest {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentService studentService;

    @AfterEach
    void clearStore() {
        if (studentRepository instanceof MemoryStudentRepository) {
            ((MemoryStudentRepository) studentRepository).clearStore();
        }
    }

    @Test
    void register() {

        //given
        Student student = new Student("studentA", 95, Grade.ONE, 80.0);

        //when
        studentService.register(student);
        Student findStudent = studentService.findStudent(student.getId());

        //then
        assertThat(student).isEqualTo(findStudent);
    }

    @Test
    void edit() {

        // given
        Student studentA = new Student("studentA", 85, Grade.THREE, 92.0);
        studentRepository.save(studentA);

        // when
        StudentUpdateDto studentAUpdate = new StudentUpdateDto("studentB", 90, "TWO", 98.5);
        Student updatedStudent = studentService.edit(studentA.getId(), studentAUpdate);

        // then
        assertThat(updatedStudent.getId()).isEqualTo(studentA.getId());
        assertThat(updatedStudent.getName()).isEqualTo("studentB");
    }

}