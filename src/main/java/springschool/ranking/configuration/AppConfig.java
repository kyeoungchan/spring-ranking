package springschool.ranking.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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
