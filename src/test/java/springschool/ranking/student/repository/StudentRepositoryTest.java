package springschool.ranking.student.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springschool.ranking.member.domain.Member;
import springschool.ranking.student.domain.Student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @Test
    void save() {

        Student student = new Student();

        Member teacher = new Member();
        teacher.createMember("userid", "thispassword", "teacherName");
        student.createStudent("s1", "0100000", teacher);

        studentRepository.save(student);

        assertThat(student.getId()).isEqualTo(1L);
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findAllBySemester() {
    }

    @Test
    void findAllByName() {
    }

    @Test
    void deleteOne() {
    }

    @Test
    void findAllByDto() {
    }
}