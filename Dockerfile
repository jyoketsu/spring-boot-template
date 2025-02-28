# 使用官方 Java 运行时基础镜像
# FROM openjdk:17-jdk-slim

# 安装字体库
# RUN apt-get update && apt-get install -y fontconfig fonts-dejavu

# 使用更小的JRE基础镜像
FROM eclipse-temurin:17-jre-alpine

# 安装字体库（Alpine 使用 apk）
RUN apk add --no-cache fontconfig ttf-dejavu

# 设置工作目录
WORKDIR /app

# 将应用的 JAR 包复制到镜像中
COPY target/demo-*.jar app.jar

# 暴露端口（根据项目中的应用端口）
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"]
