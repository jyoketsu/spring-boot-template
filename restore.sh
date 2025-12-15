#!/bin/bash

# --- 配置 (与 backup.sh 保持一致) ---
DB_CONTAINER="spring-boot-template-mysql"
DB_NAME="mydatabase"
DB_USER="myuser"
DB_PASSWORD="secret"

BACKUP_DIR="./mysql_backups"

# --- 脚本执行 ---

# 检查 Docker 容器是否正在运行
if ! docker ps | grep -q "${DB_CONTAINER}"; then
  echo "❌ 错误：MySQL 容器 '${DB_CONTAINER}' 不在运行状态。"
  exit 1
fi

echo "🔎 正在查找可用的备份文件..."

# 使用 select 结构让用户选择要还原的备份文件
# PS3 是 select 命令的提示符
PS3="请选择要还原的备份文件 (输入数字): "
# find 命令查找所有 .sql.gz 文件，-printf '%f\n' 只打印文件名
backup_files=($(find "${BACKUP_DIR}" -name "*.sql.gz" -printf '%f\n' | sort -r))

if [ ${#backup_files[@]} -eq 0 ]; then
    echo "❌ 错误：在 '${BACKUP_DIR}' 目录中未找到任何 .sql.gz 备份文件。"
    exit 1
fi

select backup_file in "${backup_files[@]}" "取消操作"; do
    # 如果用户选择了 "取消操作"
    if [ "$REPLY" == "$((${#backup_files[@]} + 1))" ]; then
        echo "🚫 操作已取消。"
        exit 0
    fi

    # 检查用户的输入是否有效
    if [ -n "$backup_file" ]; then
        FULL_PATH="${BACKUP_DIR}/${backup_file}"
        echo "✅ 您选择了: ${backup_file}"
        break
    else
        echo "❌ 无效的选择，请输入列表中的数字。"
    fi
done

# 再次向用户确认，这是一个危险操作
read -p "🚨 警告：这将覆盖当前数据库 '${DB_NAME}' 的所有数据！是否继续? (y/N) " confirm
if [[ "$confirm" != "y" && "$confirm" != "Y" ]]; then
    echo "🚫 操作已取消。"
    exit 0
fi

echo "⏳ 正在从 '${backup_file}' 文件进行还原..."

# 执行还原命令
# 1. 使用 gunzip -c 解压文件到标准输出
# 2. 通过管道 | 将SQL流传递给 mysql 客户端
# 3. 使用 MYSQL_PWD 环境变量传递密码
set -o pipefail
gunzip -c "${FULL_PATH}" | docker exec -i "${DB_CONTAINER}" bash -c "MYSQL_PWD='${DB_PASSWORD}' mysql -u '${DB_USER}' '${DB_NAME}'"

# 检查还原是否成功
if [ $? -eq 0 ]; then
  echo "✅ 数据库还原成功！"
else
  echo "❌ 错误：数据库还原失败。"
  exit 1
fi
