package tobyspring.config;

import org.springframework.context.annotation.Import;
import tobyspring.config.autoconfig.TomcatWebServerConfig;
import tobyspring.config.autoconfig.DispatcherServletConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
//@Import({DispatcherServletConfig.class, TomcatWebServerConfig.class}) // 정적
@Import(MyAutoConfigImportSelector.class) //동적으로
public @interface EnableMyAutoConfiguration {
}
