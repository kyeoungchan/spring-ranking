package springschool.ranking.member.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDto {
    private final Long id;
    private final String name;
}
