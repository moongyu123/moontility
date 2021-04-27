# moontility
### 프로젝트 설명
+ 자리안비움 
    + 일정시간(2분)동안 움직임이 없으면 마우스를 움직여서 컴퓨터가 잠기지 않도록 합니다.
     
+ VDI클립보드 전송 
    + 분리망 개발환경에서 클립보드 전송이 어려울 경우(가상화 -> 인터넷PC). 웹사이트등을 이용하여 클립보드를 가져올 수 있도록 도와줍니다.
    + 수신(receive) : 매1초마다 클립보드 내용이 바뀐경우 인터넷PC로 내용을 가져와서 클립보드에 저장  
    + 송신(send) : 매 1초마다 클립보드 내용이 바뀐경우 웹사이트로 내 클립보드 내용을 보냄 
    + File - Config에서 설정

---
### 사용 기술
#### language : scala, java
#### framework : scala-swing, spring boot 2.2 
#### skill : data-jpa(sqlite), snakeYaml, ThreadPoolTaskScheduler, httpClient, sttp, java-Toolkit, java-Robot

---
### application 생성 
#### 1. Executable jar생성

    mvn clean package spring-boot:repackage -Dmaven.test.skip=true
     
    -> jar 생성 경로 : target/moontility-${version}-SNAPSHOT.jar

#### 2-1. 손쉬운 사용
    java -jar moontility-0.0.1.SNAPSHOT.jar
    
    를 실행하는 sh or cmd 파일 만들어서 사용

#### 2-2. MACOS app파일 만들기 
> download AppMaker
>> https://sourceforge.net/projects/jarappmaker/
>> AppMaker실행 안될때 : sudo su chmod 777 appMaker.dmg 로 권한변경 후 실행
>> JAR버튼으로 1에서 생성된 jar선택
>> .app파일이 생성됨

> download Image2icon
>> 앱스토어에서 검색 (https://apps.apple.com/us/app/image2icon-make-your-own-icons/id992115977?ls=1&mt=12)
>> 앱아이콘을 예쁘게 변환

> dmg 배포
>> 빈 폴더 생성 후 폴더에 app파일 넣고 Disk Utility실행
>> File - New Image - Image from Folder - 생성한 폴더 선택
>> Image Format을 read/write 로 변경/저장 .dmg 파일 생성 


#### 2-3. WindowsOS exe파일 만들기

> TODO