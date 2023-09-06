package springschool.ranking.record.service.dto.partial;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PartialRecordDto {

    // 학생 구별자
    private Long studentId;

    // 성적 관련 데이터
    private double score;

    // 학년 학기
    private int year;
    private int semester;

}
