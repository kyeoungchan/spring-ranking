package springschool.ranking.rank;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springschool.ranking.AppConfig;
import springschool.ranking.rank.domain.Rank;
import springschool.ranking.rank.service.RankService;
import springschool.ranking.rank.service.RankServiceImpl;
import springschool.ranking.student.domain.Grade;
import springschool.ranking.student.domain.Student;
import springschool.ranking.student.repository.MemoryStudentRepository;
import springschool.ranking.student.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RankServiceImplTest {

    @Test
    void createRank() {
        //when
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        RankService rankService = ac.getBean(RankServiceImpl.class);
        StudentRepository studentRepository = ac.getBean(MemoryStudentRepository.class);



        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "studentA", 100, Grade.ONE, 90.0));
        students.add(new Student(2L, "studentB", 70, Grade.FOUR, 65.0));
        students.add(new Student(3L, "studentC", 86, Grade.TWO, 85.0));
        students.add(new Student(4L, "studentD", 40, Grade.SIX, 20.0));
        students.add(new Student(5L, "studentE", 75, Grade.THREE, 70.0));

        for (Student student : students) {
            studentRepository.save(student);
        }



        //given
        Rank rank1 = rankService.createRank(1L);
        Rank rank2 = rankService.createRank(2L);

        //then
        assertThat(rank1.getRank()).isEqualTo(1);
        assertThat(rank2.getRank()).isEqualTo(4);
    }
}