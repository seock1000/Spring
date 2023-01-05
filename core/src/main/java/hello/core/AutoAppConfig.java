package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
// @Component 어노테이션이 붙은 클래스 자동으로 스캔해서 스프링 빈으로 등록해주는 어노테이션
// @Configuration 소스 코드에도 @Component 붙어있음
@ComponentScan(
        // AppConfig 제외하기 위해서 Configuration 어노테이션 붙어있는 클래스는 제외(충돌 방지)
        // 실제로는 제외하지 않지만, 이전 실습 코드 살리기 위해서 제외
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
    // @Bean으로 클래스 등록해 줄 필요 없음
    // 자동으로 등록해주니까
}
