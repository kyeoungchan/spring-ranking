package springschool.ranking.rank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springschool.ranking.rank.domain.Rank;

public interface JpaRankRepository extends JpaRepository<Rank, Long> {

}
