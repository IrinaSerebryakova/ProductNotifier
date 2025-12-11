package ru.product.microservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.product.microservice.core.ProductCreatedEvent;
import ru.product.microservice.service.dto.CreateProductDto;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class ProductServiceImpl implements ProductService {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	public ProductServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	public String createProduct(CreateProductDto createProductDto) throws ExecutionException, InterruptedException {
		//TODO save DB
		String productId = UUID.randomUUID().toString();
		ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(
				productId,
				createProductDto.getTitle(),
				createProductDto.getPrice(),
				createProductDto.getQuantity());

		SendResult<String, Object> result = kafkaTemplate.send(
				"product-created-events-topic", productId, productCreatedEvent).get();

		LOGGER.info("Topic: {}", result.getRecordMetadata().topic());
		LOGGER.info("Partition: {}", result.getRecordMetadata().partition());
		LOGGER.info("Offset: {}", result.getRecordMetadata().offset());

		LOGGER.info("Return: {}", productId);

		return productId;
	}
}
