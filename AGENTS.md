# GRE 백엔드

- Java 21, Spring Boot, MyBatis, Spring AI, PostgreSQL/pgvector를 사용한다. 현재 기본 패키지는 `com.teamrefund.gre`다.
- Java·Spring·MyBatis 코드 작성 및 리뷰, 네이밍·포맷 검사, 브랜치·커밋·PR 작업 시 [GRE 백엔드 컨벤션 스킬](.agents/skills/gre-backend-conventions/SKILL.md)을 적용한다.
- 요청 흐름은 Controller → Service → DAO로 구성한다. Spring Bean은 생성자로 의존성을 주입하고, DB 트랜잭션은 Service에서 짧게 유지한다. Gmail·LLM 호출은 DB 트랜잭션 밖에서 실행한다.
- 프로젝트 루트에서 Gradle Wrapper를 사용한다. Windows 전체 검증은 `.\gradlew.bat build`, 테스트는 `.\gradlew.bat test`로 실행한다. 다른 환경에서는 `./gradlew`를 사용한다.
- `.env.example`에는 예시값만 둔다. 실행 및 테스트에 필요한 실제 설정은 환경 변수로 전달하며, API 키·OAuth 토큰·비밀번호를 코드나 커밋에 포함하지 않는다. 검증 결과와 환경 설정으로 실행하지 못한 검사는 구분해 보고한다.
