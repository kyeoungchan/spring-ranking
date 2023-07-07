package springschool.ranking.rank.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import springschool.ranking.rank.Policy;

@Getter
@AllArgsConstructor
public class RankDto {
    private Long id;
    private String name;
    private int rank;
}
