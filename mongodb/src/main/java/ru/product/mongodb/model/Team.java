package ru.product.mongodb.model;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Team {
	private String id;
	private Location location;
}
