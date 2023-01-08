package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        assertThat(prototypeBean1).isInstanceOf(PrototypeBean.class);
        assertThat(prototypeBean2).isInstanceOf(PrototypeBean.class);
        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        // 프로토 타입 빈은 생성, DI 외는 클라이언트가 직접 관리
        // 프로토타입 빈은 종료 메서드 호출 필요시 클라이언트가 직접 호출
        prototypeBean1.destroy();
        prototypeBean2.destroy();

        ac.close();
    }

    // prototype
    // 빈을 요청 시에 생성, DI, 초기화만 관여하고 반환, 이후 스프링에서 관리 X
    // 따라서, 종료 메서드 호출 X
    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
