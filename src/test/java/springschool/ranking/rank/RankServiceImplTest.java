package springschool.ranking.rank;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springschool.ranking.rank.domain.Rank;
import springschool.ranking.rank.service.RankService;
import springschool.ranking.student.domain.Grade;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.MemoryStudentRepository;
import springschool.ranking.student.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RankServiceImplTest {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    RankService rankService;

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
        Rank rank1 = rankService.createRank(students.get(0).getId());
        Rank rank2 = rankService.createRank(students.get(1).getId());

        //then
        assertThat(rank1.getRank()).isEqualTo(1);
        assertThat(rank2.getRank()).isEqualTo(4);
    }
}