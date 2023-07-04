package springschool.ranking.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springschool.ranking.SessionConst;
import springschool.ranking.exception.UnValidatedException;
import springschool.ranking.exception.domain.ErrorResult;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.domain.MemberDto;
import springschool.ranking.member.domain.MemberLoginDto;
import springschool.ranking.member.domain.MemberSaveDto;
import springschool.ranking.member.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/login/v1")
    public MemberDto loginV1(@Validated @RequestBody MemberLoginDto memberLoginDto, BindingResult bindingResult,
                           HttpServletRequest request) throws UnValidatedException {

        log.info("로그인 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            throw new UnValidatedException("로그인 검증에 실패하였습니다.");
        }

        log.info("성공 로직 실행");
        String userId = memberLoginDto.getUserId();
        String password = memberLoginDto.getPassword();
        Member loginMember = memberService.login(userId, password);

        // 세션 처리
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return new MemberDto(loginMember.getId(), loginMember.getName());
    }

    @PostMapping("/logout/v1")
    public void logoutV1(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        // 세션이 존재하지 않는다면 굳이 생성시킬 필요가 없다.

        if (session != null) {
            session.invalidate();
            // 세션을 제거한다.
        }
    }
}
