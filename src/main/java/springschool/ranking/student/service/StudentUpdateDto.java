package springschool.ranking.student.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class StudentUpdateDto {

    @NotBlank
    private String name;

    private String phoneNumber;

    @NotNull
    private Long teacherId;

    @NotNull
    private String teacherName;

}