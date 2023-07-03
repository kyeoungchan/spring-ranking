package springschool.ranking.member.service;

import springschool.ranking.member.domain.Member;

public interface MemberService {

    void register(Member member);

    Member findMember(Long memberId);

    Member login(String userId, String password);

    void logout();
}
