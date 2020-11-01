FROM openjdk:8
MAINTAINER Martin Callesen <martin.callesen@gmail.com>, Gert Wohlgemuth <wohlgemuth@ucdavis.edu>
ARG JBOSS_VERSION_ARG="4.0.4.GA"
ENV JBOSS_VERSION="${JBOSS_VERSION_ARG}"
ENV JBOSS_HOME="/opt/jboss-${JBOSS_VERSION}"
RUN useradd -m -d ${JBOSS_HOME} -s /bin/bash jboss
RUN cd $home

#RUN apk --no-cache --update add bash curl unzip wget
RUN wget --no-check-certificate \
             -O /tmp/jboss-${JBOSS_VERSION}.zip \
             https://sourceforge.net/projects/jboss/files/JBoss/JBoss-${JBOSS_VERSION}/jboss-${JBOSS_VERSION}.zip \
 && unzip -d /opt /tmp/jboss-${JBOSS_VERSION}.zip

RUN mkdir -p ${JBOSS_HOME}/server/default/deploy/ecom/

#COPY jboss-4.0.4.GAi /opt/jboss-4.0.4.GAi/

# Enable remote debugging 
ENV JAVA_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000

# Копируем настройки
COPY Docker/postgresql-9.3-1103.jdbc3.jar ${JBOSS_HOME}/server/default/lib/
COPY Docker/ecom.properties ${JBOSS_HOME}/server/default/conf/
COPY Docker/roles.properties ${JBOSS_HOME}/server/default/conf/
COPY Docker/users.properties ${JBOSS_HOME}/server/default/conf/
COPY target/riams-app.ear ${JBOSS_HOME}/server/default/deploy/ecom/
COPY Docker/riams-ds.xml ${JBOSS_HOME}/server/default/deploy/ecom/
COPY Docker/jboss ${JBOSS_HOME}/bin/

RUN chown jboss ${JBOSS_HOME}/* -R
USER jboss
# Expose the ports we're interested in
# Webserver is running on 8080 
# Adminserver is running on 9990
# Remote debug port can be accessed on 8000
EXPOSE 8080 9990 8000 1098 1099 3873 4444 4445 4446 8009 8083 8090 8092 8093
WORKDIR ${JBOSS_HOME}
# Configurations
#ADD ejb3/jboss-service.xml /opt/jboss-4.0.4.GAi/server/default/deploy/ejb3.deployer/META-INF/jboss-service.xml

# ADD run.sh /opt/run.sh
# Set the default command to run on boot
# RUN chmod +x /opt/jboss-4.0.4.GAi/bin/run.sh
# CMD ["/bin/bash", "/opt/jboss-4.0.4.GAi/bin/run.sh"]
# CMD ["/bin/bash", "/opt/jboss-4.0.4.GAi/bin/jboss start"]
# ENTRYPOINT /opt/jboss-4.0.4.GAi/bin/run.sh
ENTRYPOINT chmod +x ${JBOSS_HOME}/bin/run.sh \
                            && ${JBOSS_HOME}/bin/run.sh -b 0.0.0.0