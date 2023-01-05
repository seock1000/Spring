package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

        // ThreadA : userA가 10000원 주문
        statefulService1.order("userA", 10000);
        // ThreadB : userB가 20000원 주문
        statefulService2.order("userB", 20000);

        // ThreadA : userA가 주문 금액 조회
        int price = statefulService1.getPrice();

        System.out.println("price = " + price);

        // userA의 주문 금액이 20000원으로 조회
        // 싱글톤 패턴에서 stateful 필드를 사용해서 발생하는 문제
        // 특정 클라이언트가 공유되는 값을 변경하게 됨
        Assertions.assertThat(price).isEqualTo(20000);
    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}