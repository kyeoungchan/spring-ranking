package springschool.ranking.record.service.dto;

import lombok.Data;

@Data
public class RecordDto {

    // 학생 구별자
    private final Long studentId;

    // 성적 관련 데이터
    private final long score;
    private final int rank;
    private final int grade;
    private final int rate;

    // 학년 학기
    private final int year;
    private final int semester;
}
