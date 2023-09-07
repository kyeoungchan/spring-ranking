package springschool.ranking.commondto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StudentCheckListDto {

    int count;
    List<StudentCheckElementDto> students = new ArrayList<>();
}
