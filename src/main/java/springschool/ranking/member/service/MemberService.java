package springschool.ranking.member.service;

import springschool.ranking.member.domain.Member;
import springschool.ranking.member.domain.MemberUpdateDto;
import springschool.ranking.exception.DuplicatedException;

public interface MemberService {

    void register(Member member) throws DuplicatedException;

    Member findMember(Long memberId);

    Member login(String userId, String password);

    Member edit(Long memberId, MemberUpdateDto updateDto);
}
