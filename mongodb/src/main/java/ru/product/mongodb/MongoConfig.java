package ru.product.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

	@Value("${spring.data.mongodb.uri}")
	private String uri;

	MongoClient mongoClient = MongoClients.create(uri);
	MongoDatabase database = mongoClient.getDatabase("testDataBase");


}
