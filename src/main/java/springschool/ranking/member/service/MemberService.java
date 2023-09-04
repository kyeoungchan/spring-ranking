package springschool.ranking.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.domain.MemberUpdateDto;
import springschool.ranking.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * @throws DuplicatedException 만약 사용자가 입력한 userId가 이미 DB에 존재한다면 에러 발생
     */
    public void register(Member member) {
        memberRepository.save(member);
    }

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    /**
     * 5번 로그인 시도 후 실패 시 NoSuchUserIdException 발생
     */
    @Retry(5)
    public Member login(String userId, String password) {
        return memberRepository.findByUserId(userId);
    }

    public Member edit(Long memberId, MemberUpdateDto updateDto) {
        Member updatedMember = memberRepository.findById(memberId);
        updatedMember.setName(updateDto.getName());
        updatedMember.setPassword(updateDto.getPassword());

        return updatedMember;
    }

}
