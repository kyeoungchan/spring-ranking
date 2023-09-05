package springschool.ranking.rank.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springschool.ranking.Semester;
import springschool.ranking.rank.Policy;
import springschool.ranking.rank.domain.Rank;
import springschool.ranking.rank.domain.RankDto;
import springschool.ranking.rank.service.RankInputDto;
import springschool.ranking.rank.service.RankService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rank")
public class RankController {

    private final RankService rankService;

    /**
     * 성적 정책을 선택하는 로직이다.
     */
    @PostMapping("/setting/policy/v1")
    public void setPolicy(Policy policy) {
        rankService.selectRankPolicy(policy);
    }

    /**
     * 학년, 학기를 선택하는 로직이다.
     */
    @PostMapping("/setting/semester/v1")
    public void setSemester(int year, int semester) {
        rankService.setSemester(year, semester);
    }

    /**
     * @return 학생 한 명의 석차를 담은 객체를 반환한다.
     */
    @GetMapping("/{studentId}/v1")
    RankDto checkRankOneV1(@PathVariable Long studentId) {

        int rank = rankService.getRankOne(studentId);

        return new RankDto(studentId, rank.getName(), rank);
    }

    /**
     * @return 전체 학생들의 석차를 담은 객체 리스트를 반환한다.
     */
    @GetMapping("/rankList/v1")
    List<RankDto> studentRankListV1() {
        List<Rank> rankList = rankService.getRankList();

        return rankList.stream()
                .map(r -> new RankDto(r.getId(), r.getName(), r.getRank()))
                .collect(Collectors.toList());
    }
}
