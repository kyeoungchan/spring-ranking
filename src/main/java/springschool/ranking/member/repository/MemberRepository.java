package springschool.ranking.member.repository;

import springschool.ranking.member.domain.Member;
import springschool.ranking.exception.DuplicatedException;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    void save(Member member) throws DuplicatedException;

    Member findById(Long id);

    Optional<Member> findByUserId(String userId);

    List<Member> findAll();

}
