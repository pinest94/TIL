package io.pinest94.reactivestreams.futures;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RemoteService {

    @RestController
    public static class MyController {
        @GetMapping("/service")
        public String service(@RequestParam("req") String req) throws InterruptedException {
            Thread.sleep(2000);
            return req + "/service";
        }

        @GetMapping("/service2")
        public String service2(@RequestParam("req") String req) throws InterruptedException {
            Thread.sleep(2000);
            return req + "/service2";
        }
    }

    public static void main(String[] args) {
        System.setProperty("server.port", "8081");
        System.setProperty("server.tomcat.threads.max", "1000");
        SpringApplication.run(RemoteService.class, args);
    }
}
