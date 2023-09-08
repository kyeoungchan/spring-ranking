package springschool.ranking.record.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springschool.ranking.Semester;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.repository.MemberRepository;
import springschool.ranking.record.Policy;
import springschool.ranking.record.domain.Record;
import springschool.ranking.record.service.dto.ScoreInputDto;
import springschool.ranking.record.service.dto.partial.*;
import springschool.ranking.record.service.policy.GradeRecordPolicyService;
import springschool.ranking.record.service.policy.RankRecordPolicyService;
import springschool.ranking.record.service.policy.RateRecordPolicyService;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class RecordServiceTest {

    @Autowired RecordService recordService;
    @Autowired MemberRepository memberRepository;
    @Autowired StudentRepository studentRepository;
    @Autowired RankRecordPolicyService recordPolicyService;
    @Autowired RateRecordPolicyService rateRecordPolicyService;
    @Autowired GradeRecordPolicyService gradeRecordPolicyService;

    /**
     * 쓰레드 로컬 초기화용 메서드
     */
    @BeforeEach
    void beforeEach() {
        recordService.setSemester(new Semester(1, 1));
        recordService.setRankPolicy(Policy.RANK);
    }

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
        PartialRankRecordDto rank1 = (PartialRankRecordDto) recordService.getRankOneByPolicy(student1.getId());
        PartialRankRecordDto rank2 = (PartialRankRecordDto) recordService.getRankOneByPolicy(student2.getId());
        PartialRankRecordDto rank3 = (PartialRankRecordDto) recordService.getRankOneByPolicy(student3.getId());

        // then
        assertThat(rank1.getRank()).isEqualTo(1);
        assertThat(rank2.getRank()).isEqualTo(2);
        assertThat(rank3.getRank()).isEqualTo(3);
    }

    @Test
    void setRankPolicy() {
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
        recordService.setRankPolicy(Policy.GRADE);
        PartialGradeRecordDto rank1 = (PartialGradeRecordDto) recordService.getRankOneByPolicy(student1.getId());
        PartialGradeRecordDto rank2 = (PartialGradeRecordDto) recordService.getRankOneByPolicy(student2.getId());
        PartialGradeRecordDto rank3 = (PartialGradeRecordDto) recordService.getRankOneByPolicy(student3.getId());

        /* 3명의 등급
        * 1등은 40%(3 * 0.4) 이하 => 4등급
        * 2등은 77%(3 * 0.77) 이하 => 6등급
        * 3등은 96%(3 * 0.96) 이상 => 9등급*/
        assertThat(rank1.getGrade()).isEqualTo(4);
        assertThat(rank2.getGrade()).isEqualTo(6);
        assertThat(rank3.getGrade()).isEqualTo(9);

        recordService.setRankPolicy(Policy.RATE);
        PartialRateRecordDto rank4 = (PartialRateRecordDto) recordService.getRankOneByPolicy(student1.getId());
        PartialRateRecordDto rank5 = (PartialRateRecordDto) recordService.getRankOneByPolicy(student2.getId());
        PartialRateRecordDto rank6 = (PartialRateRecordDto) recordService.getRankOneByPolicy(student3.getId());

        /* 3명 중
        * 1등 : 백분율 66.66666..%
        * 2등 : 백운율 33.33333..%
        * 3등 : 백분율 0.00%*/
        assertThat(rank4.getRate()).isBetween(66.66, 66.67);
        assertThat(rank5.getRate()).isBetween(33.33, 33.34);
        assertThat(rank6.getRate()).isEqualTo(0.0);

    }

    @Test
    void setSemester() {
        // given
        Semester semester = new Semester(1, 1);
        Semester semester2 = new Semester(2, 1);

        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");
        memberRepository.save(teacher);

        // 3 학생들은 모두 2학년이라고 가정
        Student student1 = Student.createStudent("s1", "01000000", semester2, teacher);
        Student student2 = Student.createStudent("s2", "01000230", semester2, teacher);
        Student student3 = Student.createStudent("s3", "01000234", semester2, teacher);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);

        // 1학년 1학기 점수 입력
        ScoreInputDto scoreInputDto1 = new ScoreInputDto(student1.getId(), student1.getName(), semester.getYear(), semester.getSemester(), 100.0);
        ScoreInputDto scoreInputDto2 = new ScoreInputDto(student2.getId(), student2.getName(), semester.getYear(), semester.getSemester(), 90.0);
        ScoreInputDto scoreInputDto3 = new ScoreInputDto(student3.getId(), student3.getName(), semester.getYear(), semester.getSemester(), 80.0);

        recordService.inputRecord(scoreInputDto1);
        recordService.inputRecord(scoreInputDto2);
        recordService.inputRecord(scoreInputDto3);

        // 2학년 1학기 점수 입력. 세 학생은 역전이 일어났다.
        ScoreInputDto scoreInputDto4 = new ScoreInputDto(student1.getId(), student1.getName(), semester2.getYear(), semester2.getSemester(), 80.0);
        ScoreInputDto scoreInputDto5 = new ScoreInputDto(student2.getId(), student2.getName(), semester2.getYear(), semester2.getSemester(), 90.0);
        ScoreInputDto scoreInputDto6 = new ScoreInputDto(student3.getId(), student3.getName(), semester2.getYear(), semester2.getSemester(), 100.0);

        recordService.inputRecord(scoreInputDto4);
        recordService.inputRecord(scoreInputDto5);
        recordService.inputRecord(scoreInputDto6);

        // when
        recordService.setSemester(semester);

        // then
        PartialRankRecordDto rank1 = (PartialRankRecordDto) recordService.getRankOneByPolicy(student1.getId());
        PartialRankRecordDto rank2 = (PartialRankRecordDto) recordService.getRankOneByPolicy(student2.getId());
        PartialRankRecordDto rank3 = (PartialRankRecordDto) recordService.getRankOneByPolicy(student3.getId());

        assertThat(rank1.getRank()).isEqualTo(1);
        assertThat(rank2.getRank()).isEqualTo(2);
        assertThat(rank3.getRank()).isEqualTo(3);

        // 현재는 2학년 1학기
        recordService.setSemester(semester2);

        PartialRankRecordDto rank4 = (PartialRankRecordDto) recordService.getRankOneByPolicy(student1.getId());
        PartialRankRecordDto rank5 = (PartialRankRecordDto) recordService.getRankOneByPolicy(student2.getId());
        PartialRankRecordDto rank6 = (PartialRankRecordDto) recordService.getRankOneByPolicy(student3.getId());

        assertThat(rank4.getRank()).isEqualTo(3);
        assertThat(rank5.getRank()).isEqualTo(2);
        assertThat(rank6.getRank()).isEqualTo(1);


    }

    @Test
    void sortRank() {
        // given
        Semester semester = new Semester(1, 1);
        Semester semester2 = new Semester(2, 1);

        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");
        memberRepository.save(teacher);

        // 3 학생들은 모두 2학년이라고 가정
        Student student1 = Student.createStudent("s1", "01000000", semester2, teacher);
        Student student2 = Student.createStudent("s2", "01000230", semester2, teacher);
        Student student3 = Student.createStudent("s3", "01000234", semester2, teacher);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);

        // 1학년 1학기 점수 입력
        ScoreInputDto scoreInputDto1 = new ScoreInputDto(student1.getId(), student1.getName(), semester.getYear(), semester.getSemester(), 100.0);
        ScoreInputDto scoreInputDto2 = new ScoreInputDto(student2.getId(), student2.getName(), semester.getYear(), semester.getSemester(), 90.0);
        ScoreInputDto scoreInputDto3 = new ScoreInputDto(student3.getId(), student3.getName(), semester.getYear(), semester.getSemester(), 80.0);

        recordService.inputRecord(scoreInputDto1);
        recordService.inputRecord(scoreInputDto2);
        recordService.inputRecord(scoreInputDto3);

        // 2학년 1학기 점수 입력. 세 학생은 역전이 일어났다.
        ScoreInputDto scoreInputDto4 = new ScoreInputDto(student1.getId(), student1.getName(), semester2.getYear(), semester2.getSemester(), 80.0);
        ScoreInputDto scoreInputDto5 = new ScoreInputDto(student2.getId(), student2.getName(), semester2.getYear(), semester2.getSemester(), 90.0);
        ScoreInputDto scoreInputDto6 = new ScoreInputDto(student3.getId(), student3.getName(), semester2.getYear(), semester2.getSemester(), 100.0);

        recordService.inputRecord(scoreInputDto4);
        recordService.inputRecord(scoreInputDto5);
        recordService.inputRecord(scoreInputDto6);

        // when
        PartialRecordListDto dto1 = recordService.sortRank();

        // then
        recordService.setSemester(semester2);
        PartialRecordListDto dto2 = recordService.sortRank();

        assertThat(dto1.getCount()).isEqualTo(3);
        assertThat(dto2.getCount()).isEqualTo(3);

    }

    @Test
    void findRankOneWithSemester() {
        // given
        Semester semester = new Semester(1, 1);
        Semester semester2 = new Semester(2, 1);

        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");
        memberRepository.save(teacher);

        // 3 학생들은 모두 2학년이라고 가정
        Student student1 = Student.createStudent("s1", "01000000", semester2, teacher);
        Student student2 = Student.createStudent("s2", "01000230", semester2, teacher);
        Student student3 = Student.createStudent("s3", "01000234", semester2, teacher);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);

        // 1학년 1학기 점수 입력
        ScoreInputDto scoreInputDto1 = new ScoreInputDto(student1.getId(), student1.getName(), semester.getYear(), semester.getSemester(), 100.0);
        ScoreInputDto scoreInputDto2 = new ScoreInputDto(student2.getId(), student2.getName(), semester.getYear(), semester.getSemester(), 90.0);
        ScoreInputDto scoreInputDto3 = new ScoreInputDto(student3.getId(), student3.getName(), semester.getYear(), semester.getSemester(), 80.0);

        Record record1 = recordService.inputRecord(scoreInputDto1);
        Record record2 = recordService.inputRecord(scoreInputDto2);
        Record record3 = recordService.inputRecord(scoreInputDto3);

        // 2학년 1학기 점수 입력. 세 학생은 역전이 일어났다.
        ScoreInputDto scoreInputDto4 = new ScoreInputDto(student1.getId(), student1.getName(), semester2.getYear(), semester2.getSemester(), 80.0);
        ScoreInputDto scoreInputDto5 = new ScoreInputDto(student2.getId(), student2.getName(), semester2.getYear(), semester2.getSemester(), 90.0);
        ScoreInputDto scoreInputDto6 = new ScoreInputDto(student3.getId(), student3.getName(), semester2.getYear(), semester2.getSemester(), 100.0);

        Record record4 = recordService.inputRecord(scoreInputDto4);
        Record record5 = recordService.inputRecord(scoreInputDto5);
        Record record6 = recordService.inputRecord(scoreInputDto6);

        // when
        // 현재는 1학년 1학기
        Record findRecord1 = recordService.findRankOneWithSemester(student1.getId());
        Record findRecord2 = recordService.findRankOneWithSemester(student2.getId());
        Record findRecord3 = recordService.findRankOneWithSemester(student3.getId());

        // 2학년 1학기로 적용
        recordService.setSemester(semester2);
        Record findRecord4 = recordService.findRankOneWithSemester(student1.getId());
        Record findRecord5 = recordService.findRankOneWithSemester(student2.getId());
        Record findRecord6 = recordService.findRankOneWithSemester(student3.getId());

        // then
        assertThat(findRecord1).isEqualTo(record1);
        assertThat(findRecord2).isEqualTo(record2);
        assertThat(findRecord3).isEqualTo(record3);
        assertThat(findRecord4).isEqualTo(record4);
        assertThat(findRecord5).isEqualTo(record5);
        assertThat(findRecord6).isEqualTo(record6);

    }
}