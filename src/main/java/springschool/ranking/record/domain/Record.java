package springschool.ranking.record.domain;

import lombok.Getter;
import springschool.ranking.Semester;
import springschool.ranking.student.domain.Student;

import javax.persistence.*;

@Entity
@Getter
public class Record {

    @Id @GeneratedValue
    @Column(name = "record_id")
    private Long id;

    private Double score; // 점수

    private Long rank; // 등수. update 시 반영
    private Integer grade; // 등급. update 시 반영
    private Double rate; // 백분율. update 시 반영

    @Embedded // 값 타입
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

/*
    public Record(double score, Semester semester, Student student) {
        this.score = score;
        this.semester = semester;
        this.student = student;
    }
*/

    // Record 내부에 create 메서드 추가
    public static Record createRecord(double score, Semester semester, Student student) {
        Record record = new Record();
        record.score = score;
        record.semester = semester;
        record.student = student;
        return record;
    }

    public void updateScore(double score) {
        this.score = score;
    }

    public void updateRank(long rank) {
        this.rank = rank;
    }

    public void updateGrade(int grade) {
        this.grade = grade;
    }

    public void updateRate(double rate) {
        this.rate = rate;
    }


}
