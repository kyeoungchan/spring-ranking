package springschool.ranking.record.service.dto.partial;

import lombok.Getter;

@Getter
public class PartialRankRecordDto extends PartialRecordDto {

    private final long rank;

    public PartialRankRecordDto(Long studentId, double score, int year, int semester, long rank) {
        super(studentId, score, year, semester);
        this.rank = rank;
    }
}
