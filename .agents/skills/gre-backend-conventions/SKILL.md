---
name: gre-backend-conventions
description: GRE 백엔드의 Java 21, Spring Boot, MyBatis, Spring AI 코드 작성·수정·리뷰와 네이밍, Java 포맷, Git 브랜치·커밋·PR에 팀 컨벤션을 적용한다.
---

# GRE 백엔드 컨벤션

## 패키지와 네이밍

- `AGENTS.md`의 기본 패키지 아래에서 `controller`, `service`, `mapper`, `domain` 계층을 먼저 나눈다. 같은 기능은 계층마다 일관된 하위 패키지명을 사용하고 Java 예약어를 피한다.
- 요청 DTO는 `dto.request`, 응답 DTO는 `dto.response`에 둔다. AI와 허용된 도구는 `ai`와 `tool`, 공통 설정과 예외는 `config`와 `exception`에 둔다. MyBatis XML은 `src/main/resources/mapper/` 아래 기능별로 관리한다.
- 패키지는 영문 소문자, 타입은 PascalCase, 메서드·필드·변수·매개변수는 camelCase, 상수는 UPPER_SNAKE_CASE를 쓴다. 메서드는 동사로 시작하고 boolean 이름은 참일 때의 의미가 드러나게 짓는다.
- 클래스명에 역할별 접미사 `Controller`, `Service`, `Mapper`, `Exception`을 쓴다. 요청 DTO는 동작을 드러내고 `Request`로, 응답 DTO는 대상을 드러내고 `Response`로 끝낸다.
- DB 컬럼은 ERD의 snake_case를 따르고 MyBatis에서 Java camelCase와 대응시킨다. API JSON도 별도 계약이 없다면 camelCase를 쓴다. REST URL 세부 규칙은 확정된 API 계약이 있을 때 적용한다.

## 계층과 데이터 접근

- Controller는 HTTP 요청·입력 검증·응답을 담당하고 Service를 호출한다. Service가 업무 규칙·권한·상태·트랜잭션을 담당한다. Mapper는 SQL 실행과 매핑을 담당한다. Domain에는 HTTP·외부 API 의존성을 두지 않는다.
- Spring Bean은 `private final` 필드와 생성자 주입을 사용한다. Lombok `@RequiredArgsConstructor`를 우선하고, Lombok을 쓰지 않는 클래스는 명시적 생성자를 사용한다. 필드 주입과 의존 Bean의 직접 생성을 피한다. 순환 의존은 책임과 방향을 조정해 해소한다.
- Service는 대체 구현이나 명확한 모듈 계약이 필요할 때 인터페이스를 둔다. MyBatis Mapper는 인터페이스로 선언하고 XML `namespace`는 인터페이스의 전체 클래스명과 일치시킨다. SQL 입력은 `#{...}`로 바인딩하고, 동적 식별자 치환이 필요하면 서버의 고정 허용 목록만 사용한다.
- 변경이 필요 없는 DTO는 `record`를 우선한다. 요청 DTO에 Bean Validation을 적용하고 업무 규칙은 Service에서 검증한다. 응답은 DTO로 변환한다.

## 트랜잭션과 외부 호출

- 변경 업무의 트랜잭션 경계는 Service에 둔다. 단순 조회에 `@Transactional(readOnly = true)`를 검토한다. 프록시를 거치지 않는 같은 클래스 내부 호출의 트랜잭션에 의존하지 않는다. 체크 예외까지 롤백해야 할 때는 정책을 명시한다.
- Gmail·LLM·웹 검색 등 외부 호출은 DB 트랜잭션 밖에서 실행한다. 외부 성공과 DB 반영 사이의 불확실성은 중복 방지·결과 대조·복구 정책으로 처리한다.
- AI는 작업을 판단하고 Tool은 입력 검증과 허용된 호출을 담당한다. 업무 실행 직전 Service에서 소유권·권한·상태와 필요한 사용자 승인을 검증한다. 가변 상태는 실행 단위로 관리한다. 사용자 입력이나 외부 이벤트를 기다릴 때는 상태를 영속화하고 후속 이벤트로 재개한다.
- 이메일 발송에는 별도 사용자 승인이 필요하다. 승인된 초안 revision과 연결 권한을 확인하고 발송 시도를 원자적으로 선점한다. 커밋한 뒤 외부 API를 호출하고 결과를 새 트랜잭션에 기록한다. 발송 결과가 불확실하면 상태를 보존하고 결과 대조 전 자동 재발송을 금지한다.

