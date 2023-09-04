package springschool.ranking.rank.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 점수 입력을 위한 DTO
 */
@Data
public class RankInputDto {

    private Long studentId;
    private String studentName;

    //    private Semester semester;
    // 학년 학기를 DTO 로는 따로 받는다.

    @Size(min = 1, max = 3)
    private int year; // 학년. 학년은 1 ~ 3학년 중 하나만 선택한다.

    @Size(min = 1, max = 2)
    private int semester; // 학기. 학기는 1, 2학기 중 하나만 선택한다.

    @NotBlank
    private long score; // 점수 입력
}
