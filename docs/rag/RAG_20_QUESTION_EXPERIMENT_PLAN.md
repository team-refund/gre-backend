# eBay RAG 20문항 실험 계획

## 기준 질문 목록

기존 상세 한국어 질문 20개를 원문과 순서 그대로 사용한다.

### 01 · 추적번호 없는 배송 지연

> 11월 18일 eBay.com에서 미국 판매자의 게임 컨트롤러를 결제했고 주문 화면의 배송 예정일은 11월 24일이었습니다. 11월 28일에도 물건을 못 받았고 판매자는 발송했다고만 답했으며 주문 상세에 추적번호가 없습니다. 지금 미수령 신고를 열 수 있나요? 신고 뒤 판매자의 응답을 언제까지 기다리고 어디에서 eBay에 도움을 요청하나요?

### 02 · 노트북 결함과 부분 환불

> 주문 설명에 화면 결함이 없다고 적힌 중고 노트북을 11월 25일 받았습니다. 첫날부터 화면이 깜박여 영상을 남겼고, 판매자는 수리비로 40달러를 주겠다고 했습니다. 아직 수락하지 않았고 판매 페이지는 반품 불가입니다. 결함 사유로 반품해 상품값과 최초 배송비를 돌려받을 수 있나요? 반송비는 누가 내나요?

### 03 · 발송 전 주문 취소

> eBay.com에서 즉시구매로 헤드폰을 카드 결제한 지 20분 됐습니다. 다른 판매자가 더 싸게 파는 걸 발견해 주문 상세에서 취소를 요청하려고 합니다. 추적번호는 없고 발송 표시도 없습니다. 이 사유로 취소를 요청하면 판매자가 수락해야 하나요? 거절되거나 답이 없으면 주문은 어떻게 되나요?

### 04 · 단순 변심과 최초 배송비

> 검은색 M 사이즈 코트를 주문했고 받은 제품의 색상과 사이즈는 주문과 일치합니다. 실내에서 한 번 입어 봤고 택은 그대로며, 받은 지 4일 됐습니다. 반품 가능으로 표시된 주문에서 단순 변심 반품을 신청하면 결제 시 별도로 낸 최초 배송비 12달러도 환불되나요? 반송 라벨 비용은 누가 부담하나요?

### 05 · 파손 반품과 국제 반송비

> 주문한 에스프레소 머신을 개봉하니 물탱크가 갈라져 있었고 배송 상자도 한쪽이 찌그러져 있었습니다. 받은 당일 사진을 찍어 반품을 신청했는데 판매자가 자기 반품 라벨을 보내는 대신 제가 먼저 국제 반송비를 내달라고 합니다. 누가 반송을 준비하고 비용을 부담하나요? 먼저 비용을 내도 되는지 어떻게 확인하나요?

### 06 · 교체 약속과 반품 요청 종료

> 다른 색상의 운동화가 와서 eBay에서 반품 요청을 열었습니다. 판매자는 메시지로 교체품을 보내겠다고 하면서 먼저 요청을 닫아 달라고 합니다. 교체품 추적번호는 아직 없습니다. 요청을 닫은 뒤 교체품이 오지 않으면 같은 주문의 반품 요청을 다시 열거나 eBay에 도움을 요청할 수 있나요?

### 07 · 카드사 결제 이의 제기

> 배송 예정일이 사흘 지난 카메라를 받지 못해 eBay에서 미수령 요청을 열었습니다. 판매자 답변을 기다리는 중인데 카드사는 카드 결제에 대한 이의 제기를 접수할 수 있다고 합니다. 지금 카드사에도 같은 결제 건을 접수하면 진행 중인 eBay 요청과 구매자 보호는 어떻게 되나요?

### 08 · 배대지 경유와 상품 불일치

> 미국 판매자의 재킷을 한국에서 사면서 미국 배대지 주소를 eBay 주문 주소로 넣었습니다. 추적에는 배대지 도착으로 나오고 배대지는 상자를 한국으로 다시 보냈습니다. 한국에서 열어 보니 다른 색상의 재킷이 들어 있었는데 배대지에서는 개봉 사진을 찍지 않았습니다. eBay에서 상품 설명 불일치로 반품하거나 환불받을 수 있나요?

### 09 · 한 주문의 일부 미수령

> 한 판매자에게서 카메라와 메모리카드를 각각 장바구니에 넣어 한 번에 결제했습니다. 두 상품의 가격은 주문에 따로 적혀 있고 같은 추적번호가 붙어 있는데 상자에는 카메라만 있었습니다. 메모리카드만 받거나 그 금액만 환불받을 수 있나요? 한 상품 페이지에서 세트로 판매된 경우에도 같은 절차인가요?

### 10 · 반송 완료 후 카드 환불 지연

> 판매자가 반송 상자를 11월 19일 받았다고 메시지로 확인했고, 추적도 배송 완료입니다. 11월 28일 현재 카드 명세에 환불이 없고 판매자는 환불을 발행한 날짜를 알려주지 않았습니다. 반품 상세에서 무엇을 확인해야 판매자가 아직 환불을 발행하지 않은 건지 카드에 반영되는 중인지 구분할 수 있나요?

### 11 · 반품 주소 불일치

> eBay 반품 요청에서 받은 선불 라벨에는 캘리포니아 주소가 찍혀 있는데 판매자가 메시지로 네바다 창고 주소에 보내라고 합니다. 반품 상세에는 새 라벨이 없고 발송 기한은 이틀 남았습니다. 어느 주소로 보내야 하나요? 나중에 분쟁이 생기면 어떤 추적 또는 배송 증빙이 필요한가요?

### 12 · 고가 상품과 대리 수령

> 배송비와 세금까지 1,080달러인 카메라를 eBay.com에서 주문해 친구 집을 주문 주소로 지정했습니다. 추적에는 배송 완료라고 나오지만 친구는 상자를 받지 않았고 판매자는 배송 완료 화면만 보여줬습니다. 주문 주소와 배송 증거가 일치하는지와 서명 기록을 확인해 달라고 할 수 있나요? 친구가 실제 대리 수령했다면 판단이 달라지나요?

### 13 · 운동화 상태와 위조 의심

> 새 제품으로 표시된 운동화를 받았는데 밑창에 흙과 마모 흔적이 있고 상자와 신발의 제품 코드도 다릅니다. 주문 화면에서 정품 인증 대상 표시를 확인하지 못했고 받은 지 이틀 됐습니다. 상태가 설명과 다르다는 사유와 위조 의심 중 어떤 절차로 문제를 제기할 수 있나요?

### 14 · 비회원 불량품 반품

> eBay 계정 없이 신용카드로 결제했고 주문 확인 이메일은 있지만 로그인할 계정이 없습니다. 어제 도착한 블루투스 스피커는 전원이 켜지지 않습니다. 이메일에서 어디로 들어가 반품을 시작하고 불량 사진이나 설명을 어떻게 제출하나요?

### 15 · 포장 개봉과 환불액 차감

> 설명과 다른 이어폰을 반품했습니다. 포장 씰을 뜯어 5분간 작동을 확인한 뒤 케이블과 설명서까지 전부 돌려보냈습니다. 판매자가 개봉을 이유로 180달러 중 60달러를 차감했고 다른 훼손은 설명하지 않았습니다. 어떤 조건에서 차감에 이의를 제기하고 eBay에 도움을 요청할 수 있나요?

### 16 · 국제 배송 파손과 수입 비용

> eBay International Shipping으로 주문한 310달러짜리 조명이 도착했을 때 유리 갓이 깨져 있었습니다. 결제 영수증에는 eBay에 낸 수입 비용 26달러가 있고, 배송 때 운송사에도 세금 명목으로 15달러를 냈습니다. 파손 반품이 승인되면 상품값 외에 두 곳에 낸 수입 비용을 각각 돌려받을 수 있나요?

### 17 · 예약 주문 배송 지연

> 8월에 예약 구매한 피규어의 상품 페이지에는 10월 발송 예정이라고 적혀 있었습니다. 11월 말에도 추적번호가 없고 판매자는 다시 다음 달로 미뤘습니다. 주문 후 30일은 지났으며 아직 미수령 신고는 열지 않았습니다. 지금 취소를 요청하거나 미수령으로 환불을 요청할 수 있나요?

### 18 · 발송 후 배송 주소 오류

> 결제 직후 확인해 보니 주문에 입력한 주소는 이사 전 아파트입니다. 판매자에게 새 주소를 보냈지만 이미 택배사에 넘겼고 추적은 배송 중입니다. 주문 주소를 수정하거나 올바른 주소로 돌려 보내 달라고 요청할 수 있나요? 기존 주소에서 받지 못하면 환불을 요청할 수 있나요?

### 19 · 고가 상품 반송과 서명

> eBay.com에서 세금과 배송비까지 900달러인 상품을 구매했고 반품 배송비는 제가 결제했습니다. 추적번호를 반품 요청에 올렸고 판매자 주소로 배송 완료로 나오지만 서명 옵션을 사지 않았습니다. 판매자가 반송품을 받지 못했다고 하면 추적 기록만으로 반품 배송을 입증할 수 있나요?

### 20 · 플랫폼 밖 부분 환불

> 240달러짜리 제품의 결함으로 eBay에서 반품 요청을 진행 중입니다. 판매자가 eBay 메시지로 외부 계좌이체로 70달러를 보내겠다고 제안했으며, 아직 송금은 받지 않았고 요청도 열려 있습니다. 송금을 받아도 남은 170달러에 대해 같은 요청에서 계속 다투거나 eBay 개입을 요청할 수 있나요?

## 기준 실험 조건

| 항목 | 기준값 |
|---|---|
| 질문 | 위 한국어 상세 질문 20개, 설정별 질문당 1회 |
| 문서 | 보강 후 미국 eBay 문서 25개. 기존 정제 HTML 22개와 공식 도움말 영문 요약본 3개 |
| 청킹 | 전체 450/200. `chunkSize=450` 토큰, `minChunkSizeChars=200` 문자. 기준 인덱스 149개 청크 |
| 질의·문서 임베딩 | `text-embedding-3-large`, 3,072차원 |
| 검색 | 코사인 유사도, `platform=EBAY`, `topK=15`, `similarityThreshold=0.0` |
| 생성 | 기존 `PolicyRagService` 정책 검토 프롬프트와 출력 형식. `gpt-6-luna`, `reasoning-effort=none`, 최대 출력 4,096토큰 |
| 집계 | 모델이 직접 반환한 `searchStatus`를 집계하고 `searchNote`는 원문으로 보존. `reviews`와 `evidenceGaps`는 실행 원본에 저장 |

검색한 정책은 이메일 초안 생성에 제공하는 여러 컨텍스트 중 하나다. 모델 판정은 기존 정책 검토 프롬프트의 출력값으로 기록한다.

## 25개 문서 실험 조건 타임라인

문서 25개를 사용한 조건을 적용 순서대로 정리했다.

| 순서 | 질문 세트 | 문서 수 | 청킹 | topK | 조건 변경 |
|---:|---|---:|---|---:|---|
| 01 | 상세 한국어 질문 | 25개 | 450/200 | 15 | 미국 eBay 공식 도움말 요약본 3개 추가 |
| 02 | 상세 질문 영어 번역 | 25개 | 450/200 | 15 | 임베딩에 넣는 질문을 영어로 변경 |
| 03 | 상세 한국어 질문 | 25개 | 450/350 | 15 | 구두점 분할 문자 기준 200 → 350. 검색 및 모델 응답 생성 |
| 04 | 상세 한국어 질문 | 25개 | 300/200 | 15 | 전체 문서의 목표 청크 크기를 300토큰으로 변경 |
| 05 | 상세 한국어 질문 | 25개 | 300/200 | 15 | 300/200에서 검색 상한 10 → 15개 |
| 06 | 상세 한국어 질문 | 25개 | 게스트 구매 안내 300/200, 나머지 24개 450/200 | 15 | 문서별 청킹 적용. 검색 및 기존 프롬프트로 모델 응답 생성 |

## 실험 결과 바로가기

