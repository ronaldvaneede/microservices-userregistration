rm -rf build
mkdir build
cp ../build/libs/spring-boot-restfull-service.jar build
docker build -t spring-boot-restfull-service .
