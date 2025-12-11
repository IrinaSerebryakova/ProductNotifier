package ru.product.mongodb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.product.mongodb.model.Task;
import ru.product.mongodb.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	public List<Task> getAllTasks(){
		return taskRepository.findAll();
	}
	public Task saveTask(Task task){
		return taskRepository.save(task);
	}
}