| 실험 | 조건 | 결과 |
|---|---|---|
| [01](#rag-20-run-01) | 한국어 · 450/200 · topK=15 | 충분 18/20 · 42.8점 |
| [02](#rag-20-run-02) | 영어 · 450/200 · topK=15 | 충분 17/20 · 46.8점 |
| [03](#rag-20-run-03) | 한국어 · 450/350 · topK=15 | 충분 19/20 · 50.5점 |
| [04 · 최신](#rag-20-run-04) | 한국어 · 300/200 · topK=15 | 충분 15/20 · 43.7점 |

<a id="rag-20-run-01"></a>

## 실험 01 · 25개 문서 · 450/200 · topK=15

<details>
<summary>실험 01 결과 · 접기/펼치기</summary>

### RAG 조건

| 항목 | 실제 실행값 |
|---|---|
| 문서·청크 | 미국 eBay 문서 25개. 원문에서 450/200으로 다시 분할한 149개 청크 |
| 적재 | `text-embedding-3-large`로 재임베딩한 3,072차원 벡터를 PostgreSQL 임시 테이블에 적재 |
| 검색 | 한국어 상세 질문 20개, `platform=EBAY`, 코사인 유사도, `topK=15`, `similarityThreshold=0.0` |
| 생성 | `gpt-6-luna`, `reasoning_effort=none`, 최대 출력 4,096토큰. 기존 `PolicyRagService` 시스템 프롬프트와 응답 필드를 별도 실행기에서 사용 |

### 결과 요약

- 검색·모델 응답 생성 20/20건, 검색 청크 300개
- 모델 `searchStatus` · 충분 18건 · 부족 2건 · 정책 미검색 0건
- 모델 `reviews.applicability` · 적용 104개 · 불확실 49개 · 적용 불가 131개 · 미분류 16개
- 생성 API 사용량 · 입력 142,390토큰 · 출력 45,805토큰

청크 적용 판단은 검색된 청크 ID와 모델의 검토 ID를 대조해 집계했다. 적용은 `APPLICABLE`, 불확실은 `UNCERTAIN`, 적용 불가는 `NOT_APPLICABLE`이다. 일치하는 판단이 없거나 같은 ID의 판단이 충돌하면 미분류로 표시한다.

모델의 부족 판정은 04번 단순 변심과 최초 배송비, 17번 예약 주문 배송 지연이었다. 미분류는 05번 1개, 14번 3개, 16번 2개, 20번 10개였다.

### 청크 적용 점수

| 모델 판단 | 가중치 |
|---|---:|
| 적용 (APPLICABLE) | 1 |
| 불확실 (UNCERTAIN) | 0.5 |
| 적용 불가 (NOT_APPLICABLE) | 0 |
| 미분류 | 0 |

**적용 점수** = (적용 + 0.5 × 불확실) ÷ (적용 + 불확실 + 적용 불가 + 미분류) × 100.

불확실의 가중치 0.5는 비교 실험을 위한 잠정 기준이다. 합계 행은 전체 청크 건수를 합산해 계산한다. 전체 점수는 (104 + 0.5 × 49) ÷ 300 × 100 = 42.8점이다.

| 질문 | 주제 | Search status | 1위 유사도 | 적용 | 불확실 | 적용 불가 | 미분류 | 적용 점수 / 100 |
|---:|---|---|---:|---:|---:|---:|---:|---:|
| [01](#rag-20-run-01-q01) | 추적번호 없는 배송 지연 | `EVIDENCE_SUFFICIENT` | 0.5890 | 4 | 1 | 10 | 0 | 30.0 |
| [02](#rag-20-run-01-q02) | 노트북 결함과 부분 환불 | `EVIDENCE_SUFFICIENT` | 0.3953 | 6 | 3 | 6 | 0 | 50.0 |
| [03](#rag-20-run-01-q03) | 발송 전 주문 취소 | `EVIDENCE_SUFFICIENT` | 0.5193 | 2 | 0 | 13 | 0 | 13.3 |
| [04](#rag-20-run-01-q04) | 단순 변심과 최초 배송비 | `EVIDENCE_INSUFFICIENT` | 0.3686 | 7 | 2 | 6 | 0 | 53.3 |
| [05](#rag-20-run-01-q05) | 파손 반품과 국제 반송비 | `EVIDENCE_SUFFICIENT` | 0.4808 | 7 | 3 | 4 | 1 | 56.7 |
| [06](#rag-20-run-01-q06) | 교체 약속과 반품 요청 종료 | `EVIDENCE_SUFFICIENT` | 0.4961 | 10 | 1 | 4 | 0 | 70.0 |
| [07](#rag-20-run-01-q07) | 카드사 결제 이의 제기 | `EVIDENCE_SUFFICIENT` | 0.5350 | 8 | 0 | 7 | 0 | 53.3 |
| [08](#rag-20-run-01-q08) | 배대지 경유와 상품 불일치 | `EVIDENCE_SUFFICIENT` | 0.4748 | 1 | 9 | 5 | 0 | 36.7 |
| [09](#rag-20-run-01-q09) | 한 주문의 일부 미수령 | `EVIDENCE_SUFFICIENT` | 0.4134 | 3 | 3 | 9 | 0 | 30.0 |
| [10](#rag-20-run-01-q10) | 반송 완료 후 카드 환불 지연 | `EVIDENCE_SUFFICIENT` | 0.4902 | 6 | 1 | 8 | 0 | 43.3 |
| [11](#rag-20-run-01-q11) | 반품 주소 불일치 | `EVIDENCE_SUFFICIENT` | 0.4796 | 9 | 1 | 5 | 0 | 63.3 |
| [12](#rag-20-run-01-q12) | 고가 상품과 대리 수령 | `EVIDENCE_SUFFICIENT` | 0.5131 | 7 | 0 | 8 | 0 | 46.7 |
| [13](#rag-20-run-01-q13) | 운동화 상태와 위조 의심 | `EVIDENCE_SUFFICIENT` | 0.4998 | 3 | 11 | 1 | 0 | 56.7 |
| [14](#rag-20-run-01-q14) | 비회원 불량품 반품 | `EVIDENCE_SUFFICIENT` | 0.4921 | 9 | 0 | 3 | 3 | 60.0 |
| [15](#rag-20-run-01-q15) | 포장 개봉과 환불액 차감 | `EVIDENCE_SUFFICIENT` | 0.5696 | 5 | 3 | 7 | 0 | 43.3 |
| [16](#rag-20-run-01-q16) | 국제 배송 파손과 수입 비용 | `EVIDENCE_SUFFICIENT` | 0.5343 | 2 | 3 | 8 | 2 | 23.3 |
| [17](#rag-20-run-01-q17) | 예약 주문 배송 지연 | `EVIDENCE_INSUFFICIENT` | 0.4140 | 3 | 5 | 7 | 0 | 36.7 |
| [18](#rag-20-run-01-q18) | 발송 후 배송 주소 오류 | `EVIDENCE_SUFFICIENT` | 0.4155 | 2 | 2 | 11 | 0 | 20.0 |
| [19](#rag-20-run-01-q19) | 고가 상품 반송과 서명 | `EVIDENCE_SUFFICIENT` | 0.5589 | 5 | 1 | 9 | 0 | 36.7 |
| [20](#rag-20-run-01-q20) | 플랫폼 밖 부분 환불 | `EVIDENCE_SUFFICIENT` | 0.5072 | 5 | 0 | 0 | 10 | 33.3 |
| **합계** | **20개 질문** | 충분 18 · 부족 2 · 정책 미검색 0 | — | **104** | **49** | **131** | **16** | **42.8** |

### 질문별 Search note와 검색 결과

<a id="rag-20-run-01-q01"></a>

#### 01 · 추적번호 없는 배송 지연

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 4 · 불확실 1 · 적용 불가 10 · 미분류 0 · **적용 점수 30.0 / 100**

**Search note**

> 예정 배송일이 지난 뒤 미수령 신고를 열 수 있으며, 11월 24일 예정일 기준 11월 28일은 신고 가능 기간 안입니다. 신고 후 판매자는 3영업일 이내에 추적 정보·배송 업데이트 또는 환불을 제공해야 하고, 해결되지 않으면 요청 상세의 ‘Ask eBay to step in and help’에서 eBay에 도움을 요청할 수 있습니다. eBay의 개입은 요건 충족 여부에 따라 결정되며, 환불 결과가 자동 보장되는 것은 아닙니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | 0.5890 | `APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 2 | eBay Money Back Guarantee policy | 0.5201 | `APPLICABLE` | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` |
| 3 | eBay Money Back Guarantee policy | 0.5005 | `APPLICABLE` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 4 | Get help if you bought as a guest | 0.4980 | `NOT_APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 5 | Get help if you bought as a guest | 0.4852 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 6 | eBay Money Back Guarantee policy | 0.4748 | `NOT_APPLICABLE` | `347c4c37-e21c-4f86-9575-306c1651a5df` |
| 7 | eBay International Shipping - Buyer Terms & Conditions | 0.4306 | `NOT_APPLICABLE` | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` |
| 8 | eBay Money Back Guarantee policy | 0.4177 | `NOT_APPLICABLE` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |
| 9 | Order cancellation policy | 0.4177 | `NOT_APPLICABLE` | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` |
| 10 | eBay Money Back Guarantee policy | 0.4170 | `NOT_APPLICABLE` | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` |
| 11 | eBay Money Back Guarantee policy | 0.4164 | `NOT_APPLICABLE` | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` |
| 12 | eBay Money Back Guarantee policy | 0.4160 | `APPLICABLE` | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` |
| 13 | Check the status of your request | 0.4138 | `UNCERTAIN` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 14 | Get help if you bought as a guest | 0.4135 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 15 | eBay International Shipping Seller Terms & Conditions | 0.4121 | `NOT_APPLICABLE` | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` |

</details>

<a id="rag-20-run-01-q02"></a>

#### 02 · 노트북 결함과 부분 환불

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 6 · 불확실 3 · 적용 불가 6 · 미분류 0 · **적용 점수 50.0 / 100**

**Search note**

> 화면 깜박임이 설명에 없던 결함이라면 판매 페이지의 ‘반품 불가’에도 불구하고 반품을 요청할 수 있고, 반품이 진행되면 정책상 판매자가 반송비를 부담하며 상품값과 최초 배송비가 포함된 전액 환불 대상입니다. 다만 환불이 이미 확정된 것은 아니므로 반품 요청을 열어 판매자의 응답을 받고, 영상만으로 결함 요건이 확인되는지는 보장되지 않습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help if you bought as a guest | 0.3953 | `APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 2 | Get help if you bought as a guest | 0.3945 | `UNCERTAIN` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 3 | eBay Money Back Guarantee policy | 0.3921 | `APPLICABLE` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |
| 4 | Get help if you bought as a guest | 0.3916 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 5 | Return an item for a refund | 0.3896 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 6 | eBay Money Back Guarantee policy | 0.3875 | `APPLICABLE` | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` |
| 7 | eBay International Shipping - Buyer Terms & Conditions | 0.3846 | `NOT_APPLICABLE` | `bf37f2b1-9659-40d3-844a-11545aaba365` |
| 8 | Return an item for a refund | 0.3790 | `APPLICABLE` | `927f2845-49b1-40f5-b351-48dee6c6ce2a` |
| 9 | eBay International Shipping - Buyer Terms & Conditions | 0.3769 | `NOT_APPLICABLE` | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` |
| 10 | How to handle a return request as a seller | 0.3749 | `NOT_APPLICABLE` | `1743ff1f-d3d4-4bdd-8248-211875998334` |
| 11 | Condition of returned items policy | 0.3743 | `UNCERTAIN` | `5969c401-95f9-4001-99e5-4c8592208453` |
| 12 | eBay Refurbished | 0.3698 | `NOT_APPLICABLE` | `83be0678-d0f2-40f2-aecf-6d4bc8d49f52` |
| 13 | eBay Money Back Guarantee policy | 0.3678 | `UNCERTAIN` | `40df69ec-9ecf-41e0-a32a-982d935ab64a` |
| 14 | Get help if you bought as a guest | 0.3657 | `APPLICABLE` | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` |
| 15 | eBay Money Back Guarantee policy | 0.3637 | `NOT_APPLICABLE` | `cdca8d5f-812a-4fed-a6a6-380989fdf86a` |

</details>

<a id="rag-20-run-01-q03"></a>

#### 03 · 발송 전 주문 취소

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 2 · 불확실 0 · 적용 불가 13 · 미분류 0 · **적용 점수 13.3 / 100**

**Search note**

> 주문 상세에서 마음이 바뀐 사유로 취소를 요청할 수 있으며, 발송 표시나 추적번호가 없다는 사실만으로 판매자가 취소를 수락해야 하는 것은 아닙니다. 판매자는 요청을 수락하거나 거절할 수 있고, 결제 완료 주문은 요청 후 3일 이내 답이 없으면 취소되지 않은 채 요청이 종료됩니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Order cancellation policy | 0.5193 | `APPLICABLE` | `4f1d8091-f413-47f9-92b5-8fcf59780b03` |
| 2 | Order cancellation policy | 0.5139 | `APPLICABLE` | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` |
| 3 | Get help if you bought as a guest | 0.4558 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 4 | Selling practices policy | 0.4546 | `NOT_APPLICABLE` | `29dc7c6c-e4f2-4783-b537-a7c16da81994` |
| 5 | Order cancellation policy | 0.4410 | `NOT_APPLICABLE` | `6aa36185-3f49-40c5-bbee-fb0855dfcf39` |
| 6 | Return an item for a refund | 0.4354 | `NOT_APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 7 | Get help if you bought as a guest | 0.4344 | `NOT_APPLICABLE` | `0f5fa726-6d78-401f-bca3-39487acb99e2` |
| 8 | eBay Authenticity Guarantee for Sneakers | 0.4062 | `NOT_APPLICABLE` | `b52463d6-3981-4dda-bbc0-5853e70bb899` |
| 9 | Get help if you bought as a guest | 0.4054 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 10 | Get help if you bought as a guest | 0.4045 | `NOT_APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 11 | eBay Money Back Guarantee policy | 0.4032 | `NOT_APPLICABLE` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 12 | Get help with an item that hasn't arrived | 0.3959 | `NOT_APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 13 | Get help if you bought as a guest | 0.3880 | `NOT_APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 14 | Check the status of your request | 0.3855 | `NOT_APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 15 | eBay Money Back Guarantee policy | 0.3825 | `NOT_APPLICABLE` | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` |

</details>

<a id="rag-20-run-01-q04"></a>

#### 04 · 단순 변심과 최초 배송비

**Search status** · `EVIDENCE_INSUFFICIENT`

**청크 적용 판단** · 적용 7 · 불확실 2 · 적용 불가 6 · 미분류 0 · **적용 점수 53.3 / 100**

**Search note**

> 검색된 정책에 따르면 단순 변심 반품의 최초 배송비 환불 여부는 판매자의 반품 정책에 달려 있고, 반송비도 주문 목록의 ‘무료 반품’ 또는 ‘구매자 부담’ 조건에 따릅니다. 주문이 ‘반품 가능’이라는 표시만으로 어느 조건인지 확인되지 않아, 최초 배송비 환불이나 반송 라벨 비용 부담을 확정할 수 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help if you bought as a guest | 0.3686 | `APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 2 | Return an item for a refund | 0.3667 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 3 | Get help if you bought as a guest | 0.3469 | `APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 4 | eBay International Shipping - Buyer Terms & Conditions | 0.3442 | `UNCERTAIN` | `bf37f2b1-9659-40d3-844a-11545aaba365` |
| 5 | Get help if you bought as a guest | 0.3267 | `APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 6 | eBay Refurbished | 0.3242 | `NOT_APPLICABLE` | `83be0678-d0f2-40f2-aecf-6d4bc8d49f52` |
| 7 | Return an item for a refund | 0.3240 | `NOT_APPLICABLE` | `927f2845-49b1-40f5-b351-48dee6c6ce2a` |
| 8 | Buying with Authenticity Guarantee | 0.3237 | `NOT_APPLICABLE` | `79a064e9-afda-4eb4-8f2b-171482196f23` |
| 9 | eBay International Shipping - Buyer Terms & Conditions | 0.3236 | `UNCERTAIN` | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` |
| 10 | Get help if you bought as a guest | 0.3183 | `APPLICABLE` | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` |
| 11 | eBay Money Back Guarantee policy | 0.3171 | `NOT_APPLICABLE` | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` |
| 12 | eBay Money Back Guarantee policy | 0.3133 | `APPLICABLE` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |
| 13 | Condition of returned items policy | 0.3115 | `APPLICABLE` | `5969c401-95f9-4001-99e5-4c8592208453` |
| 14 | Check the status of your request | 0.3104 | `NOT_APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 15 | eBay Authenticity Guarantee for Sneakers | 0.3079 | `NOT_APPLICABLE` | `4c3b3f5c-f79d-4e33-8ac0-c3d09a75123f` |

</details>

<a id="rag-20-run-01-q05"></a>

#### 05 · 파손 반품과 국제 반송비

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 7 · 불확실 3 · 적용 불가 4 · 미분류 1 · **적용 점수 56.7 / 100**

**Search note**

> 물탱크가 배송 당시 파손된 상태였다는 사유로 반품하는 경우, 정책상 판매자가 반송비를 부담하고 반송 라벨 또는 구매자가 수락할 다른 반송 방법을 제공해야 합니다. 다른 방법을 쓰려면 먼저 배송 서비스와 비용을 판매자와 합의해야 하며, 해당 주문이 eBay International Shipping(EIS)인지와 반품 요청의 현재 상태는 확인되지 않아 국제 반송 절차·실제 비용 처리 방식은 주문 세부정보에서 확인해야 합니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Return shipping for buyers | 0.4808 | `APPLICABLE` | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` |
| 2 | Return shipping for buyers | 0.4714 | `APPLICABLE` | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` |
| 3 | eBay International Shipping - Buyer Terms & Conditions | 0.4613 | `UNCERTAIN` | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` |
| 4 | Get help if you bought as a guest | 0.4477 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 5 | Return an item for a refund | 0.4471 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 6 | eBay Money Back Guarantee policy | 0.4415 | `APPLICABLE` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |
| 7 | Get help if you bought as a guest | 0.4403 | `APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 8 | eBay International Shipping - Buyer Terms & Conditions | 0.4394 | `UNCERTAIN` | `5dd13ab2-02b1-4a36-8e2b-1ba1e0162b44` |
| 9 | eBay International Shipping - Buyer Terms & Conditions | 0.4383 | — | `bf37f2b1-9659-40d3-844a-11545aaba365` |
| 10 | Return shipping for buyers | 0.4361 | `APPLICABLE` | `b01c873d-8724-4642-adf2-4ad03f6ada39` |
| 11 | eBay Money Back Guarantee policy | 0.4250 | `APPLICABLE` | `77c77430-aceb-4f79-b632-ef78c863faf0` |
| 12 | Get help if you bought as a guest | 0.4180 | `NOT_APPLICABLE` | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` |
| 13 | eBay International Shipping Seller Terms & Conditions | 0.4163 | `NOT_APPLICABLE` | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` |
| 14 | eBay International Shipping Seller Terms & Conditions | 0.4102 | `NOT_APPLICABLE` | `c7c113bd-7443-4ede-b558-5027c87b8564` |
| 15 | International purchases and shipping for buyers | 0.4068 | `UNCERTAIN` | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` |

</details>

<a id="rag-20-run-01-q06"></a>

#### 06 · 교체 약속과 반품 요청 종료

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 10 · 불확실 1 · 적용 불가 4 · 미분류 0 · **적용 점수 70.0 / 100**

**Search note**

> 다른 색상은 상품 설명과 불일치할 수 있어 반품 요청의 근거로 제시할 수 있고, 판매자는 요청에 답하며 교체·교환을 제안할 수 있습니다. 그러나 요청을 닫으면 같은 거래에 대해 다시 열거나 새로 제출할 수 없으므로, 교체품이 오지 않을 때 eBay가 반드시 구제한다고 단정할 수 없습니다. 현재 요청이 열려 있다면 닫지 말고, 판매자가 해결하지 못하면 적용 기한 내 eBay에 도움을 요청하세요.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | 0.4961 | `NOT_APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 2 | Get help if you bought as a guest | 0.4722 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 3 | Get help if you bought as a guest | 0.4682 | `UNCERTAIN` | `0f5fa726-6d78-401f-bca3-39487acb99e2` |
| 4 | Check the status of your request | 0.4632 | `APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 5 | Get help if you bought as a guest | 0.4616 | `APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 6 | Return shipping for buyers | 0.4594 | `APPLICABLE` | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` |
| 7 | Get help if you bought as a guest | 0.4563 | `APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 8 | Return an item for a refund | 0.4538 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 9 | eBay Money Back Guarantee policy | 0.4533 | `NOT_APPLICABLE` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 10 | Return an item for a refund | 0.4409 | `APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 11 | Return shipping for buyers | 0.4357 | `APPLICABLE` | `b01c873d-8724-4642-adf2-4ad03f6ada39` |
| 12 | eBay Money Back Guarantee policy | 0.4355 | `APPLICABLE` | `3b462389-7437-407c-87bd-ea2d91d04130` |
| 13 | Get help if you bought as a guest | 0.4320 | `NOT_APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 14 | Return an item for a refund | 0.4309 | `APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 15 | How to handle a return request as a seller | 0.4297 | `APPLICABLE` | `0655d062-13e1-45e5-af4b-9a5e802f811c` |

</details>

<a id="rag-20-run-01-q07"></a>

#### 07 · 카드사 결제 이의 제기

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 8 · 불확실 0 · 적용 불가 7 · 미분류 0 · **적용 점수 53.3 / 100**

**Search note**

> 정책상 배송 예정일이 지난 미수령 상품은 판매자에게 배송 정보·배송 업데이트·환불을 요청할 수 있고, 판매자는 요청 후 3영업일 이내 응답해야 하며 해결되지 않으면 eBay에 개입을 요청할 수 있습니다. 다만 같은 거래에 카드사 이의 제기나 결제 제공자 보호 청구를 열면 중복 청구로 eBay가 구매자 보호 사건을 종료할 수 있으므로, 접수 전에 eBay 요청을 통한 해결을 우선하는 것이 보호를 유지하는 데 유리합니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help if you bought as a guest | 0.5350 | `APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 2 | Get help with an item that hasn't arrived | 0.5308 | `APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 3 | eBay Money Back Guarantee policy | 0.5196 | `APPLICABLE` | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` |
| 4 | eBay Money Back Guarantee policy | 0.5148 | `APPLICABLE` | `6e065bd8-ddcb-4159-88ec-51f7904be5f9` |
| 5 | Get help if you bought as a guest | 0.4994 | `APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 6 | eBay Money Back Guarantee policy | 0.4914 | `NOT_APPLICABLE` | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` |
| 7 | Return an item for a refund | 0.4887 | `NOT_APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 8 | Check the status of your request | 0.4887 | `APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 9 | eBay Money Back Guarantee policy | 0.4879 | `NOT_APPLICABLE` | `3b462389-7437-407c-87bd-ea2d91d04130` |
| 10 | Order cancellation policy | 0.4861 | `NOT_APPLICABLE` | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` |
| 11 | eBay Money Back Guarantee policy | 0.4829 | `NOT_APPLICABLE` | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` |
| 12 | Get help if you bought as a guest | 0.4719 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 13 | Return an item for a refund | 0.4697 | `NOT_APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 14 | eBay Money Back Guarantee policy | 0.4680 | `APPLICABLE` | `0b3a3fdf-2b2a-485b-b84f-eace87156c0e` |
| 15 | eBay Money Back Guarantee policy | 0.4653 | `APPLICABLE` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |

</details>

<a id="rag-20-run-01-q08"></a>

#### 08 · 배대지 경유와 상품 불일치

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 1 · 불확실 9 · 적용 불가 5 · 미분류 0 · **적용 점수 36.7 / 100**

**Search note**

> 다른 색상의 재킷을 받았다면 상품 설명과 다른 상품으로 반품을 요청할 근거가 있고, 일반적으로 판매자는 반품을 받지 않는다고 했더라도 반품 및 환불 대상이 될 수 있습니다. 다만 주문 주소가 배대지였고 배대지가 한국으로 재배송했으므로 제3자 배송대행 이용이 보장 대상에서 제외되는지, 또는 eBay International Shipping 프로그램을 통한 전달인지 확인해야 하며, 배대지의 개봉 사진이 없다는 사실만으로 결과를 단정할 수는 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay International Shipping - Buyer Terms & Conditions | 0.4748 | `UNCERTAIN` | `bf37f2b1-9659-40d3-844a-11545aaba365` |
| 2 | eBay International Shipping Seller Terms & Conditions | 0.4722 | `UNCERTAIN` | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` |
| 3 | eBay International Shipping - Buyer Terms & Conditions | 0.4695 | `UNCERTAIN` | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` |
| 4 | Buying with Authenticity Guarantee | 0.4676 | `NOT_APPLICABLE` | `79a064e9-afda-4eb4-8f2b-171482196f23` |
| 5 | Condition of returned items policy | 0.4513 | `UNCERTAIN` | `87e1df21-066e-49e9-863e-7b3455872aed` |
| 6 | How to handle a return request as a seller | 0.4510 | `NOT_APPLICABLE` | `1743ff1f-d3d4-4bdd-8248-211875998334` |
| 7 | Condition of returned items policy | 0.4502 | `UNCERTAIN` | `5969c401-95f9-4001-99e5-4c8592208453` |
| 8 | eBay Money Back Guarantee policy | 0.4458 | `UNCERTAIN` | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` |
| 9 | eBay Authenticity Guarantee for Sneakers | 0.4429 | `NOT_APPLICABLE` | `4c3b3f5c-f79d-4e33-8ac0-c3d09a75123f` |
| 10 | Return an item for a refund | 0.4420 | `UNCERTAIN` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 11 | Return shipping for buyers | 0.4404 | `NOT_APPLICABLE` | `b01c873d-8724-4642-adf2-4ad03f6ada39` |
| 12 | eBay Money Back Guarantee policy | 0.4388 | `NOT_APPLICABLE` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |
| 13 | eBay Money Back Guarantee policy | 0.4378 | `UNCERTAIN` | `cdca8d5f-812a-4fed-a6a6-380989fdf86a` |
| 14 | eBay Money Back Guarantee policy | 0.4363 | `UNCERTAIN` | `3b462389-7437-407c-87bd-ea2d91d04130` |
| 15 | Selling practices policy | 0.4359 | `APPLICABLE` | `eab21850-3ea7-4d49-9473-953d73e566b5` |

</details>

<a id="rag-20-run-01-q09"></a>

#### 09 · 한 주문의 일부 미수령

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 3 · 불확실 3 · 적용 불가 9 · 미분류 0 · **적용 점수 30.0 / 100**

**Search note**

> 카메라와 메모리카드가 서로 다른 상품 페이지(리스팅)에서 각각 구매된 별도 상품이라면, 누락된 메모리카드를 미수령으로 신고하고 판매자에게 배송 정보·대체품·환불을 요청하는 절차를 뒷받침하는 정책이 있습니다. 다만 메모리카드만 보내거나 그 상품 금액만 환불하는 결과가 보장되지는 않으며, 한 페이지의 세트·번들 상품이라면 구성품만 따로 요청하는 절차가 아니라 세트 전체에 대해 반품 요청을 해야 합니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Return an item for a refund | 0.4134 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 2 | Get help if you bought as a guest | 0.3835 | `APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 3 | Get help if you bought as a guest | 0.3763 | `UNCERTAIN` | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` |
| 4 | Get help if you bought as a guest | 0.3688 | `UNCERTAIN` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 5 | Get help if you bought as a guest | 0.3591 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 6 | Return an item for a refund | 0.3575 | `NOT_APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 7 | Condition of returned items policy | 0.3539 | `NOT_APPLICABLE` | `5969c401-95f9-4001-99e5-4c8592208453` |
| 8 | eBay International Shipping \| Seller Center | 0.3525 | `NOT_APPLICABLE` | `ba80853d-fe28-4579-81c0-530b40ab121c` |
| 9 | eBay International Shipping - Buyer Terms & Conditions | 0.3508 | `NOT_APPLICABLE` | `bf37f2b1-9659-40d3-844a-11545aaba365` |
| 10 | eBay Money Back Guarantee policy | 0.3497 | `UNCERTAIN` | `0b3a3fdf-2b2a-485b-b84f-eace87156c0e` |
| 11 | Get help with an item that hasn't arrived | 0.3441 | `APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 12 | eBay Money Back Guarantee policy | 0.3433 | `NOT_APPLICABLE` | `6e065bd8-ddcb-4159-88ec-51f7904be5f9` |
| 13 | Buying with Authenticity Guarantee | 0.3426 | `NOT_APPLICABLE` | `8d8a876a-0fe7-496a-bbe4-7f7e6ec72c1e` |
| 14 | International purchases and shipping for buyers | 0.3413 | `NOT_APPLICABLE` | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` |
| 15 | eBay Authenticity Guarantee for Sneakers | 0.3370 | `NOT_APPLICABLE` | `b52463d6-3981-4dda-bbc0-5853e70bb899` |

</details>

<a id="rag-20-run-01-q10"></a>

#### 10 · 반송 완료 후 카드 환불 지연

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 6 · 불확실 1 · 적용 불가 8 · 미분류 0 · **적용 점수 43.3 / 100**

**Search note**

> 반품 상세에서 환불 마감일과 요청 상태를 확인하세요. 추적상 반송품이 배송 완료이고 판매자가 받은 사실도 확인됐다면, 일반적으로 판매자는 수령 후 2영업일 안에 환불해야 하며, 기한을 넘겼는데도 환불이 발행되지 않았으면 eBay에 도움을 요청할 수 있습니다. 환불이 발행된 경우 원래 결제수단에 반영되기까지 보통 3~5영업일이 걸리지만, 명세에 환불이 없다는 사실만으로 미발행이라고 확정할 수는 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help if you bought as a guest | 0.4902 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 2 | Return an item for a refund | 0.4735 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 3 | eBay Money Back Guarantee policy | 0.4685 | `NOT_APPLICABLE` | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` |
| 4 | Check the status of your request | 0.4665 | `APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 5 | eBay Money Back Guarantee policy | 0.4648 | `APPLICABLE` | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` |
| 6 | Get help if you bought as a guest | 0.4615 | `UNCERTAIN` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 7 | eBay Money Back Guarantee policy | 0.4583 | `APPLICABLE` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |
| 8 | How to handle a return request as a seller | 0.4575 | `APPLICABLE` | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` |
| 9 | eBay Money Back Guarantee policy | 0.4498 | `NOT_APPLICABLE` | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` |
| 10 | eBay Money Back Guarantee policy | 0.4465 | `NOT_APPLICABLE` | `6e065bd8-ddcb-4159-88ec-51f7904be5f9` |
| 11 | eBay Money Back Guarantee policy | 0.4464 | `APPLICABLE` | `3b462389-7437-407c-87bd-ea2d91d04130` |
| 12 | Get help if you bought as a guest | 0.4448 | `NOT_APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 13 | Get help with an item that hasn't arrived | 0.4382 | `NOT_APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 14 | eBay Money Back Guarantee policy | 0.4232 | `NOT_APPLICABLE` | `cdca8d5f-812a-4fed-a6a6-380989fdf86a` |
| 15 | How to handle a return request as a seller | 0.4202 | `NOT_APPLICABLE` | `350c99f0-67bd-4939-abab-98d6ddf129f7` |

</details>

<a id="rag-20-run-01-q11"></a>

#### 11 · 반품 주소 불일치

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 9 · 불확실 1 · 적용 불가 5 · 미분류 0 · **적용 점수 63.3 / 100**

**Search note**

> 정책상 판매자가 부담하는 반품에서는 제공된 라벨을 사용하거나 판매자와 합의한 다른 반품 방법을 사용할 수 있고, 판매자 부담 반품의 경우 판매자는 라벨 또는 구매자가 수락할 다른 방법을 제공해야 합니다. 다만 라벨의 캘리포니아 주소와 메시지의 네바다 주소 중 어느 쪽이 이 반품에 유효한지는 검색된 정책만으로 확인되지 않으므로, 임의로 네바다로 보내기보다 반품 상세에서 eBay에 새 라벨·주소 확인을 요청하고 기한 전에 발송하는 것이 안전합니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Return shipping for buyers | 0.4796 | `APPLICABLE` | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` |
| 2 | Return shipping for buyers | 0.4547 | `APPLICABLE` | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` |
| 3 | Return shipping for buyers | 0.4535 | `APPLICABLE` | `b01c873d-8724-4642-adf2-4ad03f6ada39` |
| 4 | eBay Money Back Guarantee policy | 0.4369 | `NOT_APPLICABLE` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |
| 5 | How to handle a return request as a seller | 0.4292 | `NOT_APPLICABLE` | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` |
| 6 | Get help if you bought as a guest | 0.4210 | `APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 7 | eBay Money Back Guarantee policy | 0.4186 | `APPLICABLE` | `77c77430-aceb-4f79-b632-ef78c863faf0` |
| 8 | eBay Money Back Guarantee policy | 0.4182 | `APPLICABLE` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |
| 9 | Return an item for a refund | 0.4168 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 10 | Check the status of your request | 0.4167 | `APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 11 | eBay International Shipping - Buyer Terms & Conditions | 0.4116 | `NOT_APPLICABLE` | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` |
| 12 | Return an item for a refund | 0.4003 | `APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 13 | How to handle a return request as a seller | 0.3978 | `NOT_APPLICABLE` | `0655d062-13e1-45e5-af4b-9a5e802f811c` |
| 14 | eBay Money Back Guarantee policy | 0.3937 | `NOT_APPLICABLE` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 15 | eBay Money Back Guarantee policy | 0.3928 | `UNCERTAIN` | `cdca8d5f-812a-4fed-a6a6-380989fdf86a` |

</details>

<a id="rag-20-run-01-q12"></a>

#### 12 · 고가 상품과 대리 수령

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 7 · 불확실 0 · 적용 불가 8 · 미분류 0 · **적용 점수 46.7 / 100**

**Search note**

> 판매자에게 주문 세부정보에 기재된 주소와 배송 증거가 일치하는지, 배송 날짜·상태와 서명 확인 기록을 제시해 달라고 요청할 근거가 있습니다. 다만 판매 완료 화면만으로 정책상 배송 증명이 충족되는지는 알 수 없으며, 친구가 대리 수령했는지와 서명 기록 등 핵심 사실에 따라 판단이 달라질 수 있습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | 0.5131 | `APPLICABLE` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 2 | eBay Money Back Guarantee policy | 0.4839 | `NOT_APPLICABLE` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |
| 3 | eBay Money Back Guarantee policy | 0.4672 | `APPLICABLE` | `347c4c37-e21c-4f86-9575-306c1651a5df` |
| 4 | Get help with an item that hasn't arrived | 0.4564 | `APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 5 | eBay International Shipping Seller Terms & Conditions | 0.4376 | `NOT_APPLICABLE` | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` |
| 6 | Get help if you bought as a guest | 0.4368 | `APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 7 | Signature confirmation policy | 0.4346 | `APPLICABLE` | `604b7fbb-2462-4a94-a0a3-59fb5290d846` |
| 8 | eBay International Shipping Seller Terms & Conditions | 0.4282 | `NOT_APPLICABLE` | `c7c113bd-7443-4ede-b558-5027c87b8564` |
| 9 | eBay International Shipping - Buyer Terms & Conditions | 0.4267 | `NOT_APPLICABLE` | `5dd13ab2-02b1-4a36-8e2b-1ba1e0162b44` |
| 10 | eBay Money Back Guarantee policy | 0.4260 | `APPLICABLE` | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` |
| 11 | Selling practices policy | 0.4190 | `APPLICABLE` | `dec306d9-6700-480e-8686-950f1be0db28` |
| 12 | eBay International Shipping - Buyer Terms & Conditions | 0.4119 | `NOT_APPLICABLE` | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` |
| 13 | eBay International Shipping - Buyer Terms & Conditions | 0.4117 | `NOT_APPLICABLE` | `88ee7312-806c-49e9-875f-f20a8881ce44` |
| 14 | International purchases and shipping for buyers | 0.4102 | `NOT_APPLICABLE` | `02ed01df-7637-4e68-8458-f8e01b540ce4` |
| 15 | eBay Authenticity Guarantee for Sneakers | 0.4045 | `NOT_APPLICABLE` | `28c18aa9-8df1-4134-bfa2-934df227a276` |

</details>

<a id="rag-20-run-01-q13"></a>

#### 13 · 운동화 상태와 위조 의심

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 3 · 불확실 11 · 적용 불가 1 · 미분류 0 · **적용 점수 56.7 / 100**

**Search note**

> 밑창의 흙·마모 흔적은 새 제품 설명과 맞지 않는다는 이의 제기의 근거로 제시할 수 있고, 판매자에게 먼저 연락하거나 eBay 환불 보장 절차를 통해 문제를 제기할 수 있습니다. 다만 주문에 정품 인증 표시가 없었다면 인증 절차가 적용됐다고 볼 수 없으며, 코드 불일치만으로 위조가 확정되지는 않습니다. 환불 가능 여부는 인증 여부, 반품 가능 기간과 최종 판매 적용 여부 등 조건에 달려 있습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay Authenticity Guarantee for Sneakers | 0.4998 | `UNCERTAIN` | `668ef1c6-a0e2-4a5d-bf03-c24cc6e00f89` |
| 2 | eBay Authenticity Guarantee for Sneakers | 0.4716 | `APPLICABLE` | `a8720eaf-1dbd-4eaa-929a-d067e5780bf1` |
| 3 | eBay Authenticity Guarantee for Sneakers | 0.4595 | `NOT_APPLICABLE` | `bd335ae7-a208-4fb1-a080-26f3ce45688f` |
| 4 | eBay Authenticity Guarantee for Sneakers | 0.4466 | `UNCERTAIN` | `28a46454-f148-412a-b6be-827a11666c92` |
| 5 | eBay Authenticity Guarantee for Sneakers | 0.4417 | `UNCERTAIN` | `909603f2-3b0e-4a92-b440-b3b76df9df36` |
| 6 | eBay Authenticity Guarantee for Sneakers | 0.4258 | `UNCERTAIN` | `d7ea9541-18ed-4a3b-860e-44597608060c` |
| 7 | Buying with Authenticity Guarantee | 0.4205 | `UNCERTAIN` | `79a064e9-afda-4eb4-8f2b-171482196f23` |
| 8 | eBay Authenticity Guarantee for Sneakers | 0.4005 | `APPLICABLE` | `b52463d6-3981-4dda-bbc0-5853e70bb899` |
| 9 | Buying with Authenticity Guarantee | 0.3942 | `UNCERTAIN` | `abfb1a23-9b8e-4be4-ba50-bfd32ea7d279` |
| 10 | eBay Authenticity Guarantee for Sneakers | 0.3878 | `UNCERTAIN` | `310c3728-23b8-46d5-88f9-cfccc4aa62bc` |
| 11 | eBay Authenticity Guarantee for Sneakers | 0.3849 | `UNCERTAIN` | `4c3b3f5c-f79d-4e33-8ac0-c3d09a75123f` |
| 12 | eBay Money Back Guarantee policy | 0.3816 | `UNCERTAIN` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |
| 13 | eBay Authenticity Guarantee for Sneakers | 0.3807 | `UNCERTAIN` | `4493c75d-f79a-4cd5-b15e-e7c847137741` |
| 14 | Buying with Authenticity Guarantee | 0.3694 | `UNCERTAIN` | `8d8a876a-0fe7-496a-bbe4-7f7e6ec72c1e` |
| 15 | Counterfeit policy | 0.3657 | `APPLICABLE` | `26dc2d98-b36f-476c-a878-9053053198be` |

</details>

<a id="rag-20-run-01-q14"></a>

#### 14 · 비회원 불량품 반품

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 9 · 불확실 0 · 적용 불가 3 · 미분류 3 · **적용 점수 60.0 / 100**

**Search note**

> 주문 확인 이메일의 ‘View order details’에서 게스트 주문을 연 뒤 ‘Contact seller’로 들어가 불량 문제를 설명해 요청을 제출할 수 있고, 고장·불량 반품 요청에는 사진을 1장 이상(최대 10장) 첨부해야 합니다. 판매자는 통상 3영업일 안에 응답하고 해결책을 제시해야 하지만, 환불이나 교환 중 특정 결과가 보장되는 것은 아니며, 신용카드 결제가 eBay 체크아웃으로 이루어졌는지와 적용 가능한 보장 요건은 확인이 필요합니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help if you bought as a guest | 0.4921 | `APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 2 | Get help if you bought as a guest | 0.4580 | `APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 3 | Get help if you bought as a guest | 0.4273 | `APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 4 | Get help with an item that hasn't arrived | 0.4185 | — | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 5 | Return an item for a refund | 0.4097 | `APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 6 | Selling practices policy | 0.3971 | `APPLICABLE` | `960e9856-d535-4377-b917-e125ee9fff41` |
| 7 | Get help if you bought as a guest | 0.3944 | `APPLICABLE` | `0f5fa726-6d78-401f-bca3-39487acb99e2` |
| 8 | Return an item for a refund | 0.3871 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 9 | Buying with Authenticity Guarantee | 0.3865 | `NOT_APPLICABLE` | `79a064e9-afda-4eb4-8f2b-171482196f23` |
| 10 | eBay Money Back Guarantee policy | 0.3859 | `APPLICABLE` | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` |
| 11 | eBay Money Back Guarantee policy | 0.3855 | `APPLICABLE` | `0b3a3fdf-2b2a-485b-b84f-eace87156c0e` |
| 12 | eBay Refurbished | 0.3843 | — | `83be0678-d0f2-40f2-aecf-6d4bc8d49f52` |
| 13 | eBay Money Back Guarantee policy | 0.3823 | — | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 14 | How to handle a return request as a seller | 0.3815 | `NOT_APPLICABLE` | `1743ff1f-d3d4-4bdd-8248-211875998334` |
| 15 | eBay Authenticity Guarantee for Sneakers | 0.3808 | `NOT_APPLICABLE` | `b52463d6-3981-4dda-bbc0-5853e70bb899` |

</details>

<a id="rag-20-run-01-q15"></a>

#### 15 · 포장 개봉과 환불액 차감

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 5 · 불확실 3 · 적용 불가 7 · 미분류 0 · **적용 점수 43.3 / 100**

**Search note**

> 씰을 개봉했더라도 이어폰이 원래 상태이고 작동 확인에 필요한 범위를 넘는 사용·변형·훼손이 없었다면, 개봉만을 이유로 한 60달러 차감은 이의를 제기할 근거가 있습니다. 다만 차감 가능 여부와 금액은 반품 당시 상태, 확인에 필요한 범위를 넘는 취급으로 발생한 가치 손실 및 판매자의 차감 자격에 달려 있으므로, 판매자에게 근거를 요청하고 해결되지 않으면 3영업일 후 eBay에 도움을 요청할 수 있습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | How to handle a return request as a seller | 0.5696 | `APPLICABLE` | `1743ff1f-d3d4-4bdd-8248-211875998334` |
| 2 | Condition of returned items policy | 0.5363 | `APPLICABLE` | `5969c401-95f9-4001-99e5-4c8592208453` |
| 3 | Condition of returned items policy | 0.4805 | `UNCERTAIN` | `87e1df21-066e-49e9-863e-7b3455872aed` |
| 4 | Return an item for a refund | 0.4802 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 5 | How to handle a return request as a seller | 0.4741 | `APPLICABLE` | `6109adcf-ec42-4a95-818c-a2ef540a612d` |
| 6 | Get help if you bought as a guest | 0.4705 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 7 | Buying with Authenticity Guarantee | 0.4691 | `NOT_APPLICABLE` | `79a064e9-afda-4eb4-8f2b-171482196f23` |
| 8 | eBay International Shipping - Buyer Terms & Conditions | 0.4614 | `UNCERTAIN` | `bf37f2b1-9659-40d3-844a-11545aaba365` |
| 9 | eBay International Shipping - Buyer Terms & Conditions | 0.4547 | `NOT_APPLICABLE` | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` |
| 10 | Return an item for a refund | 0.4543 | `APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 11 | eBay Money Back Guarantee policy | 0.4534 | `NOT_APPLICABLE` | `008dfe95-0e97-43ae-b30b-304d611fcf17` |
| 12 | eBay Authenticity Guarantee for Sneakers | 0.4505 | `NOT_APPLICABLE` | `b3af4677-12f3-44be-8750-6cb51b7529b9` |
| 13 | eBay Authenticity Guarantee for Sneakers | 0.4483 | `NOT_APPLICABLE` | `4c3b3f5c-f79d-4e33-8ac0-c3d09a75123f` |
| 14 | eBay Money Back Guarantee policy | 0.4482 | `UNCERTAIN` | `40df69ec-9ecf-41e0-a32a-982d935ab64a` |
| 15 | eBay Refurbished | 0.4479 | `NOT_APPLICABLE` | `83be0678-d0f2-40f2-aecf-6d4bc8d49f52` |

</details>

<a id="rag-20-run-01-q16"></a>

#### 16 · 국제 배송 파손과 수입 비용

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 2 · 불확실 3 · 적용 불가 8 · 미분류 2 · **적용 점수 23.3 / 100**

**Search note**

> 파손이 배송 중 발생했고 eBay Money Back Guarantee 청구가 승인되면, eBay에 낸 세금·관세·수입 수수료도 환불 대상이 될 수 있다는 정책 근거가 있습니다. 다만 운송사에 별도로 낸 15달러는 eBay가 환불한다고 보장하는 조항이 없고, 두 금액의 성격과 배송 중 파손 및 청구 승인 여부는 확인되지 않았습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay International Shipping - Buyer Terms & Conditions | 0.5343 | — | `5dd13ab2-02b1-4a36-8e2b-1ba1e0162b44` |
| 2 | eBay International Shipping - Buyer Terms & Conditions | 0.5242 | `UNCERTAIN` | `bf37f2b1-9659-40d3-844a-11545aaba365` |
| 3 | eBay International Shipping - Buyer Terms & Conditions | 0.5138 | `UNCERTAIN` | `936ad65f-d348-453a-9ada-fd5061587211` |
| 4 | eBay International Shipping Seller Terms & Conditions | 0.4899 | `NOT_APPLICABLE` | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` |
| 5 | eBay International Shipping - Buyer Terms & Conditions | 0.4662 | `NOT_APPLICABLE` | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` |
| 6 | International purchases and shipping for buyers | 0.4659 | `APPLICABLE` | `02ed01df-7637-4e68-8458-f8e01b540ce4` |
| 7 | eBay International Shipping - Buyer Terms & Conditions | 0.4596 | `NOT_APPLICABLE` | `89d3cde5-4330-49cf-872f-576d13c53acd` |
| 8 | International purchases and shipping for buyers | 0.4538 | — | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` |
| 9 | eBay International Shipping Seller Terms & Conditions | 0.4515 | `NOT_APPLICABLE` | `aea220ac-fba6-4e07-b5e4-8d76311a2a21` |
| 10 | Condition of returned items policy | 0.4505 | `NOT_APPLICABLE` | `5969c401-95f9-4001-99e5-4c8592208453` |
| 11 | International purchases and shipping for buyers | 0.4484 | `NOT_APPLICABLE` | `76066a7d-2738-449c-9574-7d68d06faa54` |
| 12 | eBay International Shipping \| Seller Center | 0.4457 | `APPLICABLE` | `ba80853d-fe28-4579-81c0-530b40ab121c` |
| 13 | eBay International Shipping - Buyer Terms & Conditions | 0.4402 | `NOT_APPLICABLE` | `f054ff4a-2c33-4f61-857b-3c6b7712dafd` |
| 14 | eBay International Shipping Seller Terms & Conditions | 0.4341 | `NOT_APPLICABLE` | `c7c113bd-7443-4ede-b558-5027c87b8564` |
| 15 | Return shipping for buyers | 0.4305 | `UNCERTAIN` | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` |

</details>

<a id="rag-20-run-01-q17"></a>

#### 17 · 예약 주문 배송 지연

**Search status** · `EVIDENCE_INSUFFICIENT`

**청크 적용 판단** · 적용 3 · 불확실 5 · 적용 불가 7 · 미분류 0 · **적용 점수 36.7 / 100**

**Search note**

> 취소는 주문 후 30일 이내에 요청해야 하며, 요청을 받아들일지는 판매자 재량이므로 주문 후 30일이 지난 현재 취소를 보장받을 수 없습니다. 미수령 신고는 예상 배송일이 지난 뒤 가능하고, 신고 기한은 주문일로부터 30일이 아니라 예상 배송일부터 30일이므로 지금 신고할 수 있을 가능성이 있지만, 주문 내역의 공식 예상 배송일과 그 날짜가 변경됐는지 확인해야 합니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Order cancellation policy | 0.4140 | `APPLICABLE` | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` |
| 2 | Get help if you bought as a guest | 0.4007 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 3 | Get help with an item that hasn't arrived | 0.3918 | `UNCERTAIN` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 4 | eBay Authenticity Guarantee for Sneakers | 0.3795 | `NOT_APPLICABLE` | `b52463d6-3981-4dda-bbc0-5853e70bb899` |
| 5 | Get help if you bought as a guest | 0.3772 | `NOT_APPLICABLE` | `0f5fa726-6d78-401f-bca3-39487acb99e2` |
| 6 | Get help if you bought as a guest | 0.3767 | `UNCERTAIN` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 7 | Order cancellation policy | 0.3571 | `APPLICABLE` | `4f1d8091-f413-47f9-92b5-8fcf59780b03` |
| 8 | eBay Money Back Guarantee policy | 0.3569 | `UNCERTAIN` | `347c4c37-e21c-4f86-9575-306c1651a5df` |
| 9 | eBay Money Back Guarantee policy | 0.3553 | `UNCERTAIN` | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` |
| 10 | eBay Money Back Guarantee policy | 0.3504 | `NOT_APPLICABLE` | `cdca8d5f-812a-4fed-a6a6-380989fdf86a` |
| 11 | Check the status of your request | 0.3475 | `NOT_APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 12 | Get help if you bought as a guest | 0.3425 | `UNCERTAIN` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 13 | Return an item for a refund | 0.3403 | `NOT_APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 14 | Order cancellation policy | 0.3328 | `APPLICABLE` | `6aa36185-3f49-40c5-bbee-fb0855dfcf39` |
| 15 | Buying with Authenticity Guarantee | 0.3316 | `NOT_APPLICABLE` | `8d8a876a-0fe7-496a-bbe4-7f7e6ec72c1e` |

</details>

<a id="rag-20-run-01-q18"></a>

#### 18 · 발송 후 배송 주소 오류

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 2 · 불확실 2 · 적용 불가 11 · 미분류 0 · **적용 점수 20.0 / 100**

**Search note**

> 판매자에게 연락해 주문 취소를 요청할 수 있지만, 판매자는 수락하거나 거절할 수 있고 이미 발송된 주문은 취소할 수 없습니다. 잘못된 주소로 발송된 경우 가능한 한 원래 주소에서 수령해 보라는 안내는 있으나, 배송 중 주소 변경·반송이나 원래 주소에서 받지 못한 경우의 환불은 보장되지 않습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help if you bought as a guest | 0.4155 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 2 | Get help if you bought as a guest | 0.3825 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 3 | Get help if you bought as a guest | 0.3758 | `APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 4 | eBay Money Back Guarantee policy | 0.3740 | `NOT_APPLICABLE` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |
| 5 | Get help if you bought as a guest | 0.3723 | `NOT_APPLICABLE` | `0f5fa726-6d78-401f-bca3-39487acb99e2` |
| 6 | Get help if you bought as a guest | 0.3705 | `NOT_APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 7 | Return an item for a refund | 0.3674 | `NOT_APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 8 | Get help with an item that hasn't arrived | 0.3593 | `UNCERTAIN` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 9 | eBay Authenticity Guarantee for Sneakers | 0.3503 | `NOT_APPLICABLE` | `b52463d6-3981-4dda-bbc0-5853e70bb899` |
| 10 | Selling practices policy | 0.3480 | `NOT_APPLICABLE` | `eab21850-3ea7-4d49-9473-953d73e566b5` |
| 11 | eBay Money Back Guarantee policy | 0.3460 | `UNCERTAIN` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 12 | Condition of returned items policy | 0.3421 | `NOT_APPLICABLE` | `5969c401-95f9-4001-99e5-4c8592208453` |
| 13 | Return shipping for buyers | 0.3409 | `NOT_APPLICABLE` | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` |
| 14 | Order cancellation policy | 0.3361 | `APPLICABLE` | `4f1d8091-f413-47f9-92b5-8fcf59780b03` |
| 15 | Get help if you bought as a guest | 0.3361 | `NOT_APPLICABLE` | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` |

</details>

<a id="rag-20-run-01-q19"></a>

#### 19 · 고가 상품 반송과 서명

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 5 · 불확실 1 · 적용 불가 9 · 미분류 0 · **적용 점수 36.7 / 100**

**Search note**

> 반품 배송의 증빙 요건은 검색된 정책에서 확인됩니다. 구매 총액이 세금·배송비 포함 900달러이므로 반품에는 추적 배송과 서명 확인이 필요하며, 추적 기록의 배송 완료만으로는 요구된 증빙을 충족한다고 보기 어렵습니다. eBay에 추적 기록을 제출할 수는 있지만 환불이나 판매자의 수령을 보장하지는 않습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | 0.5589 | `NOT_APPLICABLE` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 2 | Return shipping for buyers | 0.5306 | `APPLICABLE` | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` |
| 3 | Signature confirmation policy | 0.5292 | `APPLICABLE` | `604b7fbb-2462-4a94-a0a3-59fb5290d846` |
| 4 | Get help if you bought as a guest | 0.5222 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 5 | eBay Money Back Guarantee policy | 0.5219 | `NOT_APPLICABLE` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |
| 6 | eBay International Shipping - Buyer Terms & Conditions | 0.5061 | `NOT_APPLICABLE` | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` |
| 7 | Selling practices policy | 0.5057 | `NOT_APPLICABLE` | `dec306d9-6700-480e-8686-950f1be0db28` |
| 8 | eBay Authenticity Guarantee for Sneakers | 0.4976 | `NOT_APPLICABLE` | `4c3b3f5c-f79d-4e33-8ac0-c3d09a75123f` |
| 9 | eBay International Shipping Seller Terms & Conditions | 0.4933 | `NOT_APPLICABLE` | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` |
| 10 | Return shipping for buyers | 0.4901 | `APPLICABLE` | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` |
| 11 | Signature confirmation policy | 0.4900 | `APPLICABLE` | `e2b3df5e-28ee-48d2-b4f7-f78175e269b9` |
| 12 | eBay Money Back Guarantee policy | 0.4879 | `APPLICABLE` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |
| 13 | Return shipping for buyers | 0.4829 | `NOT_APPLICABLE` | `b01c873d-8724-4642-adf2-4ad03f6ada39` |
| 14 | Get help if you bought as a guest | 0.4791 | `UNCERTAIN` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 15 | eBay Money Back Guarantee policy | 0.4771 | `NOT_APPLICABLE` | `347c4c37-e21c-4f86-9575-306c1651a5df` |

</details>

<a id="rag-20-run-01-q20"></a>

#### 20 · 플랫폼 밖 부분 환불

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 5 · 불확실 0 · 적용 불가 0 · 미분류 10 · **적용 점수 33.3 / 100**

**Search note**

> 검색된 정책은 판매자가 반품 요청에서 부분 환불을 제안할 수 있고, 해결되지 않으면 정해진 시점 이후 eBay에 개입을 요청할 수 있다고 뒷받침합니다. 다만 외부 계좌이체로 70달러를 받은 뒤 같은 요청에서 남은 170달러를 계속 청구하거나 eBay가 그 차액을 보장하는지는 확인되지 않았으므로, 송금을 받거나 요청을 종결하기 전에 eBay를 통해 처리할 수 있는지 확인하세요.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help if you bought as a guest | 0.5072 | — | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 2 | eBay Money Back Guarantee policy | 0.5045 | — | `6e065bd8-ddcb-4159-88ec-51f7904be5f9` |
| 3 | Get help if you bought as a guest | 0.4794 | — | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 4 | Return an item for a refund | 0.4711 | `APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 5 | eBay Money Back Guarantee policy | 0.4708 | `APPLICABLE` | `3b462389-7437-407c-87bd-ea2d91d04130` |
| 6 | Return an item for a refund | 0.4600 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 7 | Check the status of your request | 0.4520 | — | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 8 | eBay Money Back Guarantee policy | 0.4510 | — | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` |
| 9 | eBay International Shipping - Buyer Terms & Conditions | 0.4459 | — | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` |
| 10 | How to handle a return request as a seller | 0.4398 | — | `1743ff1f-d3d4-4bdd-8248-211875998334` |
| 11 | Return an item for a refund | 0.4391 | `APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 12 | How to handle a return request as a seller | 0.4371 | — | `99dbe313-a5e6-473a-884d-9758d7490e93` |
| 13 | eBay International Shipping Seller Terms & Conditions | 0.4354 | — | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` |
| 14 | How to handle a return request as a seller | 0.4308 | `APPLICABLE` | `0655d062-13e1-45e5-af4b-9a5e802f811c` |
| 15 | eBay Money Back Guarantee policy | 0.4288 | — | `008dfe95-0e97-43ae-b30b-304d611fcf17` |

</details>

</details>

<a id="rag-20-run-02"></a>

## 실험 02 · 25개 문서 · 450/200 · topK=15 · 영어 질의

<details>
<summary>실험 02 결과 · 접기/펼치기</summary>

### RAG 조건

| 변경 항목 | 실제 실행값 |
|---|---|
| 검색 질의 | 같은 상세 질문 20개의 기존 영어 번역 |
| 적재 | 기존 25개 문서·149개 청크의 PostgreSQL 벡터 재사용 |
| 출력 길이 | 청크별 reason을 짧은 구절로 작성하도록 추가 지시 |

450/200, topK=15, similarityThreshold=0.0, 플랫폼 EBAY, 임베딩 text-embedding-3-large, 생성 gpt-6-luna 및 기존 정책 검토 프롬프트를 사용했다. 질문 언어와 reason 길이 지시가 변경됐다.

### 결과 요약

- 모델 Search status · 충분 17건 · 부족 3건
- 모델 입력 133,517토큰 · 출력 32,615토큰 · 합계 166,132토큰. 실험 1번 대비 11.7% 감소. 문서 재임베딩 생략.
- 적용 점수 46.8 / 100. (적용 + 0.5 × 불확실) ÷ 전체 반환 수 × 100.

| 질문 | 주제 | Search status | 1위 유사도 | 적용 | 불확실 | 적용 불가 | 미분류 | 적용 점수 / 100 |
|---:|---|---|---:|---:|---:|---:|---:|---:|
| [01](#rag-20-run-02-q01) | 추적번호 없는 배송 지연 | `EVIDENCE_SUFFICIENT` | 0.6728 | 4 | 0 | 11 | 0 | 26.7 |
| [02](#rag-20-run-02-q02) | 노트북 결함과 부분 환불 | `EVIDENCE_SUFFICIENT` | 0.4859 | 12 | 1 | 2 | 0 | 83.3 |
| [03](#rag-20-run-02-q03) | 발송 전 주문 취소 | `EVIDENCE_SUFFICIENT` | 0.5577 | 2 | 0 | 13 | 0 | 13.3 |
| [04](#rag-20-run-02-q04) | 단순 변심과 최초 배송비 | `EVIDENCE_SUFFICIENT` | 0.4781 | 2 | 7 | 6 | 0 | 36.7 |
| [05](#rag-20-run-02-q05) | 파손 반품과 국제 반송비 | `EVIDENCE_SUFFICIENT` | 0.5066 | 7 | 8 | 0 | 0 | 73.3 |
| [06](#rag-20-run-02-q06) | 교체 약속과 반품 요청 종료 | `EVIDENCE_SUFFICIENT` | 0.5618 | 9 | 1 | 5 | 0 | 63.3 |
| [07](#rag-20-run-02-q07) | 카드사 결제 이의 제기 | `EVIDENCE_SUFFICIENT` | 0.5551 | 4 | 0 | 10 | 1 | 26.7 |
| [08](#rag-20-run-02-q08) | 배대지 경유와 상품 불일치 | `EVIDENCE_SUFFICIENT` | 0.5410 | 7 | 3 | 5 | 0 | 56.7 |
| [09](#rag-20-run-02-q09) | 한 주문의 일부 미수령 | `EVIDENCE_SUFFICIENT` | 0.4581 | 7 | 2 | 6 | 0 | 53.3 |
| [10](#rag-20-run-02-q10) | 반송 완료 후 카드 환불 지연 | `EVIDENCE_SUFFICIENT` | 0.5231 | 6 | 0 | 9 | 0 | 40.0 |
| [11](#rag-20-run-02-q11) | 반품 주소 불일치 | `EVIDENCE_SUFFICIENT` | 0.5036 | 8 | 0 | 7 | 0 | 53.3 |
| [12](#rag-20-run-02-q12) | 고가 상품과 대리 수령 | `EVIDENCE_SUFFICIENT` | 0.4839 | 4 | 2 | 8 | 1 | 33.3 |
| [13](#rag-20-run-02-q13) | 운동화 상태와 위조 의심 | `EVIDENCE_INSUFFICIENT` | 0.6133 | 3 | 12 | 0 | 0 | 60.0 |
| [14](#rag-20-run-02-q14) | 비회원 불량품 반품 | `EVIDENCE_SUFFICIENT` | 0.5318 | 6 | 0 | 9 | 0 | 40.0 |
| [15](#rag-20-run-02-q15) | 포장 개봉과 환불액 차감 | `EVIDENCE_SUFFICIENT` | 0.5767 | 10 | 0 | 5 | 0 | 66.7 |
| [16](#rag-20-run-02-q16) | 국제 배송 파손과 수입 비용 | `EVIDENCE_INSUFFICIENT` | 0.5881 | 6 | 1 | 7 | 1 | 43.3 |
| [17](#rag-20-run-02-q17) | 예약 주문 배송 지연 | `EVIDENCE_SUFFICIENT` | 0.5241 | 1 | 5 | 9 | 0 | 23.3 |
| [18](#rag-20-run-02-q18) | 발송 후 배송 주소 오류 | `EVIDENCE_INSUFFICIENT` | 0.3909 | 1 | 2 | 12 | 0 | 13.3 |
| [19](#rag-20-run-02-q19) | 고가 상품 반송과 서명 | `EVIDENCE_SUFFICIENT` | 0.5838 | 7 | 1 | 7 | 0 | 50.0 |
| [20](#rag-20-run-02-q20) | 플랫폼 밖 부분 환불 | `EVIDENCE_SUFFICIENT` | 0.5323 | 12 | 0 | 3 | 0 | 80.0 |
| **합계** | **20개 질문** | 충분 17 · 부족 3 · 정책 미검색 0 | — | **118** | **45** | **134** | **3** | **46.8** |

### 질문별 Search note와 검색 결과

<a id="rag-20-run-02-q01"></a>

#### 01 · 추적번호 없는 배송 지연

**영어 검색 질의**

> I paid for a game controller from a US seller on eBay.com on November 18. The estimated delivery date was November 24. As of November 28 it has not arrived, the seller only says it was shipped, and the order has no tracking number. Can I report it missing now? How long should I wait for the seller and where can I ask eBay for help?

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 4 · 불확실 0 · 적용 불가 11 · 미분류 0 · **적용 점수 26.7 / 100**

**Search note**

> 예상 배송일 11월 24일이 지났으므로 지금 Purchases에서 미수령으로 신고하고 판매자에게 배송 정보·교체·환불을 요청할 수 있습니다. 판매자는 신고 후 3영업일 안에 답변해야 하며, 해결되지 않으면 요청을 연 뒤 3영업일 후 eBay에 도움을 요청할 수 있지만, 환불 결과는 자격 요건과 배송 증거 검토에 달려 있습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | 0.6728 | `APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 2 | eBay Money Back Guarantee policy | 0.5016 | `APPLICABLE` | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` |
| 3 | eBay Money Back Guarantee policy | 0.4925 | `APPLICABLE` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 4 | Get help if you bought as a guest | 0.4909 | `NOT_APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 5 | Get help if you bought as a guest | 0.4872 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 6 | eBay Money Back Guarantee policy | 0.4620 | `NOT_APPLICABLE` | `347c4c37-e21c-4f86-9575-306c1651a5df` |
| 7 | Check the status of your request | 0.4602 | `NOT_APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 8 | Get help if you bought as a guest | 0.4320 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 9 | eBay Money Back Guarantee policy | 0.4279 | `NOT_APPLICABLE` | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` |
| 10 | Return an item for a refund | 0.4247 | `NOT_APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 11 | International purchases and shipping for buyers | 0.4185 | `NOT_APPLICABLE` | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` |
| 12 | Selling practices policy | 0.4163 | `NOT_APPLICABLE` | `29dc7c6c-e4f2-4783-b537-a7c16da81994` |
| 13 | International purchases and shipping for buyers | 0.4149 | `NOT_APPLICABLE` | `9da141f2-c82b-4943-9404-e162c739dda3` |
| 14 | eBay Money Back Guarantee policy | 0.4091 | `NOT_APPLICABLE` | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` |
| 15 | eBay Money Back Guarantee policy | 0.4054 | `APPLICABLE` | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` |

</details>

<a id="rag-20-run-02-q02"></a>

#### 02 · 노트북 결함과 부분 환불

**영어 검색 질의**

> I received a used laptop on November 25. The listing said the screen had no defects, but it flickered from day one and I recorded a video. The seller offered $40 for repairs, which I have not accepted, and the listing says no returns. Can I return it as faulty and recover the price and original shipping? Who pays return shipping?

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 12 · 불확실 1 · 적용 불가 2 · 미분류 0 · **적용 점수 83.3 / 100**

**Search note**

> 화면 깜박임이 설명과 다르거나 제품 결함이면 ‘반품 불가’ 표시와 무관하게 반품을 요청할 수 있고, 결함·설명 불일치 반품의 배송비는 판매자 부담입니다. 반품이 수락되어 제품을 반환하면 원래 배송비를 포함한 전액 환불이 안내되지만, 확정 환불은 판매자 응답이나 eBay 심사에 달려 있으며 사용·손상 상태에 따른 공제 가능성도 있습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Return an item for a refund | 0.4859 | `APPLICABLE` | `927f2845-49b1-40f5-b351-48dee6c6ce2a` |
| 2 | Return an item for a refund | 0.4623 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 3 | Return shipping for buyers | 0.4572 | `APPLICABLE` | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` |
| 4 | Condition of returned items policy | 0.4541 | `APPLICABLE` | `5969c401-95f9-4001-99e5-4c8592208453` |
| 5 | How to handle a return request as a seller | 0.4481 | `APPLICABLE` | `c4954f02-57d2-46c7-8e54-0ed22200153e` |
| 6 | Get help if you bought as a guest | 0.4474 | `APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 7 | How to handle a return request as a seller | 0.4466 | `APPLICABLE` | `99dbe313-a5e6-473a-884d-9758d7490e93` |
| 8 | eBay Refurbished | 0.4458 | `NOT_APPLICABLE` | `83be0678-d0f2-40f2-aecf-6d4bc8d49f52` |
| 9 | Return an item for a refund | 0.4458 | `APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 10 | eBay Money Back Guarantee policy | 0.4408 | `APPLICABLE` | `77c77430-aceb-4f79-b632-ef78c863faf0` |
| 11 | Return an item for a refund | 0.4404 | `APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 12 | Return shipping for buyers | 0.4398 | `UNCERTAIN` | `b01c873d-8724-4642-adf2-4ad03f6ada39` |
| 13 | Return shipping for buyers | 0.4389 | `APPLICABLE` | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` |
| 14 | Get help if you bought as a guest | 0.4380 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 15 | eBay Money Back Guarantee policy | 0.4362 | `APPLICABLE` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |

</details>

<a id="rag-20-run-02-q03"></a>

#### 03 · 발송 전 주문 취소

**영어 검색 질의**

> I bought headphones on eBay.com using Buy It Now and paid by card 20 minutes ago. I found a lower price and want to request cancellation from the order details. There is no tracking number or shipped status. Does the seller have to accept? What happens if they decline or do not respond?

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 2 · 불확실 0 · 적용 불가 13 · 미분류 0 · **적용 점수 13.3 / 100**

**Search note**

> 판매자는 구매자의 취소 요청을 수락하거나 거절할 수 있으므로, 배송 전이고 구매 후 30일 이내라는 사실만으로 수락 의무가 생기지는 않습니다. 판매자가 요청 후 3일 안에 응답하지 않으면 이미 결제한 주문은 취소되지 않고 요청이 종료되며, 취소가 수락되면 eBay 결제 건은 원래 결제수단으로 전액 환불됩니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Order cancellation policy | 0.5577 | `APPLICABLE` | `4f1d8091-f413-47f9-92b5-8fcf59780b03` |
| 2 | Order cancellation policy | 0.5529 | `APPLICABLE` | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` |
| 3 | Selling practices policy | 0.5043 | `NOT_APPLICABLE` | `29dc7c6c-e4f2-4783-b537-a7c16da81994` |
| 4 | Order cancellation policy | 0.4899 | `NOT_APPLICABLE` | `6aa36185-3f49-40c5-bbee-fb0855dfcf39` |
| 5 | Get help if you bought as a guest | 0.4891 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 6 | Get help if you bought as a guest | 0.4729 | `NOT_APPLICABLE` | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` |
| 7 | How to handle a return request as a seller | 0.4646 | `NOT_APPLICABLE` | `350c99f0-67bd-4939-abab-98d6ddf129f7` |
| 8 | Check the status of your request | 0.4593 | `NOT_APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 9 | Get help if you bought as a guest | 0.4570 | `NOT_APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 10 | Return an item for a refund | 0.4510 | `NOT_APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 11 | Get help with an item that hasn't arrived | 0.4455 | `NOT_APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 12 | Get help if you bought as a guest | 0.4430 | `NOT_APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 13 | Get help if you bought as a guest | 0.4425 | `NOT_APPLICABLE` | `0f5fa726-6d78-401f-bca3-39487acb99e2` |
| 14 | Return an item for a refund | 0.4423 | `NOT_APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 15 | Get help if you bought as a guest | 0.4391 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |

</details>

<a id="rag-20-run-02-q04"></a>

#### 04 · 단순 변심과 최초 배송비

**영어 검색 질의**

> I ordered a black size M coat and received exactly that. I tried it on indoors once, the tags are attached, and it arrived four days ago. The order allows returns. If I return it because I changed my mind, will I recover the $12 I originally paid for shipping? Who pays for the return label?

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 2 · 불확실 7 · 적용 불가 6 · 미분류 0 · **적용 점수 36.7 / 100**

**Search note**

> 변심 반품은 판매자 반품 정책에 따라 반품 배송비 부담자가 정해지며, 구매자 부담이면 원래 배송비는 환불에 포함되지 않고 반품 라벨 비용도 구매자가 부담합니다. 주문에 반품이 허용된다는 사실만으로 원래 낸 $12의 환불 여부나 반품 라벨 부담자를 확정할 수 없으므로, 해당 주문의 판매자 반품 정책에서 확인해야 합니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Return shipping for buyers | 0.4781 | `UNCERTAIN` | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` |
| 2 | Return shipping for buyers | 0.4718 | `UNCERTAIN` | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` |
| 3 | Return shipping for buyers | 0.4619 | `NOT_APPLICABLE` | `b01c873d-8724-4642-adf2-4ad03f6ada39` |
| 4 | Return an item for a refund | 0.4283 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 5 | eBay Money Back Guarantee policy | 0.4217 | `UNCERTAIN` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |
| 6 | eBay Money Back Guarantee policy | 0.4160 | `UNCERTAIN` | `77c77430-aceb-4f79-b632-ef78c863faf0` |
| 7 | eBay International Shipping - Buyer Terms & Conditions | 0.4080 | `NOT_APPLICABLE` | `bf37f2b1-9659-40d3-844a-11545aaba365` |
| 8 | eBay Money Back Guarantee policy | 0.4075 | `NOT_APPLICABLE` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |
| 9 | Get help if you bought as a guest | 0.4058 | `UNCERTAIN` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 10 | Get help if you bought as a guest | 0.4022 | `APPLICABLE` | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` |
| 11 | eBay Authenticity Guarantee for Sneakers | 0.3975 | `NOT_APPLICABLE` | `4c3b3f5c-f79d-4e33-8ac0-c3d09a75123f` |
| 12 | Get help if you bought as a guest | 0.3942 | `UNCERTAIN` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 13 | Check the status of your request | 0.3932 | `NOT_APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 14 | Get help if you bought as a guest | 0.3923 | `UNCERTAIN` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 15 | eBay International Shipping - Buyer Terms & Conditions | 0.3894 | `NOT_APPLICABLE` | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` |

</details>

<a id="rag-20-run-02-q05"></a>

#### 05 · 파손 반품과 국제 반송비

**영어 검색 질의**

> My espresso machine arrived with a cracked water tank and a dented shipping box. I took photos and requested a return the day it arrived. Instead of sending a label, the seller asks me to pay international return postage upfront. Who arranges and pays for shipping, and how can I confirm whether I should pay first?

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 7 · 불확실 8 · 적용 불가 0 · 미분류 0 · **적용 점수 73.3 / 100**

**Search note**

> 깨지거나 손상된 상품은 판매자가 반품 배송비를 부담하며, 판매자는 라벨 또는 구매자가 수락할 수 있는 다른 반송 방법을 제공해야 합니다. 다만 다른 배송 방법의 비용·서비스는 판매자와 먼저 합의해야 하므로, 주문의 배송 프로그램과 반품 세부정보를 확인하고 eBay 메시지로 비용 부담과 사전 결제 여부를 확인하세요. 판매자가 비용을 부담하지 않으면 eBay에 개입을 요청할 수 있지만, 검색된 정책만으로 판매자가 반드시 특정 라벨을 보내거나 선결제 비용을 환급한다고 단정할 수는 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Return shipping for buyers | 0.5066 | `APPLICABLE` | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` |
| 2 | Return shipping for buyers | 0.5003 | `APPLICABLE` | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` |
| 3 | Return shipping for buyers | 0.4911 | `UNCERTAIN` | `b01c873d-8724-4642-adf2-4ad03f6ada39` |
| 4 | How to handle a return request as a seller | 0.4654 | `APPLICABLE` | `c4954f02-57d2-46c7-8e54-0ed22200153e` |
| 5 | International purchases and shipping for buyers | 0.4629 | `UNCERTAIN` | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` |
| 6 | eBay International Shipping - Buyer Terms & Conditions | 0.4579 | `UNCERTAIN` | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` |
| 7 | eBay International Shipping - Buyer Terms & Conditions | 0.4536 | `UNCERTAIN` | `5dd13ab2-02b1-4a36-8e2b-1ba1e0162b44` |
| 8 | International purchases and shipping for buyers | 0.4534 | `UNCERTAIN` | `9da141f2-c82b-4943-9404-e162c739dda3` |
| 9 | eBay International Shipping - Buyer Terms & Conditions | 0.4526 | `UNCERTAIN` | `bf37f2b1-9659-40d3-844a-11545aaba365` |
| 10 | eBay Money Back Guarantee policy | 0.4503 | `APPLICABLE` | `77c77430-aceb-4f79-b632-ef78c863faf0` |
| 11 | eBay International Shipping \| Seller Center | 0.4495 | `UNCERTAIN` | `ba80853d-fe28-4579-81c0-530b40ab121c` |
| 12 | How to handle a return request as a seller | 0.4456 | `APPLICABLE` | `99dbe313-a5e6-473a-884d-9758d7490e93` |
| 13 | Get help if you bought as a guest | 0.4442 | `APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 14 | eBay International Shipping Seller Terms & Conditions | 0.4427 | `UNCERTAIN` | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` |
| 15 | eBay Money Back Guarantee policy | 0.4411 | `APPLICABLE` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |

</details>

<a id="rag-20-run-02-q06"></a>

#### 06 · 교체 약속과 반품 요청 종료

**영어 검색 질의**

> I opened an eBay return after receiving sneakers in the wrong color. The seller messaged that they would send a replacement if I closed the request first. There is no replacement tracking number yet. If I close it and the replacement never arrives, can I reopen the return or ask eBay for help on the same order?

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 9 · 불확실 1 · 적용 불가 5 · 미분류 0 · **적용 점수 63.3 / 100**

**Search note**

> Wrong-color sneakers support the claim that the item did not match the listing, and the seller’s replacement offer is an option, not a guaranteed outcome. Do not close the request unless the issue is resolved: eBay’s policy says a closed return cannot be reopened and a new request cannot be filed for the same transaction; eBay assistance may be requested if the seller has not resolved the issue after 3 business days, subject to the request remaining open and eligibility requirements.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Check the status of your request | 0.5618 | `APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 2 | Get help with an item that hasn't arrived | 0.5340 | `NOT_APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 3 | Get help if you bought as a guest | 0.5285 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 4 | Return an item for a refund | 0.5187 | `APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 5 | Get help if you bought as a guest | 0.5177 | `APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 6 | Get help if you bought as a guest | 0.5130 | `APPLICABLE` | `0f5fa726-6d78-401f-bca3-39487acb99e2` |
| 7 | How to handle a return request as a seller | 0.5087 | `APPLICABLE` | `350c99f0-67bd-4939-abab-98d6ddf129f7` |
| 8 | Get help if you bought as a guest | 0.5077 | `APPLICABLE` | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` |
| 9 | How to handle a return request as a seller | 0.4932 | `APPLICABLE` | `0655d062-13e1-45e5-af4b-9a5e802f811c` |
| 10 | How to handle a return request as a seller | 0.4876 | `NOT_APPLICABLE` | `99dbe313-a5e6-473a-884d-9758d7490e93` |
| 11 | Get help if you bought as a guest | 0.4871 | `APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 12 | eBay Authenticity Guarantee for Sneakers | 0.4869 | `UNCERTAIN` | `b52463d6-3981-4dda-bbc0-5853e70bb899` |
| 13 | Return an item for a refund | 0.4826 | `APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 14 | Get help if you bought as a guest | 0.4773 | `NOT_APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 15 | How to handle a return request as a seller | 0.4771 | `NOT_APPLICABLE` | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` |

</details>

<a id="rag-20-run-02-q07"></a>

#### 07 · 카드사 결제 이의 제기

**영어 검색 질의**

> My camera is three days past its expected delivery date and has not arrived, so I opened an eBay missing-item request. While I am waiting for the seller, my card issuer says I can dispute the same payment. What happens to the open eBay request and buyer protection if I dispute it with my card issuer now?

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 4 · 불확실 0 · 적용 불가 10 · 미분류 1 · **적용 점수 26.7 / 100**

**Search note**

> 검색된 정책은 배송 예정일이 지난 뒤 미수령 신고를 하고, 판매자 응답을 기다린 뒤 해결되지 않으면 eBay에 개입을 요청하는 절차와 판매자의 3영업일 내 응답 의무를 뒷받침합니다. 다만 같은 거래에 카드사 차지백이나 결제 제공자 보호 청구를 제기하면 eBay가 미수령 보호 사건을 종료할 수 있으므로, 카드사 분쟁을 시작해도 eBay 요청이 계속 열린다고 보장할 수 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Check the status of your request | 0.5551 | `APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 2 | Get help with an item that hasn't arrived | 0.5467 | `APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 3 | Signature confirmation policy | 0.5165 | `NOT_APPLICABLE` | `e0323222-01f1-4d5f-a76a-7b5888d4e0f5` |
| 4 | Return an item for a refund | 0.5119 | `NOT_APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 5 | eBay Money Back Guarantee policy | 0.4922 | `APPLICABLE` | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` |
| 6 | Get help if you bought as a guest | 0.4917 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 7 | Return an item for a refund | 0.4902 | `NOT_APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 8 | eBay Money Back Guarantee policy | 0.4898 | `NOT_APPLICABLE` | `3b462389-7437-407c-87bd-ea2d91d04130` |
| 9 | eBay Money Back Guarantee policy | 0.4839 | `NOT_APPLICABLE` | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` |
| 10 | How to handle a return request as a seller | 0.4727 | `NOT_APPLICABLE` | `350c99f0-67bd-4939-abab-98d6ddf129f7` |
| 11 | How to handle a return request as a seller | 0.4658 | `NOT_APPLICABLE` | `99dbe313-a5e6-473a-884d-9758d7490e93` |
| 12 | eBay Money Back Guarantee policy | 0.4657 | `NOT_APPLICABLE` | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` |
| 13 | How to handle a return request as a seller | 0.4655 | `NOT_APPLICABLE` | `0655d062-13e1-45e5-af4b-9a5e802f811c` |
| 14 | eBay Money Back Guarantee policy | 0.4635 | — | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 15 | eBay Money Back Guarantee policy | 0.4632 | `APPLICABLE` | `6e065bd8-ddcb-4159-88ec-51f7904be5f9` |

</details>

<a id="rag-20-run-02-q08"></a>

#### 08 · 배대지 경유와 상품 불일치

**영어 검색 질의**

> I bought a jacket from a US seller while in South Korea and entered a US forwarding warehouse as my eBay order address. Tracking shows delivery to that warehouse, and it forwarded the parcel to Korea. I found the wrong color inside, but the warehouse did not take unpacking photos. Can I return it as not as described or get a refund through eBay?

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 7 · 불확실 3 · 적용 불가 5 · 미분류 0 · **적용 점수 56.7 / 100**

**Search note**

> 리스팅과 다른 색상의 재킷을 받았다면 판매자 반품 불가 정책과 관계없이 ‘not as described’ 반품을 요청할 근거가 있고, 요건을 충족하면 반품 배송비는 판매자 부담이며 eBay에 개입을 요청할 수 있습니다. 다만 주문 주소의 포워더 창고가 실제 배송지였고 포워딩 뒤 잘못된 색상이 발견된 상황에서 포워더의 개봉 사진이 없으므로, 적용·환불 여부는 요청 기한과 리스팅 색상, 거래가 eBay International Shipping이었는지 및 증빙을 확인해야 하며 결과가 보장되지는 않습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Return an item for a refund | 0.5410 | `APPLICABLE` | `927f2845-49b1-40f5-b351-48dee6c6ce2a` |
| 2 | Return an item for a refund | 0.5251 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 3 | Return an item for a refund | 0.5138 | `APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 4 | Get help if you bought as a guest | 0.5134 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 5 | Get help if you bought as a guest | 0.5122 | `APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 6 | Return an item for a refund | 0.5120 | `APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 7 | Return shipping for buyers | 0.5018 | `UNCERTAIN` | `b01c873d-8724-4642-adf2-4ad03f6ada39` |
| 8 | Get help with an item that hasn't arrived | 0.5001 | `NOT_APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 9 | Get help if you bought as a guest | 0.4956 | `APPLICABLE` | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` |
| 10 | eBay International Shipping Seller Terms & Conditions | 0.4943 | `UNCERTAIN` | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` |
| 11 | eBay International Shipping - Buyer Terms & Conditions | 0.4907 | `UNCERTAIN` | `bf37f2b1-9659-40d3-844a-11545aaba365` |
| 12 | eBay International Shipping - Buyer Terms & Conditions | 0.4894 | `NOT_APPLICABLE` | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` |
| 13 | eBay Money Back Guarantee policy | 0.4891 | `APPLICABLE` | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` |
| 14 | eBay Money Back Guarantee policy | 0.4889 | `NOT_APPLICABLE` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |
| 15 | Get help if you bought as a guest | 0.4888 | `NOT_APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |

</details>

<a id="rag-20-run-02-q09"></a>

#### 09 · 한 주문의 일부 미수령

**영어 검색 질의**

> I added a camera and memory card from one seller to my cart separately and paid together. They have separate prices but the same tracking number. Only the camera was in the box. Can I get the missing memory card or just its price refunded? Would the process differ if both were sold as a set in one listing?

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 7 · 불확실 2 · 적용 불가 6 · 미분류 0 · **적용 점수 53.3 / 100**

**Search note**

> 별도 리스팅으로 각각 구매한 물품이라면 메모리 카드를 별도 미수령 신고하고 판매자에게 교체품이나 원래 결제수단 환불을 요청할 근거가 있습니다. 세트 리스팅이면 세트 전체에 대한 반품 요청이 필요하며, 판매자가 부분 환불이나 교체를 제안할 수는 있지만 부분 환불이 보장되지는 않습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | 0.4581 | `APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 2 | Return an item for a refund | 0.4051 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 3 | Get help if you bought as a guest | 0.4027 | `APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 4 | Get help if you bought as a guest | 0.3914 | `APPLICABLE` | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` |
| 5 | Return an item for a refund | 0.3858 | `APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 6 | Get help if you bought as a guest | 0.3745 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 7 | eBay International Shipping \| Seller Center | 0.3732 | `NOT_APPLICABLE` | `ba80853d-fe28-4579-81c0-530b40ab121c` |
| 8 | Get help if you bought as a guest | 0.3718 | `APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 9 | International purchases and shipping for buyers | 0.3690 | `NOT_APPLICABLE` | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` |
| 10 | Get help if you bought as a guest | 0.3679 | `APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 11 | eBay Money Back Guarantee policy | 0.3619 | `UNCERTAIN` | `3b462389-7437-407c-87bd-ea2d91d04130` |
| 12 | Condition of returned items policy | 0.3570 | `NOT_APPLICABLE` | `5969c401-95f9-4001-99e5-4c8592208453` |
| 13 | Return shipping for buyers | 0.3534 | `NOT_APPLICABLE` | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` |
| 14 | eBay Money Back Guarantee policy | 0.3534 | `UNCERTAIN` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 15 | How to handle a return request as a seller | 0.3484 | `NOT_APPLICABLE` | `6109adcf-ec42-4a95-818c-a2ef540a612d` |

</details>

<a id="rag-20-run-02-q10"></a>

#### 10 · 반송 완료 후 카드 환불 지연

**영어 검색 질의**

> The seller confirmed by message that they received my return on November 19, and tracking shows delivery. As of November 28, no refund appears on my card and the seller has not told me when it was issued. What should I check in the return details to tell whether it was never issued or is still being processed by the card issuer?

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 6 · 불확실 0 · 적용 불가 9 · 미분류 0 · **적용 점수 40.0 / 100**

**Search note**

> 반품 세부정보에서 환불 기한과 환불 상태를 확인하고, 환불이 발행됐는지 판매자에게 확인하라는 근거로 쓸 수 있습니다. 판매자는 반품품 수령 후 보통 2영업일 내 환불해야 하지만 세부정보에 더 늦은 기한이 표시될 수 있고, 발행된 환불도 결제 수단에 반영되기까지 보통 3~5영업일이 걸릴 수 있으므로 현재 사실만으로 미발행이라고 단정할 수 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Check the status of your request | 0.5231 | `APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 2 | eBay Money Back Guarantee policy | 0.4953 | `APPLICABLE` | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` |
| 3 | Get help if you bought as a guest | 0.4804 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 4 | Get help with an item that hasn't arrived | 0.4793 | `NOT_APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 5 | How to handle a return request as a seller | 0.4760 | `NOT_APPLICABLE` | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` |
| 6 | Return an item for a refund | 0.4752 | `APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 7 | Return an item for a refund | 0.4703 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 8 | How to handle a return request as a seller | 0.4615 | `NOT_APPLICABLE` | `350c99f0-67bd-4939-abab-98d6ddf129f7` |
| 9 | eBay Money Back Guarantee policy | 0.4609 | `NOT_APPLICABLE` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 10 | How to handle a return request as a seller | 0.4555 | `NOT_APPLICABLE` | `6109adcf-ec42-4a95-818c-a2ef540a612d` |
| 11 | eBay Money Back Guarantee policy | 0.4505 | `NOT_APPLICABLE` | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` |
| 12 | eBay Money Back Guarantee policy | 0.4498 | `NOT_APPLICABLE` | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` |
| 13 | eBay Money Back Guarantee policy | 0.4447 | `NOT_APPLICABLE` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |
| 14 | How to handle a return request as a seller | 0.4425 | `APPLICABLE` | `d58dd7b1-f4d6-47ba-b7ae-81501242f723` |
| 15 | Return an item for a refund | 0.4425 | `APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |

</details>

<a id="rag-20-run-02-q11"></a>

#### 11 · 반품 주소 불일치

**영어 검색 질의**

> The prepaid label in my eBay return request lists a California address, but the seller messaged me to send the package to a Nevada warehouse. There is no new label in the return details and the deadline is in two days. Which address should I use, and what tracking or delivery evidence should I keep in case of a dispute?

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 8 · 불확실 0 · 적용 불가 7 · 미분류 0 · **적용 점수 53.3 / 100**

**Search note**

> 검색된 정책은 판매자가 배송비를 부담하는 경우 eBay가 제공한 라벨 또는 판매자와 합의한 다른 반송 방법을 사용하고, 반송 추적 및 배송 증빙을 남기라는 근거가 됩니다. 다만 판매자가 메시지로 안내한 네바다 주소가 반송 요청에 합의된 주소인지, 기존 라벨을 대체하는 새 라벨이 발급될지는 확인되지 않았으므로 라벨 주소와 다른 곳으로 보내기 전에 eBay 반송 상세에서 확인하거나 eBay에 도움을 요청하세요.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Return shipping for buyers | 0.5036 | `APPLICABLE` | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` |
| 2 | Check the status of your request | 0.4990 | `APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 3 | Return shipping for buyers | 0.4905 | `APPLICABLE` | `b01c873d-8724-4642-adf2-4ad03f6ada39` |
| 4 | Return shipping for buyers | 0.4829 | `APPLICABLE` | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` |
| 5 | How to handle a return request as a seller | 0.4745 | `APPLICABLE` | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` |
| 6 | Appeal the outcome of a case as a buyer | 0.4557 | `NOT_APPLICABLE` | `8b037b81-9cde-4035-bf40-683bb1eb2a47` |
| 7 | How to handle a return request as a seller | 0.4544 | `NOT_APPLICABLE` | `c4954f02-57d2-46c7-8e54-0ed22200153e` |
| 8 | eBay Money Back Guarantee policy | 0.4494 | `NOT_APPLICABLE` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |
| 9 | eBay Money Back Guarantee policy | 0.4478 | `APPLICABLE` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |
| 10 | How to handle a return request as a seller | 0.4453 | `NOT_APPLICABLE` | `350c99f0-67bd-4939-abab-98d6ddf129f7` |
| 11 | How to handle a return request as a seller | 0.4437 | `NOT_APPLICABLE` | `0655d062-13e1-45e5-af4b-9a5e802f811c` |
| 12 | Get help with an item that hasn't arrived | 0.4436 | `NOT_APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 13 | Return an item for a refund | 0.4418 | `APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 14 | How to handle a return request as a seller | 0.4358 | `NOT_APPLICABLE` | `00ab85d7-00dc-4e04-bc46-416a6a759751` |
| 15 | eBay Money Back Guarantee policy | 0.4341 | `APPLICABLE` | `77c77430-aceb-4f79-b632-ef78c863faf0` |

</details>

<a id="rag-20-run-02-q12"></a>

#### 12 · 고가 상품과 대리 수령

**영어 검색 질의**

> I bought a camera on eBay.com for $1,080 including shipping and tax and used my friend’s home as the order address. Tracking says delivered but my friend never received it, and the seller only showed a delivered status. Can I ask for proof that the delivery address matches the order and for the signature record? Would it matter if my friend actually accepted it for me?

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 4 · 불확실 2 · 적용 불가 8 · 미분류 1 · **적용 점수 33.3 / 100**

**Search note**

> 판매자에게 주문 상세의 배송지와 일치하는 주소(우편번호, 없으면 도시/카운티)로 배송됐다는 증거와 배송일·추적 상태, 총액 $750 이상 주문에 필요한 서명 확인 기록을 요청할 근거가 있습니다. 친구가 대신 받은 경우 제3자가 구매자를 대신해 수령한 물품은 미수령 보장 제외에 해당할 수 있지만, 실제 수령 여부는 확인되지 않았으며 정책이 환불 결과를 미리 보장하지는 않습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | 0.4839 | `APPLICABLE` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 2 | Get help with an item that hasn't arrived | 0.4665 | `APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 3 | eBay Money Back Guarantee policy | 0.4468 | `UNCERTAIN` | `347c4c37-e21c-4f86-9575-306c1651a5df` |
| 4 | eBay Money Back Guarantee policy | 0.4420 | `NOT_APPLICABLE` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |
| 5 | Signature confirmation policy | 0.4390 | `APPLICABLE` | `604b7fbb-2462-4a94-a0a3-59fb5290d846` |
| 6 | Get help if you bought as a guest | 0.4379 | `NOT_APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 7 | Get help if you bought as a guest | 0.4241 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 8 | Signature confirmation policy | 0.4210 | `APPLICABLE` | `e2b3df5e-28ee-48d2-b4f7-f78175e269b9` |
| 9 | International purchases and shipping for buyers | 0.4169 | `NOT_APPLICABLE` | `02ed01df-7637-4e68-8458-f8e01b540ce4` |
| 10 | Get help if you bought as a guest | 0.4031 | `UNCERTAIN` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 11 | eBay International Shipping - Buyer Terms & Conditions | 0.4004 | `NOT_APPLICABLE` | `88ee7312-806c-49e9-875f-f20a8881ce44` |
| 12 | International purchases and shipping for buyers | 0.3899 | `NOT_APPLICABLE` | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` |
| 13 | eBay Money Back Guarantee policy | 0.3894 | `NOT_APPLICABLE` | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` |
| 14 | eBay Authenticity Guarantee for Sneakers | 0.3867 | — | `4c3b3f5c-f79d-4e33-8ac0-c3d09a75123f` |
| 15 | International purchases and shipping for buyers | 0.3852 | `NOT_APPLICABLE` | `76066a7d-2738-449c-9574-7d68d06faa54` |

</details>

<a id="rag-20-run-02-q13"></a>

#### 13 · 운동화 상태와 위조 의심

**영어 검색 질의**

> Sneakers listed as new arrived with dirt and wear on the soles, and the product code on the box differs from the shoes. I could not find an authenticity verification indicator on the order, and they arrived two days ago. What process can I use to report the condition mismatch or suspected counterfeit item?

**Search status** · `EVIDENCE_INSUFFICIENT`

**청크 적용 판단** · 적용 3 · 불확실 12 · 적용 불가 0 · 미분류 0 · **적용 점수 60.0 / 100**

**Search note**

> 판매자에게 상품 설명과 다르다고 반품을 요청할 근거는 있습니다. 다만 주문의 Authenticity Guarantee 적용 여부와 판매자의 반품 기간·최종 판매 여부가 확인되지 않아, 이 절차만으로 환불이나 위조 판정을 보장할 수는 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay Authenticity Guarantee for Sneakers | 0.6133 | `APPLICABLE` | `a8720eaf-1dbd-4eaa-929a-d067e5780bf1` |
| 2 | eBay Authenticity Guarantee for Sneakers | 0.5962 | `APPLICABLE` | `668ef1c6-a0e2-4a5d-bf03-c24cc6e00f89` |
| 3 | eBay Authenticity Guarantee for Sneakers | 0.5692 | `UNCERTAIN` | `bd335ae7-a208-4fb1-a080-26f3ce45688f` |
| 4 | eBay Authenticity Guarantee for Sneakers | 0.5270 | `APPLICABLE` | `b52463d6-3981-4dda-bbc0-5853e70bb899` |
| 5 | eBay Authenticity Guarantee for Sneakers | 0.5269 | `UNCERTAIN` | `909603f2-3b0e-4a92-b440-b3b76df9df36` |
| 6 | eBay Authenticity Guarantee for Sneakers | 0.5228 | `UNCERTAIN` | `4c3b3f5c-f79d-4e33-8ac0-c3d09a75123f` |
| 7 | eBay Authenticity Guarantee for Sneakers | 0.5180 | `UNCERTAIN` | `4493c75d-f79a-4cd5-b15e-e7c847137741` |
| 8 | eBay Authenticity Guarantee for Sneakers | 0.5145 | `UNCERTAIN` | `d7ea9541-18ed-4a3b-860e-44597608060c` |
| 9 | eBay Authenticity Guarantee for Sneakers | 0.5132 | `UNCERTAIN` | `310c3728-23b8-46d5-88f9-cfccc4aa62bc` |
| 10 | eBay Authenticity Guarantee for Sneakers | 0.5116 | `UNCERTAIN` | `28a46454-f148-412a-b6be-827a11666c92` |
| 11 | eBay Authenticity Guarantee for Sneakers | 0.5051 | `UNCERTAIN` | `b3af4677-12f3-44be-8750-6cb51b7529b9` |
| 12 | Buying with Authenticity Guarantee | 0.5026 | `UNCERTAIN` | `79a064e9-afda-4eb4-8f2b-171482196f23` |
| 13 | eBay Authenticity Guarantee for Sneakers | 0.4997 | `UNCERTAIN` | `28c18aa9-8df1-4134-bfa2-934df227a276` |
| 14 | eBay Authenticity Guarantee for Sneakers | 0.4980 | `UNCERTAIN` | `a2c75035-7ecb-4809-a425-bb9a5655986c` |
| 15 | eBay Authenticity Guarantee for Sneakers | 0.4766 | `UNCERTAIN` | `b7fadb64-8245-4379-8046-a7ababfa5da8` |

</details>

<a id="rag-20-run-02-q14"></a>

#### 14 · 비회원 불량품 반품

**영어 검색 질의**

> I paid by credit card without an eBay account. I have the order confirmation email but no account to sign in to. A Bluetooth speaker delivered yesterday does not turn on. Where in the email do I start a return, and how do I submit photos or a description of the defect?

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 6 · 불확실 0 · 적용 불가 9 · 미분류 0 · **적용 점수 40.0 / 100**

**Search note**

> 게스트 구매자는 주문 확인 이메일에서 View order details > Return item으로 반품을 시작하고, 사유를 선택한 뒤 판매자에게 메시지를 적고 결함 사진을 최대 10장 올려 제출할 수 있습니다. 제품이 고장 난 경우 반품 거부 정책과 관계없이 반품 대상이 될 수 있지만, 환불·교환 결과는 확정된 것이 아니며 판매자가 응답하고 해결 방안을 제시합니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help if you bought as a guest | 0.5318 | `APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 2 | Get help if you bought as a guest | 0.5241 | `APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 3 | Get help if you bought as a guest | 0.4927 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 4 | Get help if you bought as a guest | 0.4904 | `APPLICABLE` | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` |
| 5 | Return an item for a refund | 0.4899 | `APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 6 | Return an item for a refund | 0.4719 | `NOT_APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 7 | Return an item for a refund | 0.4634 | `NOT_APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 8 | Return an item for a refund | 0.4614 | `APPLICABLE` | `927f2845-49b1-40f5-b351-48dee6c6ce2a` |
| 9 | Get help if you bought as a guest | 0.4544 | `NOT_APPLICABLE` | `0f5fa726-6d78-401f-bca3-39487acb99e2` |
| 10 | eBay Refurbished | 0.4520 | `NOT_APPLICABLE` | `83be0678-d0f2-40f2-aecf-6d4bc8d49f52` |
| 11 | Get help if you bought as a guest | 0.4493 | `APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 12 | How to handle a return request as a seller | 0.4430 | `NOT_APPLICABLE` | `0655d062-13e1-45e5-af4b-9a5e802f811c` |
| 13 | Return shipping for buyers | 0.4426 | `NOT_APPLICABLE` | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` |
| 14 | How to handle a return request as a seller | 0.4417 | `NOT_APPLICABLE` | `99dbe313-a5e6-473a-884d-9758d7490e93` |
| 15 | How to handle a return request as a seller | 0.4360 | `NOT_APPLICABLE` | `350c99f0-67bd-4939-abab-98d6ddf129f7` |

</details>

<a id="rag-20-run-02-q15"></a>

#### 15 · 포장 개봉과 환불액 차감

**영어 검색 질의**

> I returned earphones that did not match the description. I broke the seal to test them for five minutes and returned all accessories and instructions. The seller deducted $60 from the $180 price, citing opened packaging without explaining any other damage. When can I dispute the deduction and ask eBay to help?

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 10 · 불확실 0 · 적용 불가 5 · 미분류 0 · **적용 점수 66.7 / 100**

**Search note**

> 설명과 다른 상품은 판매자의 일반 반품 불가 정책과 무관하게 Money Back Guarantee 대상이 될 수 있고, 반품품이 검사에 필요한 범위를 넘어 사용·변경·손상되지 않았다면 판매자는 가치 하락분을 임의로 공제할 수 없습니다. 포장 봉인을 시험 목적으로 5분간 연 사실만으로 공제가 정당한지는 확인되지 않으며, 판매자와 해결되지 않으면 반품 요청일로부터 3영업일 후 eBay에 개입을 요청할 수 있습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | How to handle a return request as a seller | 0.5767 | `APPLICABLE` | `1743ff1f-d3d4-4bdd-8248-211875998334` |
| 2 | Condition of returned items policy | 0.5484 | `APPLICABLE` | `5969c401-95f9-4001-99e5-4c8592208453` |
| 3 | Return an item for a refund | 0.5304 | `APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 4 | How to handle a return request as a seller | 0.5199 | `APPLICABLE` | `6109adcf-ec42-4a95-818c-a2ef540a612d` |
| 5 | Return an item for a refund | 0.5185 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 6 | Return an item for a refund | 0.5158 | `APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 7 | How to handle a return request as a seller | 0.5063 | `APPLICABLE` | `99dbe313-a5e6-473a-884d-9758d7490e93` |
| 8 | Return an item for a refund | 0.5026 | `APPLICABLE` | `927f2845-49b1-40f5-b351-48dee6c6ce2a` |
| 9 | Check the status of your request | 0.5025 | `APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 10 | eBay Money Back Guarantee policy | 0.5020 | `APPLICABLE` | `3b462389-7437-407c-87bd-ea2d91d04130` |
| 11 | Get help if you bought as a guest | 0.5004 | `NOT_APPLICABLE` | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` |
| 12 | Condition of returned items policy | 0.4993 | `NOT_APPLICABLE` | `87e1df21-066e-49e9-863e-7b3455872aed` |
| 13 | Appeal the outcome of a case as a buyer | 0.4958 | `NOT_APPLICABLE` | `8b037b81-9cde-4035-bf40-683bb1eb2a47` |
| 14 | How to handle a return request as a seller | 0.4941 | `NOT_APPLICABLE` | `350c99f0-67bd-4939-abab-98d6ddf129f7` |
| 15 | Get help if you bought as a guest | 0.4897 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |

</details>

<a id="rag-20-run-02-q16"></a>

#### 16 · 국제 배송 파손과 수입 비용

**영어 검색 질의**

> A $310 light shipped through eBay International Shipping arrived with a broken glass shade. My receipt shows $26 in import charges paid to eBay, and I paid the carrier $15 in taxes on delivery. If my return for damage is approved, can I recover the import charges paid to each party as well as the item price?

**Search status** · `EVIDENCE_INSUFFICIENT`

**청크 적용 판단** · 적용 6 · 불확실 1 · 적용 불가 7 · 미분류 1 · **적용 점수 43.3 / 100**

**Search note**

> 승인된 손상 클레임에서 eBay에 낸 수입세·관세·국제배송료·수입 수수료를 환급받을 수 있다는 조항은 근거로 제시할 수 있습니다. 다만 상품 가격 환급 여부와 배송 때 운송사에 낸 $15의 세금은 확인된 조항이 보장하지 않으며, 적용 가능한 배송 조건과 해당 금액의 성격을 확인해야 합니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | International purchases and shipping for buyers | 0.5881 | `APPLICABLE` | `02ed01df-7637-4e68-8458-f8e01b540ce4` |
| 2 | eBay International Shipping - Buyer Terms & Conditions | 0.5759 | `APPLICABLE` | `936ad65f-d348-453a-9ada-fd5061587211` |
| 3 | International purchases and shipping for buyers | 0.5708 | `NOT_APPLICABLE` | `76066a7d-2738-449c-9574-7d68d06faa54` |
| 4 | eBay International Shipping - Buyer Terms & Conditions | 0.5707 | `UNCERTAIN` | `bf37f2b1-9659-40d3-844a-11545aaba365` |
| 5 | eBay International Shipping - Buyer Terms & Conditions | 0.5656 | `APPLICABLE` | `5dd13ab2-02b1-4a36-8e2b-1ba1e0162b44` |
| 6 | International purchases and shipping for buyers | 0.5539 | `APPLICABLE` | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` |
| 7 | International purchases and shipping for buyers | 0.5455 | `NOT_APPLICABLE` | `9da141f2-c82b-4943-9404-e162c739dda3` |
| 8 | eBay International Shipping Seller Terms & Conditions | 0.5449 | `NOT_APPLICABLE` | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` |
| 9 | eBay International Shipping \| Seller Center | 0.5420 | `NOT_APPLICABLE` | `ba80853d-fe28-4579-81c0-530b40ab121c` |
| 10 | eBay International Shipping - Buyer Terms & Conditions | 0.5359 | `APPLICABLE` | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` |
| 11 | International purchases and shipping for buyers | 0.5353 | — | `45c4e413-c093-479c-b1a0-e97c51c95848` |
| 12 | Return shipping for buyers | 0.5345 | `NOT_APPLICABLE` | `b01c873d-8724-4642-adf2-4ad03f6ada39` |
| 13 | eBay International Shipping Seller Terms & Conditions | 0.5253 | `NOT_APPLICABLE` | `aea220ac-fba6-4e07-b5e4-8d76311a2a21` |
| 14 | eBay International Shipping - Buyer Terms & Conditions | 0.5218 | `APPLICABLE` | `89d3cde5-4330-49cf-872f-576d13c53acd` |
| 15 | Return shipping for buyers | 0.5210 | `NOT_APPLICABLE` | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` |

</details>

<a id="rag-20-run-02-q17"></a>

#### 17 · 예약 주문 배송 지연

**영어 검색 질의**

> I preordered a figure in August that the listing said would ship in October. In late November there is still no tracking number and the seller has postponed shipment to next month. More than 30 days have passed since ordering, and I have not opened a missing-item request. Can I request cancellation or a refund for non-delivery now?

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 1 · 불확실 5 · 적용 불가 9 · 미분류 0 · **적용 점수 23.3 / 100**

**Search note**

> 판매자에게 미도착 신고를 하고 배송 정보·환불 등을 요청할 근거는 있으며, 추정 배송일이 지난 뒤 신고하고 그 날짜부터 30일 안에 해야 합니다. 주문 취소는 주문 후 30일이 넘으면 불가하고, 그 안에도 취소 요청은 판매자가 수락해야 하므로 지금 취소나 환불이 보장되지는 않습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | 0.5241 | `UNCERTAIN` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 2 | Order cancellation policy | 0.4569 | `UNCERTAIN` | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` |
| 3 | Check the status of your request | 0.4471 | `NOT_APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 4 | Order cancellation policy | 0.4209 | `APPLICABLE` | `4f1d8091-f413-47f9-92b5-8fcf59780b03` |
| 5 | eBay Money Back Guarantee policy | 0.4083 | `UNCERTAIN` | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` |
| 6 | Get help if you bought as a guest | 0.4050 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 7 | Selling practices policy | 0.3966 | `NOT_APPLICABLE` | `29dc7c6c-e4f2-4783-b537-a7c16da81994` |
| 8 | eBay Money Back Guarantee policy | 0.3849 | `UNCERTAIN` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 9 | Return an item for a refund | 0.3845 | `NOT_APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 10 | eBay Money Back Guarantee policy | 0.3795 | `NOT_APPLICABLE` | `347c4c37-e21c-4f86-9575-306c1651a5df` |
| 11 | Get help if you bought as a guest | 0.3778 | `UNCERTAIN` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 12 | eBay Money Back Guarantee policy | 0.3675 | `NOT_APPLICABLE` | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` |
| 13 | Return an item for a refund | 0.3640 | `NOT_APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 14 | Order cancellation policy | 0.3640 | `NOT_APPLICABLE` | `6aa36185-3f49-40c5-bbee-fb0855dfcf39` |
| 15 | eBay Money Back Guarantee policy | 0.3623 | `NOT_APPLICABLE` | `3b462389-7437-407c-87bd-ea2d91d04130` |

</details>

<a id="rag-20-run-02-q18"></a>

#### 18 · 발송 후 배송 주소 오류

**영어 검색 질의**

> Just after payment I realized my order was addressed to my old apartment. I sent the seller my new address, but the parcel was already with the carrier and is in transit. Can I change the order address or ask for redelivery to the correct address? If I cannot collect it at the old address, can I ask for a refund?

**Search status** · `EVIDENCE_INSUFFICIENT`

**청크 적용 판단** · 적용 1 · 불확실 2 · 적용 불가 12 · 미분류 0 · **적용 점수 13.3 / 100**

**Search note**

> 주소 변경·운송 중 재배송을 보장하는 정책은 검색되지 않았고, 배송 전 주소가 잘못됐을 때 판매자에게 취소를 요청할 수 있지만 이미 발송된 주문은 취소할 수 없으며 원래 주소에서 수령을 시도하도록 안내합니다. 미수령 신고와 환불 절차는 있으나 예상 배송일이 지난 경우에 해당하므로, 현재 운송 중이고 수령 불가만으로 환불을 받을 수 있다고 단정할 수 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help if you bought as a guest | 0.3909 | `UNCERTAIN` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 2 | Get help with an item that hasn't arrived | 0.3878 | `UNCERTAIN` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 3 | Check the status of your request | 0.3782 | `NOT_APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 4 | Return an item for a refund | 0.3739 | `NOT_APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 5 | Get help if you bought as a guest | 0.3686 | `APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 6 | Get help if you bought as a guest | 0.3635 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 7 | Return an item for a refund | 0.3629 | `NOT_APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 8 | Get help if you bought as a guest | 0.3628 | `NOT_APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 9 | Order cancellation policy | 0.3567 | `NOT_APPLICABLE` | `4f1d8091-f413-47f9-92b5-8fcf59780b03` |
| 10 | How to handle a return request as a seller | 0.3525 | `NOT_APPLICABLE` | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` |
| 11 | Get help if you bought as a guest | 0.3511 | `NOT_APPLICABLE` | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` |
| 12 | How to handle a return request as a seller | 0.3470 | `NOT_APPLICABLE` | `c4954f02-57d2-46c7-8e54-0ed22200153e` |
| 13 | Order cancellation policy | 0.3465 | `NOT_APPLICABLE` | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` |
| 14 | Get help if you bought as a guest | 0.3461 | `NOT_APPLICABLE` | `0f5fa726-6d78-401f-bca3-39487acb99e2` |
| 15 | How to handle a return request as a seller | 0.3421 | `NOT_APPLICABLE` | `0655d062-13e1-45e5-af4b-9a5e802f811c` |

</details>

<a id="rag-20-run-02-q19"></a>

#### 19 · 고가 상품 반송과 서명

**영어 검색 질의**

> I bought an item on eBay.com for $900 including tax and shipping and paid for return shipping myself. I uploaded the tracking number to the return request, and it shows delivery to the seller’s address, but I did not purchase signature confirmation. If the seller says they did not receive it, is tracking alone sufficient proof of return delivery?

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 7 · 불확실 1 · 적용 불가 7 · 미분류 0 · **적용 점수 50.0 / 100**

**Search note**

> eBay 정책상 구매자가 반송비를 부담하고 eBay.com 주문 총액이 $750 이상이면, 반송 추적·주소 일치 증거 외에 서명 확인이 보호 요건입니다. 배송 추적만으로 배송 완료가 표시되어도 서명 확인이 없으므로 요건 충족을 보장할 수 없으며, eBay가 반송 분쟁을 어떻게 판정할지는 단정할 수 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | 0.5838 | `NOT_APPLICABLE` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 2 | Return shipping for buyers | 0.5567 | `APPLICABLE` | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` |
| 3 | Signature confirmation policy | 0.5478 | `APPLICABLE` | `604b7fbb-2462-4a94-a0a3-59fb5290d846` |
| 4 | Return shipping for buyers | 0.5212 | `APPLICABLE` | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` |
| 5 | Return shipping for buyers | 0.5163 | `UNCERTAIN` | `b01c873d-8724-4642-adf2-4ad03f6ada39` |
| 6 | eBay Money Back Guarantee policy | 0.5119 | `NOT_APPLICABLE` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |
| 7 | Signature confirmation policy | 0.5040 | `APPLICABLE` | `e2b3df5e-28ee-48d2-b4f7-f78175e269b9` |
| 8 | eBay Money Back Guarantee policy | 0.4989 | `NOT_APPLICABLE` | `347c4c37-e21c-4f86-9575-306c1651a5df` |
| 9 | eBay Money Back Guarantee policy | 0.4933 | `APPLICABLE` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |
| 10 | Get help with an item that hasn't arrived | 0.4916 | `NOT_APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 11 | eBay Money Back Guarantee policy | 0.4754 | `NOT_APPLICABLE` | `77c77430-aceb-4f79-b632-ef78c863faf0` |
| 12 | Get help if you bought as a guest | 0.4685 | `APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 13 | eBay Money Back Guarantee policy | 0.4663 | `NOT_APPLICABLE` | `cdca8d5f-812a-4fed-a6a6-380989fdf86a` |
| 14 | eBay Authenticity Guarantee for Sneakers | 0.4625 | `NOT_APPLICABLE` | `4c3b3f5c-f79d-4e33-8ac0-c3d09a75123f` |
| 15 | Return an item for a refund | 0.4520 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |

</details>

<a id="rag-20-run-02-q20"></a>

#### 20 · 플랫폼 밖 부분 환불

**영어 검색 질의**

> I have an open eBay return request for a defective $240 item. The seller proposed by eBay message to send me $70 by an off-platform bank transfer. I have not received a transfer and the request is still open. If I accept it, can I continue disputing the remaining $170 in the same request or ask eBay to intervene?

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 12 · 불확실 0 · 적용 불가 3 · 미분류 0 · **적용 점수 80.0 / 100**

**Search note**

> 검색된 정책은 판매자가 eBay 반품 요청에서 부분 환불을 제안할 수 있고, 해결되지 않으면 구매자나 판매자가 eBay에 개입을 요청할 수 있다고 뒷받침합니다. 다만 오프플랫폼 $70 이체를 수락한 뒤 같은 요청에서 나머지 $170을 계속 다툴 수 있는지는 확인되지 않았으므로, eBay 절차에서 제안을 수락하거나 요청을 닫기 전에 요청을 열린 상태로 두고 eBay에 확인하는 것이 안전합니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Check the status of your request | 0.5323 | `APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 2 | Return an item for a refund | 0.5205 | `APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 3 | How to handle a return request as a seller | 0.5204 | `APPLICABLE` | `0655d062-13e1-45e5-af4b-9a5e802f811c` |
| 4 | How to handle a return request as a seller | 0.5186 | `NOT_APPLICABLE` | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` |
| 5 | How to handle a return request as a seller | 0.5142 | `APPLICABLE` | `350c99f0-67bd-4939-abab-98d6ddf129f7` |
| 6 | How to handle a return request as a seller | 0.5130 | `APPLICABLE` | `99dbe313-a5e6-473a-884d-9758d7490e93` |
| 7 | How to handle a return request as a seller | 0.5092 | `APPLICABLE` | `00ab85d7-00dc-4e04-bc46-416a6a759751` |
| 8 | eBay Money Back Guarantee policy | 0.5060 | `APPLICABLE` | `3b462389-7437-407c-87bd-ea2d91d04130` |
| 9 | How to handle a return request as a seller | 0.5056 | `NOT_APPLICABLE` | `1743ff1f-d3d4-4bdd-8248-211875998334` |
| 10 | How to handle a return request as a seller | 0.5027 | `APPLICABLE` | `c4954f02-57d2-46c7-8e54-0ed22200153e` |
| 11 | Return an item for a refund | 0.4978 | `APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 12 | Get help if you bought as a guest | 0.4941 | `APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 13 | eBay Money Back Guarantee policy | 0.4895 | `APPLICABLE` | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` |
| 14 | How to handle a return request as a seller | 0.4880 | `APPLICABLE` | `d58dd7b1-f4d6-47ba-b7ae-81501242f723` |
| 15 | How to handle a return request as a seller | 0.4847 | `NOT_APPLICABLE` | `6109adcf-ec42-4a95-818c-a2ef540a612d` |

</details>

</details>

<a id="rag-20-run-03"></a>

## 실험 03 · 25개 문서 · 450/350 · topK=15 · 한국어 질의

<details>
<summary>실험 03 결과 · 접기/펼치기</summary>

### RAG 조건

| 변경 항목 | 실제 실행값 |
|---|---|
| 청킹 | 450/350, 문서 25개·청크 149개 |
| 질문 | 기준 한국어 상세 질문 20개 |
| 적재 | 청크 본문과 경계를 검증한 뒤 기존 벡터를 임시 PgVector 테이블에 적재 |

450/200과 450/350을 문서별로 대조한 결과 25개 모두 본문·경계가 같았다. 동일 본문 벡터를 재사용하고 질의 벡터만 생성했다. topK=15, 최소 유사도 0.0, 플랫폼 EBAY, text-embedding-3-large, gpt-6-luna를 사용했다. 청크별 reason을 간결하게 작성하는 지시는 실험 02와 같았다.

### 결과 요약

- 검색 및 모델 응답 생성 20/20건 · 반환 청크 300개
- 모델 Search status · 충분 19건 · 부족 1건 · 정책 미검색 0건
- 청크 판단 · 적용 131 · 불확실 41 · 적용 불가 125 · 미분류 3 · 적용 점수 50.5 / 100
- 생성 API 입력 132,912토큰 · 출력 32,879토큰 · 합계 165,791토큰

청크 본문과 경계가 동일하므로 이번 결과의 차이를 청킹 파라미터 변경의 효과로 해석하지 않는다. 실험 01은 문서 벡터를 새로 생성했고 이번에는 저장된 문서 벡터를 재사용했다. 질의 임베딩과 모델 응답도 다시 생성했다. 적용 점수는 (적용 + 0.5 × 불확실) ÷ 전체 반환 청크 수 × 100으로 계산했다.

| 질문 | 주제 | Search status | 1위 유사도 | 적용 | 불확실 | 적용 불가 | 미분류 | 적용 점수 / 100 |
|---:|---|---|---:|---:|---:|---:|---:|---:|
| [01](#rag-20-run-03-q01) | 추적번호 없는 배송 지연 | `EVIDENCE_SUFFICIENT` | 0.5890 | 4 | 0 | 11 | 0 | 26.7 |
| [02](#rag-20-run-03-q02) | 노트북 결함과 부분 환불 | `EVIDENCE_SUFFICIENT` | 0.4004 | 10 | 0 | 5 | 0 | 66.7 |
| [03](#rag-20-run-03-q03) | 발송 전 주문 취소 | `EVIDENCE_SUFFICIENT` | 0.5037 | 3 | 0 | 12 | 0 | 20.0 |
| [04](#rag-20-run-03-q04) | 단순 변심과 최초 배송비 | `EVIDENCE_SUFFICIENT` | 0.3598 | 10 | 0 | 5 | 0 | 66.7 |
| [05](#rag-20-run-03-q05) | 파손 반품과 국제 반송비 | `EVIDENCE_SUFFICIENT` | 0.4410 | 8 | 6 | 1 | 0 | 73.3 |
| [06](#rag-20-run-03-q06) | 교체 약속과 반품 요청 종료 | `EVIDENCE_SUFFICIENT` | 0.4961 | 9 | 0 | 5 | 1 | 60.0 |
| [07](#rag-20-run-03-q07) | 카드사 결제 이의 제기 | `EVIDENCE_SUFFICIENT` | 0.5307 | 8 | 0 | 7 | 0 | 53.3 |
| [08](#rag-20-run-03-q08) | 배대지 경유와 상품 불일치 | `EVIDENCE_SUFFICIENT` | 0.4907 | 9 | 6 | 0 | 0 | 80.0 |
| [09](#rag-20-run-03-q09) | 한 주문의 일부 미수령 | `EVIDENCE_SUFFICIENT` | 0.3957 | 3 | 3 | 9 | 0 | 30.0 |
| [10](#rag-20-run-03-q10) | 반송 완료 후 카드 환불 지연 | `EVIDENCE_SUFFICIENT` | 0.4657 | 8 | 0 | 6 | 1 | 53.3 |
| [11](#rag-20-run-03-q11) | 반품 주소 불일치 | `EVIDENCE_SUFFICIENT` | 0.4412 | 9 | 1 | 5 | 0 | 63.3 |
| [12](#rag-20-run-03-q12) | 고가 상품과 대리 수령 | `EVIDENCE_SUFFICIENT` | 0.4653 | 6 | 5 | 4 | 0 | 56.7 |
| [13](#rag-20-run-03-q13) | 운동화 상태와 위조 의심 | `EVIDENCE_INSUFFICIENT` | 0.4879 | 2 | 12 | 1 | 0 | 53.3 |
| [14](#rag-20-run-03-q14) | 비회원 불량품 반품 | `EVIDENCE_SUFFICIENT` | 0.4644 | 11 | 0 | 4 | 0 | 73.3 |
| [15](#rag-20-run-03-q15) | 포장 개봉과 환불액 차감 | `EVIDENCE_SUFFICIENT` | 0.5061 | 8 | 2 | 5 | 0 | 60.0 |
| [16](#rag-20-run-03-q16) | 국제 배송 파손과 수입 비용 | `EVIDENCE_SUFFICIENT` | 0.4802 | 1 | 3 | 11 | 0 | 16.7 |
| [17](#rag-20-run-03-q17) | 예약 주문 배송 지연 | `EVIDENCE_SUFFICIENT` | 0.3913 | 5 | 1 | 8 | 1 | 36.7 |
| [18](#rag-20-run-03-q18) | 발송 후 배송 주소 오류 | `EVIDENCE_SUFFICIENT` | 0.3592 | 2 | 1 | 12 | 0 | 16.7 |
| [19](#rag-20-run-03-q19) | 고가 상품 반송과 서명 | `EVIDENCE_SUFFICIENT` | 0.5289 | 6 | 1 | 8 | 0 | 43.3 |
| [20](#rag-20-run-03-q20) | 플랫폼 밖 부분 환불 | `EVIDENCE_SUFFICIENT` | 0.4682 | 9 | 0 | 6 | 0 | 60.0 |
| **합계** | **20개 질문** | 충분 19 · 부족 1 · 정책 미검색 0 | — | **131** | **41** | **125** | **3** | **50.5** |

### 청크 본문 검토

450/200과 450/350은 문서별 청크 본문 문자열을 순서대로 대조했다. 25개 문서의 149개 본문이 모두 같았으며, 따라서 조건·예외의 묶임과 문장 절단 위치도 같았다. 아래는 실제 반환 본문에서 확인한 내용이다.

| 질문 | 핵심 근거 순위 | 본문에서 확인한 문맥 | 내용상 관찰 |
|---|---:|---|---|
| 04 · 최초 배송비 | 14 | 판매자 반품 정책 내 변심 반품 허용, 최초 배송비 제외 가능, 반송비 부담 기준 | 필요한 조건과 조항이 함께 있다. 같은 청크 앞부분에는 최종 판매·품목별 정의도 섞여 있다. |
| 07 · 카드사 이의 제기 | 15 | 동일 거래 중복 청구 제한, 카드사 청구 시 eBay 사건 종료 가능, 전액·일부 청구 모두 적용 | 핵심 조건과 결과가 함께 있다. 환율·판매자 자금 보류·상환 내용도 같은 청크에 포함된다. |
| 08 · 배대지 | 15 | eBay 공식 프로그램으로 재배송한 경우와 제3자 배송대행을 쓴 경우의 보호 범위 | 적용·제외 문구가 함께 보존된다. 카드·자동차 부품·위조품·이벤트 티켓 조항까지 함께 들어 있어 주제가 넓다. |
| 13 · 위조 의심 | 12 | 인증 담당자가 위조를 의심하면 유통에서 제거할 수 있다는 인증 서비스 규칙 | 판매자에게 요청할 일반 반품 절차보다 인증 서비스 규칙 중심이다. 주문의 인증 대상 여부를 확인해야 한다. |
| 17 · 예약판매 | — | 상위 결과는 미수령 신고와 일반 주문 취소 절차 | 예약판매 자체의 발송 조건은 검색 결과에 나타나지 않았다. |

이번 파라미터 변경으로 본문의 문맥 묶임이 개선되지는 않았다. 위 관찰은 청크 내용과 경계를 검토한 기록이며 질문별 Search status와 적용 판단은 실제 모델 출력값을 유지했다.

### 질문별 Search note와 검색 결과

<a id="rag-20-run-03-q01"></a>

#### 01 · 추적번호 없는 배송 지연

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 4 · 불확실 0 · 적용 불가 11 · 미분류 0 · **적용 점수 26.7 / 100**

**Search note**

> 예정일이 지난 뒤 구매 내역에서 미수령 신고를 할 수 있고, 신고 기한은 예정 배송일로부터 30일 이내입니다. 판매자는 신고 후 3영업일 이내에 추적 정보·배송 상황 또는 환불을 제공해야 하며, 해결되지 않으면 요청 상세에서 eBay에 도움을 요청할 수 있습니다(추적 정보가 없을 때 eBay가 개입할 수 있는 시점은 신고 후 3영업일부터 21영업일까지입니다).

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | 0.5890 | `APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 2 | eBay Money Back Guarantee policy | 0.4876 | `APPLICABLE` | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` |
| 3 | eBay Money Back Guarantee policy | 0.4820 | `APPLICABLE` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 4 | Get help if you bought as a guest | 0.4582 | `NOT_APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 5 | eBay Money Back Guarantee policy | 0.4581 | `NOT_APPLICABLE` | `347c4c37-e21c-4f86-9575-306c1651a5df` |
| 6 | Get help if you bought as a guest | 0.4500 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 7 | eBay Money Back Guarantee policy | 0.4214 | `NOT_APPLICABLE` | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` |
| 8 | eBay Money Back Guarantee policy | 0.4157 | `NOT_APPLICABLE` | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` |
| 9 | Check the status of your request | 0.4139 | `NOT_APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 10 | eBay Money Back Guarantee policy | 0.4074 | `NOT_APPLICABLE` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |
| 11 | Return an item for a refund | 0.4022 | `NOT_APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 12 | eBay Money Back Guarantee policy | 0.3975 | `APPLICABLE` | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` |
| 13 | Get help if you bought as a guest | 0.3934 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 14 | Selling practices policy | 0.3931 | `NOT_APPLICABLE` | `29dc7c6c-e4f2-4783-b537-a7c16da81994` |
| 15 | eBay Money Back Guarantee policy | 0.3914 | `NOT_APPLICABLE` | `3b462389-7437-407c-87bd-ea2d91d04130` |

</details>

<a id="rag-20-run-03-q02"></a>

#### 02 · 노트북 결함과 부분 환불

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 10 · 불확실 0 · 적용 불가 5 · 미분류 0 · **적용 점수 66.7 / 100**

**Search note**

> 설명과 다른 고장 상품은 판매자의 반품 불가 정책과 무관하게 반품을 요청할 수 있으며, 반품이 승인·진행되면 상품값과 최초 배송비 환불 및 반송비 판매자 부담이 정책에 뒷받침됩니다. 다만 판매자는 전액 환불 외에 부분 환불이나 교환을 제안할 수도 있어 결과가 자동 보장되지는 않으며, 요청 기한과 노트북의 반환 상태는 확인해야 합니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Return an item for a refund | 0.4004 | `APPLICABLE` | `927f2845-49b1-40f5-b351-48dee6c6ce2a` |
| 2 | Return an item for a refund | 0.3898 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 3 | eBay Money Back Guarantee policy | 0.3843 | `APPLICABLE` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |
| 4 | eBay Money Back Guarantee policy | 0.3840 | `APPLICABLE` | `77c77430-aceb-4f79-b632-ef78c863faf0` |
| 5 | eBay Money Back Guarantee policy | 0.3820 | `APPLICABLE` | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` |
| 6 | Condition of returned items policy | 0.3799 | `APPLICABLE` | `5969c401-95f9-4001-99e5-4c8592208453` |
| 7 | eBay Refurbished | 0.3747 | `NOT_APPLICABLE` | `14425763-a00d-4017-b045-aa88eb4d0695` |
| 8 | Get help if you bought as a guest | 0.3729 | `APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 9 | Get help if you bought as a guest | 0.3729 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 10 | How to handle a return request as a seller | 0.3723 | `NOT_APPLICABLE` | `1743ff1f-d3d4-4bdd-8248-211875998334` |
| 11 | eBay Refurbished | 0.3717 | `NOT_APPLICABLE` | `83be0678-d0f2-40f2-aecf-6d4bc8d49f52` |
| 12 | How to handle a return request as a seller | 0.3699 | `APPLICABLE` | `0655d062-13e1-45e5-af4b-9a5e802f811c` |
| 13 | eBay International Shipping - Buyer Terms & Conditions | 0.3678 | `NOT_APPLICABLE` | `bf37f2b1-9659-40d3-844a-11545aaba365` |
| 14 | Return an item for a refund | 0.3652 | `APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 15 | How to handle a return request as a seller | 0.3637 | `APPLICABLE` | `c4954f02-57d2-46c7-8e54-0ed22200153e` |

</details>

<a id="rag-20-run-03-q03"></a>

#### 03 · 발송 전 주문 취소

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 3 · 불확실 0 · 적용 불가 12 · 미분류 0 · **적용 점수 20.0 / 100**

**Search note**

> 구매자가 마음을 바꾼 경우에도 취소를 요청할 수 있지만, 판매자는 수락하거나 거절할 수 있어 더 싼 판매자를 찾았다는 이유만으로 수락을 강제할 수는 없습니다. 판매자가 발송 표시를 하기 전이고 주문 후 3일 이내에 요청하는 절차가 확인되며, 결제 완료 후 3일 안에 답이 없으면 요청은 종료되고 주문은 취소되지 않습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Order cancellation policy | 0.5037 | `APPLICABLE` | `4f1d8091-f413-47f9-92b5-8fcf59780b03` |
| 2 | Order cancellation policy | 0.4983 | `APPLICABLE` | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` |
| 3 | Order cancellation policy | 0.4682 | `APPLICABLE` | `6aa36185-3f49-40c5-bbee-fb0855dfcf39` |
| 4 | Selling practices policy | 0.4574 | `NOT_APPLICABLE` | `29dc7c6c-e4f2-4783-b537-a7c16da81994` |
| 5 | Get help if you bought as a guest | 0.4169 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 6 | Get help with an item that hasn't arrived | 0.3959 | `NOT_APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 7 | Return an item for a refund | 0.3942 | `NOT_APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 8 | eBay Money Back Guarantee policy | 0.3908 | `NOT_APPLICABLE` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 9 | Get help if you bought as a guest | 0.3897 | `NOT_APPLICABLE` | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` |
| 10 | How to handle a return request as a seller | 0.3880 | `NOT_APPLICABLE` | `350c99f0-67bd-4939-abab-98d6ddf129f7` |
| 11 | Get help if you bought as a guest | 0.3871 | `NOT_APPLICABLE` | `0f5fa726-6d78-401f-bca3-39487acb99e2` |
| 12 | Check the status of your request | 0.3855 | `NOT_APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 13 | Get help if you bought as a guest | 0.3835 | `NOT_APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 14 | Get help if you bought as a guest | 0.3822 | `NOT_APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 15 | Get help if you bought as a guest | 0.3746 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |

</details>

<a id="rag-20-run-03-q04"></a>

#### 04 · 단순 변심과 최초 배송비

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 10 · 불확실 0 · 적용 불가 5 · 미분류 0 · **적용 점수 66.7 / 100**

**Search note**

> 주문에 반품 가능으로 표시되어 있고 판매자 반품 기간 안에 신청한다면 단순 변심 반품을 요청할 수 있으며, 반품 배송비 부담은 목록에 정한 조건을 따릅니다. 최초 배송비는 판매자가 환불에서 제외할 수 있으므로, 해당 주문의 정책과 반송 라벨 비용 부담 주체를 확인해야 합니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Return an item for a refund | 0.3598 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 2 | Return an item for a refund | 0.3411 | `NOT_APPLICABLE` | `927f2845-49b1-40f5-b351-48dee6c6ce2a` |
| 3 | Get help if you bought as a guest | 0.3307 | `APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 4 | eBay Money Back Guarantee policy | 0.3225 | `NOT_APPLICABLE` | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` |
| 5 | Return an item for a refund | 0.3177 | `APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 6 | Return an item for a refund | 0.3175 | `APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 7 | Get help if you bought as a guest | 0.3137 | `APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 8 | eBay Money Back Guarantee policy | 0.3101 | `APPLICABLE` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |
| 9 | Check the status of your request | 0.3092 | `NOT_APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 10 | Get help if you bought as a guest | 0.3015 | `APPLICABLE` | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` |
| 11 | eBay Money Back Guarantee policy | 0.3014 | `APPLICABLE` | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` |
| 12 | eBay Money Back Guarantee policy | 0.2976 | `NOT_APPLICABLE` | `3b462389-7437-407c-87bd-ea2d91d04130` |
| 13 | eBay Money Back Guarantee policy | 0.2967 | `NOT_APPLICABLE` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |
| 14 | eBay Money Back Guarantee policy | 0.2961 | `APPLICABLE` | `40df69ec-9ecf-41e0-a32a-982d935ab64a` |
| 15 | eBay Money Back Guarantee policy | 0.2938 | `APPLICABLE` | `77c77430-aceb-4f79-b632-ef78c863faf0` |

</details>

<a id="rag-20-run-03-q05"></a>

#### 05 · 파손 반품과 국제 반송비

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 8 · 불확실 6 · 적용 불가 1 · 미분류 0 · **적용 점수 73.3 / 100**

**Search note**

> 갈라진 물탱크처럼 상품이 파손되어 도착한 경우 판매자가 반송비를 부담하며, 반품을 수락하면 판매자는 라벨 또는 구매자가 수락할 다른 반송 방법을 제공해야 합니다. 국제 반송비를 먼저 내라는 요구가 이 주문에 적용되는지는 배송 프로그램과 반품 상세에 표시된 지침을 확인해야 하며, 판매자 부담인데도 비용을 부담시키면 eBay에 개입을 요청할 수 있습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Return shipping for buyers | 0.4410 | `APPLICABLE` | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` |
| 2 | Return shipping for buyers | 0.4385 | `APPLICABLE` | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` |
| 3 | Return shipping for buyers | 0.4142 | `UNCERTAIN` | `b01c873d-8724-4642-adf2-4ad03f6ada39` |
| 4 | eBay International Shipping - Buyer Terms & Conditions | 0.4057 | `UNCERTAIN` | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` |
| 5 | eBay International Shipping Seller Terms & Conditions | 0.3969 | `NOT_APPLICABLE` | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` |
| 6 | How to handle a return request as a seller | 0.3954 | `APPLICABLE` | `c4954f02-57d2-46c7-8e54-0ed22200153e` |
| 7 | Return an item for a refund | 0.3940 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 8 | International purchases and shipping for buyers | 0.3905 | `UNCERTAIN` | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` |
| 9 | eBay International Shipping - Buyer Terms & Conditions | 0.3892 | `UNCERTAIN` | `bf37f2b1-9659-40d3-844a-11545aaba365` |
| 10 | Get help if you bought as a guest | 0.3878 | `APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 11 | eBay International Shipping \| Seller Center | 0.3868 | `UNCERTAIN` | `ba80853d-fe28-4579-81c0-530b40ab121c` |
| 12 | eBay Money Back Guarantee policy | 0.3858 | `APPLICABLE` | `77c77430-aceb-4f79-b632-ef78c863faf0` |
| 13 | How to handle a return request as a seller | 0.3838 | `APPLICABLE` | `0655d062-13e1-45e5-af4b-9a5e802f811c` |
| 14 | eBay International Shipping - Buyer Terms & Conditions | 0.3835 | `UNCERTAIN` | `5dd13ab2-02b1-4a36-8e2b-1ba1e0162b44` |
| 15 | eBay Money Back Guarantee policy | 0.3822 | `APPLICABLE` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |

</details>

<a id="rag-20-run-03-q06"></a>

#### 06 · 교체 약속과 반품 요청 종료

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 9 · 불확실 0 · 적용 불가 5 · 미분류 1 · **적용 점수 60.0 / 100**

**Search note**

> 다른 색상의 상품은 설명과 다른 상품에 해당할 수 있어 반품 요청을 유지하고 판매자에게 해결을 요구할 근거가 있으며, 판매자는 교체품을 제안할 수 있습니다. 하지만 요청을 닫으면 같은 거래의 요청을 다시 열거나 새로 제출할 수 없고, 교체품이 오지 않은 뒤 eBay에 도움을 요청할 수 있다고 보장하는 정책은 확인되지 않았습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | 0.4961 | `NOT_APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 2 | Get help if you bought as a guest | 0.4746 | `APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 3 | Check the status of your request | 0.4635 | `APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 4 | Get help if you bought as a guest | 0.4587 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 5 | Return shipping for buyers | 0.4446 | `APPLICABLE` | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` |
| 6 | eBay Money Back Guarantee policy | 0.4434 | `NOT_APPLICABLE` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 7 | Return an item for a refund | 0.4391 | `APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 8 | Return shipping for buyers | 0.4384 | — | `b01c873d-8724-4642-adf2-4ad03f6ada39` |
| 9 | Return an item for a refund | 0.4334 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 10 | How to handle a return request as a seller | 0.4316 | `APPLICABLE` | `0655d062-13e1-45e5-af4b-9a5e802f811c` |
| 11 | Get help if you bought as a guest | 0.4281 | `APPLICABLE` | `0f5fa726-6d78-401f-bca3-39487acb99e2` |
| 12 | How to handle a return request as a seller | 0.4253 | `APPLICABLE` | `350c99f0-67bd-4939-abab-98d6ddf129f7` |
| 13 | Get help if you bought as a guest | 0.4252 | `APPLICABLE` | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` |
| 14 | Get help if you bought as a guest | 0.4251 | `NOT_APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 15 | How to handle a return request as a seller | 0.4216 | `NOT_APPLICABLE` | `99dbe313-a5e6-473a-884d-9758d7490e93` |

</details>

<a id="rag-20-run-03-q07"></a>

#### 07 · 카드사 결제 이의 제기

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 8 · 불확실 0 · 적용 불가 7 · 미분류 0 · **적용 점수 53.3 / 100**

**Search note**

> 배송 예정일이 지난 미수령 거래는 판매자에게 추적 정보·배송 상황 또는 환불을 요청할 수 있고, 판매자는 요청 후 3영업일 안에 응답해야 합니다. 다만 같은 거래에 카드사 이의 제기를 접수하면 eBay가 미수령 요청을 종료할 수 있어 구매자 보호를 잃을 수 있으므로, eBay 요청과 카드사 절차를 동시에 진행해도 된다고 보장하는 근거는 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | 0.5307 | `APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 2 | eBay Money Back Guarantee policy | 0.4960 | `APPLICABLE` | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` |
| 3 | Check the status of your request | 0.4882 | `APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 4 | Get help if you bought as a guest | 0.4827 | `APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 5 | eBay Money Back Guarantee policy | 0.4773 | `NOT_APPLICABLE` | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` |
| 6 | Return an item for a refund | 0.4730 | `NOT_APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 7 | eBay Money Back Guarantee policy | 0.4651 | `NOT_APPLICABLE` | `3b462389-7437-407c-87bd-ea2d91d04130` |
| 8 | eBay Money Back Guarantee policy | 0.4565 | `NOT_APPLICABLE` | `347c4c37-e21c-4f86-9575-306c1651a5df` |
| 9 | Get help if you bought as a guest | 0.4563 | `APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 10 | eBay Money Back Guarantee policy | 0.4553 | `APPLICABLE` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 11 | eBay Money Back Guarantee policy | 0.4491 | `APPLICABLE` | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` |
| 12 | Order cancellation policy | 0.4477 | `NOT_APPLICABLE` | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` |
| 13 | eBay Money Back Guarantee policy | 0.4470 | `NOT_APPLICABLE` | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` |
| 14 | How to handle a return request as a seller | 0.4416 | `NOT_APPLICABLE` | `350c99f0-67bd-4939-abab-98d6ddf129f7` |
| 15 | eBay Money Back Guarantee policy | 0.4410 | `APPLICABLE` | `6e065bd8-ddcb-4159-88ec-51f7904be5f9` |

</details>

<a id="rag-20-run-03-q08"></a>

#### 08 · 배대지 경유와 상품 불일치

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 9 · 불확실 6 · 적용 불가 0 · 미분류 0 · **적용 점수 80.0 / 100**

**Search note**

> 다른 색상의 재킷은 설명 불일치 반품 사유가 될 수 있으며, 정책상 판매자는 반품 요청에 3영업일 내 응답하고 반품 배송비를 부담해야 합니다. 다만 제3자 배대지를 이용한 배송은 보장에서 제외될 수 있어 eBay 보호 여부와 신청 기한은 확인이 필요하며, 환불은 자동 보장되지 않습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Return an item for a refund | 0.4907 | `APPLICABLE` | `927f2845-49b1-40f5-b351-48dee6c6ce2a` |
| 2 | Return an item for a refund | 0.4667 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 3 | eBay International Shipping - Buyer Terms & Conditions | 0.4539 | `UNCERTAIN` | `bf37f2b1-9659-40d3-844a-11545aaba365` |
| 4 | Return an item for a refund | 0.4458 | `APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 5 | eBay Money Back Guarantee policy | 0.4455 | `UNCERTAIN` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |
| 6 | Get help if you bought as a guest | 0.4417 | `APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 7 | eBay International Shipping Seller Terms & Conditions | 0.4400 | `UNCERTAIN` | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` |
| 8 | eBay Money Back Guarantee policy | 0.4378 | `APPLICABLE` | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` |
| 9 | Get help if you bought as a guest | 0.4367 | `APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 10 | International purchases and shipping for buyers | 0.4364 | `UNCERTAIN` | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` |
| 11 | Return an item for a refund | 0.4347 | `APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 12 | Return shipping for buyers | 0.4325 | `APPLICABLE` | `b01c873d-8724-4642-adf2-4ad03f6ada39` |
| 13 | eBay Money Back Guarantee policy | 0.4312 | `UNCERTAIN` | `3b462389-7437-407c-87bd-ea2d91d04130` |
| 14 | eBay International Shipping - Buyer Terms & Conditions | 0.4296 | `UNCERTAIN` | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` |
| 15 | eBay Money Back Guarantee policy | 0.4293 | `APPLICABLE` | `cdca8d5f-812a-4fed-a6a6-380989fdf86a` |

</details>

<a id="rag-20-run-03-q09"></a>

#### 09 · 한 주문의 일부 미수령

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 3 · 불확실 3 · 적용 불가 9 · 미분류 0 · **적용 점수 30.0 / 100**

**Search note**

> 서로 다른 상품 페이지에 각각 등록된 카메라와 메모리카드라면, 함께 결제했어도 메모리카드 미수령으로 별도 요청을 여는 절차를 뒷받침합니다. 한 페이지의 세트·번들이라면 일부만 받았을 때 세트 전체의 반품 요청이 필요하며, 판매자는 배송 업데이트·대체품·환불을 제안할 수 있지만 메모리카드 단독 제공이나 특정 금액 환불이 보장되지는 않습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Return an item for a refund | 0.3957 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 2 | Get help if you bought as a guest | 0.3601 | `APPLICABLE` | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` |
| 3 | Return an item for a refund | 0.3588 | `UNCERTAIN` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 4 | Get help if you bought as a guest | 0.3517 | `UNCERTAIN` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 5 | Get help if you bought as a guest | 0.3461 | `APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 6 | Get help with an item that hasn't arrived | 0.3440 | `UNCERTAIN` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 7 | eBay International Shipping \| Seller Center | 0.3366 | `NOT_APPLICABLE` | `ba80853d-fe28-4579-81c0-530b40ab121c` |
| 8 | Condition of returned items policy | 0.3349 | `NOT_APPLICABLE` | `5969c401-95f9-4001-99e5-4c8592208453` |
| 9 | Return an item for a refund | 0.3322 | `NOT_APPLICABLE` | `927f2845-49b1-40f5-b351-48dee6c6ce2a` |
| 10 | eBay International Shipping - Buyer Terms & Conditions | 0.3305 | `NOT_APPLICABLE` | `bf37f2b1-9659-40d3-844a-11545aaba365` |
| 11 | International purchases and shipping for buyers | 0.3304 | `NOT_APPLICABLE` | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` |
| 12 | eBay Money Back Guarantee policy | 0.3297 | `NOT_APPLICABLE` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |
| 13 | eBay Money Back Guarantee policy | 0.3271 | `NOT_APPLICABLE` | `0b3a3fdf-2b2a-485b-b84f-eace87156c0e` |
| 14 | Get help if you bought as a guest | 0.3254 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 15 | eBay Money Back Guarantee policy | 0.3246 | `NOT_APPLICABLE` | `cdca8d5f-812a-4fed-a6a6-380989fdf86a` |

</details>

<a id="rag-20-run-03-q10"></a>

#### 10 · 반송 완료 후 카드 환불 지연

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 8 · 불확실 0 · 적용 불가 6 · 미분류 1 · **적용 점수 53.3 / 100**

**Search note**

> 반품 상세에서 환불 기한과 환불 상태를 확인하고, 기한이 지났는데도 환불되지 않았다면 eBay에 개입을 요청할 수 있다는 근거가 있습니다. 판매자는 반품품을 받은 뒤 일반적으로 2영업일 이내 환불해야 하지만, 상세에 더 늦은 기한이 표시될 수 있고 환불 발행 후 카드 반영은 보통 3~5영업일 걸리므로, 현재 카드 명세만으로 미발행 여부를 단정할 수는 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Check the status of your request | 0.4657 | `APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 2 | eBay Money Back Guarantee policy | 0.4539 | `APPLICABLE` | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` |
| 3 | eBay Money Back Guarantee policy | 0.4435 | `APPLICABLE` | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` |
| 4 | Get help if you bought as a guest | 0.4412 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 5 | eBay Money Back Guarantee policy | 0.4401 | `APPLICABLE` | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` |
| 6 | Return an item for a refund | 0.4398 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 7 | Get help with an item that hasn't arrived | 0.4381 | `NOT_APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 8 | eBay Money Back Guarantee policy | 0.4330 | `NOT_APPLICABLE` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |
| 9 | eBay Money Back Guarantee policy | 0.4252 | `NOT_APPLICABLE` | `347c4c37-e21c-4f86-9575-306c1651a5df` |
| 10 | eBay Money Back Guarantee policy | 0.4252 | `APPLICABLE` | `3b462389-7437-407c-87bd-ea2d91d04130` |
| 11 | Return an item for a refund | 0.4244 | `APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 12 | eBay Money Back Guarantee policy | 0.4240 | — | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 13 | How to handle a return request as a seller | 0.4161 | `APPLICABLE` | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` |
| 14 | How to handle a return request as a seller | 0.4141 | `NOT_APPLICABLE` | `350c99f0-67bd-4939-abab-98d6ddf129f7` |
| 15 | eBay Money Back Guarantee policy | 0.4119 | `NOT_APPLICABLE` | `40df69ec-9ecf-41e0-a32a-982d935ab64a` |

</details>

<a id="rag-20-run-03-q11"></a>

#### 11 · 반품 주소 불일치

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 9 · 불확실 1 · 적용 불가 5 · 미분류 0 · **적용 점수 63.3 / 100**

**Search note**

> 검색된 정책에 따르면 판매자가 반품 배송비를 부담하는 경우 제공된 라벨을 사용하거나 판매자와 합의한 다른 반품 방법을 써야 하며, 추적 가능한 배송과 반품 주소가 확인되는 배송 증빙이 중요합니다. 하지만 캘리포니아 라벨과 네바다 주소 중 어느 것이 이 반품의 승인된 주소인지는 확인되지 않으므로, 임의로 네바다로 보내지 말고 eBay 반품 상세에서 새 라벨을 요청하거나 eBay에 도움을 요청하세요.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Return shipping for buyers | 0.4412 | `APPLICABLE` | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` |
| 2 | Return shipping for buyers | 0.4223 | `APPLICABLE` | `b01c873d-8724-4642-adf2-4ad03f6ada39` |
| 3 | Return shipping for buyers | 0.4204 | `APPLICABLE` | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` |
| 4 | Check the status of your request | 0.4171 | `APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 5 | How to handle a return request as a seller | 0.4075 | `UNCERTAIN` | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` |
| 6 | eBay Money Back Guarantee policy | 0.4018 | `NOT_APPLICABLE` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |
| 7 | How to handle a return request as a seller | 0.3966 | `APPLICABLE` | `c4954f02-57d2-46c7-8e54-0ed22200153e` |
| 8 | How to handle a return request as a seller | 0.3936 | `APPLICABLE` | `0655d062-13e1-45e5-af4b-9a5e802f811c` |
| 9 | Return an item for a refund | 0.3927 | `APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 10 | Get help with an item that hasn't arrived | 0.3866 | `NOT_APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 11 | How to handle a return request as a seller | 0.3823 | `NOT_APPLICABLE` | `350c99f0-67bd-4939-abab-98d6ddf129f7` |
| 12 | How to handle a return request as a seller | 0.3796 | `NOT_APPLICABLE` | `00ab85d7-00dc-4e04-bc46-416a6a759751` |
| 13 | eBay Money Back Guarantee policy | 0.3793 | `APPLICABLE` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |
| 14 | How to handle a return request as a seller | 0.3775 | `NOT_APPLICABLE` | `d58dd7b1-f4d6-47ba-b7ae-81501242f723` |
| 15 | eBay Money Back Guarantee policy | 0.3759 | `APPLICABLE` | `77c77430-aceb-4f79-b632-ef78c863faf0` |

</details>

<a id="rag-20-run-03-q12"></a>

#### 12 · 고가 상품과 대리 수령

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 6 · 불확실 5 · 적용 불가 4 · 미분류 0 · **적용 점수 56.7 / 100**

**Search note**

> 주문 상세 주소와 배송 증거의 우편번호(없으면 도시·카운티) 일치 여부 및 750달러 이상 주문의 서명 확인을 근거로 판매자에게 기록을 요청할 수 있습니다. 다만 친구가 실제로 대리 수령한 경우 제3자 대리 수령은 미수령 보장 제외 사유로 제시되어 있어 판단이 달라질 수 있으며, 배송 완료 표시만으로 환불 결과를 단정할 수는 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | 0.4653 | `APPLICABLE` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 2 | Get help with an item that hasn't arrived | 0.4564 | `APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 3 | eBay Money Back Guarantee policy | 0.4489 | `APPLICABLE` | `347c4c37-e21c-4f86-9575-306c1651a5df` |
| 4 | eBay Money Back Guarantee policy | 0.4375 | `NOT_APPLICABLE` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |
| 5 | International purchases and shipping for buyers | 0.4227 | `NOT_APPLICABLE` | `02ed01df-7637-4e68-8458-f8e01b540ce4` |
| 6 | Signature confirmation policy | 0.4169 | `APPLICABLE` | `604b7fbb-2462-4a94-a0a3-59fb5290d846` |
| 7 | Get help if you bought as a guest | 0.4076 | `NOT_APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 8 | eBay International Shipping Seller Terms & Conditions | 0.4051 | `UNCERTAIN` | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` |
| 9 | International purchases and shipping for buyers | 0.4046 | `UNCERTAIN` | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` |
| 10 | Signature confirmation policy | 0.4033 | `APPLICABLE` | `e2b3df5e-28ee-48d2-b4f7-f78175e269b9` |
| 11 | eBay International Shipping - Buyer Terms & Conditions | 0.4031 | `UNCERTAIN` | `88ee7312-806c-49e9-875f-f20a8881ce44` |
| 12 | International purchases and shipping for buyers | 0.3982 | `UNCERTAIN` | `76066a7d-2738-449c-9574-7d68d06faa54` |
| 13 | eBay Money Back Guarantee policy | 0.3925 | `APPLICABLE` | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` |
| 14 | Get help if you bought as a guest | 0.3851 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 15 | eBay International Shipping - Buyer Terms & Conditions | 0.3822 | `UNCERTAIN` | `5dd13ab2-02b1-4a36-8e2b-1ba1e0162b44` |

</details>

<a id="rag-20-run-03-q13"></a>

#### 13 · 운동화 상태와 위조 의심

**Search status** · `EVIDENCE_INSUFFICIENT`

**청크 적용 판단** · 적용 2 · 불확실 12 · 적용 불가 1 · 미분류 0 · **적용 점수 53.3 / 100**

**Search note**

> 새 상품 설명과 달리 밑창에 사용 흔적이 있거나 제품이 목록과 크게 다르면 문제 제기의 근거가 될 수 있고, 위조 여부는 정품 인증 대상 상품이면 eBay 인증 절차로 확인됩니다. 다만 주문에 인증 배지가 있었는지, 상품이 실제로 인증을 거쳤는지와 판매자의 반품 조건·기간은 확인되지 않아 어느 절차가 적용되는지 또는 환불이 보장되는지는 단정할 수 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay Authenticity Guarantee for Sneakers | 0.4879 | `APPLICABLE` | `a8720eaf-1dbd-4eaa-929a-d067e5780bf1` |
| 2 | eBay Authenticity Guarantee for Sneakers | 0.4830 | `APPLICABLE` | `668ef1c6-a0e2-4a5d-bf03-c24cc6e00f89` |
| 3 | eBay Authenticity Guarantee for Sneakers | 0.4613 | `UNCERTAIN` | `bd335ae7-a208-4fb1-a080-26f3ce45688f` |
| 4 | eBay Authenticity Guarantee for Sneakers | 0.4364 | `UNCERTAIN` | `909603f2-3b0e-4a92-b440-b3b76df9df36` |
| 5 | eBay Authenticity Guarantee for Sneakers | 0.4306 | `UNCERTAIN` | `b52463d6-3981-4dda-bbc0-5853e70bb899` |
| 6 | eBay Authenticity Guarantee for Sneakers | 0.4282 | `UNCERTAIN` | `28a46454-f148-412a-b6be-827a11666c92` |
| 7 | eBay Authenticity Guarantee for Sneakers | 0.4181 | `UNCERTAIN` | `4493c75d-f79a-4cd5-b15e-e7c847137741` |
| 8 | eBay Authenticity Guarantee for Sneakers | 0.4161 | `UNCERTAIN` | `d7ea9541-18ed-4a3b-860e-44597608060c` |
| 9 | eBay Authenticity Guarantee for Sneakers | 0.4151 | `UNCERTAIN` | `4c3b3f5c-f79d-4e33-8ac0-c3d09a75123f` |
| 10 | eBay Authenticity Guarantee for Sneakers | 0.4137 | `UNCERTAIN` | `310c3728-23b8-46d5-88f9-cfccc4aa62bc` |
| 11 | Buying with Authenticity Guarantee | 0.4101 | `UNCERTAIN` | `79a064e9-afda-4eb4-8f2b-171482196f23` |
| 12 | eBay Authenticity Guarantee for Sneakers | 0.4004 | `UNCERTAIN` | `28c18aa9-8df1-4134-bfa2-934df227a276` |
| 13 | eBay Authenticity Guarantee for Sneakers | 0.4001 | `UNCERTAIN` | `a2c75035-7ecb-4809-a425-bb9a5655986c` |
| 14 | eBay Authenticity Guarantee for Sneakers | 0.3951 | `UNCERTAIN` | `b3af4677-12f3-44be-8750-6cb51b7529b9` |
| 15 | eBay Authenticity Guarantee for Sneakers | 0.3786 | `NOT_APPLICABLE` | `b7fadb64-8245-4379-8046-a7ababfa5da8` |

</details>

<a id="rag-20-run-03-q14"></a>

#### 14 · 비회원 불량품 반품

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 11 · 불확실 0 · 적용 불가 4 · 미분류 0 · **적용 점수 73.3 / 100**

**Search note**

> 게스트 구매도 주문 확인 이메일의 ‘View order details’에서 ‘Return item’을 선택해 반품을 시작하고, 사유를 고른 뒤 메시지와 사진을 최대 10장 첨부해 ‘Confirm return’을 누를 수 있습니다. 전원이 켜지지 않는 불량품은 판매자 반품 불가 정책과 무관하게 반품 대상이 될 수 있지만, 환불·교환 결과는 보장되지 않으며 판매자는 3영업일 안에 응답해야 합니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help if you bought as a guest | 0.4644 | `APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 2 | Get help if you bought as a guest | 0.4637 | `APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 3 | Return an item for a refund | 0.4255 | `APPLICABLE` | `927f2845-49b1-40f5-b351-48dee6c6ce2a` |
| 4 | Return an item for a refund | 0.4192 | `APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 5 | Get help with an item that hasn't arrived | 0.4185 | `NOT_APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 6 | Get help if you bought as a guest | 0.4144 | `APPLICABLE` | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` |
| 7 | Get help if you bought as a guest | 0.4125 | `APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 8 | Return an item for a refund | 0.4091 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 9 | Get help if you bought as a guest | 0.4005 | `APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 10 | Return an item for a refund | 0.3910 | `APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 11 | eBay Refurbished | 0.3891 | `NOT_APPLICABLE` | `83be0678-d0f2-40f2-aecf-6d4bc8d49f52` |
| 12 | eBay Money Back Guarantee policy | 0.3888 | `NOT_APPLICABLE` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 13 | Get help if you bought as a guest | 0.3883 | `APPLICABLE` | `0f5fa726-6d78-401f-bca3-39487acb99e2` |
| 14 | eBay Money Back Guarantee policy | 0.3824 | `APPLICABLE` | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` |
| 15 | eBay Money Back Guarantee policy | 0.3798 | `NOT_APPLICABLE` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |

</details>

<a id="rag-20-run-03-q15"></a>

#### 15 · 포장 개봉과 환불액 차감

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 8 · 불확실 2 · 적용 불가 5 · 미분류 0 · **적용 점수 60.0 / 100**

**Search note**

> 씰 개봉만으로 자동 전액 환불이 보장되지는 않지만, 정책은 상태 확인에 필요하지 않은 취급으로 사용·가치 하락이 발생한 경우에 한해 차감을 허용하며 개봉 후에도 원래 상태인 상품은 일반 지침상 5~10% 구간입니다. 판매자에게 차감 근거와 실제 가치 하락을 설명해 달라고 요청할 수 있고, 3영업일 동안 해결되지 않으면 eBay에 개입을 요청할 수 있으나 환불액 조정 결과는 eBay의 심사에 달려 있습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Condition of returned items policy | 0.5061 | `APPLICABLE` | `5969c401-95f9-4001-99e5-4c8592208453` |
| 2 | How to handle a return request as a seller | 0.5060 | `UNCERTAIN` | `1743ff1f-d3d4-4bdd-8248-211875998334` |
| 3 | Return an item for a refund | 0.4802 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 4 | Return an item for a refund | 0.4734 | `APPLICABLE` | `927f2845-49b1-40f5-b351-48dee6c6ce2a` |
| 5 | How to handle a return request as a seller | 0.4664 | `UNCERTAIN` | `6109adcf-ec42-4a95-818c-a2ef540a612d` |
| 6 | Condition of returned items policy | 0.4637 | `APPLICABLE` | `87e1df21-066e-49e9-863e-7b3455872aed` |
| 7 | Return an item for a refund | 0.4509 | `APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 8 | Return an item for a refund | 0.4505 | `APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 9 | eBay Money Back Guarantee policy | 0.4469 | `NOT_APPLICABLE` | `008dfe95-0e97-43ae-b30b-304d611fcf17` |
| 10 | eBay Money Back Guarantee policy | 0.4372 | `NOT_APPLICABLE` | `40df69ec-9ecf-41e0-a32a-982d935ab64a` |
| 11 | eBay Money Back Guarantee policy | 0.4360 | `APPLICABLE` | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` |
| 12 | eBay Money Back Guarantee policy | 0.4358 | `NOT_APPLICABLE` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |
| 13 | Get help if you bought as a guest | 0.4341 | `APPLICABLE` | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` |
| 14 | Condition of returned items policy | 0.4332 | `NOT_APPLICABLE` | `0777d913-f3c3-482a-b418-ec20bf723bc5` |
| 15 | eBay Refurbished | 0.4322 | `NOT_APPLICABLE` | `83be0678-d0f2-40f2-aecf-6d4bc8d49f52` |

</details>

<a id="rag-20-run-03-q16"></a>

#### 16 · 국제 배송 파손과 수입 비용

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 1 · 불확실 3 · 적용 불가 11 · 미분류 0 · **적용 점수 16.7 / 100**

**Search note**

> 파손이 배송 중 발생했고 eBay Money Back Guarantee 청구가 승인되면 eBay에 낸 세금·관세·수입 비용도 환급 대상이라고 약관에 명시되어 있어, 영수증의 26달러에 대해 근거를 제시할 수 있습니다. 다만 배송 때 운송사에 낸 15달러는 반품 시 원칙적으로 환급되지 않는 DAP 조건일 수 있고, 현지 법률상 eBay가 징수·송금해야 했던 세금인지와 실제 배송 조건이 확인되지 않아 환급을 단정할 수 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay International Shipping - Buyer Terms & Conditions | 0.4802 | `APPLICABLE` | `bf37f2b1-9659-40d3-844a-11545aaba365` |
| 2 | International purchases and shipping for buyers | 0.4731 | `NOT_APPLICABLE` | `76066a7d-2738-449c-9574-7d68d06faa54` |
| 3 | eBay International Shipping - Buyer Terms & Conditions | 0.4717 | `UNCERTAIN` | `936ad65f-d348-453a-9ada-fd5061587211` |
| 4 | International purchases and shipping for buyers | 0.4657 | `NOT_APPLICABLE` | `02ed01df-7637-4e68-8458-f8e01b540ce4` |
| 5 | eBay International Shipping - Buyer Terms & Conditions | 0.4642 | `UNCERTAIN` | `5dd13ab2-02b1-4a36-8e2b-1ba1e0162b44` |
| 6 | International purchases and shipping for buyers | 0.4631 | `UNCERTAIN` | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` |
| 7 | eBay International Shipping Seller Terms & Conditions | 0.4577 | `NOT_APPLICABLE` | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` |
| 8 | International purchases and shipping for buyers | 0.4491 | `NOT_APPLICABLE` | `9da141f2-c82b-4943-9404-e162c739dda3` |
| 9 | eBay International Shipping Seller Terms & Conditions | 0.4479 | `NOT_APPLICABLE` | `aea220ac-fba6-4e07-b5e4-8d76311a2a21` |
| 10 | International purchases and shipping for buyers | 0.4441 | `NOT_APPLICABLE` | `45c4e413-c093-479c-b1a0-e97c51c95848` |
| 11 | eBay International Shipping - Buyer Terms & Conditions | 0.4430 | `NOT_APPLICABLE` | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` |
| 12 | eBay International Shipping \| Seller Center | 0.4385 | `NOT_APPLICABLE` | `ba80853d-fe28-4579-81c0-530b40ab121c` |
| 13 | Return shipping for buyers | 0.4262 | `NOT_APPLICABLE` | `b01c873d-8724-4642-adf2-4ad03f6ada39` |
| 14 | Return shipping for buyers | 0.4249 | `NOT_APPLICABLE` | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` |
| 15 | eBay International Shipping - Buyer Terms & Conditions | 0.4197 | `NOT_APPLICABLE` | `89d3cde5-4330-49cf-872f-576d13c53acd` |

</details>

<a id="rag-20-run-03-q17"></a>

#### 17 · 예약 주문 배송 지연

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 5 · 불확실 1 · 적용 불가 8 · 미분류 1 · **적용 점수 36.7 / 100**

**Search note**

> 예상 배송일이 지난 뒤 미수령 신고를 할 수 있고, 판매자는 3영업일 안에 추적 정보·배송 업데이트 또는 환불로 응답해야 하므로 미수령 신고를 요청할 근거가 있습니다. 다만 신고 기한은 예상 배송일 기준 30일 이내이고, 구매 취소는 주문 후 30일이 넘었거나 미수령 신고를 한 경우 불가하므로 지금 취소가 가능한지는 주문일·신고 상태의 정확한 확인이 필요합니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | 0.3913 | `APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 2 | Order cancellation policy | 0.3857 | `APPLICABLE` | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` |
| 3 | Get help if you bought as a guest | 0.3653 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 4 | eBay Money Back Guarantee policy | 0.3645 | `UNCERTAIN` | `347c4c37-e21c-4f86-9575-306c1651a5df` |
| 5 | Get help if you bought as a guest | 0.3523 | `NOT_APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 6 | Order cancellation policy | 0.3518 | `APPLICABLE` | `4f1d8091-f413-47f9-92b5-8fcf59780b03` |
| 7 | eBay Money Back Guarantee policy | 0.3497 | — | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 8 | Check the status of your request | 0.3468 | `NOT_APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 9 | eBay Money Back Guarantee policy | 0.3364 | `APPLICABLE` | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` |
| 10 | eBay Money Back Guarantee policy | 0.3355 | `NOT_APPLICABLE` | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` |
| 11 | Get help if you bought as a guest | 0.3342 | `NOT_APPLICABLE` | `0f5fa726-6d78-401f-bca3-39487acb99e2` |
| 12 | Get help if you bought as a guest | 0.3336 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 13 | Order cancellation policy | 0.3317 | `NOT_APPLICABLE` | `6aa36185-3f49-40c5-bbee-fb0855dfcf39` |
| 14 | eBay Money Back Guarantee policy | 0.3282 | `APPLICABLE` | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` |
| 15 | eBay Money Back Guarantee policy | 0.3272 | `NOT_APPLICABLE` | `cdca8d5f-812a-4fed-a6a6-380989fdf86a` |

</details>

<a id="rag-20-run-03-q18"></a>

#### 18 · 발송 후 배송 주소 오류

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 2 · 불확실 1 · 적용 불가 12 · 미분류 0 · **적용 점수 16.7 / 100**

**Search note**

> 배송 전이라면 판매자에게 취소를 요청할 수 있지만, 수락 여부는 판매자 재량이며 이미 발송된 주문은 취소할 수 없습니다. 제공된 주소가 잘못된 경우에도 판매자가 이미 발송했다면 취소할 수 없고 원래 주소에서 수령을 시도하도록 안내되므로, 주소 변경·반송이나 주소 오류만을 이유로 한 환불은 보장되지 않습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | 0.3592 | `NOT_APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 2 | Get help if you bought as a guest | 0.3544 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 3 | eBay Money Back Guarantee policy | 0.3450 | `NOT_APPLICABLE` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |
| 4 | Return an item for a refund | 0.3436 | `NOT_APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 5 | Get help if you bought as a guest | 0.3433 | `APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 6 | Return an item for a refund | 0.3356 | `NOT_APPLICABLE` | `927f2845-49b1-40f5-b351-48dee6c6ce2a` |
| 7 | Check the status of your request | 0.3344 | `NOT_APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 8 | eBay Money Back Guarantee policy | 0.3277 | `UNCERTAIN` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 9 | Get help if you bought as a guest | 0.3261 | `NOT_APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |
| 10 | Get help if you bought as a guest | 0.3199 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 11 | How to handle a return request as a seller | 0.3188 | `NOT_APPLICABLE` | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` |
| 12 | Return shipping for buyers | 0.3166 | `NOT_APPLICABLE` | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` |
| 13 | Return an item for a refund | 0.3121 | `NOT_APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 14 | Order cancellation policy | 0.3117 | `APPLICABLE` | `4f1d8091-f413-47f9-92b5-8fcf59780b03` |
| 15 | eBay Money Back Guarantee policy | 0.3112 | `NOT_APPLICABLE` | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` |

</details>

<a id="rag-20-run-03-q19"></a>

#### 19 · 고가 상품 반송과 서명

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 6 · 불확실 1 · 적용 불가 8 · 미분류 0 · **적용 점수 43.3 / 100**

**Search note**

> 추적 기록을 반품 요청에 올리고 배송 완료가 표시되면 배송 사실을 뒷받침하는 자료로 쓸 수 있지만, eBay.com에서 총액 750달러 이상인 반품은 서명 확인도 요구됩니다. 따라서 서명 옵션 없이 추적 기록만으로 정책상 반품 배송 보호 요건을 충족했다고 단정할 수 없으며, eBay가 사건을 어떻게 판단할지는 보장되지 않습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | 0.5289 | `NOT_APPLICABLE` | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` |
| 2 | Signature confirmation policy | 0.5277 | `APPLICABLE` | `604b7fbb-2462-4a94-a0a3-59fb5290d846` |
| 3 | Return shipping for buyers | 0.5217 | `APPLICABLE` | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` |
| 4 | Signature confirmation policy | 0.5070 | `APPLICABLE` | `e2b3df5e-28ee-48d2-b4f7-f78175e269b9` |
| 5 | eBay Money Back Guarantee policy | 0.4992 | `NOT_APPLICABLE` | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` |
| 6 | eBay Money Back Guarantee policy | 0.4846 | `NOT_APPLICABLE` | `347c4c37-e21c-4f86-9575-306c1651a5df` |
| 7 | Return shipping for buyers | 0.4817 | `APPLICABLE` | `b01c873d-8724-4642-adf2-4ad03f6ada39` |
| 8 | Get help if you bought as a guest | 0.4781 | `UNCERTAIN` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 9 | Return shipping for buyers | 0.4770 | `APPLICABLE` | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` |
| 10 | Get help with an item that hasn't arrived | 0.4704 | `NOT_APPLICABLE` | `ed06401d-054a-4e08-9faa-e830c341e214` |
| 11 | eBay International Shipping Seller Terms & Conditions | 0.4645 | `NOT_APPLICABLE` | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` |
| 12 | Get help if you bought as a guest | 0.4596 | `NOT_APPLICABLE` | `cd466c01-5b0f-4451-a90b-ada15011016b` |
| 13 | eBay Money Back Guarantee policy | 0.4562 | `APPLICABLE` | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` |
| 14 | eBay Authenticity Guarantee for Sneakers | 0.4547 | `NOT_APPLICABLE` | `4c3b3f5c-f79d-4e33-8ac0-c3d09a75123f` |
| 15 | eBay Money Back Guarantee policy | 0.4497 | `NOT_APPLICABLE` | `cdca8d5f-812a-4fed-a6a6-380989fdf86a` |

</details>

<a id="rag-20-run-03-q20"></a>

#### 20 · 플랫폼 밖 부분 환불

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 9 · 불확실 0 · 적용 불가 6 · 미분류 0 · **적용 점수 60.0 / 100**

**Search note**

> 결함 상품은 eBay Money Back Guarantee 대상일 수 있고, 판매자와 해결되지 않으면 열린 반품 요청에서 eBay 개입을 요청할 수 있습니다. 다만 정책은 외부 계좌이체를 받은 뒤에도 잔액을 같은 요청에서 청구할 수 있다고 보장하지 않으므로, 요청을 닫거나 외부 결제 방식으로 중복 분쟁을 제기하지 말고 eBay 요청 내 부분 환불 제안을 처리할 수 있는지 확인해야 합니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | 0.4682 | `APPLICABLE` | `3b462389-7437-407c-87bd-ea2d91d04130` |
| 2 | Return an item for a refund | 0.4669 | `APPLICABLE` | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` |
| 3 | eBay Money Back Guarantee policy | 0.4627 | `APPLICABLE` | `6e065bd8-ddcb-4159-88ec-51f7904be5f9` |
| 4 | How to handle a return request as a seller | 0.4607 | `NOT_APPLICABLE` | `1743ff1f-d3d4-4bdd-8248-211875998334` |
| 5 | Get help if you bought as a guest | 0.4524 | `NOT_APPLICABLE` | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` |
| 6 | Check the status of your request | 0.4518 | `APPLICABLE` | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` |
| 7 | How to handle a return request as a seller | 0.4512 | `NOT_APPLICABLE` | `99dbe313-a5e6-473a-884d-9758d7490e93` |
| 8 | Get help if you bought as a guest | 0.4502 | `NOT_APPLICABLE` | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` |
| 9 | eBay Money Back Guarantee policy | 0.4447 | `APPLICABLE` | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` |
| 10 | Return an item for a refund | 0.4436 | `APPLICABLE` | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` |
| 11 | Return an item for a refund | 0.4421 | `APPLICABLE` | `685c1734-1f4e-487d-80a4-9a9888659d25` |
| 12 | How to handle a return request as a seller | 0.4407 | `NOT_APPLICABLE` | `6109adcf-ec42-4a95-818c-a2ef540a612d` |
| 13 | How to handle a return request as a seller | 0.4406 | `APPLICABLE` | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` |
| 14 | How to handle a return request as a seller | 0.4400 | `NOT_APPLICABLE` | `350c99f0-67bd-4939-abab-98d6ddf129f7` |
| 15 | Get help if you bought as a guest | 0.4398 | `APPLICABLE` | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` |

</details>

</details>

<a id="rag-20-run-04"></a>

## 실험 04 · 25개 문서 · 300/200 · topK=15 · 한국어 질의

<details open>
<summary>실험 04 결과 · 접기/펼치기</summary>

### RAG 조건

| 변경 항목 | 실제 실행값 |
|---|---|
| 청킹 | 300/200. 문서 25개·청크 222개 |
| 적재 | 222개 청크를 text-embedding-3-large로 임베딩해 임시 PgVector 테이블에 적재 |

같은 한국어 상세 질문 20개와 topK=15, 최소 유사도 0.0, 플랫폼 EBAY를 사용했다. 모델은 gpt-6-luna, reasoning_effort=none, 최대 출력 4,096토큰이며 기존 정책 검토 프롬프트와 실험 02·03의 간결한 reason 지시를 적용했다.

### 결과 요약

- 검색 및 모델 응답 생성 20/20건 · 반환 청크 300개
- 모델 Search status · 충분 15건 · 부족 5건 · 정책 미검색 0건
- 청크 판단 · 적용 100 · 불확실 62 · 적용 불가 134 · 미분류 4 · 적용 점수 43.7 / 100
- 생성 API 입력 98,435토큰 · 출력 33,440토큰 · 합계 131,875토큰. 실험 03 대비 20.5% 감소.

적용 점수는 (적용 + 0.5 × 불확실) ÷ 전체 반환 청크 수 × 100으로 계산했다. 충분·부족과 청크 적용 판단은 모델이 반환한 값을 집계했다. 부족 판정 문항은 4·8·13·17·20번이다.

### 청크 본문 검토

450/200 기준 본문을 기존 인덱스와 대조한 뒤 300/200으로 분할했다. 문서 25개 중 21개에서 본문·경계가 바뀌었다.

| 질문 | 실험 03 핵심 조항 순위 | 이번 순위 | 본문 관찰 |
|---|---:|---:|---|
| 04 · 최초 배송비 | 14 | 상위 15개 미검색 | 단순 변심의 최초 배송비 제외 조항이 검색 결과에서 빠졌다. |
| 07 · 카드사 이의 제기 | 15 | 1 | 중복 청구 조건과 eBay 사건 종료 가능성이 함께 있다. 앞부분의 판매자 자금 보류·상환 내용은 남았지만 환율 설명 등 일부 다른 주제는 빠졌다. |
| 08 · 배대지 | 15 | 7 | 제3자 배송대행 제외와 공식 프로그램 예외가 함께 보존됐다. 이후 위조품·티켓·정품 인증 설명까지 포함되던 큰 청크에서 일부 후속 주제가 분리됐다. |
| 13 · 위조 의심 | 상위 15개 미검색 | 상위 15개 미검색 | 일반 위조 의심 신고·반품의 핵심 조항을 찾지 못했다. |
| 17 · 예약판매 | 상위 15개 미검색 | 상위 15개 미검색 | 예약판매 정책은 검색되지 않았다. |

| 질문 | 주제 | Search status | 1위 유사도 | 적용 | 불확실 | 적용 불가 | 미분류 | 적용 점수 / 100 |
|---:|---|---|---:|---:|---:|---:|---:|---:|
| [01](#rag-20-run-04-q01) | 추적번호 없는 배송 지연 | `EVIDENCE_SUFFICIENT` | 0.5946 | 5 | 1 | 8 | 1 | 36.7 |
| [02](#rag-20-run-04-q02) | 노트북 결함과 부분 환불 | `EVIDENCE_SUFFICIENT` | 0.4072 | 8 | 2 | 5 | 0 | 60.0 |
| [03](#rag-20-run-04-q03) | 발송 전 주문 취소 | `EVIDENCE_SUFFICIENT` | 0.5246 | 4 | 0 | 11 | 0 | 26.7 |
| [04](#rag-20-run-04-q04) | 단순 변심과 최초 배송비 | `EVIDENCE_INSUFFICIENT` | 0.3415 | 3 | 5 | 7 | 0 | 36.7 |
| [05](#rag-20-run-04-q05) | 파손 반품과 국제 반송비 | `EVIDENCE_SUFFICIENT` | 0.4869 | 10 | 5 | 0 | 0 | 83.3 |
| [06](#rag-20-run-04-q06) | 교체 약속과 반품 요청 종료 | `EVIDENCE_SUFFICIENT` | 0.4961 | 7 | 0 | 8 | 0 | 46.7 |
| [07](#rag-20-run-04-q07) | 카드사 결제 이의 제기 | `EVIDENCE_SUFFICIENT` | 0.5332 | 5 | 1 | 9 | 0 | 36.7 |
| [08](#rag-20-run-04-q08) | 배대지 경유와 상품 불일치 | `EVIDENCE_INSUFFICIENT` | 0.4827 | 1 | 9 | 5 | 0 | 36.7 |
| [09](#rag-20-run-04-q09) | 한 주문의 일부 미수령 | `EVIDENCE_SUFFICIENT` | 0.4254 | 7 | 0 | 8 | 0 | 46.7 |
| [10](#rag-20-run-04-q10) | 반송 완료 후 카드 환불 지연 | `EVIDENCE_SUFFICIENT` | 0.4953 | 7 | 0 | 8 | 0 | 46.7 |
| [11](#rag-20-run-04-q11) | 반품 주소 불일치 | `EVIDENCE_SUFFICIENT` | 0.4866 | 7 | 3 | 5 | 0 | 56.7 |
| [12](#rag-20-run-04-q12) | 고가 상품과 대리 수령 | `EVIDENCE_SUFFICIENT` | 0.5120 | 4 | 1 | 10 | 0 | 30.0 |
| [13](#rag-20-run-04-q13) | 운동화 상태와 위조 의심 | `EVIDENCE_INSUFFICIENT` | 0.4950 | 2 | 13 | 0 | 0 | 56.7 |
| [14](#rag-20-run-04-q14) | 비회원 불량품 반품 | `EVIDENCE_SUFFICIENT` | 0.5054 | 3 | 4 | 7 | 1 | 33.3 |
| [15](#rag-20-run-04-q15) | 포장 개봉과 환불액 차감 | `EVIDENCE_SUFFICIENT` | 0.5418 | 5 | 4 | 6 | 0 | 46.7 |
| [16](#rag-20-run-04-q16) | 국제 배송 파손과 수입 비용 | `EVIDENCE_SUFFICIENT` | 0.5335 | 0 | 5 | 8 | 2 | 16.7 |
| [17](#rag-20-run-04-q17) | 예약 주문 배송 지연 | `EVIDENCE_INSUFFICIENT` | 0.4181 | 3 | 3 | 9 | 0 | 30.0 |
| [18](#rag-20-run-04-q18) | 발송 후 배송 주소 오류 | `EVIDENCE_SUFFICIENT` | 0.5310 | 3 | 5 | 7 | 0 | 36.7 |
| [19](#rag-20-run-04-q19) | 고가 상품 반송과 서명 | `EVIDENCE_SUFFICIENT` | 0.6034 | 7 | 0 | 8 | 0 | 46.7 |
| [20](#rag-20-run-04-q20) | 플랫폼 밖 부분 환불 | `EVIDENCE_INSUFFICIENT` | 0.4893 | 9 | 1 | 5 | 0 | 63.3 |
| **합계** | **20개 질문** | 충분 15 · 부족 5 · 정책 미검색 0 | — | **100** | **62** | **134** | **4** | **43.7** |

### 질문별 Search note와 검색 결과

<a id="rag-20-run-04-q01"></a>

#### 01 · 추적번호 없는 배송 지연

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 5 · 불확실 1 · 적용 불가 8 · 미분류 1 · **적용 점수 36.7 / 100**

**Search note**

> 예정 배송일이 지난 뒤 미수령 신고를 할 수 있고, 신고 후 판매자는 3영업일 안에 추적정보·배송 업데이트 또는 환불을 제공해야 하므로 지금 신고하라는 근거가 있습니다. 해결되지 않으면 요청 상세의 ‘Ask eBay to step in and help’에서 eBay에 도움을 요청할 수 있지만, 정확한 신고 가능 기한은 주문의 실제 예정 배송일 기준으로 확인해야 합니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | 0.5946 | `APPLICABLE` | `83068342-2f1f-3df6-9194-448740f70dc9` |
| 2 | eBay Money Back Guarantee policy | 0.5071 | `APPLICABLE` | `96abfa97-3caf-3b5f-bd5d-6156fcc2e3d6` |
| 3 | Get help if you bought as a guest | 0.5030 | `APPLICABLE` | `dad48511-7153-322c-a25f-a579024d1cf2` |
| 4 | eBay Money Back Guarantee policy | 0.4960 | `APPLICABLE` | `c3aaadcc-229c-39c9-ae0d-5868d7dcb687` |
| 5 | Get help if you bought as a guest | 0.4738 | `NOT_APPLICABLE` | `f23c0885-a103-3826-91a8-1b8bf9a19901` |
| 6 | eBay Money Back Guarantee policy | 0.4515 | `APPLICABLE` | `f6a42832-f498-333f-828b-b0dfbf1e143a` |
| 7 | eBay Money Back Guarantee policy | 0.4504 | `NOT_APPLICABLE` | `cfeb62b8-7ff2-3e05-a49e-68f2a2ec826b` |
| 8 | eBay Money Back Guarantee policy | 0.4427 | `NOT_APPLICABLE` | `767e72a9-ba70-37d8-b35f-fa595e53e11f` |
| 9 | eBay Money Back Guarantee policy | 0.4424 | `NOT_APPLICABLE` | `f9bf348c-a210-350e-b26d-9078c7834ab9` |
| 10 | Get help if you bought as a guest | 0.4410 | `NOT_APPLICABLE` | `2ee17d00-fe59-3880-ac1f-7860cdb999a3` |
| 11 | Order cancellation policy | 0.4354 | `NOT_APPLICABLE` | `4aaba1d3-8caa-315a-be30-32ad20e88a24` |
| 12 | Get help if you bought as a guest | 0.4343 | — | `5a4ef199-ce7d-32e9-8d04-f426e243268c` |
| 13 | How to handle a return request as a seller | 0.4331 | `NOT_APPLICABLE` | `bca31841-5555-35ef-8983-fcdc48b999db` |
| 14 | Get help if you bought as a guest | 0.4293 | `UNCERTAIN` | `da1c3186-e355-3949-8cd0-20c85432b3fb` |
| 15 | eBay International Shipping - Buyer Terms & Conditions | 0.4292 | `NOT_APPLICABLE` | `fb769281-142f-3c4f-a4a1-ff32cb8c5dac` |

</details>

<a id="rag-20-run-04-q02"></a>

#### 02 · 노트북 결함과 부분 환불

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 8 · 불확실 2 · 적용 불가 5 · 미분류 0 · **적용 점수 60.0 / 100**

**Search note**

> 화면 결함이 목록에 설명되지 않은 고장이라면 반품 불가 표시와 무관하게 반품·환불을 요청할 근거가 있고, 정책상 승인된 반품은 상품값과 최초 배송비를 포함한 전액 환불 및 판매자 부담 반송이 규정되어 있습니다. 다만 판매자가 세부 정보를 요청하거나 다른 해결책을 제안할 수 있으므로 40달러 제안이 거절됐다는 사실만으로 환불이 확정되는 것은 아니며, eBay 반품 절차를 따르고 반품 상태·기한 요건을 확인해야 합니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | 0.4072 | `APPLICABLE` | `0c424616-3570-34cf-8a46-aa74cc2aaa87` |
| 2 | Return an item for a refund | 0.3998 | `APPLICABLE` | `3aa51348-7b58-32d2-b842-23d2b8912fa1` |
| 3 | Get help if you bought as a guest | 0.3930 | `NOT_APPLICABLE` | `2ee17d00-fe59-3880-ac1f-7860cdb999a3` |
| 4 | Get help if you bought as a guest | 0.3913 | `NOT_APPLICABLE` | `3702eda5-7acc-3a13-b410-44a5db8dbf0a` |
| 5 | eBay Money Back Guarantee policy | 0.3907 | `NOT_APPLICABLE` | `a8034eba-b1ff-3c75-8105-c154db0473be` |
| 6 | Get help if you bought as a guest | 0.3907 | `APPLICABLE` | `56dcd166-76e1-3918-915f-73e28d02f916` |
| 7 | Condition of returned items policy | 0.3873 | `APPLICABLE` | `00ae2fce-91a1-3c59-ad6d-9d2c63de4b9d` |
| 8 | eBay International Shipping - Buyer Terms & Conditions | 0.3872 | `NOT_APPLICABLE` | `4b12e5c6-9e52-3b9c-9b65-8ce68e4dbebc` |
| 9 | eBay Money Back Guarantee policy | 0.3871 | `APPLICABLE` | `23e23177-53b8-331c-8f47-02953c934f72` |
| 10 | Return an item for a refund | 0.3851 | `APPLICABLE` | `90cd5354-8183-3f13-b6ff-e1fb0a81ff5b` |
| 11 | eBay International Shipping - Buyer Terms & Conditions | 0.3805 | `NOT_APPLICABLE` | `fb769281-142f-3c4f-a4a1-ff32cb8c5dac` |
| 12 | eBay Money Back Guarantee policy | 0.3677 | `APPLICABLE` | `9efb24b6-84f1-37d6-acbf-7a6b64fb80fd` |
| 13 | Get help if you bought as a guest | 0.3668 | `UNCERTAIN` | `9de76706-b041-34ea-bbcc-549ade99166b` |
| 14 | How to handle a return request as a seller | 0.3656 | `APPLICABLE` | `16fe84d7-4dbe-3140-b275-b6e8a491a252` |
| 15 | Get help if you bought as a guest | 0.3627 | `UNCERTAIN` | `5a4ef199-ce7d-32e9-8d04-f426e243268c` |

</details>

<a id="rag-20-run-04-q03"></a>

#### 03 · 발송 전 주문 취소

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 4 · 불확실 0 · 적용 불가 11 · 미분류 0 · **적용 점수 26.7 / 100**

**Search note**

> 주문 상세에서 취소를 요청할 수 있고, 단순 변심도 요청 사유에 포함되지만 취소 수락은 판매자 재량이므로 더 싼 판매자를 찾았다는 이유만으로 수락을 보장받지는 않습니다. 결제 완료 후 판매자가 3일 안에 답하지 않으면 요청은 닫히고 주문은 취소되지 않으며, 판매자가 취소하면 eBay 결제분은 원래 결제수단으로 전액 환불됩니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Order cancellation policy | 0.5246 | `APPLICABLE` | `4aaba1d3-8caa-315a-be30-32ad20e88a24` |
| 2 | Order cancellation policy | 0.5221 | `APPLICABLE` | `73061322-96b2-3b7f-bcc5-031af28d0141` |
| 3 | Order cancellation policy | 0.5185 | `APPLICABLE` | `af287d7d-8441-30bc-b5a5-f5d968e9f789` |
| 4 | Selling practices policy | 0.4925 | `NOT_APPLICABLE` | `f74674f4-a266-3d4b-8944-1abf658cf3a9` |
| 5 | Order cancellation policy | 0.4570 | `APPLICABLE` | `d02a148c-0ec5-3f63-8e63-b847d55fa996` |
| 6 | Get help if you bought as a guest | 0.4553 | `NOT_APPLICABLE` | `dad48511-7153-322c-a25f-a579024d1cf2` |
| 7 | eBay Authenticity Guarantee for Sneakers | 0.4328 | `NOT_APPLICABLE` | `bcbea579-051b-35da-9e5e-f0a7374d03ce` |
| 8 | Get help if you bought as a guest | 0.4131 | `NOT_APPLICABLE` | `5a4ef199-ce7d-32e9-8d04-f426e243268c` |
| 9 | Get help if you bought as a guest | 0.4103 | `NOT_APPLICABLE` | `f23c0885-a103-3826-91a8-1b8bf9a19901` |
| 10 | Get help if you bought as a guest | 0.4064 | `NOT_APPLICABLE` | `3702eda5-7acc-3a13-b410-44a5db8dbf0a` |
| 11 | eBay Money Back Guarantee policy | 0.4058 | `NOT_APPLICABLE` | `96abfa97-3caf-3b5f-bd5d-6156fcc2e3d6` |
| 12 | Get help if you bought as a guest | 0.4030 | `NOT_APPLICABLE` | `2ee17d00-fe59-3880-ac1f-7860cdb999a3` |
| 13 | Get help if you bought as a guest | 0.4018 | `NOT_APPLICABLE` | `56dcd166-76e1-3918-915f-73e28d02f916` |
| 14 | Get help with an item that hasn't arrived | 0.3984 | `NOT_APPLICABLE` | `83068342-2f1f-3df6-9194-448740f70dc9` |
| 15 | Return an item for a refund | 0.3948 | `NOT_APPLICABLE` | `3aa51348-7b58-32d2-b842-23d2b8912fa1` |

</details>

<a id="rag-20-run-04-q04"></a>

#### 04 · 단순 변심과 최초 배송비

**Search status** · `EVIDENCE_INSUFFICIENT`

**청크 적용 판단** · 적용 3 · 불확실 5 · 적용 불가 7 · 미분류 0 · **적용 점수 36.7 / 100**

**Search note**

> 반품 가능으로 표시된 주문의 목록상 반품 기간·조건을 확인해 단순 변심 반품을 신청할 수 있고, 반송비 부담은 판매자의 반품 정책과 반품 사유에 따라 달라집니다. 다만 최초 배송비 12달러 환불 여부는 확인된 청크가 단순 변심 반품에 대해 확정하지 않으며, 반송 라벨 비용도 판매자 정책을 확인해야 합니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Return an item for a refund | 0.3415 | `UNCERTAIN` | `8c293bb9-7271-3ce8-a241-939574139af5` |
| 2 | Buying with Authenticity Guarantee | 0.3363 | `NOT_APPLICABLE` | `13c1cee6-c990-3162-be68-a2116607261a` |
| 3 | Get help if you bought as a guest | 0.3359 | `UNCERTAIN` | `3702eda5-7acc-3a13-b410-44a5db8dbf0a` |
| 4 | Get help if you bought as a guest | 0.3356 | `UNCERTAIN` | `2ee17d00-fe59-3880-ac1f-7860cdb999a3` |
| 5 | Return an item for a refund | 0.3354 | `UNCERTAIN` | `3aa51348-7b58-32d2-b842-23d2b8912fa1` |
| 6 | eBay International Shipping - Buyer Terms & Conditions | 0.3347 | `NOT_APPLICABLE` | `0e3f099d-7b23-3f1b-a4ee-3d96c5786c42` |
| 7 | Get help if you bought as a guest | 0.3339 | `NOT_APPLICABLE` | `56dcd166-76e1-3918-915f-73e28d02f916` |
| 8 | eBay Guaranteed Fit | 0.3338 | `NOT_APPLICABLE` | `f91b3af4-fc3e-3174-8bcf-1a8cf6bfe01d` |
| 9 | Get help if you bought as a guest | 0.3336 | `UNCERTAIN` | `9de76706-b041-34ea-bbcc-549ade99166b` |
| 10 | How to handle a return request as a seller | 0.3314 | `APPLICABLE` | `f7696f89-41db-36d4-a9d1-71dddc1596dc` |
| 11 | Condition of returned items policy | 0.3299 | `APPLICABLE` | `00ae2fce-91a1-3c59-ad6d-9d2c63de4b9d` |
| 12 | eBay Refurbished | 0.3237 | `NOT_APPLICABLE` | `9d4e8035-33fa-341e-807c-8db7cd51dacb` |
| 13 | eBay International Shipping - Buyer Terms & Conditions | 0.3236 | `NOT_APPLICABLE` | `4b12e5c6-9e52-3b9c-9b65-8ce68e4dbebc` |
| 14 | eBay Money Back Guarantee policy | 0.3233 | `NOT_APPLICABLE` | `0c424616-3570-34cf-8a46-aa74cc2aaa87` |
| 15 | Return an item for a refund | 0.3219 | `APPLICABLE` | `90cd5354-8183-3f13-b6ff-e1fb0a81ff5b` |

</details>

<a id="rag-20-run-04-q05"></a>

#### 05 · 파손 반품과 국제 반송비

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 10 · 불확실 5 · 적용 불가 0 · 미분류 0 · **적용 점수 83.3 / 100**

**Search note**

> 물탱크 파손은 배송 당시 손상된 상품에 해당할 수 있어, 이를 이유로 반품한다면 판매자가 반송비를 부담하며 판매자 제공 라벨 또는 합의된 반송 방법을 사용해야 합니다. 다만 국제 반송이 eBay International Shipping(EIS) 건인지 확인되지 않았으므로, 먼저 반품 세부정보와 eBay 안내에서 라벨·비용 부담 주체를 확인하고, 판매자 부담인데도 비용을 내라고 하면 eBay에 개입을 요청하세요.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay International Shipping - Buyer Terms & Conditions | 0.4869 | `UNCERTAIN` | `4b12e5c6-9e52-3b9c-9b65-8ce68e4dbebc` |
| 2 | Return shipping for buyers | 0.4838 | `APPLICABLE` | `f74324db-3ca6-3004-9b03-6c90b9e84277` |
| 3 | eBay Money Back Guarantee policy | 0.4829 | `APPLICABLE` | `23e23177-53b8-331c-8f47-02953c934f72` |
| 4 | Return shipping for buyers | 0.4784 | `APPLICABLE` | `658c960b-811c-392e-956a-71fa18e78a99` |
| 5 | eBay International Shipping - Buyer Terms & Conditions | 0.4772 | `UNCERTAIN` | `0e3f099d-7b23-3f1b-a4ee-3d96c5786c42` |
| 6 | Return an item for a refund | 0.4732 | `APPLICABLE` | `3aa51348-7b58-32d2-b842-23d2b8912fa1` |
| 7 | eBay Money Back Guarantee policy | 0.4667 | `APPLICABLE` | `9efb24b6-84f1-37d6-acbf-7a6b64fb80fd` |
| 8 | Get help if you bought as a guest | 0.4484 | `APPLICABLE` | `3702eda5-7acc-3a13-b410-44a5db8dbf0a` |
| 9 | Return shipping for buyers | 0.4449 | `UNCERTAIN` | `3b6c2c88-ccb3-3896-8ca3-cdf2458f9c1d` |
| 10 | Get help if you bought as a guest | 0.4385 | `APPLICABLE` | `56dcd166-76e1-3918-915f-73e28d02f916` |
| 11 | Return shipping for buyers | 0.4305 | `UNCERTAIN` | `7165fa1d-ec48-3fee-98bb-462980942162` |
| 12 | eBay International Shipping - Buyer Terms & Conditions | 0.4287 | `UNCERTAIN` | `fb769281-142f-3c4f-a4a1-ff32cb8c5dac` |
| 13 | Return shipping for buyers | 0.4270 | `APPLICABLE` | `5f3b063a-52b0-3f02-afd7-94139a28c53b` |
| 14 | Condition of returned items policy | 0.4217 | `APPLICABLE` | `00ae2fce-91a1-3c59-ad6d-9d2c63de4b9d` |
| 15 | eBay Money Back Guarantee policy | 0.4183 | `APPLICABLE` | `0c424616-3570-34cf-8a46-aa74cc2aaa87` |

</details>

<a id="rag-20-run-04-q06"></a>

#### 06 · 교체 약속과 반품 요청 종료

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 7 · 불확실 0 · 적용 불가 8 · 미분류 0 · **적용 점수 46.7 / 100**

**Search note**

> 다른 색상은 상품 설명과 다른 경우의 반품 사유가 될 수 있고, 판매자는 반품 요청에 교체품을 제안할 수 있습니다. 다만 요청을 닫으면 같은 거래에 대해 다시 열거나 새 요청을 낼 수 없으므로, 교체품을 받기 전에 닫아도 이후 eBay의 도움을 받을 수 있다고 보장하는 근거는 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | 0.4961 | `NOT_APPLICABLE` | `83068342-2f1f-3df6-9194-448740f70dc9` |
| 2 | Get help if you bought as a guest | 0.4681 | `NOT_APPLICABLE` | `5a4ef199-ce7d-32e9-8d04-f426e243268c` |
| 3 | eBay Authenticity Guarantee for Sneakers | 0.4657 | `NOT_APPLICABLE` | `bcbea579-051b-35da-9e5e-f0a7374d03ce` |
| 4 | Get help if you bought as a guest | 0.4654 | `APPLICABLE` | `da1c3186-e355-3949-8cd0-20c85432b3fb` |
| 5 | Check the status of your request | 0.4639 | `APPLICABLE` | `44c90d3a-122e-38f2-83bf-21a7e015e578` |
| 6 | eBay Money Back Guarantee policy | 0.4526 | `NOT_APPLICABLE` | `96abfa97-3caf-3b5f-bd5d-6156fcc2e3d6` |
| 7 | Get help if you bought as a guest | 0.4523 | `APPLICABLE` | `56dcd166-76e1-3918-915f-73e28d02f916` |
| 8 | How to handle a return request as a seller | 0.4504 | `APPLICABLE` | `8ab68ca1-80f8-3fdd-a69d-89f33704c7d6` |
| 9 | Return an item for a refund | 0.4502 | `APPLICABLE` | `7e2a30a3-6b3b-3eab-b7cb-6b1c16e3543f` |
| 10 | Return shipping for buyers | 0.4421 | `NOT_APPLICABLE` | `f74324db-3ca6-3004-9b03-6c90b9e84277` |
| 11 | Return an item for a refund | 0.4406 | `APPLICABLE` | `90cd5354-8183-3f13-b6ff-e1fb0a81ff5b` |
| 12 | Return an item for a refund | 0.4400 | `APPLICABLE` | `bf1c9cad-1cc0-31da-8b4c-1e1720cb961e` |
| 13 | Get help if you bought as a guest | 0.4398 | `NOT_APPLICABLE` | `9de76706-b041-34ea-bbcc-549ade99166b` |
| 14 | Get help if you bought as a guest | 0.4391 | `NOT_APPLICABLE` | `dad48511-7153-322c-a25f-a579024d1cf2` |
| 15 | Get help if you bought as a guest | 0.4365 | `NOT_APPLICABLE` | `3702eda5-7acc-3a13-b410-44a5db8dbf0a` |

</details>

<a id="rag-20-run-04-q07"></a>

#### 07 · 카드사 결제 이의 제기

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 5 · 불확실 1 · 적용 불가 9 · 미분류 0 · **적용 점수 36.7 / 100**

**Search note**

> 미수령 요청은 배송 예정일이 지난 뒤 열 수 있고, 판매자는 3영업일 안에 추적 정보·배송 상황 또는 환불을 제공해야 하며 해결되지 않으면 eBay에 도움을 요청할 수 있습니다. 다만 같은 결제 건을 카드사에 차지백 또는 구매자 보호 청구로 접수하면 중복 청구가 되어 eBay가 진행 중인 보호 요청을 종료할 수 있으므로, 카드사 접수 시 eBay 요청이 유지된다고 보장할 수 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | 0.5332 | `APPLICABLE` | `55a39fdb-8e97-3808-a31e-0688c855fd57` |
| 2 | Get help with an item that hasn't arrived | 0.5308 | `APPLICABLE` | `83068342-2f1f-3df6-9194-448740f70dc9` |
| 3 | Order cancellation policy | 0.5126 | `NOT_APPLICABLE` | `4aaba1d3-8caa-315a-be30-32ad20e88a24` |
| 4 | eBay Money Back Guarantee policy | 0.5116 | `APPLICABLE` | `767e72a9-ba70-37d8-b35f-fa595e53e11f` |
| 5 | Return an item for a refund | 0.5002 | `NOT_APPLICABLE` | `bf1c9cad-1cc0-31da-8b4c-1e1720cb961e` |
| 6 | Get help if you bought as a guest | 0.4947 | `APPLICABLE` | `dad48511-7153-322c-a25f-a579024d1cf2` |
| 7 | eBay Money Back Guarantee policy | 0.4941 | `NOT_APPLICABLE` | `f9bf348c-a210-350e-b26d-9078c7834ab9` |
| 8 | eBay Money Back Guarantee policy | 0.4912 | `NOT_APPLICABLE` | `cfeb62b8-7ff2-3e05-a49e-68f2a2ec826b` |
| 9 | Check the status of your request | 0.4883 | `NOT_APPLICABLE` | `44c90d3a-122e-38f2-83bf-21a7e015e578` |
| 10 | Order cancellation policy | 0.4875 | `NOT_APPLICABLE` | `dfef84c3-bd6a-38cc-95fd-84140eec42b7` |
| 11 | Get help if you bought as a guest | 0.4864 | `NOT_APPLICABLE` | `f23c0885-a103-3826-91a8-1b8bf9a19901` |
| 12 | Get help if you bought as a guest | 0.4861 | `NOT_APPLICABLE` | `da1c3186-e355-3949-8cd0-20c85432b3fb` |
| 13 | eBay Money Back Guarantee policy | 0.4829 | `UNCERTAIN` | `025cc8d5-2251-37e3-a2c6-1ea0a6c13f0e` |
| 14 | eBay Money Back Guarantee policy | 0.4787 | `APPLICABLE` | `c3aaadcc-229c-39c9-ae0d-5868d7dcb687` |
| 15 | How to handle a return request as a seller | 0.4744 | `NOT_APPLICABLE` | `a6cb112b-7bb9-3c32-916e-98bb606c904c` |

</details>

<a id="rag-20-run-04-q08"></a>

#### 08 · 배대지 경유와 상품 불일치

**Search status** · `EVIDENCE_INSUFFICIENT`

**청크 적용 판단** · 적용 1 · 불확실 9 · 적용 불가 5 · 미분류 0 · **적용 점수 36.7 / 100**

**Search note**

> 다른 색상의 재킷은 설명과 다를 수 있어 반품 요청의 근거가 될 수 있으며, 일반적으로 설명 불일치 반품은 판매자가 반품을 받지 않는 경우에도 가능할 수 있습니다. 다만 주문 배송지가 미국 배대지이고 구매자가 제3자 배대지를 이용한 경우 보장 제외 규정이 있어, 이 주문이 eBay 국제배송 프로그램인지와 적용 사이트·요청 기한 등 핵심 조건이 확인되지 않아 환불을 보장할 수 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay International Shipping Seller Terms & Conditions | 0.4827 | `NOT_APPLICABLE` | `2d826b7f-0af3-3041-b2c9-06dafd3fe27c` |
| 2 | eBay International Shipping - Buyer Terms & Conditions | 0.4779 | `UNCERTAIN` | `4b12e5c6-9e52-3b9c-9b65-8ce68e4dbebc` |
| 3 | eBay Authenticity Guarantee for Sneakers | 0.4665 | `NOT_APPLICABLE` | `f599de33-2264-3d88-aa60-cc81f46e2993` |
| 4 | Condition of returned items policy | 0.4641 | `UNCERTAIN` | `00ae2fce-91a1-3c59-ad6d-9d2c63de4b9d` |
| 5 | eBay Money Back Guarantee policy | 0.4637 | `UNCERTAIN` | `99633358-9e67-3c6b-bd52-08275d59d46b` |
| 6 | eBay International Shipping - Buyer Terms & Conditions | 0.4598 | `UNCERTAIN` | `fb769281-142f-3c4f-a4a1-ff32cb8c5dac` |
| 7 | eBay Money Back Guarantee policy | 0.4594 | `UNCERTAIN` | `a8034eba-b1ff-3c75-8105-c154db0473be` |
| 8 | eBay International Shipping - Buyer Terms & Conditions | 0.4571 | `UNCERTAIN` | `0e3f099d-7b23-3f1b-a4ee-3d96c5786c42` |
| 9 | eBay Authenticity Guarantee for Sneakers | 0.4552 | `NOT_APPLICABLE` | `667783c1-6df3-35a7-8791-02e7c0a837ef` |
| 10 | eBay International Shipping - Buyer Terms & Conditions | 0.4543 | `NOT_APPLICABLE` | `fecfb463-c2d2-304d-8426-247b808bfed1` |
| 11 | Buying with Authenticity Guarantee | 0.4525 | `NOT_APPLICABLE` | `13c1cee6-c990-3162-be68-a2116607261a` |
| 12 | Get help if you bought as a guest | 0.4523 | `APPLICABLE` | `9de76706-b041-34ea-bbcc-549ade99166b` |
| 13 | Return an item for a refund | 0.4494 | `UNCERTAIN` | `3aa51348-7b58-32d2-b842-23d2b8912fa1` |
| 14 | eBay International Shipping - Buyer Terms & Conditions | 0.4475 | `UNCERTAIN` | `458dbcc2-d7fc-302d-9b58-e67ebc191e7a` |
| 15 | eBay Money Back Guarantee policy | 0.4475 | `UNCERTAIN` | `a7fa2dc2-4134-3a2e-978a-1cd5432f7335` |

</details>

<a id="rag-20-run-04-q09"></a>

#### 09 · 한 주문의 일부 미수령

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 7 · 불확실 0 · 적용 불가 8 · 미분류 0 · **적용 점수 46.7 / 100**

**Search note**

> 별도 상품으로 구매한 메모리카드는 미수령 상품으로 신고할 수 있고, 동일 판매자의 결합 구매라도 각 상품별로 별도 요청을 열 수 있습니다. 메모리카드 제공이나 환불은 요청할 수 있지만 결과가 보장되지는 않으며, 한 페이지의 세트·번들 상품이라면 누락분 신고가 아니라 세트 전체의 반품 요청 절차를 따라야 합니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help if you bought as a guest | 0.4254 | `APPLICABLE` | `9de76706-b041-34ea-bbcc-549ade99166b` |
| 2 | Get help if you bought as a guest | 0.4064 | `APPLICABLE` | `5a4ef199-ce7d-32e9-8d04-f426e243268c` |
| 3 | Get help if you bought as a guest | 0.4031 | `APPLICABLE` | `a6d28387-7b75-38bc-a3ff-43172d19f5d9` |
| 4 | eBay International Shipping \| Seller Center | 0.3972 | `NOT_APPLICABLE` | `44db9844-b73d-3935-9ef3-6e7a0f12a7fd` |
| 5 | Condition of returned items policy | 0.3688 | `NOT_APPLICABLE` | `00ae2fce-91a1-3c59-ad6d-9d2c63de4b9d` |
| 6 | Get help if you bought as a guest | 0.3638 | `APPLICABLE` | `2ee17d00-fe59-3880-ac1f-7860cdb999a3` |
| 7 | Get help if you bought as a guest | 0.3628 | `NOT_APPLICABLE` | `3702eda5-7acc-3a13-b410-44a5db8dbf0a` |
| 8 | eBay Money Back Guarantee policy | 0.3620 | `NOT_APPLICABLE` | `99633358-9e67-3c6b-bd52-08275d59d46b` |
| 9 | Return an item for a refund | 0.3612 | `NOT_APPLICABLE` | `3aa51348-7b58-32d2-b842-23d2b8912fa1` |
| 10 | How to handle a return request as a seller | 0.3590 | `NOT_APPLICABLE` | `f7696f89-41db-36d4-a9d1-71dddc1596dc` |
| 11 | Return an item for a refund | 0.3518 | `APPLICABLE` | `90cd5354-8183-3f13-b6ff-e1fb0a81ff5b` |
| 12 | Get help if you bought as a guest | 0.3517 | `NOT_APPLICABLE` | `56dcd166-76e1-3918-915f-73e28d02f916` |
| 13 | Get help if you bought as a guest | 0.3496 | `APPLICABLE` | `dad48511-7153-322c-a25f-a579024d1cf2` |
| 14 | eBay Authenticity Guarantee for Sneakers | 0.3490 | `NOT_APPLICABLE` | `bcbea579-051b-35da-9e5e-f0a7374d03ce` |
| 15 | Get help with an item that hasn't arrived | 0.3441 | `APPLICABLE` | `83068342-2f1f-3df6-9194-448740f70dc9` |

</details>

<a id="rag-20-run-04-q10"></a>

#### 10 · 반송 완료 후 카드 환불 지연

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 7 · 불확실 0 · 적용 불가 8 · 미분류 0 · **적용 점수 46.7 / 100**

**Search note**

> 반품 상세에서 환불 기한과 환불 상태를 확인하고, 환불 기한이 지났는데 환불이 발행되지 않았다면 eBay에 도움을 요청할 수 있습니다. 반품 도착 후 판매자는 통상 2영업일 내 환불해야 하지만 일부 경우 기한이 더 주어질 수 있으므로, 카드 명세에 환불이 없다는 사실만으로 미발행 여부를 단정하지 말고 상세의 기한 및 발행 상태를 확인하세요.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help if you bought as a guest | 0.4953 | `APPLICABLE` | `3702eda5-7acc-3a13-b410-44a5db8dbf0a` |
| 2 | eBay Money Back Guarantee policy | 0.4753 | `APPLICABLE` | `cfeb62b8-7ff2-3e05-a49e-68f2a2ec826b` |
| 3 | How to handle a return request as a seller | 0.4729 | `APPLICABLE` | `a6cb112b-7bb9-3c32-916e-98bb606c904c` |
| 4 | Check the status of your request | 0.4657 | `APPLICABLE` | `44c90d3a-122e-38f2-83bf-21a7e015e578` |
| 5 | Return an item for a refund | 0.4643 | `APPLICABLE` | `3aa51348-7b58-32d2-b842-23d2b8912fa1` |
| 6 | eBay Money Back Guarantee policy | 0.4618 | `APPLICABLE` | `f9bf348c-a210-350e-b26d-9078c7834ab9` |
| 7 | How to handle a return request as a seller | 0.4513 | `NOT_APPLICABLE` | `d30cc690-4347-3685-95f3-6250dbb7a681` |
| 8 | Get help if you bought as a guest | 0.4460 | `NOT_APPLICABLE` | `dad48511-7153-322c-a25f-a579024d1cf2` |
| 9 | eBay Money Back Guarantee policy | 0.4419 | `NOT_APPLICABLE` | `64755b79-3a4b-3239-b120-6a72dab1bcec` |
| 10 | eBay Money Back Guarantee policy | 0.4389 | `NOT_APPLICABLE` | `0c424616-3570-34cf-8a46-aa74cc2aaa87` |
| 11 | Get help with an item that hasn't arrived | 0.4382 | `NOT_APPLICABLE` | `83068342-2f1f-3df6-9194-448740f70dc9` |
| 12 | Get help if you bought as a guest | 0.4376 | `NOT_APPLICABLE` | `56dcd166-76e1-3918-915f-73e28d02f916` |
| 13 | eBay Money Back Guarantee policy | 0.4365 | `APPLICABLE` | `767e72a9-ba70-37d8-b35f-fa595e53e11f` |
| 14 | eBay Money Back Guarantee policy | 0.4300 | `NOT_APPLICABLE` | `9efb24b6-84f1-37d6-acbf-7a6b64fb80fd` |
| 15 | Condition of returned items policy | 0.4250 | `NOT_APPLICABLE` | `00ae2fce-91a1-3c59-ad6d-9d2c63de4b9d` |

</details>

<a id="rag-20-run-04-q11"></a>

#### 11 · 반품 주소 불일치

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 7 · 불확실 3 · 적용 불가 5 · 미분류 0 · **적용 점수 56.7 / 100**

**Search note**

> 판매자가 부담하는 반품이면 eBay가 제공한 라벨 또는 판매자와 합의한 다른 반품 방법을 사용해야 하며, 배송은 추적으로 검증 가능해야 합니다. 검색된 정책은 라벨 주소와 판매자 메시지의 주소가 다를 때 어느 쪽을 우선할지 정하지 않으므로, 네바다 주소로 보내기 전 eBay 반품 상세에서 새 라벨·주소를 확인하거나 eBay에 기한 내 도움을 요청하세요.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Return shipping for buyers | 0.4866 | `APPLICABLE` | `f74324db-3ca6-3004-9b03-6c90b9e84277` |
| 2 | Return shipping for buyers | 0.4674 | `APPLICABLE` | `5f3b063a-52b0-3f02-afd7-94139a28c53b` |
| 3 | Return shipping for buyers | 0.4589 | `APPLICABLE` | `658c960b-811c-392e-956a-71fa18e78a99` |
| 4 | eBay Money Back Guarantee policy | 0.4561 | `APPLICABLE` | `9efb24b6-84f1-37d6-acbf-7a6b64fb80fd` |
| 5 | Return shipping for buyers | 0.4559 | `APPLICABLE` | `3b6c2c88-ccb3-3896-8ca3-cdf2458f9c1d` |
| 6 | Return an item for a refund | 0.4406 | `APPLICABLE` | `3aa51348-7b58-32d2-b842-23d2b8912fa1` |
| 7 | Return shipping for buyers | 0.4335 | `NOT_APPLICABLE` | `7165fa1d-ec48-3fee-98bb-462980942162` |
| 8 | Get help if you bought as a guest | 0.4295 | `UNCERTAIN` | `3702eda5-7acc-3a13-b410-44a5db8dbf0a` |
| 9 | eBay Money Back Guarantee policy | 0.4271 | `UNCERTAIN` | `23e23177-53b8-331c-8f47-02953c934f72` |
| 10 | How to handle a return request as a seller | 0.4245 | `NOT_APPLICABLE` | `d30cc690-4347-3685-95f3-6250dbb7a681` |
| 11 | Check the status of your request | 0.4170 | `APPLICABLE` | `44c90d3a-122e-38f2-83bf-21a7e015e578` |
| 12 | Return an item for a refund | 0.4128 | `NOT_APPLICABLE` | `8c293bb9-7271-3ce8-a241-939574139af5` |
| 13 | eBay International Shipping - Buyer Terms & Conditions | 0.4079 | `NOT_APPLICABLE` | `0e3f099d-7b23-3f1b-a4ee-3d96c5786c42` |
| 14 | eBay Money Back Guarantee policy | 0.4069 | `UNCERTAIN` | `d0d45a8e-a9d9-378c-a7ea-e58663d2cf42` |
| 15 | Get help if you bought as a guest | 0.4028 | `NOT_APPLICABLE` | `dad48511-7153-322c-a25f-a579024d1cf2` |

</details>

<a id="rag-20-run-04-q12"></a>

#### 12 · 고가 상품과 대리 수령

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 4 · 불확실 1 · 적용 불가 10 · 미분류 0 · **적용 점수 30.0 / 100**

**Search note**

> 판매자에게 주문 상세에 기재된 주소와 일치하는 배송 증거 및 750달러 이상 주문에 필요한 서명 확인 기록을 요청할 근거가 있습니다. 다만 추적의 ‘배송 완료’ 표시만으로는 정책상 배송 증거 요건 충족 여부가 확인되지 않으며, 친구가 실제로 대리 수령했는지도 아직 확인되지 않았습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | 0.5120 | `APPLICABLE` | `96abfa97-3caf-3b5f-bd5d-6156fcc2e3d6` |
| 2 | eBay Money Back Guarantee policy | 0.4967 | `UNCERTAIN` | `d9e42008-7e13-37a4-af84-c0301b6530d8` |
| 3 | eBay Money Back Guarantee policy | 0.4922 | `NOT_APPLICABLE` | `1a4d84cb-cb7f-34a2-9db9-d5a99aeb5749` |
| 4 | eBay Money Back Guarantee policy | 0.4758 | `NOT_APPLICABLE` | `9efb24b6-84f1-37d6-acbf-7a6b64fb80fd` |
| 5 | eBay International Shipping - Buyer Terms & Conditions | 0.4669 | `NOT_APPLICABLE` | `fecfb463-c2d2-304d-8426-247b808bfed1` |
| 6 | Get help with an item that hasn't arrived | 0.4564 | `APPLICABLE` | `83068342-2f1f-3df6-9194-448740f70dc9` |
| 7 | eBay Authenticity Guarantee for Sneakers | 0.4507 | `NOT_APPLICABLE` | `bcbea579-051b-35da-9e5e-f0a7374d03ce` |
| 8 | Signature confirmation policy | 0.4385 | `APPLICABLE` | `37ec797d-9a13-3e29-8493-231371a5598d` |
| 9 | eBay International Shipping - Buyer Terms & Conditions | 0.4381 | `NOT_APPLICABLE` | `fb769281-142f-3c4f-a4a1-ff32cb8c5dac` |
| 10 | eBay International Shipping Seller Terms & Conditions | 0.4326 | `NOT_APPLICABLE` | `2d826b7f-0af3-3041-b2c9-06dafd3fe27c` |
| 11 | Get help if you bought as a guest | 0.4321 | `NOT_APPLICABLE` | `dad48511-7153-322c-a25f-a579024d1cf2` |
| 12 | Get help if you bought as a guest | 0.4298 | `NOT_APPLICABLE` | `f23c0885-a103-3826-91a8-1b8bf9a19901` |
| 13 | eBay Money Back Guarantee policy | 0.4295 | `NOT_APPLICABLE` | `0c424616-3570-34cf-8a46-aa74cc2aaa87` |
| 14 | eBay Authenticity Guarantee for Sneakers | 0.4285 | `NOT_APPLICABLE` | `6c32225d-8f36-324e-8306-64ddc12ff577` |
| 15 | Signature confirmation policy | 0.4270 | `APPLICABLE` | `0c3873a4-2f79-3940-8a2c-c80271728154` |

</details>

<a id="rag-20-run-04-q13"></a>

#### 13 · 운동화 상태와 위조 의심

**Search status** · `EVIDENCE_INSUFFICIENT`

**청크 적용 판단** · 적용 2 · 불확실 13 · 적용 불가 0 · 미분류 0 · **적용 점수 56.7 / 100**

**Search note**

> 밑창의 마모 흔적은 새 상품 설명과 맞지 않을 수 있고, 정품 인증 대상 상품에서 인증 과정 중 진품을 확인할 수 없거나 상태가 설명과 크게 다르면 반송 및 환불 절차가 규정되어 있습니다. 다만 주문에 인증 배지가 있었는지 확인되지 않아 인증 절차 적용은 단정할 수 없으며, 이 자료만으로 인증 비대상 상품의 일반적인 ‘설명과 다름’ 또는 위조 의심 신고 절차와 그 결과까지 확인할 수는 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay Authenticity Guarantee for Sneakers | 0.4950 | `APPLICABLE` | `8715c406-82fe-3735-8c1f-1b4d24bc40b6` |
| 2 | eBay Authenticity Guarantee for Sneakers | 0.4739 | `UNCERTAIN` | `6940a9bd-044b-3baa-a44b-b42b6c7d4b13` |
| 3 | eBay Authenticity Guarantee for Sneakers | 0.4730 | `UNCERTAIN` | `c8ac4039-809a-3045-a20d-be4750bab617` |
| 4 | eBay Authenticity Guarantee for Sneakers | 0.4603 | `UNCERTAIN` | `6015dd3a-08f9-3e43-af3f-a1d995e02026` |
| 5 | eBay Authenticity Guarantee for Sneakers | 0.4562 | `UNCERTAIN` | `bcbea579-051b-35da-9e5e-f0a7374d03ce` |
| 6 | eBay Authenticity Guarantee for Sneakers | 0.4502 | `UNCERTAIN` | `1cb81081-8163-321b-9e45-be35c4e31ec6` |
| 7 | eBay Authenticity Guarantee for Sneakers | 0.4436 | `UNCERTAIN` | `f599de33-2264-3d88-aa60-cc81f46e2993` |
| 8 | eBay Authenticity Guarantee for Sneakers | 0.4412 | `APPLICABLE` | `25288895-90c3-3e63-914b-7d765e6bdace` |
| 9 | eBay Authenticity Guarantee for Sneakers | 0.4369 | `UNCERTAIN` | `fa2baa60-2cec-365d-83a7-39a96f86341e` |
| 10 | Buying with Authenticity Guarantee | 0.4342 | `UNCERTAIN` | `93a83b94-cb04-3fdb-8864-8ddd5fb0187e` |
| 11 | eBay Authenticity Guarantee for Sneakers | 0.4227 | `UNCERTAIN` | `a450612d-633f-3f85-822f-c133623d766e` |
| 12 | eBay Authenticity Guarantee for Sneakers | 0.4089 | `UNCERTAIN` | `e12db648-65d8-30dc-b63f-3931d4f714eb` |
| 13 | Buying with Authenticity Guarantee | 0.4000 | `UNCERTAIN` | `ab0c689f-7168-3f5c-b4d5-e91d940ab10f` |
| 14 | eBay Authenticity Guarantee for Sneakers | 0.3960 | `UNCERTAIN` | `6d9f2ea3-a8f6-390e-9727-9626d9eccd32` |
| 15 | eBay Authenticity Guarantee for Sneakers | 0.3925 | `UNCERTAIN` | `28f3a23f-6af5-37e0-babd-961404f51251` |

</details>

<a id="rag-20-run-04-q14"></a>

#### 14 · 비회원 불량품 반품

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 3 · 불확실 4 · 적용 불가 7 · 미분류 1 · **적용 점수 33.3 / 100**

**Search note**

> 주문 확인 이메일 본문의 ‘View order details’에서 ‘Return item’을 선택하고, 고장 사유를 입력한 뒤 설명과 사진을 최대 10장 첨부해 ‘Confirm return’을 누르라는 절차가 확인됩니다. 전원이 켜지지 않는 제품은 고장에 해당할 수 있지만, 적용 자격·기한은 확인되지 않았으며 환불이나 교환 결과는 보장되지 않습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help if you bought as a guest | 0.5054 | `APPLICABLE` | `f23c0885-a103-3826-91a8-1b8bf9a19901` |
| 2 | Get help if you bought as a guest | 0.4540 | `NOT_APPLICABLE` | `5a4ef199-ce7d-32e9-8d04-f426e243268c` |
| 3 | Get help if you bought as a guest | 0.4459 | `APPLICABLE` | `9de76706-b041-34ea-bbcc-549ade99166b` |
| 4 | Get help if you bought as a guest | 0.4420 | `APPLICABLE` | `2ee17d00-fe59-3880-ac1f-7860cdb999a3` |
| 5 | Return an item for a refund | 0.4237 | `UNCERTAIN` | `90cd5354-8183-3f13-b6ff-e1fb0a81ff5b` |
| 6 | Get help if you bought as a guest | 0.4226 | `NOT_APPLICABLE` | `dad48511-7153-322c-a25f-a579024d1cf2` |
| 7 | Get help with an item that hasn't arrived | 0.4185 | `NOT_APPLICABLE` | `83068342-2f1f-3df6-9194-448740f70dc9` |
| 8 | Get help if you bought as a guest | 0.4056 | `UNCERTAIN` | `3702eda5-7acc-3a13-b410-44a5db8dbf0a` |
| 9 | eBay Authenticity Guarantee for Sneakers | 0.3984 | `NOT_APPLICABLE` | `667783c1-6df3-35a7-8791-02e7c0a837ef` |
| 10 | eBay Authenticity Guarantee for Sneakers | 0.3974 | `NOT_APPLICABLE` | `bcbea579-051b-35da-9e5e-f0a7374d03ce` |
| 11 | eBay Authenticity Guarantee for Sneakers | 0.3972 | `NOT_APPLICABLE` | `f599de33-2264-3d88-aa60-cc81f46e2993` |
| 12 | eBay International Shipping - Buyer Terms & Conditions | 0.3947 | — | `fb769281-142f-3c4f-a4a1-ff32cb8c5dac` |
| 13 | Buying with Authenticity Guarantee | 0.3940 | `NOT_APPLICABLE` | `93a83b94-cb04-3fdb-8864-8ddd5fb0187e` |
| 14 | eBay Money Back Guarantee policy | 0.3934 | `UNCERTAIN` | `025cc8d5-2251-37e3-a2c6-1ea0a6c13f0e` |
| 15 | Return an item for a refund | 0.3868 | `UNCERTAIN` | `bf1c9cad-1cc0-31da-8b4c-1e1720cb961e` |

</details>

<a id="rag-20-run-04-q15"></a>

#### 15 · 포장 개봉과 환불액 차감

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 5 · 불확실 4 · 적용 불가 6 · 미분류 0 · **적용 점수 46.7 / 100**

**Search note**

> 설명과 다른 상품이라면 eBay Money Back Guarantee에 따라 개봉 여부와 별개로 반품 근거가 될 수 있고, 작동 여부를 확인하는 데 필요한 사용은 전액 환불 대상에 해당할 수 있습니다. 다만 씰을 연 것이 필요한 확인을 넘는 취급인지, 60달러가 실제 가치 하락에 해당하는지 및 판매자 보호 요건 충족 여부는 확인되지 않았으므로, 이를 근거로 차감에 이의를 제기하고 판매자와 해결되지 않으면 eBay에 개입을 요청할 수 있지만 전액 환불이 보장되지는 않습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Condition of returned items policy | 0.5418 | `APPLICABLE` | `00ae2fce-91a1-3c59-ad6d-9d2c63de4b9d` |
| 2 | How to handle a return request as a seller | 0.5388 | `UNCERTAIN` | `16fe84d7-4dbe-3140-b275-b6e8a491a252` |
| 3 | Condition of returned items policy | 0.5245 | `APPLICABLE` | `b9a068b1-f76b-316b-bacd-8178b1647a58` |
| 4 | Return an item for a refund | 0.4867 | `APPLICABLE` | `3aa51348-7b58-32d2-b842-23d2b8912fa1` |
| 5 | eBay International Shipping - Buyer Terms & Conditions | 0.4774 | `NOT_APPLICABLE` | `4b12e5c6-9e52-3b9c-9b65-8ce68e4dbebc` |
| 6 | Condition of returned items policy | 0.4763 | `UNCERTAIN` | `10f25986-0ce1-39b6-8c98-94f3f27fa81b` |
| 7 | How to handle a return request as a seller | 0.4727 | `NOT_APPLICABLE` | `8ab68ca1-80f8-3fdd-a69d-89f33704c7d6` |
| 8 | eBay Authenticity Guarantee for Sneakers | 0.4723 | `NOT_APPLICABLE` | `f599de33-2264-3d88-aa60-cc81f46e2993` |
| 9 | How to handle a return request as a seller | 0.4714 | `UNCERTAIN` | `e47636c9-45fa-3971-ab18-02c31ca45ff0` |
| 10 | eBay Money Back Guarantee policy | 0.4685 | `APPLICABLE` | `a8034eba-b1ff-3c75-8105-c154db0473be` |
| 11 | Return an item for a refund | 0.4636 | `APPLICABLE` | `90cd5354-8183-3f13-b6ff-e1fb0a81ff5b` |
| 12 | Get help if you bought as a guest | 0.4575 | `NOT_APPLICABLE` | `9de76706-b041-34ea-bbcc-549ade99166b` |
| 13 | eBay Authenticity Guarantee for Sneakers | 0.4562 | `NOT_APPLICABLE` | `667783c1-6df3-35a7-8791-02e7c0a837ef` |
| 14 | Get help if you bought as a guest | 0.4552 | `UNCERTAIN` | `3702eda5-7acc-3a13-b410-44a5db8dbf0a` |
| 15 | eBay Money Back Guarantee policy | 0.4511 | `NOT_APPLICABLE` | `4f3aea41-bb85-3452-b5e3-3965b85f1d6e` |

</details>

<a id="rag-20-run-04-q16"></a>

#### 16 · 국제 배송 파손과 수입 비용

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 0 · 불확실 5 · 적용 불가 8 · 미분류 2 · **적용 점수 16.7 / 100**

**Search note**

> 조명이 eBay 운송 허브에서 구매자에게 오는 동안 파손됐고 Money Back Guarantee 청구가 승인되면, eBay에 낸 수입세·관세·국제 배송료·수입 비용은 환불 대상이 될 수 있습니다. 다만 배송 때 운송사에 별도로 낸 15달러는 DAP 반품 시 환불되지 않는다는 조항이 있고, 결제 시 낸 26달러도 DAP 여부와 eBay가 법률상 징수·송금해야 한 세금인지 확인되지 않아 전액 환불을 단정할 수 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay International Shipping - Buyer Terms & Conditions | 0.5335 | `UNCERTAIN` | `30d41968-66c0-34d0-8539-8bbbb806f6b7` |
| 2 | eBay International Shipping - Buyer Terms & Conditions | 0.5252 | `UNCERTAIN` | `4b12e5c6-9e52-3b9c-9b65-8ce68e4dbebc` |
| 3 | eBay International Shipping - Buyer Terms & Conditions | 0.4997 | `UNCERTAIN` | `bdbafc55-486d-31f6-8db0-77165cbe2d5e` |
| 4 | International purchases and shipping for buyers | 0.4901 | `NOT_APPLICABLE` | `45a43abe-e89a-346c-9e22-9af7dc29173c` |
| 5 | eBay International Shipping - Buyer Terms & Conditions | 0.4881 | `UNCERTAIN` | `458dbcc2-d7fc-302d-9b58-e67ebc191e7a` |
| 6 | eBay International Shipping - Buyer Terms & Conditions | 0.4695 | `UNCERTAIN` | `4161f85b-6cba-329c-9584-530573271439` |
| 7 | eBay International Shipping Seller Terms & Conditions | 0.4674 | `NOT_APPLICABLE` | `801afe6f-9cf7-3113-a7f9-dbfac024ba1d` |
| 8 | Condition of returned items policy | 0.4645 | `NOT_APPLICABLE` | `b9a068b1-f76b-316b-bacd-8178b1647a58` |
| 9 | Condition of returned items policy | 0.4583 | `NOT_APPLICABLE` | `00ae2fce-91a1-3c59-ad6d-9d2c63de4b9d` |
| 10 | International purchases and shipping for buyers | 0.4569 | `NOT_APPLICABLE` | `6abdafbd-391f-3153-8804-686a714794fb` |
| 11 | eBay International Shipping - Buyer Terms & Conditions | 0.4564 | — | `fb769281-142f-3c4f-a4a1-ff32cb8c5dac` |
| 12 | eBay International Shipping - Buyer Terms & Conditions | 0.4562 | — | `0e3f099d-7b23-3f1b-a4ee-3d96c5786c42` |
| 13 | eBay International Shipping Seller Terms & Conditions | 0.4551 | `NOT_APPLICABLE` | `2d826b7f-0af3-3041-b2c9-06dafd3fe27c` |
| 14 | eBay International Shipping Seller Terms & Conditions | 0.4513 | `NOT_APPLICABLE` | `f1d8ed26-3f79-3046-a123-e919787f06ad` |
| 15 | Return an item for a refund | 0.4511 | `NOT_APPLICABLE` | `3aa51348-7b58-32d2-b842-23d2b8912fa1` |

</details>

<a id="rag-20-run-04-q17"></a>

#### 17 · 예약 주문 배송 지연

**Search status** · `EVIDENCE_INSUFFICIENT`

**청크 적용 판단** · 적용 3 · 불확실 3 · 적용 불가 9 · 미분류 0 · **적용 점수 30.0 / 100**

**Search note**

> 주문 취소 요청은 판매자에게 할 수 있지만, 주문 후 30일이 지나면 정책상 취소할 수 없고 판매자가 요청을 수락할 의무도 없습니다. 미수령 신고는 예상 배송일이 지난 뒤 가능하며 통상 배송일로부터 30일 안에 해야 하지만, 예약 상품의 결제 시 표시된 정확한 예상 배송일과 적용 기한이 확인되지 않아 지금 환불 자격이 있다고 단정할 수 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Order cancellation policy | 0.4181 | `APPLICABLE` | `4aaba1d3-8caa-315a-be30-32ad20e88a24` |
| 2 | Get help if you bought as a guest | 0.4161 | `UNCERTAIN` | `dad48511-7153-322c-a25f-a579024d1cf2` |
| 3 | Get help with an item that hasn't arrived | 0.3918 | `UNCERTAIN` | `83068342-2f1f-3df6-9194-448740f70dc9` |
| 4 | Get help if you bought as a guest | 0.3787 | `NOT_APPLICABLE` | `2ee17d00-fe59-3880-ac1f-7860cdb999a3` |
| 5 | eBay Money Back Guarantee policy | 0.3768 | `UNCERTAIN` | `1ecfe2e1-b0a0-3a48-872a-6f720d549b55` |
| 6 | eBay Money Back Guarantee policy | 0.3670 | `APPLICABLE` | `f6a42832-f498-333f-828b-b0dfbf1e143a` |
| 7 | Get help if you bought as a guest | 0.3603 | `NOT_APPLICABLE` | `f23c0885-a103-3826-91a8-1b8bf9a19901` |
| 8 | Order cancellation policy | 0.3524 | `APPLICABLE` | `af287d7d-8441-30bc-b5a5-f5d968e9f789` |
| 9 | Selling practices policy | 0.3520 | `NOT_APPLICABLE` | `f74674f4-a266-3d4b-8944-1abf658cf3a9` |
| 10 | Get help if you bought as a guest | 0.3491 | `NOT_APPLICABLE` | `3702eda5-7acc-3a13-b410-44a5db8dbf0a` |
| 11 | Check the status of your request | 0.3473 | `NOT_APPLICABLE` | `44c90d3a-122e-38f2-83bf-21a7e015e578` |
| 12 | Order cancellation policy | 0.3428 | `NOT_APPLICABLE` | `d02a148c-0ec5-3f63-8e63-b847d55fa996` |
| 13 | eBay International Shipping Seller Terms & Conditions | 0.3382 | `NOT_APPLICABLE` | `2d826b7f-0af3-3041-b2c9-06dafd3fe27c` |
| 14 | Get help if you bought as a guest | 0.3372 | `NOT_APPLICABLE` | `5a4ef199-ce7d-32e9-8d04-f426e243268c` |
| 15 | eBay Authenticity Guarantee for Sneakers | 0.3369 | `NOT_APPLICABLE` | `bcbea579-051b-35da-9e5e-f0a7374d03ce` |

</details>

<a id="rag-20-run-04-q18"></a>

#### 18 · 발송 후 배송 주소 오류

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 3 · 불확실 5 · 적용 불가 7 · 미분류 0 · **적용 점수 36.7 / 100**

**Search note**

> 판매자에게 즉시 연락해 취소 가능 여부를 물을 수 있지만, 판매자가 이미 발송한 경우 취소할 수 없고 주소 변경이나 올바른 주소로의 재배송은 보장되지 않습니다. 발송 후에는 가능하면 기존 주소에서 수령하라는 안내가 있으며, 예상 배송일이 지난 뒤 미수령 신고를 할 수 있지만 주문 때 주소를 잘못 입력한 경우 환불 자격이 보장되는지는 확인되지 않았습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | Get help if you bought as a guest | 0.5310 | `APPLICABLE` | `dad48511-7153-322c-a25f-a579024d1cf2` |
| 2 | eBay Authenticity Guarantee for Sneakers | 0.4359 | `NOT_APPLICABLE` | `bcbea579-051b-35da-9e5e-f0a7374d03ce` |
| 3 | eBay International Shipping - Buyer Terms & Conditions | 0.4019 | `UNCERTAIN` | `fecfb463-c2d2-304d-8426-247b808bfed1` |
| 4 | Get help if you bought as a guest | 0.3955 | `NOT_APPLICABLE` | `5a4ef199-ce7d-32e9-8d04-f426e243268c` |
| 5 | Get help if you bought as a guest | 0.3831 | `UNCERTAIN` | `2ee17d00-fe59-3880-ac1f-7860cdb999a3` |
| 6 | eBay Money Back Guarantee policy | 0.3781 | `UNCERTAIN` | `0c424616-3570-34cf-8a46-aa74cc2aaa87` |
| 7 | eBay Money Back Guarantee policy | 0.3743 | `APPLICABLE` | `9efb24b6-84f1-37d6-acbf-7a6b64fb80fd` |
| 8 | Get help if you bought as a guest | 0.3714 | `NOT_APPLICABLE` | `3702eda5-7acc-3a13-b410-44a5db8dbf0a` |
| 9 | Get help if you bought as a guest | 0.3690 | `NOT_APPLICABLE` | `56dcd166-76e1-3918-915f-73e28d02f916` |
| 10 | Condition of returned items policy | 0.3674 | `NOT_APPLICABLE` | `00ae2fce-91a1-3c59-ad6d-9d2c63de4b9d` |
| 11 | Order cancellation policy | 0.3607 | `APPLICABLE` | `af287d7d-8441-30bc-b5a5-f5d968e9f789` |
| 12 | Return an item for a refund | 0.3602 | `NOT_APPLICABLE` | `3aa51348-7b58-32d2-b842-23d2b8912fa1` |
| 13 | Get help with an item that hasn't arrived | 0.3593 | `UNCERTAIN` | `83068342-2f1f-3df6-9194-448740f70dc9` |
| 14 | Get help if you bought as a guest | 0.3590 | `UNCERTAIN` | `f23c0885-a103-3826-91a8-1b8bf9a19901` |
| 15 | How to handle a return request as a seller | 0.3585 | `NOT_APPLICABLE` | `a7e4ec47-2865-3c6b-8aa6-0e8d739de0f7` |

</details>

<a id="rag-20-run-04-q19"></a>

#### 19 · 고가 상품 반송과 서명

**Search status** · `EVIDENCE_SUFFICIENT`

**청크 적용 판단** · 적용 7 · 불확실 0 · 적용 불가 8 · 미분류 0 · **적용 점수 46.7 / 100**

**Search note**

> 판매자 부담이 아닌 반품 배송에서 총액 750달러 이상이면 서명 확인을 포함한 추적 배송을 이용해야 보호된다고 명시되어 있어, 900달러 상품의 배송 완료 기록만으로는 요건을 충족하지 못합니다. 반품 주소와 배송일 등 기록의 세부 내용은 확인되지 않았으며, eBay의 최종 분쟁 판단 결과까지 보장하는 정책은 검색되지 않았습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | 0.6034 | `APPLICABLE` | `9efb24b6-84f1-37d6-acbf-7a6b64fb80fd` |
| 2 | eBay Money Back Guarantee policy | 0.5573 | `NOT_APPLICABLE` | `96abfa97-3caf-3b5f-bd5d-6156fcc2e3d6` |
| 3 | eBay Money Back Guarantee policy | 0.5399 | `NOT_APPLICABLE` | `1a4d84cb-cb7f-34a2-9db9-d5a99aeb5749` |
| 4 | Signature confirmation policy | 0.5360 | `APPLICABLE` | `37ec797d-9a13-3e29-8493-231371a5598d` |
| 5 | Return an item for a refund | 0.5316 | `APPLICABLE` | `3aa51348-7b58-32d2-b842-23d2b8912fa1` |
| 6 | Signature confirmation policy | 0.5115 | `APPLICABLE` | `0c3873a4-2f79-3940-8a2c-c80271728154` |
| 7 | Get help if you bought as a guest | 0.5046 | `APPLICABLE` | `3702eda5-7acc-3a13-b410-44a5db8dbf0a` |
| 8 | eBay International Shipping Seller Terms & Conditions | 0.5006 | `NOT_APPLICABLE` | `2d826b7f-0af3-3041-b2c9-06dafd3fe27c` |
| 9 | eBay International Shipping - Buyer Terms & Conditions | 0.4963 | `NOT_APPLICABLE` | `fb769281-142f-3c4f-a4a1-ff32cb8c5dac` |
| 10 | eBay International Shipping - Buyer Terms & Conditions | 0.4943 | `NOT_APPLICABLE` | `4b12e5c6-9e52-3b9c-9b65-8ce68e4dbebc` |
| 11 | eBay Money Back Guarantee policy | 0.4935 | `NOT_APPLICABLE` | `d9e42008-7e13-37a4-af84-c0301b6530d8` |
| 12 | eBay International Shipping - Buyer Terms & Conditions | 0.4885 | `NOT_APPLICABLE` | `0e3f099d-7b23-3f1b-a4ee-3d96c5786c42` |
| 13 | Return shipping for buyers | 0.4877 | `APPLICABLE` | `5f3b063a-52b0-3f02-afd7-94139a28c53b` |
| 14 | Return shipping for buyers | 0.4851 | `APPLICABLE` | `f74324db-3ca6-3004-9b03-6c90b9e84277` |
| 15 | Get help if you bought as a guest | 0.4802 | `NOT_APPLICABLE` | `f23c0885-a103-3826-91a8-1b8bf9a19901` |

</details>

<a id="rag-20-run-04-q20"></a>

#### 20 · 플랫폼 밖 부분 환불

**Search status** · `EVIDENCE_INSUFFICIENT`

**청크 적용 판단** · 적용 9 · 불확실 1 · 적용 불가 5 · 미분류 0 · **적용 점수 63.3 / 100**

**Search note**

> 결함 상품은 eBay 반품 절차 대상이 될 수 있고, 판매자가 부분 환불을 제안할 수 있으며 해결되지 않으면 정해진 시점에 eBay에 개입을 요청할 수 있습니다. 다만 외부 계좌이체를 받은 뒤 같은 요청에서 남은 금액을 계속 청구할 수 있는지, 그 지급이 부분 환불로 인정되는지는 검색된 정책이 정하지 않으므로 보장할 수 없습니다.

<details>
<summary>검색 결과 15개 · 유사도와 적용 판단</summary>

| 순위 | 정책 문서 | 코사인 유사도 | 적용 판단 | 청크 ID |
|---:|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | 0.4893 | `UNCERTAIN` | `767e72a9-ba70-37d8-b35f-fa595e53e11f` |
| 2 | eBay Money Back Guarantee policy | 0.4860 | `APPLICABLE` | `55a39fdb-8e97-3808-a31e-0688c855fd57` |
| 3 | Get help if you bought as a guest | 0.4814 | `NOT_APPLICABLE` | `3702eda5-7acc-3a13-b410-44a5db8dbf0a` |
| 4 | How to handle a return request as a seller | 0.4796 | `APPLICABLE` | `a6cb112b-7bb9-3c32-916e-98bb606c904c` |
| 5 | Return an item for a refund | 0.4779 | `APPLICABLE` | `bf1c9cad-1cc0-31da-8b4c-1e1720cb961e` |
| 6 | eBay Money Back Guarantee policy | 0.4773 | `NOT_APPLICABLE` | `64755b79-3a4b-3239-b120-6a72dab1bcec` |
| 7 | eBay International Shipping Seller Terms & Conditions | 0.4562 | `NOT_APPLICABLE` | `2d826b7f-0af3-3041-b2c9-06dafd3fe27c` |
| 8 | Get help if you bought as a guest | 0.4541 | `APPLICABLE` | `da1c3186-e355-3949-8cd0-20c85432b3fb` |
| 9 | How to handle a return request as a seller | 0.4540 | `APPLICABLE` | `8ab68ca1-80f8-3fdd-a69d-89f33704c7d6` |
| 10 | eBay Money Back Guarantee policy | 0.4522 | `APPLICABLE` | `f9bf348c-a210-350e-b26d-9078c7834ab9` |
| 11 | Check the status of your request | 0.4515 | `APPLICABLE` | `44c90d3a-122e-38f2-83bf-21a7e015e578` |
| 12 | Return an item for a refund | 0.4468 | `APPLICABLE` | `8c293bb9-7271-3ce8-a241-939574139af5` |
| 13 | How to handle a return request as a seller | 0.4434 | `APPLICABLE` | `69a84283-6371-317d-8842-a9c6e5368fc7` |
| 14 | Return an item for a refund | 0.4428 | `NOT_APPLICABLE` | `3aa51348-7b58-32d2-b842-23d2b8912fa1` |
| 15 | Signature confirmation policy | 0.4376 | `NOT_APPLICABLE` | `6c051396-b6c0-3ad1-abc8-f81ff5a551e8` |

</details>

</details>
