# Базовый образ, "наследуемся" от него
FROM tomcat:9-jre8-alpine
# Копируем из Build Context'а собранный web archive в каталог томката
COPY target/riams.war /usr/local/tomcat/webapps/riams.war
COPY target/skin.war /usr/local/tomcat/webapps/skin.war
COPY Docker/riams.xml /usr/local/tomcat/ecom/riams.xml
COPY Docker/formsCustomize.xml /usr/local/tomcat/conf/formsCustomize.xml
COPY Docker/msh_auth.conf /usr/local/tomcat/conf/msh_auth.conf
COPY Docker/setenv.sh /usr/local/tomcat/bin/setenv.sh
COPY Docker/commons-logging.jar /usr/local/tomcat/lib
COPY Docker/log4j-1.2.17.jar /usr/local/tomcat/lib

# Меняем рабочий каталог на томкатовский
WORKDIR /usr/local/tomcat
# Открываем порт 8080 для контейнера, т.к. его слушает томкат
EXPOSE 8080