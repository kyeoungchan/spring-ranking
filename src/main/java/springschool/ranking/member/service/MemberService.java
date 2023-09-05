package springschool.ranking.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springschool.ranking.Retry;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * @throws DuplicatedException 만약 사용자가 입력한 userId가 이미 DB에 존재한다면 에러 발생
     */
    @Transactional
    public void register(Member member) {
        memberRepository.save(member);
    }

    public Member checkMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Transactional
    public void edit(Long memberId, MemberUpdateDto updateDto) {
        Member updatedMember = memberRepository.findById(memberId);
        updatedMember.updateMember(updatedMember.getName(), updatedMember.getPassword());
    }

    /**
     * 회원 탈퇴 로직
     * 회원 정보를 DB 에서 삭제 후 컨트롤러에서 로그아웃 로직을 수행한다.
     */
    @Transactional
    public void withdraw(Long memberId) {
        memberRepository.deleteMember(memberId);
    }

    /**
     * 5번 로그인 시도 후 실패 시 NoSuchUserIdException 발생
     */
    @Retry(5)
    public Member login(String userId, String password) {
        return memberRepository.findByUserId(userId);
    }

}
