package springschool.ranking.member.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class MemberSaveDto {

    @NotBlank
    private String userId;

    @NotBlank(message = "{NotBlank.password}")
    @Size(min = 8, message = "{Size.password}")
    private String password;

    @NotBlank(message = "{NotBlank.name}")
    @Size(max = 20, message = "{Size.name}")
    private String name;
}
