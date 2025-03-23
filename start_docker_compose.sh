./gradlew clean
./gradlew build

docker build -f ../Dockerfile --tag fashion-e-commerce:v1.0.0 .

docker compose up -d --build
