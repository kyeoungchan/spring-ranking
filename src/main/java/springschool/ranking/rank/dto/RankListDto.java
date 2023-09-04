package springschool.ranking.rank.dto;

import lombok.Data;
import springschool.ranking.rank.Policy;

import java.util.ArrayList;
import java.util.List;

@Data
public class RankListDto {

    private Policy policy;
    private int count;
    private List<RankElementDto> rankList = new ArrayList<>();

    @Data
    public static class RankElementDto {

        private final Long studentId;
        private final String studentName;
        private final String teacherName;
        private final int rank; // 등수!
    }

}
