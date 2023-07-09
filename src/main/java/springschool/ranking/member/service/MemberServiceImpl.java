package springschool.ranking.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.domain.MemberDto;
import springschool.ranking.member.domain.MemberUpdateDto;
import springschool.ranking.member.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    /**
     * @throws DuplicatedException 만약 사용자가 입력한 userId가 이미 DB에 존재한다면
     */
    @Override
    public void register(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    /**
     * @return null이면 로그인 실패
     */
    @Override
    public Member login(String userId, String password) {

        Optional<Member> findMember = memberRepository.findByUserId(userId);

        if (findMember.isEmpty()) {
            // userId로 존재하는 회원 자체가 없을 수 있다.
            return null;
        } else {
            return memberRepository.findByUserId(userId)
                    .filter(m -> m.getPassword().equals(password))
                    .orElse(null);
        }
    }

    @Override
    public Member edit(Long memberId, MemberUpdateDto updateDto) {
        Member updatedMember = memberRepository.findById(memberId);
        updatedMember.setName(updateDto.getName());
        updatedMember.setPassword(updateDto.getPassword());

        return updatedMember;
    }

}
