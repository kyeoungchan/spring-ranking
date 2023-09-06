package springschool.ranking.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

}
