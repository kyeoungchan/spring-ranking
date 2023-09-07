package springschool.ranking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootApplication
@Slf4j
public class RankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RankingApplication.class, args);
	}

/*
	@Bean
	@Profile("test")
	public DataSource dataSource() {
		*/
/* 테스트 케이스를 실행하는 경우 DataSource를 스프링 부트에 직접 등록한다.*//*


		log.info("메모리 데이터베이스 초기화");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver"); // h2 데이터베이스 드라이버 지정
		dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1"); // 메모리 DB -> JVM 내에 데이터베이스를 만들고 거기에 데이터를 쌓는다.
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}
*/
}
