package sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancerAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactivefeign.spring.config.EnableReactiveFeignClients;
import reactor.core.publisher.Mono;

@SpringBootApplication(exclude = ReactiveLoadBalancerAutoConfiguration.class)
@EnableEurekaClient
@RestController
@EnableReactiveFeignClients
@EnableFeignClients
public class FeignApplication {

    @Autowired
    private GreetingReactive reactiveFeignClient;

    @Autowired
    private GreetingReactiveWOtherName reactiveFeignClientOther;

    @Autowired
    private Greeting feignClient;

    @Autowired
    private GreetingWOtherName feignClient2;

    @Autowired
    private GreetingReactiveDomainEntity reactiveFeignClient2;

    @Value("${spring.application.name}")
    private String appName;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(FeignApplication.class);
        application.setAllowBeanDefinitionOverriding(true);
        application.run(args);
    }

    @GetMapping("/greetingReactive")
    public Mono<String> greetingReactive() {
        return reactiveFeignClient.greeting().map(s -> "reactive feign! : " + s);
    }

    @GetMapping("/greetingReactiveWithParam")
    public Mono<String> greetingReactiveWithParam(@RequestParam(value = "id") Long id) {
        return reactiveFeignClient.greetingWithParam(id).map(s -> "reactive feign with param! : " + s);
    }

    @GetMapping("/greetingReactiveOther")
    public Mono<String> greetingReactiveOther() {
        return reactiveFeignClientOther.greeting().map(s -> "reactive feign other! : " + s);
    }

    @GetMapping("/greeting")
    public String greeting() {
        return "feign! : " + feignClient.greeting();
    }

    @GetMapping("/greetingOther")
    public String greetingOther() {
        return "feign other! : " + feignClient2.greeting();
    }

    @GetMapping("/greetingReactiveEntity")
    public Mono<GreetingVO> greetingReactiveEntity() {
        return reactiveFeignClient2.greetingReactiveEntity()
                .map(greetingVO -> {
                    System.out.println("before reactive feign domain entity! : " + greetingVO.toString());

                    GreetingVO receivedGreetingVO = new GreetingVO();
                    receivedGreetingVO.setId(10086L);
                    receivedGreetingVO.setFirstName(greetingVO.getFirstName() + " After");
                    receivedGreetingVO.setLastName(greetingVO.getLastName() + " After");
                    receivedGreetingVO.setNickname(greetingVO.getNickname() + " After");

                    System.out.println("after reactive feign domain entity! : " + greetingVO.toString());
                    return receivedGreetingVO;
                });
    }

    @GetMapping("/greetingReactiveEntityWithParam")
    public Mono<GreetingVO> greetingReactiveEntityWithParam(@RequestParam(value = "id") Long id) {
        return reactiveFeignClient2.greetingReactiveEntityWithParam(id)
                .map(greetingVO -> {
                    System.out.println("before reactive feign domain entity by param! : " + greetingVO.toString());

                    GreetingVO receivedGreetingVO = new GreetingVO();
                    receivedGreetingVO.setId(id);
                    receivedGreetingVO.setFirstName(greetingVO.getFirstName() + " After");
                    receivedGreetingVO.setLastName(greetingVO.getLastName() + " After");
                    receivedGreetingVO.setNickname(greetingVO.getNickname() + " After");

                    System.out.println("after reactive feign domain entity by param! : " + greetingVO.toString());
                    return receivedGreetingVO;
                });
    }
}