package springschool.ranking.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springschool.ranking.student.domain.Student;

import java.util.List;

public interface StudentJpaRepository extends JpaRepository<Student, Long> {

    @Query("select s from Student s " +
            "where s.semester.year = :year and s.semester.semester = :semester")
    List<Student> findAllBySemester(@Param("year") int year, @Param("semester") int semester);

}
