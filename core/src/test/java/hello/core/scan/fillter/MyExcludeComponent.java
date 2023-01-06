package hello.core.scan.fillter;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

// 커스터 마이즈 어노테이션
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}
