# Дипломная работа по профессии "Тестировщик ПО"

[Задача для дипломной работы](https://github.com/netology-code/qa-diploma)

## Документация

[План автоматизации тестирования](https://github.com/EvheniiV/diplom_QA/blob/main/docs/Plan.md)

[Отчет об автоматизации](https://github.com/EvheniiV/diplom_QA/blob/main/docs/Summary.md)

[Отчёт по итогам тестирования](https://github.com/EvheniiV/diplom_QA/blob/main/docs/Report.md)

## Объект: Веб-сервис "Путешествие дня"

Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API
Банка.

Приложение предлагает купить тур с помощью двух способов:

1. Оплата по дебетовой карте
2. Оплата по кредитной карте

Приложение для обработки данных пересылает их банковским сервисам:

1. Сервису платежей - Payment Gate
2. Кредитному сервису - Credit Gate

Приложение в собственной СУБД сохраняет информацию о том, каким способом был совершён платёж и успешно ли он был
совершён.

## Начало работы

1. Склонировать [проект](https://github.com/EvheniiV/diplom_QA) 

Для запуска тестов на вашем ПК должно быть установлено следующее ПО:

- IntelliJ IDEA
- Git
- Docker Desktop
- Google Chrome (или другой браузер)

2. Открыть проект в IntelliJ IDEA

3. Запускаем контейнеры из файла **docker-compose.yml** командой в терминале:

```
docker-compose up
```

4. Запускаем SUT командой в терминале:

- для MySQL:

```
java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar
```

- для PostgreSQL:

```
java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar
```

5. Запускаем авто-тесты командой в терминале:

- для MySQL:

```
./gradlew clean test "-db.url=jdbc:mysql://localhost:3306/app"
```

- для PostgreSQL:

```
./gradlew clean test "-db.url=jdbc:postgresql://localhost:5432/app"
```
Сервис будет доступен в браузере по адресу: _http://localhost:8080/_

6. Генерируем отчёт по итогам тестирования с помощью **Allure**. Отчёт автоматически откроется в браузере с помощью команды в терминале:

```
./gradlew allureServe
```

После генерации и работы с отчётом, останавливаем работу **allureServe** в терминале сочетанием клавиш _CTRL + C_ и
подтверждаем действие в терминале вводом _Y_.

Если необходимо перезапустить контейнеры, приложение или авто-тесты, нужно остановить работу сервисов в терминале
сочетанием клавиш _CTRL + C_ и перезапустить их, повторив шаги 1-3.