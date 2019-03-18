FROM azul/zulu-openjdk-alpine:11.0.2
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-Xmx256m","-Djava.security.egd=file:/dev/./urandom","-cp","app:app/lib/*","com.glic.adminserver.AdminServerApplication"]