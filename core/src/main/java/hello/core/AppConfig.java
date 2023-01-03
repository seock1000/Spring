package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// application의 구성 정보임을 나타내는 annotation
// 스프링 컨테이너가 구성(설정) 정보로 사용
// 기존에는 개발자가 직접 참조했지만 스프링을 적용하면 스프링 컨테이너로 관리
@Configuration
public class AppConfig {

    // 객체 생성 관련 책임은 구성 영역에서 전부 담당 -> 모든 구현 객체들을 다 알아야 함
    // 사용 영역에서는 이런 내용 전혀 몰라도 됨 -> 오로지 인터페이스에만 의존
    // DIP 만족하게 함
    // AppConfig가 의존관계를 주입하므로 사용하는 구현체가 변해도(확장) 클라이언트 코드는 변경할 필요 없음
    // 확장에는 열려있고 변경에는 닫혀있게됨
    // OCP 만족
    // 기존 클라이언트(Service)는 객체의 생성과 연결, 실행까지 여러 책임을 갖고 있었음 -> SRP 위반
    // SRP-단일 책임 원칙을 따르며 관심사 분리 -> AppConfig가 구현 객체의 생성과 연결 책임을 담당, 클라이언트는 실행만을 담당
    // 설정 파일은 구조가 명확하게 드러나도록 디자인
    /*
    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository);
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository, new FixDiscountPolicy);
    }
     */

    // @Bean : 스프링 빈
    // 해당 annotation이 붙은 메서드를 모두 호출하여 반환된 객체를 스프링 컨테이너에 등록
    // 메서드의 이름을 스프링 빈의 이름으로 사용
    // 이름을 바꾸려면 @Bean(name = "name")과 같이 변경 -> 근데 특별한 경우 아니면 그냥 쓰는 게 관례
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        //return new FixDiscountPolicy();
        // 구현체 변경 시에 이 부분만 수정하면 됨 -> 좋은 설계
        return new RateDiscountPolicy();
    }

}
