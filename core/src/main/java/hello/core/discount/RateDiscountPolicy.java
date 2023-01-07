package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary // 자주 사용
// ex) main DB와 보조 DB가 있을 때, main DB 사용률이 훨씬 많은 경우 -> main DB primary, 보조 DB Qualifier
// @Qualifier("mainDiscountPolicy") // 빈에 특별한 이름 부여
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
