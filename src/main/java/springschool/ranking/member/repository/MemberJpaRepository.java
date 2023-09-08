package springschool.ranking.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springschool.ranking.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByUserId(String userId);

    @Query("select m from Member m where m.name like :name")
    List<Member> findMemberByName(@Param("name") String name);

}
