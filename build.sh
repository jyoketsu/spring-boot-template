#!/bin/bash

# 定义变量
APP_NAME="demo-app"                        # 应用镜像名称
VERSION="latest"                           # 应用镜像版本
JAR_FILE="target/demo-0.0.1-SNAPSHOT.jar"  # 打包生成的 JAR 文件路径
DOCKER_IMAGE_NAME="${APP_NAME}:${VERSION}" # 完整镜像名称

# 打印步骤信息
echo_step() {
	echo -e "\033[1;34m$1\033[0m"
}

# 检查 Maven 和 Docker 是否可用
check_requirements() {
	echo_step "检查构建环境..."
	if ! command -v mvn &>/dev/null; then
		echo "错误：Maven 未安装，请先安装 Maven。"
		exit 1
	fi

	if ! command -v docker &>/dev/null; then
		echo "错误：Docker 未安装，请先安装 Docker。"
		exit 1
	fi

	if ! command -v docker-compose &>/dev/null; then
		echo "错误：docker-compose 未安装，请先安装 docker-compose。"
		exit 1
	fi
}

# 使用 Maven 打包项目
build_jar() {
	echo_step "开始使用 Maven 打包项目..."
	mvn clean package -DskipTests
	if [ $? -ne 0 ]; then
		echo "错误：Maven 构建失败。"
		exit 1
	fi
	echo_step "Maven 打包完成，生成 JAR 文件：${JAR_FILE}"
}

# 构建 Docker 镜像
build_docker_image() {
	echo_step "开始构建 Docker 镜像..."
	docker build -t ${DOCKER_IMAGE_NAME} .
	if [ $? -ne 0 ]; then
		echo "错误：Docker 镜像构建失败。"
		exit 1
	fi
	echo_step "Docker 镜像构建完成：${DOCKER_IMAGE_NAME}"
}

# 启动 Docker Compose 服务
start_docker_compose() {
	echo_step "启动 Docker Compose 服务..."
	docker-compose -f docker-compose.yml up -d
	if [ $? -ne 0 ]; then
		echo "错误：Docker Compose 启动失败。"
		exit 1
	fi
	echo_step "服务已成功启动。"
}

# 执行步骤
main() {
	check_requirements
	build_jar
	build_docker_image
	# start_docker_compose
}

# 执行主函数
main
