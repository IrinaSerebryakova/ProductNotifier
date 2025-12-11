package ru.product.microservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.product.microservice.exception.ErrorMessage;
import ru.product.microservice.service.ProductService;
import ru.product.microservice.service.dto.CreateProductDto;

import java.util.Date;

@RestController
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;
	private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping
	public ResponseEntity<Object> createProduct(@RequestBody CreateProductDto createProductDto) {
		String productId = null;
		try {
			productService.createProduct(createProductDto);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(new Date(), e.getMessage()));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(productId);
	}
}
