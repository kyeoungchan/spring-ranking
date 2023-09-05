package springschool.ranking.student.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StudentCheckDto {

    private String name;
    private String phoneNumber;
    private String teacherName;
}
