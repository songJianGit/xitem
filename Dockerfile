# 使用 OpenJDK 17 作为基础镜像 (根据你的 JDK 版本调整)
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY ./target/xitem-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9095

ENTRYPOINT ["java", "-jar", "app.jar"]