FROM openjdk:8-slim AS builder

WORKDIR /work
COPY . /work

RUN chmod +x mvnw
RUN ./mvnw clean package

FROM openjdk:8-jre
ENV TZ="Asia/Seoul"

WORKDIR /work
COPY --from=builder /work/target/*.jar /work/app.jar
COPY ./deploy/start-app.sh /work/start-app.sh

RUN chmod +x start-app.sh
ENTRYPOINT ["./start-app.sh"]