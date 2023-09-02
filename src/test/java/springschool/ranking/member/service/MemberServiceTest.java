package springschool.ranking.member.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springschool.ranking.exception.repository.DuplicatedException;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.domain.MemberUpdateDto;
import springschool.ranking.member.repository.MemberRepository;
import springschool.ranking.member.repository.MemoryMemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Slf4j
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @AfterEach
    void clearStore() {
        if (memberRepository instanceof MemoryMemberRepository) {
            ((MemoryMemberRepository) memberRepository).clearStore();
        }
    }

    @Test
    void registerFind() throws DuplicatedException {

        // given
        Member newMember = new Member();
        newMember.setName("a");
        newMember.setUserId("b");
        newMember.setPassword("c");
        memberService.register(newMember);

        // when
        Member findMember = memberService.findMember(newMember.getId());

        // then
        assertThat(findMember).isEqualTo(newMember);
    }

    @Test
    void registerDuplicated() {
        // given
        Member member1 = new Member();
        member1.setName("a");
        member1.setUserId("b");
        member1.setPassword("c");

        Member member2 = new Member();
        member2.setName("a");
        member2.setUserId("b");
        member2.setPassword("c");

        // when

        // then
        assertThatThrownBy(()->{
            memberService.register(member1);
            memberService.register(member2);
        }).isInstanceOf(DuplicatedException.class);
    }

    @Test
    void login() throws DuplicatedException {
        // given
        Member member = new Member();
        member.setUserId("a");
        member.setPassword("b");
        member.setName("c");
        memberService.register(member);

        // when
        Member loginMember = memberService.login("a", "b");

        // then
        Member findMember = memberService.findMember(member.getId());
        assertThat(loginMember).isEqualTo(findMember);
    }

    @Test
    void edit() throws DuplicatedException {
        // given
        Member member = new Member();
        member.setUserId("a");
        member.setPassword("b");
        member.setName("c");
        memberService.register(member);

        // when
        MemberUpdateDto memberUpdateDto = new MemberUpdateDto();
        memberUpdateDto.setPassword("B");
        memberUpdateDto.setName("C");
        Member edittedMember = memberService.edit(member.getId(), memberUpdateDto);
        log.info("updatedMember={}", edittedMember);

        // then
        assertThat(edittedMember).isEqualTo(member); // 참조에 의한 변경으로 기존 member도 업데이트 됐다.
        assertThat(edittedMember.getId()).isEqualTo(member.getId());
        assertThat(edittedMember.getPassword()).isEqualTo("B");
        assertThat(edittedMember.getName()).isEqualTo("C");
    }
}