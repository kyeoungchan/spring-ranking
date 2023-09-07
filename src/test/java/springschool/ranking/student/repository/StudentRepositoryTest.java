package springschool.ranking.student.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.repository.MemberRepository;
import springschool.ranking.student.domain.Student;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void save() {

        Member teacher = Member.createMember("userid", "thispassword", "teacherName");
        Student student = Student.createStudent("s1", "0100000", teacher);

        studentRepository.save(student);

        assertThat(student.getId()).isEqualTo(1L);
        assertThat(student.getName()).isEqualTo("s1");
        assertThat(student.getPhoneNumber()).isEqualTo("0100000");
        assertThat(student.getTeacher()).isEqualTo(teacher);
    }

    @Test
    void findById() {

        // given
        Member teacher = Member.createMember("userid", "thispassword", "teacherName");
        Student student = Student.createStudent("s1", "0100000", teacher);

        // when
        studentRepository.save(student);


        // then
        Student findStudent = studentRepository.findById(student.getId());
        assertThat(findStudent).isEqualTo(student);
        assertThat(findStudent.getTeacher()).isEqualTo(teacher);

    }

    @Test
    void findAll() {

        // given
        Student student1 = new Student();
        Student student2 = new Student();

        Member teacher = new Member();
        teacher.createMember("userid", "thispassword", "teacherName");
        student1.createStudent("s1", "0100000", teacher);
        student2.createStudent("s2", "01023000", teacher);

        // when
        memberRepository.save(teacher);
        studentRepository.save(student1);
        studentRepository.save(student2);

        studentRepository.findById(student2.getId());

        List<Student> students = studentRepository.findAll();

        // then
        assertThat(students.size()).isEqualTo(2);

    }

    @Test
    void findAllBySemester() {

        // given

        // when

        // then

    }

    @Test
    void findAllByName() {

        // given

        // when

        // then

    }

    @Test
    void deleteOne() {

        // given

        // when

        // then

    }

    @Test
    void findAllByDto() {

        // given

        // when

        // then

    }
}