#scrm app
FROM 47.96.110.23:8081/scrm/base:0.02
LABEL auth="Jitre"
LABEL time="2019年03月23日"

WORKDIR /root

# 请根据所在地域替换探针的下载地址。
RUN wget http://arms-apm-shanghai.oss-cn-shanghai.aliyuncs.com/ArmsAgent.zip -O ArmsAgent.zip
RUN unzip ArmsAgent.zip -d /root/
RUN rm -rf /root/ArmsAgent.zip
# LicenseKey 在控制台应用监控接入页面查看。
# AppName 为用户自定义 ARMS 监控应用名称，用户名暂不支持中文。
# 若所有镜像都接入同一个应用监控任务，配置此处的 arms_licenseKey 和 arms_appName 即可。
# 若需将镜像接入其他应用监控任务，可在 docker run 中使用 -e 参数指定该应用的 arms_licenseKey 和 arms_appName 参数，以覆盖此处的配置。
#VOLUME ["/root/ArmsAgent"]
ENV arms_licenseKey=gdesptj1p7@5022bc8e8fc237b
ENV arms_appName=scrm-new
ENV JAVA_TOOL_OPTIONS ${JAVA_TOOL_OPTIONS} '-javaagent:/root/ArmsAgent/arms-bootstrap-1.7.0-SNAPSHOT.jar -Darms.licenseKey='${arms_licenseKey}' -Darms.appName='${arms_appName}
### for check the args
RUN env | grep JAVA_TOOL_OPTIONS
#安装curl 用于进行监控检查
RUN apk add curl

COPY  ./scrmapp.war /usr/local/tomcat/webapps/wx.war

# 端口开放声明
EXPOSE 8080

# 启动脚本
# docker run -d --net=scrm --name=wx -p 8080:8080 -v /opt/ziwow:/opt/ziwow ID 


