# spring-ranking

## 학생들의 성적을 편하게 알려주는 프로그램
본 프로젝트는 Spring 및 jpa 적용 목적 프로젝트로, 성적을 점수 순, 등급 순, 백분율 순 등 정책에 따라서 학생들의 성적을 정한 성적 정책에 따라서 출력해주는 프로그램입니다.
RESTful API만을 지원하는 프로젝트로, Web 화면은 지원하지 않으며, 스웨거(Swagger) API 문서화 도구를 사용합니다.

## 설치하는 법
0. git, java(version 11), h2 db 모두 설치되어 있어야 합니다.
1. pull 받습니다.
2. 터미널을 열고, 디렉토리로 이동 후 해당 프로그램을 엽니다.
   * intellij 사용자면 `idea .`을 입력하면 프로그램이 열립니다.
   * visul studio 사용자면 `code .`을 입력하면 프로그램이 열립니다.
3. h2를 실행시킵니다.
    1. h2를 다운받습니다.
    2. IOS나 리눅스 운영체제인 경우 `chmod 755 h2.sh`를 터미널에 입력합니다.
    3. 터미널을 열고 h2를 다운받은 디렉토리에 bin 디렉토리로 이동 후 `./h2.sh`를 입력합니다.
    4. 브라우저 URL 창에 `localhost:8082`을 열고 아래에 해당하는 것들을 입력합니다.
       * jdbc URL : `jdbc:h2:~/record`
           * 위와 같이는 한 번만 입력하고 그 다음부터는 `jdbc:h2:tcp://localhost/~/record`로 입력합니다.
       * 사용자명 : `sa`
       * 비밀번호 : 공백입니다.
4. IDE를 통해 `RankingApplication(1)`을 실행시킵니다.
5. 브라우저 URL 창에 `http://localhost:8080/swagger-ui/index.html`을 입력하면 작동될 것입니다.
   

## 작동 방법
* 해당 프로젝트는 회원에게만 접근 권한이 주어지는 프로그램이므로 회원가입을 먼저 하거나 로그인을 먼저 해야 다른 기능들을 확인할 수 있습니다.
  * 편리한 테스트를 위해서 "userId": "test", "password": "test!" 로 바로 로그인이 가능하게 돼있습니다.
* 그 외에 학생들 정보와 성적(record) 정보가 이미 DB에 저장이 돼있습니다.
* 더 추가하거나 더 삭제하면 새로 갱신된 결과를 확인할 수 있습니다.
