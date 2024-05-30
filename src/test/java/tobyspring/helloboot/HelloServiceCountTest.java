package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tobyspring.helloboot.HelloRepository;
import tobyspring.helloboot.HelloService;
import tobyspring.helloboot.HellobootTest;

import java.util.stream.IntStream;

@HellobootTest
public class HelloServiceCountTest {

    @Autowired
    HelloService helloService;

    @Autowired
    HelloRepository helloRepository;

    @Test
    void sayHelloIncreaseCount() {
        IntStream.rangeClosed(1, 10).forEach(count -> {
            helloService.sayHello("Yujin");
            Assertions.assertThat(helloRepository.countOf("Yujin")).isEqualTo(count);
        });


    }
}
