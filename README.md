# health-care-traveler

### 현재 swagger 있음

* 프론트 실행!
  - cd front
  - npm install
  - npm start
  
* 백엔드 실행
  - build.gradle -> runtimeOnly 'com.h2database:h2' 디펜던시에 추가
  - application.yml config.import에 optional:executable.yml만 입력
  - executable.yml 추가
 spring boot 실행 후 http://localhost:8080/swagger-ui/index.html?urls.primaryName=Client-api 접속
