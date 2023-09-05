package springschool.ranking.student.service;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
public class StudentAddDto {

    @NotBlank
    private String name;
    private String phoneNumber;

    @NotBlank
    private Long teacherId;
    @NotBlank
    private String teacherName;

}
