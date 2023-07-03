package springschool.ranking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.domain.MemberDto;

@Slf4j
@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public MemberDto home(@RequestBody Member member) {
        log.info("home controller");
        return new MemberDto(member.getId(), member.getName());
    }
}
