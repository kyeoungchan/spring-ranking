package springschool.ranking.student.domain;

import lombok.Getter;
import springschool.ranking.Semester;
import springschool.ranking.member.domain.Member;

import javax.persistence.*;

@Entity
@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
     * Student 를 새로 등록할 때 사용하는 로직이다.
     * Rank 는 학기마다 업데이트 되는 것이므로 생성자 주입을 하지는 않는다.
     */
    public Student createStudent(String name, String phoneNumber, Member teacher) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        matchTeacher(teacher);
        return this;
    }

    /**
     * 양방향으로 바로 주입하기 위한 private 메서드
     */
    private void matchTeacher(Member teacher) {
        this.teacher = teacher;
        teacher.getStudents().add(this);
    }

    /**
     * 회원 정보를 수정하기 위한 로직
     */
    public void updateStudent(String name, String phoneNumber, Member teacher) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        matchTeacher(teacher);
    }
}
