# feign-reactive-sample

Sample of reactive feign in cloud mode

1. start EurekaApplication 
2. start WebFluxApplication
3. start FeignApplication

I have defined domain entity response in **GreetingReactiveDomainEntity.java**

1) visit url:

[http://localhost:8090/greetingReactiveEntityWithParam?id=1](http://localhost:8090/greetingReactiveEntityWithParam?id=1)

**response data**

`{
id: 1,
nickname: "bb2",
first_name: "abc2",
last_name: "bb"
}`

2) visit url:

[http://localhost:8080/greetingReactiveEntityWithParam?id=1](http://localhost:8080/greetingReactiveEntityWithParam?id=1)

**now output data**

`{
id: 1,
nickname: "bb2 After",
first_name: "null After",
last_name: "null After"
}`

I want to get like under,

`{
id: 1,
nickname: "bb2 After",
first_name: "abc2 After",
last_name: "bb After"
}`

because I have config **application.yml** in feign project

`spring.jackson.property-naming-strategy: SNAKE_CASE`