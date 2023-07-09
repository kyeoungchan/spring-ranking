package springschool.ranking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.domain.MemberDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/api")
public class HomeController {

    //    @GetMapping("/v1")
    public MemberDto homeV1(HttpServletRequest request) {
        log.info("home controller");
        HttpSession session = request.getSession(false);

        // 세션이 없다면
        if (session == null) {
            return new MemberDto(-1L, "NONE");
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        // 세션은 있는데 회원에 관한 게 아니면
        if (loginMember == null) {
            return new MemberDto(-1L, "NONE");
        }

        return new MemberDto(loginMember.getId(), loginMember.getName());
    }

    /**
     * 인터셉터에서 이미 세션에 대한 검증을 다 끝낸 상황이므로 if문이 불필요하지만, 인터셉터가 제대로 작동되지 않을 때를 대비해서 남겨놨다.
     * @param loginMember required=false도 불필요하지만 혹시 몰라 남겨놨다.
     */
    @GetMapping("/v1")
    public MemberDto homeV1Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {

        // 세션은 있는데 회원에 관한 게 아니면
        if (loginMember == null) {
            return new MemberDto(-1L, "NONE");
        }

        return new MemberDto(loginMember.getId(), loginMember.getName());
    }

}
