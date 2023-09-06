package springschool.ranking.record.service.dto;

import lombok.Data;

@Data
public class RecordDto {

    // 학생 구별자
    private final Long studentId;

    // 성적 관련 데이터
    private final double score;
    private final long rank;
    private final int grade;
    private final double rate;

    // 학년 학기
    private final int year;
    private final int semester;
}
