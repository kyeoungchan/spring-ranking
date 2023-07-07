package springschool.ranking.member.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class MemberSaveDto {

    @NotBlank
    private String userId;

    @NotBlank
    @Size(min = 8, message = "8글자 이상이어야합니다.")
    private String password;

    @NotBlank
    @Size(max = 20, message = "20글자 이하여야합니다.")
    private String name;
}
