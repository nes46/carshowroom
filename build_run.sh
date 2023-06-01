
#./mvnw clean install -DskipTests
#lsof -ti :8080 | xargs kill
#./mvnw spring-boot:run &

echo "RUNNING SPRING-BOOT BACKEND"
lsof -ti :3000 | xargs kill

echo "RUNNING REACT FRONTEND"
npm --prefix src/main/js/frontend start &

