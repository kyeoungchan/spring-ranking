//package springschool.ranking.member.repository;
//
//import org.springframework.stereotype.Repository;
//import springschool.ranking.member.domain.Member;
//import springschool.ranking.exception.repository.DuplicatedException;
//
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Repository
//public class MemoryMemberRepository implements MemberRepository{
//
//    private static Map<Long, Member> store = new ConcurrentHashMap<>();
//    private static long sequence = 0L;
//
//    /**
//     * userId도 유일해야한다.
//     * Service에서 구현해도 되지만, DB에 unique 옵션을 설정하는 것을 고려했을 때, 리포지토리에서 구현하는 것이 맞는 것으로 판단된다.
//     */
//    @Override
//    public void save(Member member) {
//
//        boolean isDuplicated = findByUserId(member.getUserId()).isPresent();
//        if (!isDuplicated) {
////            member.setId(++sequence);
//            store.put(member.getId(), member);
//        } else {
//            throw new DuplicatedException("중복된 userId입니다.");
//        }
//    }
//
//    /**
//     * @param id 사용자가 입력한 id가 아닌 프로그램 내부에서 PK로 사용하는 id다.
//     * @return Member 객체를 반환한다.
//     */
//    @Override
//    public Member findById(Long id) {
//        return store.get(id);
//    }
//
//    /**
//     * @param userId id가 아닌 사용자가 입력한 userId다.
//     * @return Optional로 감싸서 값을 반환하므로, 빈 값일 수 있다.
//     */
//    @Override
//    public Optional<Member> findByUserId(String userId) {
//
//        return findAll().stream()
//                .filter(m -> m.getUserId().equals(userId))
//                .findFirst();
//    }
//
//    @Override
//    public List<Member> findAll() {
//        return new ArrayList<>(store.values());
//    }
//
//    /**
//     * 테스트 케이스에 사용하기 위해서 구현한 메서드
//     */
//    public void clearStore() {
//        store.clear();
//    }
//}
