package springschool.ranking.record.service.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import springschool.ranking.Semester;
import springschool.ranking.record.Policy;
import springschool.ranking.record.domain.Record;
import springschool.ranking.record.repository.RecordRepository;
import springschool.ranking.record.service.dto.partial.PartialRankRecordDto;
import springschool.ranking.record.service.dto.partial.PartialRecordDto;
import springschool.ranking.record.service.dto.partial.PartialRecordListDto;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class RankRecordPolicyService implements RecordPolicyService {

    private final StudentRepository studentRepository;
    private final RecordRepository recordRepository;

    @Override
    public Record setRecordByPolicy(Record record, Semester semester) {

        // 해당 학기의 Rank 객체 리스트 추출
        List<Record> records = recordRepository.findAllBySemester(semester);

        innerSorting(records, record);

        return record;
    }

    @Override
    public PartialRecordDto getPartialRecordOne(Long studentId, Semester semester) {

        Student student = studentRepository.findById(studentId);
        Record record = recordRepository.findRecordByStudentAndSemester(student, semester);

        return new PartialRankRecordDto(studentId, record.getScore(), semester.getYear(), semester.getSemester(), record.getRank());
    }

    /**
     * 백분율이 큰 순(숫자 기준)으로 -> 내림차순
     */
    @Override
    public PartialRecordListDto sortRecord(Semester semester) {

        log.info("등수 정책 정렬 메서드 실행. 학기={}", semester);

        List<Record> records = recordRepository.findAllWithStudentTeacherBySemester(semester);
        log.info("records={}", records);

        List<Record> sortedRecords = innerSorting(records, null);
        log.info("sortedRecords={}", sortedRecords);

        PartialRecordListDto result = new PartialRecordListDto(Policy.RANK, sortedRecords.size());

        sortedRecords.stream()
                .forEach(s -> result.getRecordList().add(new PartialRankRecordDto(
                        s.getStudent().getId(),
                        s.getScore(),
                        s.getSemester().getYear(),
                        s.getSemester().getSemester(),
                        s.getRank())
                ));

        log.info("result={}", result);
        return result;
    }

    private List<Record> innerSorting(List<Record> records, Record record) {

        if (record != null) {
            records.add(record);
        }

        // 점수순으로 일단 정렬
        List<Record> sortedRecords = records.stream()
                .sorted(Comparator.comparing(Record::getScore).reversed())
                .collect(Collectors.toList());

        int total = sortedRecords.size();

        // 등급 세팅 및 저장
        for (int i = 0; i < total; i++) {
            Record tempRecord = sortedRecords.get(i);
            tempRecord.updateRank(i + 1); // 등수 업데이트
        }

        if (record != null)
            sortedRecords.remove(record);

        // 등급 모두 세팅 후 DB에 저장
        return recordRepository.saveAll(sortedRecords);
    }
}
