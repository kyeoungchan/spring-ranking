package springschool.ranking.rank.service;

import springschool.ranking.rank.Policy;
import springschool.ranking.student.domain.Student;

import java.util.List;

public interface RankPolicyService {

    /**
     * 등수를 정렬
     */
    void sortRank();

    /**
     * @return 학생의 등수 반환
     */
    int rank(Long studentId);

}
