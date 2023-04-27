package springschool.ranking.student;

public class Student {
    private Long id;
    private String name;
    private Integer score;
    private Grade grade;
    private Double rate;

    public Student(Long id, String name, Integer score, Grade grade, Double rate) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.grade = grade;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Integer getScore() {
        return score;
    }

    public Grade getGrade() {
        return grade;
    }

    public Double getRate() {
        return rate;
    }
}
