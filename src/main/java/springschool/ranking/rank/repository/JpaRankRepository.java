package springschool.ranking.rank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springschool.ranking.Semester;
import springschool.ranking.rank.domain.Rank;

import java.util.List;

public interface JpaRankRepository extends JpaRepository<Rank, Long> {

    @Query("select r from Rank r " +
            "join fetch r.student s " +
            "join fetch s.teacher " +
            "where r.semester.year = :year and r.semester.semester = :semester")
    List<Rank> findAllBySemesters(@Param("year") int year, @Param("semester") int semester);

    @Query("select r from Rank r " +
            "join fetch r.student s")
    List<Rank> findAllWithStudent();
/*
    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class
        ).getResultList();
    }
*/
}
