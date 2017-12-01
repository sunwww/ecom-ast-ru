Тут находятся сбилденные версии приложения для работы. 
Приложения собраны пятой версией явы. Для работы необходимо распаковать *.tar и полученные файлы положить в: 
riams.war, skin.war - в папку webapps контейнера сервлетов "Apache Tomcat"
riams-app.ear - в папку deploy JBoss 

Для первоначальной работы необходимо настроить томкат: 
В папку $CATALINA_HOME$/conf положить файл msh_auth.conf
В папку $JBOSS$/server/default/conf положить файл ecom.properties
В папку $JBOSS$/server/default/deploy положить файл riams-ds.xml