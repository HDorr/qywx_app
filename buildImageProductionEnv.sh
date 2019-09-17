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
    cd scrmapp-parent
    mvn clean package -P$ENV

    # 3.构建镜像
    cd ../scrmapp-webapp
    echo "===================>镜像构建-scrm-生产环境"
    mvn docker:build -DdockerImageTags=$TAG-$ENV
fi




