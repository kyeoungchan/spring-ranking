package springschool.ranking.record.service.dto.partial;

import lombok.Getter;

@Getter
public class PartialRateRecordDto extends PartialRecordDto {

    private final double rate;

    public PartialRateRecordDto(Long studentId, long score, int year, int semester, double rate) {
        super(studentId, score, year, semester);
        this.rate = rate;
    }
}
