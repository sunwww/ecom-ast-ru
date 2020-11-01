#build jboss app
docker build -t gbuz_amokb/medos_jboss .

#run jboss 
docker run --name d_jboss gbuz_amokb/medos_jboss

#build tomcat app
docker build -t gbuz_amokb/medos_tomcat .

#run tomcat
docker run --name d_tomcat gbuz_amokb/medos_tomcat