package ru.product.mongodb.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "tasks")
@Getter
@NoArgsConstructor
public class Task {

	@Id
	private String id;

	private String name;
	private String description;

	private Date dueDate;
	private Date completedDate;

	private Integer priority; // например, 1-5
	private String status;    // например, "TODO", "IN_PROGRESS", "DONE"

	private Boolean isPublic;

	private Integer progress; // 0-100%

	private String user; // ID пользователя

	private Team team;
	private Assignee assignee;

	private List<String> num;
	private List<String> tags;

	private List<Category> categories;

	public Task(String name, Date dueDate, Integer priority, String status) {
		this.name = name;
		this.dueDate = dueDate;
		this.priority = priority;
		this.status = status;
	}

	@Override
	public String toString() {
		return "Task{" +
				"name='" + name + '\'' +
				", dueDate=" + dueDate +
				", priority=" + priority +
				", status='" + status + '\'' +
				'}';
	}
}
