package springschool.ranking.rank.repository;

import springschool.ranking.rank.domain.Rank;

import java.util.Optional;

public interface RankRepository {
    void save(Rank rank);
    Optional<Rank> findById(Long id);
    void delete(Rank rank);
}
