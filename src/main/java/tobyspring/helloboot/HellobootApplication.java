package tobyspring.helloboot;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import tobyspring.config.MySpringBootApplication;

@MySpringBootApplication
public class HellobootApplication {

	// property - application.properties, system, environment
//	@Bean
//	ApplicationRunner applicationRunner(Environment env) {
//		return args -> {
//			String name = env.getProperty("my.name");
//			System.out.println("my.name: " + name);
//		};
//	}

	private final JdbcTemplate jdbcTemplate;

	public HellobootApplication(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@PostConstruct
	void init() {
		jdbcTemplate.execute("create table if not exists hello(name varchar(50) primary key, count int)");

	}

	public static void main(String[] args) {
		SpringApplication.run(HellobootApplication.class, args);
	}

}


/**
 * 내가 만든 SpringApplication
 * 결론은 원래 기존 형태였던
 * SpringApplication.run(HellobootApplication.class, args)
 * 와 형태가 유사하다.
 *
@Configuration
@ComponentScan // @ComponentScan : @Component가 붙어있는 클래스와 하위 패키지를 뒤져서 Bean을 등록
public class HellobootApplication {

	@Bean
	public ServletWebServerFactory servletWebServerFactory() {
		return new TomcatServletWebServerFactory();
	}

	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}

	public static void main(String[] args) {
		MySpringApplication.run(HellobootApplication.class, args);
	}

}
*/



/**
 *
 * 팩토리 메서드 사용
 *
@Configuration
public class HellobootApplication {
	@Bean
	public HelloController helloController(HelloService helloService) {
		return new HelloController(helloService);
	}

	@Bean
	public HelloService helloService() {
		return new SimpleHelloService();
	}

	public static void main(String[] args) {

		// 스프링 컨테이너는 싱글톤패턴처럼 동작
		// 안에 Bean을 만들어 놓으면 동일한 오브젝트 반환 (하나의 오브젝트만 만들어 놓고 재사용)

		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
			@Override
			protected void onRefresh() {
				super.onRefresh();

				ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
				WebServer webServer = serverFactory.getWebServer(servletContext -> {

					// DispatcherServlet : MVC에서 모든 요청 처리
					servletContext.addServlet("dispatcherServlet",
							new DispatcherServlet(this)
					).addMapping("/*"); //이 아래로 들어오는 url은 모두 받겠다.
				});
				webServer.start();
			}
		}; // 스프링 컨테이너 생성

		applicationContext.register(HellobootApplication.class);
		applicationContext.refresh();


	}

}
*/




/**
 * 독립 실행형 스프링 애플리케이션
 *
 *
@SpringBootApplication
public class HellobootApplication {

	public static void main(String[] args) {

		// 스프링 컨테이너는 싱글톤패턴처럼 동작
		// 안에 Bean을 만들어 놓으면 동일한 오브젝트 반환 (하나의 오브젝트만 만들어 놓고 재사용)

		GenericApplicationContext applicationContext = new GenericApplicationContext(); // 스프링 컨테이너 생성

		// 스프링 컨테이너에는 어떤 클래스를 이용해서 Bean 오브젝터를 생성할 것인가로 등록
		applicationContext.registerBean(HelloController.class); // Bean 등록
		applicationContext.registerBean(SimpleHelloService.class); // Bean 등록

		// 스프링 컨테이너에 자기가 가진 구성정보로 컨테이너 초기화 하는것
		applicationContext.refresh(); // Bean 오브젝트 생성


		//빈 서블릿
		ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		WebServer webServer = serverFactory.getWebServer(servletContext -> {

			servletContext.addServlet("frontController", new HttpServlet() {
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					// 인증, 보안, 다국어, 공통 기능 모두 처리 - 프론트 컨트롤러에 이런기능들 들어감

					//  /hello 주소며  +  GET 으로 들어온것만 처리한다.
					if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
						// ========== 요청 ==========
						String name = req.getParameter("name");

						// getBean 으로 bean오브젝트 가져옴 ()안에 빈 이름으로 가져와도 괜찮고 클래스로도 가능
						// 서블릿 컨테이너 쪽에서는 타입은 신경 x
						HelloController helloController = applicationContext.getBean(HelloController.class);
						String ret = helloController.hello(name);

						// ========== 응답 ==========
						resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
						resp.getWriter().println(ret);

					} else if (req.getRequestURI().equals("/user")) {

					} else {  //   해당하는 주소가 없다면
						resp.setStatus(HttpStatus.NOT_FOUND.value());
					}

				}
			}).addMapping("/*"); //이 아래로 들어오는 url은 모두 받겠다.
		});
		webServer.start();
	}

}
*/




/**
 * 독립 실행형 서블릿 애플리케이션
 * - 프론트 컨트롤러로 전환 버전
 *
@SpringBootApplication
public class HellobootApplication {

	public static void main(String[] args) {
		//SpringApplication.run(HellobootApplication.class, args)


		//빈 서블릿
		ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		WebServer webServer = serverFactory.getWebServer(servletContext -> {
			HelloController helloController = new HelloController();

			servletContext.addServlet("frontController", new HttpServlet() {
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					// 인증, 보안, 다국어, 공통 기능 모두 처리 - 프론트 컨트롤러에 이런기능들 들어감

					//  /hello 주소며  +  GET 으로 들어온것만 처리한다.
					if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
						// ========== 요청 ==========
						String name = req.getParameter("name");

						// HelloController 클래스를 활용
						String ret = helloController.hello(name);

						// ========== 응답 ==========
						// resp.setStatus(HttpStatus.OK.value());   -> 생략가능
						resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
						// resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
						resp.getWriter().println(ret);

					} else if (req.getRequestURI().equals("/user")) {

					} else {  //   해당하는 주소가 없다면
						resp.setStatus(HttpStatus.NOT_FOUND.value());
					}

				}
			}).addMapping("/*"); //이 아래로 들어오는 url은 모두 받겠다.
		});
		webServer.start();
	}

}
*/



/**
 * 독립 실행형 서블릿 애플리케이션
 * - 서블릿 생성 버전
 *
@SpringBootApplication
public class HellobootApplication {

	public static void main(String[] args) {
		//SpringApplication.run(HellobootApplication.class, args)


		//빈 서블릿
		ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		WebServer webServer = serverFactory.getWebServer(servletContext -> {
			servletContext.addServlet("hello", new HttpServlet() {
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					// ========== 요청 ==========
					String name = req.getParameter("name");


					// ========== 응답 ==========
					//웹 응답의 3가지 요소 : 상태코드, 헤더(바디의 컨텐트 타입), 바디
					resp.setStatus(200);
					// resp.setStatus(HttpStatus.OK.value()); (위 내용과 동일)

					resp.setHeader("Content-Type", "text/plain");
					// resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);   (위 내용과 동일)

					resp.getWriter().println("Hello " + name);
				}
			}).addMapping("/hello");
		});
		webServer.start();
	}

}
 */


