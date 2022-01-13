[![Codacy Badge](https://app.codacy.com/project/badge/Grade/e7133a5b701043b4ae084ec708d76a0b)](https://www.codacy.com/gh/Ponser2000/parser-zakupki/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Ponser2000/parser-zakupki&amp;utm_campaign=Badge_Grade)

## Overview
Приложение позволяющее парсить сайты с очень большими вставками на JavaScript

Для примера взят сайт закупок https://zakupki.gov.ru

Результат парсинга оформляется в Exel-файл и отправляется на почту

Данная история крутится в docker окружении

### Подготовка контейнера приложения
 a. build JAR package
    - mvn clean package

 b. build docker image
    - docker build -t selenium_docker_sample .

### Запуск приложения
 a. (Вариант 1) Запуск в консоли
    - docker run selenium_docker_sample -p 8080:8080 -d

 b. (Вариант 2) Через composer
    - docker-composer up -d

## License
This project is Apache License 2.0 - see the [LICENSE](LICENSE) file for details
