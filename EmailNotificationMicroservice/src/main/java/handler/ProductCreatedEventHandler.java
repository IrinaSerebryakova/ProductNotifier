package handler;

import exception.NonRetryableException;
import exception.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import ru.product.microservice.core.ProductCreatedEvent;

@Component
@KafkaListener(topics = "product-created-events-topic",
		groupId = "product-created-events")
public class ProductCreatedEventHandler {

	private RestTemplate restTemplate;
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductCreatedEventHandler.class);

	public ProductCreatedEventHandler(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@KafkaHandler
	public void handle(ProductCreatedEvent productCreatedEvent) {
		LOGGER.info("Received event: {}", productCreatedEvent.getTitle());
		String url = "http://localhost:8090/response/200";
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
			if(response.getStatusCode().value() == HttpStatus.OK.value()){
				LOGGER.info("Received response: {}", response.getBody());
			}
		} catch (ResourceAccessException e) {
			LOGGER.error(e.getMessage());
			throw new RetryableException(e);
		} catch(HttpServerErrorException ex){
			LOGGER.error(ex.getMessage());
			throw new NonRetryableException(ex);
		}catch(Exception exc){
			LOGGER.error(exc.getMessage());
			throw new NonRetryableException(exc);
		}
	}
}
