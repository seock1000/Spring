package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

// 같은 타입의 스프링 빈이 여러개 필요한 경우
// ex) 할인방법을 선택 가능한 경우 등
public class AllBeanTest {

    @Test
    void findAllBena() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        // AutoAppConfig를 스프링 빈으로 등록한 다음, DiscountService를 스프링 빈으로 등록

        DiscountService discountService = ac.getBean(DiscountService.class);
        // when
        Member member = new Member(1L, "userA", Grade.VIP);

        // if
        int discountPrice = discountService.discount(member, 1000, "fixDiscountPolicy");
        int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");

        // then
        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);

        assertThat(rateDiscountPrice).isEqualTo(2000);
    }

    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policyList;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policyList) {
            this.policyMap = policyMap;
            this.policyList = policyList;

            System.out.println("policyMap = " + policyMap);
            System.out.println("policyList = " + policyList);
        }

        public int discount(Member member, int i, String discountCode) {
            // PolicyMap의 객체(빈)를 discountCode(빈 이름)으로 꺼내와서
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            // 해당 객체(빈)으로 discount
            return discountPolicy.discount(member, i);
        }
    }
}
