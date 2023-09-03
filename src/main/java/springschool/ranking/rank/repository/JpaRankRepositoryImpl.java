package springschool.ranking.rank.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springschool.ranking.rank.domain.Rank;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaRankRepositoryImpl implements RankRepository {

    private final JpaRankRepository jpaRankRepository;

    @Override
    public void save(Rank rank) {
        jpaRankRepository.save(rank);
    }

    @Override
    public Optional<Rank> findById(Long id) {
        return jpaRankRepository.findById(id);
    }

    @Override
    public void delete(Rank rank) {
        jpaRankRepository.delete(rank);
    }
}
