package springschool.ranking.rank.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springschool.ranking.Semester;
import springschool.ranking.rank.Policy;
import springschool.ranking.rank.domain.Rank;
import springschool.ranking.rank.repository.RankRepository;
import springschool.ranking.rank.service.policy.RankPolicyService;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankService {

    private final StudentRepository studentRepository;
    private final RankRepository rankRepository;
    private final Map<String, RankPolicyService> rankPolicyServiceMap;
    private ThreadLocal<String> policyHolder = new ThreadLocal<>();
    private ThreadLocal<Semester> semesterHolder = new ThreadLocal<>();

    /**
     * 디폴트 정책은 ScoreRankPolicy 로 지정한다.
     */
    @PostConstruct
    private void initPolicy() {
        if (policyHolder.get() == null) {
            policyHolder.set("ScoreRankPolicyService");
        }
        log.info("Init Policy={}", policyHolder.get());

        semesterHolder.set(new Semester(1, 1));
        log.info("Init Semester={}", semesterHolder.get());
    }

    /**
     * 쓰레드 로컬은 요청이 끝나면 꼭 remove() 를 호출해야 한다.
     */
    @PreDestroy
    private void flushPolicyHolder() {
        policyHolder.remove();
    }

    /**
     * 학생의 점수를 입력하는 로직
     */
    @Transactional // readOnly = false 로 변경
    public void inputRank(RankInputDto rankInputDto) {

        Long studentId = rankInputDto.getStudentId();
        Semester semester = new Semester(rankInputDto.getYear(), rankInputDto.getSemester());

        Student targetStudent = studentRepository.findById(studentId);

        long score = rankInputDto.getScore();
        Rank rank = new Rank(score, semester, targetStudent);
        rankRepository.save(rank);
    }

    /**
     * 성적 산출 정책을 선택하는 로직
     */
    public void selectRankPolicy(Policy policy) {
        if (policy.equals(Policy.SCORE)) {
            policyHolder.set("ScoreRankPolicyService");
        } else if (policy.equals(Policy.GRADE)) {
            policyHolder.set("GradeRankPolicyService");
        } else {
            policyHolder.set("RateRankPolicyService");
        }
        log.info("Current Policy={}", policyHolder.get());
    }

    /**
     * 학기 선택
     * DB에 접근하는 로직이 아니므로 readOnly = true
     */
    public void setSemester(int year, int semester) {
        semesterHolder.set(new Semester(year, semester));
    }

    /**
     * 해당 학기 성적 순으로 학생들 정렬
     */
    public RankListDto sortRank() {
        return rankPolicyServiceMap.get(policyHolder).sortRank(semesterHolder.get());
    }

    /**
     * 해당 학기 학생 석차 조회
     */
    public int getRankOne(Long studentId) {
        return rankPolicyServiceMap.get(policyHolder).getRankOne(studentId, semesterHolder.get());
    }
}
