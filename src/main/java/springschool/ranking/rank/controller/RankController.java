package springschool.ranking.rank.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springschool.ranking.rank.domain.Rank;
import springschool.ranking.rank.domain.RankDto;
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
     * @return 학생 한 명의 석차를 담은 객체를 반환한다.
     */
    @GetMapping("/{studentId}/v1")
    RankDto aStudentRankV1(@PathVariable Long studentId) {
        Rank rank = rankService.createRank(studentId);
        return new RankDto(studentId, rank.getName(), rank.getRank());
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
