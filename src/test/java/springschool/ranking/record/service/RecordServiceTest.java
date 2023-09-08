package springschool.ranking.record.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springschool.ranking.Semester;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.repository.MemberRepository;
import springschool.ranking.record.service.dto.ScoreInputDto;
import springschool.ranking.record.service.dto.partial.PartialRankRecordDto;
import springschool.ranking.record.service.dto.partial.PartialRecordDto;
import springschool.ranking.record.service.policy.GradeRecordPolicyService;
import springschool.ranking.record.service.policy.RankRecordPolicyService;
import springschool.ranking.record.service.policy.RateRecordPolicyService;
import springschool.ranking.record.service.policy.RecordPolicyService;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RecordServiceTest {

    @Autowired RecordService recordService;
    @Autowired MemberRepository memberRepository;
    @Autowired StudentRepository studentRepository;
    @Autowired RankRecordPolicyService recordPolicyService;
    @Autowired RateRecordPolicyService rateRecordPolicyService;
    @Autowired GradeRecordPolicyService gradeRecordPolicyService;

    @Test
    void inputRecord() {
        // given
        Semester semester = new Semester(1, 1);

        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");
        memberRepository.save(teacher);

        Student student1 = Student.createStudent("s1", "01000000", semester, teacher);
        Student student2 = Student.createStudent("s2", "01000230", semester, teacher);
        Student student3 = Student.createStudent("s3", "01000234", semester, teacher);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);

        ScoreInputDto scoreInputDto1 = new ScoreInputDto(student1.getId(), student1.getName(), semester.getYear(), semester.getSemester(), 100.0);
        ScoreInputDto scoreInputDto2 = new ScoreInputDto(student2.getId(), student2.getName(), semester.getYear(), semester.getSemester(), 90.0);
        ScoreInputDto scoreInputDto3 = new ScoreInputDto(student3.getId(), student3.getName(), semester.getYear(), semester.getSemester(), 80.0);

        // when
        recordService.inputRecord(scoreInputDto1);
        recordService.inputRecord(scoreInputDto2);
        recordService.inputRecord(scoreInputDto3);

        // then

    }

    @Test
    void getRankOne() {
        // given
        Semester semester = new Semester(1, 1);

        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");
        memberRepository.save(teacher);

        Student student1 = Student.createStudent("s1", "01000000", semester, teacher);
        Student student2 = Student.createStudent("s2", "01000230", semester, teacher);
        Student student3 = Student.createStudent("s3", "01000234", semester, teacher);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);

        ScoreInputDto scoreInputDto1 = new ScoreInputDto(student1.getId(), student1.getName(), semester.getYear(), semester.getSemester(), 100.0);
        ScoreInputDto scoreInputDto2 = new ScoreInputDto(student2.getId(), student2.getName(), semester.getYear(), semester.getSemester(), 90.0);
        ScoreInputDto scoreInputDto3 = new ScoreInputDto(student3.getId(), student3.getName(), semester.getYear(), semester.getSemester(), 80.0);

        recordService.inputRecord(scoreInputDto1);
        recordService.inputRecord(scoreInputDto2);
        recordService.inputRecord(scoreInputDto3);

        // when
        PartialRankRecordDto rank1 = (PartialRankRecordDto) recordService.getRankOne(student1.getId());
        PartialRankRecordDto rank2 = (PartialRankRecordDto) recordService.getRankOne(student2.getId());
        PartialRankRecordDto rank3 = (PartialRankRecordDto) recordService.getRankOne(student3.getId());

        // then
        assertThat(rank1.getRank()).isEqualTo(1);
        assertThat(rank2.getRank()).isEqualTo(2);
        assertThat(rank3.getRank()).isEqualTo(3);
    }

    @Test
    void setRankPolicy() {
        // given

        // when

        // then

    }

    @Test
    void setSemester() {
        // given

        // when

        // then

    }

    @Test
    void sortRank() {
        // given

        // when

        // then

    }

    @Test
    void findRank() {
        // given

        // when

        // then

    }

    @Test
    void findRankOneWithSemester() {
        // given

        // when

        // then

    }
}