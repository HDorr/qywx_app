#!/bin/bash

# 1.检查参数的格式
if [ $# -lt 1 ]
then
  echo -e "===================>参数的个数不正确"
  exit 0
fi

ENV=production
TAG=$1

# 2.让用户判断环境和标签的正确性
echo -e "===================>请确认当前的环境:$ENV,镜像标签:$TAG [Y/N]"
read answer

if [ "$answer" = "Y" ]
then
  echo -e "===================>打包-scrm-生产环境"

  # 替换application.properties版本号
  PROD=./scrmapp-webapp/src/main/resources/production/application.properties
  APPV=$(sed -n '/app\.v/p' $PROD)
  echo -e "\033[33m 原始版本:$APPV \n 更新版本:app.v=$1 \033[0m"
  if [ app.v=$1 \< $APPV ] || [ app.v=$1 == $APPV ]
  then
    echo -e "\033[31m 更新版本不能低于原始版本 \033[0m"
    exit 0
  fi
  sed -i "s/^app\.v.*/app\.v=$1/" $PROD
  NEW_APPV=$(sed -n '/app\.v/p' $PROD)
  echo -e "\033[33m 更新完成,打印当前版本:$NEW_APPV \033[0m"

  cd scrmapp-parent
  mvn clean package -P$ENV

  # 3.构建镜像
  cd ../scrmapp-webapp
  echo "===================>镜像构建-scrm-生产环境"
  #mvn docker:build -DdockerImageTags=$TAG-$ENV
fi
