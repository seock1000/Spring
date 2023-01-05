package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수 자바 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        // 호출 할 때마다 객체 생성
        // 메모리 낭비
        // 해결 방법? 객체를 하나만 생성해놓고 생성한 객체를 공유하도록 설계 => 싱글톤 패턴
        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // 두 객체가 다름
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        // 두 객체가 같음
        // same은 객체 자체가 같은지 비교
        // equals는 equals 메서드, 오버라이드 가능
        assertThat(singletonService1).isSameAs(singletonService2);
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // 두 객체가 같음
        assertThat(memberService1).isSameAs(memberService2);

        // 스프링을 사용하면 스프링이 싱글톤의 단점없이 싱글톤으로 관리해줌
        // 싱글톤 코드 없어도 됨..! 스프링 쓰는 이유 중 하나
        // 스프링의 기본 빈 등록은 싱글톤이지만, 다른 방식도 지원 -> 이건 추후에
    }
}

