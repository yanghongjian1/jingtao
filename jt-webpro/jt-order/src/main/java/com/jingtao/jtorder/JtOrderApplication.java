package com.jingtao.jtorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableSwagger2
@ComponentScan({"com.jingtao.jtcommon", "com.jingtao.jtorder"})
public class JtOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(JtOrderApplication.class, args);
    }

}