## 오류 응답과 AI Run

- Service는 `BusinessException(ErrorCode)`를 발생시키고 `GlobalExceptionHandler`의 `@RestControllerAdvice`가 HTTP 응답으로 변환한다. Controller에 업무 예외 처리를 반복하지 않는다. 외부 호출의 복구·재시도·상태 기록은 해당 Service 또는 AI 실행 계층에서 담당한다.
- 공통 오류 응답 `ErrorResponse`는 `code`, 안전한 한국어 `message`, 후속 `action`, `retryable`, `fieldErrors`, `path`, ISO-8601 `timestamp`를 제공한다. 필드 오류가 없으면 `fieldErrors`는 null이며 `path`에는 쿼리와 토큰을 넣지 않는다. `retryable`은 작업의 재시도 안전성을 나타낸다. 정상 응답은 API별 계약을 따른다.
- DTO 검증 오류와 잘못된 JSON은 안전한 400 응답으로 매핑한다. Security Filter Chain 오류도 `AuthenticationEntryPoint`와 `AccessDeniedHandler`에서 같은 형식을 사용한다. 예상치 못한 오류는 서버에서 원인과 추적 ID를 기록하고 500 `INTERNAL_SERVER_ERROR`로 응답한다. 응답에 외부 API 원문·OAuth 토큰·개인정보·스택 트레이스를 포함하지 않는다.
- HTTP 상태·오류 코드·후속 조치는 기존 코드와 Notion의 확정 매핑을 확인한다. 새 매핑이 필요하면 프론트와 공유하는 계약으로 관리한다.
- 비동기 Run 접수 성공은 202와 `runId`로 반환한다. 실행 계층이 오류를 분류하고, 복구할 수 없는 오류는 별도 트랜잭션에 안전한 오류 정보와 함께 기록한다. 조회 자체가 성공하면 200으로 Run 상태를 전달한다. 사용자 입력이나 승인 대기는 정상 업무 상태로 다룬다.

## Java 포맷과 검증

- UTF-8, LF, 파일 끝 줄바꿈을 사용한다. Java는 Spotless의 google-java-format 기본 스타일(2칸 들여쓰기)을 따른다. EditorConfig는 인코딩·개행·들여쓰기 기본값에 적용하고 Java 포맷은 포매터 결과를 우선한다.
- Gradle Wrapper의 `spotlessApply`로 포맷하고 변경 사항을 확인한다. PR 전에 `spotlessCheck`를 통과시킨다. 포맷만 변경한 커밋은 가능한 한 분리한다.
- PR에는 실제 단위·통합 테스트 결과를 기록하고, 관련 없는 대량 포맷 변경이 섞였는지 확인한다.

## Git 협업

- 최신 `dev`에서 `feature/<기능명>`으로 분기한다. 이름은 영문 소문자와 하이픈을 사용한다. 기능 PR의 대상은 `dev`이며, 통합 테스트를 마친 `dev`는 PR로 `main`에 병합한다. `main`과 `dev`에는 직접 커밋하거나 푸시하지 않는다. 기능 브랜치는 테스트·검토·병합 후 삭제한다.
- 커밋 제목은 `type(scope): 작업 요약`을 사용한다. 범위가 불명확하면 scope를 생략할 수 있다. 타입은 `feat`, `fix`, `refactor`, `docs`, `style`, `test`, `chore`, `build`, `ci`, `perf` 중 선택한다. 본문에 목적, 실제 변경 내용, 영향 범위를 남기고 관련 없는 작업은 분리한다.
- PR 제목은 `[type] 작업 요약`을 사용한다. 본문에 작업 목적, 주요 변경, 단위·통합 테스트 결과, Jira 이슈, 추가 검토 사항을 포함한다. 변경 및 영향 범위의 담당자를 리뷰어로 지정하고, 수정 요청을 반영한 뒤 재검토를 요청한다. 충돌은 PR 작성자가 해결한다.

## Notion 원문

- 기능별 흐름·상태·오류 코드가 필요한 작업은 [백엔드 코드 컨벤션](https://app.notion.com/p/41ff138a33718132907fc78f91e98d58)의 해당 섹션과 현재 구현을 확인한다.
- [세부 네이밍 컨벤션](https://app.notion.com/p/41ff138a337181ad9b95f363aa17e98a)
- [포매터·정적 분석](https://app.notion.com/p/41ff138a337181b3bf41d21fb5cf61b4)
- [Git 협업 컨벤션](https://app.notion.com/p/41ff138a337181d7ac9cdc5baa004957)
