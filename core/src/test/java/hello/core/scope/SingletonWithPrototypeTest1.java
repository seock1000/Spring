package hello.core.scope;

import ch.qos.logback.core.net.server.Client;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    @DisplayName("프로토타입 빈 count 증가 테스트")
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);

    }

    @Test
    @DisplayName("싱글톤과 프로토타입 동시 사용 테스트 - ObjectProvider")
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(
                        ClientBean.class,
                        PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);

    }

    @Scope("singleton")
    static class ClientBean {

        /**
        private final PrototypeBean prototypeBean;

        // 생성 시점에 주입
        // 따라서, ClientBean이 여러번 호출되어도 생성 시점에 주입된 프로토타입 빈 계속 사용
        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }
         **/


        /**
         * 원하는 대로 사용할 때마다 새로운 프로토타입 빈 생성
         * 그러나 다소 무식한 방법
         * 스프링에 너무 의존적
         @Autowired
         private ApplicationContext ac;

         public int logic() {
         PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
         prototypeBean.addCount();
         return prototypeBean.getCount();
         }
         **/


        /**
        // ObjectProvider : getObject 메서드 호출하면 스프링 컨테이너가 지정한 빈을 찾아서 반환
        // ObjectFactory : getObject만 제공, ObjectProvider는 여기에 편의기능 추가
        // 핵심은 DL(Dependency Lookup)을 간단하게 해주는 것
        // DL : DI와 달리 주입은 X, 단지 찾아서 반환

        private ObjectProvider<PrototypeBean> prototypeBeanObjectProvider;

        @Autowired
        public ClientBean(ObjectProvider<PrototypeBean> prototypeBeanObjectProvider) {
            this.prototypeBeanObjectProvider = prototypeBeanObjectProvider;
        }
        **/

        // Provider
        // 자바 표준 -> 스프링 외의 컨테이너에서도 사용 가능
        // 장점이자 단점 : 매우 심플 - DL에 필요한 get 메서드만 제공
        // 단점 : 별로의 라이브러리 필요
        private Provider<PrototypeBean> prototypeBeanProvider;

        @Autowired
        public ClientBean(Provider<PrototypeBean> prototypeBeanProvider) {
            this.prototypeBeanProvider = prototypeBeanProvider;
        }

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            int cnt = prototypeBean.getCount();
            return cnt;
        }

        // ObjectProvider(스프링 제공) vs Provider(자바 표준)
        // JPA의 경우에는 자바 표준 승리 - JPA 사용하고 구현체로 하이버네이트 쓰는 편
        // 스프링은 거의 표준처럼 사용되기 때문에 스프링 제공과 자바표준이 대립할 때에는 기능보고 선택
        // 만약 기능이 비슷하면 자바 표준이 나을 듯
        // 사실 그냥 더 맘에 드는 거 쓰면 됨
    }


    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count ++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
