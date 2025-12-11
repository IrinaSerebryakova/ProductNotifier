package ru.product.mongodb.model;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Location {
		private String address;
		private Double lat;
		private Double lng;
}
