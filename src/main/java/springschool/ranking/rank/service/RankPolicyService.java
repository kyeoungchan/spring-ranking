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
    int rank(Student student);

    /**
     * 전체 학생의 리스트 출력
     */
    void printRankList();

    /**
     * 전체 학생의 리스트를 반환
     */
    List<Student> getSortedList();

    /**
     * @return 현재 어떤 정책 기준인지 반환
     */
    Policy getPolicy();
}
