package springschool.ranking.student.domain;

import lombok.Data;

@Data
public class Student {
    private Long id;
    private String name;
    private Integer score;
    private Grade grade;
    private Double rate;

    public Student(String name, Integer score, Grade grade, Double rate) {
        this.name = name;
        this.score = score;
        this.grade = grade;
        this.rate = rate;
    }

}
