package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.config.property.SystemConfig;

@SpringBootApplication
@MapperScan("com.example.mapper")
@EnableTransactionManagement
@EnableConfigurationProperties(value = { SystemConfig.class })
public class QzCApplication {

	public static void main(String[] args) {
		SpringApplication.run(QzCApplication.class, args);
	}

}
