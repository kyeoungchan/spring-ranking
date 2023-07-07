package springschool.ranking.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springschool.ranking.SessionConst;
import springschool.ranking.exception.domain.ErrorResult;
import springschool.ranking.exception.repository.DuplicatedException;
import springschool.ranking.exception.repository.UnValidatedException;
import springschool.ranking.member.domain.*;
import springschool.ranking.member.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * @param memberLoginDto 로그인 입력용 DTO 객체
     * @param bindingResult  로그인 입력 검증 관련
     * @param request        세션 생성을 위한 HttpServletRequest
     * @return 컨트롤러 간에는 MemberDto를 반환하는 것으로 통일하였다.
     * @throws UnValidatedException 로그인 입력 검증 결과 문제가 있을 경우 예외를 날린다.
     */
    @PostMapping("/login/v1")
    public Object loginV1(@Validated @RequestBody MemberLoginDto memberLoginDto, BindingResult bindingResult,
                             HttpServletRequest request) {

        log.info("로그인 컨트롤러 호출");

        if (bindingResult.hasErrors() || memberLoginDto == null) {
            String message = getErrorMessages(bindingResult);
            throw new UnValidatedException("로그인 검증에 실패하였습니다. " + message);
        }

        String userId = memberLoginDto.getUserId();
        String password = memberLoginDto.getPassword();
        Member loginMember = memberService.login(userId, password);

        if (loginMember == null) {
            return new ErrorResult("UNCORRECT_MEMBER", "아이디 또는 비밀번호가 맞지 않습니다.");
        }

        log.info("성공 로직 실행");
        // 세션 처리
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return new MemberDto(loginMember.getId(), loginMember.getName());
    }

    /**
     * 세션에 사용자 정보를 삭제
     */
    @PostMapping("/logout/v1")
    public void logoutV1(HttpServletRequest request) {

        log.info("로그아웃 컨트롤러 호출");

        HttpSession session = request.getSession(false);
        // 세션이 존재하지 않는다면 굳이 생성시킬 필요가 없다.

        if (session != null) {
            session.invalidate();
            // 세션을 제거한다.
        }
    }

    /**
     * @param memberSaveDto 회원가입용 DTO 객체
     * @param bindingResult 회원가입 검증 관련
     * @return 컨트롤러 간에는 MemberDto를 반환하는 것으로 통일하였다.
     * @throws UnValidatedException 회원가입 입력 검증 결과 문제가 있을 경우 예외를 날린다.
     * @throws DuplicatedException  회원가입하려는 데 이미 DB에 같은 userId가 있을 경우 예외를 날린다.
     */
    @PostMapping("/register/v1")
    public Object registerV1(@Validated @RequestBody MemberSaveDto memberSaveDto, BindingResult bindingResult) {

        log.info("회원가입 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            String message = getErrorMessages(bindingResult);
            throw new UnValidatedException("회원가입 검증에 실패하였습니다." + message);
        }

        Member registerMember = new Member();
        registerMember.setUserId(memberSaveDto.getUserId());
        registerMember.setPassword(memberSaveDto.getPassword());
        registerMember.setName(memberSaveDto.getName());

        try {
            memberService.register(registerMember);
        } catch (DuplicatedException e) {
            log.error("[register Controller] ex", e);
            // 중복 id에 대한 예외 처리
            return new ErrorResult("DUPLICATED_ID_ERROR", e.getMessage());
        }

        log.info("회원가입 성공 로직 실행");
        return new MemberDto(registerMember.getId(), memberSaveDto.getName());
    }

    private static String getErrorMessages(BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        String message = "";
        for (ObjectError error : allErrors) {
            message += ", " + error.getDefaultMessage();
        }
        return message;
    }

    @PostMapping("/{memberId}/edit/v1")
    public MemberDto updateV1(@PathVariable Long memberId, @Validated @RequestBody MemberUpdateDto memberUpdateDto, BindingResult bindingResult) {
        log.info("회원수정 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            String message = getErrorMessages(bindingResult);
            throw new UnValidatedException("회원수정 검증에 실패하였습니다. " + message);
        }

        log.info("회원수정 성공 로직 실행");
        Member updatedMember = memberService.edit(memberId, memberUpdateDto);
        return new MemberDto(updatedMember.getId(), updatedMember.getName());
    }
}
