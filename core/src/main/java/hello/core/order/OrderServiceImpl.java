package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
// lombok 기능, 필수인(=final이 붙은) 필드의 생성자 자동 생성
// @RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // 클라이언트 코드 변경 -> OCP 위반 / 인터페이스 + 구체 클래스에 의존 -> DIP 위반
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    private final DiscountPolicy discountPolicy;

    /**
    // 일반 메서드 주입
    // 아무 일반 메서드에나 지정
    // 수정자 주입이랑 다를게 거의 없음 -> 그래서 거의 사용 X
    @Autowired
    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
    **/

    /**
    // 필드 주입
    // 필드를 Autowired로 지정하여 주입
    // 코드는 간결하나 테스트 등에서 순수 자바로 사용이 어려움
    // ex) 순수자바로는 OrderService os = new OrderServiceImpl(); 같이만 사용 가능.
    // DI 컨테이너 없이는 테스트 자체가 불가 -> 순수한 테스트 불가
    // 일반적으로는 사용 권장 X, 테스트 코드 정도에서 사용 권장
    // @SpringBootTest 또는 @Configuration에서 임시로 사용하는 경우는 사용해도 괜찮을 듯
    @Autowired private final MemberRepository memberRepository;
    @Autowired  private final DiscountPolicy discountPolicy;
    **/

    /**
    // 수정자 주입(setter로 주입)
    // 생성자 주입과 동시에 있으면 생성자 주입은 빈 등록과정에서 의존관계 주입이 일어나기 때문에 수정자 주입이 그 이후에 일어남
    // 필드에서 final 떼야함
    // 선택 : 선택적으로 의존관계 주입하는 경우, @Autowired(required = false)
    // 변경 : 변경 가능성이 있는 경우
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
    **/

    // 생성자 주입(가장 많이 사용)
    // 불변 : 생성자는 두 번 호출될 수 없기 때문에 값이 변하면 안되는 경우에 사용
    // 필수 : 생성자 파라미터는 일반적으로 필수 값, 문서에 null 허용이라고 명시되지 않은 경우에는 관례적으로 생성자에 값은 다 전달함
    // 생성자가 하나만 있을 때는 @Autowired 생략 가능 - 스프링이 자동으로 인지하고 생성자에 주입
    // 다른 주입의 경우에는 스프링 컨테이너 Life Cycle이 빈 등록, 의존관계 주입 두 단계로 나뉘지만, 생성자 주입은 생성자에 대해 의존관계를 주입하기 때문에 빈 등록과 의존관계 주입이 함께 발생함
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    /**
     // 조회할 타입에 매칭되는 빈이 여러 개일 때,

     // 1.
     // 파라미터 명에 매칭되는 빈 등록
     // Autowired 기능
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy rateDiscountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = rateDiscountPolicy; // rateDiscountPolicy 빈으로 주입
    }

     // 2.
     // @Qualifier 사용하여 매칭되는 빈 등록
     @Autowired
     public OrderServiceImpl(MemberRepository memberRepository,
        @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) {
     this.memberRepository = memberRepository;
     this.discountPolicy = discountPolicy;
     }

     // 3. 컴포넌트에 @Primary 사용

     **/


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
