package springschool.ranking.record.service.dto.partial;

import lombok.Getter;

@Getter
public class PartialRankRecordDto extends PartialRecordDto {

    private final int rank;

    public PartialRankRecordDto(Long studentId, long score, int year, int semester, int rank) {
        super(studentId, score, year, semester);
        this.rank = rank;
    }
}
