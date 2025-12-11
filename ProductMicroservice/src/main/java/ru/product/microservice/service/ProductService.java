package ru.product.microservice.service;

import ru.product.microservice.service.dto.CreateProductDto;

import java.util.concurrent.ExecutionException;

public interface ProductService {

	String createProduct(CreateProductDto createProductDto) throws ExecutionException, InterruptedException;
}
