package com.cigarette;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author EdwardLee
 */
@SpringBootApplication(scanBasePackages = {"com.cigarette"})
@MapperScan("com.cigarette.mapper")
@EnableSwagger2
public class MaterialCountingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaterialCountingApplication.class, args);
	}

}