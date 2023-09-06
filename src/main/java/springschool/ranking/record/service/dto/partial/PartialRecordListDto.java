package springschool.ranking.record.service.dto.partial;

import lombok.Data;
import springschool.ranking.record.Policy;

import java.util.ArrayList;
import java.util.List;

@Data
public class PartialRecordListDto {

    private final Policy policy;
    private final int count;
    private final List<PartialRecordDto> recordList = new ArrayList<>();
}
