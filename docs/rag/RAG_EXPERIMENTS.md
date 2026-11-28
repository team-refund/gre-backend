# 정책 RAG 실험 기록

## 실험 개요

450/200과 같은 표기는 `chunkSize / minChunkSizeChars` 순서다. 충분·부족은 당시 모델 응답의 `searchStatus`에 기록된 값이다. 이전 응답에 `SUFFICIENT / INSUFFICIENT`로 기록된 표기는 보존한다. 검색 전용 실험은 순위·반환 수·토큰을 보고하고, 상태가 공란인 응답은 판정 집계에서 제외한다. 모델의 `searchNote`는 원문으로 보존한다.

| 실험 | 청킹 설정 | 문서 / 청크 수 | 질문 수 | 결과 |
|---|---|---:|---:|---|
| [미수령 환불 첫 검증](#initial-delivery) | 기록 없음 | 1 / 13 | 1 | 모델 충분 0/1건 |
| [정책·안내문 교차 검색](#cross-search) | 600/200 | 24 / 88 | 10 | 모델 충분 1/9건 · 상태 공란 1건 |
| [전체 청크 반환 검증](#all-chunks) | 450/200 | 18 / 125 | 1 | 모델 충분 1/1건 |
| [정제 HTML · 신발 오배송](#clean-shoes) | 기록 없음 | 22 / 146 | 1 | 모델 충분 0/1건 |
| [정제 HTML · 해외직구 나머지 사례](#clean-other) | 기록 없음 | 22 / 146 | 4 | 모델 충분 3/4건 |
| [해외직구 재실험 · 350/200](#clean-350) | 350/200 | 22 / 186 | 5 | 모델 충분 0/5건 |
| [해외직구 재실험 · 450/200](#clean-450) | 450/200 | 22 / 146 | 5 | 모델 충분 3/5건 |
| [해외직구 재실험 · 400/200](#clean-400) | 400/200 | 22 / 164 | 5 | 모델 충분 0/5건 |
| [해외직구 재실험 · 500/200](#clean-500) | 500/200 | 22 / 133 | 5 | 모델 충분 4/5건 |
| [구매자 질문 · 500/200](#buyer-500) | 500/200 | 22 / 133 | 20 | 모델 충분 13/20건 |
| [구매자 질문 · 450/200](#buyer-450) | 450/200 | 22 / 146 | 20 | 모델 충분 14/20건 |
| [상세 구매자 질문 · 450/200](#detailed-450) | 450/200 | 22 / 146 | 20 | 검색 20/20건 · 반환 100개 · 모델 출력 없음 |
| [상세 구매자 질문 · 450/200 · topK=15](#detailed-450-topk15) | 450/200 | 22 / 146 | 20 | 검색 20/20건 · 반환 300개 · 모델 출력 없음 |
| [상세 구매자 질문 · 450/200 · topK=15 · 문서 보강](#detailed-450-expanded-topk15) | 450/200 | 25 / 149 | 20 | 검색 20/20건 · 반환 300개 · 모델 출력 없음 |
| [상세 구매자 질문 · 450/200 · topK=15 · 영어 질의](#detailed-450-english-topk15) | 450/200 | 25 / 149 | 20 | 검색 20/20건 · 반환 300개 · 모델 출력 없음 |
| [상세 구매자 질문 · 300/200 비교 · topK=10](#detailed-300-200-topk10) | 450/200 ↔ 300/200 | 25 / 149 ↔ 222 | 20 | 설정별 검색 20/20건 · 설정별 반환 200개 · 모델 출력 없음 |
| [상세 구매자 질문 · 300/200 · topK=15](#detailed-300-200-topk15) | 300/200 | 25 / 222 | 20 | 검색 20/20건 · 반환 300개 · 모델 출력 없음 |
| [상세 구매자 질문 · 문서별 청킹 · 전체 RAG](#detailed-document-chunking-full-rag) | 300/200 + 450/200 | 25 / 152 | 20 | 모델 충분 19/20건 |

### 공통 실행 조건

| 항목 | 값 |
|---|---|
| 플랫폼 | `EBAY` |
| 검색 결과 상한 | `topK=5` |
| 최소 유사도 | `similarityThreshold=0.0` |
| 실행 횟수 | 설정별 질문당 1회 |

각 실험의 실행 조건표에는 앞선 실행에서 달라진 항목을 기록했다. 같은 조건으로 이어진 경우 기존 값을 적용한다. 청킹에 따른 문서·청크 수와 질문 수는 위 요약표에서도 확인할 수 있다.

<a id="initial-delivery"></a>

## 1회차 · 배송 완료 표시와 미수령 환불 · 2026-11-25

### 질문별 결과 요약

| 질문 | 주제 | 모델 출력 상태 | 반환 수 | 상태 출처 |
|---:|---|---|---:|---|
| [01](#initial-delivery-q01) | 배송 완료 표시와 미수령 환불 | `EVIDENCE_INSUFFICIENT` | 3 | 모델 |

**집계** · 전체 1건 · 모델 충분 0건 · 부족 1건 · 정책 미검색 0건 · 반환 결과 3개

**평가** · 배송 증빙과 미배송 시 전액 환불 조항을 찾았다. 실제 배송지와 거래 자격을 확인할 자료가 부족해 모델은 근거 부족으로 판정했다.


### 실행 조건

| 변경 항목 | 이번 실험 |
|---|---|
| 실행 경로 | 로컬 백엔드 `GET http://localhost:8080/rag` |
| 적재 문서 | [eBay Money Back Guarantee](https://www.ebay.com/help/policies/ebay/ebay?id=4210) 1개, 청크 13개 |
| 청킹 | 기록 없음 |

### 질문별 결과

<a id="initial-delivery-q01"></a>

#### 질문 01 · 배송 완료 표시와 미수령 환불

**질문 원문**

> 주문 상품을 받지 못했지만 판매자는 배송 조회에 ‘배송 완료’로 표시된다는 이유로 환불을 거절했다. 소비자는 배송 증빙을 확인하고 주문 주소로 배송된 사실이 확인되지 않으면 상품값과 최초 배송비 전액을 돌려받고 싶다.

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

HTTP 200으로 응답했으며, 정책 검토 상태는 `EVIDENCE_INSUFFICIENT`였다. 응답에는 동일한 [eBay Money Back Guarantee 정책](https://www.ebay.com/help/policies/ebay/ebay?id=4210)에서 가져온 조항 세 개가 있었다. 세 조항의 적용 여부는 모두 `UNCERTAIN`이었다.

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [eBay Money Back Guarantee 정책](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | When the buyer doesn't receive an item<br>주문 주소로 상품이 배송되지 않았을 때 상품값과 해당하는 최초 배송비의 전액 환불 근거 | — | — | `UNCERTAIN` |
| 2 | [eBay Money Back Guarantee 정책](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | Deciding the outcome when the buyer doesn't receive an item<br>구매자 보호 자격을 충족하는 거래에서 배송 또는 수령 증빙을 확인하고, 배송·수령이 이루어지지 않았다고 판단하면 전액 환불한다는 근거 | — | — | `UNCERTAIN` |
| 3 | [eBay Money Back Guarantee 정책](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | Evidence of successful delivery<br>추적번호, 배송 스캔, 배송 상태·날짜, 수취인 주소 일치 및 주문 금액에 따른 서명 확인 요건 | — | — | `UNCERTAIN` |

**모델 응답 원문**

`note`

> 정책은 주문 상세의 주소로 배송되지 않았다고 확인되면 상품값과 최초 배송비 전액 환불을 규정합니다. 배송 조회에 ‘배송 완료’가 표시된다는 사실만으로는 정책상 배송 증빙 요건이 모두 충족되었는지 알 수 없습니다. 통합 배송사의 eBay 업로드 추적번호와 배송 스캔, 배송 상태와 날짜, 주문 상세 주소와 일치하는 수취인 주소(우편번호가 없으면 시·군·구), 그리고 총 주문 금액이 $750 이상인 경우 서명 확인이 필요합니다. 제공된 거래 사실에는 이러한 증빙의 내용, 주문 금액, 거래의 자격 요건 충족 여부와 신고 시점이 확인되지 않아 환불 적용 여부를 확정할 수 없습니다. 해당 증빙을 확인해 주문 주소로 배송된 사실이 입증되지 않으면 전액 환불 조항이 요청을 뒷받침합니다.

**관찰**

응답은 소비자가 요청한 전액 환불의 정책 근거를 찾았다. 배송 조회의 ‘배송 완료’ 표시만으로는 증빙 요건 충족을 확인할 수 없다고 설명했고, 거래 자격·신고 시점·주문 금액·실제 배송 증빙을 미확인 사항으로 남겼다. 조항별 요약 없이 응답의 `note`에 판단을 통합했다.

<a id="cross-search"></a>

## 2회차 · eBay 정책·안내문 교차 검색 · 2026-11-25

### 질문별 결과 요약

| 질문 | 주제 | 모델 출력 상태 | 반환 수 | 상태 출처 |
|---:|---|---|---:|---|
| [01](#cross-search-q01) | 배송 완료 표시와 미수령 환불 | — | 4 | 응답 상태 공란 |
| [02](#cross-search-q02) | 작동 불량과 반품 불가 | `EVIDENCE_INSUFFICIENT` | 3 | 모델 |
| [03](#cross-search-q03) | 반송 완료 후 환불 지연 | `EVIDENCE_INSUFFICIENT` | 2 | 모델 |
| [04](#cross-search-q04) | 반품 후 환불액 차감 | `EVIDENCE_INSUFFICIENT` | 4 | 모델 |
| [05](#cross-search-q05) | 주소 오류와 발송 전 취소 | `EVIDENCE_INSUFFICIENT` | 3 | 모델 |
| [06](#cross-search-q06) | 선주문 발송 지연 | `EVIDENCE_INSUFFICIENT` | 4 | 모델 |
| [07](#cross-search-q07) | 국제 배송 중 파손 | `EVIDENCE_INSUFFICIENT` | 3 | 모델 |
| [08](#cross-search-q08) | 현지 픽업 미수령 | `EVIDENCE_INSUFFICIENT` | 3 | 모델 |
| [09](#cross-search-q09) | 차량 부품 호환 및 반송비 | `EVIDENCE_INSUFFICIENT` | 4 | 모델 |
| [10](#cross-search-q10) | 미수령 사건 이의 제기 | `EVIDENCE_SUFFICIENT` | 4 | 모델 |

**집계** · 전체 10건 · 모델 충분 1건 · 부족 8건 · 정책 미검색 0건 · 상태 공란 1건 · 반환 결과 34개

**평가** · 10번은 모델이 충분으로 판정했다. 1번은 응답 상태가 비어 있어 모델 판정 집계에서 제외했다.


### 실행 조건

| 변경 항목 | 이번 실험 |
|---|---|
| 적재 문서 | eBay 정책·안내문 24개, 청크 88개 |
| 청킹 | 600/200 |
| 질문 세트 | 교차 검색 질문 10개 |

### 질문별 결과

### 질문별 결과

<a id="cross-search-q01"></a>

#### 질문 01 · 배송 완료 표시와 미수령 환불

**질문 원문**

> 배송 조회에는 ‘배송 완료’로 표시되지만 상품을 받지 못했습니다. 판매자는 그 표시만 근거로 환불을 거절했습니다. 주문 주소에 배송됐다는 사실을 확인하려면 어떤 증빙이 필요하고, 배송이 확인되지 않으면 상품값과 최초 배송비를 돌려받을 수 있나요?

**모델 출력 상태**

`SUFFICIENT` · 기록 검토

**실행 결과**

HTTP 200 · 응답의 status 값 확인 불가(공란) · 반환 조항 4개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | Evidence of successful delivery | — | — | `UNCERTAIN` |
| 2 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | Deciding the outcome when the buyer doesn't receive an item | — | — | `UNCERTAIN` |
| 3 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | 제목 없음 | — | — | `UNCERTAIN` |
| 4 | [Get help with an item that hasn't arrived](https://www.ebay.com/help/returns-refunds/item-not-delivered/item-not-received?id=4042) | Report an item you didn't receive | — | — | `APPLICABLE` |

**모델 응답 원문**

`note`

> 확인된 근거: 배송이 성공적으로 확인됐다는 증빙에는 eBay에 업로드된 통합 배송업체의 추적 번호와 기한 내 발송 스캔, 배송 완료 또는 배송 시도 상태와 날짜, 주문 상세 주소와 일치하는 우편번호(없으면 도시/카운티), 그리고 총 주문액이 $750 이상이면 서명 확인이 필요합니다. 정책은 상품이 주문 주소로 배송되지 않은 것으로 판단되면 상품값과 최초 배송비(해당하는 경우) 전액 환불을 규정합니다.
> 부족한 근거: 현재 확인된 거래 사실은 조회가 ‘배송 완료’라고 표시된다는 것뿐이며, 위 배송 증빙 요건을 충족하는지 또는 상품이 주문 주소에 실제 배송됐는지는 확인되지 않았습니다. 따라서 환불 결과는 확정할 수 없습니다. 구매 내역에서 ‘상품을 받지 못함’을 신고하고, 배송 사진이나 배송업체의 주소·배송일 확인 자료 등 추가 배송 정보를 요청하세요. 안내문상 배송일로부터 30일 이내 신고해야 보호 대상이 될 수 있고, 판매자가 해결하지 않으면 eBay에 개입을 요청할 수 있습니다.

**관찰**

배송 증빙 요건과 미수령 시 환불 조건이 기록됐다.

<a id="cross-search-q02"></a>

#### 질문 02 · 작동 불량과 반품 불가

**질문 원문**

> 상품 설명에는 정상 작동한다고 되어 있었는데 받아 보니 전원이 켜지지 않습니다. 판매자는 상품 페이지에 ‘반품 불가’라고 적었다며 요청을 거절했습니다. 반품과 환불을 요청할 근거는 무엇이고, 반품 배송비는 누가 부담하나요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

HTTP 200 · `EVIDENCE_INSUFFICIENT` · 반환 조항 3개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | 제목 없음 | — | — | `APPLICABLE` |
| 2 | [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115) | 제목 없음 | — | — | `APPLICABLE` |
| 3 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | 제목 없음 | — | — | `UNCERTAIN` |

**모델 응답 원문**

`note`

> 확인된 근거: 상품 설명과 달리 전원이 켜지지 않는다는 사실은 상품이 설명과 다르거나 고장 난 상태로 도착한 경우에 해당할 수 있습니다. eBay 안내에 따르면 이런 경우 판매자의 ‘반품 불가’ 정책이 있어도 반품을 요청할 수 있으며, 반품 요청을 열어 사유를 선택하고 고장 상태를 보여주는 사진을 첨부할 수 있습니다.
> 부족한 근거: 반품 배송비는 반품 사유와 판매자의 반품 정책에 따라 달라진다고 안내되어 있지만, 제공된 검색 결과에는 이 거래에서 누가 비용을 부담하는지 정하는 구체적인 규정이나 판매자의 전체 반품 정책이 없습니다. 따라서 배송비 부담자는 현재 자료만으로 확정할 수 없습니다. 판매자가 해결하지 않거나 요청 후 3영업일 이내 응답하지 않으면 eBay에 개입을 요청할 수 있습니다.

**관찰**

별도 관찰 기록 없음

<a id="cross-search-q03"></a>

#### 질문 03 · 반송 완료 후 환불 지연

**질문 원문**

> 반품을 접수했고 추적 기록에는 상품이 판매자에게 전달된 것으로 나옵니다. 판매자가 반품 수령 후 4영업일째 응답도 환불도 하지 않습니다. 환불 요청 상태를 어디서 확인하고, 어떤 조건에서 eBay에 개입을 요청할 수 있나요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

HTTP 200 · `EVIDENCE_INSUFFICIENT` · 반환 조항 2개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | The seller issues a refund; Ask eBay to step in | — | — | `UNCERTAIN` |
| 2 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | Get your refund; Ask eBay to step in and help | — | — | `APPLICABLE` |

**모델 응답 원문**

`note`

> 확인된 근거: 추적 기록상 반품 상품이 판매자에게 전달되었고, 판매자가 수령 후 4영업일째 환불하지 않았다는 사실은 확인됩니다. eBay 안내에 따르면 Purchases(구매 내역)에서 해당 상품의 See return details(반품 세부정보)를 선택하면 요청 상태와 환불 기한을 확인할 수 있습니다.
> 부족한 근거: 환불 기한은 반품 세부정보에 표시되며 판매자에게 추가 시간이 주어지는 경우도 있습니다. 그 기한이 이미 지났는지는 확인되지 않아 현재 eBay 개입 요청 조건을 충족했는지 단정할 수 없습니다. 세부정보에서 기한을 확인하고, 기한이 지났다면 See return details > Ask eBay to step in and help를 선택해 사유를 제출하세요.

**관찰**

별도 관찰 기록 없음

<a id="cross-search-q04"></a>

#### 질문 04 · 반품 후 환불액 차감

**질문 원문**

> 판매자가 반품을 받은 뒤 사용 흔적이 있다며 환불액 일부를 차감했습니다. 저는 개봉해서 상품을 확인한 뒤 구성품을 모두 넣어 보냈습니다. 어떤 경우에 차감할 수 있고, 차감에 동의하지 않으면 어떻게 이의를 제기할 수 있나요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

HTTP 200 · `EVIDENCE_INSUFFICIENT` · 반환 조항 4개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Condition of returned items policy](https://www.ebay.com/help/policies/member-behavior-policies/condition-returned-items-policy?id=4763) | Condition of returns policy overview | — | — | `UNCERTAIN` |
| 2 | [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115) | Misuse of this protection | — | — | `UNCERTAIN` |
| 3 | [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115) | Ask eBay to step in and help | — | — | `UNCERTAIN` |
| 4 | [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115) | What to do if an item is returned used or damaged | — | — | `UNCERTAIN` |

**모델 응답 원문**

`note`

> 확인된 근거: eBay 정책상 상품 상태·특성·작동 방식을 확인하는 데 필요하지 않은 취급으로 상품 가치가 떨어졌다면 차감할 수 있지만, 같은 상태로 반품된 상품의 시장 손실이나 반품 배송비·재입고 비용을 보전하기 위한 차감은 허용되지 않습니다. 확인된 거래 사실은 상품을 개봉해 확인하고 구성품을 모두 넣어 보냈다는 귀하의 설명뿐이며, 실제 상품 상태와 차감 사유·금액의 근거는 확인되지 않았습니다.
> 부족한 근거: 단순 확인을 넘는 사용·변경·손상이나 가치 하락이 있었는지, 원래 포장 및 구성품이 모두 반환되었는지, 차감이 실제 가치 하락에 해당하는지 확인할 자료가 없어 차감의 적정성을 확정할 수 없습니다. 반품 요청 화면에서 판매자에게 차감 사유와 상품 상태를 설명하는 자료를 요청하고, 반품 전후 사진·구성품 목록·포장 및 배송 증빙을 모아 이의를 제기하세요. 해결되지 않은 경우 3영업일이 지나면 eBay에 개입을 요청할 수 있습니다. 환불이 이미 처리됐다면 반품이 최대 10일간 열려 있을 수 있으므로 그 기간 내 요청 가능 여부를 확인하세요. eBay가 개입하면 판매자는 이후 차감할 수 없다는 정책도 있으나, 현재 거래에 적용되는지는 확인되지 않았습니다.

**관찰**

별도 관찰 기록 없음

<a id="cross-search-q05"></a>

#### 질문 05 · 주소 오류와 발송 전 취소

**질문 원문**

> 결제 직후 배송 주소의 번지가 틀린 것을 발견했습니다. 아직 발송 표시가 없고 판매자는 주문 주소를 바꿔 줄 수 없다고 합니다. 주문을 취소하고 정확한 주소로 다시 구매하려면 어떻게 해야 하고, 취소가 승인되면 결제 금액은 어떻게 환불되나요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

HTTP 200 · `EVIDENCE_INSUFFICIENT` · 반환 조항 3개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Changing your shipping details after a purchase](https://www.ebay.com/help/buying/shipping-delivery/tracking-item?id=4028) | Changing your shipping address after you’ve bought an item | — | — | `APPLICABLE` |
| 2 | [How buyers can cancel an order](https://www.ebay.com/help/buying/buy-now/cancel-order?id=4004) | Cancel an order | — | — | `APPLICABLE` |
| 3 | [Order cancellation policy](https://www.ebay.com/help/member-behavior-policies/policies/order-cancellation-policy?id=5298) | Refunds | — | — | `UNCERTAIN` |

**모델 응답 원문**

`note`

> 확인된 근거: 주소 변경은 일반적으로 판매자가 할 수 없으며, 아직 발송되지 않았다면 주문 취소를 요청한 뒤 정확한 주소로 다시 구매하라는 안내가 있습니다. Purchase history에서 해당 주문의 More actions > Cancel this order를 선택해 Submit하면 판매자가 3일 이내 수락 또는 거절할 수 있습니다. 판매자가 취소를 수락하고 eBay checkout으로 결제했다면 전액이 원래 결제 수단으로 환불됩니다.
> 부족한 근거: 결제가 eBay checkout을 통해 완료됐는지는 확인되지 않아 환불 방식에 대한 해당 조건의 적용 여부는 확정할 수 없습니다. 취소 요청은 판매자 승인이 필요하므로 취소 자체도 아직 확정된 것은 아닙니다.

**관찰**

별도 관찰 기록 없음

<a id="cross-search-q06"></a>

#### 질문 06 · 선주문 발송 지연

**질문 원문**

> 선주문 상품의 판매 페이지에 적힌 발송 예정일이 지났는데 판매자는 새 발송일을 알려주지 않았습니다. 주문에 아직 발송 기록이 없습니다. 선주문에서 판매자가 지켜야 할 발송 조건은 무엇이고, 구매자가 취소와 환불을 요청할 수 있나요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

HTTP 200 · `EVIDENCE_INSUFFICIENT` · 반환 조항 4개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Order cancellation policy](https://www.ebay.com/help/member-behavior-policies/policies/order-cancellation-policy?id=5298) | Canceling an order | — | — | `UNCERTAIN` |
| 2 | [Order cancellation policy](https://www.ebay.com/help/member-behavior-policies/policies/order-cancellation-policy?id=5298) | Actions & time frames for order cancellations | — | — | `UNCERTAIN` |
| 3 | [Order cancellation policy](https://www.ebay.com/help/member-behavior-policies/policies/order-cancellation-policy?id=5298) | Refunds | — | — | `UNCERTAIN` |
| 4 | [How buyers can cancel an order](https://www.ebay.com/help/buying/buy-now/cancel-order?id=4004) | 제목 없음 | — | — | `UNCERTAIN` |

**모델 응답 원문**

`note`

> 확인된 근거: 검색된 취소 정책에 따르면 아직 발송되지 않은 주문은 취소 요청을 제출할 수 있지만, 판매자가 수락하거나 거절할 수 있습니다. 취소가 이루어지고 eBay 결제로 결제한 경우에 한해 원래 결제 수단으로 전액 환불된다고 안내합니다. 구매 내역에서 취소 요청을 제출할 수 있으며, 판매자는 요청 후 3일 이내에 답변하도록 안내되어 있습니다.
> 부족한 근거: 선주문 상품의 발송 예정일이 지난 경우 판매자가 지켜야 할 구체적인 발송 조건이나 예정일 변경 통지 의무는 검색된 문서에서 확인되지 않습니다. 주문일이 30일 이내인지, eBay Live·경매 주문인지, 결제 방식 및 취소 요청의 수락 여부도 확인되지 않아 취소와 환불이 확정된다고 판단할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="cross-search-q07"></a>

#### 질문 07 · 국제 배송 중 파손

**질문 원문**

> eBay International Shipping으로 받은 상품이 도착했을 때 파손돼 있었습니다. 판매자는 국제 배송 중 파손이라며 자신에게 반품을 보내지 말라고 합니다. 누구에게 문제를 신고하고, 환불을 받으려면 어떤 증빙과 반송 절차가 필요한가요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

HTTP 200 · `EVIDENCE_INSUFFICIENT` · 반환 조항 3개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | 11. International Returns | — | — | `APPLICABLE` |
| 2 | [International purchases and shipping for buyers](https://www.ebay.com/help/buying/shipping-delivery/international-shipping-ebay?id=4057) | 제목 없음 | — | — | `UNCERTAIN` |
| 3 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | 11. International Returns | — | — | `UNCERTAIN` |

**모델 응답 원문**

`note`

> 확인된 근거: EIS 약관은 실제 배송일로부터 30일 안에 반품을 요청할 수 있고, 국제 반품은 판매자가 아닌 eBay가 처리·응답하며 eBay가 제공한 라벨의 반송 주소로 보내도록 정합니다. 사용자가 도착 당시 파손됐다고 밝혔으므로, 판매자에게 직접 보내지 말고 eBay의 반품 절차를 통해 요청할 근거가 있습니다.
> 부족한 근거: 검색된 문서에는 파손 신고에 필요한 사진·포장재 등 증빙의 구체적 요건이나 국제 운송 중 파손이 이 사안에 적용되는지에 대한 판단 기준이 없습니다. 환불은 반송품 검사 후 승인될 수 있고 보장되지 않으며, 승인되더라도 반송품이 지정 주소에 도착한 뒤 약 5~7영업일이 걸릴 수 있습니다. 배송일과 실제 파손 상태를 확인할 자료는 제공되지 않았습니다.

**관찰**

별도 관찰 기록 없음

<a id="cross-search-q08"></a>

#### 질문 08 · 현지 픽업 미수령

**질문 원문**

> 현지 픽업으로 결제했는데 약속한 장소와 시간에 갔을 때 판매자가 나타나지 않았습니다. 상품을 받지 않았고 수령 코드도 제공하지 않았습니다. 미수령 환불을 요청할 수 있나요? 판매자가 제가 이미 수령했다고 주장하면 어떤 증빙이 필요한가요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

HTTP 200 · `EVIDENCE_INSUFFICIENT` · 반환 조항 3개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Buying with local pickup](https://www.ebay.com/help/buying/shipping-delivery/changing-delivery-address-method/local-pickup?id=4056) | Seller isn't responding or you need to reschedule | — | — | `UNCERTAIN` |
| 2 | [Buying with local pickup](https://www.ebay.com/help/buying/shipping-delivery/changing-delivery-address-method/local-pickup?id=4056) | Seller won't scan or enter the code | — | — | `APPLICABLE` |
| 3 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | Proof that the buyer collected the item | — | — | `UNCERTAIN` |

**모델 응답 원문**

`note`

> 확인된 근거: 현지 픽업 안내는 판매자가 픽업을 확인하지 않으면 상품을 두고 오고 Purchases에서 미수령으로 신고할 수 있다고 안내합니다. 사용자는 판매자가 나타나지 않았고 상품과 수령 코드를 주고받지 않았다고 했으므로, 미수령 신고를 뒷받침하는 상황입니다. 판매자 수령 주장의 증거로는 픽업 당시 구매자가 서명한 eBay 주문 상세 사본이나 판매자가 QR 코드를 스캔하거나 6자리 코드를 입력한 기록이 제시되어 있습니다.
> 부족한 근거: 이 안내는 미수령 신고 방법을 제시하지만, 현재 사실만으로 환불 결과를 보장하지는 않습니다. 판매자가 응답하지 않는 경우의 취소·환불 절차는 eBay 메시지로 후속 연락을 하고 24~48시간 뒤에도 답이 없을 때 다시 명확한 기한을 제시한 뒤 응답이 없으면 진행하는 조건입니다. 해당 연락 내역, 주문 상태, 결제 여부 및 판매자가 실제로 수령 완료 처리했는지는 확인되지 않았습니다.

**관찰**

별도 관찰 기록 없음

<a id="cross-search-q09"></a>

#### 질문 09 · 차량 부품 호환 및 반송비

**질문 원문**

> 주문할 때 차량 정보를 정확히 입력했고 상품 페이지에 Guaranteed Fit 표시가 있었는데, 받은 부품이 제 차량에 맞지 않습니다. 판매자는 제가 반품 배송비를 부담해야 한다고 합니다. 보장 적용을 확인하려면 무엇을 봐야 하고, 어떤 절차로 반품과 환불을 요청하나요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

HTTP 200 · `EVIDENCE_INSUFFICIENT` · 반환 조항 4개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [eBay Guaranteed Fit](https://pages.ebay.com/motors/ebay-guaranteed-fit/) | How it works | — | — | `UNCERTAIN` |
| 2 | [eBay Guaranteed Fit](https://pages.ebay.com/motors/ebay-guaranteed-fit/) | Eligibility | — | — | `UNCERTAIN` |
| 3 | [eBay Guaranteed Fit](https://pages.ebay.com/motors/ebay-guaranteed-fit/) | How do returns work for eBay Guaranteed Fit? | — | — | `UNCERTAIN` |
| 4 | [eBay Guaranteed Fit](https://pages.ebay.com/motors/ebay-guaranteed-fit/) | How long do I have to open a return? | — | — | `UNCERTAIN` |

**모델 응답 원문**

`note`

> 확인된 근거: 적격 부품을 리스팅의 체크 표시와 함께 구매했고 해당 부품이 차량에 맞지 않는다면, Guaranteed Fit은 수령 후 30일 이내 무료 반품과 전액 환불을 안내합니다. My eBay의 Purchases에서 반품을 시작하고 사유로 “Doesn’t fit my vehicle”을 선택하면 무료 반품 라벨을 받을 수 있으며, 반품 승인 후 라벨이 오지 않으면 eBay에 개입을 요청할 수 있습니다.
> 부족한 근거: 차량 정보를 정확히 입력했고 상품 페이지에 Guaranteed Fit 표시가 있었다는 설명만으로는 주문한 상품의 체크 표시·프로그램 적격 여부, 해당 부품의 예외 해당 여부, 결제 수단 및 Money Back Guarantee 요건 충족 여부, 반품 신청 기한 내 여부가 확인되지 않습니다. 주문/리스팅의 체크 표시와 상품 유형, 결제 내역, 배송일 및 판매자의 반품 기간을 확인하세요. 바퀴와 타이어는 Guaranteed Fit에서 제외됩니다.

**관찰**

별도 관찰 기록 없음

<a id="cross-search-q10"></a>

#### 질문 10 · 미수령 사건 이의 제기

**질문 원문**

> 미수령 신고가 배송 완료 기록을 근거로 종결됐습니다. 종결 후 배송사로부터 다른 우편번호로 배달됐다는 확인을 받았습니다. 이 자료로 eBay 결정에 이의를 제기할 수 있나요? 제출 기한과 배송 증빙 기준은 무엇인가요?

**모델 출력 상태**

`EVIDENCE_SUFFICIENT` · 모델

**실행 결과**

HTTP 200 · `EVIDENCE_SUFFICIENT` · 반환 조항 4개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Appeal the outcome of a case as a buyer](https://www.ebay.com/help/buying/returns-refunds/appeal-ebays-decision-return-missing-item-buyers?id=4039) | Appeal the outcome of a case as a buyer | — | — | `APPLICABLE` |
| 2 | [Appeal the outcome of a case as a buyer](https://www.ebay.com/help/buying/returns-refunds/appeal-ebays-decision-return-missing-item-buyers?id=4039) | Information you may need when appealing | — | — | `APPLICABLE` |
| 3 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | Evidence of successful delivery | — | — | `APPLICABLE` |
| 4 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | 제목 없음 | — | — | `APPLICABLE` |

**모델 응답 원문**

`note`

> 정책 근거: 배송 완료 증빙에는 주문 상세의 우편번호(우편번호를 확인할 수 없으면 도시/카운티)와 일치하는 수령인 주소가 필요하며, 구매자 이의 제기 안내는 다른 주소로 배송됐다는 증거를 새 정보의 예로 명시합니다. 배송사가 주문 주소와 다른 우편번호로 배달됐다고 확인한 사실은 이의를 뒷받침합니다.
> 요청 방법: 사건 종결 후 30일 이내에 구매 내역에서 해당 상품의 ‘See case details’와 ‘Send us an appeal’을 선택해 배송사의 확인 자료를 제출하세요. 이의 제기는 검토를 요청하는 절차이며, 환불 결정이 보장되지는 않습니다.

**관찰**

별도 관찰 기록 없음

<a id="all-chunks"></a>

## 검색 결과 전체 청크 반환 검증 · 2026-11-26

### 질문별 결과 요약

| 질문 | 주제 | 모델 출력 상태 | 반환 수 | 상태 출처 |
|---:|---|---|---:|---|
| [01](#all-chunks-q01) | 배송 완료 표시와 미수령 환불 | `EVIDENCE_SUFFICIENT` | 5 | 모델 |

**집계** · 전체 1건 · 모델 충분 1건 · 부족 0건 · 정책 미검색 0건 · 반환 결과 5개

**평가** · 상위 5개 청크와 각 청크에 대한 검토 5개가 응답에 포함돼 전체 반환 동작을 확인했다. 미수령 질문 1건에 대한 검증이다.

### 실행 조건

| 변경 항목 | 이번 실험 |
|---|---|
| 적재 문서 | eBay 문서 18개, 청크 125개 |
| 청킹 | 450/200 |
| 질문 세트 | 배송 완료 표시와 미수령 환불 영문 질문 1개 |

### 질문별 결과

<a id="all-chunks-q01"></a>

#### 질문 01 · 배송 완료 표시와 미수령 환불

**질문 원문**

> Find the eBay refund policy for an item marked delivered but not received |

**모델 출력 상태**

`EVIDENCE_SUFFICIENT` · 모델

**실행 결과**

HTTP 200 OK · 반환 조항 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.6028231978416443 | `5f7fe4d0-426d-4a68-b4e3-71d8bfa89510` | `UNCERTAIN` |
| 2 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.5974207818508148 | `19d1e372-5b08-4a80-8cf3-7792894c6d3f` | `UNCERTAIN` |
| 3 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.5824366211891174 | `bbba9a37-a92a-454b-a9fe-c56a79ec3136` | `UNCERTAIN` |
| 4 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.5824358761310577 | `c7d95b0b-db1d-4a78-b1cf-ef6801ce00dd` | `NOT_APPLICABLE` |
| 5 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.5770852863788605 | `f1056fcc-c6be-4f90-8e44-3c1aca2e987d` | `NOT_APPLICABLE` |

**모델 응답 원문**

`searchNote`

> 검색된 공식 eBay Money Back Guarantee 정책에는 미수령 신고 기한과 판매자 응답 기한, eBay 개입 가능성, 성공 배송으로 인정되기 위한 추적·주소·배송 상태 및 750달러 이상 주문의 서명 확인 요건이 있습니다. 배송 완료 표시만으로 환불 여부가 확정되는지는 검색된 내용만으로 판단할 수 없습니다. 거래별 주문 정보와 배송 증빙은 제공되지 않았습니다. 다른 검색 청크는 설명과 다른 상품의 반품이나 일반적인 반품 절차로, 미수령 판단에는 적용되지 않습니다.

**관찰**

별도 관찰 기록 없음

<details>
<summary>실제 응답 JSON</summary>

```json
{"searchStatus":"EVIDENCE_SUFFICIENT","searchNote":"검색된 공식 eBay Money Back Guarantee 정책에는 미수령 신고 기한과 판매자 응답 기한, eBay 개입 가능성, 성공 배송으로 인정되기 위한 추적·주소·배송 상태 및 750달러 이상 주문의 서명 확인 요건이 있습니다. 배송 완료 표시만으로 환불 여부가 확정되는지는 검색된 내용만으로 판단할 수 없습니다. 거래별 주문 정보와 배송 증빙은 제공되지 않았습니다. 다른 검색 청크는 설명과 다른 상품의 반품이나 일반적인 반품 절차로, 미수령 판단에는 적용되지 않습니다.","clauses":[{"articleNumber":null,"articleTitle":null,"chunkId":"5f7fe4d0-426d-4a68-b4e3-71d8bfa89510","officialUrl":"https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041","originalTextEn":"Top Takeaway If the item you received doesn't match the listing, or if it arrived faulty or damaged, you can return it even if the seller's returns policy says they don't accept returns. Was this article helpful for you? Related help topics See other articles in: Returns and refunds Returning items You might also be interested in: eBay Money Back Guarantee policy eBay Money Back Guarantee covers most transactions on eBay. It means buyers can get their money back if an item didn't arrive, is faulty or damaged, or doesn't match the listing. 13 min article Return shipping for buyers There are several ways you can send an item back to the seller. Who pays the cost of return shipping depends on the seller’s return policy and the reason for the return. 2 min article Ask eBay to step in and help for buyers You can ask us to step in if there's an issue with your order and the seller hasn't been able to help. 1 min article","policyName":"Return an item for a refund"},{"articleNumber":null,"articleTitle":null,"chunkId":"19d1e372-5b08-4a80-8cf3-7792894c6d3f","officialUrl":"https://www.ebay.com/help/policies/ebay/ebay?id=4210","originalTextEn":"eBay may step in without the buyer asking if there is no valid tracking information available Earliest: 3 business days after the report date Latest: 21 business days after the report date Deciding the outcome when the buyer doesn't receive an item When a buyer reports that they didn't receive an item and the transaction meets our eligibility requirements, we will look for: Evidence of successful delivery to the address provided in the Order details, or Proof that the buyer collected the item If we determine that the item was not successfully delivered or collected: The buyer will receive a refund for the full cost of the item and original shipping, and The seller may be required to reimburse eBay for the amount of the refund Evidence of successful delivery We require all of the following to prove a successful on-time delivery: Tracking number from an integrated carrier which was uploaded to eBay and shows a shipping scan before the latest estimated delivery date; A delivery status of \"delivered\" or \"attempted delivery\" (or equivalent in the country to which the item was delivered); The date of delivery or attempted delivery; The recipient's address, showing the zip code (or international equivalent) that matches the one on the Order details page. If the zip code is unavailable, the recipient's city/county must match; and Signature confirmation, on orders with a total cost of $750 or more. Learn more about our signature confirmation requirements. For eBay Authenticity Guarantee items, additional evidence is required. See Evidence of successful delivery to the authenticator below.","policyName":"eBay Money Back Guarantee policy"},{"articleNumber":null,"articleTitle":null,"chunkId":"bbba9a37-a92a-454b-a9fe-c56a79ec3136","officialUrl":"https://www.ebay.com/help/policies/ebay/ebay?id=4210","originalTextEn":"- opens in new window or tab ebay.com.au - opens in new window or tab ebay.ca - opens in new window or tab cafr.ebay.ca - opens in new window or tab ebay.ie - opens in new window or tab ebay.nl - opens in new window or tab ebay.pl - opens in new window or tab benl.ebay.be - opens in new window or tab eBay Buyer Protection: ebay.de - opens in new window or tab ebay.at - opens in new window or tab ebay.ch - opens in new window or tab eBay Customer Guarantee: ebay.fr - opens in new window or tab ebay.it - opens in new window or tab ebay.es - opens in new window or tab befr.ebay.be - opens in new window or tab When the buyer doesn't receive an item Actions and timeframes when the buyer doesn't receive an item Deciding the outcome when the buyer doesn't receive an item Exclusions and special coverage when the buyer doesn't receive an item Sellers are required to deliver the item to the address in the Order details, unless the buyer selected a local pickup option. If the item doesn't arrive at the address provided by the buyer at checkout or isn't made available for collection, the buyer is entitled to a full refund, including original shipping costs (if applicable). Actions & time frames when the buyer doesn't receive an item Action Time frame The buyer reports that the item hasn't arrived or was not available for collection Report an item that hasn't arrived Earliest: Once the estimated or actual delivery/collection date has passed Latest: 30 calendar days after the estimated or actual delivery/collection date has passed The seller responds to the buyer's report The seller is required to respond and provide either tracking information, delivery updates, or a refund. Latest: 3 business days after the report date If the seller provided tracking details, eBay may close the case automatically once tracking shows the item has been delivered. Ask eBay to step in If the seller hasn't responded or if the buyer and seller can't reach a resolution, they can ask us to step in and help.","policyName":"eBay Money Back Guarantee policy"},{"articleNumber":null,"articleTitle":null,"chunkId":"c7d95b0b-db1d-4a78-b1cf-ef6801ce00dd","officialUrl":"https://www.ebay.com/help/policies/ebay/ebay?id=4210","originalTextEn":"When the item received by the buyer doesn't match the listing Actions and timeframes when the item received by the buyer doesn't match the listing Deciding the outcome when the item received by the buyer doesn't match the listing Exclusions and special coverage when the item received by the buyer doesn't match the listing Sellers are required to deliver the item as it was described in the listing. If the buyer receives the wrong item, or the item arrives broken, damaged, or faulty (and was not clearly described as such), they are entitled to return it for a refund, even if the seller doesn't offer returns. Buyers and sellers may agree to another solution, such as a full or partial refund while the buyer keeps the item, or a replacement item instead of a refund. Both buyers and sellers must meet all applicable return requirements. If an item is being returned, the seller is responsible for return shipping. Actions & time frames for \"not as described\" returns Action Time frame The buyer requests a return Start a return request Latest: 30 calendar days after the estimated or actual delivery date or within the seller's stated returns window, whichever is longer Trading Cards have a more limited return window. See Exclusions and special coverage. The seller responds to the buyer's request The seller is required to respond and provide a solution to the buyer's issue. Latest: 3 business days after the request date In some cases, eBay may automatically accept the return on the seller's behalf. If the item is being returned The buyer sends the item back Latest: The latest date to ship the return will be shown in an email sent by eBay to the buyer, as well as within the return request If the buyer does not ship the return by the specified date, eBay may close the return request. The seller issues a refund If return tracking shows the item was delivered, or if the item was shipped through eBay International Shipping - opens in new window or tab, eBay may automatically issue a full refund on the seller's behalf. Latest: 2 business days after receiving the returned item. In some cases, sellers have additional time to issue the refund. You can find the refund deadline in the return details.","policyName":"eBay Money Back Guarantee policy"},{"articleNumber":null,"articleTitle":null,"chunkId":"f1056fcc-c6be-4f90-8e44-3c1aca2e987d","officialUrl":"https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041","originalTextEn":"Sets and bundled items – Return the whole lot. Multiples of the same item – return some or all, but only one request for the order. Combined purchases from the same seller – open a separate request for each listing. How the seller may respond to your request The seller has 3 business days to accept and offer a full refund, offer a partial refund, offer a replacement or exchange, decline (if you changed your mind and their policy says no returns, or you missed the deadline), or ask you for more details. Send the item back Once your return is accepted, we'll email you asking you to send the item back. Ship it by the date in that email to avoid delays. If you're buying the label yourself, use a tracked service. Items worth $750 or more need signature confirmation. See return shipping for buyers for who pays. Important Repack the item carefully. If it's returned used, damaged, missing parts, or damaged in shipping because it wasn't packaged correctly, the seller may deduct from your refund — see Condition of returned items policy. Trouble with your return label? If your return label won't print, won't load, or the QR code won't scan, don't worry — you don't need to start over. Ask eBay to step in (see below) and we'll help you get a working label so you can complete your return. Get your refund Most sellers refund as soon as they get the item back. Refunds typically arrive within 3-5 business days of the seller receiving your return, back to your original or selected payment method. You can check the status of your request at any time to find your refund deadline. Why was my refund less than what I paid? If the item was returned used, damaged, missing parts, or damaged in shipping, the seller might deduct from your refund — see the Condition of returned items policy. If you paid for a label we provided, its cost is deducted too.","policyName":"Return an item for a refund"}],"reviews":[{"applicability":"UNCERTAIN","chunkId":"5f7fe4d0-426d-4a68-b4e3-71d8bfa89510","missingInformation":"이 청크는 상품 미수령의 일반적인 보장 가능성만 언급합니다. 개별 거래의 배송·신고 사실은 확인되지 않았습니다.","reason":"미수령 상품은 eBay Money Back Guarantee 대상이 될 수 있다는 안내로 질문과 관련은 있지만, 구체적인 미수령 신고 절차나 배송 완료로 표시된 경우의 판단 기준은 이 청크에 없습니다."},{"applicability":"UNCERTAIN","chunkId":"19d1e372-5b08-4a80-8cf3-7792894c6d3f","missingInformation":"주문 총액, eBay에 업로드된 통합 배송업체 추적 정보와 배송 스캔 시각, 배송 상태·날짜·주문 주소와 일치하는 우편번호 또는 도시/카운티, 해당 시 서명 확인, 수령 또는 픽업 사실이 필요합니다.","reason":"미수령 신고가 자격 요건을 충족하면 eBay는 주문 주소로 성공적으로 배송됐다는 증거 또는 구매자의 수령 증명을 검토합니다. 성공 배송 증거에는 통합 배송업체 추적 정보가 eBay에 업로드되고 예상 배송일 전 배송 스캔이 있어야 하며, 배송 완료 또는 배송 시도 상태, 날짜, 주문 상세와 일치하는 주소가 필요합니다. 총액 750달러 이상 주문에는 서명 확인도 요구됩니다. 유효한 배송 증거가 없으면 eBay가 신고 후 3~21영업일 사이에 개입할 수 있고, 미배송으로 판단하면 상품 가격과 최초 배송비를 환불할 수 있습니다. 거래별 증거는 제공되지 않았습니다."},{"applicability":"UNCERTAIN","chunkId":"bbba9a37-a92a-454b-a9fe-c56a79ec3136","missingInformation":"예상 또는 실제 배송일이 지났는지, 미수령 신고일, 판매자 응답 여부와 응답 내용, 추적 정보가 실제로 배송 완료를 증명하는지가 필요합니다.","reason":"정책상 구매자는 예상 또는 실제 배송·수령일이 지난 뒤 신고할 수 있으며, 그 날짜로부터 30일 이내가 신고 기한입니다. 판매자는 신고 후 3영업일 이내에 추적 정보, 배송 업데이트 또는 환불로 응답해야 합니다. 판매자가 응답하지 않거나 해결에 합의하지 못하면 eBay에 개입을 요청할 수 있습니다. 추적상 배송 완료로 표시되면 eBay가 사건을 자동으로 종료할 수도 있으나, 그 표시가 정책의 성공 배송 증거 요건을 충족하는지는 별도로 확인해야 합니다."},{"applicability":"NOT_APPLICABLE","chunkId":"c7d95b0b-db1d-4a78-b1cf-ef6801ce00dd","missingInformation":null,"reason":"이 청크는 상품이 설명과 다르거나 파손·고장 상태로 도착한 경우의 반품 정책입니다. 질문은 배송 완료 표시 후 상품을 받지 못한 경우이므로 해당 조항은 적용되지 않습니다."},{"applicability":"NOT_APPLICABLE","chunkId":"f1056fcc-c6be-4f90-8e44-3c1aca2e987d","missingInformation":null,"reason":"이 청크는 반품이 승인된 뒤 상품을 반환하고 환불받는 절차를 설명합니다. 미수령 신고와 배송 완료 표시의 판단 기준에는 해당하지 않습니다."}],"evidenceGaps":"거래별 환불 여부를 판단하려면 주문의 예상 또는 실제 배송일, 구매자가 미수령 신고를 했는지와 신고일, 주문 상세의 배송 주소, 추적 정보 및 배송 증빙(배송 시각·상태·주소 우편번호 또는 해당 지역, 해당되는 경우 서명 확인), 수령 또는 현지 픽업 여부를 확인해야 합니다. 거래별 사실은 제공되지 않았습니다.","similarityScores":[0.6028231978416443,0.5974207818508148,0.5824366211891174,0.5824358761310577,0.5770852863788605]}
```

</details>

<a id="clean-shoes"></a>

## 정제 HTML 검증 · 미국 판매자 직접 배송 신발 오배송 · 2026-11-26

### 질문별 결과 요약

| 질문 | 주제 | 모델 출력 상태 | 반환 수 | 상태 출처 |
|---:|---|---|---:|---|
| [01](#clean-shoes-q01) | 판매자 직접 배송 신발 오배송 | `EVIDENCE_INSUFFICIENT` | 5 | 모델 |

**집계** · 전체 1건 · 모델 충분 0건 · 부족 1건 · 정책 미검색 0건 · 반환 결과 5개

**평가** · 반품 불가 표기에도 오배송품을 반품할 수 있다는 조항을 찾았다. 상품 불일치 반품 접수 기한과 한국에서의 국제 반송 방식은 상위 5개에서 확인되지 않았다.


### 실행 조건

| 변경 항목 | 이번 실험 |
|---|---|
| 실행 경로 | 개발 로그인 세션으로 로컬 `GET /rag` 호출 |
| 적재 문서 | 정제 HTML 22개, 청크 146개 |
| 청킹 | 실행 당시 기록 없음 |
| 질문 세트 | 해외직구 질문 중 신발 오배송 1건 |

### 질문별 결과

<a id="clean-shoes-q01"></a>

#### 질문 01 · 판매자 직접 배송 신발 오배송

**질문 원문**

> 미국 eBay 판매자가 직접 배송한 신발을 한국에서 받았습니다. 주문은 US 9였지만 상자와 신발 모두 US 8입니다. 수령 12일째이고 판매자는 상품 페이지에 ‘반품 불가’라고 적었습니다. 교환을 요청할 수 있나요? 판매자가 교환을 거절하면 전액 환불과 최초·반품 배송비는 어떻게 처리되나요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

HTTP 200 · `EVIDENCE_INSUFFICIENT` · 반환 조항 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | — | 0.483672 | `ddf2ad28-5f70-4b47-b7ca-a7f228124122` | `APPLICABLE` |
| 2 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.466508 | `6829845e-3c3d-4cb4-a0bc-a75c322e72fb` | `APPLICABLE` |
| 3 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.466189 | `4a6b92ff-3ebf-4f13-af8a-2746d993fba0` | `APPLICABLE` |
| 4 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | — | 0.466053 | `b7bdd4a6-9f45-44e5-88d9-ba3987ddb2ff` | `UNCERTAIN` |
| 5 | [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115) | — | 0.459522 | `bb57cc6c-0cf2-49ac-abc0-e436570beae5` | `UNCERTAIN` |

**모델 응답 원문**

`searchNote`

> 주문 사이즈가 US 9인데 실제로 US 8을 받았다는 점이 증명되면, 상품이 listing과 다르므로 ‘반품 불가’ 표시와 무관하게 반품을 요청할 근거가 있습니다. 교환은 판매자가 제안할 수 있지만 보장되지는 않으며, 반품이 수락되어 전액 환불되는 경우 원래 배송비와 반품 배송비는 판매자가 부담한다고 정책은 설명합니다. 다만 12일째가 반품 기한 내인지와 한국발 국제 반품 배송의 구체적 처리 방식은 제공된 내용으로 확인되지 않습니다.

**관찰**

3위 청크는 상품 설명과 다른 경우 판매자가 반품 불가로 표시했어도 반품할 수 있다는 근거를 반환했다. 2위 청크는 교환이나 대체품이 판매자가 제시할 수 있는 선택지임을 보여줬다. 최초 배송비를 포함한 전액 환불과 판매자의 반품 배송비 부담을 함께 명시한 청크는 1위였고, 게스트 구매 안내에서 검색됐다. 질의에는 게스트 결제 여부가 없었다.

상위 5개에 Money Back Guarantee의 상품 불일치 반품 접수 기한이나 국제 반송 수단에 관한 청크가 포함되지 않았다. 응답은 수령 12일째가 기한 내인지와 한국에서의 구체적인 반송 방식을 미확인 사항으로 남겼다.

<a id="clean-other"></a>

## 정제 HTML 검증 · 나머지 해외직구 질의 4건 · 2026-11-26

### 질문별 결과 요약

| 질문 | 주제 | 모델 출력 상태 | 반환 수 | 상태 출처 |
|---:|---|---|---:|---|
| [02](#clean-other-q02) | 단순 변심과 사이즈 교환 | `EVIDENCE_INSUFFICIENT` | 5 | 모델 |
| [03](#clean-other-q03) | eIS 구성품 누락 | `EVIDENCE_SUFFICIENT` | 5 | 모델 |
| [04](#clean-other-q04) | 제3자 배대지와 구매자 보호 | `EVIDENCE_SUFFICIENT` | 5 | 모델 |
| [05](#clean-other-q05) | 국제 반품 라벨과 배송 증빙 | `EVIDENCE_SUFFICIENT` | 5 | 모델 |

**집계** · 전체 4건 · 모델 충분 3건 · 부족 1건 · 정책 미검색 0건 · 반환 결과 20개

**평가** · eIS 구성품 누락, 제3자 배대지, 국제 반송 증빙은 모델이 충분으로 판정했다. 단순 변심 반품의 최초 배송비 처리 조항은 상위 5개에서 확인되지 않았다.


### 실행 조건

| 변경 항목 | 이번 실험 |
|---|---|
| 질문 세트 | 같은 정제 HTML 검증의 나머지 해외직구 질문 4개 |

### 질문별 결과

### 질문별 결과

<a id="clean-other-q02"></a>

#### 질문 02 · 단순 변심과 사이즈 교환

**질문 원문**

> 미국 eBay 판매자가 한국으로 직접 배송한 재킷을 수령한 지 8일 됐습니다. 주문한 사이즈와 설명은 정확하지만 입어보니 작습니다. 상품 페이지에 ‘30일 반품 가능, 반품 배송비 구매자 부담’이라고 적혀 있습니다. 다른 사이즈로 교환해 달라고 할 수 있나요? 반품하면 최초 배송비도 돌려받나요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

HTTP 200 · EVIDENCE_INSUFFICIENT · 반환 조항 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.472321 | `4a6b92ff-3ebf-4f13-af8a-2746d993fba0` | `NOT_APPLICABLE` |
| 2 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | — | 0.453620 | `b7bdd4a6-9f45-44e5-88d9-ba3987ddb2ff` | `APPLICABLE` |
| 3 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | — | 0.450435 | `ddf2ad28-5f70-4b47-b7ca-a7f228124122` | `APPLICABLE` |
| 4 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.442368 | `6829845e-3c3d-4cb4-a0bc-a75c322e72fb` | `UNCERTAIN` |
| 5 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.439553 | `010641a4-7898-48ce-b397-703242dfb7e6` | `UNCERTAIN` |

**모델 응답 원문**

`searchNote`

> 상품 페이지의 30일 반품 조건에 따라 8일째인 지금 반품을 요청할 근거는 있으며, 국제 배송 프로그램 주문이라면 배송일부터 30일 이내 요청과 마음 변경 반송비 구매자 부담 규정이 있습니다. 교환은 판매자가 제안할 수 있을 뿐 보장되지 않고, 최초 배송비 환불 규정은 설명 불일치·결함·파손 반품에 명시되어 있어 단순히 사이즈가 맞지 않는 이 경우의 환불 여부는 확인되지 않습니다.

**관찰**

1위는 설명 불일치 조항으로 이 질의에 적용되지 않았다. 2~3위 게스트 구매 안내에서 판매자 반품 조건과 교환 제안을 찾았다. 5위 eIS 약관은 판매자 직접 배송 조건에 맞지 않는다. 단순 변심 반품의 최초 배송비를 직접 판단할 청크는 상위 5개에 없었다.

<a id="clean-other-q03"></a>

#### 질문 03 · eIS 구성품 누락

**질문 원문**

> 주문 상세에 eBay International Shipping으로 표시된 미국발 상품을 한국에서 받았는데 구성품이 빠져 있습니다. 수령 18일째이고 판매자는 교체품을 직접 보내줄 수 없다고 합니다. 누구에게 반품이나 환불을 요청해야 하며, 교체품 발송도 요구할 수 있나요?

**모델 출력 상태**

`EVIDENCE_SUFFICIENT` · 모델

**실행 결과**

HTTP 200 · EVIDENCE_SUFFICIENT · 반환 조항 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | — | 0.488246 | `b7bdd4a6-9f45-44e5-88d9-ba3987ddb2ff` | `UNCERTAIN` |
| 2 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.486075 | `010641a4-7898-48ce-b397-703242dfb7e6` | `APPLICABLE` |
| 3 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.485053 | `da4a9608-9c37-43f0-b0ec-71dd61a08b91` | `APPLICABLE` |
| 4 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.472561 | `6829845e-3c3d-4cb4-a0bc-a75c322e72fb` | `UNCERTAIN` |
| 5 | [eBay International Shipping Seller Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/seller/terms/) | — | 0.470751 | `5bfdc014-677b-4c1c-af27-c8e289ecb105` | `NOT_APPLICABLE` |

**모델 응답 원문**

`searchNote`

> 검색된 EIS 약관에 따르면 미국발 EIS 상품은 실제 배송일부터 30일 안에 반품을 요청할 수 있고, 국제 반품은 판매자가 아니라 eBay가 처리·응답합니다. 18일째라면 기간 내 요청이 가능하지만 환불은 검사와 승인에 달려 있으며, 교체품은 판매자가 제안할 수 있을 뿐 구매자가 발송을 강제할 근거는 검색된 정책에 없습니다.

**관찰**

2~3위 eIS 구매자 약관에서 실제 배송 후 30일 접수, eBay의 반품 처리, 라벨 제공, 검수 후 환불 근거가 검색됐다. 4위 일반 반품 안내는 교체가 판매자의 선택지임을 뒷받침하지만 eIS 주문에서 교체 발송을 강제할 근거는 제공하지 않았다.

<a id="clean-other-q04"></a>

#### 질문 04 · 제3자 배대지와 구매자 보호

**질문 원문**

> 미국 eBay에서 중고 카메라를 구매해 제가 선택한 미국 배대지로 보낸 뒤 한국에서 받았습니다. 배대지에는 배송 완료됐지만 한국에서 개봉하니 상품 설명에 없던 렌즈 파손이 확인됐습니다. 판매자에게 환불을 요청할 때 eBay Money Back Guarantee를 적용받을 수 있나요?

**모델 출력 상태**

`EVIDENCE_SUFFICIENT` · 모델

**실행 결과**

HTTP 200 · EVIDENCE_SUFFICIENT · 반환 조항 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [eBay International Shipping Seller Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/seller/terms/) | — | 0.500551 | `5bfdc014-677b-4c1c-af27-c8e289ecb105` | `UNCERTAIN` |
| 2 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.499626 | `9fe730cd-ed84-4ed4-adf2-543102c93aa5` | `UNCERTAIN` |
| 3 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.491886 | `156f28ea-ff16-498c-be2e-2fc4aef5c3de` | `UNCERTAIN` |
| 4 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.485033 | `06ef1fe4-d9d2-4abf-96e2-43ea2a63f3d1` | `UNCERTAIN` |
| 5 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.482306 | `3cbade8f-4033-4be5-9a7e-ea5047e4fa55` | `UNCERTAIN` |

**모델 응답 원문**

`searchNote`

> 설명에 없던 렌즈 파손은 리스팅과 다른 상품에 해당할 수 있어, 자격 요건을 충족하면 판매자에게 반품·환불 해결을 요청할 근거가 있습니다. 그러나 제3자 배대지 이용은 해당 사유의 보장에서 제외될 수 있고, eBay 프로그램 배송인지·결제 및 요청 기한 요건 충족 여부가 확인되지 않아 환불을 보장할 수는 없습니다.

**관찰**

5위 Money Back Guarantee 청크에서 제3자 배송대행의 상품 불일치 보호 제외와 eBay 공식 프로그램 예외를 모두 찾았다. 응답은 사용자가 선택한 배대지라는 진술을 충분히 반영하지 않고 eIS 가능성을 열어뒀다. 근거 검색의 성공과 해당 근거를 배송 경로에 적용하는 판단의 정확도를 구분할 필요가 있다.

<a id="clean-other-q05"></a>

#### 질문 05 · 국제 반품 라벨과 배송 증빙

**질문 원문**

> 미국 eBay 판매자가 한국으로 직접 배송한 820달러짜리 기기가 작동하지 않아 반품을 접수했습니다. 판매자는 반품을 승인했지만, 접수 후 영업일 4일이 지나도 한국에서 쓸 수 있는 라벨을 주지 않았습니다. 제가 먼저 국제 반송비를 결제하면 돌려받을 수 있나요? eBay 개입은 언제 요청할 수 있고, 반송 시 어떤 배송 증빙이 필요한가요?

**모델 출력 상태**

`EVIDENCE_SUFFICIENT` · 모델

**실행 결과**

HTTP 200 · EVIDENCE_SUFFICIENT · 반환 조항 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.548385 | `010641a4-7898-48ce-b397-703242dfb7e6` | `UNCERTAIN` |
| 2 | [Return shipping for buyers](https://www.ebay.com/help/buying/returns-refunds/returning-item/return-shipping?id=4066) | — | 0.544070 | `994c0415-1ca9-4f46-b4c0-cd6de32cb0ba` | `APPLICABLE` |
| 3 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.543908 | `545eb687-aded-4011-ba2f-0c5f55c10bc6` | `APPLICABLE` |
| 4 | [Return shipping for buyers](https://www.ebay.com/help/buying/returns-refunds/returning-item/return-shipping?id=4066) | — | 0.543133 | `9b595379-57c6-41d3-a6c0-45ac0047da77` | `APPLICABLE` |
| 5 | [eBay International Shipping Seller Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/seller/terms/) | — | 0.540261 | `5bfdc014-677b-4c1c-af27-c8e289ecb105` | `NOT_APPLICABLE` |

**모델 응답 원문**

`searchNote`

> 판매자 부담 반품이라면 반송비를 판매자가 부담해야 하며, 승인 후 라벨을 주지 않은 경우 영업일 3일 이후 eBay 개입을 요청할 수 있고, 820달러 상품은 추적 및 서명 확인 배송을 사용해야 한다는 점은 근거가 있습니다. 다만 국제 반송비를 먼저 결제하면 상환된다는 보장은 확인되지 않았으므로, 반송 전에 판매자와 운송 방식·비용을 합의하거나 eBay에 개입을 요청하는 편이 안전합니다.

**관찰**

2~4위에서 판매자의 반송 수단 제공, 라벨 미제공 시 eBay 개입, 대체 배송 비용 사전 합의, 750달러 이상 추적·서명 확인 근거를 찾았다. 선결제한 국제 운임의 자동 상환은 확인되지 않았다. 1·5위 eIS 약관은 판매자 직접 배송 조건에 맞지 않았다.

<a id="clean-350"></a>

## 정제 HTML 350/200 재실험 · 2026-11-27

### 질문별 결과 요약

| 질문 | 주제 | 모델 출력 상태 | 반환 수 | 상태 출처 |
|---:|---|---|---:|---|
| [01](#clean-350-q01) | 판매자 직접 배송 신발 오배송 | `EVIDENCE_INSUFFICIENT` | 5 | 모델 |
| [02](#clean-350-q02) | 단순 변심과 사이즈 교환 | `EVIDENCE_INSUFFICIENT` | 5 | 모델 |
| [03](#clean-350-q03) | eIS 구성품 누락 | `EVIDENCE_INSUFFICIENT` | 5 | 모델 |
| [04](#clean-350-q04) | 제3자 배대지와 구매자 보호 | `EVIDENCE_INSUFFICIENT` | 5 | 모델 |
| [05](#clean-350-q05) | 국제 반품 라벨과 배송 증빙 | `EVIDENCE_INSUFFICIENT` | 5 | 모델 |

**집계** · 전체 5건 · 모델 충분 0건 · 부족 5건 · 정책 미검색 0건 · 반환 결과 25개

**평가** · 오배송의 반품 접수 기한은 여전히 상위 5개에 없었다. 배대지 적용 제외·예외와 국제 반송의 서명 요건도 상위 5개에서 빠졌다.


### 실행 조건

| 변경 항목 | 이번 실험 |
|---|---|
| 실행 경로 | 개발 로그인 오류로 Spring 컨텍스트에서 `PolicyRagService.reviewPolicy` 직접 호출 |
| 청킹 | 350/200으로 재적재, 청크 186개 |
| 질문 세트 | 정제 HTML 검증의 해외직구 질문 5개 원문 |

이전 정제 HTML 검증 당시의 적재 설정 기록이 없어 관찰된 차이를 청크 크기만의 효과로 판단하기 어렵다.

### 관찰

다섯 질의 모두 `EVIDENCE_INSUFFICIENT`였다. 이전 실험의 1·2번은 같은 상태였고 3~5번은 `EVIDENCE_SUFFICIENT`에서 바뀌었다. 오배송의 반품 접수 기한은 여전히 상위 5개에 없었고, 배대지의 상품 불일치 보호 제외·예외와 국제 반송의 서명 확인 요건은 상위 5개에서 빠졌다.

### 질문별 결과

### 질문별 결과

<a id="clean-350-q01"></a>

#### 질문 01 · 판매자 직접 배송 신발 오배송

**질문 원문**

> 미국 eBay 판매자가 직접 배송한 신발을 한국에서 받았습니다. 주문은 US 9였지만 상자와 신발 모두 US 8입니다. 수령 12일째이고 판매자는 상품 페이지에 ‘반품 불가’라고 적었습니다. 교환을 요청할 수 있나요? 판매자가 교환을 거절하면 전액 환불과 최초·반품 배송비는 어떻게 처리되나요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 12199ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | — | 0.499796 | `7b80f97b-5df0-4bed-8dec-66e63608035f` | APPLICABLE |
| 2 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.474651 | `48be591a-4fc1-4186-8073-faf38225b015` | UNCERTAIN |
| 3 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.473454 | `cb7c4afa-c5e8-4fdf-b76a-db78df6d9874` | 검토 누락 |
| 4 | [eBay Authenticity Guarantee for Sneakers](https://www.ebay.com/authenticity-guarantee/sneakers) | — | 0.461258 | `61310e54-dc7a-4575-ac79-f4ce175cf859` | NOT_APPLICABLE |
| 5 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | — | 0.457800 | `59978e03-7755-435d-a263-13514c4f04e9` | UNCERTAIN |

**모델 응답 원문**

`searchNote`

> 주문한 US 9 대신 US 8을 받았다는 사실을 입증하면 상품이 listing과 다르다는 사유로 반품을 요청할 근거가 있고, 정책상 반품이 받아들여지면 원래 배송비 포함 전액 환불과 판매자 부담 반품 배송이 안내됩니다. 교환은 판매자가 제공할 수 있는 선택지일 뿐 보장되지 않으며, 사이즈 불일치 증빙과 EIS 이용 여부가 확인되지 않아 교환 의무나 국제배송·수입 비용 환급을 단정할 수 없습니다.

**관찰**

이전과 같이 반품 접수 기한을 뒷받침하는 Money Back Guarantee 청크는 상위 5개에 없었다. 전액 환불과 배송비 부담 근거는 이전과 같은 게스트 구매 안내가 1위였으며, 게스트 결제 여부는 질의에 없었다. 2위 eIS 약관과 4위 스니커즈 인증 안내도 거래 조건에서 적용이 확인되지 않았다.

<a id="clean-350-q02"></a>

#### 질문 02 · 단순 변심과 사이즈 교환

**질문 원문**

> 미국 eBay 판매자가 한국으로 직접 배송한 재킷을 수령한 지 8일 됐습니다. 주문한 사이즈와 설명은 정확하지만 입어보니 작습니다. 상품 페이지에 ‘30일 반품 가능, 반품 배송비 구매자 부담’이라고 적혀 있습니다. 다른 사이즈로 교환해 달라고 할 수 있나요? 반품하면 최초 배송비도 돌려받나요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 10379ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [eBay Guaranteed Fit](https://pages.ebay.com/motors/ebay-guaranteed-fit/) | — | 0.466290 | `6f6e54f4-1332-4e86-9d6d-77b345eafc16` | NOT_APPLICABLE |
| 2 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | — | 0.461308 | `7b80f97b-5df0-4bed-8dec-66e63608035f` | APPLICABLE |
| 3 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.452265 | `cb7c4afa-c5e8-4fdf-b76a-db78df6d9874` | 검토 누락 |
| 4 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.446401 | `e9d02b20-0f66-4674-8457-e9c6c2e51d9a` | APPLICABLE |
| 5 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.445548 | `e865caaa-2e7f-4b07-a6af-2d4574c4511d` | UNCERTAIN |

**모델 응답 원문**

`searchNote`

> 30일 반품 가능이라는 판매자 안내와 EIS가 적용되는 경우 배송일 기준 30일 내 마음 변경성 반품을 요청하고 라벨 비용을 구매자가 부담한다는 정책은 반품 요구의 근거가 될 수 있습니다. 다만 사이즈 교환은 판매자가 제안할 수 있을 뿐 보장되지 않으며, 단순히 작다는 이유로 최초 배송비를 돌려받는다는 근거는 검색된 청크에 없습니다.

**관찰**

최초 배송비 환급 근거는 이전과 같이 상위 5개에서 확보되지 않았다. 1위는 이전의 상품 불일치 반품 안내에서 자동차 부품용 Guaranteed Fit 청크로 바뀌었고, 모델도 적용 불가로 판단했다. 5위 eIS 약관은 이전에도 검색됐으며 판매자 직접 배송 사례에 적용할 근거가 없다.

<a id="clean-350-q03"></a>

#### 질문 03 · eIS 구성품 누락

**질문 원문**

> 주문 상세에 eBay International Shipping으로 표시된 미국발 상품을 한국에서 받았는데 구성품이 빠져 있습니다. 수령 18일째이고 판매자는 교체품을 직접 보내줄 수 없다고 합니다. 누구에게 반품이나 환불을 요청해야 하며, 교체품 발송도 요구할 수 있나요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 7973ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.491335 | `48be591a-4fc1-4186-8073-faf38225b015` | UNCERTAIN |
| 2 | [eBay International Shipping Seller Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/seller/terms/) | — | 0.489814 | `01edd991-d052-4882-98e8-4476753c363e` | NOT_APPLICABLE |
| 3 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.489626 | `e865caaa-2e7f-4b07-a6af-2d4574c4511d` | APPLICABLE |
| 4 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | — | 0.474093 | `7b80f97b-5df0-4bed-8dec-66e63608035f` | UNCERTAIN |
| 5 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | — | 0.473982 | `f0310982-281e-4a06-8a61-4c19fb623096` | UNCERTAIN |

**모델 응답 원문**

`searchNote`

> 반품·환불 요청은 판매자에게 직접 처리받기보다 eBay의 EIS 국제 반품 절차로 접수할 근거가 있고, 실제 배송일부터 30일이므로 수령 18일째가 맞다면 아직 요청할 수 있습니다. 누락이 세트·번들의 구성품이면 반품 요청이 안내되며, 교체·교환은 판매자가 제안할 수 있을 뿐 구매자가 발송을 강제할 권리나 환불 승인이 보장되지는 않습니다.

**관찰**

eIS 구매자 약관이 이전 2·3위에서 1·3위로 바뀌었다. 3위에서 실제 배송일 기준 30일 접수, eBay 처리 및 라벨 제공을 확인했고 1위는 검수·승인 후 환불을 다룬다. 응답은 핵심 절차를 유지했지만 상태는 SUFFICIENT에서 INSUFFICIENT로 바뀌었다.

<a id="clean-350-q04"></a>

#### 질문 04 · 제3자 배대지와 구매자 보호

**질문 원문**

> 미국 eBay에서 중고 카메라를 구매해 제가 선택한 미국 배대지로 보낸 뒤 한국에서 받았습니다. 배대지에는 배송 완료됐지만 한국에서 개봉하니 상품 설명에 없던 렌즈 파손이 확인됐습니다. 판매자에게 환불을 요청할 때 eBay Money Back Guarantee를 적용받을 수 있나요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 8637ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [eBay International Shipping Seller Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/seller/terms/) | — | 0.512509 | `01edd991-d052-4882-98e8-4476753c363e` | UNCERTAIN |
| 2 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.506709 | `7543569d-a0ee-4bd9-924d-4b248ee834bc` | UNCERTAIN |
| 3 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.497802 | `72bbcc36-a850-4769-b1a5-8b5b97a1929b` | UNCERTAIN |
| 4 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.495506 | `d66fe5a9-8e98-460b-a8d0-adb374a52dde` | UNCERTAIN |
| 5 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.489746 | `fc4728f7-6894-4a25-96d6-ee4eb07ec981` | UNCERTAIN |

**모델 응답 원문**

`searchNote`

> 상품 설명에 없던 렌즈 파손은 ‘상품이 설명과 다름’에 해당할 수 있으므로, 자격 요건을 충족하면 eBay 절차로 문제를 제기할 근거가 있습니다. 다만 배대지 이용만으로 보호 여부가 결정되지는 않으며 결제·신고 기한·배송 경로와 손상 발생 시점이 확인되지 않아 환불 결과는 보장할 수 없고, 판매자에게 요청하는 것과 eBay가 환불을 결정하는 것도 구분해야 합니다.

**관찰**

이전 5위에서 상품 불일치 청구에 대한 제3자 배송대행 제외와 eBay 공식 프로그램 예외를 함께 찾았다. 이번 4위는 미수령 청구에 대한 배송대행 제외를 다뤘고, 상품 불일치에 해당하는 제외·예외 조항은 상위 5개에 없었다. 상태는 SUFFICIENT에서 INSUFFICIENT로 바뀌었다.

<a id="clean-350-q05"></a>

#### 질문 05 · 국제 반품 라벨과 배송 증빙

**질문 원문**

> 미국 eBay 판매자가 한국으로 직접 배송한 820달러짜리 기기가 작동하지 않아 반품을 접수했습니다. 판매자는 반품을 승인했지만, 접수 후 영업일 4일이 지나도 한국에서 쓸 수 있는 라벨을 주지 않았습니다. 제가 먼저 국제 반송비를 결제하면 돌려받을 수 있나요? eBay 개입은 언제 요청할 수 있고, 반송 시 어떤 배송 증빙이 필요한가요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 8301ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.569165 | `cb7c4afa-c5e8-4fdf-b76a-db78df6d9874` | APPLICABLE |
| 2 | [eBay International Shipping Seller Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/seller/terms/) | — | 0.560118 | `01edd991-d052-4882-98e8-4476753c363e` | UNCERTAIN |
| 3 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.557398 | `e865caaa-2e7f-4b07-a6af-2d4574c4511d` | UNCERTAIN |
| 4 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.547154 | `48be591a-4fc1-4186-8073-faf38225b015` | UNCERTAIN |
| 5 | [Return shipping for buyers](https://www.ebay.com/help/buying/returns-refunds/returning-item/return-shipping?id=4066) | — | 0.547034 | `832f6fcf-7711-4512-91f1-9574772a6850` | UNCERTAIN |

**모델 응답 원문**

`searchNote`

> 판매자가 반품을 승인하고도 라벨을 보내지 않았다면 eBay에 개입을 요청할 수 있다는 근거가 있으며, 판매자가 비용 부담인 경우 자기 운송사를 이용해 라벨을 업로드하고 추적 가능한 서비스를 쓰는 방안도 안내되어 있습니다. 다만 국제 반송비를 먼저 내면 돌려받는다는 보장은 확인되지 않았고, EIS 여부·배송비 부담 주체와 국제 반송 증빙의 구체 요건은 추가 확인이 필요합니다.

**관찰**

라벨 미제공 시 eBay 개입 근거가 1위로 올라왔다. 반면 eIS 약관이 상위 5개 중 3개를 차지했고, 이전에 찾은 750달러 이상 반송의 서명 확인 요건과 대체 배송 비용 사전 합의 근거가 상위 5개에서 빠졌다. 상태는 SUFFICIENT에서 INSUFFICIENT로 바뀌었다.

<a id="clean-450"></a>

## 정제 HTML 450/200 재실험 · 2026-11-27

### 질문별 결과 요약

| 질문 | 주제 | 모델 출력 상태 | 반환 수 | 상태 출처 |
|---:|---|---|---:|---|
| [01](#clean-450-q01) | 판매자 직접 배송 신발 오배송 | `EVIDENCE_INSUFFICIENT` | 5 | 모델 |
| [02](#clean-450-q02) | 단순 변심과 사이즈 교환 | `EVIDENCE_INSUFFICIENT` | 5 | 모델 |
| [03](#clean-450-q03) | eIS 구성품 누락 | `EVIDENCE_SUFFICIENT` | 5 | 모델 |
| [04](#clean-450-q04) | 제3자 배대지와 구매자 보호 | `EVIDENCE_SUFFICIENT` | 5 | 모델 |
| [05](#clean-450-q05) | 국제 반품 라벨과 배송 증빙 | `EVIDENCE_SUFFICIENT` | 5 | 모델 |

**집계** · 전체 5건 · 모델 충분 3건 · 부족 2건 · 정책 미검색 0건 · 반환 결과 25개

**평가** · 배대지 적용 제외·예외와 국제 반송의 추적·서명 요건을 다시 찾았다. 오배송의 반품 접수 기한과 단순 변심의 최초 배송비 조항은 여전히 빠졌다.


### 실행 조건

| 변경 항목 | 이번 실험 |
|---|---|
| 청킹 | 450/200으로 재적재, 청크 146개 |
| 인덱스 확인 | 350/200 인덱스 백업. 이전 정제 HTML 백업과 출처 URL·본문 146개 일치 |

### 관찰

| 질의 | 350/200 | 450/200 | 검색 근거 비교 |
|---|---|---|---|
| 신발 오배송 | INSUFFICIENT | INSUFFICIENT | 반품 접수 기한 미회수. 게스트 안내가 여전히 1위이며 eIS·스니커즈 인증 안내는 상위 5개에서 빠짐. |
| 단순 변심 | INSUFFICIENT | INSUFFICIENT | 최초 배송비 정책 미회수. 자동차 부품 정책과 eIS 약관은 빠졌으나 1위 일반 반품 청크도 모델이 적용 불가로 평가함. |
| eIS 구성품 누락 | INSUFFICIENT | SUFFICIENT | 양쪽 모두 30일 기한과 eBay 처리 근거를 회수. 450/200에서 이 근거가 3위에서 2위로 이동함. |
| 제3자 배대지 | INSUFFICIENT | SUFFICIENT | 상품 불일치 보호에서 제3자 배송대행 제외와 공식 프로그램 예외를 5위에서 다시 회수함. |
| 국제 반품 라벨 | INSUFFICIENT | SUFFICIENT | 라벨 미제공 시 개입 근거가 1위에 유지됐고, 추적·서명 요건과 비용 사전 합의 근거를 다시 회수함. |

### 질문별 결과

### 질문별 결과

<a id="clean-450-q01"></a>

#### 질문 01 · 판매자 직접 배송 신발 오배송

**질문 원문**

> 미국 eBay 판매자가 직접 배송한 신발을 한국에서 받았습니다. 주문은 US 9였지만 상자와 신발 모두 US 8입니다. 수령 12일째이고 판매자는 상품 페이지에 ‘반품 불가’라고 적었습니다. 교환을 요청할 수 있나요? 판매자가 교환을 거절하면 전액 환불과 최초·반품 배송비는 어떻게 처리되나요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 10921ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | — | 0.485444 | `981a3c6a-7df9-48eb-999d-d84f9ef193fc` | APPLICABLE |
| 2 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.473828 | `c9325879-31ab-415c-8298-75e4359000ee` | APPLICABLE |
| 3 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.470697 | `a84a0fa4-bd6c-49f7-9778-105a99fa75d4` | APPLICABLE |
| 4 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | — | 0.466906 | `d2193020-5fba-42e8-beb7-cc24803c756f` | UNCERTAIN |
| 5 | [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115) | — | 0.464719 | `c169ac92-d2ef-44a7-b4c6-597ce8cde0ba` | UNCERTAIN |

**모델 응답 원문**

`searchNote`

> 주문은 US 9인데 수령품이 US 8이라는 사실이 증빙되면 ‘리스팅과 다름’을 근거로 반품 불가 표기에도 반환을 요청할 수 있습니다. 교환은 판매자가 제안할 수 있을 뿐 보장되지 않으며, 반환이 전액 환불로 처리되면 원래 배송비 포함 및 판매자 부담 반품 배송비가 안내되어 있지만, 교환 거절만으로 전액 환불이 자동 확정되는지와 실제 국제 반품 비용 절차는 검색된 내용만으로 단정할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="clean-450-q02"></a>

#### 질문 02 · 단순 변심과 사이즈 교환

**질문 원문**

> 미국 eBay 판매자가 한국으로 직접 배송한 재킷을 수령한 지 8일 됐습니다. 주문한 사이즈와 설명은 정확하지만 입어보니 작습니다. 상품 페이지에 ‘30일 반품 가능, 반품 배송비 구매자 부담’이라고 적혀 있습니다. 다른 사이즈로 교환해 달라고 할 수 있나요? 반품하면 최초 배송비도 돌려받나요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 8967ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.475635 | `c9325879-31ab-415c-8298-75e4359000ee` | NOT_APPLICABLE |
| 2 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | — | 0.454265 | `d2193020-5fba-42e8-beb7-cc24803c756f` | APPLICABLE |
| 3 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | — | 0.452398 | `981a3c6a-7df9-48eb-999d-d84f9ef193fc` | APPLICABLE |
| 4 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.445989 | `a84a0fa4-bd6c-49f7-9778-105a99fa75d4` | UNCERTAIN |
| 5 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.438915 | `4251873c-b636-4e78-9232-22ff508d6f05` | NOT_APPLICABLE |

**모델 응답 원문**

`searchNote`

> 상품 페이지에 30일 반품 가능이라고 적혀 있으므로 해당 조건에 따라 반품을 요청할 근거는 있습니다. 다만 교환은 판매자가 제안할 수 있을 뿐 보장되지 않고, 사이즈가 맞지 않는 단순 변심 반품에서 최초 배송비를 환급하는지는 검색된 정책이 확인해 주지 않습니다.

**관찰**

별도 관찰 기록 없음

<a id="clean-450-q03"></a>

#### 질문 03 · eIS 구성품 누락

**질문 원문**

> 주문 상세에 eBay International Shipping으로 표시된 미국발 상품을 한국에서 받았는데 구성품이 빠져 있습니다. 수령 18일째이고 판매자는 교체품을 직접 보내줄 수 없다고 합니다. 누구에게 반품이나 환불을 요청해야 하며, 교체품 발송도 요구할 수 있나요?

**모델 출력 상태**

`EVIDENCE_SUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_SUFFICIENT · 반환 청크 5개 · 서비스 호출 7880ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | — | 0.487956 | `d2193020-5fba-42e8-beb7-cc24803c756f` | UNCERTAIN |
| 2 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.482835 | `3d3ae2c3-5854-4f55-be13-63a0557a428f` | APPLICABLE |
| 3 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.482810 | `835f76b7-3ab8-408e-b906-346ba236d13a` | UNCERTAIN |
| 4 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.475530 | `a84a0fa4-bd6c-49f7-9778-105a99fa75d4` | UNCERTAIN |
| 5 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.472191 | `c9325879-31ab-415c-8298-75e4359000ee` | UNCERTAIN |

**모델 응답 원문**

`searchNote`

> EIS 국제 반품은 실제 배송일부터 30일 안에 신청할 수 있고 eBay가 처리하므로, 수령 18일째라면 eBay에 반품 요청을 하는 근거가 있습니다. 구성품 누락이 목록 설명과 다르거나 세트·번들의 누락이면 반품 요청을 뒷받침하지만, 교체 발송은 판매자가 선택할 수 있는 응답일 뿐 보장되지 않으며 누락 구성품의 성격과 목록 내용은 확인되지 않았습니다.

**관찰**

별도 관찰 기록 없음

<a id="clean-450-q04"></a>

#### 질문 04 · 제3자 배대지와 구매자 보호

**질문 원문**

> 미국 eBay에서 중고 카메라를 구매해 제가 선택한 미국 배대지로 보낸 뒤 한국에서 받았습니다. 배대지에는 배송 완료됐지만 한국에서 개봉하니 상품 설명에 없던 렌즈 파손이 확인됐습니다. 판매자에게 환불을 요청할 때 eBay Money Back Guarantee를 적용받을 수 있나요?

**모델 출력 상태**

`EVIDENCE_SUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_SUFFICIENT · 반환 청크 5개 · 서비스 호출 8304ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [eBay International Shipping Seller Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/seller/terms/) | — | 0.501574 | `7df0fec2-72ba-4cef-8857-29e4e821ac71` | UNCERTAIN |
| 2 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.496686 | `1bb4713a-5b1f-4ac4-98bf-384dce710cd5` | UNCERTAIN |
| 3 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.488324 | `98d98236-684a-4462-bb62-6ca0b33f8550` | UNCERTAIN |
| 4 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.480319 | `9bee7ef4-9956-4370-9ba6-a5db304fd7df` | UNCERTAIN |
| 5 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.478101 | `289cb7f7-8c36-4849-8fdc-b8778b082053` | UNCERTAIN |

**모델 응답 원문**

`searchNote`

> 렌즈 파손이 설명에 없었다면 상품 설명 불일치로 판매자에게 반품·환불을 요청할 근거는 있습니다. 다만 제3자 배대지 사용은 해당 사유의 보장 제외가 될 수 있고, eBay 프로그램 배송 여부와 결제·요청 기한 등 핵심 자격 요건이 확인되지 않아 환불 보장을 단정할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="clean-450-q05"></a>

#### 질문 05 · 국제 반품 라벨과 배송 증빙

**질문 원문**

> 미국 eBay 판매자가 한국으로 직접 배송한 820달러짜리 기기가 작동하지 않아 반품을 접수했습니다. 판매자는 반품을 승인했지만, 접수 후 영업일 4일이 지나도 한국에서 쓸 수 있는 라벨을 주지 않았습니다. 제가 먼저 국제 반송비를 결제하면 돌려받을 수 있나요? eBay 개입은 언제 요청할 수 있고, 반송 시 어떤 배송 증빙이 필요한가요?

**모델 출력 상태**

`EVIDENCE_SUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_SUFFICIENT · 반환 청크 5개 · 서비스 호출 9423ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.546420 | `4251873c-b636-4e78-9232-22ff508d6f05` | APPLICABLE |
| 2 | [Return shipping for buyers](https://www.ebay.com/help/buying/returns-refunds/returning-item/return-shipping?id=4066) | — | 0.544969 | `a0ea401b-15d4-4b4f-abb1-6b1d929108f3` | UNCERTAIN |
| 3 | [Return shipping for buyers](https://www.ebay.com/help/buying/returns-refunds/returning-item/return-shipping?id=4066) | — | 0.544741 | `b66430d7-aa0e-414c-82e7-0096cae6b1b6` | UNCERTAIN |
| 4 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.543333 | `835f76b7-3ab8-408e-b906-346ba236d13a` | NOT_APPLICABLE |
| 5 | [Return shipping for buyers](https://www.ebay.com/help/buying/returns-refunds/returning-item/return-shipping?id=4066) | — | 0.540695 | `7fcb7b2c-62ee-4261-8341-3b40e678266b` | APPLICABLE |

**모델 응답 원문**

`searchNote`

> 판매자에게 작동 불량 반품의 반송비를 부담하고 라벨을 제공해 달라고 요구할 근거가 있으며, 라벨을 주지 않은 상황은 eBay 개입 요청 사유로 명시되어 있습니다. 다만 국제 반송비를 먼저 낸 뒤 상환받는 것은 보장되지 않으므로 발송 전 배송 방법과 비용을 합의해야 하고, $820 반품에는 추적 및 서명 확인 배송이 필요합니다.

**관찰**

별도 관찰 기록 없음

<a id="clean-400"></a>

## 정제 HTML 400/200 재실험 · 2026-11-28

### 질문별 결과 요약

| 질문 | 주제 | 모델 출력 상태 | 반환 수 | 상태 출처 |
|---:|---|---|---:|---|
| [01](#clean-400-q01) | 판매자 직접 배송 신발 오배송 | `EVIDENCE_INSUFFICIENT` | 5 | 모델 |
| [02](#clean-400-q02) | 단순 변심과 사이즈 교환 | `EVIDENCE_INSUFFICIENT` | 5 | 모델 |
| [03](#clean-400-q03) | eIS 구성품 누락 | `EVIDENCE_INSUFFICIENT` | 5 | 모델 |
| [04](#clean-400-q04) | 제3자 배대지와 구매자 보호 | `EVIDENCE_INSUFFICIENT` | 5 | 모델 |
| [05](#clean-400-q05) | 국제 반품 라벨과 배송 증빙 | `EVIDENCE_INSUFFICIENT` | 5 | 모델 |

**집계** · 전체 5건 · 모델 충분 0건 · 부족 5건 · 정책 미검색 0건 · 반환 결과 25개

**평가** · eIS 구성품 누락의 30일 접수 근거가 1위로 올랐다. 배대지 적용 제외·예외와 라벨 미제공 시 개입 근거 및 고액 반송의 서명 요건은 빠졌다.


### 실행 조건

| 변경 항목 | 이번 실험 |
|---|---|
| 청킹 | 400/200으로 재적재, 청크 164개 |
| 인덱스 관리 | 직전 450/200 인덱스 백업 |

### 관찰

다섯 질의 모두 `EVIDENCE_INSUFFICIENT`였다. 이전 450/200의 1·2번은 같은 상태였고, 3~5번은 `EVIDENCE_SUFFICIENT`에서 바뀌었다.

| 질의 | 450/200 대비 검색 근거 |
|---|---|
| 신발 오배송 | 판매자 직접 배송에 적용할 접수 기한을 여전히 찾지 못했다. 최초 배송비 포함 전액 환불 및 판매자 반품 배송비 부담을 함께 설명한 게스트 안내가 상위 5개에서 빠졌고, 대신 EIS 구매자 약관이 2개 들어왔다. |
| 단순 변심 | 최초 배송비 환급 근거를 여전히 찾지 못했다. 판매자 직접 배송 질의의 상위 5개에 EIS 구매자 약관이 2개 들어왔다. |
| eIS 구성품 누락 | 실제 배송일 기준 30일과 eBay 처리 근거가 2위에서 1위로 올랐다. 판매자의 교체·교환 제안을 설명한 일반 반품 안내가 상위 5개에서 빠졌다. |
| 제3자 배대지 | 상품 불일치 보호에 대한 제3자 배송대행 제외와 eBay 공식 프로그램 예외를 담은 청크가 빠졌다. |
| 국제 반품 라벨 | 대체 반송 비용 사전 합의 근거는 5위에서 회수했다. 라벨 미제공 시 개입 근거와 750달러 이상 반송의 서명 확인 요건은 빠졌다. 1위는 작동하지 않는 라벨에 관한 안내였다. |

### 질문별 결과

### 질문별 결과

<a id="clean-400-q01"></a>

#### 질문 01 · 판매자 직접 배송 신발 오배송

**질문 원문**

> 미국 eBay 판매자가 직접 배송한 신발을 한국에서 받았습니다. 주문은 US 9였지만 상자와 신발 모두 US 8입니다. 수령 12일째이고 판매자는 상품 페이지에 ‘반품 불가’라고 적었습니다. 교환을 요청할 수 있나요? 판매자가 교환을 거절하면 전액 환불과 최초·반품 배송비는 어떻게 처리되나요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 14492ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.476811 | `cef400e9-540c-4995-b9b1-5219e6964f09` | UNCERTAIN |
| 2 | [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115) | — | 0.462375 | `958073cc-2eb9-4e71-a460-b121fa82b9d7` | APPLICABLE |
| 3 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.461679 | `349906e7-644c-40a3-ab63-5b6c9cdbaa7e` | UNCERTAIN |
| 4 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.459066 | `3cf95b7b-2823-42b9-bc69-c5f6a9057d95` | UNCERTAIN |
| 5 | [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115) | — | 0.456954 | `15c79ca7-a600-40ff-b436-dfa45f21eb18` | 검토 누락 |

**모델 응답 원문**

`searchNote`

> US EIS 거래라면 배송일로부터 30일 안에 반품을 요청할 수 있어 수령 12일째라는 점과 ‘반품 불가’ 표기는 그 반품 절차 자체를 막는다고 검색된 조항에 나타나지 않습니다. 다만 교환은 판매자가 제안할 수 있는 선택지일 뿐 보장된 권리가 아니며, 오배송 시 전액 환불과 최초·반품 배송비 부담은 거래가 EIS인지 및 배송·수입 비용 조건에 따라 달라질 수 있어 검색 청크만으로 확정할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="clean-400-q02"></a>

#### 질문 02 · 단순 변심과 사이즈 교환

**질문 원문**

> 미국 eBay 판매자가 한국으로 직접 배송한 재킷을 수령한 지 8일 됐습니다. 주문한 사이즈와 설명은 정확하지만 입어보니 작습니다. 상품 페이지에 ‘30일 반품 가능, 반품 배송비 구매자 부담’이라고 적혀 있습니다. 다른 사이즈로 교환해 달라고 할 수 있나요? 반품하면 최초 배송비도 돌려받나요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 11620ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.461908 | `cb102cd1-b3dc-4586-9b25-16e944d76b7d` | NOT_APPLICABLE |
| 2 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.452923 | `cef400e9-540c-4995-b9b1-5219e6964f09` | UNCERTAIN |
| 3 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.446559 | `349906e7-644c-40a3-ab63-5b6c9cdbaa7e` | UNCERTAIN |
| 4 | [International purchases and shipping for buyers](https://www.ebay.com/help/buying/shipping-delivery/international-shipping-ebay?id=4057) | — | 0.446054 | `e6d9e3fa-ca48-415e-8fc8-9f8157805b24` | NOT_APPLICABLE |
| 5 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.440974 | `3cf95b7b-2823-42b9-bc69-c5f6a9057d95` | UNCERTAIN |

**모델 응답 원문**

`searchNote`

> 판매자 페이지의 ‘30일 반품 가능, 반품 배송비 구매자 부담’ 조건에 따라 변심 반품을 요청할 근거는 있지만, 이 정책 자료만으로 다른 사이즈 교환을 요구할 권리가 보장된다고 볼 수는 없습니다. 최초 배송비 환불 여부도 확인되지 않았으며, EIS라면 안내된 변심 반품 라벨 비용은 구매자 부담이고 일부 국제 비용은 환불되지 않을 수 있습니다.

**관찰**

별도 관찰 기록 없음

<a id="clean-400-q03"></a>

#### 질문 03 · eIS 구성품 누락

**질문 원문**

> 주문 상세에 eBay International Shipping으로 표시된 미국발 상품을 한국에서 받았는데 구성품이 빠져 있습니다. 수령 18일째이고 판매자는 교체품을 직접 보내줄 수 없다고 합니다. 누구에게 반품이나 환불을 요청해야 하며, 교체품 발송도 요구할 수 있나요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 7271ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.488561 | `3cf95b7b-2823-42b9-bc69-c5f6a9057d95` | UNCERTAIN |
| 2 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.487450 | `cef400e9-540c-4995-b9b1-5219e6964f09` | UNCERTAIN |
| 3 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.479702 | `d7e31859-c94c-4c8f-a294-914c06c919f9` | APPLICABLE |
| 4 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.479210 | `120db792-5f96-4b7d-8b9b-63640974bbac` | NOT_APPLICABLE |
| 5 | [International purchases and shipping for buyers](https://www.ebay.com/help/buying/shipping-delivery/international-shipping-ebay?id=4057) | — | 0.478876 | `e6d9e3fa-ca48-415e-8fc8-9f8157805b24` | NOT_APPLICABLE |

**모델 응답 원문**

`searchNote`

> 반품은 실제 배송일부터 30일 안에 요청할 수 있고, EIS 국제 반품은 eBay가 처리하므로 판매자에게 직접 반품·환불을 요구하기보다 eBay 반품 절차를 이용하는 것이 근거에 맞습니다. 구성품이 설명과 다르면 Money Back Guarantee 대상이 될 수 있지만 환불은 심사·승인 대상이며, 검색된 정책은 교체품 발송을 보장하거나 판매자에게 의무화하지 않습니다.

**관찰**

별도 관찰 기록 없음

<a id="clean-400-q04"></a>

#### 질문 04 · 제3자 배대지와 구매자 보호

**질문 원문**

> 미국 eBay에서 중고 카메라를 구매해 제가 선택한 미국 배대지로 보낸 뒤 한국에서 받았습니다. 배대지에는 배송 완료됐지만 한국에서 개봉하니 상품 설명에 없던 렌즈 파손이 확인됐습니다. 판매자에게 환불을 요청할 때 eBay Money Back Guarantee를 적용받을 수 있나요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 7926ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.496414 | `38d27e15-4490-47cc-a476-f1c5caebcd2c` | UNCERTAIN |
| 2 | [eBay Guaranteed Fit](https://pages.ebay.com/motors/ebay-guaranteed-fit/) | — | 0.489808 | `339cd14a-2db7-4558-bd4e-0bc98148a318` | NOT_APPLICABLE |
| 3 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.486840 | `120db792-5f96-4b7d-8b9b-63640974bbac` | UNCERTAIN |
| 4 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.485908 | `6a188e93-aa69-450c-8998-c5f3ada2ed1f` | UNCERTAIN |
| 5 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.483613 | `9190639d-2eb6-4847-bfbc-177ba801a6bc` | NOT_APPLICABLE |

**모델 응답 원문**

`searchNote`

> 판매자에게 렌즈 파손이 목록에 없었고 상품이 설명과 다르다는 이유로 eBay 환불 절차에 따라 문제를 신고·해결해 달라고 요구할 정책 근거는 있습니다. 다만 적격 eBay 결제와 신고 기한 등 요건, 파손이 배대지 수령 전에 있었는지 및 적용되는 배대지 관련 예외는 확인되지 않아 환불을 단정할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="clean-400-q05"></a>

#### 질문 05 · 국제 반품 라벨과 배송 증빙

**질문 원문**

> 미국 eBay 판매자가 한국으로 직접 배송한 820달러짜리 기기가 작동하지 않아 반품을 접수했습니다. 판매자는 반품을 승인했지만, 접수 후 영업일 4일이 지나도 한국에서 쓸 수 있는 라벨을 주지 않았습니다. 제가 먼저 국제 반송비를 결제하면 돌려받을 수 있나요? eBay 개입은 언제 요청할 수 있고, 반송 시 어떤 배송 증빙이 필요한가요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 8377ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.591464 | `cb102cd1-b3dc-4586-9b25-16e944d76b7d` | APPLICABLE |
| 2 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.567179 | `cef400e9-540c-4995-b9b1-5219e6964f09` | UNCERTAIN |
| 3 | [International purchases and shipping for buyers](https://www.ebay.com/help/buying/shipping-delivery/international-shipping-ebay?id=4057) | — | 0.551171 | `e6d9e3fa-ca48-415e-8fc8-9f8157805b24` | APPLICABLE |
| 4 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.548914 | `3cf95b7b-2823-42b9-bc69-c5f6a9057d95` | UNCERTAIN |
| 5 | [Return shipping for buyers](https://www.ebay.com/help/buying/returns-refunds/returning-item/return-shipping?id=4066) | — | 0.548628 | `872f13ba-83a3-4cfc-a9e1-cfdf6cbf7bc0` | APPLICABLE |

**모델 응답 원문**

`searchNote`

> 작동하지 않는 기기는 고장 반품 대상일 수 있고, 라벨이 작동하지 않으면 eBay에 개입을 요청해 쓸 수 있는 라벨을 도움받을 수 있습니다. 그러나 판매자가 승인 후 4영업일 내 라벨을 주지 않았을 때의 구체적 개입 가능 시점이나 국제 반송비를 먼저 결제하면 돌려받는다는 보장은 검색된 정책에 없으며, EIS 여부와 비용 부담·대체 배송 합의도 확인해야 합니다.

**관찰**

별도 관찰 기록 없음

<a id="clean-500"></a>

## 정제 HTML 500/200 재실험 · 2026-11-28

### 질문별 결과 요약

| 질문 | 주제 | 모델 출력 상태 | 반환 수 | 상태 출처 |
|---:|---|---|---:|---|
| [01](#clean-500-q01) | 판매자 직접 배송 신발 오배송 | `EVIDENCE_SUFFICIENT` | 5 | 모델 |
| [02](#clean-500-q02) | 단순 변심과 사이즈 교환 | `EVIDENCE_INSUFFICIENT` | 5 | 모델 |
| [03](#clean-500-q03) | eIS 구성품 누락 | `EVIDENCE_SUFFICIENT` | 5 | 모델 |
| [04](#clean-500-q04) | 제3자 배대지와 구매자 보호 | `EVIDENCE_SUFFICIENT` | 5 | 모델 |
| [05](#clean-500-q05) | 국제 반품 라벨과 배송 증빙 | `EVIDENCE_SUFFICIENT` | 5 | 모델 |

**집계** · 전체 5건 · 모델 충분 4건 · 부족 1건 · 정책 미검색 0건 · 반환 결과 25개

**평가** · 오배송의 전액 환불과 판매자 반송비 부담을 확인했다. 신발 오배송과 제3자 배대지는 충분 판정에도 각각 접수 기한과 배대지 예외 조항이 빠졌다.


### 실행 조건

| 변경 항목 | 이번 실험 |
|---|---|
| 청킹 | 500/200으로 재적재, 청크 133개 |
| 인덱스 관리 | 직전 400/200 인덱스 백업 |

### 관찰

모델의 `EVIDENCE_SUFFICIENT` 판정은 500/200에서 4건, 450/200에서 3건이었다. 신발 오배송과 제3자 배대지에서는 충분 판정에도 질문에 필요한 일부 조항이 상위 5개에 없었다.

| 질의 | 450/200 | 500/200 | 검색 근거 비교 |
|---|---|---|---|
| 신발 오배송 | INSUFFICIENT | SUFFICIENT | 판매자 반품 안내 3위에서 반품 불가 표기에도 오배송품을 반품할 수 있고 전액 환불에 최초 배송비가 포함되며 반송비는 판매자가 부담한다는 근거를 찾았다. 반품 접수 기한은 두 설정 모두 상위 5개에 없었다. |
| 단순 변심 | INSUFFICIENT | INSUFFICIENT | Money Back Guarantee가 4·5위에 들어왔다. 5위에서 판매자 반품 정책과 구매자 부담 반송비를 뒷받침했으나 최초 배송비 환급 근거는 여전히 없었다. |
| eIS 구성품 누락 | SUFFICIENT | SUFFICIENT | 실제 배송일부터 30일 이내 접수 및 eBay 처리 근거가 2위에서 1위로 이동했다. 3위에서 교체품은 합의 가능한 대안임을 확인했다. |
| 제3자 배대지 | SUFFICIENT | SUFFICIENT | 상품 불일치에 대한 일반 반품 접수 기한은 4위에서 찾았다. 450/200의 5위에서 회수했던 제3자 배송대행 제외와 공식 프로그램 예외를 담은 조항은 이번 상위 5개에서 빠졌다. |
| 국제 반품 라벨 | SUFFICIENT | SUFFICIENT | 라벨 미제공 시 개입 근거가 1위에서 4위로 내려갔다. 2위의 판매자 반송비 부담과 추적·서명 요건은 유지됐다. 450/200에서 찾은 대체 반송 서비스 및 비용의 사전 합의 문구는 상위 5개에 없었다. |

### 질문별 결과

### 질문별 결과

<a id="clean-500-q01"></a>

#### 질문 01 · 판매자 직접 배송 신발 오배송

**질문 원문**

> 미국 eBay 판매자가 직접 배송한 신발을 한국에서 받았습니다. 주문은 US 9였지만 상자와 신발 모두 US 8입니다. 수령 12일째이고 판매자는 상품 페이지에 ‘반품 불가’라고 적었습니다. 교환을 요청할 수 있나요? 판매자가 교환을 거절하면 전액 환불과 최초·반품 배송비는 어떻게 처리되나요?

**모델 출력 상태**

`EVIDENCE_SUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_SUFFICIENT · 반환 청크 5개 · 서비스 호출 10442ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.479966 | `504fcdd8-5ab7-4972-acaf-7c763d5ba9c2` | APPLICABLE |
| 2 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | — | 0.470295 | `84f62e4d-194f-4eb5-b98a-82c43ea6b666` | APPLICABLE |
| 3 | [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115) | — | 0.461377 | `7be642f8-ad44-4a49-9c95-7445840a4316` | APPLICABLE |
| 4 | [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115) | — | 0.461166 | `57834d49-6e6b-4433-ae42-28c686ba2120` | NOT_APPLICABLE |
| 5 | [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115) | — | 0.458304 | `3aeabc42-0cec-41f5-a6b8-34e2e0b05ae7` | APPLICABLE |

**모델 응답 원문**

`searchNote`

> 주문이 US 9이고 실제 상품이 US 8이라는 불일치를 입증할 수 있다면 ‘상품 설명과 불일치’ 사유로 반품을 요청할 근거가 있으며, ‘반품 불가’ 표시는 이를 막지 않는다고 정책에 나와 있습니다. 이 경우 전액 환불에는 최초 배송비가 포함되고 판매자가 반품 배송비를 부담하지만, 교환은 판매자가 제안할 수 있는 선택지일 뿐 보장되지 않으며 실제 불일치 증빙과 상품 상태는 확인되지 않았습니다.

**관찰**

별도 관찰 기록 없음

<a id="clean-500-q02"></a>

#### 질문 02 · 단순 변심과 사이즈 교환

**질문 원문**

> 미국 eBay 판매자가 한국으로 직접 배송한 재킷을 수령한 지 8일 됐습니다. 주문한 사이즈와 설명은 정확하지만 입어보니 작습니다. 상품 페이지에 ‘30일 반품 가능, 반품 배송비 구매자 부담’이라고 적혀 있습니다. 다른 사이즈로 교환해 달라고 할 수 있나요? 반품하면 최초 배송비도 돌려받나요?

**모델 출력 상태**

`EVIDENCE_INSUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 10495ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.458778 | `504fcdd8-5ab7-4972-acaf-7c763d5ba9c2` | APPLICABLE |
| 2 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.451710 | `434a0b50-8322-47af-b17b-c822e841149f` | APPLICABLE |
| 3 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | — | 0.444510 | `84f62e4d-194f-4eb5-b98a-82c43ea6b666` | UNCERTAIN |
| 4 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.443247 | `5a4aa44e-d3b1-400c-9b05-55fe482421ca` | APPLICABLE |
| 5 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.442290 | `3ba007d6-ca80-4362-9bc5-eecb708460f8` | APPLICABLE |

**모델 응답 원문**

`searchNote`

> 30일 반품 조건에 따라 수령 8일째인 단순 변심 반품은 요청할 근거가 있고, 반품 배송비는 표시된 대로 구매자 부담으로 보입니다. 다른 사이즈 교환은 판매자가 제안할 수 있을 뿐 보장되지 않으며, 최초 배송비 환급은 설명 불일치·불량·파손일 때만 명시되어 있어 이 경우에는 확인된 근거가 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="clean-500-q03"></a>

#### 질문 03 · eIS 구성품 누락

**질문 원문**

> 주문 상세에 eBay International Shipping으로 표시된 미국발 상품을 한국에서 받았는데 구성품이 빠져 있습니다. 수령 18일째이고 판매자는 교체품을 직접 보내줄 수 없다고 합니다. 누구에게 반품이나 환불을 요청해야 하며, 교체품 발송도 요구할 수 있나요?

**모델 출력 상태**

`EVIDENCE_SUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_SUFFICIENT · 반환 청크 5개 · 서비스 호출 8233ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.484339 | `a1b9dab3-dab6-4a1a-b48e-0f2416ecbe12` | APPLICABLE |
| 2 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.480975 | `504fcdd8-5ab7-4972-acaf-7c763d5ba9c2` | UNCERTAIN |
| 3 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.480746 | `5a4aa44e-d3b1-400c-9b05-55fe482421ca` | UNCERTAIN |
| 4 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.477421 | `c58ab21a-6401-421e-b64c-21ec03c34e7c` | UNCERTAIN |
| 5 | [eBay International Shipping Seller Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/seller/terms/) | — | 0.469910 | `7d04590e-a60a-4447-86b0-dd97fca32d95` | NOT_APPLICABLE |

**모델 응답 원문**

`searchNote`

> EIS로 확인되고 실제 배송일부터 30일 이내라면 판매자에게만 요청하기보다 eBay에서 국제 반품을 신청하는 절차가 근거로 뒷받침됩니다. 구성품 누락이 설명과 다른 상품 상태에 해당하면 반품·환불 요청 근거가 될 수 있지만, 교체품은 합의 가능한 대안일 뿐 강제 발송 권리는 확인되지 않으며, 누락 구성품이 목록에 포함됐는지와 환불 승인 여부는 추가 확인이 필요합니다.

**관찰**

별도 관찰 기록 없음

<a id="clean-500-q04"></a>

#### 질문 04 · 제3자 배대지와 구매자 보호

**질문 원문**

> 미국 eBay에서 중고 카메라를 구매해 제가 선택한 미국 배대지로 보낸 뒤 한국에서 받았습니다. 배대지에는 배송 완료됐지만 한국에서 개봉하니 상품 설명에 없던 렌즈 파손이 확인됐습니다. 판매자에게 환불을 요청할 때 eBay Money Back Guarantee를 적용받을 수 있나요?

**모델 출력 상태**

`EVIDENCE_SUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_SUFFICIENT · 반환 청크 5개 · 서비스 호출 8386ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.499229 | `a7d6e7f3-5c5f-4fa4-9d76-c14852eeb9d8` | UNCERTAIN |
| 2 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.492509 | `90b591ba-a082-4d0c-8e39-2b56278899fc` | UNCERTAIN |
| 3 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.488751 | `7cedeb20-1545-48f8-ae47-27cb520459fc` | UNCERTAIN |
| 4 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.480051 | `5a4aa44e-d3b1-400c-9b05-55fe482421ca` | UNCERTAIN |
| 5 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | — | 0.474716 | `748b0b87-4840-4963-9ed4-76ad728fd211` | NOT_APPLICABLE |

**모델 응답 원문**

`searchNote`

> 상품 설명에 없던 렌즈 파손은 ‘설명과 다름’ 사유로 반품·환불을 요청할 근거가 될 수 있으며, 해당 요청은 원칙적으로 배송일 기준 30일 또는 판매자 반품 기간 중 더 긴 기한 내에 해야 합니다. 다만 eBay.com checkout·적격 결제 등 자격 요건, 배대지 도착 후 파손이 생겼는지, 기한 준수 여부가 확인되지 않아 환불이 반드시 승인된다고 단정할 수는 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="clean-500-q05"></a>

#### 질문 05 · 국제 반품 라벨과 배송 증빙

**질문 원문**

> 미국 eBay 판매자가 한국으로 직접 배송한 820달러짜리 기기가 작동하지 않아 반품을 접수했습니다. 판매자는 반품을 승인했지만, 접수 후 영업일 4일이 지나도 한국에서 쓸 수 있는 라벨을 주지 않았습니다. 제가 먼저 국제 반송비를 결제하면 돌려받을 수 있나요? eBay 개입은 언제 요청할 수 있고, 반송 시 어떤 배송 증빙이 필요한가요?

**모델 출력 상태**

`EVIDENCE_SUFFICIENT` · 모델

**실행 결과**

상태 EVIDENCE_SUFFICIENT · 반환 청크 5개 · 서비스 호출 9307ms

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.545307 | `c58ab21a-6401-421e-b64c-21ec03c34e7c` | UNCERTAIN |
| 2 | [Return shipping for buyers](https://www.ebay.com/help/buying/returns-refunds/returning-item/return-shipping?id=4066) | — | 0.544505 | `accca91a-6c46-4914-972b-c063b17acccd` | APPLICABLE |
| 3 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | — | 0.542991 | `a1b9dab3-dab6-4a1a-b48e-0f2416ecbe12` | APPLICABLE |
| 4 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | — | 0.541845 | `504fcdd8-5ab7-4972-acaf-7c763d5ba9c2` | APPLICABLE |
| 5 | [Return shipping for buyers](https://www.ebay.com/help/buying/returns-refunds/returning-item/return-shipping?id=4066) | — | 0.541036 | `b767d1cd-72d0-40f7-914b-0a6b49fcedf7` | APPLICABLE |

**모델 응답 원문**

`searchNote`

> 기기 고장 반품이면 판매자가 반송비를 부담해야 하며, 판매자가 승인했지만 해결책이나 라벨을 주지 않은 경우 eBay 개입을 요청할 근거가 있습니다. 다만 국제 반송비를 먼저 내면 반드시 상환된다는 보장은 없으므로 먼저 eBay에 한국에서 쓸 라벨·반송 지침을 요청하고, 직접 발송한다면 추적과 서명 확인을 갖추며 이메일의 기한과 반송 주소를 따라야 합니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500"></a>

## 새 구매자 질의 20개 · 500/200 · 2026-11-28

### 질문별 결과 요약

| 질문 | 주제 | 모델 출력 상태 | 반환 수 | 상태 출처 |
|---:|---|---|---:|---|
| [01](#buyer-500-q01) | 추적번호 없는 배송 지연 | `SUFFICIENT` | 5 | 모델 |
| [02](#buyer-500-q02) | 노트북 결함과 부분 환불 | `SUFFICIENT` | 5 | 모델 |
| [03](#buyer-500-q03) | 발송 전 주문 취소 | `SUFFICIENT` | 5 | 모델 |
| [04](#buyer-500-q04) | 단순 변심과 최초 배송비 | `INSUFFICIENT` | 5 | 모델 |
| [05](#buyer-500-q05) | 배송 완료 사진과 미수령 | `SUFFICIENT` | 5 | 모델 |
| [06](#buyer-500-q06) | 교체 약속과 반품 요청 종료 | `INSUFFICIENT` | 5 | 모델 |
| [07](#buyer-500-q07) | 카드사 결제 이의 제기 | `INSUFFICIENT` | 5 | 모델 |
| [08](#buyer-500-q08) | 쿠폰과 카드 결제 취소 | `INSUFFICIENT` | 5 | 모델 |
| [09](#buyer-500-q09) | 한 주문의 일부 미수령 | `SUFFICIENT` | 5 | 모델 |
| [10](#buyer-500-q10) | 반송 완료 후 카드 환불 지연 | `SUFFICIENT` | 5 | 모델 |
| [11](#buyer-500-q11) | 반품 주소 불일치 | `INSUFFICIENT` | 5 | 모델 |
| [12](#buyer-500-q12) | 고가 상품과 대리 수령 | `SUFFICIENT` | 5 | 모델 |
| [13](#buyer-500-q13) | 운동화 상태와 위조 의심 | `INSUFFICIENT` | 5 | 모델 |
| [14](#buyer-500-q14) | 비회원 불량품 반품 | `SUFFICIENT` | 5 | 모델 |
| [15](#buyer-500-q15) | 포장 개봉과 환불액 차감 | `SUFFICIENT` | 5 | 모델 |
| [16](#buyer-500-q16) | 국제 배송 파손과 수입 비용 | `SUFFICIENT` | 5 | 모델 |
| [17](#buyer-500-q17) | 예약 주문 배송 지연 | `SUFFICIENT` | 5 | 모델 |
| [18](#buyer-500-q18) | 발송 후 배송 주소 오류 | `SUFFICIENT` | 5 | 모델 |
| [19](#buyer-500-q19) | 고가 상품 반송과 서명 | `SUFFICIENT` | 5 | 모델 |
| [20](#buyer-500-q20) | 플랫폼 밖 부분 환불 | `INSUFFICIENT` | 5 | 모델 |

**집계** · 전체 20건 · 모델 충분 13건 · 부족 7건 · 정책 미검색 0건 · 반환 결과 100개

**평가** · 단순 변심 반품에서 최초 배송비를 제외할 수 있다는 조항을 찾았다. 카드사 이의 제기와 플랫폼 밖 일부 환불의 핵심 근거는 상위 5개에서 확인되지 않았다.


### 실행 조건

| 변경 항목 | 이번 실험 |
|---|---|
| 질문 세트 | 구매자 가상 사례 20개. 정책 문구를 출발점으로 삼지 않은 편의 표본 |

### 결과

| SUFFICIENT | INSUFFICIENT | POLICY_NOT_FOUND |
|---:|---:|---:|
| 13 | 7 | 0 |

위 집계는 모델이 반환한 `searchStatus`다. 질문별 정답을 사전에 확정하거나 반복성을 측정하지 않았다.

### 질문별 결과

검색 순서대로 상위 5개 청크의 문서 제목과 유사도를 기록했다.

<a id="buyer-500-q01"></a>

#### 질문 01 · 추적번호 없는 배송 지연

**질문 원문**

> 배송 예정일이 지났는데 판매자가 발송했다고만 하고 추적번호를 주지 않아요. 며칠 더 기다려야 하고 어디서 도움을 요청하나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.3626 | — | — |
| 2 | eBay Money Back Guarantee policy | — | 0.3615 | — | — |
| 3 | Get help if you bought as a guest | — | 0.3312 | — | — |
| 4 | Selling practices policy | — | 0.3249 | — | — |
| 5 | eBay Money Back Guarantee policy | — | 0.3130 | — | — |

**모델 응답 원문**

`searchNote`

> 판매자에게 상품이 도착하지 않았다고 알리고 미수령 신고를 할 수 있으며, 판매자는 신고 후 영업일 3일 이내에 추적 정보·배송 업데이트 또는 환불을 제공해야 합니다. 해결되지 않으면 eBay에 개입을 요청할 수 있고, 유효한 추적 정보가 없을 때 eBay는 신고 후 영업일 3~21일 사이에 개입할 수 있지만, 신고일과 거래 자격이 확인되지 않아 정확히 며칠을 기다려야 하는지는 단정할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500-q02"></a>

#### 질문 02 · 노트북 결함과 부분 환불

**질문 원문**

> 중고 노트북을 받았는데 설명에 없던 화면 깜박임이 있어요. 판매자가 수리비 일부만 보내겠다고 하는데 반품하고 전액을 받을 수 있나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.3517 | — | — |
| 2 | Get help if you bought as a guest | — | 0.3492 | — | — |
| 3 | Return an item for a refund | — | 0.3450 | — | — |
| 4 | Condition of returned items policy | — | 0.3359 | — | — |
| 5 | Return an item for a refund | — | 0.3329 | — | — |

**모델 응답 원문**

`searchNote`

> 설명에 없던 실제 결함이라면 반품 요청과 환불을 요구할 정책상 근거가 있고, 판매자와 부분 환불에 합의하지 않고 반품을 요청할 수 있습니다. 다만 기한 준수와 결함·미고지 여부, 반품 시 상태가 확인되지 않아 전액 환불이 확정된다고 할 수 없으며, 판매자의 부분 환불 제안은 가능한 해결책이지 구매자가 반드시 수락해야 하는 조건은 아닙니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500-q03"></a>

#### 질문 03 · 발송 전 주문 취소

**질문 원문**

> 결제하고 20분 뒤에 같은 물건을 다른 곳에서 더 싸게 발견했어요. 아직 발송 전인데 주문 취소를 요청하면 판매자가 꼭 취소해 줘야 하나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Order cancellation policy | — | 0.4622 | — | — |
| 2 | Order cancellation policy | — | 0.4552 | — | — |
| 3 | Order cancellation policy | — | 0.4083 | — | — |
| 4 | Return an item for a refund | — | 0.3495 | — | — |
| 5 | Return an item for a refund | — | 0.3418 | — | — |

**모델 응답 원문**

`searchNote`

> 더 싸게 발견했다는 이유로 취소를 요청할 수는 있지만, 판매자는 요청을 수락하거나 거절할 수 있으므로 반드시 취소해 줘야 하는 것은 아닙니다. 발송 전이라는 사실만으로 결과가 보장되지는 않으며, 경매·eBay Live 구매 여부와 발송·수령 상태 등 핵심 조건도 확인해야 합니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500-q04"></a>

#### 질문 04 · 단순 변심과 최초 배송비

**질문 원문**

> 선물로 산 코트를 개봉해 보니 색상과 사이즈는 주문한 그대로인데 마음에 들지 않아요. 반품 가능 상품이면 처음 낸 배송비도 돌아오나요?

**모델 출력 상태**

`INSUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.3188 | — | — |
| 2 | Return an item for a refund | — | 0.3170 | — | — |
| 3 | eBay Money Back Guarantee policy | — | 0.3156 | — | — |
| 4 | Condition of returned items policy | — | 0.3156 | — | — |
| 5 | Return an item for a refund | — | 0.3152 | — | — |

**모델 응답 원문**

`searchNote`

> 목록에서 반품을 허용하고 반품 기간 안에 요청하면 판매자는 변심 반품을 받아야 하지만, 최초 배송비는 환불액에서 제외할 수 있어 반환을 요구해도 보장되지는 않습니다. 목록의 반품 조건과 기간이 확인되지 않았고, 정책은 최초 배송비 제외를 명시적으로 허용합니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500-q05"></a>

#### 질문 05 · 배송 완료 사진과 미수령

**질문 원문**

> 문 앞 배송 사진이 제 집 현관과 전혀 다릅니다. 배송 완료 상태인데 물건을 못 받았으면 어떤 증거를 내야 하나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.4439 | — | — |
| 2 | eBay Money Back Guarantee policy | — | 0.4138 | — | — |
| 3 | eBay Money Back Guarantee policy | — | 0.3539 | — | — |
| 4 | eBay Money Back Guarantee policy | — | 0.3466 | — | — |
| 5 | Get help if you bought as a guest | — | 0.3238 | — | — |

**모델 응답 원문**

`searchNote`

> 판매자에게 미수령을 신고하고 배송 추적·상황 확인 또는 환불을 요청하는 근거로는 사용할 수 있습니다. 다만 구매자가 반드시 제출해야 할 증거 목록은 검색된 정책에 없으며, 배송 성공 여부는 주문 주소와 일치하는 배송 정보 등 판매자의 증빙 및 실제 수령 사실을 확인해야 판단할 수 있습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500-q06"></a>

#### 질문 06 · 교체 약속과 반품 요청 종료

**질문 원문**

> 판매자가 오배송을 인정하고 새 상품을 보낸다고 했는데 반품 요청부터 닫으래요. 닫은 뒤에도 새 상품이 안 오면 다시 도움을 받을 수 있나요?

**모델 출력 상태**

`INSUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.3796 | — | — |
| 2 | How to handle a return request as a seller | — | 0.3793 | — | — |
| 3 | How to handle a return request as a seller | — | 0.3701 | — | — |
| 4 | Return an item for a refund | — | 0.3654 | — | — |
| 5 | Get help if you bought as a guest | — | 0.3647 | — | — |

**모델 응답 원문**

`searchNote`

> 판매자가 교환품을 제안할 수 있고, 열린 요청에서 합의가 되지 않으면 3영업일 후 eBay에 개입을 요청할 수 있다는 근거는 있습니다. 그러나 요청을 닫으면 다시 열거나 같은 거래로 새 요청을 제출할 수 없으며, 닫은 뒤 교환품이 오지 않았을 때의 별도 구제 절차는 확인되지 않아 요청을 닫아도 다시 도움을 받을 수 있다고 판매자에게 확답할 근거는 부족합니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500-q07"></a>

#### 질문 07 · 카드사 결제 이의 제기

**질문 원문**

> 카드로 결제하고 받은 물건이 설명과 달라요. 카드사에 먼저 결제 이의를 제기하면 사이트에서 반품이나 환불 신청도 할 수 있나요?

**모델 출력 상태**

`INSUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help if you bought as a guest | — | 0.3962 | — | — |
| 2 | Get help if you bought as a guest | — | 0.3937 | — | — |
| 3 | Get help if you bought as a guest | — | 0.3873 | — | — |
| 4 | Return an item for a refund | — | 0.3799 | — | — |
| 5 | Get help if you bought as a guest | — | 0.3729 | — | — |

**모델 응답 원문**

`searchNote`

> 설명과 다른 상품은 반품 신청 대상이 될 수 있으며, 주문 내역에서 반품 사유를 제출할 수 있다는 점은 판매자에게 요구할 근거로 쓸 수 있습니다. 다만 카드사에 먼저 결제 이의를 제기한 뒤에도 사이트 반품 신청이 가능한지와 두 절차의 관계는 검색된 정책에 없어 단정할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500-q08"></a>

#### 질문 08 · 쿠폰과 카드 결제 취소

**질문 원문**

> 할인 쿠폰과 카드로 나눠서 결제했는데 판매자가 재고가 없다며 주문을 취소했어요. 쿠폰과 카드 결제액은 각각 어떻게 되나요?

**모델 출력 상태**

`INSUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Coupon FAQs | — | 0.4502 | — | — |
| 2 | eBay Coupon FAQs | — | 0.4339 | — | — |
| 3 | Order cancellation policy | — | 0.3675 | — | — |
| 4 | Order cancellation policy | — | 0.3619 | — | — |
| 5 | eBay Coupon FAQs | — | 0.3604 | — | — |

**모델 응답 원문**

`searchNote`

> 판매자 취소 시 eBay 체크아웃 결제는 원래 결제 수단으로 전액 환불된다는 정책과, 단일 상품 쿠폰은 원래 만료일을 유지한 채 반환된다는 안내를 근거로 요구할 수 있습니다. 다만 주문이 eBay 체크아웃으로 처리됐는지와 쿠폰·카드 각 부분의 정확한 환급 배분은 확인되지 않았으므로 구체적인 결과를 단정할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500-q09"></a>

#### 질문 09 · 한 주문의 일부 미수령

**질문 원문**

> 상품 두 개를 한 주문으로 샀는데 택배 상자에는 한 개만 들어 있어요. 받은 것까지 같이 반품해야 하나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help if you bought as a guest | — | 0.3338 | — | — |
| 2 | Return shipping for buyers | — | 0.3112 | — | — |
| 3 | Get help if you bought as a guest | — | 0.3110 | — | — |
| 4 | Return an item for a refund | — | 0.3048 | — | — |
| 5 | Get help if you bought as a guest | — | 0.3042 | — | — |

**모델 응답 원문**

`searchNote`

> 일부만 배송된 경우 누락 상품을 받지 못했다고 신고하는 절차가 근거로 뒷받침됩니다. 다만 세트·번들은 전체 반환이 필요하고, 개별 상품은 다르게 처리될 수 있으므로 상품이 세트인지 별도 목록 상품인지 확인 전에는 받은 상품도 함께 반환해야 한다고 단정할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500-q10"></a>

#### 질문 10 · 반송 완료 후 카드 환불 지연

**질문 원문**

> 판매자가 반품을 받았다고 확인했는데 일주일째 카드에 환불이 안 보여요. 판매자가 환불을 안 한 건지 결제 처리 중인지 어떻게 확인하나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | How to handle a return request as a seller | — | 0.4392 | — | — |
| 2 | Return an item for a refund | — | 0.4312 | — | — |
| 3 | Get help if you bought as a guest | — | 0.4184 | — | — |
| 4 | Return an item for a refund | — | 0.4076 | — | — |
| 5 | eBay Money Back Guarantee policy | — | 0.4057 | — | — |

**모델 응답 원문**

`searchNote`

> 판매자에게 반품 상세의 환불 상태와 기한을 확인해 달라고 요청할 근거가 있습니다. 판매자는 통상 반품품 수령 후 2영업일 이내 환불을 발행하지만, 카드 반영은 보통 3~5영업일이고 결제 수단에 따라 최대 30일 걸릴 수 있으므로, 실제 수령일·표시된 기한·환불 발행 여부를 확인하기 전에는 미발행인지 처리 중인지 단정할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500-q11"></a>

#### 질문 11 · 반품 주소 불일치

**질문 원문**

> 반품 라벨에 적힌 주소와 판매자가 메시지로 보낸 주소가 달라요. 어느 주소로 보내야 하고 어떤 배송 기록을 남겨야 하나요?

**모델 출력 상태**

`INSUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.4982 | — | — |
| 2 | Return shipping for buyers | — | 0.4786 | — | — |
| 3 | Return shipping for buyers | — | 0.4706 | — | — |
| 4 | Return shipping for buyers | — | 0.4526 | — | — |
| 5 | How to handle a return request as a seller | — | 0.4405 | — | — |

**모델 응답 원문**

`searchNote`

> 검색된 정책은 추적 가능한 배송을 이용하고 추적 정보를 반품 요청에 올리라는 요구를 뒷받침하며, 판매자 부담 반품에서는 제공된 라벨이나 판매자와 합의한 다른 방법을 쓰도록 합니다. 그러나 라벨 주소와 메시지 주소 중 어느 쪽을 우선할지는 정하지 않으므로, 발송 전에 반품 요청을 통해 판매자에게 주소를 확인하고 합의된 주소를 서면으로 남기세요.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500-q12"></a>

#### 질문 12 · 고가 상품과 대리 수령

**질문 원문**

> 친구가 대신 받아준 고가 카메라가 배송 완료로 찍혔는데 친구는 받은 적이 없다고 해요. 서명이나 배송지까지 확인해 달라고 할 수 있나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.3339 | — | — |
| 2 | eBay Money Back Guarantee policy | — | 0.3295 | — | — |
| 3 | Signature confirmation policy | — | 0.2799 | — | — |
| 4 | eBay Authenticity Guarantee for Sneakers | — | 0.2703 | — | — |
| 5 | Get help if you bought as a guest | — | 0.2692 | — | — |

**모델 응답 원문**

`searchNote`

> 판매자에게 미수령 문제를 알리고 배송 업데이트를 요청할 수 있으며, 배송 추적의 날짜와 주문 주소 일치 여부 및 적용되는 경우 서명 확인 정보를 확인해 달라고 하는 것은 정책상 배송 증거 요건에 근거가 있습니다. 그러나 친구가 대신 수령한 경우 보호 제외가 될 수 있고, 총액·배송 주소·서명 기록 등 핵심 조건이 미확인이라 서명이나 주소 자료를 판매자가 반드시 제공해야 한다거나 환불된다고 단정할 수는 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500-q13"></a>

#### 질문 13 · 운동화 상태와 위조 의심

**질문 원문**

> 새 운동화라고 해서 주문했는데 도착한 제품에 사용 흔적이 있고 정품인지도 의심돼요. 어떤 이유로 신고하거나 반품 요청을 해야 하나요?

**모델 출력 상태**

`INSUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Authenticity Guarantee for Sneakers | — | 0.4480 | — | — |
| 2 | eBay Authenticity Guarantee for Sneakers | — | 0.4178 | — | — |
| 3 | eBay Authenticity Guarantee for Sneakers | — | 0.4042 | — | — |
| 4 | eBay Authenticity Guarantee for Sneakers | — | 0.3971 | — | — |
| 5 | eBay Authenticity Guarantee for Sneakers | — | 0.3940 | — | — |

**모델 응답 원문**

`searchNote`

> 새 상품은 미사용·미착용이어야 하므로 실제 사용 흔적이 있다면 등록 상태와 다르다는 이유로 신고할 근거가 됩니다. 정품 여부는 적격 인증 상품에서 인증 불가 또는 등록 내용 불일치가 확인되면 전액 환불 절차가 안내돼 있지만, 상품의 인증 대상 여부와 검사 결과가 확인되지 않아 판매자에게 환불을 요구하면 반드시 받아들여진다고 단정할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500-q14"></a>

#### 질문 14 · 비회원 불량품 반품

**질문 원문**

> 주문자가 회원가입 없이 결제했고 주문 확인 이메일만 가지고 있어요. 불량품 반품을 어디에서 시작하나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help if you bought as a guest | — | 0.4174 | — | — |
| 2 | Get help if you bought as a guest | — | 0.3756 | — | — |
| 3 | Get help if you bought as a guest | — | 0.3587 | — | — |
| 4 | Get help if you bought as a guest | — | 0.3549 | — | — |
| 5 | Get help if you bought as a guest | — | 0.3511 | — | — |

**모델 응답 원문**

`searchNote`

> 불량품 반품은 주문 확인 이메일의 주문 상세에서 Return item을 선택해 시작할 수 있고, 사진을 첨부할 수도 있습니다. 불량품은 판매자의 반품 불가 정책에도 불구하고 자격 요건을 충족하면 보호 대상일 수 있지만, 교환은 판매자가 제안할 수 있는 선택지일 뿐 보장되지 않으며 실제 보호 적용 여부는 확인되지 않았습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500-q15"></a>

#### 질문 15 · 포장 개봉과 환불액 차감

**질문 원문**

> 반품한 이어폰은 작동 상태만 확인했는데 판매자가 포장을 뜯었다며 환불액에서 상당 부분을 뺐어요. 차감에 이의를 제기할 수 있나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Condition of returned items policy | — | 0.4466 | — | — |
| 2 | Condition of returned items policy | — | 0.4290 | — | — |
| 3 | Return an item for a refund | — | 0.3884 | — | — |
| 4 | How to handle a return request as a seller | — | 0.3857 | — | — |
| 5 | Return an item for a refund | — | 0.3844 | — | — |

**모델 응답 원문**

`searchNote`

> 차감에 이의를 제기하고, 반환 상품의 상태나 차감 사유가 납득되지 않으면 판매자와 해결을 시도한 뒤 조건에 해당할 때 eBay에 개입을 요청할 수 있습니다. 다만 판매자는 실제 가치 손실을 회복하기 위해 차감할 수 있고, 포장 개봉만으로 차감이 부당하다고 단정할 수 없으므로 이어폰·구성품 상태와 차감 근거를 확인해야 합니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500-q16"></a>

#### 질문 16 · 국제 배송 파손과 수입 비용

**질문 원문**

> 해외에서 산 제품이 도착했을 때 파손돼 반품하려고 해요. 상품 가격과 별도로 낸 관세와 세금도 돌려받을 수 있나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay International Shipping - Buyer Terms & Conditions | — | 0.3807 | — | — |
| 2 | Condition of returned items policy | — | 0.3794 | — | — |
| 3 | International purchases and shipping for buyers | — | 0.3652 | — | — |
| 4 | eBay International Shipping Seller Terms & Conditions | — | 0.3647 | — | — |
| 5 | eBay International Shipping - Buyer Terms & Conditions | — | 0.3572 | — | — |

**모델 응답 원문**

`searchNote`

> 검색된 EIS 약관을 근거로, 상품이 EIS로 배송 중 파손됐고 eBay Money Back Guarantee 청구가 승인되면 eBay에 낸 관세·세금도 환급받을 자격이 있을 수 있다고 요구할 수 있습니다. 다만 EIS 적용 여부, 파손 발생 구간과 청구 승인, 납부처가 확인되지 않았으며 DAP 반품은 원칙적으로 관세·세금 환급 대상이 아니므로 현재 사실만으로 환급을 단정할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500-q17"></a>

#### 질문 17 · 예약 주문 배송 지연

**질문 원문**

> 예약 주문한 제품이 약속한 달이 지나도 출시되지 않았습니다. 판매자가 배송을 또 미루는데 주문 취소나 환불을 요청할 수 있나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Order cancellation policy | — | 0.4150 | — | — |
| 2 | Order cancellation policy | — | 0.3935 | — | — |
| 3 | Return an item for a refund | — | 0.3431 | — | — |
| 4 | Order cancellation policy | — | 0.3402 | — | — |
| 5 | eBay Money Back Guarantee policy | — | 0.3355 | — | — |

**모델 응답 원문**

`searchNote`

> 판매자에게 취소를 요청할 수는 있지만 판매자가 수락 여부를 결정하며, 요청 가능 기간과 발송 여부에 제한이 있습니다. 취소나 전액 환불이 자동 보장되는 것은 아니며, 배송 예정일이 지난 뒤 미수령 신고 요건을 충족하면 판매자는 3영업일 내 배송 정보 또는 환불로 응답해야 합니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500-q18"></a>

#### 질문 18 · 발송 후 배송 주소 오류

**질문 원문**

> 물건을 받기 전에 주소가 틀린 걸 알았지만 이미 발송됐어요. 올바른 주소로 보내 달라고 요청하거나 환불받을 수 있나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.3381 | — | — |
| 2 | Get help if you bought as a guest | — | 0.3334 | — | — |
| 3 | Get help if you bought as a guest | — | 0.3321 | — | — |
| 4 | Return an item for a refund | — | 0.3253 | — | — |
| 5 | Get help if you bought as a guest | — | 0.3237 | — | — |

**모델 응답 원문**

`searchNote`

> 판매자에게 올바른 주소로 보내 달라고 요청하는 것은 가능하지만, 검색된 정책은 발송 후 취소나 주소 변경·재발송을 보장하지 않으며 원래 주소에서 수령해 보도록 권고합니다. 주소 오류만으로 환불받을 권리는 확인되지 않았고, 예상 배송일이 지난 뒤에도 상품이 미도착이면 신고할 수 있으며 판매자는 3영업일 내 배송 안내·교환 제안·환불을 제공해야 합니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500-q19"></a>

#### 질문 19 · 고가 상품 반송과 서명

**질문 원문**

> 주문 가격이 900달러인 제품을 판매자에게 반송했고 배송은 완료로 나오지만 서명을 받지 않았어요. 환불 분쟁에서 추적번호만으로 충분한가요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.4979 | — | — |
| 2 | Signature confirmation policy | — | 0.4889 | — | — |
| 3 | Signature confirmation policy | — | 0.4610 | — | — |
| 4 | eBay Money Back Guarantee policy | — | 0.4501 | — | — |
| 5 | eBay Money Back Guarantee policy | — | 0.4348 | — | — |

**모델 응답 원문**

`searchNote`

> 검색된 정책을 판매자에게 근거로 제시해 배송 완료 기록만으로는 충분하지 않을 수 있다고 요청할 수 있습니다. 반품비를 부담하는 구매자는 사이트별 기준에 따라 추적 가능한 배송과 주소·날짜 기록을 갖춰야 하며, eBay.com 구매 상품이 750달러 이상이거나 적용 기준을 넘으면 서명 확인이 요구됩니다. 다만 900달러가 세금 포함 가격인지, 구매 사이트와 반송비 부담 주체가 무엇인지 확인되지 않아 추적번호만으로 충분하다고 단정할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-500-q20"></a>

#### 질문 20 · 플랫폼 밖 부분 환불

**질문 원문**

> 판매자가 플랫폼 밖에서 계좌이체로 일부 환불을 해주겠다고 합니다. 받아도 남은 금액에 대해 사이트에서 분쟁을 계속할 수 있나요?

**모델 출력 상태**

`INSUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | How to handle a return request as a seller | — | 0.4184 | — | — |
| 2 | eBay Money Back Guarantee policy | — | 0.4165 | — | — |
| 3 | How to handle a return request as a seller | — | 0.4042 | — | — |
| 4 | How to handle a return request as a seller | — | 0.4020 | — | — |
| 5 | How to handle a return request as a seller | — | 0.3952 | — | — |

**모델 응답 원문**

`searchNote`

> 정책 근거로는 판매자가 플랫폼에서 부분 환불을 제안할 수 있고, 3영업일 내 합의하지 못하면 당사자가 플랫폼 개입을 요청할 수 있다고 말할 수 있습니다. 다만 계좌이체로 일부 환불을 받은 뒤 남은 금액에 대한 분쟁을 계속할 수 있는지는 검색된 정책에 명시되어 있지 않으므로 결과를 보장할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450"></a>

## 새 구매자 질의 20개 · 450/200 · 2026-11-28

### 질문별 결과 요약

| 질문 | 주제 | 모델 출력 상태 | 반환 수 | 상태 출처 |
|---:|---|---|---:|---|
| [01](#buyer-450-q01) | 추적번호 없는 배송 지연 | `SUFFICIENT` | 5 | 모델 |
| [02](#buyer-450-q02) | 노트북 결함과 부분 환불 | `SUFFICIENT` | 5 | 모델 |
| [03](#buyer-450-q03) | 발송 전 주문 취소 | `SUFFICIENT` | 5 | 모델 |
| [04](#buyer-450-q04) | 단순 변심과 최초 배송비 | `SUFFICIENT` | 5 | 모델 |
| [05](#buyer-450-q05) | 배송 완료 사진과 미수령 | `SUFFICIENT` | 5 | 모델 |
| [06](#buyer-450-q06) | 교체 약속과 반품 요청 종료 | `SUFFICIENT` | 5 | 모델 |
| [07](#buyer-450-q07) | 카드사 결제 이의 제기 | `INSUFFICIENT` | 5 | 모델 |
| [08](#buyer-450-q08) | 쿠폰과 카드 결제 취소 | `INSUFFICIENT` | 5 | 모델 |
| [09](#buyer-450-q09) | 한 주문의 일부 미수령 | `INSUFFICIENT` | 5 | 모델 |
| [10](#buyer-450-q10) | 반송 완료 후 카드 환불 지연 | `SUFFICIENT` | 5 | 모델 |
| [11](#buyer-450-q11) | 반품 주소 불일치 | `SUFFICIENT` | 5 | 모델 |
| [12](#buyer-450-q12) | 고가 상품과 대리 수령 | `SUFFICIENT` | 5 | 모델 |
| [13](#buyer-450-q13) | 운동화 상태와 위조 의심 | `INSUFFICIENT` | 5 | 모델 |
| [14](#buyer-450-q14) | 비회원 불량품 반품 | `SUFFICIENT` | 5 | 모델 |
| [15](#buyer-450-q15) | 포장 개봉과 환불액 차감 | `SUFFICIENT` | 5 | 모델 |
| [16](#buyer-450-q16) | 국제 배송 파손과 수입 비용 | `SUFFICIENT` | 5 | 모델 |
| [17](#buyer-450-q17) | 예약 주문 배송 지연 | `INSUFFICIENT` | 5 | 모델 |
| [18](#buyer-450-q18) | 발송 후 배송 주소 오류 | `SUFFICIENT` | 5 | 모델 |
| [19](#buyer-450-q19) | 고가 상품 반송과 서명 | `SUFFICIENT` | 5 | 모델 |
| [20](#buyer-450-q20) | 플랫폼 밖 부분 환불 | `INSUFFICIENT` | 5 | 모델 |

**집계** · 전체 20건 · 모델 충분 14건 · 부족 6건 · 정책 미검색 0건 · 반환 결과 100개

**평가** · 500/200보다 모델의 충분 판정이 1건 많았다. 단순 변심의 최초 배송비 조항과 반품 주소 불일치 시 우선 주소는 확인되지 않았다. 질문당 한 번 실행한 결과다.


### 실행 조건

| 변경 항목 | 이번 실험 |
|---|---|
| 청킹 | 450/200으로 재적재, 청크 146개 |
| 인덱스 관리 | 500/200 벡터 인덱스 백업 |

### 결과

| SUFFICIENT | INSUFFICIENT | POLICY_NOT_FOUND |
|---:|---:|---:|
| 14 | 6 | 0 |

위 집계는 모델이 반환한 `searchStatus`다. 질문별 정답을 사전에 확정하거나 반복성을 측정하지 않았다.

### 관찰

**결론.** 모델의 근거 충분 판정은 `450/200`이 14/20건, `500/200`이 13/20건이었다. 반면 단순 변심 반품에서 최초 배송비를 제외할 수 있다는 조항은 `500/200`에서만 검색됐다. 설정별로 질의당 한 번 실행했으므로 이번 결과만으로 검색 품질의 우열을 확정하기 어렵다. 비교 기준은 각 질문에 필요한 정책 조항이 상위 5개 청크에 포함됐는지다.

- 두 설정의 충분 판정은 12건에서 겹쳤다. 450/200에서만 충분 판정한 질문은 4·6·11번, 500/200에서만 충분 판정한 질문은 9·17번이었다.
- **4번 · 단순 변심의 최초 배송비**에서 500/200은 판매자가 최초 배송비를 환불액에서 제외할 수 있다는 Money Back Guarantee 본문을 찾았다. 450/200은 충분 판정을 했지만 해당 문구가 상위 5개에 없었고, 응답은 판매자 정책에 달려 있다고 설명했다.
- **6번 · 교체 약속 후 요청 종료**에서 두 설정 모두 닫은 요청은 다시 열 수 없다는 근거를 찾았다. **9번 · 한 주문의 일부 미수령**에서도 두 설정 모두 번들은 전부 반환한다는 문구를 찾았다. 500/200 응답은 개별 상품과 번들을 어떻게 처리하는지 더 직접적으로 설명했다.
- **11번 · 반품 주소 불일치**에서 450/200은 충분 판정을 했지만, 두 설정의 응답 모두 어떤 주소가 우선하는지 확인하지 못했다. **17번 · 예약 주문 지연**에서 500/200은 충분 판정을 했지만, 두 설정 모두 예약 주문 정책 대신 일반 주문 취소 안내를 검색했다.
- **7번 · 카드사 이의 제기**와 **20번 · 플랫폼 밖 일부 환불**은 양쪽 모두 질문의 핵심 쟁점을 뒷받침하는 근거를 찾지 못했다.

### 질문별 결과

검색 순서대로 상위 5개 청크의 문서 제목과 유사도를 기록했다.

<a id="buyer-450-q01"></a>

#### 질문 01 · 추적번호 없는 배송 지연

**질문 원문**

> 배송 예정일이 지났는데 판매자가 발송했다고만 하고 추적번호를 주지 않아요. 며칠 더 기다려야 하고 어디서 도움을 요청하나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.3602 | — | — |
| 2 | eBay Money Back Guarantee policy | — | 0.3478 | — | — |
| 3 | Get help if you bought as a guest | — | 0.3436 | — | — |
| 4 | Get help if you bought as a guest | — | 0.3385 | — | — |
| 5 | eBay Money Back Guarantee policy | — | 0.3209 | — | — |

**모델 응답 원문**

`searchNote`

> 예상 배송일이 지났다면 주문에서 미수령으로 신고할 수 있고, 판매자는 신고 후 영업일 3일 이내에 추적 정보·배송 업데이트 또는 환불을 제공해야 하며 해결되지 않으면 eBay에 개입을 요청할 수 있습니다. 신고 기한과 eBay 개입 가능 시점은 안내되어 있지만, 신고일과 거래의 보호 요건이 확인되지 않아 지금부터 정확히 며칠 기다려야 하는지는 단정할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450-q02"></a>

#### 질문 02 · 노트북 결함과 부분 환불

**질문 원문**

> 중고 노트북을 받았는데 설명에 없던 화면 깜박임이 있어요. 판매자가 수리비 일부만 보내겠다고 하는데 반품하고 전액을 받을 수 있나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.3876 | — | — |
| 2 | Get help if you bought as a guest | — | 0.3417 | — | — |
| 3 | Condition of returned items policy | — | 0.3409 | — | — |
| 4 | Return an item for a refund | — | 0.3391 | — | — |
| 5 | Get help if you bought as a guest | — | 0.3382 | — | — |

**모델 응답 원문**

`searchNote`

> 화면 깜박임이 설명에 없던 결함임을 뒷받침할 수 있다면, 판매자 반품 정책과 무관하게 반품을 요청하고 반품 수락 시 원래 배송비를 포함한 전액 환불 및 판매자 부담 반품 배송을 주장할 근거가 있습니다. 다만 결함·설명 불일치 여부와 신청 기한은 확인되지 않았고, 판매자는 부분 환불이나 교환을 제안할 수 있으며 전액 환불은 반품 수락과 반환 상태에 달려 있습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450-q03"></a>

#### 질문 03 · 발송 전 주문 취소

**질문 원문**

> 결제하고 20분 뒤에 같은 물건을 다른 곳에서 더 싸게 발견했어요. 아직 발송 전인데 주문 취소를 요청하면 판매자가 꼭 취소해 줘야 하나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Order cancellation policy | — | 0.4614 | — | — |
| 2 | Order cancellation policy | — | 0.4510 | — | — |
| 3 | Order cancellation policy | — | 0.4105 | — | — |
| 4 | Selling practices policy | — | 0.3606 | — | — |
| 5 | Return an item for a refund | — | 0.3584 | — | — |

**모델 응답 원문**

`searchNote`

> 판매자에게 주문 취소를 요청할 수 있다는 점은 정책으로 뒷받침되지만, 판매자는 수락하거나 거절할 수 있으므로 반드시 취소해 줘야 한다고 요구할 근거는 없습니다. 발송 전이라도 경매·eBay Live 구매 여부와 판매자의 발송 표시 여부가 확인되지 않았고, 더 싸게 발견했다는 이유만으로 취소가 보장되지는 않습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450-q04"></a>

#### 질문 04 · 단순 변심과 최초 배송비

**질문 원문**

> 선물로 산 코트를 개봉해 보니 색상과 사이즈는 주문한 그대로인데 마음에 들지 않아요. 반품 가능 상품이면 처음 낸 배송비도 돌아오나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.3410 | — | — |
| 2 | Return an item for a refund | — | 0.3324 | — | — |
| 3 | Get help if you bought as a guest | — | 0.3308 | — | — |
| 4 | Return an item for a refund | — | 0.3217 | — | — |
| 5 | Get help if you bought as a guest | — | 0.3215 | — | — |

**모델 응답 원문**

`searchNote`

> 판매자에게 마음에 들지 않는다는 이유로 반품을 요청할 수는 있지만, 반품 수락 여부와 최초 배송비 환불은 상품 등록의 판매자 반품 정책에 달려 있습니다. 주문 내용과 다르거나 하자·손상된 경우에는 최초 배송비 환불이 명시되어 있으나, 현재 코트는 주문한 색상·사이즈와 같아 그 예외의 근거는 확인되지 않습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450-q05"></a>

#### 질문 05 · 배송 완료 사진과 미수령

**질문 원문**

> 문 앞 배송 사진이 제 집 현관과 전혀 다릅니다. 배송 완료 상태인데 물건을 못 받았으면 어떤 증거를 내야 하나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.4283 | — | — |
| 2 | eBay Money Back Guarantee policy | — | 0.4139 | — | — |
| 3 | eBay Money Back Guarantee policy | — | 0.3647 | — | — |
| 4 | eBay Money Back Guarantee policy | — | 0.3416 | — | — |
| 5 | Get help if you bought as a guest | — | 0.3339 | — | — |

**모델 응답 원문**

`searchNote`

> 미도착 신고는 예상 또는 실제 배송일이 지난 뒤 제기할 수 있고, 판매자는 추적 정보·배송 업데이트 또는 환불로 응답해야 하므로 판매자에게 미도착 사실과 현관 사진이 주문 주소와 다르다는 점을 알릴 근거는 있습니다. 다만 구매자에게 요구되는 특정 증거 목록은 검색된 정책에 없으며, 배송 성공 여부는 주문 주소와 일치하는 수령지 정보 등 판매자의 배송 증빙 및 거래 세부사항으로 판단되므로 사진만으로 환불을 보장하지는 않습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450-q06"></a>

#### 질문 06 · 교체 약속과 반품 요청 종료

**질문 원문**

> 판매자가 오배송을 인정하고 새 상품을 보낸다고 했는데 반품 요청부터 닫으래요. 닫은 뒤에도 새 상품이 안 오면 다시 도움을 받을 수 있나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.3860 | — | — |
| 2 | How to handle a return request as a seller | — | 0.3796 | — | — |
| 3 | Get help if you bought as a guest | — | 0.3750 | — | — |
| 4 | Get help if you bought as a guest | — | 0.3684 | — | — |
| 5 | Get help if you bought as a guest | — | 0.3681 | — | — |

**모델 응답 원문**

`searchNote`

> 판매자에게 요청을 닫지 말고 열린 요청에서 메시지를 보내거나, 판매자가 해결책을 제공하지 않으면 eBay에 개입을 요청할 수 있다는 근거는 있습니다. 다만 닫은 요청은 다시 열거나 같은 거래에 새 요청을 낼 수 없다고 안내하므로, 닫은 뒤 새 상품이 오지 않았을 때 다시 도움받을 수 있다고 단정할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450-q07"></a>

#### 질문 07 · 카드사 결제 이의 제기

**질문 원문**

> 카드로 결제하고 받은 물건이 설명과 달라요. 카드사에 먼저 결제 이의를 제기하면 사이트에서 반품이나 환불 신청도 할 수 있나요?

**모델 출력 상태**

`INSUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.3993 | — | — |
| 2 | Return an item for a refund | — | 0.3895 | — | — |
| 3 | Get help if you bought as a guest | — | 0.3875 | — | — |
| 4 | Get help if you bought as a guest | — | 0.3826 | — | — |
| 5 | Get help if you bought as a guest | — | 0.3803 | — | — |

**모델 응답 원문**

`searchNote`

> 상품이 설명과 다르면 사이트에 반품 요청을 제출할 수 있고, 판매자가 반품 불가 정책을 두었더라도 반품 대상이 될 수 있다는 근거가 있습니다. 그러나 카드사 이의 제기를 먼저 한 뒤에도 신청 가능한지, 신청 기한 등 핵심 조건은 검색된 자료에 없어 그 점까지 판매자에게 확정적으로 요구할 근거는 부족합니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450-q08"></a>

#### 질문 08 · 쿠폰과 카드 결제 취소

**질문 원문**

> 할인 쿠폰과 카드로 나눠서 결제했는데 판매자가 재고가 없다며 주문을 취소했어요. 쿠폰과 카드 결제액은 각각 어떻게 되나요?

**모델 출력 상태**

`INSUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Coupon FAQs | — | 0.4308 | — | — |
| 2 | Order cancellation policy | — | 0.3605 | — | — |
| 3 | Order cancellation policy | — | 0.3593 | — | — |
| 4 | eBay Coupon FAQs | — | 0.3574 | — | — |
| 5 | eBay Coupon FAQs | — | 0.3519 | — | — |

**모델 응답 원문**

`searchNote`

> 판매자에게 eBay 체크아웃 결제의 전액을 원래 결제수단으로 환불해 달라고 요구할 근거가 있고, 체크아웃 외 결제라면 판매자가 전액 환불을 처리해야 합니다. 쿠폰을 사용한 결제가 역결제되면 단일 상품 쿠폰도 원래 만료일을 유지한 채 반환된다는 정책은 있으나, 이번 주문의 쿠폰별 반환 여부와 카드·쿠폰에 배분되는 개별 금액은 확인된 정보만으로 단정할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450-q09"></a>

#### 질문 09 · 한 주문의 일부 미수령

**질문 원문**

> 상품 두 개를 한 주문으로 샀는데 택배 상자에는 한 개만 들어 있어요. 받은 것까지 같이 반품해야 하나요?

**모델 출력 상태**

`INSUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.3401 | — | — |
| 2 | Get help if you bought as a guest | — | 0.3347 | — | — |
| 3 | Get help if you bought as a guest | — | 0.3221 | — | — |
| 4 | Get help if you bought as a guest | — | 0.3170 | — | — |
| 5 | Return an item for a refund | — | 0.3119 | — | — |

**모델 응답 원문**

`searchNote`

> 세트·번들이 아니라면, 받은 상품을 함께 반품해야 한다고 단정할 근거는 없으며 일부 상품 미수령으로 신고하는 절차가 안내되어 있습니다. 세트·번들이면 반품 요청이 필요하지만, 받은 구성품을 보관하고 부분 환불받는 것은 판매자가 제안할 수 있을 뿐 보장되지는 않습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450-q10"></a>

#### 질문 10 · 반송 완료 후 카드 환불 지연

**질문 원문**

> 판매자가 반품을 받았다고 확인했는데 일주일째 카드에 환불이 안 보여요. 판매자가 환불을 안 한 건지 결제 처리 중인지 어떻게 확인하나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.4315 | — | — |
| 2 | Get help if you bought as a guest | — | 0.4250 | — | — |
| 3 | Return an item for a refund | — | 0.4138 | — | — |
| 4 | How to handle a return request as a seller | — | 0.4126 | — | — |
| 5 | eBay Money Back Guarantee policy | — | 0.3996 | — | — |

**모델 응답 원문**

`searchNote`

> 판매자에게 요청 상세에서 환불 상태와 마감일을 확인하라고 요구하고, 반품 수령 후 판매자가 통상 2영업일 이내 환불을 발행하며 결제 반영에는 보통 3~5영업일, 일부 결제 수단은 최대 30일 걸릴 수 있다는 근거를 제시할 수 있습니다. 다만 수령 확인일·환불 발행 여부·요청 상세의 기한이 확인되지 않아 판매자가 환불하지 않았는지, 카드 처리 중인지는 아직 단정할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450-q11"></a>

#### 질문 11 · 반품 주소 불일치

**질문 원문**

> 반품 라벨에 적힌 주소와 판매자가 메시지로 보낸 주소가 달라요. 어느 주소로 보내야 하고 어떤 배송 기록을 남겨야 하나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return shipping for buyers | — | 0.4882 | — | — |
| 2 | Return shipping for buyers | — | 0.4554 | — | — |
| 3 | Return shipping for buyers | — | 0.4460 | — | — |
| 4 | How to handle a return request as a seller | — | 0.4423 | — | — |
| 5 | How to handle a return request as a seller | — | 0.4354 | — | — |

**모델 응답 원문**

`searchNote`

> 반품 기한을 지키고 추적 가능한 배송을 사용해 추적 정보를 반품 요청에 등록하라는 요구는 근거로 쓸 수 있으며, 750달러 이상이면 서명 확인도 필요합니다. 그러나 라벨 주소와 메시지 주소가 다를 때 어느 쪽을 따라야 하는지는 정책이 정하지 않으므로, 반품 상세와 공식 발송 안내를 확인하고 판매자에게 명확히 확인받기 전에는 특정 주소로 보내라고 단정할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450-q12"></a>

#### 질문 12 · 고가 상품과 대리 수령

**질문 원문**

> 친구가 대신 받아준 고가 카메라가 배송 완료로 찍혔는데 친구는 받은 적이 없다고 해요. 서명이나 배송지까지 확인해 달라고 할 수 있나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.3342 | — | — |
| 2 | eBay Money Back Guarantee policy | — | 0.3332 | — | — |
| 3 | eBay Money Back Guarantee policy | — | 0.2975 | — | — |
| 4 | Get help if you bought as a guest | — | 0.2748 | — | — |
| 5 | Signature confirmation policy | — | 0.2689 | — | — |

**모델 응답 원문**

`searchNote`

> 미수령 신고 후 eBay가 주문 주소의 우편번호(또는 주소 정보)와 배송 상태·날짜를 확인하며, 주문 총액이 750달러 이상이면 서명 확인도 요구한다는 정책을 근거로 판매자에게 배송지와 서명 확인 기록을 문의할 수 있습니다. 다만 검색된 정책은 판매자가 구매자에게 그 자료를 직접 제공해야 한다고 보장하지 않으며, 친구가 실제 대리 수령했다면 제3자 수령 제외가 적용될 수 있어 수령 여부와 거래 조건을 확인해야 합니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450-q13"></a>

#### 질문 13 · 운동화 상태와 위조 의심

**질문 원문**

> 새 운동화라고 해서 주문했는데 도착한 제품에 사용 흔적이 있고 정품인지도 의심돼요. 어떤 이유로 신고하거나 반품 요청을 해야 하나요?

**모델 출력 상태**

`INSUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Authenticity Guarantee for Sneakers | — | 0.4660 | — | — |
| 2 | eBay Authenticity Guarantee for Sneakers | — | 0.4464 | — | — |
| 3 | eBay Authenticity Guarantee for Sneakers | — | 0.4206 | — | — |
| 4 | eBay Authenticity Guarantee for Sneakers | — | 0.3969 | — | — |
| 5 | eBay Authenticity Guarantee for Sneakers | — | 0.3957 | — | — |

**모델 응답 원문**

`searchNote`

> 새 제품은 미사용·미착용이어야 하므로 실제 사용 흔적이 있고 목록에 새 제품으로 표시됐다면 목록과 다르다는 사유로 문제를 제기할 근거가 있습니다. 정품이 아니거나 현저히 설명과 다르다고 확인되면 환불 정책이 뒷받침되지만, 인증 대상 여부와 최종 판매 여부 및 반품 기간은 확인되지 않아 환불을 보장할 수는 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450-q14"></a>

#### 질문 14 · 비회원 불량품 반품

**질문 원문**

> 주문자가 회원가입 없이 결제했고 주문 확인 이메일만 가지고 있어요. 불량품 반품을 어디에서 시작하나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help if you bought as a guest | — | 0.4081 | — | — |
| 2 | Get help if you bought as a guest | — | 0.3796 | — | — |
| 3 | Get help if you bought as a guest | — | 0.3604 | — | — |
| 4 | Get help if you bought as a guest | — | 0.3581 | — | — |
| 5 | Get help if you bought as a guest | — | 0.3483 | — | — |

**모델 응답 원문**

`searchNote`

> 주문 확인 이메일에서 ‘View order details’ → ‘Return item’을 선택해 불량 사유를 제출하고 사진을 최대 10장 첨부할 수 있으므로 판매자에게 반품을 요청하는 절차의 근거로 쓸 수 있습니다. 불량품은 머니백 개런티 대상일 수 있지만 자격 요건 충족 여부와 불량 상태는 확인되지 않았고, 판매자의 응답 기한은 3영업일이며 특정 환불 결과까지 보장하지는 않습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450-q15"></a>

#### 질문 15 · 포장 개봉과 환불액 차감

**질문 원문**

> 반품한 이어폰은 작동 상태만 확인했는데 판매자가 포장을 뜯었다며 환불액에서 상당 부분을 뺐어요. 차감에 이의를 제기할 수 있나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Condition of returned items policy | — | 0.4448 | — | — |
| 2 | How to handle a return request as a seller | — | 0.4120 | — | — |
| 3 | Condition of returned items policy | — | 0.4111 | — | — |
| 4 | Return an item for a refund | — | 0.4090 | — | — |
| 5 | Return an item for a refund | — | 0.4028 | — | — |

**모델 응답 원문**

`searchNote`

> 차감액에 이의를 제기하고 eBay에 도움을 요청할 수 있으며, 차감은 반환 상품의 실제 가치 손실을 보전하는 경우로 제한된다는 근거가 있습니다. 다만 작동 확인을 위한 포장 개봉인지 불필요한 취급인지, 이어폰에 실제 가치 손실이 있었는지는 확인되지 않았고, 이의 제기가 차감 취소나 전액 환불을 보장하지는 않습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450-q16"></a>

#### 질문 16 · 국제 배송 파손과 수입 비용

**질문 원문**

> 해외에서 산 제품이 도착했을 때 파손돼 반품하려고 해요. 상품 가격과 별도로 낸 관세와 세금도 돌려받을 수 있나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4088 | — | — |
| 2 | Return an item for a refund | — | 0.3863 | — | — |
| 3 | Condition of returned items policy | — | 0.3859 | — | — |
| 4 | eBay International Shipping Seller Terms & Conditions | — | 0.3696 | — | — |
| 5 | Return an item for a refund | — | 0.3669 | — | — |

**모델 응답 원문**

`searchNote`

> 도착 시 파손된 상품은 판매자의 반품 불가 정책과 무관하게 반품할 수 있다는 요구는 근거가 있습니다. 다만 관세·세금 환급은 EIS 여부, 납부처, 배송 구간 및 승인된 청구 여부에 따라 달라지며, EIS DAP 반품은 원칙적으로 환급되지 않고 예외가 있으므로 현재 사실만으로 환급을 단정할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450-q17"></a>

#### 질문 17 · 예약 주문 배송 지연

**질문 원문**

> 예약 주문한 제품이 약속한 달이 지나도 출시되지 않았습니다. 판매자가 배송을 또 미루는데 주문 취소나 환불을 요청할 수 있나요?

**모델 출력 상태**

`INSUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Order cancellation policy | — | 0.4167 | — | — |
| 2 | Order cancellation policy | — | 0.3909 | — | — |
| 3 | Order cancellation policy | — | 0.3608 | — | — |
| 4 | Get help if you bought as a guest | — | 0.3473 | — | — |
| 5 | Return an item for a refund | — | 0.3315 | — | — |

**모델 응답 원문**

`searchNote`

> 취소를 요청할 수는 있지만 판매자가 수락하거나 거절할 수 있으므로, 약속한 달이 지나 배송이 미뤄졌다는 사실만으로 취소나 환불이 보장되지는 않습니다. 주문 후 30일 이내이고 발송 전인지, 이미 미도착 신고를 했는지 등이 확인되지 않았으며, 판매자가 취소하면 결제 경로에 따라 환불 방식이 다릅니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450-q18"></a>

#### 질문 18 · 발송 후 배송 주소 오류

**질문 원문**

> 물건을 받기 전에 주소가 틀린 걸 알았지만 이미 발송됐어요. 올바른 주소로 보내 달라고 요청하거나 환불받을 수 있나요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.3495 | — | — |
| 2 | Get help if you bought as a guest | — | 0.3445 | — | — |
| 3 | Return an item for a refund | — | 0.3344 | — | — |
| 4 | Get help if you bought as a guest | — | 0.3315 | — | — |
| 5 | Get help if you bought as a guest | — | 0.3288 | — | — |

**모델 응답 원문**

`searchNote`

> 판매자에게 주문 페이지에서 연락해 취소 가능 여부를 문의할 수 있지만, 이미 발송됐다면 취소할 수 없다고 안내되어 있고 가능하면 원래 주소에서 수령하는 것이 권장됩니다. 올바른 주소로 재배송이나 주소 오류만을 이유로 한 환불은 보장되지 않으며, 환불·반품 가능성은 판매자 반품 정책과 상품의 실제 문제 여부에 달려 있습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450-q19"></a>

#### 질문 19 · 고가 상품 반송과 서명

**질문 원문**

> 주문 가격이 900달러인 제품을 판매자에게 반송했고 배송은 완료로 나오지만 서명을 받지 않았어요. 환불 분쟁에서 추적번호만으로 충분한가요?

**모델 출력 상태**

`SUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.4972 | — | — |
| 2 | Signature confirmation policy | — | 0.4824 | — | — |
| 3 | Signature confirmation policy | — | 0.4809 | — | — |
| 4 | eBay Money Back Guarantee policy | — | 0.4501 | — | — |
| 5 | Signature confirmation policy | — | 0.4416 | — | — |

**모델 응답 원문**

`searchNote`

> 검색된 정책상 eBay.com에서 상품 총액이 750달러 이상이면 반송 시 서명 확인이 요구될 수 있고, 구매자가 반송비를 부담하는 경우에도 해당 기준 이상이면 서명 확인이 보호 요건입니다. 다만 구매 사이트, 상품 총액 계산과 반송비 부담 여부가 확인되지 않았으며, 배송 완료 추적만으로 환불 분쟁이 해결된다고 정책이 보장하지는 않습니다.

**관찰**

별도 관찰 기록 없음

<a id="buyer-450-q20"></a>

#### 질문 20 · 플랫폼 밖 부분 환불

**질문 원문**

> 판매자가 플랫폼 밖에서 계좌이체로 일부 환불을 해주겠다고 합니다. 받아도 남은 금액에 대해 사이트에서 분쟁을 계속할 수 있나요?

**모델 출력 상태**

`INSUFFICIENT` · 모델

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.4023 | — | — |
| 2 | eBay Money Back Guarantee policy | — | 0.3869 | — | — |
| 3 | Signature confirmation policy | — | 0.3862 | — | — |
| 4 | How to handle a return request as a seller | — | 0.3801 | — | — |
| 5 | How to handle a return request as a seller | — | 0.3788 | — | — |

**모델 응답 원문**

`searchNote`

> 검색된 정책으로는 환불 기한까지 판매자가 환불하지 않거나 양측이 해결하지 못한 경우 eBay에 개입을 요청할 수 있고, 요청·사건을 자발적으로 닫으면 보호를 잃을 수 있다는 점을 근거로 들 수 있습니다. 그러나 플랫폼 밖 계좌이체로 일부 환불을 받아도 남은 금액의 분쟁을 계속할 수 있는지, 그 수령이나 사건 상태가 어떻게 처리되는지는 확인되지 않아 이를 보장한다고 단정할 수 없습니다.

**관찰**

별도 관찰 기록 없음

<a id="detailed-450"></a>

## 상세 구매자 질의 20개 · 450/200 검색 실험 · 2026-11-28

### 질문별 결과 요약

| 질문 | 주제 | 모델 출력 상태 | 반환 수 | 상태 출처 |
|---:|---|---|---:|---|
| [01](#detailed-450-q01) | 추적번호 없는 배송 지연 | — | 5 | 응답 생성 미실행 |
| [02](#detailed-450-q02) | 노트북 결함과 부분 환불 | — | 5 | 응답 생성 미실행 |
| [03](#detailed-450-q03) | 발송 전 주문 취소 | — | 5 | 응답 생성 미실행 |
| [04](#detailed-450-q04) | 단순 변심과 최초 배송비 | — | 5 | 응답 생성 미실행 |
| [05](#detailed-450-q05) | 파손 반품과 국제 반송비 | — | 5 | 응답 생성 미실행 |
| [06](#detailed-450-q06) | 교체 약속과 반품 요청 종료 | — | 5 | 응답 생성 미실행 |
| [07](#detailed-450-q07) | 카드사 결제 이의 제기 | — | 5 | 응답 생성 미실행 |
| [08](#detailed-450-q08) | 배대지 경유와 상품 불일치 | — | 5 | 응답 생성 미실행 |
| [09](#detailed-450-q09) | 한 주문의 일부 미수령 | — | 5 | 응답 생성 미실행 |
| [10](#detailed-450-q10) | 반송 완료 후 카드 환불 지연 | — | 5 | 응답 생성 미실행 |
| [11](#detailed-450-q11) | 반품 주소 불일치 | — | 5 | 응답 생성 미실행 |
| [12](#detailed-450-q12) | 고가 상품과 대리 수령 | — | 5 | 응답 생성 미실행 |
| [13](#detailed-450-q13) | 운동화 상태와 위조 의심 | — | 5 | 응답 생성 미실행 |
| [14](#detailed-450-q14) | 비회원 불량품 반품 | — | 5 | 응답 생성 미실행 |
| [15](#detailed-450-q15) | 포장 개봉과 환불액 차감 | — | 5 | 응답 생성 미실행 |
| [16](#detailed-450-q16) | 국제 배송 파손과 수입 비용 | — | 5 | 응답 생성 미실행 |
| [17](#detailed-450-q17) | 예약 주문 배송 지연 | — | 5 | 응답 생성 미실행 |
| [18](#detailed-450-q18) | 발송 후 배송 주소 오류 | — | 5 | 응답 생성 미실행 |
| [19](#detailed-450-q19) | 고가 상품 반송과 서명 | — | 5 | 응답 생성 미실행 |
| [20](#detailed-450-q20) | 플랫폼 밖 부분 환불 | — | 5 | 응답 생성 미실행 |

**집계** · 검색 20/20건 · 반환 100개 · 모델 응답 생성 미실행

**검색 관찰** · 상위 5개에서 미수령 배송 증빙과 고가 반송 서명 요건을 찾았다. 카드사 이의 제기와 예약판매의 핵심 조항은 찾지 못했다.


### 실행 조건

| 변경 항목 | 이번 실험 |
|---|---|
| 실행 경로 | `text-embedding-3-large` 질의 임베딩 및 PostgreSQL 코사인 유사도 검색 |
| 질문 세트 | 구매자 질문 20개의 거래 상황을 구체화한 상세 질문 |
| 평가 방식 | 상위 청크 직접 검토 |

### 결과

20개 질문에서 상위 5개씩 총 100개 순위 결과를 확인했다. 검색 단계까지 실행했다.

### 관찰

- 1번은 1·2위에서 판매자 응답 기한과 eBay 개입 시점의 근거를 찾았다.
- 7번은 미수령 정책을 찾았으나 카드사 이의 제기가 진행 중인 eBay 요청에 미치는 영향을 설명하는 조항은 상위 5개에 없었다.
- 9번은 2~4위에서 세트 전체 반환과 별도 구매한 미수령 상품의 신고 절차를 찾았다.
- 17번은 일반 주문 취소 조항이 검색됐으며 예약판매 정책은 상위 5개에 없었다.
- 19번은 3위에서 750달러 이상 반송의 추적 및 서명 확인 요건을 찾았다.
- 20번은 플랫폼 밖 계좌이체 이후 남은 금액을 계속 다툴 수 있는지 직접 설명하는 조항을 찾지 못했다.

### 질문별 결과

### 질문별 결과

<a id="detailed-450-q01"></a>

#### 질문 01 · 추적번호 없는 배송 지연

**질문 원문**

> 11월 18일 eBay.com에서 미국 판매자의 게임 컨트롤러를 결제했고 주문 화면의 배송 예정일은 11월 24일이었습니다. 11월 28일에도 물건을 못 받았고 판매자는 발송했다고만 답했으며 주문 상세에 추적번호가 없습니다. 지금 미수령 신고를 열 수 있나요? 신고 뒤 판매자의 응답을 언제까지 기다리고 어디에서 eBay에 도움을 요청하나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.4925 | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` | — |
| 2 | eBay Money Back Guarantee policy | — | 0.4884 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 3 | Get help if you bought as a guest | — | 0.4619 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 4 | eBay Money Back Guarantee policy | — | 0.4601 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 5 | Get help if you bought as a guest | — | 0.4578 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

1·2위에서 판매자 응답 기한과 eBay 개입 시점을 확인했다.

<a id="detailed-450-q02"></a>

#### 질문 02 · 노트북 결함과 부분 환불

**질문 원문**

> 주문 설명에 화면 결함이 없다고 적힌 중고 노트북을 11월 25일 받았습니다. 첫날부터 화면이 깜박여 영상을 남겼고, 판매자는 수리비로 40달러를 주겠다고 했습니다. 아직 수락하지 않았고 판매 페이지는 반품 불가입니다. 결함 사유로 반품해 상품값과 최초 배송비를 돌려받을 수 있나요? 반송비는 누가 내나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.4005 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 2 | Return an item for a refund | — | 0.3891 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 3 | eBay Money Back Guarantee policy | — | 0.3826 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 4 | eBay Money Back Guarantee policy | — | 0.3818 | `77c77430-aceb-4f79-b632-ef78c863faf0` | — |
| 5 | eBay Money Back Guarantee policy | — | 0.3812 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

2·3위에서 불일치 반품, 전액 환불 및 판매자의 반송비 부담 근거를 확인했다.

<a id="detailed-450-q03"></a>

#### 질문 03 · 발송 전 주문 취소

**질문 원문**

> eBay.com에서 즉시구매로 헤드폰을 카드 결제한 지 20분 됐습니다. 다른 판매자가 더 싸게 파는 걸 발견해 주문 상세에서 취소를 요청하려고 합니다. 추적번호는 없고 발송 표시도 없습니다. 이 사유로 취소를 요청하면 판매자가 수락해야 하나요? 거절되거나 답이 없으면 주문은 어떻게 되나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Order cancellation policy | — | 0.5037 | `4f1d8091-f413-47f9-92b5-8fcf59780b03` | — |
| 2 | Order cancellation policy | — | 0.4983 | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` | — |
| 3 | Order cancellation policy | — | 0.4682 | `6aa36185-3f49-40c5-bbee-fb0855dfcf39` | — |
| 4 | Selling practices policy | — | 0.4574 | `29dc7c6c-e4f2-4783-b537-a7c16da81994` | — |
| 5 | Get help if you bought as a guest | — | 0.4169 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

1·2위에서 판매자의 취소 재량과 결제 후 미응답 시 처리 규칙을 확인했다.

<a id="detailed-450-q04"></a>

#### 질문 04 · 단순 변심과 최초 배송비

**질문 원문**

> 검은색 M 사이즈 코트를 주문했고 받은 제품의 색상과 사이즈는 주문과 일치합니다. 실내에서 한 번 입어 봤고 택은 그대로며, 받은 지 4일 됐습니다. 반품 가능으로 표시된 주문에서 단순 변심 반품을 신청하면 결제 시 별도로 낸 최초 배송비 12달러도 환불되나요? 반송 라벨 비용은 누가 부담하나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.3598 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 2 | Return an item for a refund | — | 0.3411 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 3 | Get help if you bought as a guest | — | 0.3307 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 4 | eBay Money Back Guarantee policy | — | 0.3225 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 5 | Return an item for a refund | — | 0.3177 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

반품 조건은 확인했으나 단순 변심 시 최초 배송비를 제외할 수 있는 조항이 빠졌다.

<a id="detailed-450-q05"></a>

#### 질문 05 · 파손 반품과 국제 반송비

**질문 원문**

> 주문한 에스프레소 머신을 개봉하니 물탱크가 갈라져 있었고 배송 상자도 한쪽이 찌그러져 있었습니다. 받은 당일 사진을 찍어 반품을 신청했는데 판매자가 자기 반품 라벨을 보내는 대신 제가 먼저 국제 반송비를 내달라고 합니다. 누가 반송을 준비하고 비용을 부담하나요? 먼저 비용을 내도 되는지 어떻게 확인하나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return shipping for buyers | — | 0.4410 | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` | — |
| 2 | Return shipping for buyers | — | 0.4385 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 3 | Return shipping for buyers | — | 0.4142 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 4 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4057 | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` | — |
| 5 | eBay International Shipping Seller Terms & Conditions | — | 0.3969 | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

1~3위에서 파손 반송비 부담과 대체 발송 방식의 사전 합의 절차를 확인했다.

<a id="detailed-450-q06"></a>

#### 질문 06 · 교체 약속과 반품 요청 종료

**질문 원문**

> 다른 색상의 운동화가 와서 eBay에서 반품 요청을 열었습니다. 판매자는 메시지로 교체품을 보내겠다고 하면서 먼저 요청을 닫아 달라고 합니다. 교체품 추적번호는 아직 없습니다. 요청을 닫은 뒤 교체품이 오지 않으면 같은 주문의 반품 요청을 다시 열거나 eBay에 도움을 요청할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help if you bought as a guest | — | 0.4746 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 2 | Get help if you bought as a guest | — | 0.4587 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 3 | Return shipping for buyers | — | 0.4446 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 4 | eBay Money Back Guarantee policy | — | 0.4434 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 5 | Return an item for a refund | — | 0.4391 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

5위에서 닫은 요청의 재개와 동일 거래의 새 요청이 불가능하다는 안내를 확인했다.

<a id="detailed-450-q07"></a>

#### 질문 07 · 카드사 결제 이의 제기

**질문 원문**

> 배송 예정일이 사흘 지난 카메라를 받지 못해 eBay에서 미수령 요청을 열었습니다. 판매자 답변을 기다리는 중인데 카드사는 카드 결제에 대한 이의 제기를 접수할 수 있다고 합니다. 지금 카드사에도 같은 결제 건을 접수하면 진행 중인 eBay 요청과 구매자 보호는 어떻게 되나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.4960 | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` | — |
| 2 | Get help if you bought as a guest | — | 0.4827 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 3 | eBay Money Back Guarantee policy | — | 0.4773 | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` | — |
| 4 | Return an item for a refund | — | 0.4730 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 5 | eBay Money Back Guarantee policy | — | 0.4651 | `3b462389-7437-407c-87bd-ea2d91d04130` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

카드사 이의 제기가 기존 eBay 요청과 구매자 보호에 미치는 영향이 빠졌다.

<a id="detailed-450-q08"></a>

#### 질문 08 · 배대지 경유와 상품 불일치

**질문 원문**

> 미국 판매자의 재킷을 한국에서 사면서 미국 배대지 주소를 eBay 주문 주소로 넣었습니다. 추적에는 배대지 도착으로 나오고 배대지는 상자를 한국으로 다시 보냈습니다. 한국에서 열어 보니 다른 색상의 재킷이 들어 있었는데 배대지에서는 개봉 사진을 찍지 않았습니다. eBay에서 상품 설명 불일치로 반품하거나 환불받을 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.4907 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 2 | Return an item for a refund | — | 0.4667 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 3 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4539 | `bf37f2b1-9659-40d3-844a-11545aaba365` | — |
| 4 | Return an item for a refund | — | 0.4458 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 5 | eBay Money Back Guarantee policy | — | 0.4455 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

직접 이용한 배대지에 적용되는 보호 제외 및 예외가 빠졌다.

<a id="detailed-450-q09"></a>

#### 질문 09 · 한 주문의 일부 미수령

**질문 원문**

> 한 판매자에게서 카메라와 메모리카드를 각각 장바구니에 넣어 한 번에 결제했습니다. 두 상품의 가격은 주문에 따로 적혀 있고 같은 추적번호가 붙어 있는데 상자에는 카메라만 있었습니다. 메모리카드만 받거나 그 금액만 환불받을 수 있나요? 한 상품 페이지에서 세트로 판매된 경우에도 같은 절차인가요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.3957 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 2 | Get help if you bought as a guest | — | 0.3601 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 3 | Return an item for a refund | — | 0.3588 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 4 | Get help if you bought as a guest | — | 0.3517 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 5 | Get help if you bought as a guest | — | 0.3461 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

2~4위에서 별도 구매 품목의 미수령 신고와 세트 전체 반환을 확인했다.

<a id="detailed-450-q10"></a>

#### 질문 10 · 반송 완료 후 카드 환불 지연

**질문 원문**

> 판매자가 반송 상자를 11월 19일 받았다고 메시지로 확인했고, 추적도 배송 완료입니다. 11월 28일 현재 카드 명세에 환불이 없고 판매자는 환불을 발행한 날짜를 알려주지 않았습니다. 반품 상세에서 무엇을 확인해야 판매자가 아직 환불을 발행하지 않은 건지 카드에 반영되는 중인지 구분할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.4539 | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` | — |
| 2 | eBay Money Back Guarantee policy | — | 0.4435 | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` | — |
| 3 | Get help if you bought as a guest | — | 0.4412 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 4 | eBay Money Back Guarantee policy | — | 0.4401 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 5 | Return an item for a refund | — | 0.4398 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

반송품 수령 후 환불 발행 기한과 결제 수단 반영까지의 처리 기간을 확인했다.

<a id="detailed-450-q11"></a>

#### 질문 11 · 반품 주소 불일치

**질문 원문**

> eBay 반품 요청에서 받은 선불 라벨에는 캘리포니아 주소가 찍혀 있는데 판매자가 메시지로 네바다 창고 주소에 보내라고 합니다. 반품 상세에는 새 라벨이 없고 발송 기한은 이틀 남았습니다. 어느 주소로 보내야 하나요? 나중에 분쟁이 생기면 어떤 추적 또는 배송 증빙이 필요한가요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return shipping for buyers | — | 0.4412 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 2 | Return shipping for buyers | — | 0.4223 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 3 | Return shipping for buyers | — | 0.4204 | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` | — |
| 4 | How to handle a return request as a seller | — | 0.4075 | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` | — |
| 5 | eBay Money Back Guarantee policy | — | 0.4018 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

추적 등록과 대체 배송 합의 절차는 있으나 두 주소 중 어느 주소가 우선하는지 확인할 수 없다.

<a id="detailed-450-q12"></a>

#### 질문 12 · 고가 상품과 대리 수령

**질문 원문**

> 배송비와 세금까지 1,080달러인 카메라를 eBay.com에서 주문해 친구 집을 주문 주소로 지정했습니다. 추적에는 배송 완료라고 나오지만 친구는 상자를 받지 않았고 판매자는 배송 완료 화면만 보여줬습니다. 주문 주소와 배송 증거가 일치하는지와 서명 기록을 확인해 달라고 할 수 있나요? 친구가 실제 대리 수령했다면 판단이 달라지나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.4653 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 2 | eBay Money Back Guarantee policy | — | 0.4489 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 3 | eBay Money Back Guarantee policy | — | 0.4375 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 4 | International purchases and shipping for buyers | — | 0.4227 | `02ed01df-7637-4e68-8458-f8e01b540ce4` | — |
| 5 | Signature confirmation policy | — | 0.4169 | `604b7fbb-2462-4a94-a0a3-59fb5290d846` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

1·2·5위에서 주소 일치와 서명 요건, 제3자 대리 수령의 예외를 확인했다.

<a id="detailed-450-q13"></a>

#### 질문 13 · 운동화 상태와 위조 의심

**질문 원문**

> 새 제품으로 표시된 운동화를 받았는데 밑창에 흙과 마모 흔적이 있고 상자와 신발의 제품 코드도 다릅니다. 주문 화면에서 정품 인증 대상 표시를 확인하지 못했고 받은 지 이틀 됐습니다. 상태가 설명과 다르다는 사유와 위조 의심 중 어떤 절차로 문제를 제기할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Authenticity Guarantee for Sneakers | — | 0.4879 | `a8720eaf-1dbd-4eaa-929a-d067e5780bf1` | — |
| 2 | eBay Authenticity Guarantee for Sneakers | — | 0.4830 | `668ef1c6-a0e2-4a5d-bf03-c24cc6e00f89` | — |
| 3 | eBay Authenticity Guarantee for Sneakers | — | 0.4613 | `bd335ae7-a208-4fb1-a080-26f3ce45688f` | — |
| 4 | eBay Authenticity Guarantee for Sneakers | — | 0.4364 | `909603f2-3b0e-4a92-b440-b3b76df9df36` | — |
| 5 | eBay Authenticity Guarantee for Sneakers | — | 0.4306 | `b52463d6-3981-4dda-bbc0-5853e70bb899` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

인증 대상 스니커즈 정책은 있으나 인증 여부가 미확인인 제품의 위조 의심 신고 절차가 빠졌다.

<a id="detailed-450-q14"></a>

#### 질문 14 · 비회원 불량품 반품

**질문 원문**

> eBay 계정 없이 신용카드로 결제했고 주문 확인 이메일은 있지만 로그인할 계정이 없습니다. 어제 도착한 블루투스 스피커는 전원이 켜지지 않습니다. 이메일에서 어디로 들어가 반품을 시작하고 불량 사진이나 설명을 어떻게 제출하나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help if you bought as a guest | — | 0.4644 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 2 | Get help if you bought as a guest | — | 0.4637 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 3 | Return an item for a refund | — | 0.4255 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 4 | Return an item for a refund | — | 0.4192 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 5 | Get help if you bought as a guest | — | 0.4144 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

5위에서 주문 확인 이메일로 반품을 시작하고 사진을 올리는 절차를 확인했다.

<a id="detailed-450-q15"></a>

#### 질문 15 · 포장 개봉과 환불액 차감

**질문 원문**

> 설명과 다른 이어폰을 반품했습니다. 포장 씰을 뜯어 5분간 작동을 확인한 뒤 케이블과 설명서까지 전부 돌려보냈습니다. 판매자가 개봉을 이유로 180달러 중 60달러를 차감했고 다른 훼손은 설명하지 않았습니다. 어떤 조건에서 차감에 이의를 제기하고 eBay에 도움을 요청할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Condition of returned items policy | — | 0.5061 | `5969c401-95f9-4001-99e5-4c8592208453` | — |
| 2 | How to handle a return request as a seller | — | 0.5060 | `1743ff1f-d3d4-4bdd-8248-211875998334` | — |
| 3 | Return an item for a refund | — | 0.4802 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 4 | Return an item for a refund | — | 0.4734 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 5 | How to handle a return request as a seller | — | 0.4664 | `6109adcf-ec42-4a95-818c-a2ef540a612d` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

차감 조건은 있으나 구매자가 차감에 이의를 제기하는 직접 절차가 상위 청크에 명확히 나오지 않는다.

<a id="detailed-450-q16"></a>

#### 질문 16 · 국제 배송 파손과 수입 비용

**질문 원문**

> eBay International Shipping으로 주문한 310달러짜리 조명이 도착했을 때 유리 갓이 깨져 있었습니다. 결제 영수증에는 eBay에 낸 수입 비용 26달러가 있고, 배송 때 운송사에도 세금 명목으로 15달러를 냈습니다. 파손 반품이 승인되면 상품값 외에 두 곳에 낸 수입 비용을 각각 돌려받을 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4802 | `bf37f2b1-9659-40d3-844a-11545aaba365` | — |
| 2 | International purchases and shipping for buyers | — | 0.4731 | `76066a7d-2738-449c-9574-7d68d06faa54` | — |
| 3 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4717 | `936ad65f-d348-453a-9ada-fd5061587211` | — |
| 4 | International purchases and shipping for buyers | — | 0.4657 | `02ed01df-7637-4e68-8458-f8e01b540ce4` | — |
| 5 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4642 | `5dd13ab2-02b1-4a36-8e2b-1ba1e0162b44` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

EIS의 배송 조건별 규칙은 찾았으나 두 납부처에 낸 금액이 각각 환급되는지 확인할 근거가 부족하다.

<a id="detailed-450-q17"></a>

#### 질문 17 · 예약 주문 배송 지연

**질문 원문**

> 8월에 예약 구매한 피규어의 상품 페이지에는 10월 발송 예정이라고 적혀 있었습니다. 11월 말에도 추적번호가 없고 판매자는 다시 다음 달로 미뤘습니다. 주문 후 30일은 지났으며 아직 미수령 신고는 열지 않았습니다. 지금 취소를 요청하거나 미수령으로 환불을 요청할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Order cancellation policy | — | 0.3857 | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` | — |
| 2 | Get help if you bought as a guest | — | 0.3653 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 3 | eBay Money Back Guarantee policy | — | 0.3645 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 4 | Get help if you bought as a guest | — | 0.3523 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 5 | Order cancellation policy | — | 0.3518 | `4f1d8091-f413-47f9-92b5-8fcf59780b03` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

일반 취소 안내가 검색됐으며 예약판매 지연에 적용할 조항이 빠졌다.

<a id="detailed-450-q18"></a>

#### 질문 18 · 발송 후 배송 주소 오류

**질문 원문**

> 결제 직후 확인해 보니 주문에 입력한 주소는 이사 전 아파트입니다. 판매자에게 새 주소를 보냈지만 이미 택배사에 넘겼고 추적은 배송 중입니다. 주문 주소를 수정하거나 올바른 주소로 돌려 보내 달라고 요청할 수 있나요? 기존 주소에서 받지 못하면 환불을 요청할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help if you bought as a guest | — | 0.3544 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 2 | eBay Money Back Guarantee policy | — | 0.3450 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 3 | Return an item for a refund | — | 0.3436 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 4 | Get help if you bought as a guest | — | 0.3433 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 5 | Return an item for a refund | — | 0.3356 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

4위에서 발송 후 취소 제한과 기존 주소에서의 수령 안내를 찾았으나 재배송이나 주소 오류로 인한 환불의 적용 조건은 빠졌다.

<a id="detailed-450-q19"></a>

#### 질문 19 · 고가 상품 반송과 서명

**질문 원문**

> eBay.com에서 세금과 배송비까지 900달러인 상품을 구매했고 반품 배송비는 제가 결제했습니다. 추적번호를 반품 요청에 올렸고 판매자 주소로 배송 완료로 나오지만 서명 옵션을 사지 않았습니다. 판매자가 반송품을 받지 못했다고 하면 추적 기록만으로 반품 배송을 입증할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.5289 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 2 | Signature confirmation policy | — | 0.5277 | `604b7fbb-2462-4a94-a0a3-59fb5290d846` | — |
| 3 | Return shipping for buyers | — | 0.5217 | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` | — |
| 4 | Signature confirmation policy | — | 0.5070 | `e2b3df5e-28ee-48d2-b4f7-f78175e269b9` | — |
| 5 | eBay Money Back Guarantee policy | — | 0.4992 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

3위에서 750달러 이상 반송의 추적 및 서명 확인 요건을 확인했다.

<a id="detailed-450-q20"></a>

#### 질문 20 · 플랫폼 밖 부분 환불

**질문 원문**

> 240달러짜리 제품의 결함으로 eBay에서 반품 요청을 진행 중입니다. 판매자가 eBay 메시지로 외부 계좌이체로 70달러를 보내겠다고 제안했으며, 아직 송금은 받지 않았고 요청도 열려 있습니다. 송금을 받아도 남은 170달러에 대해 같은 요청에서 계속 다투거나 eBay 개입을 요청할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 5개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.4682 | `3b462389-7437-407c-87bd-ea2d91d04130` | — |
| 2 | Return an item for a refund | — | 0.4669 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 3 | eBay Money Back Guarantee policy | — | 0.4627 | `6e065bd8-ddcb-4159-88ec-51f7904be5f9` | — |
| 4 | How to handle a return request as a seller | — | 0.4607 | `1743ff1f-d3d4-4bdd-8248-211875998334` | — |
| 5 | Get help if you bought as a guest | — | 0.4524 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

외부 계좌이체를 받은 뒤 남은 금액의 분쟁을 계속할 수 있는지 설명하는 조항이 빠졌다.

<a id="detailed-450-topk15"></a>

## 상세 구매자 질의 20개 · 450/200 · topK=15 · 2026-11-28

### 질문별 결과 요약

| 질문 | 주제 | 모델 출력 상태 | 반환 수 | 상태 출처 |
|---:|---|---|---:|---|
| [01](#detailed-450-topk15-q01) | 추적번호 없는 배송 지연 | — | 15 | 응답 생성 미실행 |
| [02](#detailed-450-topk15-q02) | 노트북 결함과 부분 환불 | — | 15 | 응답 생성 미실행 |
| [03](#detailed-450-topk15-q03) | 발송 전 주문 취소 | — | 15 | 응답 생성 미실행 |
| [04](#detailed-450-topk15-q04) | 단순 변심과 최초 배송비 | — | 15 | 응답 생성 미실행 |
| [05](#detailed-450-topk15-q05) | 파손 반품과 국제 반송비 | — | 15 | 응답 생성 미실행 |
| [06](#detailed-450-topk15-q06) | 교체 약속과 반품 요청 종료 | — | 15 | 응답 생성 미실행 |
| [07](#detailed-450-topk15-q07) | 카드사 결제 이의 제기 | — | 15 | 응답 생성 미실행 |
| [08](#detailed-450-topk15-q08) | 배대지 경유와 상품 불일치 | — | 15 | 응답 생성 미실행 |
| [09](#detailed-450-topk15-q09) | 한 주문의 일부 미수령 | — | 15 | 응답 생성 미실행 |
| [10](#detailed-450-topk15-q10) | 반송 완료 후 카드 환불 지연 | — | 15 | 응답 생성 미실행 |
| [11](#detailed-450-topk15-q11) | 반품 주소 불일치 | — | 15 | 응답 생성 미실행 |
| [12](#detailed-450-topk15-q12) | 고가 상품과 대리 수령 | — | 15 | 응답 생성 미실행 |
| [13](#detailed-450-topk15-q13) | 운동화 상태와 위조 의심 | — | 15 | 응답 생성 미실행 |
| [14](#detailed-450-topk15-q14) | 비회원 불량품 반품 | — | 15 | 응답 생성 미실행 |
| [15](#detailed-450-topk15-q15) | 포장 개봉과 환불액 차감 | — | 15 | 응답 생성 미실행 |
| [16](#detailed-450-topk15-q16) | 국제 배송 파손과 수입 비용 | — | 15 | 응답 생성 미실행 |
| [17](#detailed-450-topk15-q17) | 예약 주문 배송 지연 | — | 15 | 응답 생성 미실행 |
| [18](#detailed-450-topk15-q18) | 발송 후 배송 주소 오류 | — | 15 | 응답 생성 미실행 |
| [19](#detailed-450-topk15-q19) | 고가 상품 반송과 서명 | — | 15 | 응답 생성 미실행 |
| [20](#detailed-450-topk15-q20) | 플랫폼 밖 부분 환불 | — | 15 | 응답 생성 미실행 |

**집계** · 검색 20/20건 · 반환 300개 · 모델 응답 생성 미실행

**검색 관찰** · 최초 배송비 제외·카드사 이의 제기는 13위, 배대지 제외·공식 프로그램 예외는 15위였다.

### 실행 조건

| 변경 항목 | 이번 실험 |
|---|---|
| 검색 결과 상한 | `topK=15` |

질문 20개와 450/200 인덱스를 유지했다. 질문을 같은 모델로 다시 임베딩하고 PostgreSQL에서 벡터 검색했다. 

### 질문별 결과

<a id="detailed-450-topk15-q01"></a>

#### 질문 01 · 추적번호 없는 배송 지연

**질문 원문**

> 11월 18일 eBay.com에서 미국 판매자의 게임 컨트롤러를 결제했고 주문 화면의 배송 예정일은 11월 24일이었습니다. 11월 28일에도 물건을 못 받았고 판매자는 발송했다고만 답했으며 주문 상세에 추적번호가 없습니다. 지금 미수령 신고를 열 수 있나요? 신고 뒤 판매자의 응답을 언제까지 기다리고 어디에서 eBay에 도움을 요청하나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.4876 | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` | — |
| 2 | eBay Money Back Guarantee policy | — | 0.4820 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 3 | Get help if you bought as a guest | — | 0.4582 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 4 | eBay Money Back Guarantee policy | — | 0.4581 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 5 | Get help if you bought as a guest | — | 0.4500 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 6 | eBay Money Back Guarantee policy | — | 0.4214 | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` | — |
| 7 | eBay Money Back Guarantee policy | — | 0.4157 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 8 | eBay Money Back Guarantee policy | — | 0.4074 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |
| 9 | Return an item for a refund | — | 0.4022 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 10 | eBay Money Back Guarantee policy | — | 0.3975 | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` | — |
| 11 | Get help if you bought as a guest | — | 0.3934 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 12 | Selling practices policy | — | 0.3931 | `29dc7c6c-e4f2-4783-b537-a7c16da81994` | — |
| 13 | eBay Money Back Guarantee policy | — | 0.3914 | `3b462389-7437-407c-87bd-ea2d91d04130` | — |
| 14 | International purchases and shipping for buyers | — | 0.3877 | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` | — |
| 15 | eBay International Shipping Seller Terms & Conditions | — | 0.3847 | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

기존 상위 5개에 있던 핵심 근거가 유지됐다.

<a id="detailed-450-topk15-q02"></a>

#### 질문 02 · 노트북 결함과 부분 환불

**질문 원문**

> 주문 설명에 화면 결함이 없다고 적힌 중고 노트북을 11월 25일 받았습니다. 첫날부터 화면이 깜박여 영상을 남겼고, 판매자는 수리비로 40달러를 주겠다고 했습니다. 아직 수락하지 않았고 판매 페이지는 반품 불가입니다. 결함 사유로 반품해 상품값과 최초 배송비를 돌려받을 수 있나요? 반송비는 누가 내나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.4004 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 2 | Return an item for a refund | — | 0.3898 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 3 | eBay Money Back Guarantee policy | — | 0.3843 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |
| 4 | eBay Money Back Guarantee policy | — | 0.3840 | `77c77430-aceb-4f79-b632-ef78c863faf0` | — |
| 5 | eBay Money Back Guarantee policy | — | 0.3820 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 6 | Condition of returned items policy | — | 0.3799 | `5969c401-95f9-4001-99e5-4c8592208453` | — |
| 7 | eBay Refurbished | — | 0.3747 | `14425763-a00d-4017-b045-aa88eb4d0695` | — |
| 8 | Get help if you bought as a guest | — | 0.3729 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 9 | Get help if you bought as a guest | — | 0.3729 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 10 | How to handle a return request as a seller | — | 0.3723 | `1743ff1f-d3d4-4bdd-8248-211875998334` | — |
| 11 | eBay Refurbished | — | 0.3717 | `83be0678-d0f2-40f2-aecf-6d4bc8d49f52` | — |
| 12 | How to handle a return request as a seller | — | 0.3699 | `0655d062-13e1-45e5-af4b-9a5e802f811c` | — |
| 13 | eBay International Shipping - Buyer Terms & Conditions | — | 0.3678 | `bf37f2b1-9659-40d3-844a-11545aaba365` | — |
| 14 | Return an item for a refund | — | 0.3652 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 15 | How to handle a return request as a seller | — | 0.3637 | `c4954f02-57d2-46c7-8e54-0ed22200153e` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

기존 상위 5개에 있던 핵심 근거가 유지됐다.

<a id="detailed-450-topk15-q03"></a>

#### 질문 03 · 발송 전 주문 취소

**질문 원문**

> eBay.com에서 즉시구매로 헤드폰을 카드 결제한 지 20분 됐습니다. 다른 판매자가 더 싸게 파는 걸 발견해 주문 상세에서 취소를 요청하려고 합니다. 추적번호는 없고 발송 표시도 없습니다. 이 사유로 취소를 요청하면 판매자가 수락해야 하나요? 거절되거나 답이 없으면 주문은 어떻게 되나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Order cancellation policy | — | 0.5037 | `4f1d8091-f413-47f9-92b5-8fcf59780b03` | — |
| 2 | Order cancellation policy | — | 0.4983 | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` | — |
| 3 | Order cancellation policy | — | 0.4682 | `6aa36185-3f49-40c5-bbee-fb0855dfcf39` | — |
| 4 | Selling practices policy | — | 0.4574 | `29dc7c6c-e4f2-4783-b537-a7c16da81994` | — |
| 5 | Get help if you bought as a guest | — | 0.4169 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 6 | Return an item for a refund | — | 0.3942 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 7 | eBay Money Back Guarantee policy | — | 0.3908 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 8 | Get help if you bought as a guest | — | 0.3897 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 9 | How to handle a return request as a seller | — | 0.3880 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |
| 10 | Get help if you bought as a guest | — | 0.3871 | `0f5fa726-6d78-401f-bca3-39487acb99e2` | — |
| 11 | Get help if you bought as a guest | — | 0.3835 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 12 | Get help if you bought as a guest | — | 0.3822 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 13 | Get help if you bought as a guest | — | 0.3746 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 14 | eBay Money Back Guarantee policy | — | 0.3720 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.3715 | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

기존 상위 5개에 있던 핵심 근거가 유지됐다.

<a id="detailed-450-topk15-q04"></a>

#### 질문 04 · 단순 변심과 최초 배송비

**질문 원문**

> 검은색 M 사이즈 코트를 주문했고 받은 제품의 색상과 사이즈는 주문과 일치합니다. 실내에서 한 번 입어 봤고 택은 그대로며, 받은 지 4일 됐습니다. 반품 가능으로 표시된 주문에서 단순 변심 반품을 신청하면 결제 시 별도로 낸 최초 배송비 12달러도 환불되나요? 반송 라벨 비용은 누가 부담하나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.3598 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 2 | Return an item for a refund | — | 0.3411 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 3 | Get help if you bought as a guest | — | 0.3307 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 4 | eBay Money Back Guarantee policy | — | 0.3225 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 5 | Return an item for a refund | — | 0.3177 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 6 | Return an item for a refund | — | 0.3175 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 7 | Get help if you bought as a guest | — | 0.3137 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 8 | eBay Money Back Guarantee policy | — | 0.3101 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |
| 9 | Get help if you bought as a guest | — | 0.3015 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 10 | eBay Money Back Guarantee policy | — | 0.3014 | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` | — |
| 11 | eBay Money Back Guarantee policy | — | 0.2976 | `3b462389-7437-407c-87bd-ea2d91d04130` | — |
| 12 | eBay Money Back Guarantee policy | — | 0.2967 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 13 | eBay Money Back Guarantee policy | — | 0.2961 | `40df69ec-9ecf-41e0-a32a-982d935ab64a` | — |
| 14 | eBay Money Back Guarantee policy | — | 0.2938 | `77c77430-aceb-4f79-b632-ef78c863faf0` | — |
| 15 | eBay International Shipping - Buyer Terms & Conditions | — | 0.2937 | `bf37f2b1-9659-40d3-844a-11545aaba365` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

13위에서 단순 변심 반품의 최초 배송비 제외 가능성과 판매 페이지에 따른 반송비 부담을 확인했다.

<a id="detailed-450-topk15-q05"></a>

#### 질문 05 · 파손 반품과 국제 반송비

**질문 원문**

> 주문한 에스프레소 머신을 개봉하니 물탱크가 갈라져 있었고 배송 상자도 한쪽이 찌그러져 있었습니다. 받은 당일 사진을 찍어 반품을 신청했는데 판매자가 자기 반품 라벨을 보내는 대신 제가 먼저 국제 반송비를 내달라고 합니다. 누가 반송을 준비하고 비용을 부담하나요? 먼저 비용을 내도 되는지 어떻게 확인하나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return shipping for buyers | — | 0.4410 | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` | — |
| 2 | Return shipping for buyers | — | 0.4385 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 3 | Return shipping for buyers | — | 0.4142 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 4 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4057 | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` | — |
| 5 | eBay International Shipping Seller Terms & Conditions | — | 0.3969 | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` | — |
| 6 | How to handle a return request as a seller | — | 0.3954 | `c4954f02-57d2-46c7-8e54-0ed22200153e` | — |
| 7 | Return an item for a refund | — | 0.3940 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 8 | International purchases and shipping for buyers | — | 0.3905 | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` | — |
| 9 | eBay International Shipping - Buyer Terms & Conditions | — | 0.3892 | `bf37f2b1-9659-40d3-844a-11545aaba365` | — |
| 10 | Get help if you bought as a guest | — | 0.3878 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 11 | eBay International Shipping \| Seller Center | — | 0.3868 | `ba80853d-fe28-4579-81c0-530b40ab121c` | — |
| 12 | eBay Money Back Guarantee policy | — | 0.3858 | `77c77430-aceb-4f79-b632-ef78c863faf0` | — |
| 13 | How to handle a return request as a seller | — | 0.3838 | `0655d062-13e1-45e5-af4b-9a5e802f811c` | — |
| 14 | eBay International Shipping - Buyer Terms & Conditions | — | 0.3835 | `5dd13ab2-02b1-4a36-8e2b-1ba1e0162b44` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.3822 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

기존 상위 5개에 있던 핵심 근거가 유지됐다.

<a id="detailed-450-topk15-q06"></a>

#### 질문 06 · 교체 약속과 반품 요청 종료

**질문 원문**

> 다른 색상의 운동화가 와서 eBay에서 반품 요청을 열었습니다. 판매자는 메시지로 교체품을 보내겠다고 하면서 먼저 요청을 닫아 달라고 합니다. 교체품 추적번호는 아직 없습니다. 요청을 닫은 뒤 교체품이 오지 않으면 같은 주문의 반품 요청을 다시 열거나 eBay에 도움을 요청할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help if you bought as a guest | — | 0.4746 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 2 | Get help if you bought as a guest | — | 0.4587 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 3 | Return shipping for buyers | — | 0.4446 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 4 | eBay Money Back Guarantee policy | — | 0.4434 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 5 | Return an item for a refund | — | 0.4391 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 6 | Return shipping for buyers | — | 0.4384 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 7 | Return an item for a refund | — | 0.4334 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 8 | How to handle a return request as a seller | — | 0.4316 | `0655d062-13e1-45e5-af4b-9a5e802f811c` | — |
| 9 | Get help if you bought as a guest | — | 0.4281 | `0f5fa726-6d78-401f-bca3-39487acb99e2` | — |
| 10 | How to handle a return request as a seller | — | 0.4253 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |
| 11 | Get help if you bought as a guest | — | 0.4252 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 12 | Get help if you bought as a guest | — | 0.4251 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 13 | How to handle a return request as a seller | — | 0.4216 | `99dbe313-a5e6-473a-884d-9758d7490e93` | — |
| 14 | eBay Authenticity Guarantee for Sneakers | — | 0.4206 | `bd335ae7-a208-4fb1-a080-26f3ce45688f` | — |
| 15 | Return an item for a refund | — | 0.4197 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

기존 상위 5개에 있던 핵심 근거가 유지됐다.

<a id="detailed-450-topk15-q07"></a>

#### 질문 07 · 카드사 결제 이의 제기

**질문 원문**

> 배송 예정일이 사흘 지난 카메라를 받지 못해 eBay에서 미수령 요청을 열었습니다. 판매자 답변을 기다리는 중인데 카드사는 카드 결제에 대한 이의 제기를 접수할 수 있다고 합니다. 지금 카드사에도 같은 결제 건을 접수하면 진행 중인 eBay 요청과 구매자 보호는 어떻게 되나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.4960 | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` | — |
| 2 | Get help if you bought as a guest | — | 0.4827 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 3 | eBay Money Back Guarantee policy | — | 0.4773 | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` | — |
| 4 | Return an item for a refund | — | 0.4730 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 5 | eBay Money Back Guarantee policy | — | 0.4651 | `3b462389-7437-407c-87bd-ea2d91d04130` | — |
| 6 | eBay Money Back Guarantee policy | — | 0.4565 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 7 | Get help if you bought as a guest | — | 0.4563 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 8 | eBay Money Back Guarantee policy | — | 0.4553 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 9 | eBay Money Back Guarantee policy | — | 0.4491 | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` | — |
| 10 | Order cancellation policy | — | 0.4477 | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` | — |
| 11 | eBay Money Back Guarantee policy | — | 0.4470 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 12 | How to handle a return request as a seller | — | 0.4416 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |
| 13 | eBay Money Back Guarantee policy | — | 0.4410 | `6e065bd8-ddcb-4159-88ec-51f7904be5f9` | — |
| 14 | Return an item for a refund | — | 0.4399 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 15 | Order cancellation policy | — | 0.4364 | `4f1d8091-f413-47f9-92b5-8fcf59780b03` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

13위에서 같은 거래를 카드사에 이의 제기하면 eBay가 기존 사건을 닫을 수 있다는 조항을 확인했다.

<a id="detailed-450-topk15-q08"></a>

#### 질문 08 · 배대지 경유와 상품 불일치

**질문 원문**

> 미국 판매자의 재킷을 한국에서 사면서 미국 배대지 주소를 eBay 주문 주소로 넣었습니다. 추적에는 배대지 도착으로 나오고 배대지는 상자를 한국으로 다시 보냈습니다. 한국에서 열어 보니 다른 색상의 재킷이 들어 있었는데 배대지에서는 개봉 사진을 찍지 않았습니다. eBay에서 상품 설명 불일치로 반품하거나 환불받을 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.4907 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 2 | Return an item for a refund | — | 0.4667 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 3 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4539 | `bf37f2b1-9659-40d3-844a-11545aaba365` | — |
| 4 | Return an item for a refund | — | 0.4458 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 5 | eBay Money Back Guarantee policy | — | 0.4455 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 6 | Get help if you bought as a guest | — | 0.4417 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 7 | eBay International Shipping Seller Terms & Conditions | — | 0.4400 | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` | — |
| 8 | eBay Money Back Guarantee policy | — | 0.4378 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 9 | Get help if you bought as a guest | — | 0.4367 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 10 | International purchases and shipping for buyers | — | 0.4364 | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` | — |
| 11 | Return an item for a refund | — | 0.4347 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 12 | Return shipping for buyers | — | 0.4325 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 13 | eBay Money Back Guarantee policy | — | 0.4312 | `3b462389-7437-407c-87bd-ea2d91d04130` | — |
| 14 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4296 | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.4293 | `cdca8d5f-812a-4fed-a6a6-380989fdf86a` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

15위에서 제3자 배송대행은 보호 제외이고 eBay 공식 프로그램은 예외라는 조항을 확인했다.

<a id="detailed-450-topk15-q09"></a>

#### 질문 09 · 한 주문의 일부 미수령

**질문 원문**

> 한 판매자에게서 카메라와 메모리카드를 각각 장바구니에 넣어 한 번에 결제했습니다. 두 상품의 가격은 주문에 따로 적혀 있고 같은 추적번호가 붙어 있는데 상자에는 카메라만 있었습니다. 메모리카드만 받거나 그 금액만 환불받을 수 있나요? 한 상품 페이지에서 세트로 판매된 경우에도 같은 절차인가요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.3957 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 2 | Get help if you bought as a guest | — | 0.3601 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 3 | Return an item for a refund | — | 0.3588 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 4 | Get help if you bought as a guest | — | 0.3517 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 5 | Get help if you bought as a guest | — | 0.3461 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 6 | eBay International Shipping \| Seller Center | — | 0.3366 | `ba80853d-fe28-4579-81c0-530b40ab121c` | — |
| 7 | Condition of returned items policy | — | 0.3349 | `5969c401-95f9-4001-99e5-4c8592208453` | — |
| 8 | Return an item for a refund | — | 0.3322 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 9 | eBay International Shipping - Buyer Terms & Conditions | — | 0.3305 | `bf37f2b1-9659-40d3-844a-11545aaba365` | — |
| 10 | International purchases and shipping for buyers | — | 0.3304 | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` | — |
| 11 | eBay Money Back Guarantee policy | — | 0.3297 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 12 | eBay Money Back Guarantee policy | — | 0.3271 | `0b3a3fdf-2b2a-485b-b84f-eace87156c0e` | — |
| 13 | Get help if you bought as a guest | — | 0.3254 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 14 | eBay Money Back Guarantee policy | — | 0.3246 | `cdca8d5f-812a-4fed-a6a6-380989fdf86a` | — |
| 15 | How to handle a return request as a seller | — | 0.3244 | `1743ff1f-d3d4-4bdd-8248-211875998334` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

기존 상위 5개에 있던 핵심 근거가 유지됐다.

<a id="detailed-450-topk15-q10"></a>

#### 질문 10 · 반송 완료 후 카드 환불 지연

**질문 원문**

> 판매자가 반송 상자를 11월 19일 받았다고 메시지로 확인했고, 추적도 배송 완료입니다. 11월 28일 현재 카드 명세에 환불이 없고 판매자는 환불을 발행한 날짜를 알려주지 않았습니다. 반품 상세에서 무엇을 확인해야 판매자가 아직 환불을 발행하지 않은 건지 카드에 반영되는 중인지 구분할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.4539 | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` | — |
| 2 | eBay Money Back Guarantee policy | — | 0.4435 | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` | — |
| 3 | Get help if you bought as a guest | — | 0.4412 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 4 | eBay Money Back Guarantee policy | — | 0.4401 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 5 | Return an item for a refund | — | 0.4398 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 6 | eBay Money Back Guarantee policy | — | 0.4330 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |
| 7 | eBay Money Back Guarantee policy | — | 0.4252 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 8 | eBay Money Back Guarantee policy | — | 0.4252 | `3b462389-7437-407c-87bd-ea2d91d04130` | — |
| 9 | Return an item for a refund | — | 0.4244 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 10 | eBay Money Back Guarantee policy | — | 0.4240 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 11 | How to handle a return request as a seller | — | 0.4161 | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` | — |
| 12 | How to handle a return request as a seller | — | 0.4141 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |
| 13 | eBay Money Back Guarantee policy | — | 0.4119 | `40df69ec-9ecf-41e0-a32a-982d935ab64a` | — |
| 14 | Get help if you bought as a guest | — | 0.4100 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 15 | How to handle a return request as a seller | — | 0.4084 | `1743ff1f-d3d4-4bdd-8248-211875998334` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

기존 상위 5개에 있던 핵심 근거가 유지됐다.

<a id="detailed-450-topk15-q11"></a>

#### 질문 11 · 반품 주소 불일치

**질문 원문**

> eBay 반품 요청에서 받은 선불 라벨에는 캘리포니아 주소가 찍혀 있는데 판매자가 메시지로 네바다 창고 주소에 보내라고 합니다. 반품 상세에는 새 라벨이 없고 발송 기한은 이틀 남았습니다. 어느 주소로 보내야 하나요? 나중에 분쟁이 생기면 어떤 추적 또는 배송 증빙이 필요한가요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return shipping for buyers | — | 0.4412 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 2 | Return shipping for buyers | — | 0.4223 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 3 | Return shipping for buyers | — | 0.4204 | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` | — |
| 4 | How to handle a return request as a seller | — | 0.4075 | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` | — |
| 5 | eBay Money Back Guarantee policy | — | 0.4018 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 6 | How to handle a return request as a seller | — | 0.3966 | `c4954f02-57d2-46c7-8e54-0ed22200153e` | — |
| 7 | How to handle a return request as a seller | — | 0.3936 | `0655d062-13e1-45e5-af4b-9a5e802f811c` | — |
| 8 | Return an item for a refund | — | 0.3927 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 9 | How to handle a return request as a seller | — | 0.3823 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |
| 10 | How to handle a return request as a seller | — | 0.3796 | `00ab85d7-00dc-4e04-bc46-416a6a759751` | — |
| 11 | eBay Money Back Guarantee policy | — | 0.3793 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |
| 12 | How to handle a return request as a seller | — | 0.3775 | `d58dd7b1-f4d6-47ba-b7ae-81501242f723` | — |
| 13 | eBay Money Back Guarantee policy | — | 0.3759 | `77c77430-aceb-4f79-b632-ef78c863faf0` | — |
| 14 | Get help if you bought as a guest | — | 0.3758 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.3753 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

11·13위에서 판매자 지정 반송 주소와 설정된 주소 관련 요건을 찾았다. 공식 라벨과 메시지가 충돌할 때의 우선순위는 확인되지 않았다.

<a id="detailed-450-topk15-q12"></a>

#### 질문 12 · 고가 상품과 대리 수령

**질문 원문**

> 배송비와 세금까지 1,080달러인 카메라를 eBay.com에서 주문해 친구 집을 주문 주소로 지정했습니다. 추적에는 배송 완료라고 나오지만 친구는 상자를 받지 않았고 판매자는 배송 완료 화면만 보여줬습니다. 주문 주소와 배송 증거가 일치하는지와 서명 기록을 확인해 달라고 할 수 있나요? 친구가 실제 대리 수령했다면 판단이 달라지나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.4653 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 2 | eBay Money Back Guarantee policy | — | 0.4489 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 3 | eBay Money Back Guarantee policy | — | 0.4375 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 4 | International purchases and shipping for buyers | — | 0.4227 | `02ed01df-7637-4e68-8458-f8e01b540ce4` | — |
| 5 | Signature confirmation policy | — | 0.4169 | `604b7fbb-2462-4a94-a0a3-59fb5290d846` | — |
| 6 | Get help if you bought as a guest | — | 0.4076 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 7 | eBay International Shipping Seller Terms & Conditions | — | 0.4051 | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` | — |
| 8 | International purchases and shipping for buyers | — | 0.4046 | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` | — |
| 9 | Signature confirmation policy | — | 0.4033 | `e2b3df5e-28ee-48d2-b4f7-f78175e269b9` | — |
| 10 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4031 | `88ee7312-806c-49e9-875f-f20a8881ce44` | — |
| 11 | International purchases and shipping for buyers | — | 0.3982 | `76066a7d-2738-449c-9574-7d68d06faa54` | — |
| 12 | eBay Money Back Guarantee policy | — | 0.3925 | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` | — |
| 13 | Get help if you bought as a guest | — | 0.3851 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 14 | eBay International Shipping - Buyer Terms & Conditions | — | 0.3822 | `5dd13ab2-02b1-4a36-8e2b-1ba1e0162b44` | — |
| 15 | International purchases and shipping for buyers | — | 0.3794 | `9da141f2-c82b-4943-9404-e162c739dda3` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

기존 상위 5개에 있던 핵심 근거가 유지됐다.

<a id="detailed-450-topk15-q13"></a>

#### 질문 13 · 운동화 상태와 위조 의심

**질문 원문**

> 새 제품으로 표시된 운동화를 받았는데 밑창에 흙과 마모 흔적이 있고 상자와 신발의 제품 코드도 다릅니다. 주문 화면에서 정품 인증 대상 표시를 확인하지 못했고 받은 지 이틀 됐습니다. 상태가 설명과 다르다는 사유와 위조 의심 중 어떤 절차로 문제를 제기할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Authenticity Guarantee for Sneakers | — | 0.4879 | `a8720eaf-1dbd-4eaa-929a-d067e5780bf1` | — |
| 2 | eBay Authenticity Guarantee for Sneakers | — | 0.4830 | `668ef1c6-a0e2-4a5d-bf03-c24cc6e00f89` | — |
| 3 | eBay Authenticity Guarantee for Sneakers | — | 0.4613 | `bd335ae7-a208-4fb1-a080-26f3ce45688f` | — |
| 4 | eBay Authenticity Guarantee for Sneakers | — | 0.4364 | `909603f2-3b0e-4a92-b440-b3b76df9df36` | — |
| 5 | eBay Authenticity Guarantee for Sneakers | — | 0.4306 | `b52463d6-3981-4dda-bbc0-5853e70bb899` | — |
| 6 | eBay Authenticity Guarantee for Sneakers | — | 0.4282 | `28a46454-f148-412a-b6be-827a11666c92` | — |
| 7 | eBay Authenticity Guarantee for Sneakers | — | 0.4181 | `4493c75d-f79a-4cd5-b15e-e7c847137741` | — |
| 8 | eBay Authenticity Guarantee for Sneakers | — | 0.4161 | `d7ea9541-18ed-4a3b-860e-44597608060c` | — |
| 9 | eBay Authenticity Guarantee for Sneakers | — | 0.4151 | `4c3b3f5c-f79d-4e33-8ac0-c3d09a75123f` | — |
| 10 | eBay Authenticity Guarantee for Sneakers | — | 0.4137 | `310c3728-23b8-46d5-88f9-cfccc4aa62bc` | — |
| 11 | Buying with Authenticity Guarantee | — | 0.4101 | `79a064e9-afda-4eb4-8f2b-171482196f23` | — |
| 12 | eBay Authenticity Guarantee for Sneakers | — | 0.4004 | `28c18aa9-8df1-4134-bfa2-934df227a276` | — |
| 13 | eBay Authenticity Guarantee for Sneakers | — | 0.4001 | `a2c75035-7ecb-4809-a425-bb9a5655986c` | — |
| 14 | eBay Authenticity Guarantee for Sneakers | — | 0.3951 | `b3af4677-12f3-44be-8750-6cb51b7529b9` | — |
| 15 | eBay Authenticity Guarantee for Sneakers | — | 0.3786 | `b7fadb64-8245-4379-8046-a7ababfa5da8` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

15개 중 14개가 스니커즈 인증 안내였다. 일반 위조 의심 신고·반품 절차를 확인하지 못했다.

<a id="detailed-450-topk15-q14"></a>

#### 질문 14 · 비회원 불량품 반품

**질문 원문**

> eBay 계정 없이 신용카드로 결제했고 주문 확인 이메일은 있지만 로그인할 계정이 없습니다. 어제 도착한 블루투스 스피커는 전원이 켜지지 않습니다. 이메일에서 어디로 들어가 반품을 시작하고 불량 사진이나 설명을 어떻게 제출하나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help if you bought as a guest | — | 0.4644 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 2 | Get help if you bought as a guest | — | 0.4637 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 3 | Return an item for a refund | — | 0.4255 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 4 | Return an item for a refund | — | 0.4192 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 5 | Get help if you bought as a guest | — | 0.4144 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 6 | Get help if you bought as a guest | — | 0.4125 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 7 | Return an item for a refund | — | 0.4091 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 8 | Get help if you bought as a guest | — | 0.4005 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 9 | Return an item for a refund | — | 0.3910 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 10 | eBay Refurbished | — | 0.3891 | `83be0678-d0f2-40f2-aecf-6d4bc8d49f52` | — |
| 11 | eBay Money Back Guarantee policy | — | 0.3888 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 12 | Get help if you bought as a guest | — | 0.3883 | `0f5fa726-6d78-401f-bca3-39487acb99e2` | — |
| 13 | eBay Money Back Guarantee policy | — | 0.3824 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 14 | eBay Money Back Guarantee policy | — | 0.3798 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.3781 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

기존 상위 5개에 있던 핵심 근거가 유지됐다.

<a id="detailed-450-topk15-q15"></a>

#### 질문 15 · 포장 개봉과 환불액 차감

**질문 원문**

> 설명과 다른 이어폰을 반품했습니다. 포장 씰을 뜯어 5분간 작동을 확인한 뒤 케이블과 설명서까지 전부 돌려보냈습니다. 판매자가 개봉을 이유로 180달러 중 60달러를 차감했고 다른 훼손은 설명하지 않았습니다. 어떤 조건에서 차감에 이의를 제기하고 eBay에 도움을 요청할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Condition of returned items policy | — | 0.5061 | `5969c401-95f9-4001-99e5-4c8592208453` | — |
| 2 | How to handle a return request as a seller | — | 0.5060 | `1743ff1f-d3d4-4bdd-8248-211875998334` | — |
| 3 | Return an item for a refund | — | 0.4802 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 4 | Return an item for a refund | — | 0.4734 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 5 | How to handle a return request as a seller | — | 0.4664 | `6109adcf-ec42-4a95-818c-a2ef540a612d` | — |
| 6 | Condition of returned items policy | — | 0.4637 | `87e1df21-066e-49e9-863e-7b3455872aed` | — |
| 7 | Return an item for a refund | — | 0.4509 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 8 | Return an item for a refund | — | 0.4505 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 9 | eBay Money Back Guarantee policy | — | 0.4469 | `008dfe95-0e97-43ae-b30b-304d611fcf17` | — |
| 10 | eBay Money Back Guarantee policy | — | 0.4372 | `40df69ec-9ecf-41e0-a32a-982d935ab64a` | — |
| 11 | eBay Money Back Guarantee policy | — | 0.4360 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 12 | eBay Money Back Guarantee policy | — | 0.4358 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 13 | Get help if you bought as a guest | — | 0.4341 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 14 | Condition of returned items policy | — | 0.4332 | `0777d913-f3c3-482a-b418-ec20bf723bc5` | — |
| 15 | eBay Refurbished | — | 0.4322 | `83be0678-d0f2-40f2-aecf-6d4bc8d49f52` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

6위에서 사용 및 가치 하락 조건, 7위에서 구매자의 eBay 개입 요청 절차를 찾았다. 이미 일부 환불이 발행된 사례에 적용되는 직접적인 이의 제기 조건은 확인되지 않았다.

<a id="detailed-450-topk15-q16"></a>

#### 질문 16 · 국제 배송 파손과 수입 비용

**질문 원문**

> eBay International Shipping으로 주문한 310달러짜리 조명이 도착했을 때 유리 갓이 깨져 있었습니다. 결제 영수증에는 eBay에 낸 수입 비용 26달러가 있고, 배송 때 운송사에도 세금 명목으로 15달러를 냈습니다. 파손 반품이 승인되면 상품값 외에 두 곳에 낸 수입 비용을 각각 돌려받을 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4802 | `bf37f2b1-9659-40d3-844a-11545aaba365` | — |
| 2 | International purchases and shipping for buyers | — | 0.4731 | `76066a7d-2738-449c-9574-7d68d06faa54` | — |
| 3 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4717 | `936ad65f-d348-453a-9ada-fd5061587211` | — |
| 4 | International purchases and shipping for buyers | — | 0.4657 | `02ed01df-7637-4e68-8458-f8e01b540ce4` | — |
| 5 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4642 | `5dd13ab2-02b1-4a36-8e2b-1ba1e0162b44` | — |
| 6 | International purchases and shipping for buyers | — | 0.4631 | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` | — |
| 7 | eBay International Shipping Seller Terms & Conditions | — | 0.4577 | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` | — |
| 8 | International purchases and shipping for buyers | — | 0.4491 | `9da141f2-c82b-4943-9404-e162c739dda3` | — |
| 9 | eBay International Shipping Seller Terms & Conditions | — | 0.4479 | `aea220ac-fba6-4e07-b5e4-8d76311a2a21` | — |
| 10 | International purchases and shipping for buyers | — | 0.4441 | `45c4e413-c093-479c-b1a0-e97c51c95848` | — |
| 11 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4430 | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` | — |
| 12 | eBay International Shipping | Seller Center | — | 0.4385 | `ba80853d-fe28-4579-81c0-530b40ab121c` | — |
| 13 | Return shipping for buyers | — | 0.4262 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 14 | Return shipping for buyers | — | 0.4249 | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` | — |
| 15 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4197 | `89d3cde5-4330-49cf-872f-576d13c53acd` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

1위에서 eBay에 낸 수입 비용의 조건부 환급 근거를 유지했다. 운송사에 별도로 낸 비용의 환급 조건은 확인되지 않았다.

<a id="detailed-450-topk15-q17"></a>

#### 질문 17 · 예약 주문 배송 지연

**질문 원문**

> 8월에 예약 구매한 피규어의 상품 페이지에는 10월 발송 예정이라고 적혀 있었습니다. 11월 말에도 추적번호가 없고 판매자는 다시 다음 달로 미뤘습니다. 주문 후 30일은 지났으며 아직 미수령 신고는 열지 않았습니다. 지금 취소를 요청하거나 미수령으로 환불을 요청할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Order cancellation policy | — | 0.3859 | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` | — |
| 2 | Get help if you bought as a guest | — | 0.3654 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 3 | eBay Money Back Guarantee policy | — | 0.3645 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 4 | Get help if you bought as a guest | — | 0.3523 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 5 | Order cancellation policy | — | 0.3520 | `4f1d8091-f413-47f9-92b5-8fcf59780b03` | — |
| 6 | eBay Money Back Guarantee policy | — | 0.3498 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 7 | eBay Money Back Guarantee policy | — | 0.3366 | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` | — |
| 8 | eBay Money Back Guarantee policy | — | 0.3357 | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` | — |
| 9 | Get help if you bought as a guest | — | 0.3345 | `0f5fa726-6d78-401f-bca3-39487acb99e2` | — |
| 10 | Get help if you bought as a guest | — | 0.3338 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 11 | Order cancellation policy | — | 0.3319 | `6aa36185-3f49-40c5-bbee-fb0855dfcf39` | — |
| 12 | eBay Money Back Guarantee policy | — | 0.3281 | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` | — |
| 13 | eBay Money Back Guarantee policy | — | 0.3274 | `cdca8d5f-812a-4fed-a6a6-380989fdf86a` | — |
| 14 | eBay Authenticity Guarantee for Sneakers | — | 0.3222 | `b52463d6-3981-4dda-bbc0-5853e70bb899` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.3190 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

일반 주문 취소 및 미수령 안내는 검색됐으나 예약판매 정책은 15위 안에 없었다.

<a id="detailed-450-topk15-q18"></a>

#### 질문 18 · 발송 후 배송 주소 오류

**질문 원문**

> 결제 직후 확인해 보니 주문에 입력한 주소는 이사 전 아파트입니다. 판매자에게 새 주소를 보냈지만 이미 택배사에 넘겼고 추적은 배송 중입니다. 주문 주소를 수정하거나 올바른 주소로 돌려 보내 달라고 요청할 수 있나요? 기존 주소에서 받지 못하면 환불을 요청할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help if you bought as a guest | — | 0.3542 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 2 | eBay Money Back Guarantee policy | — | 0.3452 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 3 | Return an item for a refund | — | 0.3435 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 4 | Get help if you bought as a guest | — | 0.3432 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 5 | Return an item for a refund | — | 0.3359 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 6 | eBay Money Back Guarantee policy | — | 0.3278 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 7 | Get help if you bought as a guest | — | 0.3261 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 8 | Get help if you bought as a guest | — | 0.3199 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 9 | How to handle a return request as a seller | — | 0.3185 | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` | — |
| 10 | Return shipping for buyers | — | 0.3166 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 11 | Return an item for a refund | — | 0.3118 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 12 | eBay Money Back Guarantee policy | — | 0.3112 | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` | — |
| 13 | Order cancellation policy | — | 0.3111 | `4f1d8091-f413-47f9-92b5-8fcf59780b03` | — |
| 14 | Selling practices policy | — | 0.3095 | `eab21850-3ea7-4d49-9473-953d73e566b5` | — |
| 15 | Order cancellation policy | — | 0.3086 | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

4위에서 발송 후 취소 제한과 기존 주소에서의 수령 안내를 확인했다. 주소 오류 관련 보호 제외 및 재배송 조건은 확인되지 않았다.

<a id="detailed-450-topk15-q19"></a>

#### 질문 19 · 고가 상품 반송과 서명

**질문 원문**

> eBay.com에서 세금과 배송비까지 900달러인 상품을 구매했고 반품 배송비는 제가 결제했습니다. 추적번호를 반품 요청에 올렸고 판매자 주소로 배송 완료로 나오지만 서명 옵션을 사지 않았습니다. 판매자가 반송품을 받지 못했다고 하면 추적 기록만으로 반품 배송을 입증할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.5296 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 2 | Signature confirmation policy | — | 0.5271 | `604b7fbb-2462-4a94-a0a3-59fb5290d846` | — |
| 3 | Return shipping for buyers | — | 0.5209 | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` | — |
| 4 | Signature confirmation policy | — | 0.5068 | `e2b3df5e-28ee-48d2-b4f7-f78175e269b9` | — |
| 5 | eBay Money Back Guarantee policy | — | 0.4986 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 6 | eBay Money Back Guarantee policy | — | 0.4848 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 7 | Return shipping for buyers | — | 0.4807 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 8 | Get help if you bought as a guest | — | 0.4787 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 9 | Return shipping for buyers | — | 0.4769 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 10 | eBay International Shipping Seller Terms & Conditions | — | 0.4646 | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` | — |
| 11 | Get help if you bought as a guest | — | 0.4609 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 12 | eBay Money Back Guarantee policy | — | 0.4561 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |
| 13 | eBay Authenticity Guarantee for Sneakers | — | 0.4537 | `4c3b3f5c-f79d-4e33-8ac0-c3d09a75123f` | — |
| 14 | eBay Money Back Guarantee policy | — | 0.4490 | `cdca8d5f-812a-4fed-a6a6-380989fdf86a` | — |
| 15 | Return an item for a refund | — | 0.4482 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

기존 상위 5개에 있던 핵심 근거가 유지됐다.

<a id="detailed-450-topk15-q20"></a>

#### 질문 20 · 플랫폼 밖 부분 환불

**질문 원문**

> 240달러짜리 제품의 결함으로 eBay에서 반품 요청을 진행 중입니다. 판매자가 eBay 메시지로 외부 계좌이체로 70달러를 보내겠다고 제안했으며, 아직 송금은 받지 않았고 요청도 열려 있습니다. 송금을 받아도 남은 170달러에 대해 같은 요청에서 계속 다투거나 eBay 개입을 요청할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.4679 | `3b462389-7437-407c-87bd-ea2d91d04130` | — |
| 2 | Return an item for a refund | — | 0.4666 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 3 | eBay Money Back Guarantee policy | — | 0.4624 | `6e065bd8-ddcb-4159-88ec-51f7904be5f9` | — |
| 4 | How to handle a return request as a seller | — | 0.4604 | `1743ff1f-d3d4-4bdd-8248-211875998334` | — |
| 5 | Get help if you bought as a guest | — | 0.4522 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 6 | How to handle a return request as a seller | — | 0.4509 | `99dbe313-a5e6-473a-884d-9758d7490e93` | — |
| 7 | Get help if you bought as a guest | — | 0.4500 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 8 | eBay Money Back Guarantee policy | — | 0.4443 | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` | — |
| 9 | Return an item for a refund | — | 0.4432 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 10 | Return an item for a refund | — | 0.4418 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 11 | How to handle a return request as a seller | — | 0.4404 | `6109adcf-ec42-4a95-818c-a2ef540a612d` | — |
| 12 | How to handle a return request as a seller | — | 0.4402 | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` | — |
| 13 | How to handle a return request as a seller | — | 0.4396 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |
| 14 | Get help if you bought as a guest | — | 0.4394 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.4376 | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

일반 반품 및 중복 청구 조항은 찾았으나 외부 계좌이체 이후 남은 금액의 분쟁 가능 여부는 확인되지 않았다.

<a id="detailed-450-expanded-topk15"></a>

## 상세 구매자 질의 20개 · 450/200 · topK=15 · 문서 보강 · 2026-11-28

### 질문별 결과 요약

| 질문 | 주제 | 모델 출력 상태 | 반환 수 | 상태 출처 |
|---:|---|---|---:|---|
| [01](#detailed-450-expanded-topk15-q01) | 추적번호 없는 배송 지연 | — | 15 | 응답 생성 미실행 |
| [02](#detailed-450-expanded-topk15-q02) | 노트북 결함과 부분 환불 | — | 15 | 응답 생성 미실행 |
| [03](#detailed-450-expanded-topk15-q03) | 발송 전 주문 취소 | — | 15 | 응답 생성 미실행 |
| [04](#detailed-450-expanded-topk15-q04) | 단순 변심과 최초 배송비 | — | 15 | 응답 생성 미실행 |
| [05](#detailed-450-expanded-topk15-q05) | 파손 반품과 국제 반송비 | — | 15 | 응답 생성 미실행 |
| [06](#detailed-450-expanded-topk15-q06) | 교체 약속과 반품 요청 종료 | — | 15 | 응답 생성 미실행 |
| [07](#detailed-450-expanded-topk15-q07) | 카드사 결제 이의 제기 | — | 15 | 응답 생성 미실행 |
| [08](#detailed-450-expanded-topk15-q08) | 배대지 경유와 상품 불일치 | — | 15 | 응답 생성 미실행 |
| [09](#detailed-450-expanded-topk15-q09) | 한 주문의 일부 미수령 | — | 15 | 응답 생성 미실행 |
| [10](#detailed-450-expanded-topk15-q10) | 반송 완료 후 카드 환불 지연 | — | 15 | 응답 생성 미실행 |
| [11](#detailed-450-expanded-topk15-q11) | 반품 주소 불일치 | — | 15 | 응답 생성 미실행 |
| [12](#detailed-450-expanded-topk15-q12) | 고가 상품과 대리 수령 | — | 15 | 응답 생성 미실행 |
| [13](#detailed-450-expanded-topk15-q13) | 운동화 상태와 위조 의심 | — | 15 | 응답 생성 미실행 |
| [14](#detailed-450-expanded-topk15-q14) | 비회원 불량품 반품 | — | 15 | 응답 생성 미실행 |
| [15](#detailed-450-expanded-topk15-q15) | 포장 개봉과 환불액 차감 | — | 15 | 응답 생성 미실행 |
| [16](#detailed-450-expanded-topk15-q16) | 국제 배송 파손과 수입 비용 | — | 15 | 응답 생성 미실행 |
| [17](#detailed-450-expanded-topk15-q17) | 예약 주문 배송 지연 | — | 15 | 응답 생성 미실행 |
| [18](#detailed-450-expanded-topk15-q18) | 발송 후 배송 주소 오류 | — | 15 | 응답 생성 미실행 |
| [19](#detailed-450-expanded-topk15-q19) | 고가 상품 반송과 서명 | — | 15 | 응답 생성 미실행 |
| [20](#detailed-450-expanded-topk15-q20) | 플랫폼 밖 부분 환불 | — | 15 | 응답 생성 미실행 |

**집계** · 검색 20/20건 · 반환 300개 · 모델 응답 생성 미실행

**검색 관찰** · 신규 도움말이 14개 질문에서 22회 검색됐다. 미수령 안내 12회, 요청 상태 안내 10회, 구매자 이의 제기 안내 0회였다.

### 실행 조건

| 변경 항목 | 이번 실험 |
|---|---|
| 적재 문서 | eBay 공식 도움말 영문 요약본 3개 추가, 총 25개 문서·149개 청크 |

직전 실험의 450/200, topK=15, 동일 질문 20개와 임베딩 모델을 유지했다. 질문을 다시 임베딩했고, 반환 청크의 본문과 순위를 비교했다.

### 질문별 결과

<a id="detailed-450-expanded-topk15-q01"></a>

#### 질문 01 · 추적번호 없는 배송 지연

**질문 원문**

> 11월 18일 eBay.com에서 미국 판매자의 게임 컨트롤러를 결제했고 주문 화면의 배송 예정일은 11월 24일이었습니다. 11월 28일에도 물건을 못 받았고 판매자는 발송했다고만 답했으며 주문 상세에 추적번호가 없습니다. 지금 미수령 신고를 열 수 있나요? 신고 뒤 판매자의 응답을 언제까지 기다리고 어디에서 eBay에 도움을 요청하나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | — | 0.5946 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 2 | eBay Money Back Guarantee policy | — | 0.4925 | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` | — |
| 3 | eBay Money Back Guarantee policy | — | 0.4884 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 4 | Get help if you bought as a guest | — | 0.4619 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 5 | eBay Money Back Guarantee policy | — | 0.4601 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 6 | Get help if you bought as a guest | — | 0.4578 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 7 | eBay Money Back Guarantee policy | — | 0.4312 | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` | — |
| 8 | Check the status of your request | — | 0.4286 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 9 | eBay Money Back Guarantee policy | — | 0.4210 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 10 | Return an item for a refund | — | 0.4160 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 11 | eBay Money Back Guarantee policy | — | 0.4152 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |
| 12 | eBay Money Back Guarantee policy | — | 0.4041 | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` | — |
| 13 | Get help if you bought as a guest | — | 0.4017 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 14 | Selling practices policy | — | 0.4013 | `29dc7c6c-e4f2-4783-b537-a7c16da81994` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.4006 | `3b462389-7437-407c-87bd-ea2d91d04130` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

기존 상위 5개에 있던 핵심 근거가 유지됐다.

신규 문서 검색 순위 · 1위 미수령 상품, 8위 요청 상태.

<a id="detailed-450-expanded-topk15-q02"></a>

#### 질문 02 · 노트북 결함과 부분 환불

**질문 원문**

> 주문 설명에 화면 결함이 없다고 적힌 중고 노트북을 11월 25일 받았습니다. 첫날부터 화면이 깜박여 영상을 남겼고, 판매자는 수리비로 40달러를 주겠다고 했습니다. 아직 수락하지 않았고 판매 페이지는 반품 불가입니다. 결함 사유로 반품해 상품값과 최초 배송비를 돌려받을 수 있나요? 반송비는 누가 내나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.4005 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 2 | Return an item for a refund | — | 0.3891 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 3 | eBay Money Back Guarantee policy | — | 0.3826 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 4 | eBay Money Back Guarantee policy | — | 0.3818 | `77c77430-aceb-4f79-b632-ef78c863faf0` | — |
| 5 | eBay Money Back Guarantee policy | — | 0.3812 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |
| 6 | Condition of returned items policy | — | 0.3790 | `5969c401-95f9-4001-99e5-4c8592208453` | — |
| 7 | eBay Refurbished | — | 0.3767 | `14425763-a00d-4017-b045-aa88eb4d0695` | — |
| 8 | Get help if you bought as a guest | — | 0.3734 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 9 | eBay Refurbished | — | 0.3729 | `83be0678-d0f2-40f2-aecf-6d4bc8d49f52` | — |
| 10 | Get help if you bought as a guest | — | 0.3723 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 11 | How to handle a return request as a seller | — | 0.3716 | `1743ff1f-d3d4-4bdd-8248-211875998334` | — |
| 12 | How to handle a return request as a seller | — | 0.3692 | `0655d062-13e1-45e5-af4b-9a5e802f811c` | — |
| 13 | eBay International Shipping - Buyer Terms & Conditions | — | 0.3662 | `bf37f2b1-9659-40d3-844a-11545aaba365` | — |
| 14 | Return an item for a refund | — | 0.3651 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.3630 | `40df69ec-9ecf-41e0-a32a-982d935ab64a` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

기존 상위 5개에 있던 핵심 근거가 유지됐다.

<a id="detailed-450-expanded-topk15-q03"></a>

#### 질문 03 · 발송 전 주문 취소

**질문 원문**

> eBay.com에서 즉시구매로 헤드폰을 카드 결제한 지 20분 됐습니다. 다른 판매자가 더 싸게 파는 걸 발견해 주문 상세에서 취소를 요청하려고 합니다. 추적번호는 없고 발송 표시도 없습니다. 이 사유로 취소를 요청하면 판매자가 수락해야 하나요? 거절되거나 답이 없으면 주문은 어떻게 되나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Order cancellation policy | — | 0.5037 | `4f1d8091-f413-47f9-92b5-8fcf59780b03` | — |
| 2 | Order cancellation policy | — | 0.4983 | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` | — |
| 3 | Order cancellation policy | — | 0.4682 | `6aa36185-3f49-40c5-bbee-fb0855dfcf39` | — |
| 4 | Selling practices policy | — | 0.4574 | `29dc7c6c-e4f2-4783-b537-a7c16da81994` | — |
| 5 | Get help if you bought as a guest | — | 0.4169 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 6 | Get help with an item that hasn't arrived | — | 0.3959 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 7 | Return an item for a refund | — | 0.3942 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 8 | eBay Money Back Guarantee policy | — | 0.3908 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 9 | Get help if you bought as a guest | — | 0.3897 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 10 | How to handle a return request as a seller | — | 0.3880 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |
| 11 | Get help if you bought as a guest | — | 0.3871 | `0f5fa726-6d78-401f-bca3-39487acb99e2` | — |
| 12 | Check the status of your request | — | 0.3855 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 13 | Get help if you bought as a guest | — | 0.3835 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 14 | Get help if you bought as a guest | — | 0.3822 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 15 | Get help if you bought as a guest | — | 0.3746 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

기존 상위 5개에 있던 핵심 근거가 유지됐다.

신규 문서 검색 순위 · 6위 미수령 상품, 12위 요청 상태.

<a id="detailed-450-expanded-topk15-q04"></a>

#### 질문 04 · 단순 변심과 최초 배송비

**질문 원문**

> 검은색 M 사이즈 코트를 주문했고 받은 제품의 색상과 사이즈는 주문과 일치합니다. 실내에서 한 번 입어 봤고 택은 그대로며, 받은 지 4일 됐습니다. 반품 가능으로 표시된 주문에서 단순 변심 반품을 신청하면 결제 시 별도로 낸 최초 배송비 12달러도 환불되나요? 반송 라벨 비용은 누가 부담하나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.3598 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 2 | Return an item for a refund | — | 0.3411 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 3 | Get help if you bought as a guest | — | 0.3307 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 4 | eBay Money Back Guarantee policy | — | 0.3225 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 5 | Return an item for a refund | — | 0.3177 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 6 | Return an item for a refund | — | 0.3175 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 7 | Get help if you bought as a guest | — | 0.3137 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 8 | eBay Money Back Guarantee policy | — | 0.3101 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |
| 9 | Check the status of your request | — | 0.3092 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 10 | Get help if you bought as a guest | — | 0.3015 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 11 | eBay Money Back Guarantee policy | — | 0.3014 | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` | — |
| 12 | eBay Money Back Guarantee policy | — | 0.2976 | `3b462389-7437-407c-87bd-ea2d91d04130` | — |
| 13 | eBay Money Back Guarantee policy | — | 0.2967 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 14 | eBay Money Back Guarantee policy | — | 0.2961 | `40df69ec-9ecf-41e0-a32a-982d935ab64a` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.2938 | `77c77430-aceb-4f79-b632-ef78c863faf0` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

최초 배송비 제외 조항이 13위에서 14위로 이동했다.

신규 문서 검색 순위 · 9위 요청 상태.

<a id="detailed-450-expanded-topk15-q05"></a>

#### 질문 05 · 파손 반품과 국제 반송비

**질문 원문**

> 주문한 에스프레소 머신을 개봉하니 물탱크가 갈라져 있었고 배송 상자도 한쪽이 찌그러져 있었습니다. 받은 당일 사진을 찍어 반품을 신청했는데 판매자가 자기 반품 라벨을 보내는 대신 제가 먼저 국제 반송비를 내달라고 합니다. 누가 반송을 준비하고 비용을 부담하나요? 먼저 비용을 내도 되는지 어떻게 확인하나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return shipping for buyers | — | 0.4410 | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` | — |
| 2 | Return shipping for buyers | — | 0.4385 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 3 | Return shipping for buyers | — | 0.4142 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 4 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4057 | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` | — |
| 5 | eBay International Shipping Seller Terms & Conditions | — | 0.3969 | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` | — |
| 6 | How to handle a return request as a seller | — | 0.3954 | `c4954f02-57d2-46c7-8e54-0ed22200153e` | — |
| 7 | Return an item for a refund | — | 0.3940 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 8 | International purchases and shipping for buyers | — | 0.3905 | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` | — |
| 9 | eBay International Shipping - Buyer Terms & Conditions | — | 0.3892 | `bf37f2b1-9659-40d3-844a-11545aaba365` | — |
| 10 | Get help if you bought as a guest | — | 0.3878 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 11 | eBay International Shipping \| Seller Center | — | 0.3868 | `ba80853d-fe28-4579-81c0-530b40ab121c` | — |
| 12 | eBay Money Back Guarantee policy | — | 0.3858 | `77c77430-aceb-4f79-b632-ef78c863faf0` | — |
| 13 | How to handle a return request as a seller | — | 0.3838 | `0655d062-13e1-45e5-af4b-9a5e802f811c` | — |
| 14 | eBay International Shipping - Buyer Terms & Conditions | — | 0.3835 | `5dd13ab2-02b1-4a36-8e2b-1ba1e0162b44` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.3822 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

기존 상위 5개에 있던 핵심 근거가 유지됐다.

<a id="detailed-450-expanded-topk15-q06"></a>

#### 질문 06 · 교체 약속과 반품 요청 종료

**질문 원문**

> 다른 색상의 운동화가 와서 eBay에서 반품 요청을 열었습니다. 판매자는 메시지로 교체품을 보내겠다고 하면서 먼저 요청을 닫아 달라고 합니다. 교체품 추적번호는 아직 없습니다. 요청을 닫은 뒤 교체품이 오지 않으면 같은 주문의 반품 요청을 다시 열거나 eBay에 도움을 요청할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | — | 0.4961 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 2 | Get help if you bought as a guest | — | 0.4746 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 3 | Check the status of your request | — | 0.4635 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 4 | Get help if you bought as a guest | — | 0.4587 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 5 | Return shipping for buyers | — | 0.4446 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 6 | eBay Money Back Guarantee policy | — | 0.4434 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 7 | Return an item for a refund | — | 0.4391 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 8 | Return shipping for buyers | — | 0.4384 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 9 | Return an item for a refund | — | 0.4334 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 10 | How to handle a return request as a seller | — | 0.4316 | `0655d062-13e1-45e5-af4b-9a5e802f811c` | — |
| 11 | Get help if you bought as a guest | — | 0.4281 | `0f5fa726-6d78-401f-bca3-39487acb99e2` | — |
| 12 | How to handle a return request as a seller | — | 0.4253 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |
| 13 | Get help if you bought as a guest | — | 0.4252 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 14 | Get help if you bought as a guest | — | 0.4251 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 15 | How to handle a return request as a seller | — | 0.4216 | `99dbe313-a5e6-473a-884d-9758d7490e93` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

신규 요청 상태 안내가 3위에서 종료한 요청은 다시 열 수 없다는 근거를 추가했다.

신규 문서 검색 순위 · 1위 미수령 상품, 3위 요청 상태.

<a id="detailed-450-expanded-topk15-q07"></a>

#### 질문 07 · 카드사 결제 이의 제기

**질문 원문**

> 배송 예정일이 사흘 지난 카메라를 받지 못해 eBay에서 미수령 요청을 열었습니다. 판매자 답변을 기다리는 중인데 카드사는 카드 결제에 대한 이의 제기를 접수할 수 있다고 합니다. 지금 카드사에도 같은 결제 건을 접수하면 진행 중인 eBay 요청과 구매자 보호는 어떻게 되나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | — | 0.5307 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 2 | eBay Money Back Guarantee policy | — | 0.4960 | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` | — |
| 3 | Check the status of your request | — | 0.4882 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 4 | Get help if you bought as a guest | — | 0.4827 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 5 | eBay Money Back Guarantee policy | — | 0.4773 | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` | — |
| 6 | Return an item for a refund | — | 0.4730 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 7 | eBay Money Back Guarantee policy | — | 0.4651 | `3b462389-7437-407c-87bd-ea2d91d04130` | — |
| 8 | eBay Money Back Guarantee policy | — | 0.4565 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 9 | Get help if you bought as a guest | — | 0.4563 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 10 | eBay Money Back Guarantee policy | — | 0.4553 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 11 | eBay Money Back Guarantee policy | — | 0.4491 | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` | — |
| 12 | Order cancellation policy | — | 0.4477 | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` | — |
| 13 | eBay Money Back Guarantee policy | — | 0.4470 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 14 | How to handle a return request as a seller | — | 0.4416 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.4410 | `6e065bd8-ddcb-4159-88ec-51f7904be5f9` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

카드사 이의 제기 시 eBay 사건을 닫을 수 있다는 핵심 조항이 13위에서 15위로 이동했다.

신규 문서 검색 순위 · 1위 미수령 상품, 3위 요청 상태.

<a id="detailed-450-expanded-topk15-q08"></a>

#### 질문 08 · 배대지 경유와 상품 불일치

**질문 원문**

> 미국 판매자의 재킷을 한국에서 사면서 미국 배대지 주소를 eBay 주문 주소로 넣었습니다. 추적에는 배대지 도착으로 나오고 배대지는 상자를 한국으로 다시 보냈습니다. 한국에서 열어 보니 다른 색상의 재킷이 들어 있었는데 배대지에서는 개봉 사진을 찍지 않았습니다. eBay에서 상품 설명 불일치로 반품하거나 환불받을 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.4907 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 2 | Return an item for a refund | — | 0.4667 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 3 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4539 | `bf37f2b1-9659-40d3-844a-11545aaba365` | — |
| 4 | Return an item for a refund | — | 0.4458 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 5 | eBay Money Back Guarantee policy | — | 0.4455 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 6 | Get help if you bought as a guest | — | 0.4417 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 7 | eBay International Shipping Seller Terms & Conditions | — | 0.4400 | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` | — |
| 8 | eBay Money Back Guarantee policy | — | 0.4378 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 9 | Get help if you bought as a guest | — | 0.4367 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 10 | International purchases and shipping for buyers | — | 0.4364 | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` | — |
| 11 | Return an item for a refund | — | 0.4347 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 12 | Return shipping for buyers | — | 0.4325 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 13 | eBay Money Back Guarantee policy | — | 0.4312 | `3b462389-7437-407c-87bd-ea2d91d04130` | — |
| 14 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4296 | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.4293 | `cdca8d5f-812a-4fed-a6a6-380989fdf86a` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

제3자 배대지의 보호 제외 및 공식 프로그램 예외는 15위에 유지됐다.

<a id="detailed-450-expanded-topk15-q09"></a>

#### 질문 09 · 한 주문의 일부 미수령

**질문 원문**

> 한 판매자에게서 카메라와 메모리카드를 각각 장바구니에 넣어 한 번에 결제했습니다. 두 상품의 가격은 주문에 따로 적혀 있고 같은 추적번호가 붙어 있는데 상자에는 카메라만 있었습니다. 메모리카드만 받거나 그 금액만 환불받을 수 있나요? 한 상품 페이지에서 세트로 판매된 경우에도 같은 절차인가요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.3957 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 2 | Get help if you bought as a guest | — | 0.3601 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 3 | Return an item for a refund | — | 0.3588 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 4 | Get help if you bought as a guest | — | 0.3517 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 5 | Get help if you bought as a guest | — | 0.3461 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 6 | Get help with an item that hasn't arrived | — | 0.3440 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 7 | eBay International Shipping \| Seller Center | — | 0.3366 | `ba80853d-fe28-4579-81c0-530b40ab121c` | — |
| 8 | Condition of returned items policy | — | 0.3349 | `5969c401-95f9-4001-99e5-4c8592208453` | — |
| 9 | Return an item for a refund | — | 0.3322 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 10 | eBay International Shipping - Buyer Terms & Conditions | — | 0.3305 | `bf37f2b1-9659-40d3-844a-11545aaba365` | — |
| 11 | International purchases and shipping for buyers | — | 0.3304 | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` | — |
| 12 | eBay Money Back Guarantee policy | — | 0.3297 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 13 | eBay Money Back Guarantee policy | — | 0.3271 | `0b3a3fdf-2b2a-485b-b84f-eace87156c0e` | — |
| 14 | Get help if you bought as a guest | — | 0.3254 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.3246 | `cdca8d5f-812a-4fed-a6a6-380989fdf86a` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

신규 미수령 안내가 6위에서 개별 품목 미수령 신고와 세트 반품을 구분했다.

신규 문서 검색 순위 · 6위 미수령 상품.

<a id="detailed-450-expanded-topk15-q10"></a>

#### 질문 10 · 반송 완료 후 카드 환불 지연

**질문 원문**

> 판매자가 반송 상자를 11월 19일 받았다고 메시지로 확인했고, 추적도 배송 완료입니다. 11월 28일 현재 카드 명세에 환불이 없고 판매자는 환불을 발행한 날짜를 알려주지 않았습니다. 반품 상세에서 무엇을 확인해야 판매자가 아직 환불을 발행하지 않은 건지 카드에 반영되는 중인지 구분할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Check the status of your request | — | 0.4657 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 2 | eBay Money Back Guarantee policy | — | 0.4539 | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` | — |
| 3 | eBay Money Back Guarantee policy | — | 0.4435 | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` | — |
| 4 | Get help if you bought as a guest | — | 0.4412 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 5 | eBay Money Back Guarantee policy | — | 0.4401 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 6 | Return an item for a refund | — | 0.4398 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 7 | Get help with an item that hasn't arrived | — | 0.4381 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 8 | eBay Money Back Guarantee policy | — | 0.4330 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |
| 9 | eBay Money Back Guarantee policy | — | 0.4252 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 10 | eBay Money Back Guarantee policy | — | 0.4252 | `3b462389-7437-407c-87bd-ea2d91d04130` | — |
| 11 | Return an item for a refund | — | 0.4244 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 12 | eBay Money Back Guarantee policy | — | 0.4240 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 13 | How to handle a return request as a seller | — | 0.4161 | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` | — |
| 14 | How to handle a return request as a seller | — | 0.4141 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.4119 | `40df69ec-9ecf-41e0-a32a-982d935ab64a` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

신규 요청 상태 안내가 1위에서 환불 처리 기간 및 약속한 환불 미발행 시 후속 절차를 보강했다.

신규 문서 검색 순위 · 1위 요청 상태, 7위 미수령 상품.

<a id="detailed-450-expanded-topk15-q11"></a>

#### 질문 11 · 반품 주소 불일치

**질문 원문**

> eBay 반품 요청에서 받은 선불 라벨에는 캘리포니아 주소가 찍혀 있는데 판매자가 메시지로 네바다 창고 주소에 보내라고 합니다. 반품 상세에는 새 라벨이 없고 발송 기한은 이틀 남았습니다. 어느 주소로 보내야 하나요? 나중에 분쟁이 생기면 어떤 추적 또는 배송 증빙이 필요한가요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return shipping for buyers | — | 0.4412 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 2 | Return shipping for buyers | — | 0.4223 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 3 | Return shipping for buyers | — | 0.4204 | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` | — |
| 4 | Check the status of your request | — | 0.4171 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 5 | How to handle a return request as a seller | — | 0.4075 | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` | — |
| 6 | eBay Money Back Guarantee policy | — | 0.4018 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 7 | How to handle a return request as a seller | — | 0.3966 | `c4954f02-57d2-46c7-8e54-0ed22200153e` | — |
| 8 | How to handle a return request as a seller | — | 0.3936 | `0655d062-13e1-45e5-af4b-9a5e802f811c` | — |
| 9 | Return an item for a refund | — | 0.3927 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 10 | Get help with an item that hasn't arrived | — | 0.3866 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 11 | How to handle a return request as a seller | — | 0.3823 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |
| 12 | How to handle a return request as a seller | — | 0.3796 | `00ab85d7-00dc-4e04-bc46-416a6a759751` | — |
| 13 | eBay Money Back Guarantee policy | — | 0.3793 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |
| 14 | How to handle a return request as a seller | — | 0.3775 | `d58dd7b1-f4d6-47ba-b7ae-81501242f723` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.3759 | `77c77430-aceb-4f79-b632-ef78c863faf0` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

11·13위에서 판매자 지정 반송 주소와 설정된 주소 관련 요건을 찾았다. 공식 라벨과 메시지가 충돌할 때의 우선순위는 확인되지 않았다.

신규 문서 검색 순위 · 4위 요청 상태, 10위 미수령 상품.

<a id="detailed-450-expanded-topk15-q12"></a>

#### 질문 12 · 고가 상품과 대리 수령

**질문 원문**

> 배송비와 세금까지 1,080달러인 카메라를 eBay.com에서 주문해 친구 집을 주문 주소로 지정했습니다. 추적에는 배송 완료라고 나오지만 친구는 상자를 받지 않았고 판매자는 배송 완료 화면만 보여줬습니다. 주문 주소와 배송 증거가 일치하는지와 서명 기록을 확인해 달라고 할 수 있나요? 친구가 실제 대리 수령했다면 판단이 달라지나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.4653 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 2 | Get help with an item that hasn't arrived | — | 0.4564 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 3 | eBay Money Back Guarantee policy | — | 0.4489 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 4 | eBay Money Back Guarantee policy | — | 0.4375 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 5 | International purchases and shipping for buyers | — | 0.4227 | `02ed01df-7637-4e68-8458-f8e01b540ce4` | — |
| 6 | Signature confirmation policy | — | 0.4169 | `604b7fbb-2462-4a94-a0a3-59fb5290d846` | — |
| 7 | Get help if you bought as a guest | — | 0.4076 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 8 | eBay International Shipping Seller Terms & Conditions | — | 0.4051 | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` | — |
| 9 | International purchases and shipping for buyers | — | 0.4046 | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` | — |
| 10 | Signature confirmation policy | — | 0.4033 | `e2b3df5e-28ee-48d2-b4f7-f78175e269b9` | — |
| 11 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4031 | `88ee7312-806c-49e9-875f-f20a8881ce44` | — |
| 12 | International purchases and shipping for buyers | — | 0.3982 | `76066a7d-2738-449c-9574-7d68d06faa54` | — |
| 13 | eBay Money Back Guarantee policy | — | 0.3925 | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` | — |
| 14 | Get help if you bought as a guest | — | 0.3851 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 15 | eBay International Shipping - Buyer Terms & Conditions | — | 0.3822 | `5dd13ab2-02b1-4a36-8e2b-1ba1e0162b44` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

기존 상위 5개에 있던 핵심 근거가 유지됐다.

신규 문서 검색 순위 · 2위 미수령 상품.

<a id="detailed-450-expanded-topk15-q13"></a>

#### 질문 13 · 운동화 상태와 위조 의심

**질문 원문**

> 새 제품으로 표시된 운동화를 받았는데 밑창에 흙과 마모 흔적이 있고 상자와 신발의 제품 코드도 다릅니다. 주문 화면에서 정품 인증 대상 표시를 확인하지 못했고 받은 지 이틀 됐습니다. 상태가 설명과 다르다는 사유와 위조 의심 중 어떤 절차로 문제를 제기할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Authenticity Guarantee for Sneakers | — | 0.4879 | `a8720eaf-1dbd-4eaa-929a-d067e5780bf1` | — |
| 2 | eBay Authenticity Guarantee for Sneakers | — | 0.4830 | `668ef1c6-a0e2-4a5d-bf03-c24cc6e00f89` | — |
| 3 | eBay Authenticity Guarantee for Sneakers | — | 0.4613 | `bd335ae7-a208-4fb1-a080-26f3ce45688f` | — |
| 4 | eBay Authenticity Guarantee for Sneakers | — | 0.4364 | `909603f2-3b0e-4a92-b440-b3b76df9df36` | — |
| 5 | eBay Authenticity Guarantee for Sneakers | — | 0.4306 | `b52463d6-3981-4dda-bbc0-5853e70bb899` | — |
| 6 | eBay Authenticity Guarantee for Sneakers | — | 0.4282 | `28a46454-f148-412a-b6be-827a11666c92` | — |
| 7 | eBay Authenticity Guarantee for Sneakers | — | 0.4181 | `4493c75d-f79a-4cd5-b15e-e7c847137741` | — |
| 8 | eBay Authenticity Guarantee for Sneakers | — | 0.4161 | `d7ea9541-18ed-4a3b-860e-44597608060c` | — |
| 9 | eBay Authenticity Guarantee for Sneakers | — | 0.4151 | `4c3b3f5c-f79d-4e33-8ac0-c3d09a75123f` | — |
| 10 | eBay Authenticity Guarantee for Sneakers | — | 0.4137 | `310c3728-23b8-46d5-88f9-cfccc4aa62bc` | — |
| 11 | Buying with Authenticity Guarantee | — | 0.4101 | `79a064e9-afda-4eb4-8f2b-171482196f23` | — |
| 12 | eBay Authenticity Guarantee for Sneakers | — | 0.4004 | `28c18aa9-8df1-4134-bfa2-934df227a276` | — |
| 13 | eBay Authenticity Guarantee for Sneakers | — | 0.4001 | `a2c75035-7ecb-4809-a425-bb9a5655986c` | — |
| 14 | eBay Authenticity Guarantee for Sneakers | — | 0.3951 | `b3af4677-12f3-44be-8750-6cb51b7529b9` | — |
| 15 | eBay Authenticity Guarantee for Sneakers | — | 0.3786 | `b7fadb64-8245-4379-8046-a7ababfa5da8` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

15개 중 14개가 스니커즈 인증 안내였다. 일반 위조 의심 신고·반품 절차를 확인하지 못했다.

<a id="detailed-450-expanded-topk15-q14"></a>

#### 질문 14 · 비회원 불량품 반품

**질문 원문**

> eBay 계정 없이 신용카드로 결제했고 주문 확인 이메일은 있지만 로그인할 계정이 없습니다. 어제 도착한 블루투스 스피커는 전원이 켜지지 않습니다. 이메일에서 어디로 들어가 반품을 시작하고 불량 사진이나 설명을 어떻게 제출하나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help if you bought as a guest | — | 0.4644 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 2 | Get help if you bought as a guest | — | 0.4637 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 3 | Return an item for a refund | — | 0.4255 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 4 | Return an item for a refund | — | 0.4192 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 5 | Get help with an item that hasn't arrived | — | 0.4185 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 6 | Get help if you bought as a guest | — | 0.4144 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 7 | Get help if you bought as a guest | — | 0.4125 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 8 | Return an item for a refund | — | 0.4091 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 9 | Get help if you bought as a guest | — | 0.4005 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 10 | Return an item for a refund | — | 0.3910 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 11 | eBay Refurbished | — | 0.3891 | `83be0678-d0f2-40f2-aecf-6d4bc8d49f52` | — |
| 12 | eBay Money Back Guarantee policy | — | 0.3888 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 13 | Get help if you bought as a guest | — | 0.3883 | `0f5fa726-6d78-401f-bca3-39487acb99e2` | — |
| 14 | eBay Money Back Guarantee policy | — | 0.3824 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.3798 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

기존 상위 5개에 있던 핵심 근거가 유지됐다.

신규 문서 검색 순위 · 5위 미수령 상품.

<a id="detailed-450-expanded-topk15-q15"></a>

#### 질문 15 · 포장 개봉과 환불액 차감

**질문 원문**

> 설명과 다른 이어폰을 반품했습니다. 포장 씰을 뜯어 5분간 작동을 확인한 뒤 케이블과 설명서까지 전부 돌려보냈습니다. 판매자가 개봉을 이유로 180달러 중 60달러를 차감했고 다른 훼손은 설명하지 않았습니다. 어떤 조건에서 차감에 이의를 제기하고 eBay에 도움을 요청할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Condition of returned items policy | — | 0.5061 | `5969c401-95f9-4001-99e5-4c8592208453` | — |
| 2 | How to handle a return request as a seller | — | 0.5060 | `1743ff1f-d3d4-4bdd-8248-211875998334` | — |
| 3 | Return an item for a refund | — | 0.4802 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 4 | Return an item for a refund | — | 0.4734 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 5 | How to handle a return request as a seller | — | 0.4664 | `6109adcf-ec42-4a95-818c-a2ef540a612d` | — |
| 6 | Condition of returned items policy | — | 0.4637 | `87e1df21-066e-49e9-863e-7b3455872aed` | — |
| 7 | Return an item for a refund | — | 0.4509 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 8 | Return an item for a refund | — | 0.4505 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 9 | eBay Money Back Guarantee policy | — | 0.4469 | `008dfe95-0e97-43ae-b30b-304d611fcf17` | — |
| 10 | eBay Money Back Guarantee policy | — | 0.4372 | `40df69ec-9ecf-41e0-a32a-982d935ab64a` | — |
| 11 | eBay Money Back Guarantee policy | — | 0.4360 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 12 | eBay Money Back Guarantee policy | — | 0.4358 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 13 | Get help if you bought as a guest | — | 0.4341 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 14 | Condition of returned items policy | — | 0.4332 | `0777d913-f3c3-482a-b418-ec20bf723bc5` | — |
| 15 | eBay Refurbished | — | 0.4322 | `83be0678-d0f2-40f2-aecf-6d4bc8d49f52` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

6위에서 사용 및 가치 하락 조건, 7위에서 구매자의 eBay 개입 요청 절차를 찾았다. 이미 일부 환불이 발행된 사례에 적용되는 직접적인 이의 제기 조건은 확인되지 않았다.

<a id="detailed-450-expanded-topk15-q16"></a>

#### 질문 16 · 국제 배송 파손과 수입 비용

**질문 원문**

> eBay International Shipping으로 주문한 310달러짜리 조명이 도착했을 때 유리 갓이 깨져 있었습니다. 결제 영수증에는 eBay에 낸 수입 비용 26달러가 있고, 배송 때 운송사에도 세금 명목으로 15달러를 냈습니다. 파손 반품이 승인되면 상품값 외에 두 곳에 낸 수입 비용을 각각 돌려받을 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4802 | `bf37f2b1-9659-40d3-844a-11545aaba365` | — |
| 2 | International purchases and shipping for buyers | — | 0.4731 | `76066a7d-2738-449c-9574-7d68d06faa54` | — |
| 3 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4717 | `936ad65f-d348-453a-9ada-fd5061587211` | — |
| 4 | International purchases and shipping for buyers | — | 0.4657 | `02ed01df-7637-4e68-8458-f8e01b540ce4` | — |
| 5 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4642 | `5dd13ab2-02b1-4a36-8e2b-1ba1e0162b44` | — |
| 6 | International purchases and shipping for buyers | — | 0.4631 | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` | — |
| 7 | eBay International Shipping Seller Terms & Conditions | — | 0.4577 | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` | — |
| 8 | International purchases and shipping for buyers | — | 0.4491 | `9da141f2-c82b-4943-9404-e162c739dda3` | — |
| 9 | eBay International Shipping Seller Terms & Conditions | — | 0.4479 | `aea220ac-fba6-4e07-b5e4-8d76311a2a21` | — |
| 10 | International purchases and shipping for buyers | — | 0.4441 | `45c4e413-c093-479c-b1a0-e97c51c95848` | — |
| 11 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4430 | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` | — |
| 12 | eBay International Shipping \| Seller Center | — | 0.4385 | `ba80853d-fe28-4579-81c0-530b40ab121c` | — |
| 13 | Return shipping for buyers | — | 0.4262 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 14 | Return shipping for buyers | — | 0.4249 | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` | — |
| 15 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4197 | `89d3cde5-4330-49cf-872f-576d13c53acd` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

1위에서 eBay에 낸 수입 비용의 조건부 환급 근거를 유지했다. 운송사에 별도로 낸 비용의 환급 조건은 확인되지 않았다.

<a id="detailed-450-expanded-topk15-q17"></a>

#### 질문 17 · 예약 주문 배송 지연

**질문 원문**

> 8월에 예약 구매한 피규어의 상품 페이지에는 10월 발송 예정이라고 적혀 있었습니다. 11월 말에도 추적번호가 없고 판매자는 다시 다음 달로 미뤘습니다. 주문 후 30일은 지났으며 아직 미수령 신고는 열지 않았습니다. 지금 취소를 요청하거나 미수령으로 환불을 요청할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | — | 0.3917 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 2 | Order cancellation policy | — | 0.3860 | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` | — |
| 3 | Get help if you bought as a guest | — | 0.3656 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 4 | eBay Money Back Guarantee policy | — | 0.3649 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 5 | Get help if you bought as a guest | — | 0.3525 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 6 | Order cancellation policy | — | 0.3520 | `4f1d8091-f413-47f9-92b5-8fcf59780b03` | — |
| 7 | eBay Money Back Guarantee policy | — | 0.3502 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 8 | Check the status of your request | — | 0.3473 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 9 | eBay Money Back Guarantee policy | — | 0.3367 | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` | — |
| 10 | eBay Money Back Guarantee policy | — | 0.3361 | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` | — |
| 11 | Get help if you bought as a guest | — | 0.3346 | `0f5fa726-6d78-401f-bca3-39487acb99e2` | — |
| 12 | Get help if you bought as a guest | — | 0.3340 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 13 | Order cancellation policy | — | 0.3319 | `6aa36185-3f49-40c5-bbee-fb0855dfcf39` | — |
| 14 | eBay Money Back Guarantee policy | — | 0.3284 | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.3276 | `cdca8d5f-812a-4fed-a6a6-380989fdf86a` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

신규 미수령 안내가 1위에 진입했으나 예약판매 정책은 상위 15개에 없었다.

신규 문서 검색 순위 · 1위 미수령 상품, 8위 요청 상태.

<a id="detailed-450-expanded-topk15-q18"></a>

#### 질문 18 · 발송 후 배송 주소 오류

**질문 원문**

> 결제 직후 확인해 보니 주문에 입력한 주소는 이사 전 아파트입니다. 판매자에게 새 주소를 보냈지만 이미 택배사에 넘겼고 추적은 배송 중입니다. 주문 주소를 수정하거나 올바른 주소로 돌려 보내 달라고 요청할 수 있나요? 기존 주소에서 받지 못하면 환불을 요청할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | — | 0.3592 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 2 | Get help if you bought as a guest | — | 0.3544 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 3 | eBay Money Back Guarantee policy | — | 0.3453 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 4 | Return an item for a refund | — | 0.3438 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 5 | Get help if you bought as a guest | — | 0.3434 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 6 | Return an item for a refund | — | 0.3361 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 7 | Check the status of your request | — | 0.3345 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 8 | eBay Money Back Guarantee policy | — | 0.3279 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 9 | Get help if you bought as a guest | — | 0.3264 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 10 | Get help if you bought as a guest | — | 0.3201 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 11 | How to handle a return request as a seller | — | 0.3188 | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` | — |
| 12 | Return shipping for buyers | — | 0.3170 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 13 | Return an item for a refund | — | 0.3121 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 14 | Order cancellation policy | — | 0.3114 | `4f1d8091-f413-47f9-92b5-8fcf59780b03` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.3113 | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

잘못된 배송지에 관한 기존 게스트 안내가 5위에서 검색됐다. 보호 제외와 재배송 조건은 확인되지 않았다.

신규 문서 검색 순위 · 1위 미수령 상품, 7위 요청 상태.

<a id="detailed-450-expanded-topk15-q19"></a>

#### 질문 19 · 고가 상품 반송과 서명

**질문 원문**

> eBay.com에서 세금과 배송비까지 900달러인 상품을 구매했고 반품 배송비는 제가 결제했습니다. 추적번호를 반품 요청에 올렸고 판매자 주소로 배송 완료로 나오지만 서명 옵션을 사지 않았습니다. 판매자가 반송품을 받지 못했다고 하면 추적 기록만으로 반품 배송을 입증할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.5296 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 2 | Signature confirmation policy | — | 0.5279 | `604b7fbb-2462-4a94-a0a3-59fb5290d846` | — |
| 3 | Return shipping for buyers | — | 0.5221 | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` | — |
| 4 | Signature confirmation policy | — | 0.5071 | `e2b3df5e-28ee-48d2-b4f7-f78175e269b9` | — |
| 5 | eBay Money Back Guarantee policy | — | 0.4996 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 6 | eBay Money Back Guarantee policy | — | 0.4852 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 7 | Return shipping for buyers | — | 0.4824 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 8 | Get help if you bought as a guest | — | 0.4784 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 9 | Return shipping for buyers | — | 0.4776 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 10 | Get help with an item that hasn't arrived | — | 0.4708 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 11 | eBay International Shipping Seller Terms & Conditions | — | 0.4648 | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` | — |
| 12 | Get help if you bought as a guest | — | 0.4601 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 13 | eBay Money Back Guarantee policy | — | 0.4567 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |
| 14 | eBay Authenticity Guarantee for Sneakers | — | 0.4551 | `4c3b3f5c-f79d-4e33-8ac0-c3d09a75123f` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.4503 | `cdca8d5f-812a-4fed-a6a6-380989fdf86a` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

기존 상위 5개에 있던 핵심 근거가 유지됐다.

신규 문서 검색 순위 · 10위 미수령 상품.

<a id="detailed-450-expanded-topk15-q20"></a>

#### 질문 20 · 플랫폼 밖 부분 환불

**질문 원문**

> 240달러짜리 제품의 결함으로 eBay에서 반품 요청을 진행 중입니다. 판매자가 eBay 메시지로 외부 계좌이체로 70달러를 보내겠다고 제안했으며, 아직 송금은 받지 않았고 요청도 열려 있습니다. 송금을 받아도 남은 170달러에 대해 같은 요청에서 계속 다투거나 eBay 개입을 요청할 수 있나요?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.4680 | `3b462389-7437-407c-87bd-ea2d91d04130` | — |
| 2 | Return an item for a refund | — | 0.4666 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 3 | eBay Money Back Guarantee policy | — | 0.4623 | `6e065bd8-ddcb-4159-88ec-51f7904be5f9` | — |
| 4 | How to handle a return request as a seller | — | 0.4605 | `1743ff1f-d3d4-4bdd-8248-211875998334` | — |
| 5 | Get help if you bought as a guest | — | 0.4520 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 6 | Check the status of your request | — | 0.4516 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 7 | How to handle a return request as a seller | — | 0.4507 | `99dbe313-a5e6-473a-884d-9758d7490e93` | — |
| 8 | Get help if you bought as a guest | — | 0.4503 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 9 | eBay Money Back Guarantee policy | — | 0.4445 | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` | — |
| 10 | Return an item for a refund | — | 0.4436 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 11 | Return an item for a refund | — | 0.4419 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 12 | How to handle a return request as a seller | — | 0.4405 | `6109adcf-ec42-4a95-818c-a2ef540a612d` | — |
| 13 | How to handle a return request as a seller | — | 0.4403 | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` | — |
| 14 | How to handle a return request as a seller | — | 0.4395 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |
| 15 | Get help if you bought as a guest | — | 0.4393 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

일반 반품 및 중복 청구 조항은 찾았으나 외부 계좌이체 이후 남은 금액의 분쟁 가능 여부는 확인되지 않았다.

신규 문서 검색 순위 · 6위 요청 상태.

<a id="detailed-450-english-topk15"></a>

## 상세 구매자 질의 20개 · 450/200 · topK=15 · 영어 질의 · 2026-11-28

### 질문별 결과 요약

| 질문 | 주제 | 모델 출력 상태 | 반환 수 | 상태 출처 |
|---:|---|---|---:|---|
| [01](#detailed-450-english-topk15-q01) | 추적번호 없는 배송 지연 | — | 15 | 응답 생성 미실행 |
| [02](#detailed-450-english-topk15-q02) | 노트북 결함과 부분 환불 | — | 15 | 응답 생성 미실행 |
| [03](#detailed-450-english-topk15-q03) | 발송 전 주문 취소 | — | 15 | 응답 생성 미실행 |
| [04](#detailed-450-english-topk15-q04) | 단순 변심과 최초 배송비 | — | 15 | 응답 생성 미실행 |
| [05](#detailed-450-english-topk15-q05) | 파손 반품과 국제 반송비 | — | 15 | 응답 생성 미실행 |
| [06](#detailed-450-english-topk15-q06) | 교체 약속과 반품 요청 종료 | — | 15 | 응답 생성 미실행 |
| [07](#detailed-450-english-topk15-q07) | 카드사 결제 이의 제기 | — | 15 | 응답 생성 미실행 |
| [08](#detailed-450-english-topk15-q08) | 배대지 경유와 상품 불일치 | — | 15 | 응답 생성 미실행 |
| [09](#detailed-450-english-topk15-q09) | 한 주문의 일부 미수령 | — | 15 | 응답 생성 미실행 |
| [10](#detailed-450-english-topk15-q10) | 반송 완료 후 카드 환불 지연 | — | 15 | 응답 생성 미실행 |
| [11](#detailed-450-english-topk15-q11) | 반품 주소 불일치 | — | 15 | 응답 생성 미실행 |
| [12](#detailed-450-english-topk15-q12) | 고가 상품과 대리 수령 | — | 15 | 응답 생성 미실행 |
| [13](#detailed-450-english-topk15-q13) | 운동화 상태와 위조 의심 | — | 15 | 응답 생성 미실행 |
| [14](#detailed-450-english-topk15-q14) | 비회원 불량품 반품 | — | 15 | 응답 생성 미실행 |
| [15](#detailed-450-english-topk15-q15) | 포장 개봉과 환불액 차감 | — | 15 | 응답 생성 미실행 |
| [16](#detailed-450-english-topk15-q16) | 국제 배송 파손과 수입 비용 | — | 15 | 응답 생성 미실행 |
| [17](#detailed-450-english-topk15-q17) | 예약 주문 배송 지연 | — | 15 | 응답 생성 미실행 |
| [18](#detailed-450-english-topk15-q18) | 발송 후 배송 주소 오류 | — | 15 | 응답 생성 미실행 |
| [19](#detailed-450-english-topk15-q19) | 고가 상품 반송과 서명 | — | 15 | 응답 생성 미실행 |
| [20](#detailed-450-english-topk15-q20) | 플랫폼 밖 부분 환불 | — | 15 | 응답 생성 미실행 |

**집계** · 검색 20/20건 · 반환 300개 · 모델 응답 생성 미실행

**검색 관찰** · 일부 미수령 안내는 6위에서 1위로 올랐다. 최초 배송비 제외와 배대지 조항은 영어 상위 15개에서 빠졌다.

### 실행 조건

| 변경 항목 | 이번 실험 |
|---|---|
| 임베딩 질의 | 동일 질문 20개를 영어로 번역. 거래 사실과 요청 범위 유지 |

직전 실험과 동일한 문서 25개·149개 청크, 450/200, topK=15, 임베딩 모델을 사용했다. 검색 단계만 실행하고 반환 청크를 직접 검토했다.

### 질문별 결과

<a id="detailed-450-english-topk15-q01"></a>

#### 질문 01 · 추적번호 없는 배송 지연

**질문 원문**

> 11월 18일 eBay.com에서 미국 판매자의 게임 컨트롤러를 결제했고 주문 화면의 배송 예정일은 11월 24일이었습니다. 11월 28일에도 물건을 못 받았고 판매자는 발송했다고만 답했으며 주문 상세에 추적번호가 없습니다. 지금 미수령 신고를 열 수 있나요? 신고 뒤 판매자의 응답을 언제까지 기다리고 어디에서 eBay에 도움을 요청하나요?

**영어 검색 질의**

> I paid for a game controller from a US seller on eBay.com on November 18. The estimated delivery date was November 24. As of November 28 it has not arrived, the seller only says it was shipped, and the order has no tracking number. Can I report it missing now? How long should I wait for the seller and where can I ask eBay for help?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | — | 0.6728 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 2 | eBay Money Back Guarantee policy | — | 0.5016 | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` | — |
| 3 | eBay Money Back Guarantee policy | — | 0.4925 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 4 | Get help if you bought as a guest | — | 0.4909 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 5 | Get help if you bought as a guest | — | 0.4872 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 6 | eBay Money Back Guarantee policy | — | 0.4620 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 7 | Check the status of your request | — | 0.4602 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 8 | Get help if you bought as a guest | — | 0.4320 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 9 | eBay Money Back Guarantee policy | — | 0.4279 | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` | — |
| 10 | Return an item for a refund | — | 0.4247 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 11 | International purchases and shipping for buyers | — | 0.4185 | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` | — |
| 12 | Selling practices policy | — | 0.4163 | `29dc7c6c-e4f2-4783-b537-a7c16da81994` | — |
| 13 | International purchases and shipping for buyers | — | 0.4149 | `9da141f2-c82b-4943-9404-e162c739dda3` | — |
| 14 | eBay Money Back Guarantee policy | — | 0.4091 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.4054 | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

신규 미수령 안내가 1위를 유지했고 신고 및 판매자 응답 기한 근거가 남았다.

<a id="detailed-450-english-topk15-q02"></a>

#### 질문 02 · 노트북 결함과 부분 환불

**질문 원문**

> 주문 설명에 화면 결함이 없다고 적힌 중고 노트북을 11월 25일 받았습니다. 첫날부터 화면이 깜박여 영상을 남겼고, 판매자는 수리비로 40달러를 주겠다고 했습니다. 아직 수락하지 않았고 판매 페이지는 반품 불가입니다. 결함 사유로 반품해 상품값과 최초 배송비를 돌려받을 수 있나요? 반송비는 누가 내나요?

**영어 검색 질의**

> I received a used laptop on November 25. The listing said the screen had no defects, but it flickered from day one and I recorded a video. The seller offered $40 for repairs, which I have not accepted, and the listing says no returns. Can I return it as faulty and recover the price and original shipping? Who pays return shipping?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.4859 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 2 | Return an item for a refund | — | 0.4623 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 3 | Return shipping for buyers | — | 0.4572 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 4 | Condition of returned items policy | — | 0.4541 | `5969c401-95f9-4001-99e5-4c8592208453` | — |
| 5 | How to handle a return request as a seller | — | 0.4481 | `c4954f02-57d2-46c7-8e54-0ed22200153e` | — |
| 6 | Get help if you bought as a guest | — | 0.4474 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 7 | How to handle a return request as a seller | — | 0.4466 | `99dbe313-a5e6-473a-884d-9758d7490e93` | — |
| 8 | eBay Refurbished | — | 0.4458 | `83be0678-d0f2-40f2-aecf-6d4bc8d49f52` | — |
| 9 | Return an item for a refund | — | 0.4458 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 10 | eBay Money Back Guarantee policy | — | 0.4408 | `77c77430-aceb-4f79-b632-ef78c863faf0` | — |
| 11 | Return an item for a refund | — | 0.4404 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 12 | Return shipping for buyers | — | 0.4398 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 13 | Return shipping for buyers | — | 0.4389 | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` | — |
| 14 | Get help if you bought as a guest | — | 0.4380 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.4362 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

2위 이하에서 불일치 반품 및 전액 환불·판매자 반송비 부담 근거를 확인했다.

<a id="detailed-450-english-topk15-q03"></a>

#### 질문 03 · 발송 전 주문 취소

**질문 원문**

> eBay.com에서 즉시구매로 헤드폰을 카드 결제한 지 20분 됐습니다. 다른 판매자가 더 싸게 파는 걸 발견해 주문 상세에서 취소를 요청하려고 합니다. 추적번호는 없고 발송 표시도 없습니다. 이 사유로 취소를 요청하면 판매자가 수락해야 하나요? 거절되거나 답이 없으면 주문은 어떻게 되나요?

**영어 검색 질의**

> I bought headphones on eBay.com using Buy It Now and paid by card 20 minutes ago. I found a lower price and want to request cancellation from the order details. There is no tracking number or shipped status. Does the seller have to accept? What happens if they decline or do not respond?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Order cancellation policy | — | 0.5577 | `4f1d8091-f413-47f9-92b5-8fcf59780b03` | — |
| 2 | Order cancellation policy | — | 0.5529 | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` | — |
| 3 | Selling practices policy | — | 0.5043 | `29dc7c6c-e4f2-4783-b537-a7c16da81994` | — |
| 4 | Order cancellation policy | — | 0.4899 | `6aa36185-3f49-40c5-bbee-fb0855dfcf39` | — |
| 5 | Get help if you bought as a guest | — | 0.4891 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 6 | Get help if you bought as a guest | — | 0.4729 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 7 | How to handle a return request as a seller | — | 0.4646 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |
| 8 | Check the status of your request | — | 0.4593 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 9 | Get help if you bought as a guest | — | 0.4570 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 10 | Return an item for a refund | — | 0.4510 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 11 | Get help with an item that hasn't arrived | — | 0.4455 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 12 | Get help if you bought as a guest | — | 0.4430 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 13 | Get help if you bought as a guest | — | 0.4425 | `0f5fa726-6d78-401f-bca3-39487acb99e2` | — |
| 14 | Return an item for a refund | — | 0.4423 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 15 | Get help if you bought as a guest | — | 0.4391 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

1·2위에서 판매자의 취소 재량과 미응답 시 처리 규칙을 확인했다.

<a id="detailed-450-english-topk15-q04"></a>

#### 질문 04 · 단순 변심과 최초 배송비

**질문 원문**

> 검은색 M 사이즈 코트를 주문했고 받은 제품의 색상과 사이즈는 주문과 일치합니다. 실내에서 한 번 입어 봤고 택은 그대로며, 받은 지 4일 됐습니다. 반품 가능으로 표시된 주문에서 단순 변심 반품을 신청하면 결제 시 별도로 낸 최초 배송비 12달러도 환불되나요? 반송 라벨 비용은 누가 부담하나요?

**영어 검색 질의**

> I ordered a black size M coat and received exactly that. I tried it on indoors once, the tags are attached, and it arrived four days ago. The order allows returns. If I return it because I changed my mind, will I recover the $12 I originally paid for shipping? Who pays for the return label?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return shipping for buyers | — | 0.4781 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 2 | Return shipping for buyers | — | 0.4718 | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` | — |
| 3 | Return shipping for buyers | — | 0.4619 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 4 | Return an item for a refund | — | 0.4283 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 5 | eBay Money Back Guarantee policy | — | 0.4217 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |
| 6 | eBay Money Back Guarantee policy | — | 0.4160 | `77c77430-aceb-4f79-b632-ef78c863faf0` | — |
| 7 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4080 | `bf37f2b1-9659-40d3-844a-11545aaba365` | — |
| 8 | eBay Money Back Guarantee policy | — | 0.4075 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 9 | Get help if you bought as a guest | — | 0.4058 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 10 | Get help if you bought as a guest | — | 0.4022 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 11 | eBay Authenticity Guarantee for Sneakers | — | 0.3975 | `4c3b3f5c-f79d-4e33-8ac0-c3d09a75123f` | — |
| 12 | Get help if you bought as a guest | — | 0.3942 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 13 | Check the status of your request | — | 0.3932 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 14 | Get help if you bought as a guest | — | 0.3923 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 15 | eBay International Shipping - Buyer Terms & Conditions | — | 0.3894 | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

한국어 14위의 단순 변심 시 최초 배송비 제외 조항이 영어 상위 15개에서 빠졌다. 반송비 근거는 유지됐다.

<a id="detailed-450-english-topk15-q05"></a>

#### 질문 05 · 파손 반품과 국제 반송비

**질문 원문**

> 주문한 에스프레소 머신을 개봉하니 물탱크가 갈라져 있었고 배송 상자도 한쪽이 찌그러져 있었습니다. 받은 당일 사진을 찍어 반품을 신청했는데 판매자가 자기 반품 라벨을 보내는 대신 제가 먼저 국제 반송비를 내달라고 합니다. 누가 반송을 준비하고 비용을 부담하나요? 먼저 비용을 내도 되는지 어떻게 확인하나요?

**영어 검색 질의**

> My espresso machine arrived with a cracked water tank and a dented shipping box. I took photos and requested a return the day it arrived. Instead of sending a label, the seller asks me to pay international return postage upfront. Who arranges and pays for shipping, and how can I confirm whether I should pay first?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return shipping for buyers | — | 0.5066 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 2 | Return shipping for buyers | — | 0.5003 | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` | — |
| 3 | Return shipping for buyers | — | 0.4911 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 4 | How to handle a return request as a seller | — | 0.4654 | `c4954f02-57d2-46c7-8e54-0ed22200153e` | — |
| 5 | International purchases and shipping for buyers | — | 0.4629 | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` | — |
| 6 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4579 | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` | — |
| 7 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4536 | `5dd13ab2-02b1-4a36-8e2b-1ba1e0162b44` | — |
| 8 | International purchases and shipping for buyers | — | 0.4534 | `9da141f2-c82b-4943-9404-e162c739dda3` | — |
| 9 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4526 | `bf37f2b1-9659-40d3-844a-11545aaba365` | — |
| 10 | eBay Money Back Guarantee policy | — | 0.4503 | `77c77430-aceb-4f79-b632-ef78c863faf0` | — |
| 11 | eBay International Shipping \| Seller Center | — | 0.4495 | `ba80853d-fe28-4579-81c0-530b40ab121c` | — |
| 12 | How to handle a return request as a seller | — | 0.4456 | `99dbe313-a5e6-473a-884d-9758d7490e93` | — |
| 13 | Get help if you bought as a guest | — | 0.4442 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 14 | eBay International Shipping Seller Terms & Conditions | — | 0.4427 | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.4411 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

상위 청크에서 판매자 반송비 부담 및 대체 반송 방식의 사전 합의 근거가 유지됐다.

<a id="detailed-450-english-topk15-q06"></a>

#### 질문 06 · 교체 약속과 반품 요청 종료

**질문 원문**

> 다른 색상의 운동화가 와서 eBay에서 반품 요청을 열었습니다. 판매자는 메시지로 교체품을 보내겠다고 하면서 먼저 요청을 닫아 달라고 합니다. 교체품 추적번호는 아직 없습니다. 요청을 닫은 뒤 교체품이 오지 않으면 같은 주문의 반품 요청을 다시 열거나 eBay에 도움을 요청할 수 있나요?

**영어 검색 질의**

> I opened an eBay return after receiving sneakers in the wrong color. The seller messaged that they would send a replacement if I closed the request first. There is no replacement tracking number yet. If I close it and the replacement never arrives, can I reopen the return or ask eBay for help on the same order?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Check the status of your request | — | 0.5618 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 2 | Get help with an item that hasn't arrived | — | 0.5340 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 3 | Get help if you bought as a guest | — | 0.5285 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 4 | Return an item for a refund | — | 0.5187 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 5 | Get help if you bought as a guest | — | 0.5177 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 6 | Get help if you bought as a guest | — | 0.5130 | `0f5fa726-6d78-401f-bca3-39487acb99e2` | — |
| 7 | How to handle a return request as a seller | — | 0.5087 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |
| 8 | Get help if you bought as a guest | — | 0.5077 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 9 | How to handle a return request as a seller | — | 0.4932 | `0655d062-13e1-45e5-af4b-9a5e802f811c` | — |
| 10 | How to handle a return request as a seller | — | 0.4876 | `99dbe313-a5e6-473a-884d-9758d7490e93` | — |
| 11 | Get help if you bought as a guest | — | 0.4871 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 12 | eBay Authenticity Guarantee for Sneakers | — | 0.4869 | `b52463d6-3981-4dda-bbc0-5853e70bb899` | — |
| 13 | Return an item for a refund | — | 0.4826 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 14 | Get help if you bought as a guest | — | 0.4773 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 15 | How to handle a return request as a seller | — | 0.4771 | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

신규 요청 상태 안내가 3위에서 1위로 올라 닫은 요청의 재개 제한을 뒷받침했다.

<a id="detailed-450-english-topk15-q07"></a>

#### 질문 07 · 카드사 결제 이의 제기

**질문 원문**

> 배송 예정일이 사흘 지난 카메라를 받지 못해 eBay에서 미수령 요청을 열었습니다. 판매자 답변을 기다리는 중인데 카드사는 카드 결제에 대한 이의 제기를 접수할 수 있다고 합니다. 지금 카드사에도 같은 결제 건을 접수하면 진행 중인 eBay 요청과 구매자 보호는 어떻게 되나요?

**영어 검색 질의**

> My camera is three days past its expected delivery date and has not arrived, so I opened an eBay missing-item request. While I am waiting for the seller, my card issuer says I can dispute the same payment. What happens to the open eBay request and buyer protection if I dispute it with my card issuer now?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Check the status of your request | — | 0.5551 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 2 | Get help with an item that hasn't arrived | — | 0.5467 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 3 | Signature confirmation policy | — | 0.5165 | `e0323222-01f1-4d5f-a76a-7b5888d4e0f5` | — |
| 4 | Return an item for a refund | — | 0.5119 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 5 | eBay Money Back Guarantee policy | — | 0.4922 | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` | — |
| 6 | Get help if you bought as a guest | — | 0.4916 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 7 | Return an item for a refund | — | 0.4902 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 8 | eBay Money Back Guarantee policy | — | 0.4898 | `3b462389-7437-407c-87bd-ea2d91d04130` | — |
| 9 | eBay Money Back Guarantee policy | — | 0.4839 | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` | — |
| 10 | How to handle a return request as a seller | — | 0.4727 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |
| 11 | How to handle a return request as a seller | — | 0.4658 | `99dbe313-a5e6-473a-884d-9758d7490e93` | — |
| 12 | eBay Money Back Guarantee policy | — | 0.4657 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 13 | How to handle a return request as a seller | — | 0.4655 | `0655d062-13e1-45e5-af4b-9a5e802f811c` | — |
| 14 | eBay Money Back Guarantee policy | — | 0.4635 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.4632 | `6e065bd8-ddcb-4159-88ec-51f7904be5f9` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

카드사 이의 제기 시 eBay가 기존 사건을 닫을 수 있다는 조항은 15위에 유지됐다.

<a id="detailed-450-english-topk15-q08"></a>

#### 질문 08 · 배대지 경유와 상품 불일치

**질문 원문**

> 미국 판매자의 재킷을 한국에서 사면서 미국 배대지 주소를 eBay 주문 주소로 넣었습니다. 추적에는 배대지 도착으로 나오고 배대지는 상자를 한국으로 다시 보냈습니다. 한국에서 열어 보니 다른 색상의 재킷이 들어 있었는데 배대지에서는 개봉 사진을 찍지 않았습니다. eBay에서 상품 설명 불일치로 반품하거나 환불받을 수 있나요?

**영어 검색 질의**

> I bought a jacket from a US seller while in South Korea and entered a US forwarding warehouse as my eBay order address. Tracking shows delivery to that warehouse, and it forwarded the parcel to Korea. I found the wrong color inside, but the warehouse did not take unpacking photos. Can I return it as not as described or get a refund through eBay?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return an item for a refund | — | 0.5408 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 2 | Return an item for a refund | — | 0.5240 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 3 | Return an item for a refund | — | 0.5129 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 4 | Get help if you bought as a guest | — | 0.5128 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 5 | Get help if you bought as a guest | — | 0.5113 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 6 | Return an item for a refund | — | 0.5112 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 7 | Return shipping for buyers | — | 0.5009 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 8 | Get help with an item that hasn't arrived | — | 0.5007 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 9 | eBay International Shipping Seller Terms & Conditions | — | 0.4947 | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` | — |
| 10 | Get help if you bought as a guest | — | 0.4946 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 11 | eBay Money Back Guarantee policy | — | 0.4898 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 12 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4898 | `bf37f2b1-9659-40d3-844a-11545aaba365` | — |
| 13 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4893 | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` | — |
| 14 | Get help if you bought as a guest | — | 0.4886 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.4879 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

한국어 15위의 제3자 배송대행 보호 제외 및 공식 프로그램 예외 조항이 영어 상위 15개에서 빠졌다.

<a id="detailed-450-english-topk15-q09"></a>

#### 질문 09 · 한 주문의 일부 미수령

**질문 원문**

> 한 판매자에게서 카메라와 메모리카드를 각각 장바구니에 넣어 한 번에 결제했습니다. 두 상품의 가격은 주문에 따로 적혀 있고 같은 추적번호가 붙어 있는데 상자에는 카메라만 있었습니다. 메모리카드만 받거나 그 금액만 환불받을 수 있나요? 한 상품 페이지에서 세트로 판매된 경우에도 같은 절차인가요?

**영어 검색 질의**

> I added a camera and memory card from one seller to my cart separately and paid together. They have separate prices but the same tracking number. Only the camera was in the box. Can I get the missing memory card or just its price refunded? Would the process differ if both were sold as a set in one listing?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | — | 0.4571 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 2 | Return an item for a refund | — | 0.4047 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 3 | Get help if you bought as a guest | — | 0.4020 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 4 | Get help if you bought as a guest | — | 0.3912 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 5 | Return an item for a refund | — | 0.3856 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 6 | Get help if you bought as a guest | — | 0.3740 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 7 | eBay International Shipping \| Seller Center | — | 0.3728 | `ba80853d-fe28-4579-81c0-530b40ab121c` | — |
| 8 | Get help if you bought as a guest | — | 0.3713 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 9 | International purchases and shipping for buyers | — | 0.3683 | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` | — |
| 10 | Get help if you bought as a guest | — | 0.3672 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 11 | eBay Money Back Guarantee policy | — | 0.3615 | `3b462389-7437-407c-87bd-ea2d91d04130` | — |
| 12 | Condition of returned items policy | — | 0.3571 | `5969c401-95f9-4001-99e5-4c8592208453` | — |
| 13 | Return shipping for buyers | — | 0.3531 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 14 | eBay Money Back Guarantee policy | — | 0.3524 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 15 | How to handle a return request as a seller | — | 0.3482 | `6109adcf-ec42-4a95-818c-a2ef540a612d` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

신규 미수령 안내가 6위에서 1위로 올라 별도 구매 품목의 미수령 신고와 세트 반품을 구분했다.

<a id="detailed-450-english-topk15-q10"></a>

#### 질문 10 · 반송 완료 후 카드 환불 지연

**질문 원문**

> 판매자가 반송 상자를 11월 19일 받았다고 메시지로 확인했고, 추적도 배송 완료입니다. 11월 28일 현재 카드 명세에 환불이 없고 판매자는 환불을 발행한 날짜를 알려주지 않았습니다. 반품 상세에서 무엇을 확인해야 판매자가 아직 환불을 발행하지 않은 건지 카드에 반영되는 중인지 구분할 수 있나요?

**영어 검색 질의**

> The seller confirmed by message that they received my return on November 19, and tracking shows delivery. As of November 28, no refund appears on my card and the seller has not told me when it was issued. What should I check in the return details to tell whether it was never issued or is still being processed by the card issuer?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Check the status of your request | — | 0.5231 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 2 | eBay Money Back Guarantee policy | — | 0.4952 | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` | — |
| 3 | Get help if you bought as a guest | — | 0.4804 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 4 | Get help with an item that hasn't arrived | — | 0.4793 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 5 | How to handle a return request as a seller | — | 0.4760 | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` | — |
| 6 | Return an item for a refund | — | 0.4751 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 7 | Return an item for a refund | — | 0.4703 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 8 | How to handle a return request as a seller | — | 0.4615 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |
| 9 | eBay Money Back Guarantee policy | — | 0.4609 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 10 | How to handle a return request as a seller | — | 0.4555 | `6109adcf-ec42-4a95-818c-a2ef540a612d` | — |
| 11 | eBay Money Back Guarantee policy | — | 0.4505 | `9ae7bf2b-2305-4b11-b4b6-adc73e0c574f` | — |
| 12 | eBay Money Back Guarantee policy | — | 0.4498 | `4f76900a-48a2-4f6e-9991-0ed7c95e99dd` | — |
| 13 | eBay Money Back Guarantee policy | — | 0.4447 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |
| 14 | How to handle a return request as a seller | — | 0.4425 | `d58dd7b1-f4d6-47ba-b7ae-81501242f723` | — |
| 15 | Return an item for a refund | — | 0.4425 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

신규 요청 상태 안내는 1위를 유지했고 환불 반영 기간과 약속한 환불 미발행 시 후속 조치 근거가 남았다.

<a id="detailed-450-english-topk15-q11"></a>

#### 질문 11 · 반품 주소 불일치

**질문 원문**

> eBay 반품 요청에서 받은 선불 라벨에는 캘리포니아 주소가 찍혀 있는데 판매자가 메시지로 네바다 창고 주소에 보내라고 합니다. 반품 상세에는 새 라벨이 없고 발송 기한은 이틀 남았습니다. 어느 주소로 보내야 하나요? 나중에 분쟁이 생기면 어떤 추적 또는 배송 증빙이 필요한가요?

**영어 검색 질의**

> The prepaid label in my eBay return request lists a California address, but the seller messaged me to send the package to a Nevada warehouse. There is no new label in the return details and the deadline is in two days. Which address should I use, and what tracking or delivery evidence should I keep in case of a dispute?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Return shipping for buyers | — | 0.5037 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 2 | Check the status of your request | — | 0.4990 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 3 | Return shipping for buyers | — | 0.4906 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 4 | Return shipping for buyers | — | 0.4829 | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` | — |
| 5 | How to handle a return request as a seller | — | 0.4745 | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` | — |
| 6 | Appeal the outcome of a case as a buyer | — | 0.4558 | `8b037b81-9cde-4035-bf40-683bb1eb2a47` | — |
| 7 | How to handle a return request as a seller | — | 0.4544 | `c4954f02-57d2-46c7-8e54-0ed22200153e` | — |
| 8 | eBay Money Back Guarantee policy | — | 0.4495 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 9 | eBay Money Back Guarantee policy | — | 0.4478 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |
| 10 | How to handle a return request as a seller | — | 0.4453 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |
| 11 | How to handle a return request as a seller | — | 0.4437 | `0655d062-13e1-45e5-af4b-9a5e802f811c` | — |
| 12 | Get help with an item that hasn't arrived | — | 0.4437 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 13 | Return an item for a refund | — | 0.4419 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 14 | How to handle a return request as a seller | — | 0.4358 | `00ab85d7-00dc-4e04-bc46-416a6a759751` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.4341 | `77c77430-aceb-4f79-b632-ef78c863faf0` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

판매자 지정 주소 관련 청크가 13위에서 9위로 올랐으나, 라벨과 메시지가 충돌할 때 우선 주소는 명시되지 않았다.

<a id="detailed-450-english-topk15-q12"></a>

#### 질문 12 · 고가 상품과 대리 수령

**질문 원문**

> 배송비와 세금까지 1,080달러인 카메라를 eBay.com에서 주문해 친구 집을 주문 주소로 지정했습니다. 추적에는 배송 완료라고 나오지만 친구는 상자를 받지 않았고 판매자는 배송 완료 화면만 보여줬습니다. 주문 주소와 배송 증거가 일치하는지와 서명 기록을 확인해 달라고 할 수 있나요? 친구가 실제 대리 수령했다면 판단이 달라지나요?

**영어 검색 질의**

> I bought a camera on eBay.com for $1,080 including shipping and tax and used my friend’s home as the order address. Tracking says delivered but my friend never received it, and the seller only showed a delivered status. Can I ask for proof that the delivery address matches the order and for the signature record? Would it matter if my friend actually accepted it for me?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.4838 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 2 | Get help with an item that hasn't arrived | — | 0.4665 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 3 | eBay Money Back Guarantee policy | — | 0.4468 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 4 | eBay Money Back Guarantee policy | — | 0.4420 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 5 | Signature confirmation policy | — | 0.4390 | `604b7fbb-2462-4a94-a0a3-59fb5290d846` | — |
| 6 | Get help if you bought as a guest | — | 0.4379 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 7 | Get help if you bought as a guest | — | 0.4241 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 8 | Signature confirmation policy | — | 0.4210 | `e2b3df5e-28ee-48d2-b4f7-f78175e269b9` | — |
| 9 | International purchases and shipping for buyers | — | 0.4168 | `02ed01df-7637-4e68-8458-f8e01b540ce4` | — |
| 10 | Get help if you bought as a guest | — | 0.4031 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 11 | eBay International Shipping - Buyer Terms & Conditions | — | 0.4004 | `88ee7312-806c-49e9-875f-f20a8881ce44` | — |
| 12 | International purchases and shipping for buyers | — | 0.3899 | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` | — |
| 13 | eBay Money Back Guarantee policy | — | 0.3894 | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` | — |
| 14 | eBay Authenticity Guarantee for Sneakers | — | 0.3867 | `4c3b3f5c-f79d-4e33-8ac0-c3d09a75123f` | — |
| 15 | International purchases and shipping for buyers | — | 0.3852 | `76066a7d-2738-449c-9574-7d68d06faa54` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

주소 일치, 서명 확인, 제3자 대리 수령 관련 근거가 상위 15개에 유지됐다.

<a id="detailed-450-english-topk15-q13"></a>

#### 질문 13 · 운동화 상태와 위조 의심

**질문 원문**

> 새 제품으로 표시된 운동화를 받았는데 밑창에 흙과 마모 흔적이 있고 상자와 신발의 제품 코드도 다릅니다. 주문 화면에서 정품 인증 대상 표시를 확인하지 못했고 받은 지 이틀 됐습니다. 상태가 설명과 다르다는 사유와 위조 의심 중 어떤 절차로 문제를 제기할 수 있나요?

**영어 검색 질의**

> Sneakers listed as new arrived with dirt and wear on the soles, and the product code on the box differs from the shoes. I could not find an authenticity verification indicator on the order, and they arrived two days ago. What process can I use to report the condition mismatch or suspected counterfeit item?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Authenticity Guarantee for Sneakers | — | 0.6129 | `a8720eaf-1dbd-4eaa-929a-d067e5780bf1` | — |
| 2 | eBay Authenticity Guarantee for Sneakers | — | 0.5958 | `668ef1c6-a0e2-4a5d-bf03-c24cc6e00f89` | — |
| 3 | eBay Authenticity Guarantee for Sneakers | — | 0.5689 | `bd335ae7-a208-4fb1-a080-26f3ce45688f` | — |
| 4 | eBay Authenticity Guarantee for Sneakers | — | 0.5271 | `b52463d6-3981-4dda-bbc0-5853e70bb899` | — |
| 5 | eBay Authenticity Guarantee for Sneakers | — | 0.5270 | `909603f2-3b0e-4a92-b440-b3b76df9df36` | — |
| 6 | eBay Authenticity Guarantee for Sneakers | — | 0.5227 | `4c3b3f5c-f79d-4e33-8ac0-c3d09a75123f` | — |
| 7 | eBay Authenticity Guarantee for Sneakers | — | 0.5178 | `4493c75d-f79a-4cd5-b15e-e7c847137741` | — |
| 8 | eBay Authenticity Guarantee for Sneakers | — | 0.5144 | `d7ea9541-18ed-4a3b-860e-44597608060c` | — |
| 9 | eBay Authenticity Guarantee for Sneakers | — | 0.5129 | `310c3728-23b8-46d5-88f9-cfccc4aa62bc` | — |
| 10 | eBay Authenticity Guarantee for Sneakers | — | 0.5116 | `28a46454-f148-412a-b6be-827a11666c92` | — |
| 11 | eBay Authenticity Guarantee for Sneakers | — | 0.5049 | `b3af4677-12f3-44be-8750-6cb51b7529b9` | — |
| 12 | Buying with Authenticity Guarantee | — | 0.5021 | `79a064e9-afda-4eb4-8f2b-171482196f23` | — |
| 13 | eBay Authenticity Guarantee for Sneakers | — | 0.4995 | `28c18aa9-8df1-4134-bfa2-934df227a276` | — |
| 14 | eBay Authenticity Guarantee for Sneakers | — | 0.4976 | `a2c75035-7ecb-4809-a425-bb9a5655986c` | — |
| 15 | eBay Authenticity Guarantee for Sneakers | — | 0.4764 | `b7fadb64-8245-4379-8046-a7ababfa5da8` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

한국어와 같은 15개 청크가 검색됐다. 일반 위조 의심 신고·반품 근거를 확인하지 못했다.

<a id="detailed-450-english-topk15-q14"></a>

#### 질문 14 · 비회원 불량품 반품

**질문 원문**

> eBay 계정 없이 신용카드로 결제했고 주문 확인 이메일은 있지만 로그인할 계정이 없습니다. 어제 도착한 블루투스 스피커는 전원이 켜지지 않습니다. 이메일에서 어디로 들어가 반품을 시작하고 불량 사진이나 설명을 어떻게 제출하나요?

**영어 검색 질의**

> I paid by credit card without an eBay account. I have the order confirmation email but no account to sign in to. A Bluetooth speaker delivered yesterday does not turn on. Where in the email do I start a return, and how do I submit photos or a description of the defect?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help if you bought as a guest | — | 0.5318 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 2 | Get help if you bought as a guest | — | 0.5241 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 3 | Get help if you bought as a guest | — | 0.4928 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 4 | Get help if you bought as a guest | — | 0.4904 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 5 | Return an item for a refund | — | 0.4899 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 6 | Return an item for a refund | — | 0.4719 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 7 | Return an item for a refund | — | 0.4635 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 8 | Return an item for a refund | — | 0.4614 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 9 | Get help if you bought as a guest | — | 0.4544 | `0f5fa726-6d78-401f-bca3-39487acb99e2` | — |
| 10 | eBay Refurbished | — | 0.4520 | `83be0678-d0f2-40f2-aecf-6d4bc8d49f52` | — |
| 11 | Get help if you bought as a guest | — | 0.4493 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 12 | How to handle a return request as a seller | — | 0.4431 | `0655d062-13e1-45e5-af4b-9a5e802f811c` | — |
| 13 | Return shipping for buyers | — | 0.4426 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 14 | How to handle a return request as a seller | — | 0.4418 | `99dbe313-a5e6-473a-884d-9758d7490e93` | — |
| 15 | How to handle a return request as a seller | — | 0.4360 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

주문 확인 이메일로 시작하는 비회원 반품 절차와 사진 등록 근거를 확인했다.

<a id="detailed-450-english-topk15-q15"></a>

#### 질문 15 · 포장 개봉과 환불액 차감

**질문 원문**

> 설명과 다른 이어폰을 반품했습니다. 포장 씰을 뜯어 5분간 작동을 확인한 뒤 케이블과 설명서까지 전부 돌려보냈습니다. 판매자가 개봉을 이유로 180달러 중 60달러를 차감했고 다른 훼손은 설명하지 않았습니다. 어떤 조건에서 차감에 이의를 제기하고 eBay에 도움을 요청할 수 있나요?

**영어 검색 질의**

> I returned earphones that did not match the description. I broke the seal to test them for five minutes and returned all accessories and instructions. The seller deducted $60 from the $180 price, citing opened packaging without explaining any other damage. When can I dispute the deduction and ask eBay to help?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | How to handle a return request as a seller | — | 0.5767 | `1743ff1f-d3d4-4bdd-8248-211875998334` | — |
| 2 | Condition of returned items policy | — | 0.5484 | `5969c401-95f9-4001-99e5-4c8592208453` | — |
| 3 | Return an item for a refund | — | 0.5304 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 4 | How to handle a return request as a seller | — | 0.5199 | `6109adcf-ec42-4a95-818c-a2ef540a612d` | — |
| 5 | Return an item for a refund | — | 0.5185 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 6 | Return an item for a refund | — | 0.5158 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 7 | How to handle a return request as a seller | — | 0.5063 | `99dbe313-a5e6-473a-884d-9758d7490e93` | — |
| 8 | Return an item for a refund | — | 0.5026 | `927f2845-49b1-40f5-b351-48dee6c6ce2a` | — |
| 9 | Check the status of your request | — | 0.5025 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 10 | eBay Money Back Guarantee policy | — | 0.5021 | `3b462389-7437-407c-87bd-ea2d91d04130` | — |
| 11 | Get help if you bought as a guest | — | 0.5004 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 12 | Condition of returned items policy | — | 0.4993 | `87e1df21-066e-49e9-863e-7b3455872aed` | — |
| 13 | Appeal the outcome of a case as a buyer | — | 0.4958 | `8b037b81-9cde-4035-bf40-683bb1eb2a47` | — |
| 14 | How to handle a return request as a seller | — | 0.4941 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |
| 15 | Get help if you bought as a guest | — | 0.4898 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

차감 조건과 일반 eBay 개입 안내는 검색됐으나 이미 발행된 부분 환불의 차감 이의 절차는 확인되지 않았다.

<a id="detailed-450-english-topk15-q16"></a>

#### 질문 16 · 국제 배송 파손과 수입 비용

**질문 원문**

> eBay International Shipping으로 주문한 310달러짜리 조명이 도착했을 때 유리 갓이 깨져 있었습니다. 결제 영수증에는 eBay에 낸 수입 비용 26달러가 있고, 배송 때 운송사에도 세금 명목으로 15달러를 냈습니다. 파손 반품이 승인되면 상품값 외에 두 곳에 낸 수입 비용을 각각 돌려받을 수 있나요?

**영어 검색 질의**

> A $310 light shipped through eBay International Shipping arrived with a broken glass shade. My receipt shows $26 in import charges paid to eBay, and I paid the carrier $15 in taxes on delivery. If my return for damage is approved, can I recover the import charges paid to each party as well as the item price?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | International purchases and shipping for buyers | — | 0.5881 | `02ed01df-7637-4e68-8458-f8e01b540ce4` | — |
| 2 | eBay International Shipping - Buyer Terms & Conditions | — | 0.5759 | `936ad65f-d348-453a-9ada-fd5061587211` | — |
| 3 | International purchases and shipping for buyers | — | 0.5708 | `76066a7d-2738-449c-9574-7d68d06faa54` | — |
| 4 | eBay International Shipping - Buyer Terms & Conditions | — | 0.5707 | `bf37f2b1-9659-40d3-844a-11545aaba365` | — |
| 5 | eBay International Shipping - Buyer Terms & Conditions | — | 0.5656 | `5dd13ab2-02b1-4a36-8e2b-1ba1e0162b44` | — |
| 6 | International purchases and shipping for buyers | — | 0.5539 | `f261595f-eaa0-41d8-8dae-78a4c9bfded3` | — |
| 7 | International purchases and shipping for buyers | — | 0.5455 | `9da141f2-c82b-4943-9404-e162c739dda3` | — |
| 8 | eBay International Shipping Seller Terms & Conditions | — | 0.5450 | `e4ec2cd5-82c7-4096-b925-9c1ad1159dc9` | — |
| 9 | eBay International Shipping \| Seller Center | — | 0.5420 | `ba80853d-fe28-4579-81c0-530b40ab121c` | — |
| 10 | eBay International Shipping - Buyer Terms & Conditions | — | 0.5359 | `a0e73aa8-c37d-4048-a4a7-3a7a95dc2809` | — |
| 11 | International purchases and shipping for buyers | — | 0.5354 | `45c4e413-c093-479c-b1a0-e97c51c95848` | — |
| 12 | Return shipping for buyers | — | 0.5346 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 13 | eBay International Shipping Seller Terms & Conditions | — | 0.5253 | `aea220ac-fba6-4e07-b5e4-8d76311a2a21` | — |
| 14 | eBay International Shipping - Buyer Terms & Conditions | — | 0.5218 | `89d3cde5-4330-49cf-872f-576d13c53acd` | — |
| 15 | Return shipping for buyers | — | 0.5210 | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

EIS 환급 조항이 1위에서 4위로 내려갔다. 운송사에 별도로 낸 비용은 여전히 근거가 부족했다.

<a id="detailed-450-english-topk15-q17"></a>

#### 질문 17 · 예약 주문 배송 지연

**질문 원문**

> 8월에 예약 구매한 피규어의 상품 페이지에는 10월 발송 예정이라고 적혀 있었습니다. 11월 말에도 추적번호가 없고 판매자는 다시 다음 달로 미뤘습니다. 주문 후 30일은 지났으며 아직 미수령 신고는 열지 않았습니다. 지금 취소를 요청하거나 미수령으로 환불을 요청할 수 있나요?

**영어 검색 질의**

> I preordered a figure in August that the listing said would ship in October. In late November there is still no tracking number and the seller has postponed shipment to next month. More than 30 days have passed since ordering, and I have not opened a missing-item request. Can I request cancellation or a refund for non-delivery now?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help with an item that hasn't arrived | — | 0.5241 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 2 | Order cancellation policy | — | 0.4569 | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` | — |
| 3 | Check the status of your request | — | 0.4471 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 4 | Order cancellation policy | — | 0.4209 | `4f1d8091-f413-47f9-92b5-8fcf59780b03` | — |
| 5 | eBay Money Back Guarantee policy | — | 0.4083 | `f1f4bcb7-7112-4123-b02e-9afbbb4af84a` | — |
| 6 | Get help if you bought as a guest | — | 0.4050 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 7 | Selling practices policy | — | 0.3966 | `29dc7c6c-e4f2-4783-b537-a7c16da81994` | — |
| 8 | eBay Money Back Guarantee policy | — | 0.3849 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 9 | Return an item for a refund | — | 0.3845 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 10 | eBay Money Back Guarantee policy | — | 0.3795 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 11 | Get help if you bought as a guest | — | 0.3778 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 12 | eBay Money Back Guarantee policy | — | 0.3675 | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` | — |
| 13 | Return an item for a refund | — | 0.3640 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 14 | Order cancellation policy | — | 0.3640 | `6aa36185-3f49-40c5-bbee-fb0855dfcf39` | — |
| 15 | eBay Money Back Guarantee policy | — | 0.3623 | `3b462389-7437-407c-87bd-ea2d91d04130` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

미수령 안내와 일반 주문 취소 안내가 상위에 있었고 예약판매 정책은 상위 15개에 없었다.

<a id="detailed-450-english-topk15-q18"></a>

#### 질문 18 · 발송 후 배송 주소 오류

**질문 원문**

> 결제 직후 확인해 보니 주문에 입력한 주소는 이사 전 아파트입니다. 판매자에게 새 주소를 보냈지만 이미 택배사에 넘겼고 추적은 배송 중입니다. 주문 주소를 수정하거나 올바른 주소로 돌려 보내 달라고 요청할 수 있나요? 기존 주소에서 받지 못하면 환불을 요청할 수 있나요?

**영어 검색 질의**

> Just after payment I realized my order was addressed to my old apartment. I sent the seller my new address, but the parcel was already with the carrier and is in transit. Can I change the order address or ask for redelivery to the correct address? If I cannot collect it at the old address, can I ask for a refund?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Get help if you bought as a guest | — | 0.3909 | `b5b6d063-0a34-4801-8161-f9f4f671bc5c` | — |
| 2 | Get help with an item that hasn't arrived | — | 0.3878 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 3 | Check the status of your request | — | 0.3782 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 4 | Return an item for a refund | — | 0.3739 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |
| 5 | Get help if you bought as a guest | — | 0.3686 | `cd466c01-5b0f-4451-a90b-ada15011016b` | — |
| 6 | Get help if you bought as a guest | — | 0.3635 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 7 | Return an item for a refund | — | 0.3629 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 8 | Get help if you bought as a guest | — | 0.3628 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 9 | Order cancellation policy | — | 0.3567 | `4f1d8091-f413-47f9-92b5-8fcf59780b03` | — |
| 10 | How to handle a return request as a seller | — | 0.3525 | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` | — |
| 11 | Get help if you bought as a guest | — | 0.3511 | `f2ed3931-72a9-4eb1-942b-e7d9ad5d812a` | — |
| 12 | How to handle a return request as a seller | — | 0.3470 | `c4954f02-57d2-46c7-8e54-0ed22200153e` | — |
| 13 | Order cancellation policy | — | 0.3465 | `fd1414e1-9744-4ca5-aac2-c6ea5bcc43a4` | — |
| 14 | Get help if you bought as a guest | — | 0.3461 | `0f5fa726-6d78-401f-bca3-39487acb99e2` | — |
| 15 | How to handle a return request as a seller | — | 0.3421 | `0655d062-13e1-45e5-af4b-9a5e802f811c` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

잘못된 배송지에 관한 기존 게스트 안내는 5위에 유지됐다. 보호 제외와 재배송 조건은 확인되지 않았다.

<a id="detailed-450-english-topk15-q19"></a>

#### 질문 19 · 고가 상품 반송과 서명

**질문 원문**

> eBay.com에서 세금과 배송비까지 900달러인 상품을 구매했고 반품 배송비는 제가 결제했습니다. 추적번호를 반품 요청에 올렸고 판매자 주소로 배송 완료로 나오지만 서명 옵션을 사지 않았습니다. 판매자가 반송품을 받지 못했다고 하면 추적 기록만으로 반품 배송을 입증할 수 있나요?

**영어 검색 질의**

> I bought an item on eBay.com for $900 including tax and shipping and paid for return shipping myself. I uploaded the tracking number to the return request, and it shows delivery to the seller’s address, but I did not purchase signature confirmation. If the seller says they did not receive it, is tracking alone sufficient proof of return delivery?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | eBay Money Back Guarantee policy | — | 0.5838 | `bdafdb3e-e9ea-4507-b17c-dae1218bb389` | — |
| 2 | Return shipping for buyers | — | 0.5567 | `3ce5423c-8e78-4dd2-9b14-3e0ba18b41fb` | — |
| 3 | Signature confirmation policy | — | 0.5478 | `604b7fbb-2462-4a94-a0a3-59fb5290d846` | — |
| 4 | Return shipping for buyers | — | 0.5212 | `c1f7d8cc-7e76-4990-ac01-d3d94a41e93a` | — |
| 5 | Return shipping for buyers | — | 0.5163 | `b01c873d-8724-4642-adf2-4ad03f6ada39` | — |
| 6 | eBay Money Back Guarantee policy | — | 0.5119 | `fd1225eb-bf54-4d58-a8af-6f0ba2820f9f` | — |
| 7 | Signature confirmation policy | — | 0.5040 | `e2b3df5e-28ee-48d2-b4f7-f78175e269b9` | — |
| 8 | eBay Money Back Guarantee policy | — | 0.4989 | `347c4c37-e21c-4f86-9575-306c1651a5df` | — |
| 9 | eBay Money Back Guarantee policy | — | 0.4933 | `d84d8195-c45b-4b98-9c0a-23b5cbdf830c` | — |
| 10 | Get help with an item that hasn't arrived | — | 0.4916 | `ed06401d-054a-4e08-9faa-e830c341e214` | — |
| 11 | eBay Money Back Guarantee policy | — | 0.4754 | `77c77430-aceb-4f79-b632-ef78c863faf0` | — |
| 12 | Get help if you bought as a guest | — | 0.4685 | `cf9b9d53-2690-48e5-9e02-bfe9ec218d8c` | — |
| 13 | eBay Money Back Guarantee policy | — | 0.4663 | `cdca8d5f-812a-4fed-a6a6-380989fdf86a` | — |
| 14 | eBay Authenticity Guarantee for Sneakers | — | 0.4625 | `4c3b3f5c-f79d-4e33-8ac0-c3d09a75123f` | — |
| 15 | Return an item for a refund | — | 0.4520 | `547c555c-05a8-4a9a-af29-a5c10cdbf2a6` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

750달러 이상 추적 및 서명 요건을 2위에서 확인했다.

<a id="detailed-450-english-topk15-q20"></a>

#### 질문 20 · 플랫폼 밖 부분 환불

**질문 원문**

> 240달러짜리 제품의 결함으로 eBay에서 반품 요청을 진행 중입니다. 판매자가 eBay 메시지로 외부 계좌이체로 70달러를 보내겠다고 제안했으며, 아직 송금은 받지 않았고 요청도 열려 있습니다. 송금을 받아도 남은 170달러에 대해 같은 요청에서 계속 다투거나 eBay 개입을 요청할 수 있나요?

**영어 검색 질의**

> I have an open eBay return request for a defective $240 item. The seller proposed by eBay message to send me $70 by an off-platform bank transfer. I have not received a transfer and the request is still open. If I accept it, can I continue disputing the remaining $170 in the same request or ask eBay to intervene?

**모델 출력 상태**

응답 생성 미실행

**실행 결과**

반환 15개

**검색 결과**

| 순위 | 정책 문서 | 조항 | 유사도 | 청크 ID | 적용 판단 |
|---:|---|---|---:|---|---|
| 1 | Check the status of your request | — | 0.5323 | `c3845704-f66a-4ea9-831b-3ff1cc9e171d` | — |
| 2 | Return an item for a refund | — | 0.5205 | `00ae1673-4290-4813-83b4-c1a0cb6cdb93` | — |
| 3 | How to handle a return request as a seller | — | 0.5204 | `0655d062-13e1-45e5-af4b-9a5e802f811c` | — |
| 4 | How to handle a return request as a seller | — | 0.5186 | `5a9e0d9b-f25c-4dc9-b440-55dc3364478a` | — |
| 5 | How to handle a return request as a seller | — | 0.5142 | `350c99f0-67bd-4939-abab-98d6ddf129f7` | — |
| 6 | How to handle a return request as a seller | — | 0.5130 | `99dbe313-a5e6-473a-884d-9758d7490e93` | — |
| 7 | How to handle a return request as a seller | — | 0.5092 | `00ab85d7-00dc-4e04-bc46-416a6a759751` | — |
| 8 | eBay Money Back Guarantee policy | — | 0.5060 | `3b462389-7437-407c-87bd-ea2d91d04130` | — |
| 9 | How to handle a return request as a seller | — | 0.5056 | `1743ff1f-d3d4-4bdd-8248-211875998334` | — |
| 10 | How to handle a return request as a seller | — | 0.5027 | `c4954f02-57d2-46c7-8e54-0ed22200153e` | — |
| 11 | Return an item for a refund | — | 0.4978 | `685c1734-1f4e-487d-80a4-9a9888659d25` | — |
| 12 | Get help if you bought as a guest | — | 0.4941 | `ad9cf8d5-3639-4c93-8c63-3fbd05b4e879` | — |
| 13 | eBay Money Back Guarantee policy | — | 0.4895 | `e53b76a2-88f4-4fe5-aafa-b207abaaa16e` | — |
| 14 | How to handle a return request as a seller | — | 0.4880 | `d58dd7b1-f4d6-47ba-b7ae-81501242f723` | — |
| 15 | How to handle a return request as a seller | — | 0.4847 | `6109adcf-ec42-4a95-818c-a2ef540a612d` | — |

**모델 응답 원문**

생성 응답 기록 없음

**관찰**

요청 상태 안내는 1위였으나 외부 계좌이체 후 남은 분쟁을 계속할 수 있는지 확인되지 않았다.

<a id="detailed-300-200-topk10"></a>

## 상세 구매자 질의 20개 · 450/200과 300/200 · topK=10 · 2026-11-28

### 질문별 결과 요약

| 질문 | 주제 | 450/200 본문 토큰 | 300/200 본문 토큰 | 관찰 |
|---:|---|---:|---:|---|
| 01 | 추적번호 없는 배송 지연 | 3650 | 2469 | 판매자 응답 기한과 eBay 개입 절차 유지 |
| 02 | 노트북 결함과 부분 환불 | 3797 | 2588 | 최초 배송비 포함 환불 근거 10위 → 5위 |
| 03 | 발송 전 주문 취소 | 3667 | 2699 | 판매자 취소 재량과 미응답 시 처리 유지 |
| 04 | 단순 변심과 최초 배송비 | 3608 | 2596 | 최초 배송비 제외 근거는 양쪽 상위 10개에서 미검색 |
| 05 | 파손 반품과 국제 반송비 | 4254 | 2626 | 판매자 부담과 합의한 반송 방식 근거 유지 |
| 06 | 교체 약속과 반품 요청 종료 | 3593 | 2626 | 닫은 요청의 재개 제한 3위 → 5위 |
| 07 | 카드사 결제 이의 제기 | 3578 | 2225 | 카드사 이의 제기 시 eBay 사건 종료 가능성 미검색 → 1위 |
| 08 | 배대지 경유와 상품 불일치 | 3732 | 2778 | 제3자 배대지 제외와 eBay 공식 프로그램 예외 미검색 → 7위 |
| 09 | 한 주문의 일부 미수령 | 3668 | 2663 | 별도 구매한 상품의 미수령 신고와 세트 반품 구분 유지 |
| 10 | 반송 완료 후 카드 환불 지연 | 3628 | 2691 | 환불 발행 기한과 결제 반영 기간 근거 유지 |
| 11 | 반품 주소 불일치 | 3614 | 2562 | 라벨과 판매자 메시지가 충돌할 때 주소 우선순위 미확인 |
| 12 | 고가 상품과 대리 수령 | 3698 | 2608 | 주소 일치와 서명 및 제3자 대리 수령 근거 유지 |
| 13 | 운동화 상태와 위조 의심 | 4280 | 2880 | 위조 의심 상품의 신고·반품 절차 미확인 |
| 14 | 비회원 불량품 반품 | 3679 | 2679 | 비회원 반품과 사진 첨부 근거 6위 → 3위 |
| 15 | 포장 개봉과 환불액 차감 | 3744 | 2757 | 차감 조건은 확인, 이미 발행된 부분 환불의 직접 이의 제기 조건은 미확인 |
| 16 | 국제 배송 파손과 수입 비용 | 3911 | 2730 | eBay에 낸 비용의 조건부 환급은 확인, 운송사에 낸 비용 근거 미확인 |
| 17 | 예약 주문 배송 지연 | 3625 | 2577 | 예약판매 정책 미검색 |
| 18 | 발송 후 배송 주소 오류 | 3055 | 2680 | 주소 오류의 보호 제외 및 재배송 조건 미확인 |
| 19 | 고가 상품 반송과 서명 | 3637 | 2694 | 고가 반송의 추적 및 서명 확인 근거 유지 |
| 20 | 플랫폼 밖 부분 환불 | 3702 | 2763 | 외부 계좌이체 후 남은 분쟁을 계속할 수 있는지 미확인 |

**집계** · 설정별 검색 20/20건 · 설정별 반환 200개 · 모델 응답 생성 미실행

**검색 관찰** · 300/200에서 카드사 이의 제기와 배대지 조항이 각각 1위와 7위였다. 반환 본문 토큰은 74,120개에서 52,891개로 28.6% 줄었다.

### 실행 조건

| 변경 항목 | 이번 실험 |
|---|---|
| 청킹 | 기준선 450/200(149개), 실험군 300/200(222개) |
| 검색 결과 상한 | `topK=10` |

문서 25개, 기존 한국어 상세 질문 20개, text-embedding-3-large, 플랫폼 EBAY, 코사인 유사도 하한 0.0을 사용했다. 두 설정에 동일한 질의 임베딩을 사용해 Java에서 모든 청크의 코사인 유사도를 계산했다. 청크 본문 토큰은 CL100K_BASE로 셌다. 300/200 청크는 비교 실행에만 사용했다.

<a id="detailed-300-200-topk15"></a>

## 상세 구매자 질의 20개 · 300/200 · topK=15 · 2026-11-28

### 질문별 결과 요약

| 질문 | 주제 | 상위 10개 본문 토큰 | 상위 15개 본문 토큰 | 관찰 |
|---:|---|---:|---:|---|
| 01 | 추적번호 없는 배송 지연 | 2469 | 3872 | 상위 10개 핵심 근거 및 판정 유지 |
| 02 | 노트북 결함과 부분 환불 | 2588 | 3784 | 상위 10개 핵심 근거 및 판정 유지 |
| 03 | 발송 전 주문 취소 | 2699 | 3882 | 상위 10개 핵심 근거 및 판정 유지 |
| 04 | 단순 변심과 최초 배송비 | 2596 | 4013 | 추가 후보에서도 단순 변심 반품의 최초 배송비 제외 조항은 미검색 |
| 05 | 파손 반품과 국제 반송비 | 2626 | 3963 | 상위 10개 핵심 근거 및 판정 유지 |
| 06 | 교체 약속과 반품 요청 종료 | 2626 | 4087 | 상위 10개 핵심 근거 및 판정 유지 |
| 07 | 카드사 결제 이의 제기 | 2225 | 3679 | 상위 10개 핵심 근거 및 판정 유지 |
| 08 | 배대지 경유와 상품 불일치 | 2778 | 4057 | 상위 10개 핵심 근거 및 판정 유지 |
| 09 | 한 주문의 일부 미수령 | 2663 | 4034 | 상위 10개 핵심 근거 및 판정 유지 |
| 10 | 반송 완료 후 카드 환불 지연 | 2691 | 3825 | 상위 10개 핵심 근거 및 판정 유지 |
| 11 | 반품 주소 불일치 | 2562 | 3684 | 14위에서 판매자 지정 반송 주소 관련 근거를 추가 확보. 주소 충돌 시 우선순위는 미확인 |
| 12 | 고가 상품과 대리 수령 | 2608 | 4056 | 상위 10개 핵심 근거 및 판정 유지 |
| 13 | 운동화 상태와 위조 의심 | 2880 | 4306 | 11~15위도 스니커즈 인증 관련 안내. 일반 위조 신고·반품 절차 미검색 |
| 14 | 비회원 불량품 반품 | 2679 | 4140 | 상위 10개 핵심 근거 및 판정 유지 |
| 15 | 포장 개봉과 환불액 차감 | 2757 | 4217 | 차감과 일반 개입 규칙은 확인. 이미 발행된 부분 환불의 직접 이의 제기 절차는 미확인 |
| 16 | 국제 배송 파손과 수입 비용 | 2730 | 4090 | EIS 구매자·판매자 약관이 추가됐지만 운송사에 낸 비용 환급 조건은 미확인 |
| 17 | 예약 주문 배송 지연 | 2577 | 3888 | 추가 후보에도 예약판매 정책 없음 |
| 18 | 발송 후 배송 주소 오류 | 2680 | 3930 | 주소 취소·수령 안내는 유지. 주소 오류 보호 제외와 재배송 조건 미확인 |
| 19 | 고가 상품 반송과 서명 | 2694 | 4069 | 상위 10개 핵심 근거 및 판정 유지 |
| 20 | 플랫폼 밖 부분 환불 | 2763 | 3910 | 반품 및 요청 상태 안내가 추가됐지만 외부 입금 후 남은 분쟁 가능 여부는 미확인 |

**집계** · 검색 20/20건 · 반환 300개 · 모델 응답 생성 미실행. 상위 10개 본문 52,891토큰, 상위 15개 본문 79,486토큰.

**검색 관찰** · 상위 10개 청크는 20개 질문에서 재현됐다. 11~15위에서도 최초 배송비 제외·위조 의심·예약판매의 핵심 조항을 찾지 못했다.

### 실행 조건

| 변경 항목 | 이번 실험 |
|---|---|
| 검색 결과 상한 | `topK=15` |

300/200으로 분할한 문서 25개·청크 222개와 동일한 한국어 질문 20개를 사용했다. text-embedding-3-large로 재임베딩한 뒤 Java에서 전체 청크 코사인 유사도를 계산했다. 상위 10개는 직전 실험과 청크 ID·본문을 대조했다. 토큰은 CL100K_BASE 기준 청크 본문만 합산했다.

<a id="detailed-document-chunking-full-rag"></a>

## 상세 구매자 질의 20개 · 문서별 청킹 · 전체 RAG · 2026-11-28

### 질문별 결과 요약

| 질문 | 주제 | 모델 판정 | 반환 수 | ID 일치 검토 수 |
|---:|---|---|---:|---:|
| 01 | 추적번호 없는 배송 지연 | `EVIDENCE_SUFFICIENT` | 15 | 15 |
| 02 | 노트북 결함과 부분 환불 | `EVIDENCE_SUFFICIENT` | 15 | 15 |
| 03 | 발송 전 주문 취소 | `EVIDENCE_SUFFICIENT` | 15 | 15 |
| 04 | 단순 변심과 최초 배송비 | `EVIDENCE_SUFFICIENT` | 15 | 15 |
| 05 | 파손 반품과 국제 반송비 | `EVIDENCE_SUFFICIENT` | 15 | 15 |
| 06 | 교체 약속과 반품 요청 종료 | `EVIDENCE_SUFFICIENT` | 15 | 15 |
| 07 | 카드사 결제 이의 제기 | `EVIDENCE_SUFFICIENT` | 15 | 15 |
| 08 | 배대지 경유와 상품 불일치 | `EVIDENCE_SUFFICIENT` | 15 | 15 |
| 09 | 한 주문의 일부 미수령 | `EVIDENCE_SUFFICIENT` | 15 | 15 |
| 10 | 반송 완료 후 카드 환불 지연 | `EVIDENCE_SUFFICIENT` | 15 | 15 |
| 11 | 반품 주소 불일치 | `EVIDENCE_SUFFICIENT` | 15 | 15 |
| 12 | 고가 상품과 대리 수령 | `EVIDENCE_SUFFICIENT` | 15 | 15 |
| 13 | 운동화 상태와 위조 의심 | `EVIDENCE_INSUFFICIENT` | 15 | 15 |
| 14 | 비회원 불량품 반품 | `EVIDENCE_SUFFICIENT` | 15 | 14 |
| 15 | 포장 개봉과 환불액 차감 | `EVIDENCE_SUFFICIENT` | 15 | 14 |
| 16 | 국제 배송 파손과 수입 비용 | `EVIDENCE_SUFFICIENT` | 15 | 15 |
| 17 | 예약 주문 배송 지연 | `EVIDENCE_SUFFICIENT` | 15 | 15 |
| 18 | 발송 후 배송 주소 오류 | `EVIDENCE_SUFFICIENT` | 15 | 15 |
| 19 | 고가 상품 반송과 서명 | `EVIDENCE_SUFFICIENT` | 15 | 15 |
| 20 | 플랫폼 밖 부분 환불 | `EVIDENCE_SUFFICIENT` | 15 | 15 |

**집계** · 검색 및 생성 20/20건 · 모델 충분 19건 · 부족 1건 · 정책 미검색 0건 · 검색 청크 300개 · ID가 일치하는 적용 검토 298개. 모델 입력 132,580토큰, 출력 47,490토큰.

**평가 기준** · 정책 검토 프롬프트의 실제 출력인 `searchStatus`를 집계했다. 질문별 `searchNote`는 아래에 원문으로 기록했고, `reviews`와 `evidenceGaps`는 실행 원본에 보존했다. 이전의 검색 청크 직접 검토 건수는 이 모델 출력 집계와 직접 비교하지 않는다.

### 실행 조건

| 변경 항목 | 이번 실험 |
|---|---|
| 청킹 | 게스트 구매 안내 300/200, 나머지 24개 문서 450/200 |
| 검색 결과 상한 | topK=15 |
| 평가 경로 | 동일 한국어 질문으로 코사인 벡터 검색 뒤 gpt-6-luna 근거 판정·응답 생성 |

임베딩 모델은 text-embedding-3-large, 검색 대상은 문서 25개·청크 152개, 최소 유사도는 0.0이다. 실험용 임베딩 파일을 대상으로 코사인 유사도를 계산하고, 기존 PolicyRagService 시스템 프롬프트에 질문과 검색 청크 본문·출처를 전달했다. 검색과 모델 생성은 별도 실험 실행기로 수행했다. 운영 PostgreSQL 인덱스는 그대로 보존했다.

### 질문별 모델 응답


#### 질문 01 · 추적번호 없는 배송 지연

> 11월 18일 eBay.com에서 미국 판매자의 게임 컨트롤러를 결제했고 주문 화면의 배송 예정일은 11월 24일이었습니다. 11월 28일에도 물건을 못 받았고 판매자는 발송했다고만 답했으며 주문 상세에 추적번호가 없습니다. 지금 미수령 신고를 열 수 있나요? 신고 뒤 판매자의 응답을 언제까지 기다리고 어디에서 eBay에 도움을 요청하나요?

**모델 응답 원문**

> 예정 배송일이 지난 뒤 미수령 신고를 하고, 신고 후 판매자가 3영업일 이내에 추적 정보·배송 업데이트 또는 환불로 응답하도록 요구할 수 있으며, 해결되지 않으면 요청 상세에서 eBay에 도움을 요청할 수 있습니다. 다만 환불은 신고만으로 보장되지 않으며, 거래의 보장 자격과 배송·주소 등 사실에 따라 판단됩니다.

#### 질문 02 · 노트북 결함과 부분 환불

> 주문 설명에 화면 결함이 없다고 적힌 중고 노트북을 11월 25일 받았습니다. 첫날부터 화면이 깜박여 영상을 남겼고, 판매자는 수리비로 40달러를 주겠다고 했습니다. 아직 수락하지 않았고 판매 페이지는 반품 불가입니다. 결함 사유로 반품해 상품값과 최초 배송비를 돌려받을 수 있나요? 반송비는 누가 내나요?

**모델 응답 원문**

> 화면 깜박임이 설명되지 않은 결함이라면 ‘설명과 다름/고장’ 사유로 반품을 요청할 근거가 있으며, 판매자의 ‘반품 불가’ 표시는 이 사유의 반품을 막지 않습니다. 반품이 진행되면 정책상 최초 배송비를 포함한 전액 환불과 판매자 부담 반송 배송이 규정되어 있지만, 판매자는 세 영업일 안에 다른 해결책(예: 부분 환불)을 제안할 수도 있으므로 40달러 제안만으로 반품·환불이 확정된 것은 아닙니다.

#### 질문 03 · 발송 전 주문 취소

> eBay.com에서 즉시구매로 헤드폰을 카드 결제한 지 20분 됐습니다. 다른 판매자가 더 싸게 파는 걸 발견해 주문 상세에서 취소를 요청하려고 합니다. 추적번호는 없고 발송 표시도 없습니다. 이 사유로 취소를 요청하면 판매자가 수락해야 하나요? 거절되거나 답이 없으면 주문은 어떻게 되나요?

**모델 응답 원문**

> 가격이 더 싼 판매자를 발견해 마음을 바꾼 경우에도 주문 취소를 요청할 수 있지만, 판매자는 요청을 수락하거나 거절할 수 있으므로 수락해야 할 의무는 없습니다. 이미 발송되지 않았다는 사실만으로 취소가 보장되지는 않으며, 결제한 주문에 판매자가 3일 이내 답하지 않으면 요청이 종료되고 주문은 취소되지 않습니다.

#### 질문 04 · 단순 변심과 최초 배송비

> 검은색 M 사이즈 코트를 주문했고 받은 제품의 색상과 사이즈는 주문과 일치합니다. 실내에서 한 번 입어 봤고 택은 그대로며, 받은 지 4일 됐습니다. 반품 가능으로 표시된 주문에서 단순 변심 반품을 신청하면 결제 시 별도로 낸 최초 배송비 12달러도 환불되나요? 반송 라벨 비용은 누가 부담하나요?

**모델 응답 원문**

> 단순 변심 반품의 반송비는 주문 목록의 반품 조건(무료 반품 또는 구매자 부담)에 따른다는 근거가 있습니다. 최초 배송비 환불 여부도 판매자 반품 정책에 따라 달라질 수 있으므로, ‘반품 가능’ 표시만으로 12달러 환불을 보장할 수 없으며 해당 주문의 상세 조건을 확인해야 합니다.

#### 질문 05 · 파손 반품과 국제 반송비

> 주문한 에스프레소 머신을 개봉하니 물탱크가 갈라져 있었고 배송 상자도 한쪽이 찌그러져 있었습니다. 받은 당일 사진을 찍어 반품을 신청했는데 판매자가 자기 반품 라벨을 보내는 대신 제가 먼저 국제 반송비를 내달라고 합니다. 누가 반송을 준비하고 비용을 부담하나요? 먼저 비용을 내도 되는지 어떻게 확인하나요?

**모델 응답 원문**

> 물탱크가 배송 당시 파손된 것이 확인되면 판매자가 반송비를 부담해야 하며, 판매자는 반송 라벨을 제공하거나 구매자가 수용할 다른 반송 방법을 마련해야 합니다. 다만 국제 반송이 eBay International Shipping으로 처리됐는지는 확인되지 않았으므로, 먼저 주문의 반품 안내와 라벨·비용을 확인하고 판매자가 비용 부담을 거부하면 eBay에 개입을 요청하세요.

#### 질문 06 · 교체 약속과 반품 요청 종료

> 다른 색상의 운동화가 와서 eBay에서 반품 요청을 열었습니다. 판매자는 메시지로 교체품을 보내겠다고 하면서 먼저 요청을 닫아 달라고 합니다. 교체품 추적번호는 아직 없습니다. 요청을 닫은 뒤 교체품이 오지 않으면 같은 주문의 반품 요청을 다시 열거나 eBay에 도움을 요청할 수 있나요?

**모델 응답 원문**

> 다른 색상의 운동화는 listing과 다른 상품에 해당할 수 있어 반품 요청을 진행할 수 있고, 판매자는 절차상 교체품이나 교환을 제안할 수 있습니다. 그러나 요청을 닫으면 같은 거래에 대해 다시 열거나 새 요청을 제출할 수 없으므로, 교체품을 받기 전에 닫으라는 판매자의 요청은 검색된 정책으로 뒷받침되지 않으며, 판매자가 해결하지 못하면 열린 요청에서 eBay에 도움을 요청할 수 있습니다.

#### 질문 07 · 카드사 결제 이의 제기

> 배송 예정일이 사흘 지난 카메라를 받지 못해 eBay에서 미수령 요청을 열었습니다. 판매자 답변을 기다리는 중인데 카드사는 카드 결제에 대한 이의 제기를 접수할 수 있다고 합니다. 지금 카드사에도 같은 결제 건을 접수하면 진행 중인 eBay 요청과 구매자 보호는 어떻게 되나요?

**모델 응답 원문**

> 미수령 신고는 배송 예정일이 지난 뒤 가능하고, 판매자는 신고 후 3영업일 이내에 추적 정보·배송 업데이트 또는 환불로 답해야 하므로 현재 eBay 요청을 기다리거나 요건을 충족하면 eBay에 개입을 요청할 근거가 있습니다. 다만 같은 거래에 대해 카드사에 차지백이나 구매자 보호 청구를 하면 eBay가 진행 중인 보호 요청을 종료할 수 있고, 중복 구제는 허용되지 않으므로 카드사 접수 시 eBay 보호가 유지된다고 단정할 수 없습니다.

#### 질문 08 · 배대지 경유와 상품 불일치

> 미국 판매자의 재킷을 한국에서 사면서 미국 배대지 주소를 eBay 주문 주소로 넣었습니다. 추적에는 배대지 도착으로 나오고 배대지는 상자를 한국으로 다시 보냈습니다. 한국에서 열어 보니 다른 색상의 재킷이 들어 있었는데 배대지에서는 개봉 사진을 찍지 않았습니다. eBay에서 상품 설명 불일치로 반품하거나 환불받을 수 있나요?

**모델 응답 원문**

> 다른 색상의 재킷을 받은 것이 확인된다면 상품 설명 불일치 사유로 반품을 요청할 수 있으며, 판매자는 반품 불가 정책을 내세워 이를 거절할 수 없습니다. 다만 주문 주소가 배대지이고 배대지가 한국으로 재배송한 거래는 제3자 배송대행을 이용한 것으로 보이면 보장 제외일 수 있어, 해당 주문이 eBay 국제배송 프로그램을 통한 배송인지가 핵심입니다.

#### 질문 09 · 한 주문의 일부 미수령

> 한 판매자에게서 카메라와 메모리카드를 각각 장바구니에 넣어 한 번에 결제했습니다. 두 상품의 가격은 주문에 따로 적혀 있고 같은 추적번호가 붙어 있는데 상자에는 카메라만 있었습니다. 메모리카드만 받거나 그 금액만 환불받을 수 있나요? 한 상품 페이지에서 세트로 판매된 경우에도 같은 절차인가요?

**모델 응답 원문**

> 카메라와 메모리카드가 각각 별도 상품 페이지(리스팅)에서 판매된 결합 구매라면, 함께 결제했거나 같은 추적번호로 배송됐더라도 메모리카드만 미수령으로 신고할 수 있습니다. 판매자는 3영업일 안에 배송 상황을 안내하거나 교체·환불을 제안해야 하지만, 메모리카드를 받거나 그 금액만 환불받는 결과가 보장되는 것은 아닙니다. 한 페이지에서 세트·번들로 판매됐다면 빠진 구성품만 따로 신고하는 절차가 아니라 세트 전체에 대해 반품 요청을 해야 하며, 판매자가 받은 상품을 보유하도록 허용하고 부분 환불을 제안할 수 있습니다.

#### 질문 10 · 반송 완료 후 카드 환불 지연

> 판매자가 반송 상자를 11월 19일 받았다고 메시지로 확인했고, 추적도 배송 완료입니다. 11월 28일 현재 카드 명세에 환불이 없고 판매자는 환불을 발행한 날짜를 알려주지 않았습니다. 반품 상세에서 무엇을 확인해야 판매자가 아직 환불을 발행하지 않은 건지 카드에 반영되는 중인지 구분할 수 있나요?

**모델 응답 원문**

> 반품 상세에서 환불 발행 여부와 환불 마감일을 확인하고, 환불이 발행된 경우 원결제수단에 반영되는 데 보통 영업일 기준 3~5일이 걸린다는 점을 확인하는 근거로 사용할 수 있습니다. 판매자가 11월 19일 반송품을 받았다는 사실만으로 환불 발행 여부를 알 수는 없으므로, 상세에 표시된 발행 상태와 마감일을 확인해야 하며, 마감일까지 발행되지 않았다면 eBay에 개입을 요청할 수 있습니다.

#### 질문 11 · 반품 주소 불일치

> eBay 반품 요청에서 받은 선불 라벨에는 캘리포니아 주소가 찍혀 있는데 판매자가 메시지로 네바다 창고 주소에 보내라고 합니다. 반품 상세에는 새 라벨이 없고 발송 기한은 이틀 남았습니다. 어느 주소로 보내야 하나요? 나중에 분쟁이 생기면 어떤 추적 또는 배송 증빙이 필요한가요?

**모델 응답 원문**

> 판매자가 반품 배송비를 부담하는 경우 eBay 정책은 판매자가 라벨 또는 구매자가 수락할 수 있는 다른 반품 방법을 제공하도록 하고, 구매자는 제공된 라벨이나 판매자와 합의한 다른 방법을 사용해야 한다고 정합니다. 따라서 메시지의 네바다 주소가 선불 라벨의 캘리포니아 주소를 대체한다고 단정할 수는 없으니, 반품 상세에서 eBay에 도움을 요청해 주소와 라벨을 확인하고 기한 전 발송 방법을 확인하세요; 추적 가능한 배송과 배송 완료·날짜·수취 주소가 확인되는 기록이 중요합니다.

#### 질문 12 · 고가 상품과 대리 수령

> 배송비와 세금까지 1,080달러인 카메라를 eBay.com에서 주문해 친구 집을 주문 주소로 지정했습니다. 추적에는 배송 완료라고 나오지만 친구는 상자를 받지 않았고 판매자는 배송 완료 화면만 보여줬습니다. 주문 주소와 배송 증거가 일치하는지와 서명 기록을 확인해 달라고 할 수 있나요? 친구가 실제 대리 수령했다면 판단이 달라지나요?

**모델 응답 원문**

> 배송 완료 표시만으로는 충분하지 않으며, eBay.com의 미수령 정책상 주문 상세 주소와 일치하는 배송 증거가 필요하고 총액 1,080달러 주문에는 서명 확인도 요구되므로 주문 주소·배송 기록·서명 기록을 확인해 달라고 요청할 근거가 있습니다. 구매자는 예상 배송일이 지난 뒤 30일 이내에 미수령 신고를 하고 판매자에게 확인을 요청할 수 있지만, 환불은 실제 미배송·미수령 여부와 자격 요건에 따라 결정되며 친구가 대리 수령했다면 제3자 수령 제외 조항이 적용되어 보호되지 않을 수 있습니다.

#### 질문 13 · 운동화 상태와 위조 의심

> 새 제품으로 표시된 운동화를 받았는데 밑창에 흙과 마모 흔적이 있고 상자와 신발의 제품 코드도 다릅니다. 주문 화면에서 정품 인증 대상 표시를 확인하지 못했고 받은 지 이틀 됐습니다. 상태가 설명과 다르다는 사유와 위조 의심 중 어떤 절차로 문제를 제기할 수 있나요?

**모델 응답 원문**

> 밑창의 흙·마모 흔적은 ‘새 제품’ 설명과 맞지 않을 수 있고, 제품 코드 불일치는 진품 여부 확인을 요청할 근거가 될 수 있습니다. 다만 주문에 Authenticity Guarantee 표시가 있었는지 확인되지 않았으며, 적용 대상·반품 가능 기간과 최종 판매 여부도 주문 정보 없이는 판단할 수 없습니다.

#### 질문 14 · 비회원 불량품 반품

> eBay 계정 없이 신용카드로 결제했고 주문 확인 이메일은 있지만 로그인할 계정이 없습니다. 어제 도착한 블루투스 스피커는 전원이 켜지지 않습니다. 이메일에서 어디로 들어가 반품을 시작하고 불량 사진이나 설명을 어떻게 제출하나요?

**모델 응답 원문**

> 주문 확인 이메일에서 ‘View order details’를 누른 뒤 ‘Return item’을 선택하고, 반품 사유를 고른 다음 ‘Next’를 누르세요. 판매자에게 메시지를 쓰고 불량 사진을 최대 10장 첨부한 뒤 ‘Confirm return’을 선택할 수 있습니다. 전원이 켜지지 않는 제품은 결함 사유 반품 및 eBay Money Back Guarantee 대상일 수 있지만, 보장 자격은 모든 요건 충족 여부에 달려 있으며 환불이나 교환 결과가 자동으로 보장되는 것은 아닙니다.

#### 질문 15 · 포장 개봉과 환불액 차감

> 설명과 다른 이어폰을 반품했습니다. 포장 씰을 뜯어 5분간 작동을 확인한 뒤 케이블과 설명서까지 전부 돌려보냈습니다. 판매자가 개봉을 이유로 180달러 중 60달러를 차감했고 다른 훼손은 설명하지 않았습니다. 어떤 조건에서 차감에 이의를 제기하고 eBay에 도움을 요청할 수 있나요?

**모델 응답 원문**

> 설명과 다른 상품은 eBay 환불 보장에 따라 반품 대상이 될 수 있고, 반품품의 씰 개봉만으로는 판매자 지침상 원래 상태인 경우 5~10% 차감 범주이며, 필요한 작동 확인을 넘은 사용으로 가치가 하락했을 때에만 차감이 정당화될 수 있습니다. 60달러(약 33%) 차감이 적정한지는 정책만으로 확정할 수 없으므로, 판매자에게 차감 사유와 실제 가치 하락 근거를 요청하고 해결되지 않으면 반품 후 3영업일 경과 시 eBay에 개입을 요청할 수 있습니다.

#### 질문 16 · 국제 배송 파손과 수입 비용

> eBay International Shipping으로 주문한 310달러짜리 조명이 도착했을 때 유리 갓이 깨져 있었습니다. 결제 영수증에는 eBay에 낸 수입 비용 26달러가 있고, 배송 때 운송사에도 세금 명목으로 15달러를 냈습니다. 파손 반품이 승인되면 상품값 외에 두 곳에 낸 수입 비용을 각각 돌려받을 수 있나요?

**모델 응답 원문**

> 도착 중 파손에 대해 eBay Money Back Guarantee 청구가 승인되면 eBay에 낸 세금·관세·수입 비용도 환불 대상이 될 수 있고, EIS 약관상 배송 중 파손 또는 승인된 청구가 요건입니다. 다만 운송사에 별도로 낸 15달러의 환불은 검색된 정책이 보장하지 않으며, 결제 내역과 파손 발생 경로 및 청구 승인 여부를 확인해야 합니다.

#### 질문 17 · 예약 주문 배송 지연

> 8월에 예약 구매한 피규어의 상품 페이지에는 10월 발송 예정이라고 적혀 있었습니다. 11월 말에도 추적번호가 없고 판매자는 다시 다음 달로 미뤘습니다. 주문 후 30일은 지났으며 아직 미수령 신고는 열지 않았습니다. 지금 취소를 요청하거나 미수령으로 환불을 요청할 수 있나요?

**모델 응답 원문**

> 판매자에게 지금 취소를 요청할 수는 있지만, 구매자의 취소 요청을 받아들일지는 판매자가 결정하며 취소가 보장되지는 않습니다. 미수령 신고는 예상 배송일이 지난 뒤 가능하고, 안내된 기한은 예상 배송일로부터 30일 이내이므로 주문 후 30일이 지났다는 사실만으로 기한이 지난 것은 아니지만, 실제 주문의 예상 배송일과 현재 신고 가능 여부는 주문 내역에서 확인해야 합니다.

#### 질문 18 · 발송 후 배송 주소 오류

> 결제 직후 확인해 보니 주문에 입력한 주소는 이사 전 아파트입니다. 판매자에게 새 주소를 보냈지만 이미 택배사에 넘겼고 추적은 배송 중입니다. 주문 주소를 수정하거나 올바른 주소로 돌려 보내 달라고 요청할 수 있나요? 기존 주소에서 받지 못하면 환불을 요청할 수 있나요?

**모델 응답 원문**

> 검색된 정책에 따르면 결제 시 입력한 주소가 틀렸다면 판매자에게 연락해 주문 취소가 가능한지 요청할 수 있지만, 이미 발송된 주문은 취소할 수 없고 eBay는 원래 주소에서 소포를 찾아보도록 권고합니다. 주소를 새 주소로 바꾸거나 발송물을 돌려 보내 달라는 요청이 받아들여진다는 보장은 없으며, 주소 오류만으로 환불이 보장되지는 않습니다. 예상 배송일이 지난 뒤에도 물품을 받지 못하면 정해진 절차로 미수령 신고를 할 수 있고, 환불 여부는 주문 주소로의 배송 증빙 등 자격 요건에 따라 결정됩니다.

#### 질문 19 · 고가 상품 반송과 서명

> eBay.com에서 세금과 배송비까지 900달러인 상품을 구매했고 반품 배송비는 제가 결제했습니다. 추적번호를 반품 요청에 올렸고 판매자 주소로 배송 완료로 나오지만 서명 옵션을 사지 않았습니다. 판매자가 반송품을 받지 못했다고 하면 추적 기록만으로 반품 배송을 입증할 수 있나요?

**모델 응답 원문**

> 판매자가 부담하는 반품 배송이라면 추적 가능한 배송을 사용하고, 배송 완료 상태·날짜·판매자의 지정 반품 주소와 일치하는 주소 정보로 반송 배송을 입증해야 합니다. 다만 eBay.com 거래 총액이 750달러 이상이면 반품에도 서명 확인이 필수이므로, 세금과 배송비를 포함해 900달러인 이 거래에서 서명 없이 추적 기록만으로는 정책상 보호 요건을 충족하지 못하며 환불 결과가 보장되지 않습니다.

#### 질문 20 · 플랫폼 밖 부분 환불

> 240달러짜리 제품의 결함으로 eBay에서 반품 요청을 진행 중입니다. 판매자가 eBay 메시지로 외부 계좌이체로 70달러를 보내겠다고 제안했으며, 아직 송금은 받지 않았고 요청도 열려 있습니다. 송금을 받아도 남은 170달러에 대해 같은 요청에서 계속 다투거나 eBay 개입을 요청할 수 있나요?

**모델 응답 원문**

> 결함 제품은 eBay Money Back Guarantee 대상일 수 있고, 판매자는 부분 환불을 제안할 수 있으며 해결되지 않으면 정해진 시점 이후 eBay에 개입을 요청할 수 있습니다. 다만 검색된 정책은 외부 계좌이체로 70달러를 받은 뒤 같은 요청에서 나머지 금액을 청구할 수 있는지 명시하지 않으므로, 송금을 받기 전 요청을 열린 상태로 유지하고 eBay에 확인하세요.
