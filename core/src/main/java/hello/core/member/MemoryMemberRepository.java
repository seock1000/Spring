package hello.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MemoryMemberRepository implements MemberRepository{

    // 동시성 이슈 때문에 ConcurrentHashMap 써야 하지만 예제이므로 HashMap 사용
    private static Map<Long, Member> store = new HashMap<Long, Member>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);

    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
