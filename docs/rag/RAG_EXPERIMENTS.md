# 정책 RAG 실험 기록

## 1회차 · 배송 완료 표시와 미수령 환불 · 2026-11-25

### 입력 질의

> 주문 상품을 받지 못했지만 판매자는 배송 조회에 ‘배송 완료’로 표시된다는 이유로 환불을 거절했다. 소비자는 배송 증빙을 확인하고 주문 주소로 배송된 사실이 확인되지 않으면 상품값과 최초 배송비 전액을 돌려받고 싶다.

### 실행 조건

로컬 백엔드의 `GET http://localhost:8080/rag`에 질의를 한 번 요청했다.

| 파라미터 | 요청값 | 적용 범위 |
|---|---|---|
| `platform` | `EBAY` | 정책 문서의 플랫폼 필터 |
| `topK` | `5` | 벡터 검색 청크와 응답 조항의 최대 개수 |
| `similarityThreshold` | `0.0` | 벡터 검색 최소 유사도 |

### 정책 문서 적재 현황

2026-11-25에 백엔드의 벡터 테이블 `public.policy_vector_store`를 조회했다. 임베딩이 저장된 정책은 한 문서, 총 13개 청크였다.

| 플랫폼 | 사이트 | 정책 문서 | 적재 청크 |
|---|---|---|---:|
| `EBAY` | `ebay.com` | [eBay Money Back Guarantee](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | 13 |

프로젝트의 HTML 리소스 보유 여부와 별도로, 검색에 사용할 수 있는 벡터 DB의 적재 상태를 기록했다.

### 관찰된 응답

HTTP 200으로 응답했으며, 정책 검토 상태는 `EVIDENCE_INSUFFICIENT`였다. 응답에는 동일한 [eBay Money Back Guarantee 정책](https://www.ebay.com/help/policies/ebay/ebay?id=4210)에서 가져온 조항 세 개가 있었다. 세 조항의 적용 여부는 모두 `UNCERTAIN`이었다.

| 반환 순서 | 조항 | 응답이 제시한 근거 |
|---|---|---|
| 1 | When the buyer doesn't receive an item | 주문 주소로 상품이 배송되지 않았을 때 상품값과 해당하는 최초 배송비의 전액 환불 근거 |
| 2 | Deciding the outcome when the buyer doesn't receive an item | 구매자 보호 자격을 충족하는 거래에서 배송 또는 수령 증빙을 확인하고, 배송·수령이 이루어지지 않았다고 판단하면 전액 환불한다는 근거 |
| 3 | Evidence of successful delivery | 추적번호, 배송 스캔, 배송 상태·날짜, 수취인 주소 일치 및 주문 금액에 따른 서명 확인 요건 |

응답은 소비자가 요청한 전액 환불의 정책 근거를 찾았다. 배송 조회의 ‘배송 완료’ 표시만으로는 증빙 요건 충족을 확인할 수 없다고 설명했고, 거래 자격·신고 시점·주문 금액·실제 배송 증빙을 미확인 사항으로 남겼다. 조항별 요약 없이 응답의 `note`에 판단을 통합했다.

### 실제 응답의 note

> 정책은 주문 상세의 주소로 배송되지 않았다고 확인되면 상품값과 최초 배송비 전액 환불을 규정합니다. 배송 조회에 ‘배송 완료’가 표시된다는 사실만으로는 정책상 배송 증빙 요건이 모두 충족되었는지 알 수 없습니다. 통합 배송사의 eBay 업로드 추적번호와 배송 스캔, 배송 상태와 날짜, 주문 상세 주소와 일치하는 수취인 주소(우편번호가 없으면 시·군·구), 그리고 총 주문 금액이 $750 이상인 경우 서명 확인이 필요합니다. 제공된 거래 사실에는 이러한 증빙의 내용, 주문 금액, 거래의 자격 요건 충족 여부와 신고 시점이 확인되지 않아 환불 적용 여부를 확정할 수 없습니다. 해당 증빙을 확인해 주문 주소로 배송된 사실이 입증되지 않으면 전액 환불 조항이 요청을 뒷받침합니다.

## 2회차 · eBay 정책·안내문 교차 검색 · 2026-11-25

eBay 정책·안내문 24개(88개 청크)를 적재한 뒤 아래 질의 10개를 개별 실행했다. 적재에는 `chunkSize=600`, `minChunkSizeChars=200`을 사용했다.

| 실행 파라미터 | 값 |
|---|---|
| 엔드포인트 | `GET http://localhost:8080/rag` |
| `platform` | `EBAY` |
| `topK` | `5` |
| `similarityThreshold` | `0.0` |

아래 조항은 RAG 응답이 실제로 인용한 항목이다.

### 질의 01

> 배송 조회에는 ‘배송 완료’로 표시되지만 상품을 받지 못했습니다. 판매자는 그 표시만 근거로 환불을 거절했습니다. 주문 주소에 배송됐다는 사실을 확인하려면 어떤 증빙이 필요하고, 배송이 확인되지 않으면 상품값과 최초 배송비를 돌려받을 수 있나요?

HTTP 200 · 응답의 status 값 확인 불가(공란) · 반환 조항 4개

- Evidence of successful delivery · `UNCERTAIN` · [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210)
- Deciding the outcome when the buyer doesn't receive an item · `UNCERTAIN` · [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210)
- 제목 없음 · `UNCERTAIN` · [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210)
- Report an item you didn't receive · `APPLICABLE` · [Get help with an item that hasn't arrived](https://www.ebay.com/help/returns-refunds/item-not-delivered/item-not-received?id=4042)

**실제 응답의 note**

> 확인된 근거: 배송이 성공적으로 확인됐다는 증빙에는 eBay에 업로드된 통합 배송업체의 추적 번호와 기한 내 발송 스캔, 배송 완료 또는 배송 시도 상태와 날짜, 주문 상세 주소와 일치하는 우편번호(없으면 도시/카운티), 그리고 총 주문액이 $750 이상이면 서명 확인이 필요합니다. 정책은 상품이 주문 주소로 배송되지 않은 것으로 판단되면 상품값과 최초 배송비(해당하는 경우) 전액 환불을 규정합니다.
> 부족한 근거: 현재 확인된 거래 사실은 조회가 ‘배송 완료’라고 표시된다는 것뿐이며, 위 배송 증빙 요건을 충족하는지 또는 상품이 주문 주소에 실제 배송됐는지는 확인되지 않았습니다. 따라서 환불 결과는 확정할 수 없습니다. 구매 내역에서 ‘상품을 받지 못함’을 신고하고, 배송 사진이나 배송업체의 주소·배송일 확인 자료 등 추가 배송 정보를 요청하세요. 안내문상 배송일로부터 30일 이내 신고해야 보호 대상이 될 수 있고, 판매자가 해결하지 않으면 eBay에 개입을 요청할 수 있습니다.

### 질의 02

> 상품 설명에는 정상 작동한다고 되어 있었는데 받아 보니 전원이 켜지지 않습니다. 판매자는 상품 페이지에 ‘반품 불가’라고 적었다며 요청을 거절했습니다. 반품과 환불을 요청할 근거는 무엇이고, 반품 배송비는 누가 부담하나요?

HTTP 200 · `EVIDENCE_INSUFFICIENT` · 반환 조항 3개

- 제목 없음 · `APPLICABLE` · [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041)
- 제목 없음 · `APPLICABLE` · [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115)
- 제목 없음 · `UNCERTAIN` · [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041)

**실제 응답의 note**

> 확인된 근거: 상품 설명과 달리 전원이 켜지지 않는다는 사실은 상품이 설명과 다르거나 고장 난 상태로 도착한 경우에 해당할 수 있습니다. eBay 안내에 따르면 이런 경우 판매자의 ‘반품 불가’ 정책이 있어도 반품을 요청할 수 있으며, 반품 요청을 열어 사유를 선택하고 고장 상태를 보여주는 사진을 첨부할 수 있습니다.
> 부족한 근거: 반품 배송비는 반품 사유와 판매자의 반품 정책에 따라 달라진다고 안내되어 있지만, 제공된 검색 결과에는 이 거래에서 누가 비용을 부담하는지 정하는 구체적인 규정이나 판매자의 전체 반품 정책이 없습니다. 따라서 배송비 부담자는 현재 자료만으로 확정할 수 없습니다. 판매자가 해결하지 않거나 요청 후 3영업일 이내 응답하지 않으면 eBay에 개입을 요청할 수 있습니다.

### 질의 03

> 반품을 접수했고 추적 기록에는 상품이 판매자에게 전달된 것으로 나옵니다. 판매자가 반품 수령 후 4영업일째 응답도 환불도 하지 않습니다. 환불 요청 상태를 어디서 확인하고, 어떤 조건에서 eBay에 개입을 요청할 수 있나요?

HTTP 200 · `EVIDENCE_INSUFFICIENT` · 반환 조항 2개

- The seller issues a refund; Ask eBay to step in · `UNCERTAIN` · [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210)
- Get your refund; Ask eBay to step in and help · `APPLICABLE` · [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041)

**실제 응답의 note**

> 확인된 근거: 추적 기록상 반품 상품이 판매자에게 전달되었고, 판매자가 수령 후 4영업일째 환불하지 않았다는 사실은 확인됩니다. eBay 안내에 따르면 Purchases(구매 내역)에서 해당 상품의 See return details(반품 세부정보)를 선택하면 요청 상태와 환불 기한을 확인할 수 있습니다.
> 부족한 근거: 환불 기한은 반품 세부정보에 표시되며 판매자에게 추가 시간이 주어지는 경우도 있습니다. 그 기한이 이미 지났는지는 확인되지 않아 현재 eBay 개입 요청 조건을 충족했는지 단정할 수 없습니다. 세부정보에서 기한을 확인하고, 기한이 지났다면 See return details > Ask eBay to step in and help를 선택해 사유를 제출하세요.

### 질의 04

> 판매자가 반품을 받은 뒤 사용 흔적이 있다며 환불액 일부를 차감했습니다. 저는 개봉해서 상품을 확인한 뒤 구성품을 모두 넣어 보냈습니다. 어떤 경우에 차감할 수 있고, 차감에 동의하지 않으면 어떻게 이의를 제기할 수 있나요?

HTTP 200 · `EVIDENCE_INSUFFICIENT` · 반환 조항 4개

- Condition of returns policy overview · `UNCERTAIN` · [Condition of returned items policy](https://www.ebay.com/help/policies/member-behavior-policies/condition-returned-items-policy?id=4763)
- Misuse of this protection · `UNCERTAIN` · [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115)
- Ask eBay to step in and help · `UNCERTAIN` · [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115)
- What to do if an item is returned used or damaged · `UNCERTAIN` · [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115)

**실제 응답의 note**

> 확인된 근거: eBay 정책상 상품 상태·특성·작동 방식을 확인하는 데 필요하지 않은 취급으로 상품 가치가 떨어졌다면 차감할 수 있지만, 같은 상태로 반품된 상품의 시장 손실이나 반품 배송비·재입고 비용을 보전하기 위한 차감은 허용되지 않습니다. 확인된 거래 사실은 상품을 개봉해 확인하고 구성품을 모두 넣어 보냈다는 귀하의 설명뿐이며, 실제 상품 상태와 차감 사유·금액의 근거는 확인되지 않았습니다.
> 부족한 근거: 단순 확인을 넘는 사용·변경·손상이나 가치 하락이 있었는지, 원래 포장 및 구성품이 모두 반환되었는지, 차감이 실제 가치 하락에 해당하는지 확인할 자료가 없어 차감의 적정성을 확정할 수 없습니다. 반품 요청 화면에서 판매자에게 차감 사유와 상품 상태를 설명하는 자료를 요청하고, 반품 전후 사진·구성품 목록·포장 및 배송 증빙을 모아 이의를 제기하세요. 해결되지 않은 경우 3영업일이 지나면 eBay에 개입을 요청할 수 있습니다. 환불이 이미 처리됐다면 반품이 최대 10일간 열려 있을 수 있으므로 그 기간 내 요청 가능 여부를 확인하세요. eBay가 개입하면 판매자는 이후 차감할 수 없다는 정책도 있으나, 현재 거래에 적용되는지는 확인되지 않았습니다.

### 질의 05

> 결제 직후 배송 주소의 번지가 틀린 것을 발견했습니다. 아직 발송 표시가 없고 판매자는 주문 주소를 바꿔 줄 수 없다고 합니다. 주문을 취소하고 정확한 주소로 다시 구매하려면 어떻게 해야 하고, 취소가 승인되면 결제 금액은 어떻게 환불되나요?

HTTP 200 · `EVIDENCE_INSUFFICIENT` · 반환 조항 3개

- Changing your shipping address after you’ve bought an item · `APPLICABLE` · [Changing your shipping details after a purchase](https://www.ebay.com/help/buying/shipping-delivery/tracking-item?id=4028)
- Cancel an order · `APPLICABLE` · [How buyers can cancel an order](https://www.ebay.com/help/buying/buy-now/cancel-order?id=4004)
- Refunds · `UNCERTAIN` · [Order cancellation policy](https://www.ebay.com/help/member-behavior-policies/policies/order-cancellation-policy?id=5298)

**실제 응답의 note**

> 확인된 근거: 주소 변경은 일반적으로 판매자가 할 수 없으며, 아직 발송되지 않았다면 주문 취소를 요청한 뒤 정확한 주소로 다시 구매하라는 안내가 있습니다. Purchase history에서 해당 주문의 More actions > Cancel this order를 선택해 Submit하면 판매자가 3일 이내 수락 또는 거절할 수 있습니다. 판매자가 취소를 수락하고 eBay checkout으로 결제했다면 전액이 원래 결제 수단으로 환불됩니다.
> 부족한 근거: 결제가 eBay checkout을 통해 완료됐는지는 확인되지 않아 환불 방식에 대한 해당 조건의 적용 여부는 확정할 수 없습니다. 취소 요청은 판매자 승인이 필요하므로 취소 자체도 아직 확정된 것은 아닙니다.

### 질의 06

> 선주문 상품의 판매 페이지에 적힌 발송 예정일이 지났는데 판매자는 새 발송일을 알려주지 않았습니다. 주문에 아직 발송 기록이 없습니다. 선주문에서 판매자가 지켜야 할 발송 조건은 무엇이고, 구매자가 취소와 환불을 요청할 수 있나요?

HTTP 200 · `EVIDENCE_INSUFFICIENT` · 반환 조항 4개

- Canceling an order · `UNCERTAIN` · [Order cancellation policy](https://www.ebay.com/help/member-behavior-policies/policies/order-cancellation-policy?id=5298)
- Actions & time frames for order cancellations · `UNCERTAIN` · [Order cancellation policy](https://www.ebay.com/help/member-behavior-policies/policies/order-cancellation-policy?id=5298)
- Refunds · `UNCERTAIN` · [Order cancellation policy](https://www.ebay.com/help/member-behavior-policies/policies/order-cancellation-policy?id=5298)
- 제목 없음 · `UNCERTAIN` · [How buyers can cancel an order](https://www.ebay.com/help/buying/buy-now/cancel-order?id=4004)

**실제 응답의 note**

> 확인된 근거: 검색된 취소 정책에 따르면 아직 발송되지 않은 주문은 취소 요청을 제출할 수 있지만, 판매자가 수락하거나 거절할 수 있습니다. 취소가 이루어지고 eBay 결제로 결제한 경우에 한해 원래 결제 수단으로 전액 환불된다고 안내합니다. 구매 내역에서 취소 요청을 제출할 수 있으며, 판매자는 요청 후 3일 이내에 답변하도록 안내되어 있습니다.
> 부족한 근거: 선주문 상품의 발송 예정일이 지난 경우 판매자가 지켜야 할 구체적인 발송 조건이나 예정일 변경 통지 의무는 검색된 문서에서 확인되지 않습니다. 주문일이 30일 이내인지, eBay Live·경매 주문인지, 결제 방식 및 취소 요청의 수락 여부도 확인되지 않아 취소와 환불이 확정된다고 판단할 수 없습니다.

### 질의 07

> eBay International Shipping으로 받은 상품이 도착했을 때 파손돼 있었습니다. 판매자는 국제 배송 중 파손이라며 자신에게 반품을 보내지 말라고 합니다. 누구에게 문제를 신고하고, 환불을 받으려면 어떤 증빙과 반송 절차가 필요한가요?

HTTP 200 · `EVIDENCE_INSUFFICIENT` · 반환 조항 3개

- 11. International Returns · `APPLICABLE` · [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/)
- 제목 없음 · `UNCERTAIN` · [International purchases and shipping for buyers](https://www.ebay.com/help/buying/shipping-delivery/international-shipping-ebay?id=4057)
- 11. International Returns · `UNCERTAIN` · [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/)

**실제 응답의 note**

> 확인된 근거: EIS 약관은 실제 배송일로부터 30일 안에 반품을 요청할 수 있고, 국제 반품은 판매자가 아닌 eBay가 처리·응답하며 eBay가 제공한 라벨의 반송 주소로 보내도록 정합니다. 사용자가 도착 당시 파손됐다고 밝혔으므로, 판매자에게 직접 보내지 말고 eBay의 반품 절차를 통해 요청할 근거가 있습니다.
> 부족한 근거: 검색된 문서에는 파손 신고에 필요한 사진·포장재 등 증빙의 구체적 요건이나 국제 운송 중 파손이 이 사안에 적용되는지에 대한 판단 기준이 없습니다. 환불은 반송품 검사 후 승인될 수 있고 보장되지 않으며, 승인되더라도 반송품이 지정 주소에 도착한 뒤 약 5~7영업일이 걸릴 수 있습니다. 배송일과 실제 파손 상태를 확인할 자료는 제공되지 않았습니다.

### 질의 08

> 현지 픽업으로 결제했는데 약속한 장소와 시간에 갔을 때 판매자가 나타나지 않았습니다. 상품을 받지 않았고 수령 코드도 제공하지 않았습니다. 미수령 환불을 요청할 수 있나요? 판매자가 제가 이미 수령했다고 주장하면 어떤 증빙이 필요한가요?

HTTP 200 · `EVIDENCE_INSUFFICIENT` · 반환 조항 3개

- Seller isn't responding or you need to reschedule · `UNCERTAIN` · [Buying with local pickup](https://www.ebay.com/help/buying/shipping-delivery/changing-delivery-address-method/local-pickup?id=4056)
- Seller won't scan or enter the code · `APPLICABLE` · [Buying with local pickup](https://www.ebay.com/help/buying/shipping-delivery/changing-delivery-address-method/local-pickup?id=4056)
- Proof that the buyer collected the item · `UNCERTAIN` · [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210)

**실제 응답의 note**

> 확인된 근거: 현지 픽업 안내는 판매자가 픽업을 확인하지 않으면 상품을 두고 오고 Purchases에서 미수령으로 신고할 수 있다고 안내합니다. 사용자는 판매자가 나타나지 않았고 상품과 수령 코드를 주고받지 않았다고 했으므로, 미수령 신고를 뒷받침하는 상황입니다. 판매자 수령 주장의 증거로는 픽업 당시 구매자가 서명한 eBay 주문 상세 사본이나 판매자가 QR 코드를 스캔하거나 6자리 코드를 입력한 기록이 제시되어 있습니다.
> 부족한 근거: 이 안내는 미수령 신고 방법을 제시하지만, 현재 사실만으로 환불 결과를 보장하지는 않습니다. 판매자가 응답하지 않는 경우의 취소·환불 절차는 eBay 메시지로 후속 연락을 하고 24~48시간 뒤에도 답이 없을 때 다시 명확한 기한을 제시한 뒤 응답이 없으면 진행하는 조건입니다. 해당 연락 내역, 주문 상태, 결제 여부 및 판매자가 실제로 수령 완료 처리했는지는 확인되지 않았습니다.

### 질의 09

> 주문할 때 차량 정보를 정확히 입력했고 상품 페이지에 Guaranteed Fit 표시가 있었는데, 받은 부품이 제 차량에 맞지 않습니다. 판매자는 제가 반품 배송비를 부담해야 한다고 합니다. 보장 적용을 확인하려면 무엇을 봐야 하고, 어떤 절차로 반품과 환불을 요청하나요?

HTTP 200 · `EVIDENCE_INSUFFICIENT` · 반환 조항 4개

- How it works · `UNCERTAIN` · [eBay Guaranteed Fit](https://pages.ebay.com/motors/ebay-guaranteed-fit/)
- Eligibility · `UNCERTAIN` · [eBay Guaranteed Fit](https://pages.ebay.com/motors/ebay-guaranteed-fit/)
- How do returns work for eBay Guaranteed Fit? · `UNCERTAIN` · [eBay Guaranteed Fit](https://pages.ebay.com/motors/ebay-guaranteed-fit/)
- How long do I have to open a return? · `UNCERTAIN` · [eBay Guaranteed Fit](https://pages.ebay.com/motors/ebay-guaranteed-fit/)

**실제 응답의 note**

> 확인된 근거: 적격 부품을 리스팅의 체크 표시와 함께 구매했고 해당 부품이 차량에 맞지 않는다면, Guaranteed Fit은 수령 후 30일 이내 무료 반품과 전액 환불을 안내합니다. My eBay의 Purchases에서 반품을 시작하고 사유로 “Doesn’t fit my vehicle”을 선택하면 무료 반품 라벨을 받을 수 있으며, 반품 승인 후 라벨이 오지 않으면 eBay에 개입을 요청할 수 있습니다.
> 부족한 근거: 차량 정보를 정확히 입력했고 상품 페이지에 Guaranteed Fit 표시가 있었다는 설명만으로는 주문한 상품의 체크 표시·프로그램 적격 여부, 해당 부품의 예외 해당 여부, 결제 수단 및 Money Back Guarantee 요건 충족 여부, 반품 신청 기한 내 여부가 확인되지 않습니다. 주문/리스팅의 체크 표시와 상품 유형, 결제 내역, 배송일 및 판매자의 반품 기간을 확인하세요. 바퀴와 타이어는 Guaranteed Fit에서 제외됩니다.

### 질의 10

> 미수령 신고가 배송 완료 기록을 근거로 종결됐습니다. 종결 후 배송사로부터 다른 우편번호로 배달됐다는 확인을 받았습니다. 이 자료로 eBay 결정에 이의를 제기할 수 있나요? 제출 기한과 배송 증빙 기준은 무엇인가요?

HTTP 200 · `EVIDENCE_SUFFICIENT` · 반환 조항 4개

- Appeal the outcome of a case as a buyer · `APPLICABLE` · [Appeal the outcome of a case as a buyer](https://www.ebay.com/help/buying/returns-refunds/appeal-ebays-decision-return-missing-item-buyers?id=4039)
- Information you may need when appealing · `APPLICABLE` · [Appeal the outcome of a case as a buyer](https://www.ebay.com/help/buying/returns-refunds/appeal-ebays-decision-return-missing-item-buyers?id=4039)
- Evidence of successful delivery · `APPLICABLE` · [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210)
- 제목 없음 · `APPLICABLE` · [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210)

**실제 응답의 note**

> 정책 근거: 배송 완료 증빙에는 주문 상세의 우편번호(우편번호를 확인할 수 없으면 도시/카운티)와 일치하는 수령인 주소가 필요하며, 구매자 이의 제기 안내는 다른 주소로 배송됐다는 증거를 새 정보의 예로 명시합니다. 배송사가 주문 주소와 다른 우편번호로 배달됐다고 확인한 사실은 이의를 뒷받침합니다.
> 요청 방법: 사건 종결 후 30일 이내에 구매 내역에서 해당 상품의 ‘See case details’와 ‘Send us an appeal’을 선택해 배송사의 확인 자료를 제출하세요. 이의 제기는 검토를 요청하는 절차이며, 환불 결정이 보장되지는 않습니다.
