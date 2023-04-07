package com.muchenlou.phototovideo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.muchenlou.phototovideo.mapper")
@EnableAsync
public class PhotoToVideoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoToVideoApplication.class, args);
	}

}
