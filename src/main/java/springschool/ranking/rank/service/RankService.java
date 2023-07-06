package springschool.ranking.rank.service;

import springschool.ranking.rank.domain.Rank;

import java.util.List;

public interface RankService {
    public Rank createRank(Long id);

    List<Rank> getRankList();
}
