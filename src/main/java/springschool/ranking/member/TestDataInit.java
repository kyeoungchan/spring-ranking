package springschool.ranking.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import springschool.ranking.exception.DuplicatedException;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.repository.MemberRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {
    private final MemberRepository memberRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() throws DuplicatedException {
        Member member = new Member();
        member.setId(1L);
        member.setUserId("test");
        member.setPassword("test!");
        member.setName("테스트맨");

        memberRepository.save(member);
    }
}
