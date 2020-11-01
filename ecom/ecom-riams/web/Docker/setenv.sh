#!/bin/sh

export JAVA_OPTS="$JAVA_OPTS -Dorg.apache.el.parser.SKIP_IDENTIFIER_CHECK=true -Dsun.lang.ClassLoader.allowArraySyntax=true -Duser.timezone=Europe/Astrakhan -Djava.security.auth.login.config=/usr/local/tomcat/conf/msh_auth.conf  -Xms256m -Xmx1024m -Dfile.encoding=UTF-8"