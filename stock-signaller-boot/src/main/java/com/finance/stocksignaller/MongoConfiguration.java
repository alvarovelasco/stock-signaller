package com.finance.stocksignaller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfiguration {

	@Value("${spring.data.mongodb.uri}")
	private String connectionString;

	@Value("${spring.data.mongodb.database}")
	private String databaseName;

	public @Bean MongoClient mongoClient() {
		return MongoClients.create(connectionString);
	}

	public @Bean MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient(), databaseName);
	}

}
