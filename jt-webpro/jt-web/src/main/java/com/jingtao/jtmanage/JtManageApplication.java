package com.jingtao.jtmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableSwagger2
@ComponentScan({"com.jingtao.jtcommon", "com.jingtao.jtmanage"})
public class JtManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(JtManageApplication.class, args);
    }

}

