# moontility

### application 생성 

#### 1. Executable jar생성

 mvn clean package spring-boot:repackage -Dmaven.test.skip=true
 > target/moontility-${version}-SNAPSHOT.jar


#### 2-1. MACOS
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


#### 2-2. WindowsOS

> TODO