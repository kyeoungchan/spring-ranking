package springschool.ranking.record.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springschool.ranking.Semester;
import springschool.ranking.record.Policy;
import springschool.ranking.record.domain.Record;
import springschool.ranking.record.repository.RecordRepository;
import springschool.ranking.record.service.dto.ScoreInputDto;
import springschool.ranking.record.service.dto.partial.PartialRecordDto;
import springschool.ranking.record.service.dto.partial.PartialRecordListDto;
import springschool.ranking.record.service.policy.RecordPolicyService;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {

    private final StudentRepository studentRepository;
    private final RecordRepository recordRepository;
    private final Map<String, RecordPolicyService> rankPolicyServiceMap;
    private ThreadLocal<String> policyHolder = new ThreadLocal<>();
    private ThreadLocal<Semester> semesterHolder = new ThreadLocal<>();

    /**
     * 디폴트 정책은 ScoreRankPolicy 로 지정한다.
     */
    @PostConstruct
    private void initHolders() {
        policyHolder.set("ScoreRankPolicyService");
        log.info("Init Policy={}", policyHolder.get());

        semesterHolder.set(new Semester(1, 1));
        log.info("Init Semester={}", semesterHolder.get());
    }

    /**
     * 쓰레드 로컬은 요청이 끝나면 꼭 remove() 를 호출해야 한다.
     */
    @PreDestroy
    private void flushPolicyHolder() {
        log.info("Destroying PolicyHolder and SemesterHolder");
        policyHolder.remove();
        semesterHolder.remove();
    }

    /**
     * 해당 학기 학생 석차 조회
     */
    public PartialRecordDto getRankOne(Long studentId) {
        return rankPolicyServiceMap.get(policyHolder).getPartialRecordOne(studentId, semesterHolder.get());
    }

    /**
     * 성적 산출 정책을 선택하는 로직
     */
    public void setRankPolicy(Policy policy) {
        if (policy.equals(Policy.RANK)) {
            policyHolder.set("ScoreRankPolicyService");
        } else if (policy.equals(Policy.GRADE)) {
            policyHolder.set("GradeRankPolicyService");
        } else {
            policyHolder.set("RateRankPolicyService");
        }
        log.info("Current Policy={}", policyHolder.get());
    }

    /**
     * 학생의 점수를 입력하는 로직
     * PutMapping 으로 호출한다.
     */
    @Transactional // readOnly = false 로 변경
    public Record inputRank(ScoreInputDto scoreInputDto) {

        Long studentId = scoreInputDto.getStudentId();
        Semester semester = new Semester(scoreInputDto.getYear(), scoreInputDto.getSemester());

        Student targetStudent = studentRepository.findById(studentId);

        double score = scoreInputDto.getScore();

        // Record 내부에 create 메서드 추가
//        Record record = new Record(score, semester, targetStudent);
        Record record = Record.createRecord(score, semester, targetStudent);

        // rank 입력 로직
        record = rankPolicyServiceMap.get("RankRecordPolicyService").setRecordByPolicy(record);

        // grade 입력 로직
        record = rankPolicyServiceMap.get("GradeRecordPolicyService").setRecordByPolicy(record);

        // rate 입력 로직
        record = rankPolicyServiceMap.get("RateRecordPolicyService").setRecordByPolicy(record);

        return recordRepository.save(record);
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
    public PartialRecordListDto sortRank() {
        return rankPolicyServiceMap.get(policyHolder).sortRecord(semesterHolder.get());
    }

    /**
     * 성적 객체 조회
     */
    public Record findRank(Long id) {
        return recordRepository.findById(id);
    }

    /**
     * 해당 학년 학기, 해당 학생의 랭크 객체 반환
     * Rank 객체는 정책이랑 독립돼있다.
     */
    public Record findRankOneWithSemester(Long studentId) {
        Student student = studentRepository.findById(studentId);
        return recordRepository.findRecordByStudentAndSemester(student, semesterHolder.get());
    }
}
