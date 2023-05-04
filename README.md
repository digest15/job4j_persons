# job4j_persons

## Job4j Rest API

### Базовая идея REST API.

Допустим, что у нас имеет URI - /api/user/

REST API базируется на простой идеи CRUD операции (create, read, update, delete), которые используют http methods.

GET /api/user/{id} - получить пользователя с таким id.

GET /api/user/ - получить список всех пользователей.

POST /api/user/ - создать пользователя. в теле запроса отправляет данные. в ответ приходит созданный пользователь со сгенерированным ID.

PUT /api/user/ - используется для обновления пользователя. в теле запроса данные. в ответе просто статус выполнения.

DELETE /api/user/{id} - удалить пользователя с id. ответ статус.

Я подчеркиваю внимание, что некоторые запросы возвращают только статус запроса и не возвращает тела. Некоторые библиотеки ajax не вызывают метод обработки, если тело ответа пустое.

Idempotence - это свойство запроса, при котором ответ сервера всегда будет одинаковый.

GET, PUT, DELETE - являются Idempotent запросами. POST - является изменяемым, при каждом вызове будет генерироваться новый ответ.
