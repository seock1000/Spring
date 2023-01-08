package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
/**
// http 요청 당 하나씩 생성되는 빈
// uuid로 다른 http 요청과 구별
// requestUrl을 빈의 생성으로 알 수 없기 때문에 외부에서 받아옴
 @Scope("request")
**/
// 프록시 사용한 스코프
// proxyMode : 추가하면 프록시 클래스(가짜 클래스)를 만들고 이걸 주입
// 대상이 클래스면 TARGET_CLASS, 인터페이스면 INTERFACES
// 생성된 객체 확인해보면 hello.core.common.MyLogger$$EnhancerBySpringCGLIB$$c6d20a79와 같은 MyLogger 상속받은 가짜 프록시 객체 만들어 주입
// 싱글톤에서 @Configuration 바이트 코드 조작한 것과 유사 -> 동일하게 CGLIB 라이브러리 사용하여 가짜 객체 생성
// 프록시 객체 내부에 진짜 MyLogger 객체를 요청하는 로직이 들어가 있음
// 추후 AOP도 비슷한 원리로 돌아감 -> 클라이언트 코드 변경없이!
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "]" + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "]" + "request scope bean create: " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "]" + "request scope bean close: " + this);
    }
}
