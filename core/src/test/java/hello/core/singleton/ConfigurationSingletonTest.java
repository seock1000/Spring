package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // 테스트용으로 만든 메서드 호출 예정이므로 구체 클래스로 꺼냄
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberRepository1 = " + memberRepository1);
        System.out.println("memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        // 코드 상으로는 각기 다른 인스턴스를 가져야 하나, 모두 같은 인스턴스 why?
        // 스프링에서 싱글톤을 보장하기 때문에
        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);

    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
        // class가 hello.core.AppConfig$$EnhancerBySpringCGLIB$$5bbd9900로 출력
        // @Configuration 어노테이션으로 지정된 클래스에 대해 CGLIB 라이브러리로 AppConfig를 상속받아 바이트 코드를 조작해서 이미 컨테이너 등록된 객체는 해당 객체를 반환 하도록 변경
        // 실제로 등록되는 빈은 AppConfig를 상속받은 AppConfig~CGLIB, 이로써 싱글톤 보장
        // @Configuration 어노테이션 미표기시 자바 코드 그대로 인스턴스 생성 -> 스프링 빈 등록은 되나 싱글톤 보장하지 않음
    }
}
