package tobyspring.helloboot;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)   // 어디까지 살아있을것인가, 언제까지 유지될것인가
@Target(ElementType.TYPE)             // 애노테이션 타깃위치 TYPE은 클래스나 인터페이스에 적용되는것
@Component
public @interface MyComponent {
    // 메타 애노테이션 : 애노테이션 위에 어노테이션 붙은것
}
