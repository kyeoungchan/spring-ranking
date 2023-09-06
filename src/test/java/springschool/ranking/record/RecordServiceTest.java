package springschool.ranking.record;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springschool.ranking.record.domain.Record;
import springschool.ranking.record.service.RecordService;
import springschool.ranking.student.domain.Grade;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.MemoryStudentRepository;
import springschool.ranking.student.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RecordServiceTest {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    RecordService recordService;

    @AfterEach
    void beforeEach() {
        if (studentRepository instanceof MemoryStudentRepository) {
            ((MemoryStudentRepository) studentRepository).clearStore();
        }
    }

    @Test
    void createRank() {
        //when
        List<Student> students = new ArrayList<>();
        students.add(new Student("studentA", 100, Grade.ONE, 90.0));
        students.add(new Student( "studentB", 70, Grade.FOUR, 65.0));
        students.add(new Student( "studentC", 86, Grade.TWO, 85.0));
        students.add(new Student("studentD", 40, Grade.SIX, 20.0));
        students.add(new Student( "studentE", 75, Grade.THREE, 70.0));

        for (Student student : students) {
            studentRepository.save(student);
        }

        //given
        Record record1 = recordService.createRank(students.get(0).getId());
        Record record2 = recordService.createRank(students.get(1).getId());

        //then
        assertThat(record1.getRank()).isEqualTo(1);
        assertThat(record2.getRank()).isEqualTo(4);
    }
}