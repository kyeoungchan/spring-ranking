package springschool.ranking.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.domain.MemberUpdateDto;
import springschool.ranking.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

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
        return memberRepository.findByUserId(userId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    @Override
    public Member edit(Long memberId, MemberUpdateDto updateDto) {
        Member updatedMember = memberRepository.findById(memberId);
        updatedMember.setName(updateDto.getName());
        updatedMember.setPassword(updateDto.getPassword());

        return updatedMember;
    }

}
