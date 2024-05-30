package tobyspring.helloboot;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

// dispatcherServlet 이 클래스레벨 부터 찾아보고 메서드 찾아봄


// @MyComponent  // @MyComponent 안에 @Component 가 들어있어서 @Component가 적용된다.
@RestController
public class HelloController{

    // 파라미터 타입이 helloService 인걸 확인하고
    // 컨테이너에 등록된 정보를 뒤져서 helloService 인터페이스를 SimpleHelloService 가 사용한걸 확인해서
    // helloService 생성자에 넣어줄수 있다고 생각함
    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }


    @GetMapping("/hello")
    // @ResponseBody // @RestController 안에 @ResponseBody가 들어있어서 생략가능
//    @RequestMapping(value = "/hello", method = RequestMethod.GET)  -> @GetMapping과 동일하다.
    public String hello(String name) {
        if (name == null || name.trim().length() == 0) throw new IllegalArgumentException();


        return helloService.sayHello(name);
    }

    @GetMapping("/count")
    public String count(String name) {


        return name + " :" + helloService.countOf(name);
    }


    /**
    // 웹 클라이언트가 보낸 요청에 이상이 없는지 확인하는 클래스
    public String hello(String name) {
        // SimpleHelloService helloService = new SimpleHelloService(); // 직접 생성하는 방식 (사용 x)

        //Objects.requireNonNull(name) : 널이면 오류나고 아니면 반환
        return helloService.sayHello(Objects.requireNonNull(name));
    }
     */
}
