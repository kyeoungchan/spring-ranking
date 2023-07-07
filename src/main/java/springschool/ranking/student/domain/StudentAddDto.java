package springschool.ranking.student.domain;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import springschool.ranking.validator.ValueOfEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class StudentAddDto {

    @NotBlank
    private String name;

    @NotNull
    @Range(min = 0, max = 100)
    private Integer score;

//    @ValidEnum(enumClass = Grade.class)
    @ValueOfEnum(enumClass = Grade.class)
    private String grade;

    @NotNull
    @Range(min = 0L, max = 100L)
    private Double rate;
}
