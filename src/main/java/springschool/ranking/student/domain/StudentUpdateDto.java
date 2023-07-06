package springschool.ranking.student.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import springschool.ranking.validator.ValidEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class StudentUpdateDto {

    @NotBlank
    private String name;

    @NotNull
    @Range(min = 0, max = 100)
    private Integer score;

    @NotBlank
    @ValidEnum(enumClass = Grade.class)
    private Grade grade;

    @NotNull
    @Range(min = 0L, max = 100L)
    private Double rate;
}
