package springschool.ranking.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springschool.ranking.student.domain.Student;

import java.util.List;

public interface StudentJpaRepository extends JpaRepository<Student, Long> {

    @Query("select s from Student s " +
            "where s.curSemester.year = :year and s.curSemester.semester = :semester")
    List<Student> findAllBySemester(@Param("year") int year, @Param("semester") int semester);

    @Query("select s from Student s " +
            "where s.name like :name")
    List<Student> findAllByName(@Param("name") String name);

    @Query("select s from Student s " +
            "where s.teacher.id = :memberId")
    List<Student> findAllByMember(@Param("memberId") Long memberId);

}
