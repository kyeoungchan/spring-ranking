package springschool.ranking.member.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 로그인을 위한 DTO
 */
@Data
public class MemberLoginDto {

    @NotBlank
    private String userId;

    @NotBlank
    private String password;

}
