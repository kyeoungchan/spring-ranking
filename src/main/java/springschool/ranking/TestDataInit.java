package springschool.ranking;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.repository.MemberRepository;
import springschool.ranking.student.domain.Grade;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

@Profile("local")
@Component
@RequiredArgsConstructor
public class TestDataInit {
    private final MemberRepository memberRepository;
    private final StudentRepository studentRepository;

    /**
     * 테스트용 데이터 추가
     */
//    @PostConstruct
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        Member member = new Member();
        member.setUserId("test");
        member.setPassword("test!");
        member.setName("테스트맨");

        memberRepository.save(member);

        List<Student> students = new ArrayList<>();
        students.add(new Student("studentA", 100, Grade.ONE, 90.0));
        students.add(new Student( "studentB", 70, Grade.FOUR, 65.0));
        students.add(new Student( "studentC", 86, Grade.TWO, 85.0));
        students.add(new Student("studentD", 40, Grade.SIX, 20.0));
        students.add(new Student( "studentE", 75, Grade.THREE, 70.0));

        for (Student student : students) {
            studentRepository.save(student);
        }
    }
}
