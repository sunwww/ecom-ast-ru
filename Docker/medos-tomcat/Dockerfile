# Базовый образ, "наследуемся" от него
FROM tomcat:9-jre8-alpine
# Копируем из Build Context'а собранный web archive в каталог томката
COPY riams.war /usr/local/tomcat/webapps/riams.war
COPY skin.war /usr/local/tomcat/webapps/skin.war
COPY riams.xml /usr/local/tomcat/ecom/riams.xml
COPY formsCustomize.xml /usr/local/tomcat/conf/formsCustomize.xml
COPY msh_auth.conf /usr/local/tomcat/conf/msh_auth.conf
COPY setenv.sh /usr/local/tomcat/bin/setenv.sh
# Меняем рабочий каталог на томкатовский
WORKDIR /usr/local/tomcat
# Открываем порт 8080 для контейнера, т.к. его слушает томкат
EXPOSE 8080