package tobyspring.config.autoconfig;

import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import tobyspring.config.MyAutoConfiguration;

//@MyAutoConfiguration
//public class ServerPropertiesConfig {
//    @Bean
//    public ServerProperties serverProperties(Environment environment) {
//        return Binder.get(environment).bind("", ServerProperties.class).get();
//
//
//   ================tobyspring.config.autoconfig.ServerPropertiesConfig (imports 파일에 추가)====
//
//
////        ServerProperties properties = new ServerProperties();
////
////        properties.setContextPath(environment.getProperty("contextPath"));
////        properties.setPort(Integer.parseInt(environment.getProperty("port")));
////
////        return properties;
//    }
//}
