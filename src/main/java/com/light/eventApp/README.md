Приложение для создания мероприятий и регистрации пользователей на мероприятия.

Стек:
Spring Boot
Spring Data JPA
Java 17
Postgres, Flyway
Spring Security

1. Регистрация пользователя и авторизация:
- Пользователь имеет возможность зарегистрироваться в системе как потенциальный участник или администратор мероприятия

2. Создание мероприятия:
- Администратор мероприятий имеет возможность создать мероприятие, указав его название, стоимость участия и др. при условии, что с ним подписан действительный договор.

3. Подписание договора на проведение мероприятия:
-  Администратор имеет возможность оставить заявку на  подписание договора.

4. Запись на мероприятие:
- Зарегистрированный пользователь имеет возможность записаться на мероприятие,  после проверки данных он становится участником.


<h3> Запуск приложения: </h3>

Запуск БД:
- docker-compose -f docker-compose.yml up -d 

Аутентификация по email и паролю
<h3>3 Роли:</h3>
    <h4>User </h4>
    email: user@gmail.com
    password: password
<h4>Admin </h4>
email: company@gmail.com
password: company
<h4>Principal </h4>
email: user2@gmail.com
password: password


<h3>Swagger: </h3>
http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

<h3> Endpoints:</h3>

<h4> Аккаунт юзера</h4>

POST/GET/DELETE/PUT http://localhost:8080/eventApp/profile

<h4> Аккаунт админа</h4>

GET http://localhost:8080/eventApp/admin/profile

<h4>Работа principal с юзерами</h4>
GET: http://localhost:8080/eventApp/principal/users/{id}
DELETE: http://localhost:8080/eventApp/principal/users/{id}
GET: http://localhost:8080/eventApp/principal/users/by-email - поиск юзера по email
PATCH: http://localhost:8080/eventApp/principal/users/{id} - подключение/отключение юзера
PATCH: http://localhost:8080/eventApp/principal/users/{id}/set-role - установление роли админа
PATCH: http://localhost:8080/eventApp/principal/users/{id}/remove-role - удаление роли админа

<h4>Запись юзера на мероприятие</h4>
POST: http://localhost:8080/eventApp/profile/events/{id}

<h4>Управление админом мероприятиями</h4>
POST: http://localhost:8080/eventApp/admin/events - создание мероприятия
PATCH: http://localhost:8080/eventApp/admin/events - изменение статуса юзера, подавшего заявку
GET: http://localhost:8080/eventApp/admin/events - все мероприятия
GET: http://localhost:8080/eventApp/admin/events/{id}/participants - все участники мероприятия
DELETE: http://localhost:8080/eventApp/admin/events/{id} - удаление мероприятия
PUT: http://localhost:8080/eventApp/admin/events/{id} - изменение мероприятия


<h4>Управление principal мероприятиями</h4>
GET: http://localhost:8080/eventApp/principal/events/{id} <br>
GET: http://localhost:8080/eventApp/principal/events/{id}/participants - все участники мероприятия <br>
GET: http://localhost:8080/eventApp/principal/events/{id}/all - все мероприятия админа <br>
GET: http://localhost:8080/eventApp/principal/events/all - все мероприятия

<h4>Создание заявки на контракт админом</h4>
POST: http://localhost:8080/eventApp/admin/contracts <br>
GET: http://localhost:8080/eventApp/admin/contracts/{id}

<h4>Управление контрактом principal</h4>
PATCH: http://localhost:8080/eventApp/principal/contracts/{id}


