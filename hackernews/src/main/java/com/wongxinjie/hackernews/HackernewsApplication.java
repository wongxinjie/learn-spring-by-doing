package com.wongxinjie.hackernews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@SpringBootApplication
public class HackernewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackernewsApplication.class, args);
	}

	@Bean
	public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}
}
