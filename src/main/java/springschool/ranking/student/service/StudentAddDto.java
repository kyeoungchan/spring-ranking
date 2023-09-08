package springschool.ranking.student.service;

import lombok.*;
import springschool.ranking.Semester;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class StudentAddDto {

    @NotBlank
    private String name;
    private String phoneNumber;

    @NotBlank
    private Semester semester;

    @NotBlank
    private Long teacherId;
    @NotBlank
    private String teacherName;

}
