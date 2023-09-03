package springschool.ranking.rank.domain;

import lombok.Getter;
import springschool.ranking.Semester;
import springschool.ranking.student.domain.Student;

import javax.persistence.*;

@Entity
@Getter
public class Rank {

    @Id @GeneratedValue
    @Column(name = "rank_id")
    private Long id;

    private long score;
    private int grade;
    private long rate;

    @Embedded // 값 타입
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
}
