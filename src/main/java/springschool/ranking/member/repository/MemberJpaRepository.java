package springschool.ranking.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springschool.ranking.member.domain.Member;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByUserId(String userId);
    Optional<Member> findMemberByName(String userId);

}
