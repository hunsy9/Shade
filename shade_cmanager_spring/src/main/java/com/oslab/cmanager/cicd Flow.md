# ssh_desktop CI/CD 플로우

## 데스크톱 어플리케이션(electron)에 대한 CI/CD

---

(준비 중)

## 비즈니스로직을 처리하는 Agent Spring boot 의 CI/CD

---

1. **git develop branch 커밋 후 푸쉬( 어느정도 규모의 기능 단위로 푸쉬 부탁드립니다.)**

 **2.  푸쉬된 repository에서 mvn package(빌드하는 명령어) → return agentSpringboot.jar**

 **3. 어플리케이션 동작에 대한 테스트 진행**

- java -jar agentSpringboot.jar (실행)

 **4. agentSpringboot.jar 파일을 scp를 이용하여 Service용 인스턴스에 전송**

- scp -i /Users/yuseunghun/Documents/oidc/oracle_request_manage.key ./target/agent-0.0.1-SNAPSHOT.jar [opc@152.67.213.248](mailto:opc@152.67.213.248):~/

 **5. Service 인스턴스의 systemctl service에 springboot process 등록 후 daemonize**

## SSH connection, 컨테이너를 관리하는 Cmanager Spring boot CI/CD

---

1. **git develop branch 커밋 후 푸쉬( 어느정도 규모의 기능 단위로 푸쉬 부탁드립니다.)**

 **2.  푸쉬된 repository에서 mvn package(빌드하는 명령어) → return cmanagerSpringboot.jar**

 **3. 어플리케이션 동작에 대한 테스트 진행**

- java -jar agentSpringboot.jar (실행)

 **4. cmanagerSpringboot.jar 파일을 scp를 이용하여 cmanager용 인스턴스에 전송**

- scp -i /Users/yuseunghun/Documents/oidc/oracle_container_manage.key ./target/cmanager-0.0.1-SNAPSHOT.jar [opc@144.24.78.122](mailto:opc@144.24.78.122):~/

 **5. Cmanager 인스턴스의 systemctl service에 springboot process 등록 후 daemonize**