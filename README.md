# Сервис для управления книгами

### Языки и технологии
<div>
    <a href="https://www.java.com/"><img src="https://github.com/devicons/devicon/blob/master/icons/java/java-original-wordmark.svg" title="Java" alt="Java" width="40" height="40"/>&nbsp;</a>
    <a href="https://spring.io/"><img src="https://github.com/devicons/devicon/blob/master/icons/spring/spring-original-wordmark.svg" title="Spring" alt="Spring" width="40" height="40"/>&nbsp;</a>
    <a href="https://www.postgresql.org/"><img src="https://github.com/devicons/devicon/blob/master/icons/postgresql/postgresql-original.svg" title="Postgres" alt="Postgres" width="40" height="40"/>&nbsp;</a>
    <a href="https://redis.io/"><img src="https://github.com/devicons/devicon/blob/master/icons/redis/redis-original.svg" title="Redis" alt="Redis" width="40" height="40"/>&nbsp;</a>
</div>

## Содержание
1. [Начало работы](#start)
2. [Запуск тестов](#tests)
3. [Установка приложения](#install)
4. [Настройка приложения](#properties)
5. [Запуск приложения](#bootstrup)

## <a id="start"> Как начать работу с приложением</a>

* Для начала работы необходимо JDK 17 , или выше, и Docker compose;
* Для запуска тестов необходимо скопировать репозиторий с GitHub.
~~~Shell
git clone https://github.com/SaprunovEV/library-service.git
~~~
* Перейти в папку проекта
~~~Shell
cd library-service
~~~

## <a id="tests">Как запустить тесты</a>
```shell
./gradlew test
```

## <a id="install">Как установить приложение</a>
Для установки приложения предусмотренно два способа:
1. только с помощью JVM;
2. с помощью docker.

#### 1:
```shell
./gradlew build
```
#### 2:
```shell
./gradlew build
docker -t library-app .
```
## <a id="properties">Настройки приложения</a>

### Настройки подключения к базе данных

Для удобной настройки доступа к базе данных существует ряд настроек:
* DATA_BASE_HOST - хост базы данных, по умолчанию "localhost";
* DATA_BASE_PORT - порт базы данных, по умолчанию "5432";
* POSTGRES_USER - пользователь базы данных, по умолчанию "postgres";
* POSTGRES_PASSWORD - пароль к базе данных, по умолчанию "postgres".

### Настройки кеширования

Кеширование данного приложения осуществляется с помощью Redis. Для настроек существует ряд параметров:
* APP_CACHE_ENABLE - принимает true или false, и отвечает за включение или выключение кеширования, по умолчанию "true";
* REDIS_HOST - хост на котором работает Redis, по умолчанию "localhost";
* REDIS_PORT - порт на котором работает Redis, по умолчанию "6379";
* CACHE_BY_CATEGORY_NAME_EXPIRE - настраивает время жизни для поиска по имени категории, по умолчанию "10m";
* CACHE_BY_AUTHOR_AND_TITLE_EXPIRE - настраивает время жизни для поиска по автору и названию, по умолчанию "10m".


## <a id="bootstrup">Запуск приложения</a>

Для запуска презентации возможностей приложения можно воспользоваться подготовленным docker-compose файлом.
```shell
docker-compose -f docker/docker-compose.yml up
```
В этом случае приложение запустится с включённым кешированием.

Для запуска презентации без кеширования воспользуйтесь следующей командой:
```shell
docker-compose --env-file ./docker/config/.env.dev -f docker/docker-compose.yml up
```
