package springschool.ranking.member.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springschool.ranking.exception.repository.NoSuchIdInDbException;
import springschool.ranking.member.domain.Member;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    void findById() {
        // given
        Member member = Member.createMember("!userId1", "!!userPW", "t1");

        // when
        memberRepository.save(member);

        // then
        assertThat(memberRepository.findById(member.getId())).isEqualTo(member);
        assertThatThrownBy(() -> memberRepository.findById(12L)).isInstanceOf(NoSuchIdInDbException.class);
    }

    @Test
    void findByUserId() {
        // given
        Member member1 = Member.createMember("!userId1", "!!userPW", "t1");
        memberRepository.save(member1);
        Member member2 = Member.createMember("@userId2", "@@userPW", "t2");
        memberRepository.save(member2);

        // when
        Optional<Member> findMember1 = memberRepository.findByUserId("!userId1");
        Optional<Member> findMember2 = memberRepository.findByUserId("@userId2");
        Optional<Member> findMember3 = memberRepository.findByUserId("33333");


        // then
        assertThat(findMember1.get()).isEqualTo(member1);
        assertThat(findMember2.get()).isEqualTo(member2);
        assertThatThrownBy(() -> findMember3.get()).isInstanceOf(NoSuchElementException.class);

    }

    @Test
    void deleteMember() {
        // given
        Member member1 = Member.createMember("!userId1", "!!userPW", "t1");
        memberRepository.save(member1);
        Member member2 = Member.createMember("@userId2", "@@userPW", "t2");
        memberRepository.save(member2);

        // when
        memberRepository.deleteMember(member1.getId());

        // then
        assertThat(memberRepository.findAll().size()).isEqualTo(1);
        assertThatThrownBy(() -> memberRepository.findById(member1.getId())).isInstanceOf(NoSuchIdInDbException.class);

    }

    @Test
    void findAll() {
        // given
        Member member1 = Member.createMember("!userId1", "!!userPW", "t1");
        memberRepository.save(member1);
        Member member2 = Member.createMember("@userId2", "@@userPW", "t2");
        memberRepository.save(member2);

        // when


        // then

    }

    @Test
    void findMemberByName() {
        // given

        // when

        // then

    }


}