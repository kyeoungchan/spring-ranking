package springschool.ranking.record.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springschool.ranking.Semester;
import springschool.ranking.exception.repository.NoSuchIdInDbException;
import springschool.ranking.exception.repository.NotRankedException;
import springschool.ranking.record.domain.Record;
import springschool.ranking.student.domain.Student;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RecordRepository {

    private final RecordJpaRepository recordJpaRepository;

    public Record save(Record record) {
        return recordJpaRepository.save(record);
    }

    public Record findById(Long id) {
        return recordJpaRepository.findById(id).orElseThrow(NoSuchIdInDbException::new);
    }

    public void delete(Record record) {
        recordJpaRepository.delete(record);
    }

    /**
     * 해당 학기에 부합하는 학생과 교사에 대한 정보를 함께 갖고 오는 Rank 목록
     */
    public List<Record> findAllWithStudentTeacherBySemester(Semester semester) {

        return recordJpaRepository.findAllWithStudentTeacher()
                .stream()
                .filter(r -> r.getStudent().getCurSemester().equals(semester))
                .collect(Collectors.toList());
    }

    /**
     * 학생과 조인 페치 해서 모든 Rank 조회
     */
    public List<Record> findAllWithStudent() {
        return recordJpaRepository.findAllWithStudent() ;
    }

    /**
     * 학생과 학기 조건에 충족하는 Rank 객체 반환
     * Rank 객체가 존재하지 않을 시 NotRankedException 발생
     */
    public Record findRecordByStudentAndSemester(Student student, Semester semester) {
        return findAllWithStudent().stream().filter(
                        rank -> rank.getStudent().equals(student) && rank.getSemester().equals(semester))
                .findFirst()
                .orElseThrow(() -> new NotRankedException("해당 학기에 해당 학생의 성적이 존재하지 않습니다."));
    }

    /**
     * 아무 연관 관계를 고려하지 않고 Rank 목록을 반환해주는 메서드
     */
    public List<Record> findAllSimply() {
        return recordJpaRepository.findAll();
    }

    public List<Record> saveAll(List<Record> records) {
        return recordJpaRepository.saveAll(records);
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
