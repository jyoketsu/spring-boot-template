# 使用官方 Java 运行时基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 将应用的 JAR 包复制到镜像中
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# 暴露端口（根据项目中的应用端口）
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"]
