#!/bin/bash

# 首先给脚本执行权限：
# chmod +x restore.sh

# 定义变量
CONTAINER_NAME=spring-boot-template-mysql  # MySQL 容器名称
BACKUP_DIR=./mysql_backups  # 备份文件存储目录

# 检查是否提供了备份文件名
if [ -z "$1" ]; then
  echo "请指定要恢复的备份文件名"
  echo "用法: ./restore.sh <备份文件名>"
  exit 1
fi

BACKUP_FILE=${BACKUP_DIR}/$1

# 检查备份文件是否存在
if [ ! -f "$BACKUP_FILE" ]; then
  echo "错误：备份文件 $BACKUP_FILE 不存在"
  exit 1
fi

# 执行恢复
echo "正在恢复数据库..."
docker exec -i ${CONTAINER_NAME} mysql -u myuser --password=secret mydatabase < ${BACKUP_FILE}

if [ $? -eq 0 ]; then
  echo "数据库恢复成功！"
else
  echo "数据库恢复失败！"
fi
