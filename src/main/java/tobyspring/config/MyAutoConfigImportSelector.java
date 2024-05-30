package tobyspring.config;

import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class MyAutoConfigImportSelector implements DeferredImportSelector {

    private final ClassLoader classLoader;

    public MyAutoConfigImportSelector(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }


    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
//        return new String[] {
//                "tobyspring.config.autoconfig.DispatcherServletConfig",
//                "tobyspring.config.autoconfig.TomcatWebServerConfig"
//        };


        List<String> autoConfigs = new ArrayList<>();


        /**
         *  ImportCandidates.load() : 자동으로 등록할 빈 클래스 이름이 담긴 파일 정보 전달
         *  ()안에 적어준 클래스명 적는 곳에 애노테이션 적어주는게 관례.
         *  그 애노테이션 앞에 패키지위치와, 뒤에 imports 확장자 붙여서 그이름으로 된 파일 찾음
         *
         */
        ImportCandidates.load(MyAutoConfiguration.class, classLoader).forEach(autoConfigs::add);


        return autoConfigs.toArray(new String[0]);
//        return autoConfigs.stream().toArray(String[]::new);
    }
}
