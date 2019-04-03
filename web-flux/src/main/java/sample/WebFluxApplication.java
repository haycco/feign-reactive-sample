package sample;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class WebFluxApplication {

    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Value("${spring.application.name}")
    private String appName;

    public static void main(String[] args) {
        SpringApplication.run(WebFluxApplication.class, args);
    }

    @GetMapping("/greeting")
    public Mono<String> greeting() {
        String idInEureka = eurekaClient.getApplication(appName).getInstances().get(0).getId();
        return Mono.just(String.format("Hello from '%s'!", idInEureka));
    }

    @GetMapping("/greetingWithParam")
    public Mono<String> greetingWithParam(@RequestParam(value = "id") Long id) {
        String idInEureka = eurekaClient.getApplication(appName).getInstances().get(0).getId();
        return Mono.just(String.format("Hello with param from '%s'!", idInEureka));
    }

    @GetMapping("/greetingReactiveEntity")
    public Mono<GreetingVO> greetingReactiveEntity(){
        GreetingVO greetingVO = new GreetingVO();
        greetingVO.setId(10086L);
        greetingVO.setFirstName("abc1");
        greetingVO.setLastName("bb");
        greetingVO.setNickname("bb1");
        return Mono.just(greetingVO);
    }

    @GetMapping("/greetingReactiveEntityWithParam")
    public Mono<GreetingVO> greetingReactiveEntityWithParam(@RequestParam(value = "id") Long id){
        GreetingVO greetingVO = new GreetingVO();
        greetingVO.setId(id);
        greetingVO.setFirstName("abc2");
        greetingVO.setLastName("bb");
        greetingVO.setNickname("bb2");
        return Mono.just(greetingVO);
    }
}
