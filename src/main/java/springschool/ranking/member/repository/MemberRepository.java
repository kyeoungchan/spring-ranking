package springschool.ranking.member.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springschool.ranking.member.domain.Member;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    public void save(Member member) {
        memberJpaRepository.save(member);
    }

    public Member findById(Long id) {
        return memberJpaRepository.findById(id).orElse(null);
    }

    public Optional<Member> findByUserId(String userId) {
        return memberJpaRepository.findMemberByUserId(userId);
    }

    public void deleteMember(Long id) {
        memberJpaRepository.delete(findById(id));
    }

    public List<Member> findAll() {
        return memberJpaRepository.findAll();
    }

    public List<Member> findMemberByName(String name) {
        return memberJpaRepository.findMemberByName(name);
    }
}
