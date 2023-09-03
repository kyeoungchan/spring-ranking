package springschool.ranking.member.repository.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaMemberRepositoryImpl implements MemberRepository {

    private final JpaMemberRepository jpaMemberRepository;

    @Override
    public void save(Member member) {
        jpaMemberRepository.save(member);
    }

    @Override
    public Member findById(Long id) {
        return findById(id);
    }

    @Override
    public Optional<Member> findByUserId(String userId) {
        return jpaMemberRepository.findMemberByUserId(userId);
    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    // 쿼리 DSL 학습 후 구현 예정
    @Override
    public List<Member> findMemberByName(String name) {
        return null;
    }
}
