FROM eclipse-temurin:21

WORKDIR /app

# Copy Maven files

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

# Copy source code

COPY src ./src

# Set Spring profile as an environment variable

ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

# Command to start the application with the active profile

CMD ./mvnw spring-boot:run -Dspring-boot.run.profiles=${SPRING_PROFILES_ACTIVE}