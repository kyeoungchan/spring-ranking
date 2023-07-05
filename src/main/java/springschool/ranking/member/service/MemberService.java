package springschool.ranking.member.service;

import springschool.ranking.member.domain.Member;
import springschool.ranking.member.domain.MemberUpdateDto;

public interface MemberService {

    void register(Member member);

    Member findMember(Long memberId);

    Member login(String userId, String password);

    Member edit(Long memberId, MemberUpdateDto updateDto);
}
