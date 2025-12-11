package ru.product.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.product.mongodb.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
