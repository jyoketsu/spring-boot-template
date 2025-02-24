#!/bin/bash

# 定义变量
CONTAINER_NAME=spring-boot-template-mysql  # MySQL 容器名称
BACKUP_DIR=./mysql_backups  # 备份文件存储目录
TIMESTAMP=$(date +"%F_%T")  # 当前时间戳，格式为 YYYY-MM-DD_HH:MM:SS
BACKUP_FILE=${BACKUP_DIR}/backup_${TIMESTAMP}.sql  # 备份文件路径

# 创建备份目录（如果不存在）
mkdir -p ${BACKUP_DIR}

# 执行备份
# 使用 docker exec 在容器内执行 mysqldump 命令
# --no-tablespaces 选项避免表空间权限问题
# -u 指定用户名，--password 指定密码
# 将输出重定向到备份文件
docker exec -i ${CONTAINER_NAME} bash -c 'mysqldump --no-tablespaces -u myuser --password=secret mydatabase' > ${BACKUP_FILE}

# 删除超过30天的备份文件
# find 命令查找备份目录中超过30天的 .sql 文件并删除
find ${BACKUP_DIR} -type f -name "*.sql" -mtime +30 -exec rm {} \;

