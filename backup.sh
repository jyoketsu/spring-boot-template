#!/bin/bash

# --- 配置 ---
# 确保与您的 docker-compose 配置保持一致
DB_CONTAINER="spring-boot-template-mysql"
DB_NAME="mydatabase"
DB_USER="myuser"
DB_PASSWORD="secret" # 建议从 .env 或其他安全途径读取

BACKUP_DIR="./mysql_backups"
# 使用不含特殊字符且兼容各平台的时间戳格式
TIMESTAMP=$(date +"%F_%H-%M-%S")
# 备份文件将以 .gz 格式保存
BACKUP_FILE="${BACKUP_DIR}/backup_${TIMESTAMP}.sql.gz"

# --- 脚本执行 ---

# 检查 Docker 容器是否正在运行
if ! docker ps | grep -q "${DB_CONTAINER}"; then
  echo "❌ 错误：MySQL 容器 '${DB_CONTAINER}' 不在运行状态。"
  exit 1
fi

# 创建备份目录（如果不存在）
mkdir -p "${BACKUP_DIR}"
echo "🗂️  备份目录: ${BACKUP_DIR}"

echo "⏳ 正在执行数据库备份并进行压缩..."

# 执行备份命令
# 1. 使用 MYSQL_PWD 环境变量传递密码，更安全
# 2. 使用管道 | 将 mysqldump 的输出流式传输给 gzip进行压缩
# 3. 增加 set -o pipefail 来确保 mysqldump 失败时脚本会退出
set -o pipefail
docker exec -i "${DB_CONTAINER}" bash -c "MYSQL_PWD='${DB_PASSWORD}' mysqldump --no-tablespaces -u '${DB_USER}' '${DB_NAME}'" | gzip > "${BACKUP_FILE}"

# 检查上一步的命令是否成功
if [ $? -eq 0 ]; then
  echo "✅ 备份并压缩成功！"
  echo "   -> 文件: ${BACKUP_FILE}"
else
  echo "❌ 错误：数据库备份或压缩失败。"
  # 如果失败，删除可能已创建的不完整文件
  rm -f "${BACKUP_FILE}"
  exit 1
fi

# （可选）删除超过7天的旧备份
# find "${BACKUP_DIR}" -name "backup_*.sql.gz" -mtime +7 -exec rm {} \;
# echo "🧹 已清理7天前的旧备份。"
