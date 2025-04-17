# Stage 1: Build JDK runtime using jlink
FROM eclipse-temurin:23-alpine AS build_tool

LABEL authors="lam-nguyen"

WORKDIR /build_dir

# Sử dụng jlink để tạo một JRE tối ưu hóa
RUN jlink \
    --add-modules ALL-MODULE-PATH \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress 2 \
    --output /jre

# Stage 2: Build application image
FROM alpine:latest

LABEL authors="lam-nguyen"

WORKDIR /application

# Copy JRE được tạo từ build_tool
COPY --from=build_tool /jre /library/jdk

# Thiết lập môi trường Java
ENV JAVA_HOME=/library/jdk
ENV PATH=${JAVA_HOME}/bin:${PATH}

# Copy JAR ứng dụng vào container
COPY ./build/libs/config_server-0.0.1-SNAPSHOT.jar ./server.jar

EXPOSE 1305

# Run application
ENTRYPOINT ["java", "-jar", "./server.jar"]
