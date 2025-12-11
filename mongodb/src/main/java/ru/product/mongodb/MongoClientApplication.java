package ru.product.mongodb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.product.mongodb.model.Task;
import ru.product.mongodb.model.User;
import ru.product.mongodb.service.TaskService;
import ru.product.mongodb.service.UserService;

import java.util.Date;

@SpringBootApplication
public class MongoClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoClientApplication.class, args);
	}

	@Bean
	CommandLineRunner initUsers(UserService userService) {
		return args -> {
			userService.saveUser(new User("Irina", "i.serebryakova@gmail.com"));
			userService.saveUser(new User("John", "j.smit@gmail.com"));

			System.out.println("Сохранённые пользователи:");
			userService.getAllUsers().forEach(System.out::println);
		};
	}

	@Bean
	CommandLineRunner initTasks(TaskService taskService) {
		return args -> {
			taskService.saveTask(new Task("Learn MongoDB Topic 1",
					new Date(), 1, "TODO"));

			taskService.getAllTasks().forEach(System.out::println);
		};
	}
}
