package ru.product.mongodb.model;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Category {
	private String id;
	private String key;
	private Integer score;
}
