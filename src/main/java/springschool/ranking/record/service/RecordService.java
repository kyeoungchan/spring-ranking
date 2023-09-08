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
import springschool.ranking.record.service.policy.RecordPolicyConst;
import springschool.ranking.record.service.policy.RecordPolicyService;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;

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

        log.info("스프링 빈 확인 studentRepository={} / recordRepository={} / rankPolicyServiceMap={}",
                studentRepository,
                recordRepository,
                rankPolicyServiceMap);

        policyHolder.set(RecordPolicyConst.rankPolicy);
        log.info("Init Policy={}", policyHolder.get());

        semesterHolder.set(new Semester(1, 1));
        log.info("Init Semester={}", semesterHolder.get());
    }

    /**
     * 쓰레드 로컬은 요청이 끝나면 꼭 remove() 를 호출해야 한다.
     */
    @PreDestroy
    private void flushPolicyHolder() {
        log.info("***Destroying PolicyHolder and SemesterHolder***");
        policyHolder.remove();
        semesterHolder.remove();
    }

    /**
     * 학생의 점수를 입력하는 로직
     * PutMapping 으로 호출한다.
     */
    @Transactional // readOnly = false 로 변경
    public Record inputRecord(ScoreInputDto scoreInputDto) {

        Long studentId = scoreInputDto.getStudentId();
        Semester semester = new Semester(scoreInputDto.getYear(), scoreInputDto.getSemester());

        Student targetStudent = studentRepository.findById(studentId);

        double score = scoreInputDto.getScore();

        // Record 내부에 create 메서드 추가
        Record record = Record.createRecord(score, semester, targetStudent);

        rankPolicyServiceMap.get(RecordPolicyConst.rankPolicy).setRecordByPolicy(record, semester);
        log.info("rank 입력 로직. record.getRank={}, policy={}, 학년={}, 학기={}", record.getRank(), rankPolicyServiceMap.get(RecordPolicyConst.rankPolicy), semester.getYear(), semester.getSemester());

        rankPolicyServiceMap.get(RecordPolicyConst.gradePolicy).setRecordByPolicy(record, semester);
        log.info("grade 입력 로직. record.getGrade={}, policy={}, 학년={}, 학기={}", record.getGrade(), rankPolicyServiceMap.get(RecordPolicyConst.gradePolicy), semester.getYear(), semester.getSemester());

        rankPolicyServiceMap.get(RecordPolicyConst.ratePolicy).setRecordByPolicy(record, semester);
        log.info("rate 입력 로직. record.getRate={}, policy={}, 학년={}, 학기={}", record.getRank(), rankPolicyServiceMap.get(RecordPolicyConst.ratePolicy), semester.getYear(), semester.getSemester());

        log.info("***record 결과*** rank={}, grade={}, rate={}, 학년={}, 학기={}", record.getRank(), record.getGrade(), record.getRate(), semester.getYear(), semester.getSemester());
        return recordRepository.save(record);
    }

    /**
     * 해당 학기 학생 석차 조회
     */
    public PartialRecordDto getRankOneByPolicy(Long studentId) {
        log.info("석차 조회. 학생 id={} / 정책={} / 학년={}, 학기={}", studentId, policyHolder.get(), semesterHolder.get().getYear(), semesterHolder.get().getYear());
        return rankPolicyServiceMap.get(policyHolder.get()).getPartialRecordOne(studentId, semesterHolder.get());
    }

    /**
     * 성적 산출 정책을 선택하는 로직
     */
    public void setRankPolicy(Policy policy) {
        if (policy.equals(Policy.RANK)) {
            policyHolder.set(RecordPolicyConst.rankPolicy);
        } else if (policy.equals(Policy.GRADE)) {
            policyHolder.set(RecordPolicyConst.gradePolicy);
        } else {
            policyHolder.set(RecordPolicyConst.ratePolicy);
        }
        log.info("Current Policy={}", policyHolder.get());
    }

    /**
     * 학기 선택
     * DB에 접근하는 로직이 아니므로 readOnly = true
     */
    public void setSemester(Semester semester) {
        semesterHolder.set(semester);
    }

    /**
     * 해당 학기 성적 순으로 학생들 정렬
     */
    public PartialRecordListDto sortRank() {
        log.info("Record 서비스 sorRank 메서드 policy={}, semester={}", policyHolder.get(), semesterHolder.get());
        return rankPolicyServiceMap.get(policyHolder.get()).sortRecord(semesterHolder.get());
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
