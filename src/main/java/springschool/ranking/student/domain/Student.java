package springschool.ranking.student.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springschool.ranking.Semester;
import springschool.ranking.member.domain.Member;
import springschool.ranking.rank.domain.Rank;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id @GeneratedValue
    @Column(name = "student_id")
    private Long id;
    private String name;
    private String phoneNumber;

    @Embedded // 값 타입
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member teacher;

    /**
     * id, Rank 를 제외하고 생성자를 주입받는다.
     * Rank 는 학기마다 업데이트 되는 것이므로 생성자 주입을 하지는 않는다.
     */
    public Student(String name, String phoneNumber, Semester semester, Member teacher) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.semester = semester;
        matchTeacher(teacher);
    }

    /**
     * 양방향으로 바로 주입하기 위한 private 메서드
     */
    private void matchTeacher(Member teacher) {
        this.teacher = teacher;
        teacher.getStudents().add(this);
    }
}
