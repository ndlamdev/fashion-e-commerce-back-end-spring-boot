./gradlew clean
./gradlew build

docker build -f ../Dockerfile --tag fashion-e-commerce_authentication-service:v1.0.0 .

docker compose up -d --build
