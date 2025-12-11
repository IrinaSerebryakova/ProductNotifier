package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import exception.NonRetryableException;
import exception.RetryableException;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;
import org.springframework.web.client.HttpServerErrorException;
import ru.product.microservice.core.ProductCreatedEvent;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Bean
	public ConsumerFactory<String, ProductCreatedEvent> consumerFactory(ObjectMapper objectMapper) {
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
		config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
		config.put(JsonDeserializer.TRUSTED_PACKAGES, "ru.product.microservice.core");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "product-created-events");

		JsonDeserializer<ProductCreatedEvent> deserializer = new JsonDeserializer<>(objectMapper);
		deserializer.addTrustedPackages("ru.product.microservice.core");

		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
	}

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, ProductCreatedEvent> kafkaListenerContainerFactory(
			ConsumerFactory<String, ProductCreatedEvent> consumerFactory, KafkaTemplate kafkaTemplate) {

		DefaultErrorHandler errorHandler = new DefaultErrorHandler(new DeadLetterPublishingRecoverer(kafkaTemplate),
				new FixedBackOff(3000,3));
		errorHandler.addNotRetryableExceptions(NonRetryableException.class);
		errorHandler.addRetryableExceptions(RetryableException.class);

		ConcurrentKafkaListenerContainerFactory<String, ProductCreatedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConcurrency(1);
		factory.setConsumerFactory(consumerFactory);
		factory.setCommonErrorHandler(errorHandler);
		return factory;
	}

	@Bean
	KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
		return new KafkaTemplate<>(producerFactory);
	}

	@Bean
	ProducerFactory<String, Object> producerFactory() {
		Map<String, Object> config = new HashMap<>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(config);
	}
}
