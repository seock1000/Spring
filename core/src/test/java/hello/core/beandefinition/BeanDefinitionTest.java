package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class BeanDefinitionTest {

    // 아래의 경우에는 getBeanDefinition 등 사용 불가능 -> 실제로 Bean Definition을 뽑아서 쓸 일이 없기 때문!
    //ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    GenericXmlApplicationContext gc = new GenericXmlApplicationContext("appConfig.xml");

    // XML, 자바 코드 등에서 빈 설정 메타 정보 읽어옴
    // 메타 정보를 기반으로 스프링 컨테이너는 인스턴스 생성 -> 스프링 컨테이너는 빈 메타정보에 의존
    // 스프링은 다양한 형태의 설정 정보를 Bean Definition으로 추상화하여 사용

    // XML은 빈을 직접 등록하는 방법
    // 자바 Config(자바 코드)는 factoryBeanName과 factoryMethodName을 통해서 우회하여 빈을 등록

    @Test
    @DisplayName("빈 설정 메타정보(Bean Definition) 확인")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for(String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("beanDefinitionName = " + beanDefinitionName +
                        "beanDefinition = " + beanDefinition);
            }
        }
    }

    @Test
    @DisplayName("XML 빈 설정 메타정보(Bean Definition) 확인")
    void findXmlApplicationBean() {
        String[] beanDefinitionNames = gc.getBeanDefinitionNames();
        for(String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = gc.getBeanDefinition(beanDefinitionName);

            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("beanDefinitionName = " + beanDefinitionName +
                        "beanDefinition = " + beanDefinition);
            }
        }
    }
}
