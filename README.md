# KaMaster

## 사전 점검사항

 - 마스터 서버가 사용하는 포트를 방화벽 Inbound 허용 정책에 포함
 - Java 1.8 이상 설치된 환경

## Master 설정파일 변경

`config/master.properties` 파일에서 `master.port` 를 타 서버에서 연결 가능한 포트로 변경
마스터 서버 연결 포트 기본값은 `8200`

1대의 PC 에서 Master, Agent 를 모두 기동하면서 8200번 포트를 사용하는 프로그램이 없다면 설정변경 없이 수행가능

## 마스터 서버 실행

java -jar KaMaster.jar

## Agent 설정파일 변경

config/agent.properties 파일의 포트번호와 Master 서버 연결 정보를 수정한다.

1대의 PC 에서 Agent2 를 기동하려면
config/agent2.properties 파일의 포트번호와 Master 서버 연결 정보를 수정한다.

## Agent 1 실행

java -jar KaAgent.jar start

## Agent 2 실행

java -jar -Dagent.config=config/agent2.properties KaAgent.jar start


## Agent 연결 후 Master 명령

1. 최상위 명령
    - list : 연결 Agent 목록을 확인
    - 숫자입력 : 파일 전송할 Agent 번호 선택 -> 하위 메뉴 진입
    - exit : Master 종료 
1. 파일 전송 Agent 메뉴
    - list : Agent 파일 저장소에 파일 목록을 확인
    - 숫자입력 : 전송할 파일 번호 선택 -> 하위 메뉴 진입
    - x : 상위 명령어로 복귀
1. 파일 전송 메뉴
    - list : 파일 수신할 Agent 목록 확인
    - 숫자입력 : 파일을 수신할 Agent 번호 선택 -> 파일 전송
    - x : 상위 명령어로 복귀