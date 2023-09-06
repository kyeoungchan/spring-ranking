package springschool.ranking.record.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springschool.ranking.record.Policy;
import springschool.ranking.record.service.RecordService;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.service.StudentService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rank")
public class RecordController {

    private final RecordService recordService;
    private final StudentService studentService;

    /**
     * 성적 정책을 선택하는 로직이다.
     */
    @PostMapping("/setting/policy/v1")
    public void selectPolicy(Policy policy) {
        recordService.setRankPolicy(policy);
    }

    /**
     * 학년, 학기를 선택하는 로직이다.
     */
    @PostMapping("/setting/semester/v1")
    public void selectSemester(int year, int semester) {
        recordService.setSemester(year, semester);
    }

    /**
     * @return 학생 한 명의 석차를 담은 객체를 반환한다.
     */
    @GetMapping("/{studentId}/v1")
    RankDto checkRankOneV1(@PathVariable Long studentId) {

        int rank = recordService.getRankOne(studentId);
        Student student = studentService.findStudent(studentId);

        return new RankDto(studentId, student.getName(), rank);
    }

    /**
     * @return 전체 학생들의 석차를 담은 객체 리스트를 반환한다.
     */
    @GetMapping("/rankList/v1")
    RankListDto studentRankListV1() {

        return recordService.sortRank();
    }

    /**
     * 해당 학생 점수 입력
     */
    @PostMapping("/{studentId}/inputRank/v1")
    RankDto studentRankInput
}
