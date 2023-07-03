package springschool.ranking.member.domain;

import lombok.Data;

@Data
public class Member {
    private Long id;
    private String userId;
    private String password;
    private String name;
}
