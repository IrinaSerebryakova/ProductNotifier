package ru.product.mongodb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@NoArgsConstructor
public class User {

	@Id
	private String id;
	private String name;
	private String email;


	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}


	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
