package springschool.ranking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springschool.ranking.rank.RankPolicy;
import springschool.ranking.rank.ScoreRankPolicy;
import springschool.ranking.student.MemoryStudentRepository;
import springschool.ranking.student.StudentRepository;

@Configuration
@ComponentScan
public class AppConfig {

    /*private final StudentRepository studentRepository;

    @Autowired
    public AppConfig(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Bean
    public RankPolicy rankPolicy() {
        return new ScoreRankPolicy(studentRepository);
    }*/

//    @Bean
//    private static MemoryStudentRepository studentRepository() {
//        return new MemoryStudentRepository();
//    }
}
