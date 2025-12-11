package ru.product.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.product.mongodb.model.Task;

public interface TaskRepository extends MongoRepository<Task, String> {

}
