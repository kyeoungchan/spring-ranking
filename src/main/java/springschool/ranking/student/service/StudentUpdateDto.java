package springschool.ranking.student.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class StudentUpdateDto {

    @NotBlank
    private String name;

    private String phoneNumber;

    @NotNull
    private Long teacherId;

    @NotNull
    private String teacherName;


/*
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
*/
}
