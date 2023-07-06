package springschool.ranking.member.repository;

import springschool.ranking.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long id);

    Optional<Member> findByUserId(String userId);

    List<Member> findAll();

    void clearStore();

}
