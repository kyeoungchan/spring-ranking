package springschool.ranking.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springschool.ranking.Semester;
import springschool.ranking.commondto.StudentCheckListDto;
import springschool.ranking.exception.repository.NoSuchIdInDbException;
import springschool.ranking.member.domain.Member;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired StudentRepository studentRepository;

    @Test
    void edit() {
        // given
        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");
        memberService.register(teacher);

        Member checkingMember = memberService.checkMember(teacher.getId());

        // when
        assertThat(checkingMember.getPassword()).isEqualTo("teacherPW");
        assertThat(checkingMember.getName()).isEqualTo("t1");
        memberService.edit(checkingMember.getId(), new MemberUpdateDto("newPW", "t2"));

        // then
        assertThat(checkingMember.getPassword()).isEqualTo("newPW");
        assertThat(checkingMember.getName()).isEqualTo("t2");

    }

    @Test
    void withdraw() {
        // given
        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");
        memberService.register(teacher);

        // when
        memberService.withdraw(teacher.getId());

        // then
        assertThatThrownBy(() -> memberService.checkMember(teacher.getId())).isInstanceOf(NoSuchIdInDbException.class);
    }

    @Test
    @DisplayName("로그인에 실패하면 서비스에서는 Null을 반환한다.")
    void login() {
        // given
        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");
        memberService.register(teacher);

        // when
        Member loginedMember = memberService.login("teacherId", "teacherPW");
        Member noMember = memberService.login("teacherId", "teacherPW!");

        // then
        assertThat(loginedMember).isEqualTo(teacher);
        assertThat(noMember).isNull();

    }

    @Test
    void checkStudentsByMember() {
        // given
        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");
        memberService.register(teacher);

        Student s1 = Student.createStudent("s1", "010", new Semester(1, 1), teacher);
        Student s2 = Student.createStudent("s2", "011", new Semester(1, 1), teacher);
        Student s3 = Student.createStudent("s3", "012", new Semester(1, 1), teacher);

        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);

        // when
        StudentCheckListDto dto = memberService.checkStudentsByMember(teacher);

        // then
        assertThat(dto.getCount()).isEqualTo(3);
        assertThat(dto.getStudents().size()).isEqualTo(3);
        assertThat(dto.getStudents().get(0).getId()).isEqualTo(s1.getId());


    }
}