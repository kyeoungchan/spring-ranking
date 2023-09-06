package springschool.ranking.record.service.dto.partial;

import lombok.Getter;

@Getter
public class PartialGradeRecordDto extends PartialRecordDto {

    private int grade;

    public PartialGradeRecordDto(Long studentId, double score, int year, int semester, int grade) {
        super(studentId, score, year, semester);
        this.grade = grade;
    }
}
