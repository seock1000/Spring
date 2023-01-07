package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.*;

@Configuration
// @Component 어노테이션이 붙은 클래스 자동으로 스캔해서 스프링 빈으로 등록해주는 어노테이션
// @Configuration 소스 코드에도 @Component 붙어있음
@ComponentScan(
        // AppConfig 제외하기 위해서 Configuration 어노테이션 붙어있는 클래스는 제외(충돌 방지)
        // 실제로는 제외하지 않지만, 이전 실습 코드 살리기 위해서 제외
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        /**
        // 탐색할 시작 위치 패키지 지정
        // 해당 패키지부터 시작해서 하위 패키지 탐색
        // 스캔해야할 패키지만 지정해서 빠르게 스캔할 수 있도록
        basePackages = "hello.core.member",
        // 지정한 클래스의 패키지를 시작 위치로 지정
        basePackageClasses = AutoAppConfig.class
        **/

        // 지정하지 않으면(default) @ComponentScan이 붙은 클래스의 패키지에서 시작해서 하위 패키지 탐색
        // 패키지 위치 지정하지 않고 설정 정보 클래스 위치를 프로젝트 최상단에 위치시키는 게 관례(권장)

        // @SpringBootApplication 내부 코드에 @ComponentScan이 있음
        // 따라서 SpringBoot를 쓰면 해당 패키지부터 알아서 컴포넌트 스캔해주기 때문에 사실 @ComponentScan을 따로 쓸 필요가 없음
)
public class AutoAppConfig {
    // @Bean으로 클래스 등록해 줄 필요 없음
    // 자동으로 등록해주니까

        // 자동 등록 되는 빈 이름이 중복되는 경우
        // 오류 발생

        // 자동 등록과 수동 등록 빈 이름이 중복되는 경우
        // 자동 등록된 빈이 수동 등록된 빈으로 오버라이드
        // 의도적으로 하지 않은 경우에는 잡기 몹시 어려운 버그가 됨
        // 최근 스프링은 이러한 경우도 디폴트 값에 오류로 정의
        // 굳이 오버라이드 하고 싶다면 application.properties에 spring.main.allow-bean-definition-overriding=true 설정 추가
        /**
         * 에러 : memoryMemberRepository 중복, 오버라이드 하고 싶다면 따로 설정 부여
        @Bean(name = "memoryMemberRepository")
        MemberRepository memberRepository() {
                return new MemoryMemberRepository();
        }
        **/
}
