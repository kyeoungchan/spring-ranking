package springschool.ranking.student.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;
import springschool.ranking.validator.ValidEnum;
import springschool.ranking.validator.ValueOfEnum;

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
//    @ValidEnum(enumClass = Grade.class)
    @ValueOfEnum(enumClass = Grade.class)
    private String grade;

    @NotNull
    @Range(min = 0L, max = 100L)
    private Double rate;
}
