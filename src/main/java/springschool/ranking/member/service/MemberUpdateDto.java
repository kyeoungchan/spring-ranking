package springschool.ranking.member.service;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class MemberUpdateDto {

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    @Size(max = 20)
    private String name;
}
