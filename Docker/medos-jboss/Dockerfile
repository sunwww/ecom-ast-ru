FROM openjdk:8
MAINTAINER Martin Callesen <martin.callesen@gmail.com>, Gert Wohlgemuth <wohlgemuth@ucdavis.edu>
RUN useradd -m -d /opt/jboss-4.0.4.GAi -s /bin/bash jboss

RUN cd $home

COPY jboss-4.0.4.GAi /opt/jboss-4.0.4.GAi/

# Enable remote debugging 
ENV JAVA_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000

# Копируем настройки
COPY ecom.properties /opt/jboss-4.0.4.GAi/server/default/conf/
COPY roles.properties /opt/jboss-4.0.4.GAi/server/default/conf/
COPY users.properties /opt/jboss-4.0.4.GAi/server/default/conf/
COPY riams-app.ear /opt/jboss-4.0.4.GAi/server/default/deploy/ecom/
COPY riams-ds.xml /opt/jboss-4.0.4.GAi/server/default/deploy/ecom/
COPY jboss /opt/jboss-4.0.4.GAi/bin/

RUN chown jboss /opt/jboss-4.0.4.GAi/* -R
USER jboss
# Expose the ports we're interested in
# Webserver is running on 8080 
# Adminserver is running on 9990
# Remote debug port can be accessed on 8000
EXPOSE 8080 9990 8000 1098 1099 3873 4444 4445 4446 8009 8083 8090 8092 8093

# Configurations
ENV JBOSS_HOME=/opt/jboss-4.0.4.GAi
#ADD ejb3/jboss-service.xml /opt/jboss-4.0.4.GAi/server/default/deploy/ejb3.deployer/META-INF/jboss-service.xml

# ADD run.sh /opt/run.sh
# Set the default command to run on boot
# RUN chmod +x /opt/jboss-4.0.4.GAi/bin/run.sh
# CMD ["/bin/bash", "/opt/jboss-4.0.4.GAi/bin/run.sh"]
# CMD ["/bin/bash", "/opt/jboss-4.0.4.GAi/bin/jboss start"]
# ENTRYPOINT /opt/jboss-4.0.4.GAi/bin/run.sh
ENTRYPOINT /bin/bash