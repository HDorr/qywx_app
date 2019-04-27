#scrm app
FROM 47.96.110.23:8081/scrm/base:0.02
LABEL auth="Jitre"
LABEL time="2019年03月23日"

WORKDIR /root

COPY  scrmapp-webapp/target/scrmapp.war /usr/local/tomcat/webapps/wx.war

# 端口开放声明
EXPOSE 8080

# 启动脚本
# docker run -d --net=scrm --name=wx -p 8080:8080 -v /opt/ziwow:/opt/ziwow ID 


