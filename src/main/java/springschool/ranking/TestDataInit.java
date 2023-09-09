package springschool.ranking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.service.MemberService;
import springschool.ranking.record.service.RecordService;
import springschool.ranking.record.service.dto.ScoreInputDto;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.service.StudentAddDto;
import springschool.ranking.student.service.StudentService;

import java.util.ArrayList;
import java.util.List;

@Profile("local")
@Slf4j
@Component
@RequiredArgsConstructor
public class TestDataInit {
    private final MemberService memberService;
    private final StudentService studentService;
    private final RecordService recordService;

    /**
     * 테스트용 데이터 추가
     */
    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        Member teacher = Member.createMember("test", "test!", "교사1");
        Member teacher2 = Member.createMember("test!", "test!!", "교사2");

        memberService.register(teacher);
        memberService.register(teacher2);

        List<Student> students = new ArrayList<>();
        Student s1 = Student.createStudent("s1", "010", new Semester(1, 1), teacher);
        students.add(s1);

        Student s2 = Student.createStudent("s2", "010", new Semester(1, 1), teacher);
        students.add(s2);

        Student s3 = Student.createStudent("s3", "010", new Semester(1, 1), teacher2);
        students.add(s3);

        Student s4 = Student.createStudent("s4", "010", new Semester(1, 1), teacher2);
        students.add(s4);

        List<Student> registered = new ArrayList<>();
        for (Student s : students) {
            Student student = studentService.register(new StudentAddDto(s.getName(), s.getPhoneNumber(), s.getCurSemester(), s.getTeacher().getId(), s.getTeacher().getName()));
            registered.add(student);
        }

        ScoreInputDto scoreInputDto1 = new ScoreInputDto(registered.get(0).getId(), s1.getName(), 1, 1, 100.0);
        recordService.inputRecord(scoreInputDto1);

        ScoreInputDto scoreInputDto2 = new ScoreInputDto(registered.get(1).getId(), s2.getName(), 1, 1, 90.0);
        recordService.inputRecord(scoreInputDto2);

        ScoreInputDto scoreInputDto3 = new ScoreInputDto(registered.get(2).getId(), s3.getName(), 1, 1, 80.0);
        recordService.inputRecord(scoreInputDto3);

        ScoreInputDto scoreInputDto4 = new ScoreInputDto(registered.get(3).getId(), s4.getName(), 1, 1, 85.0);
        recordService.inputRecord(scoreInputDto4);

    }
}
