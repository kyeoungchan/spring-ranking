package springschool.ranking;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springschool.ranking.rank.RankPolicy;
import springschool.ranking.rank.ScoreRankPolicy;
import springschool.ranking.student.MemoryStudentRepository;

@Configuration
@ComponentScan
public class AppConfig {

    @Bean
    public RankPolicy rankPolicy() {
        return new ScoreRankPolicy(studentRepository());
    }

    @Bean
    private static MemoryStudentRepository studentRepository() {
        return new MemoryStudentRepository();
    }
}
