FROM openjdk:11.0.1-slim
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-XX:MaxRAMPercentage=75 -XX:MaxRam=256m","-Djava.security.egd=file:/dev/./urandom","-cp","app:app/lib/*","com.glic.adminserver.AdminServerApplication"]