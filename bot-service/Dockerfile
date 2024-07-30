FROM maven:3.9.6-amazoncorretto-21-debian-bookworm AS build

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Эффективно используем кэш слоев, копируя сначала только файлы pom.xml
COPY pom.xml .

# Копируем остальные файлы проекта
COPY src ./src

# Выполняем сборку проекта
RUN mvn clean package -DskipTests

# Используем официальный образ Java для запуска приложения
FROM openjdk:21

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем собранный jar файл из предыдущего шага
COPY --from=build /app/target/*.jar app.jar

# Запускаем приложение
CMD ["java", "-jar", "app.jar"]