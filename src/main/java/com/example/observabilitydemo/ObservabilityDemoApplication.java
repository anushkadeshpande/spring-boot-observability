package com.example.observabilitydemo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.example.observabilitydemo.post.JsonPlaceholderService;
import com.example.observabilitydemo.post.Post;

@SpringBootApplication
public class ObservabilityDemoApplication {

	private static final Logger log = LoggerFactory.getLogger(ObservabilityDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ObservabilityDemoApplication.class, args);
	}

	@Bean
	JsonPlaceholderService jsonPlaceholderService() {
		RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com");
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
		return factory.createClient(JsonPlaceholderService.class);
	}

	@Bean
	CommandLineRunner commandLineRunner(JsonPlaceholderService jsonPlaceholderService) {
		return args -> {
			List<Post> posts = jsonPlaceholderService.findAll();
			log.info("Posts: {}", posts.size());
		};
	}
}
