package springschool.ranking.rank;

import springschool.ranking.student.Student;

import java.util.List;
import java.util.Map;

public interface RankPolicy {

    /**
     * 등수를 정렬
     */
    void sortRank();

    /**
     * @return 학생의 등수 반환
     */
    int rank(Student student);

    /**
     * 전체 학생의 리스트 출력
     */
    void printRankList();

    /**
     * @return 현재 어떤 정책 기준인지 반환
     */
    Policy getPolicy();
}
