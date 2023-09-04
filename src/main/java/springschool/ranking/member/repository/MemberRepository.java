package springschool.ranking.member.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springschool.ranking.exception.repository.NoSuchIdInDbException;
import springschool.ranking.member.domain.Member;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    public void save(Member member) {
        memberJpaRepository.save(member);
    }

    public Member findById(Long id) {
        return findById(id);
    }

    public Member findByUserId(String userId) {
        return memberJpaRepository.findMemberByUserId(userId).orElseThrow(NoSuchIdInDbException::new);
    }

    public List<Member> findAll() {
        return null;
    }

    // 쿼리 DSL 학습 후 구현 예정
    public List<Member> findMemberByName(String name) {
        return null;
    }
}
