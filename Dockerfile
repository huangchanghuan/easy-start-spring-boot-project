FROM tomcat:8-jre8
#RUN mkdir /usr/local/tomcat/webapps/econtract
COPY target/econtract.war /usr/local/tomcat/webapps/
#RUN unzip -q /tmp/econtract-0.0.1-SNAPSHOT.war -d /usr/local/tomcat/webapps/econtract  > /dev/null 2>&1
ENV CATALINA_OPTS "$CATALINA_OPTS  -Djava.security.egd=file:/dev/./urandom"
ENV TZ=Asia/Shanghai
RUN old="securerandom.source=file:/dev/random" && \
    new="securerandom.source=file:/dev/./urandom" && \
    sed -i "s#$old#$new#"   $JAVA_HOME/lib/security/java.security
EXPOSE 8080
ENTRYPOINT ["/usr/local/tomcat/bin/catalina.sh","run"]
