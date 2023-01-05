package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 스프링 빈으로 등록 목적
@Component
public class MemberServiceImpl implements MemberService {

    // 추상화에 의존하는 동시에 할당하는 부분에서 구현체에 의존 -> DIP 위반
    private MemberRepository memberRepository;

    // 의존관계 자동 주입 목적
    @Autowired // ac.getBean(MemberRepository.class)와 같이 동작
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

}
