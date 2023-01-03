package hello.core.xml;

import hello.core.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class XmlAppContext {

    // 지금까지 쓴 방식은 자바 코드로 된 스프링 설정 정보
    // xml 파일을 읽어와서 빈 등록 -> 요즘은 잘 안쓰지만 알아두면 좋음
    // 필요시 공식 레퍼런스 문서 참고
    // 장점 : 컴파일 없이 xml 파일만 교체하면 적용됨
    @Test
    void xmlAppContext() {
        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
