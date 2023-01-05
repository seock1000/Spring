package hello.core.member;

public class MemberServiceImpl implements MemberService {

    // 추상화에 의존하는 동시에 할당하는 부분에서 구현체에 의존 -> DIP 위반
    private MemberRepository memberRepository;

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
