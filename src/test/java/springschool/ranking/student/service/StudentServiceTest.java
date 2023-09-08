package springschool.ranking.student.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springschool.ranking.Semester;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.repository.MemberRepository;
import springschool.ranking.student.domain.Student;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class StudentServiceTest {

    @Autowired StudentService studentService;
    @Autowired MemberRepository memberRepository;

    @Test
    void registerFindOneFindAll() {
        // givenW
        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");
        memberRepository.save(teacher);

        StudentAddDto studentAddDto = new StudentAddDto("s1", "01102", new Semester(1, 1), teacher.getId(), teacher.getName());

        StudentAddDto studentAddDto2 = new StudentAddDto("s2", "02333102", new Semester(1, 1), teacher.getId(), teacher.getName());

        // when
        Student registerStudent = studentService.register(studentAddDto);
        Student registerStudent2 = studentService.register(studentAddDto2);

        // then
        Student findStudent = studentService.findStudent(registerStudent.getId());
        assertThat(findStudent).isEqualTo(registerStudent);
        List<Student> students = studentService.findStudents();
        assertThat(students).containsExactly(registerStudent, registerStudent2);
        assertThat(studentService.findStudentsByName("s1").get(0)).isEqualTo(registerStudent);

    }

    @Test
    void edit() {
        // given
        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");
        memberRepository.save(teacher);

        StudentAddDto studentAddDto = new StudentAddDto("s1", "01102", new Semester(1, 1), teacher.getId(), teacher.getName());
        Student registerStudent = studentService.register(studentAddDto);

        // when
        StudentUpdateDto dto = new StudentUpdateDto("s2", "010", teacher.getId(), teacher.getName());
        studentService.edit(registerStudent.getId(), dto);

        // then
        assertThat(registerStudent.getName()).isEqualTo("s2");
        assertThat(registerStudent.getPhoneNumber()).isEqualTo("010");

    }
}