package tobyspring.helloboot;

public interface HelloService {
    // 인사말을 만드는 클래스
    String sayHello(String name);

    default int countOf(String name) {
        return 0;
    };

}
