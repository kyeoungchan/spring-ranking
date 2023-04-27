package springschool.ranking.student;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springschool.ranking.AppConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {

    @Test
    void register() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        StudentService studentService = ac.getBean(StudentServiceImpl.class);

        //given
        Student student = new Student(1L, "studentA", 95, Grade.ONE, 80.0);

        //when
        studentService.register(student);
        Student findStudent = studentService.findStudent(1L);

        //then
        assertThat(student).isEqualTo(findStudent);
    }

}