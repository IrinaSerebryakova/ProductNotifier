## Руководство по запуску проекта

### Микросервисный проект включает в себя:

1. ProductMicroservice - Producer
2. EmailNotificationMicroservice - Consumer
3. mockservice - обработка ответов со статусом 200 или 500
4. mongodb - база данных.
5. core - библиотека, содержит используемый несколькими микросервисами класс событий ProductCreatedEvent.

### Порядок запуска:

1. mongodb
2. ProductMicroservice
3. mockservice
4. EmailNotificationMicroservice


### Настройка окружения

1. Запустите Docker-контейнеры с Kafka и MongoDB:

```
cd docker-compose
docker-compose -f docker-compose.yml up -d
```

### Сборка и запуск

1. Соберите приложение:

```
mvn clean install -DskipTests
```

2. Запустите приложение.

### Точки доступа

- **Приложение**: [http://localhost:8080/api/](http://localhost:8080/api/)
- **Swagger UI**: [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)
- **SonarQube**: [http://localhost:9000](http://localhost:9000)

Пароли для доступа к SonarQube в браузере:  
login: ```admin```  
password: ```Useruser1983!``` 

Команда для запуска SonarQube локально:  
```
mvn clean verify sonar:sonar \
-Dsonar.projectKey=ProductNotifier \
-Dsonar.projectName='ProductNotifier' \
-Dsonar.host.url=http://localhost:9000 \
-Dsonar.token=sqp_0e1e0a1287cb5b888987a552ce3952964b85a1e2
```

