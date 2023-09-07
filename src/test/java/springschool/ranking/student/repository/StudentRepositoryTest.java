package springschool.ranking.student.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springschool.ranking.Semester;
import springschool.ranking.exception.repository.NoSuchIdInDbException;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.repository.MemberRepository;
import springschool.ranking.student.domain.Student;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void save() {

        Semester semester = new Semester(1, 1);
        Member teacher = Member.createMember("userid", "thispassword", "teacherName");
        Student student = Student.createStudent("s1", "0100000", semester, teacher);

        studentRepository.save(student);

        assertThat(student.getName()).isEqualTo("s1");
        assertThat(student.getPhoneNumber()).isEqualTo("0100000");
        assertThat(student.getTeacher()).isEqualTo(teacher);
    }

    @Test
    void findById() {

        // given
        Semester semester = new Semester(1, 1);
        Member teacher = Member.createMember("userid", "thispassword", "teacherName");
        Student student = Student.createStudent("s1", "0100000", semester, teacher);

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
        Semester semester = new Semester(1, 1);

        Member teacher = Member.createMember("userid", "thispassword", "teacherName");

        Student student1 = Student.createStudent("s1", "0100000", semester, teacher);
        Student student2 = Student.createStudent("s2", "01023000", semester, teacher);

        memberRepository.save(teacher);
        studentRepository.save(student1);
        studentRepository.save(student2);

        // when

        List<Student> students = studentRepository.findAll();

        // then
        assertThat(students.size()).isEqualTo(2);
        assertThat(students.get(0).getName()).isEqualTo("s1");
        assertThat(students).containsExactly(student1, student2);
    }

    @Test
    void findAllBySemester() {

        // given
        Semester semester = new Semester(1, 1);

        Member teacher = Member.createMember("userid", "thispassword", "teacherName");

        Student student1 = Student.createStudent("s1", "0100000", semester, teacher);
        Student student2 = Student.createStudent("s2", "01023000", semester, teacher);

        memberRepository.save(teacher);
        studentRepository.save(student1);
        studentRepository.save(student2);

        Semester semester2 = new Semester(1, 2);

        Student student3 = Student.createStudent("s3", "01000000", semester2, teacher);
        studentRepository.save(student3);

        // when
        List<Student> studentsSemester = studentRepository.findAllBySemester(semester);
        List<Student> studentsSemester2 = studentRepository.findAllBySemester(semester2);

        // then
        assertThat(studentsSemester.size()).isEqualTo(2);
        assertThat(studentsSemester).containsExactly(student1, student2);
        assertThat(studentsSemester.get(0).getCurSemester()).isEqualTo(semester);

        assertThat(studentsSemester2.size()).isEqualTo(1);
        assertThat(studentsSemester2.get(0).getCurSemester()).isEqualTo(semester2);
    }

    @Test
    void findAllByName() {

        // given
        Semester semester = new Semester(1, 1);

        Member teacher = Member.createMember("userid", "thispassword", "teacherName");

        Student student1 = Student.createStudent("s1", "0100000", semester, teacher);
        Student student2 = Student.createStudent("s2", "01023000", semester, teacher);
        Student student3 = Student.createStudent("s2", "01022200", semester, teacher);

        memberRepository.save(teacher);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);

        // when
        List<Student> s2Students = studentRepository.findAllByName("s2");

        // then
        assertThat(s2Students.size()).isEqualTo(2);
        assertThat(s2Students).doesNotContain(student1);

    }

    @Test
    @DisplayName("삭제를 한 데이터에서 id로 조회를 시도하면 NoSuchIdInDbException 예외를 던진다.")
    void deleteOne() {

        // given
        Semester semester = new Semester(1, 1);

        Member teacher = Member.createMember("userid", "thispassword", "teacherName");

        Student student1 = Student.createStudent("s1", "0100000", semester, teacher);
        Student student2 = Student.createStudent("s2", "01023000", semester, teacher);

        memberRepository.save(teacher);
        studentRepository.save(student1);
        studentRepository.save(student2);

        // when
        studentRepository.deleteOne(student1.getId());
        List<Student> students = studentRepository.findAll();

        // then
        assertThat(students.size()).isEqualTo(1);
        assertThat(students).doesNotContain(student1);

        studentRepository.deleteOne(student2.getId());
        assertThatThrownBy(() -> studentRepository.findById(student2.getId())).isInstanceOf(NoSuchIdInDbException.class);

    }

    @Test
    void findAllByDto() {

        // given

        // when

        // then

    }
}