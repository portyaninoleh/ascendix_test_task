package com.ascendix.ascendix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Profile("!test")
@Configuration
@EnableMongoRepositories(basePackages = "com.ascendix.ascendix.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

	@Bean
	MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}
	
	@Override
	protected String getDatabaseName() {
		return "docker-db";
	}
	
    @Override
    public MongoClient mongoClient() {
    	ConnectionString connectionString = new ConnectionString("mongodb://mongodb/docker-db");
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build();
        return MongoClients.create(mongoClientSettings);
    }
}
