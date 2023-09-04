package springschool.ranking.rank.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springschool.ranking.Semester;
import springschool.ranking.exception.repository.NoSuchIdInDbException;
import springschool.ranking.rank.domain.Rank;
import springschool.ranking.student.domain.Student;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RankRepository/* implements RankRepository */{

    private final JpaRankRepository jpaRankRepository;

    public void save(Rank rank) {
        jpaRankRepository.save(rank);
    }

    public Rank findById(Long id) {
        return jpaRankRepository.findById(id).orElseThrow(NoSuchIdInDbException::new);
    }

    public void delete(Rank rank) {
        jpaRankRepository.delete(rank);
    }

    public List<Rank> findAllBySemesters(Semester semester) {
        return jpaRankRepository.findAllBySemesters(semester.getYear(), semester.getSemester());
    }

    public List<Rank> findAllWithStudent() {
        return jpaRankRepository.findAllWithStudent() ;
    }

    public Rank findRankByStudentAndSemester(Student student, Semester semester) {
        return findAllWithStudent().stream().filter(
                        rank -> rank.getStudent().equals(student) && rank.getSemester().equals(semester))
                .findFirst()
                .orElseThrow(() -> new NoSuchIdInDbException("해당 학기에 해당 학생의 성적이 존재하지 않습니다."));
    }

/*
    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class
        ).getResultList();
    }
*/

/*
    List<OrderItemQueryDto> orderItems = em.createQuery(
                    "select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                            " from OrderItem oi" +
                            " join oi.item i" +
                            " where oi.order.id in :orderIds", OrderItemQueryDto.class)
            .setParameter("orderIds", orderIds)
            .getResultList();

    Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
            .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));
    // orderItemQueryDto -> orderItemQueryDto.getOrderId()는 OrderItemQueryDto::getOrderId 이렇게 변경 가능
    return orderItemMap;
*/

/*
    private static List<Long> toOrderIds(List<OrderQueryDto> result) {
        List<Long> orderIds = result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());
        return orderIds;
    }
*/

}
