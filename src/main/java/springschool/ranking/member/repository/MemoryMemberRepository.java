package springschool.ranking.member.repository;

import org.springframework.stereotype.Repository;
import springschool.ranking.member.domain.Member;
import springschool.ranking.exception.DuplicatedException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    /**
     * userId도 유일해야한다.
     * Service에서 구현해도 되지만, DB에 unique 옵션을 설정하는 것을 고려했을 때, 리포지토리에서 구현하는 것이 맞는 것으로 판단된다.
     */
    @Override
    public void save(Member member) throws DuplicatedException {

        boolean isDuplicated = findByUserId(member.getUserId()).isPresent();
        if (!isDuplicated) {
            member.setId(++sequence);
            store.put(member.getId(), member);
        } else {
            throw new DuplicatedException("중복된 userId입니다.");
        }
    }

    @Override
    public Member findById(Long id) {
        return store.get(id);
    }

    @Override
    public Optional<Member> findByUserId(String userId) {

        return findAll().stream()
                .filter(m -> m.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
