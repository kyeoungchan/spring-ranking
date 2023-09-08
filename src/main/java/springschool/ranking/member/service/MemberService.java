package springschool.ranking.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springschool.ranking.aop.Retry;
import springschool.ranking.commondto.StudentCheckElementDto;
import springschool.ranking.commondto.StudentCheckListDto;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.repository.MemberRepository;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final StudentRepository studentRepository;

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
        Member findMember = memberRepository.findById(memberId);
        findMember.updateMember(updateDto.getName(), updateDto.getPassword());
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
//    @Retry(5) // 컨트롤러에서 호출해야 한다.
    public Member login(String userId, String password) {
        return memberRepository.findByUserId(userId)
                .filter(m -> m.getPassword().equals(password))
//                .orElseThrow(NoSuchUserIdException::new);
                .orElse(null);
    }

    /**
     * 해당 회원에 속해있는 학생들 목록 조회
     */
    public StudentCheckListDto checkStudentsByMember(Member member) {
        List<Student> students = studentRepository.findAllByMember(member);

        StudentCheckListDto result = new StudentCheckListDto();
        result.setCount(students.size());

        for (Student s : students) {
            result.getStudents().add(new StudentCheckElementDto(s.getId(), s.getName()));
        }

        return result;
    }

}
