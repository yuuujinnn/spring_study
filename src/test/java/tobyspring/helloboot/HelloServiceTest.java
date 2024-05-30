package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


//@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.METHOD)
//@UnitTest
//@interface FastUnitTest {
//
//}
//
//@Retention(RetentionPolicy.RUNTIME)
//@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})    //ElementType.ANNOTATION_TYPE 작성시 다른데서 사용가능
//@Test
//@interface UnitTest {
//
//}

public class HelloServiceTest {

    @Test
    void simpleHelloService() {
        SimpleHelloService helloService = new SimpleHelloService(getHelloRepositoryStub);

        String ret = helloService.sayHello("Test");

        Assertions.assertThat(ret).isEqualTo("Hello Test");
    }

    private static HelloRepository getHelloRepositoryStub = new HelloRepository() {
        @Override
        public Hello findHello(String name) {
            return null;
        }

        @Override
        public void increaseCount(String name) {

        }
    };

    @Test
    void helloDecorator() {
        HelloDecorator decorator = new HelloDecorator(name -> name);
        String ret = decorator.sayHello("Test");
        Assertions.assertThat(ret).isEqualTo("*Test*");
    }
}
