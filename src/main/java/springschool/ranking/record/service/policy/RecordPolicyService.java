package springschool.ranking.record.service.policy;

import springschool.ranking.Semester;
import springschool.ranking.record.domain.Record;
import springschool.ranking.record.service.dto.partial.PartialRecordDto;
import springschool.ranking.record.service.dto.partial.PartialRecordListDto;

public interface RecordPolicyService {

    /**
     * 성적 입력 메서드
     * 각 정책별로, 학기별로 성적의 값이 달라지므로 정책별로 호출돼야한다.
     */
    Record setRecordByPolicy(Record record, Semester semester);

    /**
     * 해당 학생의 해당 학기를 정책 별로 세분화한 성적 반환
     */
    PartialRecordDto getPartialRecordOne(Long studentId, Semester semester);

    /**
     * 해당 학기 학생들을 정렬시킨 결과 반환
     */
    PartialRecordListDto sortRecord(Semester semester);

}
