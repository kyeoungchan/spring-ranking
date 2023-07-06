package springschool.ranking.rank.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springschool.ranking.rank.domain.Rank;
import springschool.ranking.rank.domain.RankDto;
import springschool.ranking.rank.service.RankPolicyService;
import springschool.ranking.rank.service.RankService;
import springschool.ranking.student.domain.Student;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rank")
public class RankController {

    private final RankService rankService;
    private final RankPolicyService rankPolicyService;

    @GetMapping("/{studentId}")
    RankDto aStudentRank(@PathVariable Long studentId) {

        Rank rank = rankService.createRank(studentId);
        return new RankDto(studentId, rank.getName(), rank.getRank());
    }

    @GetMapping("/rankList")
    List<RankDto> studentRankList() {
        List<Student> sortedList = rankPolicyService.getSortedList();

        return sortedList.stream().map(s ->
                        new RankDto(s.getId(), s.getName(), rankPolicyService.rank(s)))
                .collect(Collectors.toList());
    }
}
