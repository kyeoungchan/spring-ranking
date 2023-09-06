package springschool.ranking.record.service.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import springschool.ranking.Semester;
import springschool.ranking.record.Policy;
import springschool.ranking.record.domain.Record;
import springschool.ranking.record.repository.RecordRepository;
import springschool.ranking.record.service.dto.partial.PartialGradeRecordDto;
import springschool.ranking.record.service.dto.partial.PartialRecordDto;
import springschool.ranking.record.service.dto.partial.PartialRecordListDto;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GradeRecordPolicyService implements RecordPolicyService {

    private final StudentRepository studentRepository;
    private final RecordRepository recordRepository;

    @Override
    public Record setRecordByPolicy(Record record) {

        // 해당 학기의 Rank 객체 리스트 추출
        List<Record> records = recordRepository.findAllSimply();

        innerSorting(records, record);

        return record;
    }

    @Override
    public PartialRecordDto getPartialRecordOne(Long studentId, Semester semester) {

        Student student = studentRepository.findById(studentId);
        Record record = recordRepository.findRecordByStudentAndSemester(student, semester);

        return new PartialGradeRecordDto(studentId, record.getScore(), semester.getYear(), semester.getSemester(), record.getGrade());
    }

    /**
     * 등급이 작은 순(숫자 기준)으로 -> 오름차순
     */
    @Override
    public PartialRecordListDto sortRecord(Semester semester) {

        log.info("등급 정렬 메서드 실행. 학기={}", semester);

        List<Record> records = recordRepository.findAllWithStudentTeacherBySemester(semester);

        List<Record> sortedRecords = innerSorting(records, null);

        PartialRecordListDto result = new PartialRecordListDto(Policy.GRADE, sortedRecords.size());

        sortedRecords.stream()
                .forEach(s -> result.getRecordList().add(new PartialGradeRecordDto(s.getStudent().getId(), s.getScore(), s.getSemester().getYear(), s.getSemester().getSemester(), s.getGrade())));

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
            if (i <= total * 0.04) sortedRecords.get(i).updateGrade(1); // 4% 이하는 1등급
            else if (i <= total * 0.11) sortedRecords.get(i).updateGrade(2); // 11% 이하는 2등급
            else if (i <= total * 0.23) sortedRecords.get(i).updateGrade(3); // 23% 이하는 3등급
            else if (i <= total * 0.40) sortedRecords.get(i).updateGrade(4); // 40% 이하는 4등급
            else if (i <= total * 0.60) sortedRecords.get(i).updateGrade(5); // 60% 이하는 5등급
            else if (i <= total * 0.77) sortedRecords.get(i).updateGrade(6); // 77% 이하는 6등급
            else if (i <= total * 0.89) sortedRecords.get(i).updateGrade(7); // 89% 이하는 7등급
            else if (i <= total * 0.96) sortedRecords.get(i).updateGrade(8); // 96% 이하는 8등급
            else sortedRecords.get(i).updateGrade(9); // 나머지는 9등급
        }

        if (record != null)
            sortedRecords.remove(record);

        // 등급 모두 세팅 후 DB에 저장
        return recordRepository.saveAll(sortedRecords);
    }

}
