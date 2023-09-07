package springschool.ranking.record.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springschool.ranking.Semester;
import springschool.ranking.exception.repository.NoSuchIdInDbException;
import springschool.ranking.exception.repository.NotRankedException;
import springschool.ranking.member.domain.Member;
import springschool.ranking.member.repository.MemberRepository;
import springschool.ranking.record.domain.Record;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class RecordRepositoryTest {

    @Autowired RecordRepository recordRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired StudentRepository studentRepository;


    @Test
    void save() {

        // given
        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");

        Semester semester = new Semester(1, 1);

        Student student = Student.createStudent("s1", "01000000", semester, teacher);

        double score = 100.0;

        Record record = Record.createRecord(score, semester, student);

        // when
        recordRepository.save(record);

        // then


    }

    @Test
    void findById() {
        // given
        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");

        Semester semester = new Semester(1, 1);
        Student student = Student.createStudent("s1", "01000000", semester, teacher);

        double score = 100.0;
        Record record = Record.createRecord(score, semester, student);
        recordRepository.save(record);

        // when
        Record findRecord = recordRepository.findById(record.getId());

        // then
        assertThat(findRecord).isEqualTo(record);
    }

    @Test
    @DisplayName("삭제를 한 데이터에서 id로 조회를 시도하면 NoSuchIdInDbException 예외를 던진다.")
    void delete() {
        // given
        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");

        Semester semester = new Semester(1, 1);
        Student student = Student.createStudent("s1", "01000000", semester, teacher);

        double score = 100.0;
        Record record = Record.createRecord(score, semester, student);
        recordRepository.save(record);
        assertThat(recordRepository.findById(record.getId())).isEqualTo(record);

        // when
        recordRepository.delete(record);

        // then
//        assertThat(recordRepository.findById(record.getId())).isNull();
        assertThatThrownBy(() -> recordRepository.findById(record.getId())).isInstanceOf(NoSuchIdInDbException.class);

    }

    @Test
    @DisplayName("Record를 Student와 Member를 페치 조인으로 갖고오므로 추가 쿼리가 발생하지 않는다.")
    void findAllWithStudentTeacherBySemester() {
        // given
        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");
        memberRepository.save(teacher);

        Semester semester11 = new Semester(1, 1);
        Student student1 = Student.createStudent("s1", "01000000", semester11, teacher);
        studentRepository.save(student1);

        Semester semester21 = new Semester(2, 1);
        Student student2 = Student.createStudent("s2", "01000000", semester21, teacher);
        studentRepository.save(student2);

        double score = 100.0;
        Record record1 = Record.createRecord(score, semester11, student1);
        Record record2 = Record.createRecord(score, semester21, student2);
        recordRepository.save(record1);
        recordRepository.save(record2);

        // when
        List<Record> semester11Rec = recordRepository.findAllWithStudentTeacherBySemester(semester11);
        List<Record> semester21Rec = recordRepository.findAllWithStudentTeacherBySemester(semester21);

        // then
        assertThat(semester11Rec.size()).isEqualTo(1);
        assertThat(semester11Rec.get(0)).isEqualTo(record1);
        assertThat(semester21Rec.size()).isEqualTo(1);
        assertThat(semester21Rec.get(0)).isEqualTo(record2);

        assertThat(semester11Rec.get(0).getStudent().getName()).isEqualTo("s1");
        assertThat(semester21Rec.get(0).getStudent().getTeacher().getName()).isEqualTo("t1");

    }

    @Test
    @DisplayName("Record 를 Student 와 페치 조인으로 갖고오므로 추가 쿼리가 발생하지 않는다.")
    void findAllWithStudent() {
        // given
        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");
        // Member 에게는 페치조인이 일어나려면 Teacher 의 id가 필요하므로 teacher 도 save 필요
        memberRepository.save(teacher);

        Semester semester11 = new Semester(1, 1);
        Student student1 = Student.createStudent("s1", "01000000", semester11, teacher);
        studentRepository.save(student1);

        Semester semester21 = new Semester(2, 1);
        Student student2 = Student.createStudent("s2", "01000000", semester21, teacher);
        studentRepository.save(student2);

        double score = 100.0;
        Record record1 = Record.createRecord(score, semester11, student1);
        Record record2 = Record.createRecord(score, semester21, student2);
        recordRepository.save(record1);
        recordRepository.save(record2);

        // when
        List<Record> recordsWithStudent = recordRepository.findAllWithStudent();
        Record findRecord1 = recordsWithStudent.get(0);
        Record findRecord2 = recordsWithStudent.get(1);

        // then
        assertThat(recordsWithStudent.size()).isEqualTo(2);
        assertThat(findRecord1).isEqualTo(record1);
        assertThat(findRecord2.getStudent()).isEqualTo(student2);
        assertThat(findRecord1.getStudent().getName()).isEqualTo("s1");
        assertThat(findRecord2.getStudent().getTeacher()).isEqualTo(teacher);


    }

    @Test
    @DisplayName("해당 학생, 해당 학기에 성적이 없으면 NotRankedException 을 던진다.")
    void findRecordByStudentAndSemester() {
        // given
        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");
        memberRepository.save(teacher);

        Semester semester11 = new Semester(1, 1);
        Student student1 = Student.createStudent("s1", "01000000", semester11, teacher);
        studentRepository.save(student1);

        Semester semester21 = new Semester(2, 1);
        Student student2 = Student.createStudent("s2", "01000000", semester21, teacher);
        studentRepository.save(student2);

        double score = 100.0;
        Record record1 = Record.createRecord(score, semester11, student1);
        Record record2 = Record.createRecord(score, semester21, student2);
        recordRepository.save(record1);
        recordRepository.save(record2);

        // when
        Record correctRec1 = recordRepository.findRecordByStudentAndSemester(student1, semester11);
        Record correctRec2 = recordRepository.findRecordByStudentAndSemester(student2, semester21);

        // then
        assertThatThrownBy(() -> recordRepository.findRecordByStudentAndSemester(student1, semester21)).isInstanceOf(NotRankedException.class);
        assertThatThrownBy(() -> recordRepository.findRecordByStudentAndSemester(student2, semester11)).isInstanceOf(NotRankedException.class);

        assertThat(correctRec1).isEqualTo(record1);
        assertThat(correctRec2).isEqualTo(record2);

        assertThat(correctRec1.getStudent().getName()).isEqualTo("s1");
        assertThat(correctRec2.getStudent().getName()).isEqualTo("s2");

    }

    @Test
    void findAllSimply() {
        // given

        // when

        // then

    }

    @Test
    void saveAll() {
        // given
        Member teacher = Member.createMember("teacherId", "teacherPW", "t1");
        memberRepository.save(teacher);

        Semester semester11 = new Semester(1, 1);
        Student student1 = Student.createStudent("s1", "01000000", semester11, teacher);
        studentRepository.save(student1);

        Semester semester21 = new Semester(2, 1);
        Student student2 = Student.createStudent("s2", "01000000", semester21, teacher);
        studentRepository.save(student2);

        double score = 100.0;
        Record record1 = Record.createRecord(score, semester11, student1);
        Record record2 = Record.createRecord(score, semester21, student2);
/*
        recordRepository.save(record1);
        recordRepository.save(record2);
*/
        List<Record> inputRecords = new ArrayList<>();
        inputRecords.add(record1);
        inputRecords.add(record2);

        // when
        List<Record> findRecords = recordRepository.saveAll(inputRecords);

        // then
        assertThat(findRecords).isEqualTo(recordRepository.findAllSimply());

    }
}