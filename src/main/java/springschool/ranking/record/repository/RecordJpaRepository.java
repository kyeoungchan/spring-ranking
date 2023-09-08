package springschool.ranking.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springschool.ranking.record.domain.Record;

import java.util.List;

public interface RecordJpaRepository extends JpaRepository<Record, Long> {

    @Query("select r from Record r " +
            "join fetch r.student s " +
            "join fetch s.teacher ")
    List<Record> findAllWithStudentTeacher();

    @Query("select r from Record r " +
            "join fetch r.student s")
    List<Record> findAllWithStudent();

    @Query("select r from Record r " +
            "where r.semester.year = :year and r.semester.semester = :semester")
    List<Record> findAllBySemester(@Param("year") int year, @Param("semester") int semester);
}
