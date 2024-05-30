package tobyspring.config.autoconfig;

import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;
import tobyspring.config.ConditionalMyOnClass;
import tobyspring.config.EnableMyConfigurationProperties;
import tobyspring.config.MyAutoConfiguration;

@MyAutoConfiguration
@ConditionalMyOnClass("org.apache.catalina.startup.Tomcat")
@EnableMyConfigurationProperties(ServerProperties.class)
public class TomcatWebServerConfig {
//    @Value("${contextPath:}")
//    String contextPath;
//
//    @Value("${port:8080}")
//    int port;


    @Bean("tomcatWebServerFactory")
    @ConditionalOnMissingBean //같은 타입의 빈이 만들어진게 없으면 이게 나타남
    public ServletWebServerFactory servletWebServerFactory(ServerProperties properties) {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();

        factory.setContextPath(properties.getContextPath());  // 모든 코드 앞에 app 가 붙어야함
        factory.setPort(properties.getPort());

        return factory;
    }



//    @Bean("tomcatWebServerFactory")
//    @ConditionalOnMissingBean //같은 타입의 빈이 만들어진게 없으면 이게 나타남
//    public ServletWebServerFactory servletWebServerFactory(Environment env) {
//        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
//        factory.setContextPath(env.getProperty("contextPath"));  // 모든 코드 앞에 app 가 붙어야함
//        return factory;
//    }



//    static class TomcatCondition implements Condition {
//        @Override
//        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
//            return false;
//        }
//    }
}
