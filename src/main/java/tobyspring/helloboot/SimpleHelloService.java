package tobyspring.helloboot;

import org.springframework.stereotype.Service;

@Service
public class SimpleHelloService implements HelloService {

    private final HelloRepository helloRepository;

    public SimpleHelloService(HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }

    // 인사말을 만드는 클래스
    @Override
    public String sayHello(String name) {
        this.helloRepository.increaseCount(name);

        return "Hello " + name;
    }

    @Override
    public int countOf(String name) {
        return helloRepository.countOf(name);
    }
}
