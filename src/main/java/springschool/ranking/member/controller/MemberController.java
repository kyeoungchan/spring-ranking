package springschool.ranking.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springschool.ranking.aop.Retry;
import springschool.ranking.SessionConst;
import springschool.ranking.exception.domain.ErrorResult;
import springschool.ranking.exception.repository.DuplicatedException;
import springschool.ranking.exception.repository.NoSuchUserIdException;
import springschool.ranking.exception.repository.UnValidatedException;
import springschool.ranking.member.domain.*;
import springschool.ranking.member.service.MemberService;
import springschool.ranking.member.service.MemberUpdateDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;
    private final MessageSource messageSource;

    /**
     * @param memberLoginDto 로그인 입력용 DTO 객체
     * @param bindingResult  로그인 입력 검증 관련
     * @param request        세션 생성을 위한 HttpServletRequest
     * @return 컨트롤러 간에는 MemberDto를 반환하는 것으로 통일하였다.
     * @throws UnValidatedException 로그인 입력 검증 결과 문제가 있을 경우 예외를 날린다.
     * @throws NoSuchUserIdException 로그인 시도를 5번 이상 실패했을 때 예외를 던진다.
     */
    @GetMapping("/login/v1")
    @Retry(5) // 로그인 시도 횟수 제한 : 5
    public MemberDto loginV1(@Validated @RequestBody MemberLoginDto memberLoginDto, BindingResult bindingResult,
                             HttpServletRequest request) {

        log.info("로그인 컨트롤러 호출");

        if (bindingResult.hasErrors() || memberLoginDto == null) {
            String message = generateErrorMessages(bindingResult);
            throw new UnValidatedException("로그인 검증에 실패하였습니다." + message);
        }

        String userId = memberLoginDto.getUserId();
        String password = memberLoginDto.getPassword();
        Member loginMember = memberService.login(userId, password);

        if (loginMember == null) {
//            return new ErrorResult("UNCORRECT_MEMBER", "아이디 또는 비밀번호가 맞지 않습니다.");
            log.info("로그인 시도 실패");
            throw new NoSuchUserIdException("아이디 또는 비밀번호를 다시 확인해주세요.");
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
     * @return 컨트롤러 간에는 MemberDto 를 반환하는 것으로 통일하였다.
     * @throws UnValidatedException 회원가입 입력 검증 결과 문제가 있을 경우 예외를 날린다.
     * @throws DuplicatedException  회원가입하려는 데 이미 DB에 같은 userId가 있을 경우 예외를 날린다.
     */
    @PostMapping("/register/v1")
    public Object registerV1(@Validated @RequestBody MemberSaveDto memberSaveDto, BindingResult bindingResult) {

        log.info("회원가입 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            String message = generateErrorMessages(bindingResult);
            throw new UnValidatedException("회원가입 검증에 실패하였습니다." + message);
        }

        // Member의 id에 대한 값은 리포지토리에서 자동 생성한다.
/*
        Member registerMember = new Member();
        registerMember.createMember(memberSaveDto.getUserId(), memberSaveDto.getPassword(), memberSaveDto.getName());
*/
        Member registerMember = Member.createMember(memberSaveDto.getUserId(), memberSaveDto.getPassword(), memberSaveDto.getName());

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

    /**
     * @param memberId 현재 업데이트하고자 하는 회원의 id 정보가 주어진다.
     * @param memberUpdateDto 회원이 업데이트하고자 하는 데이터는 MemberUpdateDto에 담긴다.
     * @param bindingResult 검증을 위해 BindingResult가 주어진다.
     * @return MemberDto를 반환한다.
     */
    @PostMapping("/{memberId}/edit/v1")
    public MemberDto updateV1(@PathVariable Long memberId, @Validated @RequestBody MemberUpdateDto memberUpdateDto, BindingResult bindingResult) {
        log.info("회원수정 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            String message = generateErrorMessages(bindingResult);
            throw new UnValidatedException("회원수정 검증에 실패하였습니다." + message);
        }

        log.info("회원수정 성공 로직 실행");
        memberService.edit(memberId, memberUpdateDto);
        Member updatedMember = memberService.checkMember(memberId);

        return new MemberDto(updatedMember.getId(), updatedMember.getName());
    }

    /**
     * 회원 탈퇴 시 사용자 정보를 DB 에서 삭제 후 로그아웃
     */
    @PostMapping("/{memberId}/withdraw/v1")
    public void withdrawV1(@PathVariable Long memberId, HttpServletRequest request) {
        log.info("회원 탈퇴 컨트롤러 호출");

        memberService.withdraw(memberId);
        logoutV1(request);
    }

    /**
     * @param bindingResult 에 담긴 에러 메시지들을 모두 담는다.
     * @return 생성한 메시지를 반환한다.
     */
    private String generateErrorMessages(BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        String message = "";
        for (ObjectError error : allErrors) {
            Object[] arguments = error.getArguments();
            String[] codes = error.getCodes();
            message += " " + messageSource.getMessage(codes[1], arguments, error.getDefaultMessage(), Locale.KOREA);
        }
        return message;
    }
}
