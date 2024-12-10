From maven:3.8.7-openjdk-18 As build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
copy src ./src
RUN mvn clean package -DskipTests


From amazoncorretto:17
ARG PROFILE=dev
ARG APP_VERSION=1.0.1

WORKDIR /app
COPY --from=build /build/target/book-network-*.jar /app/
EXPOSE 8088

ENV DB_URL=jdbc:postgresql://postgres-sql-bsn:5433/book_social_network
Env ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}
CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL} book-network-${JAR_VERSION}.jar