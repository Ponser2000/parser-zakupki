## Overview
Приложение позволяющее парсить сайты с очень большими вставсками на JavaScript
Для примера взят сайт закупок https://zakupki.gov.ru
Результат парсинга оформляется в Exel-файл и отправляется на почту
Данная история крутится в docker окружении

### Подготовка контейнера приложения
1. build JAR package
    - mvn clean package
2. build docker image
    - docker build -t selenium_docker_sample .

### Запуск приложения
a. (Вариант 1) Запуск в консоли
- docker run selenium_docker_sample -p 8080:8080 -d

b. (Вариант 2) Через composer
- docker-composer up -d


## License
This project is Apache License 2.0 - see the [LICENSE](LICENSE) file for details
