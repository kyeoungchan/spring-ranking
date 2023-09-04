package springschool.ranking.rank.service.policy;

import springschool.ranking.Semester;
import springschool.ranking.rank.dto.RankListDto;

public interface RankPolicyService {

    /**
     * 해당 학기 학생들을 정렬시킨 결과 반환
     */
    RankListDto sortRank(Semester semester);

    /**
     * 해당 학생의 해당 학기 등수 반환
     */
    int getRankOne(Long studentId, Semester semester);

}
