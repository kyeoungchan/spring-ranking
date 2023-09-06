package springschool.ranking.record.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springschool.ranking.Semester;
import springschool.ranking.student.domain.Student;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record {

    @Id @GeneratedValue
    @Column(name = "rank_id")
    private Long id;

    private long score; // 점수
    private int rank; // 등수
    private int grade; // 등급.
    private double rate; // 백분율.

    @Embedded // 값 타입
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    public void updateScore(long score) {
        this.score = score;
    }

    public void updateRank(int rank) {
        this.rank = rank;
    }

    public void updateGrade(int grade) {
        this.grade = grade;
    }

    public void updateRate(double rate) {
        this.rate = rate;
    }


}
