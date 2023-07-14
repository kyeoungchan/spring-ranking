package springschool.ranking.member.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class MemberSaveDto {

    @NotBlank
    private String userId;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    @Size(max = 20)
    private String name;
}
