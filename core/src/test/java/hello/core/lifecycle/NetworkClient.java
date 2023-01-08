package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작 시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message: " + message);
    }

    // 서비스 종료 시 호출
    public void disconnect() {
        System.out.println("close: " + url);
    }

    /**
     * implements InitializingBean, DisposableBean
    // 스프링 전용 인터페이스에 의존
    // 초기화, 소멸 메서드 이름 변경 불가
    // 외부 라이브러리(코드 수정 불가한)에 적용 불가
    // 초창기 방법, 지금은 거의 사용 X
    // InitializingBean 인터페이스 상속을 통한 초기화 메서드
    @Override
    public void afterPropertiesSet() throws Exception {
        connect();
        call("초기화 연결 메시지");
    }

    // DisposableBean 인터페이스 상속을 통한 소멸 전 메서드 호출
    @Override
    public void destroy() throws Exception {
        disconnect();
    }
    **/

    /**
     // 설정 정보 @Bean에
    * @Bean(initMethod = "init", destroyMethod = "close")
    // 메서드 이름 자유롭게 가능
    // 스프링 코드에 의존 X
    // 외부 라이브러리에도 초기화, 종료 메서드 적용 가능
    // 외부 라이브러리의 종료 메서드는 대부분 close 또는 shutdown
    // destroyMethod의 기본 값은 (inferred) = (추론)
    // (inferred)는 close 또는 shutdown이라는 이름의 메서드를 자동으로 찾아서 호출
    // 종료 메서드 자동 호출을 막고싶다면(그럴일 없겠지만..) destroyMethod = ""로 설정
    public void init() {
        connect();
        call("초기화 연결 메시지");
    }

    public void close() {
        disconnect();
    }
    **/

    // 가장 많이 쓰이는 방법 + 권장하는 방법
    // javax에서 지원 -> 자바 표준(JSR-250)으로 스프링 외 다른 컨테이너에서도 사용 가능
    // 컴포넌트 스캔과도 잘 어울리는 방식
    // 유일한 단점은 외부 라이브러리에 적용 불가능
    // 외부 라이브러리의 초기화, 종료가 필요하면 @Bean의 기능(initMethod, destroyMethod) 사용
    @PostConstruct
    public void init() {
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close() {
        disconnect();
    }
}
