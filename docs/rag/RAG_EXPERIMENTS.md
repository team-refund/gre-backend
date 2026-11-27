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

## 검색 결과 전체 청크 반환 검증 · 2026-11-26

### 실행 조건

| 항목 | 값 |
|---|---|
| 요청 | `GET http://localhost:8080/rag` |
| `question` | Find the eBay refund policy for an item marked delivered but not received |
| `platform` | `EBAY` |
| `topK` | `5` |
| `similarityThreshold` | `0.0` |
| 적재 파라미터 | `chunkSize=450`, `minChunkSizeChars=200` |
| 최근 적재 검증 | eBay 문서 18개, 125개 청크 |
| HTTP 상태 | `200 OK` |

### 실제 응답 원문

```json
{"searchStatus":"EVIDENCE_SUFFICIENT","searchNote":"검색된 공식 eBay Money Back Guarantee 정책에는 미수령 신고 기한과 판매자 응답 기한, eBay 개입 가능성, 성공 배송으로 인정되기 위한 추적·주소·배송 상태 및 750달러 이상 주문의 서명 확인 요건이 있습니다. 배송 완료 표시만으로 환불 여부가 확정되는지는 검색된 내용만으로 판단할 수 없습니다. 거래별 주문 정보와 배송 증빙은 제공되지 않았습니다. 다른 검색 청크는 설명과 다른 상품의 반품이나 일반적인 반품 절차로, 미수령 판단에는 적용되지 않습니다.","clauses":[{"articleNumber":null,"articleTitle":null,"chunkId":"5f7fe4d0-426d-4a68-b4e3-71d8bfa89510","officialUrl":"https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041","originalTextEn":"Top Takeaway If the item you received doesn't match the listing, or if it arrived faulty or damaged, you can return it even if the seller's returns policy says they don't accept returns. Was this article helpful for you? Related help topics See other articles in: Returns and refunds Returning items You might also be interested in: eBay Money Back Guarantee policy eBay Money Back Guarantee covers most transactions on eBay. It means buyers can get their money back if an item didn't arrive, is faulty or damaged, or doesn't match the listing. 13 min article Return shipping for buyers There are several ways you can send an item back to the seller. Who pays the cost of return shipping depends on the seller’s return policy and the reason for the return. 2 min article Ask eBay to step in and help for buyers You can ask us to step in if there's an issue with your order and the seller hasn't been able to help. 1 min article","policyName":"Return an item for a refund"},{"articleNumber":null,"articleTitle":null,"chunkId":"19d1e372-5b08-4a80-8cf3-7792894c6d3f","officialUrl":"https://www.ebay.com/help/policies/ebay/ebay?id=4210","originalTextEn":"eBay may step in without the buyer asking if there is no valid tracking information available Earliest: 3 business days after the report date Latest: 21 business days after the report date Deciding the outcome when the buyer doesn't receive an item When a buyer reports that they didn't receive an item and the transaction meets our eligibility requirements, we will look for: Evidence of successful delivery to the address provided in the Order details, or Proof that the buyer collected the item If we determine that the item was not successfully delivered or collected: The buyer will receive a refund for the full cost of the item and original shipping, and The seller may be required to reimburse eBay for the amount of the refund Evidence of successful delivery We require all of the following to prove a successful on-time delivery: Tracking number from an integrated carrier which was uploaded to eBay and shows a shipping scan before the latest estimated delivery date; A delivery status of \"delivered\" or \"attempted delivery\" (or equivalent in the country to which the item was delivered); The date of delivery or attempted delivery; The recipient's address, showing the zip code (or international equivalent) that matches the one on the Order details page. If the zip code is unavailable, the recipient's city/county must match; and Signature confirmation, on orders with a total cost of $750 or more. Learn more about our signature confirmation requirements. For eBay Authenticity Guarantee items, additional evidence is required. See Evidence of successful delivery to the authenticator below.","policyName":"eBay Money Back Guarantee policy"},{"articleNumber":null,"articleTitle":null,"chunkId":"bbba9a37-a92a-454b-a9fe-c56a79ec3136","officialUrl":"https://www.ebay.com/help/policies/ebay/ebay?id=4210","originalTextEn":"- opens in new window or tab ebay.com.au - opens in new window or tab ebay.ca - opens in new window or tab cafr.ebay.ca - opens in new window or tab ebay.ie - opens in new window or tab ebay.nl - opens in new window or tab ebay.pl - opens in new window or tab benl.ebay.be - opens in new window or tab eBay Buyer Protection: ebay.de - opens in new window or tab ebay.at - opens in new window or tab ebay.ch - opens in new window or tab eBay Customer Guarantee: ebay.fr - opens in new window or tab ebay.it - opens in new window or tab ebay.es - opens in new window or tab befr.ebay.be - opens in new window or tab When the buyer doesn't receive an item Actions and timeframes when the buyer doesn't receive an item Deciding the outcome when the buyer doesn't receive an item Exclusions and special coverage when the buyer doesn't receive an item Sellers are required to deliver the item to the address in the Order details, unless the buyer selected a local pickup option. If the item doesn't arrive at the address provided by the buyer at checkout or isn't made available for collection, the buyer is entitled to a full refund, including original shipping costs (if applicable). Actions & time frames when the buyer doesn't receive an item Action Time frame The buyer reports that the item hasn't arrived or was not available for collection Report an item that hasn't arrived Earliest: Once the estimated or actual delivery/collection date has passed Latest: 30 calendar days after the estimated or actual delivery/collection date has passed The seller responds to the buyer's report The seller is required to respond and provide either tracking information, delivery updates, or a refund. Latest: 3 business days after the report date If the seller provided tracking details, eBay may close the case automatically once tracking shows the item has been delivered. Ask eBay to step in If the seller hasn't responded or if the buyer and seller can't reach a resolution, they can ask us to step in and help.","policyName":"eBay Money Back Guarantee policy"},{"articleNumber":null,"articleTitle":null,"chunkId":"c7d95b0b-db1d-4a78-b1cf-ef6801ce00dd","officialUrl":"https://www.ebay.com/help/policies/ebay/ebay?id=4210","originalTextEn":"When the item received by the buyer doesn't match the listing Actions and timeframes when the item received by the buyer doesn't match the listing Deciding the outcome when the item received by the buyer doesn't match the listing Exclusions and special coverage when the item received by the buyer doesn't match the listing Sellers are required to deliver the item as it was described in the listing. If the buyer receives the wrong item, or the item arrives broken, damaged, or faulty (and was not clearly described as such), they are entitled to return it for a refund, even if the seller doesn't offer returns. Buyers and sellers may agree to another solution, such as a full or partial refund while the buyer keeps the item, or a replacement item instead of a refund. Both buyers and sellers must meet all applicable return requirements. If an item is being returned, the seller is responsible for return shipping. Actions & time frames for \"not as described\" returns Action Time frame The buyer requests a return Start a return request Latest: 30 calendar days after the estimated or actual delivery date or within the seller's stated returns window, whichever is longer Trading Cards have a more limited return window. See Exclusions and special coverage. The seller responds to the buyer's request The seller is required to respond and provide a solution to the buyer's issue. Latest: 3 business days after the request date In some cases, eBay may automatically accept the return on the seller's behalf. If the item is being returned The buyer sends the item back Latest: The latest date to ship the return will be shown in an email sent by eBay to the buyer, as well as within the return request If the buyer does not ship the return by the specified date, eBay may close the return request. The seller issues a refund If return tracking shows the item was delivered, or if the item was shipped through eBay International Shipping - opens in new window or tab, eBay may automatically issue a full refund on the seller's behalf. Latest: 2 business days after receiving the returned item. In some cases, sellers have additional time to issue the refund. You can find the refund deadline in the return details.","policyName":"eBay Money Back Guarantee policy"},{"articleNumber":null,"articleTitle":null,"chunkId":"f1056fcc-c6be-4f90-8e44-3c1aca2e987d","officialUrl":"https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041","originalTextEn":"Sets and bundled items – Return the whole lot. Multiples of the same item – return some or all, but only one request for the order. Combined purchases from the same seller – open a separate request for each listing. How the seller may respond to your request The seller has 3 business days to accept and offer a full refund, offer a partial refund, offer a replacement or exchange, decline (if you changed your mind and their policy says no returns, or you missed the deadline), or ask you for more details. Send the item back Once your return is accepted, we'll email you asking you to send the item back. Ship it by the date in that email to avoid delays. If you're buying the label yourself, use a tracked service. Items worth $750 or more need signature confirmation. See return shipping for buyers for who pays. Important Repack the item carefully. If it's returned used, damaged, missing parts, or damaged in shipping because it wasn't packaged correctly, the seller may deduct from your refund — see Condition of returned items policy. Trouble with your return label? If your return label won't print, won't load, or the QR code won't scan, don't worry — you don't need to start over. Ask eBay to step in (see below) and we'll help you get a working label so you can complete your return. Get your refund Most sellers refund as soon as they get the item back. Refunds typically arrive within 3-5 business days of the seller receiving your return, back to your original or selected payment method. You can check the status of your request at any time to find your refund deadline. Why was my refund less than what I paid? If the item was returned used, damaged, missing parts, or damaged in shipping, the seller might deduct from your refund — see the Condition of returned items policy. If you paid for a label we provided, its cost is deducted too.","policyName":"Return an item for a refund"}],"reviews":[{"applicability":"UNCERTAIN","chunkId":"5f7fe4d0-426d-4a68-b4e3-71d8bfa89510","missingInformation":"이 청크는 상품 미수령의 일반적인 보장 가능성만 언급합니다. 개별 거래의 배송·신고 사실은 확인되지 않았습니다.","reason":"미수령 상품은 eBay Money Back Guarantee 대상이 될 수 있다는 안내로 질문과 관련은 있지만, 구체적인 미수령 신고 절차나 배송 완료로 표시된 경우의 판단 기준은 이 청크에 없습니다."},{"applicability":"UNCERTAIN","chunkId":"19d1e372-5b08-4a80-8cf3-7792894c6d3f","missingInformation":"주문 총액, eBay에 업로드된 통합 배송업체 추적 정보와 배송 스캔 시각, 배송 상태·날짜·주문 주소와 일치하는 우편번호 또는 도시/카운티, 해당 시 서명 확인, 수령 또는 픽업 사실이 필요합니다.","reason":"미수령 신고가 자격 요건을 충족하면 eBay는 주문 주소로 성공적으로 배송됐다는 증거 또는 구매자의 수령 증명을 검토합니다. 성공 배송 증거에는 통합 배송업체 추적 정보가 eBay에 업로드되고 예상 배송일 전 배송 스캔이 있어야 하며, 배송 완료 또는 배송 시도 상태, 날짜, 주문 상세와 일치하는 주소가 필요합니다. 총액 750달러 이상 주문에는 서명 확인도 요구됩니다. 유효한 배송 증거가 없으면 eBay가 신고 후 3~21영업일 사이에 개입할 수 있고, 미배송으로 판단하면 상품 가격과 최초 배송비를 환불할 수 있습니다. 거래별 증거는 제공되지 않았습니다."},{"applicability":"UNCERTAIN","chunkId":"bbba9a37-a92a-454b-a9fe-c56a79ec3136","missingInformation":"예상 또는 실제 배송일이 지났는지, 미수령 신고일, 판매자 응답 여부와 응답 내용, 추적 정보가 실제로 배송 완료를 증명하는지가 필요합니다.","reason":"정책상 구매자는 예상 또는 실제 배송·수령일이 지난 뒤 신고할 수 있으며, 그 날짜로부터 30일 이내가 신고 기한입니다. 판매자는 신고 후 3영업일 이내에 추적 정보, 배송 업데이트 또는 환불로 응답해야 합니다. 판매자가 응답하지 않거나 해결에 합의하지 못하면 eBay에 개입을 요청할 수 있습니다. 추적상 배송 완료로 표시되면 eBay가 사건을 자동으로 종료할 수도 있으나, 그 표시가 정책의 성공 배송 증거 요건을 충족하는지는 별도로 확인해야 합니다."},{"applicability":"NOT_APPLICABLE","chunkId":"c7d95b0b-db1d-4a78-b1cf-ef6801ce00dd","missingInformation":null,"reason":"이 청크는 상품이 설명과 다르거나 파손·고장 상태로 도착한 경우의 반품 정책입니다. 질문은 배송 완료 표시 후 상품을 받지 못한 경우이므로 해당 조항은 적용되지 않습니다."},{"applicability":"NOT_APPLICABLE","chunkId":"f1056fcc-c6be-4f90-8e44-3c1aca2e987d","missingInformation":null,"reason":"이 청크는 반품이 승인된 뒤 상품을 반환하고 환불받는 절차를 설명합니다. 미수령 신고와 배송 완료 표시의 판단 기준에는 해당하지 않습니다."}],"evidenceGaps":"거래별 환불 여부를 판단하려면 주문의 예상 또는 실제 배송일, 구매자가 미수령 신고를 했는지와 신고일, 주문 상세의 배송 주소, 추적 정보 및 배송 증빙(배송 시각·상태·주소 우편번호 또는 해당 지역, 해당되는 경우 서명 확인), 수령 또는 현지 픽업 여부를 확인해야 합니다. 거래별 사실은 제공되지 않았습니다.","similarityScores":[0.6028231978416443,0.5974207818508148,0.5824366211891174,0.5824358761310577,0.5770852863788605]}
```


## 정제 HTML 검증 · 미국 판매자 직접 배송 신발 오배송 · 2026-11-26

### 입력 질의

> 미국 eBay 판매자가 직접 배송한 신발을 한국에서 받았습니다. 주문은 US 9였지만 상자와 신발 모두 US 8입니다. 수령 12일째이고 판매자는 상품 페이지에 ‘반품 불가’라고 적었습니다. 교환을 요청할 수 있나요? 판매자가 교환을 거절하면 전액 환불과 최초·반품 배송비는 어떻게 처리되나요?

### 실행 조건

로컬 백엔드에서 개발 로그인 세션으로 `GET /rag`를 한 번 호출했다. 벡터 스토어에는 정제 HTML 22개에서 생성한 청크 146개가 적재된 상태였다.

| 파라미터 | 값 |
|---|---|
| `platform` | `EBAY` |
| `topK` | `5` |
| `similarityThreshold` | `0.0` |

### 관찰된 응답

HTTP 200 · `EVIDENCE_INSUFFICIENT` · 반환 조항 5개

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | `ddf2ad28-5f70-4b47-b7ca-a7f228124122` | 0.483672 | `APPLICABLE` |
| 2 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `6829845e-3c3d-4cb4-a0bc-a75c322e72fb` | 0.466508 | `APPLICABLE` |
| 3 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `4a6b92ff-3ebf-4f13-af8a-2746d993fba0` | 0.466189 | `APPLICABLE` |
| 4 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | `b7bdd4a6-9f45-44e5-88d9-ba3987ddb2ff` | 0.466053 | `UNCERTAIN` |
| 5 | [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115) | `bb57cc6c-0cf2-49ac-abc0-e436570beae5` | 0.459522 | `UNCERTAIN` |

### 실제 응답의 searchNote

> 주문 사이즈가 US 9인데 실제로 US 8을 받았다는 점이 증명되면, 상품이 listing과 다르므로 ‘반품 불가’ 표시와 무관하게 반품을 요청할 근거가 있습니다. 교환은 판매자가 제안할 수 있지만 보장되지는 않으며, 반품이 수락되어 전액 환불되는 경우 원래 배송비와 반품 배송비는 판매자가 부담한다고 정책은 설명합니다. 다만 12일째가 반품 기한 내인지와 한국발 국제 반품 배송의 구체적 처리 방식은 제공된 내용으로 확인되지 않습니다.

### 검색 결과 평가

3위 청크는 상품 설명과 다른 경우 판매자가 반품 불가로 표시했어도 반품할 수 있다는 근거를 반환했다. 2위 청크는 교환이나 대체품이 판매자가 제시할 수 있는 선택지임을 보여줬다. 최초 배송비를 포함한 전액 환불과 판매자의 반품 배송비 부담을 함께 명시한 청크는 1위였고, 게스트 구매 안내에서 검색됐다. 질의에는 게스트 결제 여부가 없었다.

상위 5개에 Money Back Guarantee의 상품 불일치 반품 접수 기한이나 국제 반송 수단에 관한 청크가 포함되지 않았다. 응답은 수령 12일째가 기한 내인지와 한국에서의 구체적인 반송 방식을 미확인 사항으로 남겼다.


## 정제 HTML 검증 · 나머지 해외직구 질의 4건 · 2026-11-26

로컬 백엔드에서 개발 로그인 세션으로 아래 질의를 각각 한 번씩 실행했다. 정제 HTML 22개와 청크 146개를 대상으로 첫 질의와 동일하게 `platform=EBAY`, `topK=5`, `similarityThreshold=0.0`을 적용했다.

### 질의 2 · 단순 변심과 사이즈 교환

> 미국 eBay 판매자가 한국으로 직접 배송한 재킷을 수령한 지 8일 됐습니다. 주문한 사이즈와 설명은 정확하지만 입어보니 작습니다. 상품 페이지에 ‘30일 반품 가능, 반품 배송비 구매자 부담’이라고 적혀 있습니다. 다른 사이즈로 교환해 달라고 할 수 있나요? 반품하면 최초 배송비도 돌려받나요?

HTTP 200 · EVIDENCE_INSUFFICIENT · 반환 조항 5개

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `4a6b92ff-3ebf-4f13-af8a-2746d993fba0` | 0.472321 | `NOT_APPLICABLE` |
| 2 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | `b7bdd4a6-9f45-44e5-88d9-ba3987ddb2ff` | 0.453620 | `APPLICABLE` |
| 3 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | `ddf2ad28-5f70-4b47-b7ca-a7f228124122` | 0.450435 | `APPLICABLE` |
| 4 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `6829845e-3c3d-4cb4-a0bc-a75c322e72fb` | 0.442368 | `UNCERTAIN` |
| 5 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `010641a4-7898-48ce-b397-703242dfb7e6` | 0.439553 | `UNCERTAIN` |

#### 실제 응답의 searchNote

> 상품 페이지의 30일 반품 조건에 따라 8일째인 지금 반품을 요청할 근거는 있으며, 국제 배송 프로그램 주문이라면 배송일부터 30일 이내 요청과 마음 변경 반송비 구매자 부담 규정이 있습니다. 교환은 판매자가 제안할 수 있을 뿐 보장되지 않고, 최초 배송비 환불 규정은 설명 불일치·결함·파손 반품에 명시되어 있어 단순히 사이즈가 맞지 않는 이 경우의 환불 여부는 확인되지 않습니다.

#### 검색 결과 평가

1위는 설명 불일치 조항으로 이 질의에 적용되지 않았다. 2~3위 게스트 구매 안내에서 판매자 반품 조건과 교환 제안을 찾았다. 5위 eIS 약관은 판매자 직접 배송 조건에 맞지 않는다. 단순 변심 반품의 최초 배송비를 직접 판단할 청크는 상위 5개에 없었다.

### 질의 3 · eIS 구성품 누락

> 주문 상세에 eBay International Shipping으로 표시된 미국발 상품을 한국에서 받았는데 구성품이 빠져 있습니다. 수령 18일째이고 판매자는 교체품을 직접 보내줄 수 없다고 합니다. 누구에게 반품이나 환불을 요청해야 하며, 교체품 발송도 요구할 수 있나요?

HTTP 200 · EVIDENCE_SUFFICIENT · 반환 조항 5개

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | `b7bdd4a6-9f45-44e5-88d9-ba3987ddb2ff` | 0.488246 | `UNCERTAIN` |
| 2 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `010641a4-7898-48ce-b397-703242dfb7e6` | 0.486075 | `APPLICABLE` |
| 3 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `da4a9608-9c37-43f0-b0ec-71dd61a08b91` | 0.485053 | `APPLICABLE` |
| 4 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `6829845e-3c3d-4cb4-a0bc-a75c322e72fb` | 0.472561 | `UNCERTAIN` |
| 5 | [eBay International Shipping Seller Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/seller/terms/) | `5bfdc014-677b-4c1c-af27-c8e289ecb105` | 0.470751 | `NOT_APPLICABLE` |

#### 실제 응답의 searchNote

> 검색된 EIS 약관에 따르면 미국발 EIS 상품은 실제 배송일부터 30일 안에 반품을 요청할 수 있고, 국제 반품은 판매자가 아니라 eBay가 처리·응답합니다. 18일째라면 기간 내 요청이 가능하지만 환불은 검사와 승인에 달려 있으며, 교체품은 판매자가 제안할 수 있을 뿐 구매자가 발송을 강제할 근거는 검색된 정책에 없습니다.

#### 검색 결과 평가

2~3위 eIS 구매자 약관에서 실제 배송 후 30일 접수, eBay의 반품 처리, 라벨 제공, 검수 후 환불 근거가 검색됐다. 4위 일반 반품 안내는 교체가 판매자의 선택지임을 뒷받침하지만 eIS 주문에서 교체 발송을 강제할 근거는 제공하지 않았다.

### 질의 4 · 제3자 배대지와 구매자 보호

> 미국 eBay에서 중고 카메라를 구매해 제가 선택한 미국 배대지로 보낸 뒤 한국에서 받았습니다. 배대지에는 배송 완료됐지만 한국에서 개봉하니 상품 설명에 없던 렌즈 파손이 확인됐습니다. 판매자에게 환불을 요청할 때 eBay Money Back Guarantee를 적용받을 수 있나요?

HTTP 200 · EVIDENCE_SUFFICIENT · 반환 조항 5개

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [eBay International Shipping Seller Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/seller/terms/) | `5bfdc014-677b-4c1c-af27-c8e289ecb105` | 0.500551 | `UNCERTAIN` |
| 2 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `9fe730cd-ed84-4ed4-adf2-543102c93aa5` | 0.499626 | `UNCERTAIN` |
| 3 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `156f28ea-ff16-498c-be2e-2fc4aef5c3de` | 0.491886 | `UNCERTAIN` |
| 4 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `06ef1fe4-d9d2-4abf-96e2-43ea2a63f3d1` | 0.485033 | `UNCERTAIN` |
| 5 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `3cbade8f-4033-4be5-9a7e-ea5047e4fa55` | 0.482306 | `UNCERTAIN` |

#### 실제 응답의 searchNote

> 설명에 없던 렌즈 파손은 리스팅과 다른 상품에 해당할 수 있어, 자격 요건을 충족하면 판매자에게 반품·환불 해결을 요청할 근거가 있습니다. 그러나 제3자 배대지 이용은 해당 사유의 보장에서 제외될 수 있고, eBay 프로그램 배송인지·결제 및 요청 기한 요건 충족 여부가 확인되지 않아 환불을 보장할 수는 없습니다.

#### 검색 결과 평가

5위 Money Back Guarantee 청크에서 제3자 배송대행의 상품 불일치 보호 제외와 eBay 공식 프로그램 예외를 모두 찾았다. 응답은 사용자가 선택한 배대지라는 진술을 충분히 반영하지 않고 eIS 가능성을 열어뒀다. 근거 검색의 성공과 해당 근거를 배송 경로에 적용하는 판단의 정확도를 구분할 필요가 있다.

### 질의 5 · 국제 반품 라벨과 배송 증빙

> 미국 eBay 판매자가 한국으로 직접 배송한 820달러짜리 기기가 작동하지 않아 반품을 접수했습니다. 판매자는 반품을 승인했지만, 접수 후 영업일 4일이 지나도 한국에서 쓸 수 있는 라벨을 주지 않았습니다. 제가 먼저 국제 반송비를 결제하면 돌려받을 수 있나요? eBay 개입은 언제 요청할 수 있고, 반송 시 어떤 배송 증빙이 필요한가요?

HTTP 200 · EVIDENCE_SUFFICIENT · 반환 조항 5개

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `010641a4-7898-48ce-b397-703242dfb7e6` | 0.548385 | `UNCERTAIN` |
| 2 | [Return shipping for buyers](https://www.ebay.com/help/buying/returns-refunds/returning-item/return-shipping?id=4066) | `994c0415-1ca9-4f46-b4c0-cd6de32cb0ba` | 0.544070 | `APPLICABLE` |
| 3 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `545eb687-aded-4011-ba2f-0c5f55c10bc6` | 0.543908 | `APPLICABLE` |
| 4 | [Return shipping for buyers](https://www.ebay.com/help/buying/returns-refunds/returning-item/return-shipping?id=4066) | `9b595379-57c6-41d3-a6c0-45ac0047da77` | 0.543133 | `APPLICABLE` |
| 5 | [eBay International Shipping Seller Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/seller/terms/) | `5bfdc014-677b-4c1c-af27-c8e289ecb105` | 0.540261 | `NOT_APPLICABLE` |

#### 실제 응답의 searchNote

> 판매자 부담 반품이라면 반송비를 판매자가 부담해야 하며, 승인 후 라벨을 주지 않은 경우 영업일 3일 이후 eBay 개입을 요청할 수 있고, 820달러 상품은 추적 및 서명 확인 배송을 사용해야 한다는 점은 근거가 있습니다. 다만 국제 반송비를 먼저 결제하면 상환된다는 보장은 확인되지 않았으므로, 반송 전에 판매자와 운송 방식·비용을 합의하거나 eBay에 개입을 요청하는 편이 안전합니다.

#### 검색 결과 평가

2~4위에서 판매자의 반송 수단 제공, 라벨 미제공 시 eBay 개입, 대체 배송 비용 사전 합의, 750달러 이상 추적·서명 확인 근거를 찾았다. 선결제한 국제 운임의 자동 상환은 확인되지 않았다. 1·5위 eIS 약관은 판매자 직접 배송 조건에 맞지 않았다.

## 정제 HTML 350/200 재실험 · 2026-11-27

11월 26일 정제 HTML 검증의 마지막 다섯 질의를 원문 그대로 한 번씩 재실행했다. 정제 HTML 22개를 `chunkSize=350`, `minChunkSizeChars=200`으로 재적재한 186개 청크를 사용했다. `platform=EBAY`, `topK=5`, `similarityThreshold=0.0`을 유지했다.

개발 로그인 오류의 영향을 피하기 위해 `GET /rag`가 호출하는 `PolicyRagService.reviewPolicy`를 Spring 컨텍스트에서 직접 실행했다. 다섯 서비스 호출은 모두 정상 완료됐다. 이전 정제 HTML 146개 청크 실험의 적재 파라미터는 별도 기록이 없어, 이전 실행과의 차이를 청크 크기만의 효과로 판단하기는 어렵다.

다섯 질의 모두 `EVIDENCE_INSUFFICIENT`였다. 이전 실험의 1·2번은 같은 상태였고 3~5번은 `EVIDENCE_SUFFICIENT`에서 바뀌었다. 오배송의 반품 접수 기한은 여전히 상위 5개에 없었고, 배대지의 상품 불일치 보호 제외·예외와 국제 반송의 서명 확인 요건은 상위 5개에서 빠졌다.

### 질의 1 · 판매자 직접 배송 신발 오배송

> 미국 eBay 판매자가 직접 배송한 신발을 한국에서 받았습니다. 주문은 US 9였지만 상자와 신발 모두 US 8입니다. 수령 12일째이고 판매자는 상품 페이지에 ‘반품 불가’라고 적었습니다. 교환을 요청할 수 있나요? 판매자가 교환을 거절하면 전액 환불과 최초·반품 배송비는 어떻게 처리되나요?

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 12199ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | `7b80f97b-5df0-4bed-8dec-66e63608035f` | 0.499796 | APPLICABLE |
| 2 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `48be591a-4fc1-4186-8073-faf38225b015` | 0.474651 | UNCERTAIN |
| 3 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `cb7c4afa-c5e8-4fdf-b76a-db78df6d9874` | 0.473454 | 검토 누락 |
| 4 | [eBay Authenticity Guarantee for Sneakers](https://www.ebay.com/authenticity-guarantee/sneakers) | `61310e54-dc7a-4575-ac79-f4ce175cf859` | 0.461258 | NOT_APPLICABLE |
| 5 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | `59978e03-7755-435d-a263-13514c4f04e9` | 0.457800 | UNCERTAIN |

#### 실제 응답의 searchNote

> 주문한 US 9 대신 US 8을 받았다는 사실을 입증하면 상품이 listing과 다르다는 사유로 반품을 요청할 근거가 있고, 정책상 반품이 받아들여지면 원래 배송비 포함 전액 환불과 판매자 부담 반품 배송이 안내됩니다. 교환은 판매자가 제공할 수 있는 선택지일 뿐 보장되지 않으며, 사이즈 불일치 증빙과 EIS 이용 여부가 확인되지 않아 교환 의무나 국제배송·수입 비용 환급을 단정할 수 없습니다.

#### 이전 실행과 비교

이전과 같이 반품 접수 기한을 뒷받침하는 Money Back Guarantee 청크는 상위 5개에 없었다. 전액 환불과 배송비 부담 근거는 이전과 같은 게스트 구매 안내가 1위였으며, 게스트 결제 여부는 질의에 없었다. 2위 eIS 약관과 4위 스니커즈 인증 안내도 거래 조건에서 적용이 확인되지 않았다.

### 질의 2 · 단순 변심과 사이즈 교환

> 미국 eBay 판매자가 한국으로 직접 배송한 재킷을 수령한 지 8일 됐습니다. 주문한 사이즈와 설명은 정확하지만 입어보니 작습니다. 상품 페이지에 ‘30일 반품 가능, 반품 배송비 구매자 부담’이라고 적혀 있습니다. 다른 사이즈로 교환해 달라고 할 수 있나요? 반품하면 최초 배송비도 돌려받나요?

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 10379ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [eBay Guaranteed Fit](https://pages.ebay.com/motors/ebay-guaranteed-fit/) | `6f6e54f4-1332-4e86-9d6d-77b345eafc16` | 0.466290 | NOT_APPLICABLE |
| 2 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | `7b80f97b-5df0-4bed-8dec-66e63608035f` | 0.461308 | APPLICABLE |
| 3 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `cb7c4afa-c5e8-4fdf-b76a-db78df6d9874` | 0.452265 | 검토 누락 |
| 4 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `e9d02b20-0f66-4674-8457-e9c6c2e51d9a` | 0.446401 | APPLICABLE |
| 5 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `e865caaa-2e7f-4b07-a6af-2d4574c4511d` | 0.445548 | UNCERTAIN |

#### 실제 응답의 searchNote

> 30일 반품 가능이라는 판매자 안내와 EIS가 적용되는 경우 배송일 기준 30일 내 마음 변경성 반품을 요청하고 라벨 비용을 구매자가 부담한다는 정책은 반품 요구의 근거가 될 수 있습니다. 다만 사이즈 교환은 판매자가 제안할 수 있을 뿐 보장되지 않으며, 단순히 작다는 이유로 최초 배송비를 돌려받는다는 근거는 검색된 청크에 없습니다.

#### 이전 실행과 비교

최초 배송비 환급 근거는 이전과 같이 상위 5개에서 확보되지 않았다. 1위는 이전의 상품 불일치 반품 안내에서 자동차 부품용 Guaranteed Fit 청크로 바뀌었고, 모델도 적용 불가로 판단했다. 5위 eIS 약관은 이전에도 검색됐으며 판매자 직접 배송 사례에 적용할 근거가 없다.

### 질의 3 · eIS 구성품 누락

> 주문 상세에 eBay International Shipping으로 표시된 미국발 상품을 한국에서 받았는데 구성품이 빠져 있습니다. 수령 18일째이고 판매자는 교체품을 직접 보내줄 수 없다고 합니다. 누구에게 반품이나 환불을 요청해야 하며, 교체품 발송도 요구할 수 있나요?

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 7973ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `48be591a-4fc1-4186-8073-faf38225b015` | 0.491335 | UNCERTAIN |
| 2 | [eBay International Shipping Seller Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/seller/terms/) | `01edd991-d052-4882-98e8-4476753c363e` | 0.489814 | NOT_APPLICABLE |
| 3 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `e865caaa-2e7f-4b07-a6af-2d4574c4511d` | 0.489626 | APPLICABLE |
| 4 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | `7b80f97b-5df0-4bed-8dec-66e63608035f` | 0.474093 | UNCERTAIN |
| 5 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | `f0310982-281e-4a06-8a61-4c19fb623096` | 0.473982 | UNCERTAIN |

#### 실제 응답의 searchNote

> 반품·환불 요청은 판매자에게 직접 처리받기보다 eBay의 EIS 국제 반품 절차로 접수할 근거가 있고, 실제 배송일부터 30일이므로 수령 18일째가 맞다면 아직 요청할 수 있습니다. 누락이 세트·번들의 구성품이면 반품 요청이 안내되며, 교체·교환은 판매자가 제안할 수 있을 뿐 구매자가 발송을 강제할 권리나 환불 승인이 보장되지는 않습니다.

#### 이전 실행과 비교

eIS 구매자 약관이 이전 2·3위에서 1·3위로 바뀌었다. 3위에서 실제 배송일 기준 30일 접수, eBay 처리 및 라벨 제공을 확인했고 1위는 검수·승인 후 환불을 다룬다. 응답은 핵심 절차를 유지했지만 상태는 SUFFICIENT에서 INSUFFICIENT로 바뀌었다.

### 질의 4 · 제3자 배대지와 구매자 보호

> 미국 eBay에서 중고 카메라를 구매해 제가 선택한 미국 배대지로 보낸 뒤 한국에서 받았습니다. 배대지에는 배송 완료됐지만 한국에서 개봉하니 상품 설명에 없던 렌즈 파손이 확인됐습니다. 판매자에게 환불을 요청할 때 eBay Money Back Guarantee를 적용받을 수 있나요?

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 8637ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [eBay International Shipping Seller Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/seller/terms/) | `01edd991-d052-4882-98e8-4476753c363e` | 0.512509 | UNCERTAIN |
| 2 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `7543569d-a0ee-4bd9-924d-4b248ee834bc` | 0.506709 | UNCERTAIN |
| 3 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `72bbcc36-a850-4769-b1a5-8b5b97a1929b` | 0.497802 | UNCERTAIN |
| 4 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `d66fe5a9-8e98-460b-a8d0-adb374a52dde` | 0.495506 | UNCERTAIN |
| 5 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `fc4728f7-6894-4a25-96d6-ee4eb07ec981` | 0.489746 | UNCERTAIN |

#### 실제 응답의 searchNote

> 상품 설명에 없던 렌즈 파손은 ‘상품이 설명과 다름’에 해당할 수 있으므로, 자격 요건을 충족하면 eBay 절차로 문제를 제기할 근거가 있습니다. 다만 배대지 이용만으로 보호 여부가 결정되지는 않으며 결제·신고 기한·배송 경로와 손상 발생 시점이 확인되지 않아 환불 결과는 보장할 수 없고, 판매자에게 요청하는 것과 eBay가 환불을 결정하는 것도 구분해야 합니다.

#### 이전 실행과 비교

이전 5위에서 상품 불일치 청구에 대한 제3자 배송대행 제외와 eBay 공식 프로그램 예외를 함께 찾았다. 이번 4위는 미수령 청구에 대한 배송대행 제외를 다뤘고, 상품 불일치에 해당하는 제외·예외 조항은 상위 5개에 없었다. 상태는 SUFFICIENT에서 INSUFFICIENT로 바뀌었다.

### 질의 5 · 국제 반품 라벨과 배송 증빙

> 미국 eBay 판매자가 한국으로 직접 배송한 820달러짜리 기기가 작동하지 않아 반품을 접수했습니다. 판매자는 반품을 승인했지만, 접수 후 영업일 4일이 지나도 한국에서 쓸 수 있는 라벨을 주지 않았습니다. 제가 먼저 국제 반송비를 결제하면 돌려받을 수 있나요? eBay 개입은 언제 요청할 수 있고, 반송 시 어떤 배송 증빙이 필요한가요?

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 8301ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `cb7c4afa-c5e8-4fdf-b76a-db78df6d9874` | 0.569165 | APPLICABLE |
| 2 | [eBay International Shipping Seller Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/seller/terms/) | `01edd991-d052-4882-98e8-4476753c363e` | 0.560118 | UNCERTAIN |
| 3 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `e865caaa-2e7f-4b07-a6af-2d4574c4511d` | 0.557398 | UNCERTAIN |
| 4 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `48be591a-4fc1-4186-8073-faf38225b015` | 0.547154 | UNCERTAIN |
| 5 | [Return shipping for buyers](https://www.ebay.com/help/buying/returns-refunds/returning-item/return-shipping?id=4066) | `832f6fcf-7711-4512-91f1-9574772a6850` | 0.547034 | UNCERTAIN |

#### 실제 응답의 searchNote

> 판매자가 반품을 승인하고도 라벨을 보내지 않았다면 eBay에 개입을 요청할 수 있다는 근거가 있으며, 판매자가 비용 부담인 경우 자기 운송사를 이용해 라벨을 업로드하고 추적 가능한 서비스를 쓰는 방안도 안내되어 있습니다. 다만 국제 반송비를 먼저 내면 돌려받는다는 보장은 확인되지 않았고, EIS 여부·배송비 부담 주체와 국제 반송 증빙의 구체 요건은 추가 확인이 필요합니다.

#### 이전 실행과 비교

라벨 미제공 시 eBay 개입 근거가 1위로 올라왔다. 반면 eIS 약관이 상위 5개 중 3개를 차지했고, 이전에 찾은 750달러 이상 반송의 서명 확인 요건과 대체 배송 비용 사전 합의 근거가 상위 5개에서 빠졌다. 상태는 SUFFICIENT에서 INSUFFICIENT로 바뀌었다.

## 정제 HTML 450/200 재실험 · 2026-11-27

같은 정제 HTML 22개를 `chunkSize=450`, `minChunkSizeChars=200`으로 재적재해 146개 청크를 생성했다. 재적재 전 350/200 청크 186개를 백업했다. 이전 정제 HTML 실험의 146개 청크 백업과 새 인덱스를 출처 URL과 본문으로 대조한 결과, 146개가 모두 일치했다.

350/200 실험과 동일한 질의 원문, `platform=EBAY`, `topK=5`, `similarityThreshold=0.0`을 사용했다. 두 실험 모두 같은 Spring 서비스 경로를 직접 호출했다. 질의당 한 번 실행했다.

| 질의 | 350/200 | 450/200 | 검색 근거 비교 |
|---|---|---|---|
| 신발 오배송 | INSUFFICIENT | INSUFFICIENT | 반품 접수 기한 미회수. 게스트 안내가 여전히 1위이며 eIS·스니커즈 인증 안내는 상위 5개에서 빠짐. |
| 단순 변심 | INSUFFICIENT | INSUFFICIENT | 최초 배송비 정책 미회수. 자동차 부품 정책과 eIS 약관은 빠졌으나 1위 일반 반품 청크도 모델이 적용 불가로 평가함. |
| eIS 구성품 누락 | INSUFFICIENT | SUFFICIENT | 양쪽 모두 30일 기한과 eBay 처리 근거를 회수. 450/200에서 이 근거가 3위에서 2위로 이동함. |
| 제3자 배대지 | INSUFFICIENT | SUFFICIENT | 상품 불일치 보호에서 제3자 배송대행 제외와 공식 프로그램 예외를 5위에서 다시 회수함. |
| 국제 반품 라벨 | INSUFFICIENT | SUFFICIENT | 라벨 미제공 시 개입 근거가 1위에 유지됐고, 추적·서명 요건과 비용 사전 합의 근거를 다시 회수함. |

### 질의 1 · 판매자 직접 배송 신발 오배송

> 미국 eBay 판매자가 직접 배송한 신발을 한국에서 받았습니다. 주문은 US 9였지만 상자와 신발 모두 US 8입니다. 수령 12일째이고 판매자는 상품 페이지에 ‘반품 불가’라고 적었습니다. 교환을 요청할 수 있나요? 판매자가 교환을 거절하면 전액 환불과 최초·반품 배송비는 어떻게 처리되나요?

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 10921ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | `981a3c6a-7df9-48eb-999d-d84f9ef193fc` | 0.485444 | APPLICABLE |
| 2 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `c9325879-31ab-415c-8298-75e4359000ee` | 0.473828 | APPLICABLE |
| 3 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `a84a0fa4-bd6c-49f7-9778-105a99fa75d4` | 0.470697 | APPLICABLE |
| 4 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | `d2193020-5fba-42e8-beb7-cc24803c756f` | 0.466906 | UNCERTAIN |
| 5 | [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115) | `c169ac92-d2ef-44a7-b4c6-597ce8cde0ba` | 0.464719 | UNCERTAIN |

#### 실제 응답의 searchNote

> 주문은 US 9인데 수령품이 US 8이라는 사실이 증빙되면 ‘리스팅과 다름’을 근거로 반품 불가 표기에도 반환을 요청할 수 있습니다. 교환은 판매자가 제안할 수 있을 뿐 보장되지 않으며, 반환이 전액 환불로 처리되면 원래 배송비 포함 및 판매자 부담 반품 배송비가 안내되어 있지만, 교환 거절만으로 전액 환불이 자동 확정되는지와 실제 국제 반품 비용 절차는 검색된 내용만으로 단정할 수 없습니다.

### 질의 2 · 단순 변심과 사이즈 교환

> 미국 eBay 판매자가 한국으로 직접 배송한 재킷을 수령한 지 8일 됐습니다. 주문한 사이즈와 설명은 정확하지만 입어보니 작습니다. 상품 페이지에 ‘30일 반품 가능, 반품 배송비 구매자 부담’이라고 적혀 있습니다. 다른 사이즈로 교환해 달라고 할 수 있나요? 반품하면 최초 배송비도 돌려받나요?

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 8967ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `c9325879-31ab-415c-8298-75e4359000ee` | 0.475635 | NOT_APPLICABLE |
| 2 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | `d2193020-5fba-42e8-beb7-cc24803c756f` | 0.454265 | APPLICABLE |
| 3 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | `981a3c6a-7df9-48eb-999d-d84f9ef193fc` | 0.452398 | APPLICABLE |
| 4 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `a84a0fa4-bd6c-49f7-9778-105a99fa75d4` | 0.445989 | UNCERTAIN |
| 5 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `4251873c-b636-4e78-9232-22ff508d6f05` | 0.438915 | NOT_APPLICABLE |

#### 실제 응답의 searchNote

> 상품 페이지에 30일 반품 가능이라고 적혀 있으므로 해당 조건에 따라 반품을 요청할 근거는 있습니다. 다만 교환은 판매자가 제안할 수 있을 뿐 보장되지 않고, 사이즈가 맞지 않는 단순 변심 반품에서 최초 배송비를 환급하는지는 검색된 정책이 확인해 주지 않습니다.

### 질의 3 · eIS 구성품 누락

> 주문 상세에 eBay International Shipping으로 표시된 미국발 상품을 한국에서 받았는데 구성품이 빠져 있습니다. 수령 18일째이고 판매자는 교체품을 직접 보내줄 수 없다고 합니다. 누구에게 반품이나 환불을 요청해야 하며, 교체품 발송도 요구할 수 있나요?

상태 EVIDENCE_SUFFICIENT · 반환 청크 5개 · 서비스 호출 7880ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | `d2193020-5fba-42e8-beb7-cc24803c756f` | 0.487956 | UNCERTAIN |
| 2 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `3d3ae2c3-5854-4f55-be13-63a0557a428f` | 0.482835 | APPLICABLE |
| 3 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `835f76b7-3ab8-408e-b906-346ba236d13a` | 0.482810 | UNCERTAIN |
| 4 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `a84a0fa4-bd6c-49f7-9778-105a99fa75d4` | 0.475530 | UNCERTAIN |
| 5 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `c9325879-31ab-415c-8298-75e4359000ee` | 0.472191 | UNCERTAIN |

#### 실제 응답의 searchNote

> EIS 국제 반품은 실제 배송일부터 30일 안에 신청할 수 있고 eBay가 처리하므로, 수령 18일째라면 eBay에 반품 요청을 하는 근거가 있습니다. 구성품 누락이 목록 설명과 다르거나 세트·번들의 누락이면 반품 요청을 뒷받침하지만, 교체 발송은 판매자가 선택할 수 있는 응답일 뿐 보장되지 않으며 누락 구성품의 성격과 목록 내용은 확인되지 않았습니다.

### 질의 4 · 제3자 배대지와 구매자 보호

> 미국 eBay에서 중고 카메라를 구매해 제가 선택한 미국 배대지로 보낸 뒤 한국에서 받았습니다. 배대지에는 배송 완료됐지만 한국에서 개봉하니 상품 설명에 없던 렌즈 파손이 확인됐습니다. 판매자에게 환불을 요청할 때 eBay Money Back Guarantee를 적용받을 수 있나요?

상태 EVIDENCE_SUFFICIENT · 반환 청크 5개 · 서비스 호출 8304ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [eBay International Shipping Seller Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/seller/terms/) | `7df0fec2-72ba-4cef-8857-29e4e821ac71` | 0.501574 | UNCERTAIN |
| 2 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `1bb4713a-5b1f-4ac4-98bf-384dce710cd5` | 0.496686 | UNCERTAIN |
| 3 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `98d98236-684a-4462-bb62-6ca0b33f8550` | 0.488324 | UNCERTAIN |
| 4 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `9bee7ef4-9956-4370-9ba6-a5db304fd7df` | 0.480319 | UNCERTAIN |
| 5 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `289cb7f7-8c36-4849-8fdc-b8778b082053` | 0.478101 | UNCERTAIN |

#### 실제 응답의 searchNote

> 렌즈 파손이 설명에 없었다면 상품 설명 불일치로 판매자에게 반품·환불을 요청할 근거는 있습니다. 다만 제3자 배대지 사용은 해당 사유의 보장 제외가 될 수 있고, eBay 프로그램 배송 여부와 결제·요청 기한 등 핵심 자격 요건이 확인되지 않아 환불 보장을 단정할 수 없습니다.

### 질의 5 · 국제 반품 라벨과 배송 증빙

> 미국 eBay 판매자가 한국으로 직접 배송한 820달러짜리 기기가 작동하지 않아 반품을 접수했습니다. 판매자는 반품을 승인했지만, 접수 후 영업일 4일이 지나도 한국에서 쓸 수 있는 라벨을 주지 않았습니다. 제가 먼저 국제 반송비를 결제하면 돌려받을 수 있나요? eBay 개입은 언제 요청할 수 있고, 반송 시 어떤 배송 증빙이 필요한가요?

상태 EVIDENCE_SUFFICIENT · 반환 청크 5개 · 서비스 호출 9423ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `4251873c-b636-4e78-9232-22ff508d6f05` | 0.546420 | APPLICABLE |
| 2 | [Return shipping for buyers](https://www.ebay.com/help/buying/returns-refunds/returning-item/return-shipping?id=4066) | `a0ea401b-15d4-4b4f-abb1-6b1d929108f3` | 0.544969 | UNCERTAIN |
| 3 | [Return shipping for buyers](https://www.ebay.com/help/buying/returns-refunds/returning-item/return-shipping?id=4066) | `b66430d7-aa0e-414c-82e7-0096cae6b1b6` | 0.544741 | UNCERTAIN |
| 4 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `835f76b7-3ab8-408e-b906-346ba236d13a` | 0.543333 | NOT_APPLICABLE |
| 5 | [Return shipping for buyers](https://www.ebay.com/help/buying/returns-refunds/returning-item/return-shipping?id=4066) | `7fcb7b2c-62ee-4261-8341-3b40e678266b` | 0.540695 | APPLICABLE |

#### 실제 응답의 searchNote

> 판매자에게 작동 불량 반품의 반송비를 부담하고 라벨을 제공해 달라고 요구할 근거가 있으며, 라벨을 주지 않은 상황은 eBay 개입 요청 사유로 명시되어 있습니다. 다만 국제 반송비를 먼저 낸 뒤 상환받는 것은 보장되지 않으므로 발송 전 배송 방법과 비용을 합의해야 하고, $820 반품에는 추적 및 서명 확인 배송이 필요합니다.

## 정제 HTML 400/200 재실험 · 2026-11-28

같은 정제 HTML 22개를 `chunkSize=400`, `minChunkSizeChars=200`으로 재적재해 164개 청크를 생성했다. 이전 450/200 인덱스의 146개 청크를 백업했다. 이전과 동일한 질의 원문, `platform=EBAY`, `topK=5`, `similarityThreshold=0.0`을 사용했다. 각 질의를 같은 Spring 서비스에서 한 번씩 직접 실행했다.

다섯 질의 모두 `EVIDENCE_INSUFFICIENT`였다. 이전 450/200의 1·2번은 같은 상태였고, 3~5번은 `EVIDENCE_SUFFICIENT`에서 바뀌었다.

| 질의 | 450/200 대비 검색 근거 |
|---|---|
| 신발 오배송 | 판매자 직접 배송에 적용할 접수 기한을 여전히 찾지 못했다. 최초 배송비 포함 전액 환불 및 판매자 반품 배송비 부담을 함께 설명한 게스트 안내가 상위 5개에서 빠졌고, 대신 EIS 구매자 약관이 2개 들어왔다. |
| 단순 변심 | 최초 배송비 환급 근거를 여전히 찾지 못했다. 판매자 직접 배송 질의의 상위 5개에 EIS 구매자 약관이 2개 들어왔다. |
| eIS 구성품 누락 | 실제 배송일 기준 30일과 eBay 처리 근거가 2위에서 1위로 올랐다. 판매자의 교체·교환 제안을 설명한 일반 반품 안내가 상위 5개에서 빠졌다. |
| 제3자 배대지 | 상품 불일치 보호에 대한 제3자 배송대행 제외와 eBay 공식 프로그램 예외를 담은 청크가 빠졌다. |
| 국제 반품 라벨 | 대체 반송 비용 사전 합의 근거는 5위에서 회수했다. 라벨 미제공 시 개입 근거와 750달러 이상 반송의 서명 확인 요건은 빠졌다. 1위는 작동하지 않는 라벨에 관한 안내였다. |

### 질의 1 · 판매자 직접 배송 신발 오배송

> 미국 eBay 판매자가 직접 배송한 신발을 한국에서 받았습니다. 주문은 US 9였지만 상자와 신발 모두 US 8입니다. 수령 12일째이고 판매자는 상품 페이지에 ‘반품 불가’라고 적었습니다. 교환을 요청할 수 있나요? 판매자가 교환을 거절하면 전액 환불과 최초·반품 배송비는 어떻게 처리되나요?

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 14492ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `cef400e9-540c-4995-b9b1-5219e6964f09` | 0.476811 | UNCERTAIN |
| 2 | [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115) | `958073cc-2eb9-4e71-a460-b121fa82b9d7` | 0.462375 | APPLICABLE |
| 3 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `349906e7-644c-40a3-ab63-5b6c9cdbaa7e` | 0.461679 | UNCERTAIN |
| 4 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `3cf95b7b-2823-42b9-bc69-c5f6a9057d95` | 0.459066 | UNCERTAIN |
| 5 | [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115) | `15c79ca7-a600-40ff-b436-dfa45f21eb18` | 0.456954 | 검토 누락 |

#### 실제 응답의 searchNote

> US EIS 거래라면 배송일로부터 30일 안에 반품을 요청할 수 있어 수령 12일째라는 점과 ‘반품 불가’ 표기는 그 반품 절차 자체를 막는다고 검색된 조항에 나타나지 않습니다. 다만 교환은 판매자가 제안할 수 있는 선택지일 뿐 보장된 권리가 아니며, 오배송 시 전액 환불과 최초·반품 배송비 부담은 거래가 EIS인지 및 배송·수입 비용 조건에 따라 달라질 수 있어 검색 청크만으로 확정할 수 없습니다.

### 질의 2 · 단순 변심과 사이즈 교환

> 미국 eBay 판매자가 한국으로 직접 배송한 재킷을 수령한 지 8일 됐습니다. 주문한 사이즈와 설명은 정확하지만 입어보니 작습니다. 상품 페이지에 ‘30일 반품 가능, 반품 배송비 구매자 부담’이라고 적혀 있습니다. 다른 사이즈로 교환해 달라고 할 수 있나요? 반품하면 최초 배송비도 돌려받나요?

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 11620ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `cb102cd1-b3dc-4586-9b25-16e944d76b7d` | 0.461908 | NOT_APPLICABLE |
| 2 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `cef400e9-540c-4995-b9b1-5219e6964f09` | 0.452923 | UNCERTAIN |
| 3 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `349906e7-644c-40a3-ab63-5b6c9cdbaa7e` | 0.446559 | UNCERTAIN |
| 4 | [International purchases and shipping for buyers](https://www.ebay.com/help/buying/shipping-delivery/international-shipping-ebay?id=4057) | `e6d9e3fa-ca48-415e-8fc8-9f8157805b24` | 0.446054 | NOT_APPLICABLE |
| 5 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `3cf95b7b-2823-42b9-bc69-c5f6a9057d95` | 0.440974 | UNCERTAIN |

#### 실제 응답의 searchNote

> 판매자 페이지의 ‘30일 반품 가능, 반품 배송비 구매자 부담’ 조건에 따라 변심 반품을 요청할 근거는 있지만, 이 정책 자료만으로 다른 사이즈 교환을 요구할 권리가 보장된다고 볼 수는 없습니다. 최초 배송비 환불 여부도 확인되지 않았으며, EIS라면 안내된 변심 반품 라벨 비용은 구매자 부담이고 일부 국제 비용은 환불되지 않을 수 있습니다.

### 질의 3 · eIS 구성품 누락

> 주문 상세에 eBay International Shipping으로 표시된 미국발 상품을 한국에서 받았는데 구성품이 빠져 있습니다. 수령 18일째이고 판매자는 교체품을 직접 보내줄 수 없다고 합니다. 누구에게 반품이나 환불을 요청해야 하며, 교체품 발송도 요구할 수 있나요?

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 7271ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `3cf95b7b-2823-42b9-bc69-c5f6a9057d95` | 0.488561 | UNCERTAIN |
| 2 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `cef400e9-540c-4995-b9b1-5219e6964f09` | 0.487450 | UNCERTAIN |
| 3 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `d7e31859-c94c-4c8f-a294-914c06c919f9` | 0.479702 | APPLICABLE |
| 4 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `120db792-5f96-4b7d-8b9b-63640974bbac` | 0.479210 | NOT_APPLICABLE |
| 5 | [International purchases and shipping for buyers](https://www.ebay.com/help/buying/shipping-delivery/international-shipping-ebay?id=4057) | `e6d9e3fa-ca48-415e-8fc8-9f8157805b24` | 0.478876 | NOT_APPLICABLE |

#### 실제 응답의 searchNote

> 반품은 실제 배송일부터 30일 안에 요청할 수 있고, EIS 국제 반품은 eBay가 처리하므로 판매자에게 직접 반품·환불을 요구하기보다 eBay 반품 절차를 이용하는 것이 근거에 맞습니다. 구성품이 설명과 다르면 Money Back Guarantee 대상이 될 수 있지만 환불은 심사·승인 대상이며, 검색된 정책은 교체품 발송을 보장하거나 판매자에게 의무화하지 않습니다.

### 질의 4 · 제3자 배대지와 구매자 보호

> 미국 eBay에서 중고 카메라를 구매해 제가 선택한 미국 배대지로 보낸 뒤 한국에서 받았습니다. 배대지에는 배송 완료됐지만 한국에서 개봉하니 상품 설명에 없던 렌즈 파손이 확인됐습니다. 판매자에게 환불을 요청할 때 eBay Money Back Guarantee를 적용받을 수 있나요?

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 7926ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `38d27e15-4490-47cc-a476-f1c5caebcd2c` | 0.496414 | UNCERTAIN |
| 2 | [eBay Guaranteed Fit](https://pages.ebay.com/motors/ebay-guaranteed-fit/) | `339cd14a-2db7-4558-bd4e-0bc98148a318` | 0.489808 | NOT_APPLICABLE |
| 3 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `120db792-5f96-4b7d-8b9b-63640974bbac` | 0.486840 | UNCERTAIN |
| 4 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `6a188e93-aa69-450c-8998-c5f3ada2ed1f` | 0.485908 | UNCERTAIN |
| 5 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `9190639d-2eb6-4847-bfbc-177ba801a6bc` | 0.483613 | NOT_APPLICABLE |

#### 실제 응답의 searchNote

> 판매자에게 렌즈 파손이 목록에 없었고 상품이 설명과 다르다는 이유로 eBay 환불 절차에 따라 문제를 신고·해결해 달라고 요구할 정책 근거는 있습니다. 다만 적격 eBay 결제와 신고 기한 등 요건, 파손이 배대지 수령 전에 있었는지 및 적용되는 배대지 관련 예외는 확인되지 않아 환불을 단정할 수 없습니다.

### 질의 5 · 국제 반품 라벨과 배송 증빙

> 미국 eBay 판매자가 한국으로 직접 배송한 820달러짜리 기기가 작동하지 않아 반품을 접수했습니다. 판매자는 반품을 승인했지만, 접수 후 영업일 4일이 지나도 한국에서 쓸 수 있는 라벨을 주지 않았습니다. 제가 먼저 국제 반송비를 결제하면 돌려받을 수 있나요? eBay 개입은 언제 요청할 수 있고, 반송 시 어떤 배송 증빙이 필요한가요?

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 8377ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `cb102cd1-b3dc-4586-9b25-16e944d76b7d` | 0.591464 | APPLICABLE |
| 2 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `cef400e9-540c-4995-b9b1-5219e6964f09` | 0.567179 | UNCERTAIN |
| 3 | [International purchases and shipping for buyers](https://www.ebay.com/help/buying/shipping-delivery/international-shipping-ebay?id=4057) | `e6d9e3fa-ca48-415e-8fc8-9f8157805b24` | 0.551171 | APPLICABLE |
| 4 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `3cf95b7b-2823-42b9-bc69-c5f6a9057d95` | 0.548914 | UNCERTAIN |
| 5 | [Return shipping for buyers](https://www.ebay.com/help/buying/returns-refunds/returning-item/return-shipping?id=4066) | `872f13ba-83a3-4cfc-a9e1-cfdf6cbf7bc0` | 0.548628 | APPLICABLE |

#### 실제 응답의 searchNote

> 작동하지 않는 기기는 고장 반품 대상일 수 있고, 라벨이 작동하지 않으면 eBay에 개입을 요청해 쓸 수 있는 라벨을 도움받을 수 있습니다. 그러나 판매자가 승인 후 4영업일 내 라벨을 주지 않았을 때의 구체적 개입 가능 시점이나 국제 반송비를 먼저 결제하면 돌려받는다는 보장은 검색된 정책에 없으며, EIS 여부와 비용 부담·대체 배송 합의도 확인해야 합니다.

## 정제 HTML 500/200 재실험 · 2026-11-28

같은 정제 HTML 22개를 `chunkSize=500`, `minChunkSizeChars=200`으로 재적재해 133개 청크를 생성했다. 이전 400/200 인덱스의 164개 청크는 백업했다. 기존 다섯 질의의 원문과 `platform=EBAY`, `topK=5`, `similarityThreshold=0.0`을 사용해 같은 Spring 서비스에서 각 질의를 한 번씩 직접 실행했다.

모델의 `EVIDENCE_SUFFICIENT` 판정은 500/200에서 4건, 450/200에서 3건이었다. 신발 오배송과 제3자 배대지에서는 충분 판정에도 질문에 필요한 일부 조항이 상위 5개에 없었다.

| 질의 | 450/200 | 500/200 | 검색 근거 비교 |
|---|---|---|---|
| 신발 오배송 | INSUFFICIENT | SUFFICIENT | 판매자 반품 안내 3위에서 반품 불가 표기에도 오배송품을 반품할 수 있고 전액 환불에 최초 배송비가 포함되며 반송비는 판매자가 부담한다는 근거를 찾았다. 반품 접수 기한은 두 설정 모두 상위 5개에 없었다. |
| 단순 변심 | INSUFFICIENT | INSUFFICIENT | Money Back Guarantee가 4·5위에 들어왔다. 5위에서 판매자 반품 정책과 구매자 부담 반송비를 뒷받침했으나 최초 배송비 환급 근거는 여전히 없었다. |
| eIS 구성품 누락 | SUFFICIENT | SUFFICIENT | 실제 배송일부터 30일 이내 접수 및 eBay 처리 근거가 2위에서 1위로 이동했다. 3위에서 교체품은 합의 가능한 대안임을 확인했다. |
| 제3자 배대지 | SUFFICIENT | SUFFICIENT | 상품 불일치에 대한 일반 반품 접수 기한은 4위에서 찾았다. 450/200의 5위에서 회수했던 제3자 배송대행 제외와 공식 프로그램 예외를 담은 조항은 이번 상위 5개에서 빠졌다. |
| 국제 반품 라벨 | SUFFICIENT | SUFFICIENT | 라벨 미제공 시 개입 근거가 1위에서 4위로 내려갔다. 2위의 판매자 반송비 부담과 추적·서명 요건은 유지됐다. 450/200에서 찾은 대체 반송 서비스 및 비용의 사전 합의 문구는 상위 5개에 없었다. |

### 질의 1 · 판매자 직접 배송 신발 오배송

> 미국 eBay 판매자가 직접 배송한 신발을 한국에서 받았습니다. 주문은 US 9였지만 상자와 신발 모두 US 8입니다. 수령 12일째이고 판매자는 상품 페이지에 ‘반품 불가’라고 적었습니다. 교환을 요청할 수 있나요? 판매자가 교환을 거절하면 전액 환불과 최초·반품 배송비는 어떻게 처리되나요?

상태 EVIDENCE_SUFFICIENT · 반환 청크 5개 · 서비스 호출 10442ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `504fcdd8-5ab7-4972-acaf-7c763d5ba9c2` | 0.479966 | APPLICABLE |
| 2 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | `84f62e4d-194f-4eb5-b98a-82c43ea6b666` | 0.470295 | APPLICABLE |
| 3 | [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115) | `7be642f8-ad44-4a49-9c95-7445840a4316` | 0.461377 | APPLICABLE |
| 4 | [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115) | `57834d49-6e6b-4433-ae42-28c686ba2120` | 0.461166 | NOT_APPLICABLE |
| 5 | [How to handle a return request as a seller](https://www.ebay.com/help/buying/returns-refunds/returning-item?id=4115) | `3aeabc42-0cec-41f5-a6b8-34e2e0b05ae7` | 0.458304 | APPLICABLE |

#### 실제 응답의 searchNote

> 주문이 US 9이고 실제 상품이 US 8이라는 불일치를 입증할 수 있다면 ‘상품 설명과 불일치’ 사유로 반품을 요청할 근거가 있으며, ‘반품 불가’ 표시는 이를 막지 않는다고 정책에 나와 있습니다. 이 경우 전액 환불에는 최초 배송비가 포함되고 판매자가 반품 배송비를 부담하지만, 교환은 판매자가 제안할 수 있는 선택지일 뿐 보장되지 않으며 실제 불일치 증빙과 상품 상태는 확인되지 않았습니다.

### 질의 2 · 단순 변심과 사이즈 교환

> 미국 eBay 판매자가 한국으로 직접 배송한 재킷을 수령한 지 8일 됐습니다. 주문한 사이즈와 설명은 정확하지만 입어보니 작습니다. 상품 페이지에 ‘30일 반품 가능, 반품 배송비 구매자 부담’이라고 적혀 있습니다. 다른 사이즈로 교환해 달라고 할 수 있나요? 반품하면 최초 배송비도 돌려받나요?

상태 EVIDENCE_INSUFFICIENT · 반환 청크 5개 · 서비스 호출 10495ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `504fcdd8-5ab7-4972-acaf-7c763d5ba9c2` | 0.458778 | APPLICABLE |
| 2 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `434a0b50-8322-47af-b17b-c822e841149f` | 0.451710 | APPLICABLE |
| 3 | [Get help if you bought as a guest](https://www.ebay.com/help/buying/returns-refunds/returning-item/returning-items-bought-guest?id=4065) | `84f62e4d-194f-4eb5-b98a-82c43ea6b666` | 0.444510 | UNCERTAIN |
| 4 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `5a4aa44e-d3b1-400c-9b05-55fe482421ca` | 0.443247 | APPLICABLE |
| 5 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `3ba007d6-ca80-4362-9bc5-eecb708460f8` | 0.442290 | APPLICABLE |

#### 실제 응답의 searchNote

> 30일 반품 조건에 따라 수령 8일째인 단순 변심 반품은 요청할 근거가 있고, 반품 배송비는 표시된 대로 구매자 부담으로 보입니다. 다른 사이즈 교환은 판매자가 제안할 수 있을 뿐 보장되지 않으며, 최초 배송비 환급은 설명 불일치·불량·파손일 때만 명시되어 있어 이 경우에는 확인된 근거가 없습니다.

### 질의 3 · eIS 구성품 누락

> 주문 상세에 eBay International Shipping으로 표시된 미국발 상품을 한국에서 받았는데 구성품이 빠져 있습니다. 수령 18일째이고 판매자는 교체품을 직접 보내줄 수 없다고 합니다. 누구에게 반품이나 환불을 요청해야 하며, 교체품 발송도 요구할 수 있나요?

상태 EVIDENCE_SUFFICIENT · 반환 청크 5개 · 서비스 호출 8233ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `a1b9dab3-dab6-4a1a-b48e-0f2416ecbe12` | 0.484339 | APPLICABLE |
| 2 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `504fcdd8-5ab7-4972-acaf-7c763d5ba9c2` | 0.480975 | UNCERTAIN |
| 3 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `5a4aa44e-d3b1-400c-9b05-55fe482421ca` | 0.480746 | UNCERTAIN |
| 4 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `c58ab21a-6401-421e-b64c-21ec03c34e7c` | 0.477421 | UNCERTAIN |
| 5 | [eBay International Shipping Seller Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/seller/terms/) | `7d04590e-a60a-4447-86b0-dd97fca32d95` | 0.469910 | NOT_APPLICABLE |

#### 실제 응답의 searchNote

> EIS로 확인되고 실제 배송일부터 30일 이내라면 판매자에게만 요청하기보다 eBay에서 국제 반품을 신청하는 절차가 근거로 뒷받침됩니다. 구성품 누락이 설명과 다른 상품 상태에 해당하면 반품·환불 요청 근거가 될 수 있지만, 교체품은 합의 가능한 대안일 뿐 강제 발송 권리는 확인되지 않으며, 누락 구성품이 목록에 포함됐는지와 환불 승인 여부는 추가 확인이 필요합니다.

### 질의 4 · 제3자 배대지와 구매자 보호

> 미국 eBay에서 중고 카메라를 구매해 제가 선택한 미국 배대지로 보낸 뒤 한국에서 받았습니다. 배대지에는 배송 완료됐지만 한국에서 개봉하니 상품 설명에 없던 렌즈 파손이 확인됐습니다. 판매자에게 환불을 요청할 때 eBay Money Back Guarantee를 적용받을 수 있나요?

상태 EVIDENCE_SUFFICIENT · 반환 청크 5개 · 서비스 호출 8386ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `a7d6e7f3-5c5f-4fa4-9d76-c14852eeb9d8` | 0.499229 | UNCERTAIN |
| 2 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `90b591ba-a082-4d0c-8e39-2b56278899fc` | 0.492509 | UNCERTAIN |
| 3 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `7cedeb20-1545-48f8-ae47-27cb520459fc` | 0.488751 | UNCERTAIN |
| 4 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `5a4aa44e-d3b1-400c-9b05-55fe482421ca` | 0.480051 | UNCERTAIN |
| 5 | [eBay Money Back Guarantee policy](https://www.ebay.com/help/policies/ebay/ebay?id=4210) | `748b0b87-4840-4963-9ed4-76ad728fd211` | 0.474716 | NOT_APPLICABLE |

#### 실제 응답의 searchNote

> 상품 설명에 없던 렌즈 파손은 ‘설명과 다름’ 사유로 반품·환불을 요청할 근거가 될 수 있으며, 해당 요청은 원칙적으로 배송일 기준 30일 또는 판매자 반품 기간 중 더 긴 기한 내에 해야 합니다. 다만 eBay.com checkout·적격 결제 등 자격 요건, 배대지 도착 후 파손이 생겼는지, 기한 준수 여부가 확인되지 않아 환불이 반드시 승인된다고 단정할 수는 없습니다.

### 질의 5 · 국제 반품 라벨과 배송 증빙

> 미국 eBay 판매자가 한국으로 직접 배송한 820달러짜리 기기가 작동하지 않아 반품을 접수했습니다. 판매자는 반품을 승인했지만, 접수 후 영업일 4일이 지나도 한국에서 쓸 수 있는 라벨을 주지 않았습니다. 제가 먼저 국제 반송비를 결제하면 돌려받을 수 있나요? eBay 개입은 언제 요청할 수 있고, 반송 시 어떤 배송 증빙이 필요한가요?

상태 EVIDENCE_SUFFICIENT · 반환 청크 5개 · 서비스 호출 9307ms

| 순위 | 정책 문서 | 청크 ID | 유사도 | 적용 판단 |
|---:|---|---|---:|---|
| 1 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `c58ab21a-6401-421e-b64c-21ec03c34e7c` | 0.545307 | UNCERTAIN |
| 2 | [Return shipping for buyers](https://www.ebay.com/help/buying/returns-refunds/returning-item/return-shipping?id=4066) | `accca91a-6c46-4914-972b-c063b17acccd` | 0.544505 | APPLICABLE |
| 3 | [eBay International Shipping - Buyer Terms & Conditions](https://pages.ebay.com/internationalshippingprogram/buyer/terms/) | `a1b9dab3-dab6-4a1a-b48e-0f2416ecbe12` | 0.542991 | APPLICABLE |
| 4 | [Return an item for a refund](https://www.ebay.com/help/buying/returns/refunds/item-not-as-described-policy?id=4041) | `504fcdd8-5ab7-4972-acaf-7c763d5ba9c2` | 0.541845 | APPLICABLE |
| 5 | [Return shipping for buyers](https://www.ebay.com/help/buying/returns-refunds/returning-item/return-shipping?id=4066) | `b767d1cd-72d0-40f7-914b-0a6b49fcedf7` | 0.541036 | APPLICABLE |

#### 실제 응답의 searchNote

> 기기 고장 반품이면 판매자가 반송비를 부담해야 하며, 판매자가 승인했지만 해결책이나 라벨을 주지 않은 경우 eBay 개입을 요청할 근거가 있습니다. 다만 국제 반송비를 먼저 내면 반드시 상환된다는 보장은 없으므로 먼저 eBay에 한국에서 쓸 라벨·반송 지침을 요청하고, 직접 발송한다면 추적과 서명 확인을 갖추며 이메일의 기한과 반송 주소를 따라야 합니다.

## 새 구매자 질의 20개 · 500/200과 450/200 비교 · 2026-11-28

**결론.** 모델의 근거 충분 판정은 `450/200`이 14/20건, `500/200`이 13/20건이었다. 반면 단순 변심 반품에서 최초 배송비를 제외할 수 있다는 조항은 `500/200`에서만 검색됐다. 설정별로 질의당 한 번 실행했으므로 이번 결과만으로 검색 품질의 우열을 확정하기 어렵다. 비교 기준은 각 질문에 필요한 정책 조항이 상위 5개 청크에 포함됐는지다.

정책 문서의 제목이나 문구를 질문 작성의 출발점으로 삼지 않고, 배송·결제·취소·반품 등 구매자가 문의할 법한 다양한 가상 상황을 새로 작성했다. 무작위 표본 추출을 수행하지 않은 편의 표본이다.

같은 정제 HTML 22개에서 500/200은 133개 청크, 450/200은 146개 청크를 사용했다. 500/200을 먼저 실행한 뒤 벡터 인덱스를 백업하고 450/200으로 재적재했다. 질문 원문, `platform=EBAY`, `topK=5`, `similarityThreshold=0.0`을 고정했다. 같은 Spring 서비스에서 설정별 각 질의를 한 번 직접 실행했다.

### 판정 집계

아래 판정은 모델이 응답한 `searchStatus`다. 질문별 정답을 미리 확정하거나 여러 번 실행해 반복성을 측정하지 않았다.

| 설정 | SUFFICIENT | INSUFFICIENT | POLICY_NOT_FOUND |
|---|---:|---:|---:|
| 500/200 | 13 | 7 | 0 |
| 450/200 | 14 | 6 | 0 |

### 검색 근거 비교

- 두 설정의 충분 판정은 12건에서 겹쳤다. 450/200에서만 충분 판정한 질문은 4·6·11번, 500/200에서만 충분 판정한 질문은 9·17번이었다.
- **4번 · 단순 변심의 최초 배송비**에서 500/200은 판매자가 최초 배송비를 환불액에서 제외할 수 있다는 Money Back Guarantee 본문을 찾았다. 450/200은 충분 판정을 했지만 해당 문구가 상위 5개에 없었고, 응답은 판매자 정책에 달려 있다고 설명했다.
- **6번 · 교체 약속 후 요청 종료**에서 두 설정 모두 닫은 요청은 다시 열 수 없다는 근거를 찾았다. **9번 · 한 주문의 일부 미수령**에서도 두 설정 모두 번들은 전부 반환한다는 문구를 찾았다. 500/200 응답은 개별 상품과 번들을 어떻게 처리하는지 더 직접적으로 설명했다.
- **11번 · 반품 주소 불일치**에서 450/200은 충분 판정을 했지만, 두 설정의 응답 모두 어떤 주소가 우선하는지 확인하지 못했다. **17번 · 예약 주문 지연**에서 500/200은 충분 판정을 했지만, 두 설정 모두 예약 주문 정책 대신 일반 주문 취소 안내를 검색했다.
- **7번 · 카드사 이의 제기**와 **20번 · 플랫폼 밖 일부 환불**은 양쪽 모두 질문의 핵심 쟁점을 뒷받침하는 근거를 찾지 못했다.

### 질문별 응답

각 설정에서 검색된 상위 5개 청크의 정책 문서 제목과 유사도를 순서대로 기록했다. 같은 문서에서 여러 청크가 검색되면 제목이 반복된다.

#### 질문 1

> 배송 예정일이 지났는데 판매자가 발송했다고만 하고 추적번호를 주지 않아요. 며칠 더 기다려야 하고 어디서 도움을 요청하나요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | SUFFICIENT | 판매자에게 상품이 도착하지 않았다고 알리고 미수령 신고를 할 수 있으며, 판매자는 신고 후 영업일 3일 이내에 추적 정보·배송 업데이트 또는 환불을 제공해야 합니다. 해결되지 않으면 eBay에 개입을 요청할 수 있고, 유효한 추적 정보가 없을 때 eBay는 신고 후 영업일 3~21일 사이에 개입할 수 있지만, 신고일과 거래 자격이 확인되지 않아 정확히 며칠을 기다려야 하는지는 단정할 수 없습니다. |
| 450/200 | SUFFICIENT | 예상 배송일이 지났다면 주문에서 미수령으로 신고할 수 있고, 판매자는 신고 후 영업일 3일 이내에 추적 정보·배송 업데이트 또는 환불을 제공해야 하며 해결되지 않으면 eBay에 개입을 요청할 수 있습니다. 신고 기한과 eBay 개입 가능 시점은 안내되어 있지만, 신고일과 거래의 보호 요건이 확인되지 않아 지금부터 정확히 며칠 기다려야 하는지는 단정할 수 없습니다. |

- 500/200 · 1. eBay Money Back Guarantee policy (0.3626) / 2. eBay Money Back Guarantee policy (0.3615) / 3. Get help if you bought as a guest (0.3312) / 4. Selling practices policy (0.3249) / 5. eBay Money Back Guarantee policy (0.3130)
- 450/200 · 1. eBay Money Back Guarantee policy (0.3602) / 2. eBay Money Back Guarantee policy (0.3478) / 3. Get help if you bought as a guest (0.3436) / 4. Get help if you bought as a guest (0.3385) / 5. eBay Money Back Guarantee policy (0.3209)

#### 질문 2

> 중고 노트북을 받았는데 설명에 없던 화면 깜박임이 있어요. 판매자가 수리비 일부만 보내겠다고 하는데 반품하고 전액을 받을 수 있나요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | SUFFICIENT | 설명에 없던 실제 결함이라면 반품 요청과 환불을 요구할 정책상 근거가 있고, 판매자와 부분 환불에 합의하지 않고 반품을 요청할 수 있습니다. 다만 기한 준수와 결함·미고지 여부, 반품 시 상태가 확인되지 않아 전액 환불이 확정된다고 할 수 없으며, 판매자의 부분 환불 제안은 가능한 해결책이지 구매자가 반드시 수락해야 하는 조건은 아닙니다. |
| 450/200 | SUFFICIENT | 화면 깜박임이 설명에 없던 결함임을 뒷받침할 수 있다면, 판매자 반품 정책과 무관하게 반품을 요청하고 반품 수락 시 원래 배송비를 포함한 전액 환불 및 판매자 부담 반품 배송을 주장할 근거가 있습니다. 다만 결함·설명 불일치 여부와 신청 기한은 확인되지 않았고, 판매자는 부분 환불이나 교환을 제안할 수 있으며 전액 환불은 반품 수락과 반환 상태에 달려 있습니다. |

- 500/200 · 1. eBay Money Back Guarantee policy (0.3517) / 2. Get help if you bought as a guest (0.3492) / 3. Return an item for a refund (0.3450) / 4. Condition of returned items policy (0.3359) / 5. Return an item for a refund (0.3329)
- 450/200 · 1. Return an item for a refund (0.3876) / 2. Get help if you bought as a guest (0.3417) / 3. Condition of returned items policy (0.3409) / 4. Return an item for a refund (0.3391) / 5. Get help if you bought as a guest (0.3382)

#### 질문 3

> 결제하고 20분 뒤에 같은 물건을 다른 곳에서 더 싸게 발견했어요. 아직 발송 전인데 주문 취소를 요청하면 판매자가 꼭 취소해 줘야 하나요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | SUFFICIENT | 더 싸게 발견했다는 이유로 취소를 요청할 수는 있지만, 판매자는 요청을 수락하거나 거절할 수 있으므로 반드시 취소해 줘야 하는 것은 아닙니다. 발송 전이라는 사실만으로 결과가 보장되지는 않으며, 경매·eBay Live 구매 여부와 발송·수령 상태 등 핵심 조건도 확인해야 합니다. |
| 450/200 | SUFFICIENT | 판매자에게 주문 취소를 요청할 수 있다는 점은 정책으로 뒷받침되지만, 판매자는 수락하거나 거절할 수 있으므로 반드시 취소해 줘야 한다고 요구할 근거는 없습니다. 발송 전이라도 경매·eBay Live 구매 여부와 판매자의 발송 표시 여부가 확인되지 않았고, 더 싸게 발견했다는 이유만으로 취소가 보장되지는 않습니다. |

- 500/200 · 1. Order cancellation policy (0.4622) / 2. Order cancellation policy (0.4552) / 3. Order cancellation policy (0.4083) / 4. Return an item for a refund (0.3495) / 5. Return an item for a refund (0.3418)
- 450/200 · 1. Order cancellation policy (0.4614) / 2. Order cancellation policy (0.4510) / 3. Order cancellation policy (0.4105) / 4. Selling practices policy (0.3606) / 5. Return an item for a refund (0.3584)

#### 질문 4

> 선물로 산 코트를 개봉해 보니 색상과 사이즈는 주문한 그대로인데 마음에 들지 않아요. 반품 가능 상품이면 처음 낸 배송비도 돌아오나요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | INSUFFICIENT | 목록에서 반품을 허용하고 반품 기간 안에 요청하면 판매자는 변심 반품을 받아야 하지만, 최초 배송비는 환불액에서 제외할 수 있어 반환을 요구해도 보장되지는 않습니다. 목록의 반품 조건과 기간이 확인되지 않았고, 정책은 최초 배송비 제외를 명시적으로 허용합니다. |
| 450/200 | SUFFICIENT | 판매자에게 마음에 들지 않는다는 이유로 반품을 요청할 수는 있지만, 반품 수락 여부와 최초 배송비 환불은 상품 등록의 판매자 반품 정책에 달려 있습니다. 주문 내용과 다르거나 하자·손상된 경우에는 최초 배송비 환불이 명시되어 있으나, 현재 코트는 주문한 색상·사이즈와 같아 그 예외의 근거는 확인되지 않습니다. |

- 500/200 · 1. Return an item for a refund (0.3188) / 2. Return an item for a refund (0.3170) / 3. eBay Money Back Guarantee policy (0.3156) / 4. Condition of returned items policy (0.3156) / 5. Return an item for a refund (0.3152)
- 450/200 · 1. Return an item for a refund (0.3410) / 2. Return an item for a refund (0.3324) / 3. Get help if you bought as a guest (0.3308) / 4. Return an item for a refund (0.3217) / 5. Get help if you bought as a guest (0.3215)

#### 질문 5

> 문 앞 배송 사진이 제 집 현관과 전혀 다릅니다. 배송 완료 상태인데 물건을 못 받았으면 어떤 증거를 내야 하나요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | SUFFICIENT | 판매자에게 미수령을 신고하고 배송 추적·상황 확인 또는 환불을 요청하는 근거로는 사용할 수 있습니다. 다만 구매자가 반드시 제출해야 할 증거 목록은 검색된 정책에 없으며, 배송 성공 여부는 주문 주소와 일치하는 배송 정보 등 판매자의 증빙 및 실제 수령 사실을 확인해야 판단할 수 있습니다. |
| 450/200 | SUFFICIENT | 미도착 신고는 예상 또는 실제 배송일이 지난 뒤 제기할 수 있고, 판매자는 추적 정보·배송 업데이트 또는 환불로 응답해야 하므로 판매자에게 미도착 사실과 현관 사진이 주문 주소와 다르다는 점을 알릴 근거는 있습니다. 다만 구매자에게 요구되는 특정 증거 목록은 검색된 정책에 없으며, 배송 성공 여부는 주문 주소와 일치하는 수령지 정보 등 판매자의 배송 증빙 및 거래 세부사항으로 판단되므로 사진만으로 환불을 보장하지는 않습니다. |

- 500/200 · 1. eBay Money Back Guarantee policy (0.4439) / 2. eBay Money Back Guarantee policy (0.4138) / 3. eBay Money Back Guarantee policy (0.3539) / 4. eBay Money Back Guarantee policy (0.3466) / 5. Get help if you bought as a guest (0.3238)
- 450/200 · 1. eBay Money Back Guarantee policy (0.4283) / 2. eBay Money Back Guarantee policy (0.4139) / 3. eBay Money Back Guarantee policy (0.3647) / 4. eBay Money Back Guarantee policy (0.3416) / 5. Get help if you bought as a guest (0.3339)

#### 질문 6

> 판매자가 오배송을 인정하고 새 상품을 보낸다고 했는데 반품 요청부터 닫으래요. 닫은 뒤에도 새 상품이 안 오면 다시 도움을 받을 수 있나요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | INSUFFICIENT | 판매자가 교환품을 제안할 수 있고, 열린 요청에서 합의가 되지 않으면 3영업일 후 eBay에 개입을 요청할 수 있다는 근거는 있습니다. 그러나 요청을 닫으면 다시 열거나 같은 거래로 새 요청을 제출할 수 없으며, 닫은 뒤 교환품이 오지 않았을 때의 별도 구제 절차는 확인되지 않아 요청을 닫아도 다시 도움을 받을 수 있다고 판매자에게 확답할 근거는 부족합니다. |
| 450/200 | SUFFICIENT | 판매자에게 요청을 닫지 말고 열린 요청에서 메시지를 보내거나, 판매자가 해결책을 제공하지 않으면 eBay에 개입을 요청할 수 있다는 근거는 있습니다. 다만 닫은 요청은 다시 열거나 같은 거래에 새 요청을 낼 수 없다고 안내하므로, 닫은 뒤 새 상품이 오지 않았을 때 다시 도움받을 수 있다고 단정할 수 없습니다. |

- 500/200 · 1. Return an item for a refund (0.3796) / 2. How to handle a return request as a seller (0.3793) / 3. How to handle a return request as a seller (0.3701) / 4. Return an item for a refund (0.3654) / 5. Get help if you bought as a guest (0.3647)
- 450/200 · 1. Return an item for a refund (0.3860) / 2. How to handle a return request as a seller (0.3796) / 3. Get help if you bought as a guest (0.3750) / 4. Get help if you bought as a guest (0.3684) / 5. Get help if you bought as a guest (0.3681)

#### 질문 7

> 카드로 결제하고 받은 물건이 설명과 달라요. 카드사에 먼저 결제 이의를 제기하면 사이트에서 반품이나 환불 신청도 할 수 있나요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | INSUFFICIENT | 설명과 다른 상품은 반품 신청 대상이 될 수 있으며, 주문 내역에서 반품 사유를 제출할 수 있다는 점은 판매자에게 요구할 근거로 쓸 수 있습니다. 다만 카드사에 먼저 결제 이의를 제기한 뒤에도 사이트 반품 신청이 가능한지와 두 절차의 관계는 검색된 정책에 없어 단정할 수 없습니다. |
| 450/200 | INSUFFICIENT | 상품이 설명과 다르면 사이트에 반품 요청을 제출할 수 있고, 판매자가 반품 불가 정책을 두었더라도 반품 대상이 될 수 있다는 근거가 있습니다. 그러나 카드사 이의 제기를 먼저 한 뒤에도 신청 가능한지, 신청 기한 등 핵심 조건은 검색된 자료에 없어 그 점까지 판매자에게 확정적으로 요구할 근거는 부족합니다. |

- 500/200 · 1. Get help if you bought as a guest (0.3962) / 2. Get help if you bought as a guest (0.3937) / 3. Get help if you bought as a guest (0.3873) / 4. Return an item for a refund (0.3799) / 5. Get help if you bought as a guest (0.3729)
- 450/200 · 1. Return an item for a refund (0.3993) / 2. Return an item for a refund (0.3895) / 3. Get help if you bought as a guest (0.3875) / 4. Get help if you bought as a guest (0.3826) / 5. Get help if you bought as a guest (0.3803)

#### 질문 8

> 할인 쿠폰과 카드로 나눠서 결제했는데 판매자가 재고가 없다며 주문을 취소했어요. 쿠폰과 카드 결제액은 각각 어떻게 되나요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | INSUFFICIENT | 판매자 취소 시 eBay 체크아웃 결제는 원래 결제 수단으로 전액 환불된다는 정책과, 단일 상품 쿠폰은 원래 만료일을 유지한 채 반환된다는 안내를 근거로 요구할 수 있습니다. 다만 주문이 eBay 체크아웃으로 처리됐는지와 쿠폰·카드 각 부분의 정확한 환급 배분은 확인되지 않았으므로 구체적인 결과를 단정할 수 없습니다. |
| 450/200 | INSUFFICIENT | 판매자에게 eBay 체크아웃 결제의 전액을 원래 결제수단으로 환불해 달라고 요구할 근거가 있고, 체크아웃 외 결제라면 판매자가 전액 환불을 처리해야 합니다. 쿠폰을 사용한 결제가 역결제되면 단일 상품 쿠폰도 원래 만료일을 유지한 채 반환된다는 정책은 있으나, 이번 주문의 쿠폰별 반환 여부와 카드·쿠폰에 배분되는 개별 금액은 확인된 정보만으로 단정할 수 없습니다. |

- 500/200 · 1. eBay Coupon FAQs (0.4502) / 2. eBay Coupon FAQs (0.4339) / 3. Order cancellation policy (0.3675) / 4. Order cancellation policy (0.3619) / 5. eBay Coupon FAQs (0.3604)
- 450/200 · 1. eBay Coupon FAQs (0.4308) / 2. Order cancellation policy (0.3605) / 3. Order cancellation policy (0.3593) / 4. eBay Coupon FAQs (0.3574) / 5. eBay Coupon FAQs (0.3519)

#### 질문 9

> 상품 두 개를 한 주문으로 샀는데 택배 상자에는 한 개만 들어 있어요. 받은 것까지 같이 반품해야 하나요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | SUFFICIENT | 일부만 배송된 경우 누락 상품을 받지 못했다고 신고하는 절차가 근거로 뒷받침됩니다. 다만 세트·번들은 전체 반환이 필요하고, 개별 상품은 다르게 처리될 수 있으므로 상품이 세트인지 별도 목록 상품인지 확인 전에는 받은 상품도 함께 반환해야 한다고 단정할 수 없습니다. |
| 450/200 | INSUFFICIENT | 세트·번들이 아니라면, 받은 상품을 함께 반품해야 한다고 단정할 근거는 없으며 일부 상품 미수령으로 신고하는 절차가 안내되어 있습니다. 세트·번들이면 반품 요청이 필요하지만, 받은 구성품을 보관하고 부분 환불받는 것은 판매자가 제안할 수 있을 뿐 보장되지는 않습니다. |

- 500/200 · 1. Get help if you bought as a guest (0.3338) / 2. Return shipping for buyers (0.3112) / 3. Get help if you bought as a guest (0.3110) / 4. Return an item for a refund (0.3048) / 5. Get help if you bought as a guest (0.3042)
- 450/200 · 1. Return an item for a refund (0.3401) / 2. Get help if you bought as a guest (0.3347) / 3. Get help if you bought as a guest (0.3221) / 4. Get help if you bought as a guest (0.3170) / 5. Return an item for a refund (0.3119)

#### 질문 10

> 판매자가 반품을 받았다고 확인했는데 일주일째 카드에 환불이 안 보여요. 판매자가 환불을 안 한 건지 결제 처리 중인지 어떻게 확인하나요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | SUFFICIENT | 판매자에게 반품 상세의 환불 상태와 기한을 확인해 달라고 요청할 근거가 있습니다. 판매자는 통상 반품품 수령 후 2영업일 이내 환불을 발행하지만, 카드 반영은 보통 3~5영업일이고 결제 수단에 따라 최대 30일 걸릴 수 있으므로, 실제 수령일·표시된 기한·환불 발행 여부를 확인하기 전에는 미발행인지 처리 중인지 단정할 수 없습니다. |
| 450/200 | SUFFICIENT | 판매자에게 요청 상세에서 환불 상태와 마감일을 확인하라고 요구하고, 반품 수령 후 판매자가 통상 2영업일 이내 환불을 발행하며 결제 반영에는 보통 3~5영업일, 일부 결제 수단은 최대 30일 걸릴 수 있다는 근거를 제시할 수 있습니다. 다만 수령 확인일·환불 발행 여부·요청 상세의 기한이 확인되지 않아 판매자가 환불하지 않았는지, 카드 처리 중인지는 아직 단정할 수 없습니다. |

- 500/200 · 1. How to handle a return request as a seller (0.4392) / 2. Return an item for a refund (0.4312) / 3. Get help if you bought as a guest (0.4184) / 4. Return an item for a refund (0.4076) / 5. eBay Money Back Guarantee policy (0.4057)
- 450/200 · 1. Return an item for a refund (0.4315) / 2. Get help if you bought as a guest (0.4250) / 3. Return an item for a refund (0.4138) / 4. How to handle a return request as a seller (0.4126) / 5. eBay Money Back Guarantee policy (0.3996)

#### 질문 11

> 반품 라벨에 적힌 주소와 판매자가 메시지로 보낸 주소가 달라요. 어느 주소로 보내야 하고 어떤 배송 기록을 남겨야 하나요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | INSUFFICIENT | 검색된 정책은 추적 가능한 배송을 이용하고 추적 정보를 반품 요청에 올리라는 요구를 뒷받침하며, 판매자 부담 반품에서는 제공된 라벨이나 판매자와 합의한 다른 방법을 쓰도록 합니다. 그러나 라벨 주소와 메시지 주소 중 어느 쪽을 우선할지는 정하지 않으므로, 발송 전에 반품 요청을 통해 판매자에게 주소를 확인하고 합의된 주소를 서면으로 남기세요. |
| 450/200 | SUFFICIENT | 반품 기한을 지키고 추적 가능한 배송을 사용해 추적 정보를 반품 요청에 등록하라는 요구는 근거로 쓸 수 있으며, 750달러 이상이면 서명 확인도 필요합니다. 그러나 라벨 주소와 메시지 주소가 다를 때 어느 쪽을 따라야 하는지는 정책이 정하지 않으므로, 반품 상세와 공식 발송 안내를 확인하고 판매자에게 명확히 확인받기 전에는 특정 주소로 보내라고 단정할 수 없습니다. |

- 500/200 · 1. eBay Money Back Guarantee policy (0.4982) / 2. Return shipping for buyers (0.4786) / 3. Return shipping for buyers (0.4706) / 4. Return shipping for buyers (0.4526) / 5. How to handle a return request as a seller (0.4405)
- 450/200 · 1. Return shipping for buyers (0.4882) / 2. Return shipping for buyers (0.4554) / 3. Return shipping for buyers (0.4460) / 4. How to handle a return request as a seller (0.4423) / 5. How to handle a return request as a seller (0.4354)

#### 질문 12

> 친구가 대신 받아준 고가 카메라가 배송 완료로 찍혔는데 친구는 받은 적이 없다고 해요. 서명이나 배송지까지 확인해 달라고 할 수 있나요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | SUFFICIENT | 판매자에게 미수령 문제를 알리고 배송 업데이트를 요청할 수 있으며, 배송 추적의 날짜와 주문 주소 일치 여부 및 적용되는 경우 서명 확인 정보를 확인해 달라고 하는 것은 정책상 배송 증거 요건에 근거가 있습니다. 그러나 친구가 대신 수령한 경우 보호 제외가 될 수 있고, 총액·배송 주소·서명 기록 등 핵심 조건이 미확인이라 서명이나 주소 자료를 판매자가 반드시 제공해야 한다거나 환불된다고 단정할 수는 없습니다. |
| 450/200 | SUFFICIENT | 미수령 신고 후 eBay가 주문 주소의 우편번호(또는 주소 정보)와 배송 상태·날짜를 확인하며, 주문 총액이 750달러 이상이면 서명 확인도 요구한다는 정책을 근거로 판매자에게 배송지와 서명 확인 기록을 문의할 수 있습니다. 다만 검색된 정책은 판매자가 구매자에게 그 자료를 직접 제공해야 한다고 보장하지 않으며, 친구가 실제 대리 수령했다면 제3자 수령 제외가 적용될 수 있어 수령 여부와 거래 조건을 확인해야 합니다. |

- 500/200 · 1. eBay Money Back Guarantee policy (0.3339) / 2. eBay Money Back Guarantee policy (0.3295) / 3. Signature confirmation policy (0.2799) / 4. eBay Authenticity Guarantee for Sneakers (0.2703) / 5. Get help if you bought as a guest (0.2692)
- 450/200 · 1. eBay Money Back Guarantee policy (0.3342) / 2. eBay Money Back Guarantee policy (0.3332) / 3. eBay Money Back Guarantee policy (0.2975) / 4. Get help if you bought as a guest (0.2748) / 5. Signature confirmation policy (0.2689)

#### 질문 13

> 새 운동화라고 해서 주문했는데 도착한 제품에 사용 흔적이 있고 정품인지도 의심돼요. 어떤 이유로 신고하거나 반품 요청을 해야 하나요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | INSUFFICIENT | 새 상품은 미사용·미착용이어야 하므로 실제 사용 흔적이 있다면 등록 상태와 다르다는 이유로 신고할 근거가 됩니다. 정품 여부는 적격 인증 상품에서 인증 불가 또는 등록 내용 불일치가 확인되면 전액 환불 절차가 안내돼 있지만, 상품의 인증 대상 여부와 검사 결과가 확인되지 않아 판매자에게 환불을 요구하면 반드시 받아들여진다고 단정할 수 없습니다. |
| 450/200 | INSUFFICIENT | 새 제품은 미사용·미착용이어야 하므로 실제 사용 흔적이 있고 목록에 새 제품으로 표시됐다면 목록과 다르다는 사유로 문제를 제기할 근거가 있습니다. 정품이 아니거나 현저히 설명과 다르다고 확인되면 환불 정책이 뒷받침되지만, 인증 대상 여부와 최종 판매 여부 및 반품 기간은 확인되지 않아 환불을 보장할 수는 없습니다. |

- 500/200 · 1. eBay Authenticity Guarantee for Sneakers (0.4480) / 2. eBay Authenticity Guarantee for Sneakers (0.4178) / 3. eBay Authenticity Guarantee for Sneakers (0.4042) / 4. eBay Authenticity Guarantee for Sneakers (0.3971) / 5. eBay Authenticity Guarantee for Sneakers (0.3940)
- 450/200 · 1. eBay Authenticity Guarantee for Sneakers (0.4660) / 2. eBay Authenticity Guarantee for Sneakers (0.4464) / 3. eBay Authenticity Guarantee for Sneakers (0.4206) / 4. eBay Authenticity Guarantee for Sneakers (0.3969) / 5. eBay Authenticity Guarantee for Sneakers (0.3957)

#### 질문 14

> 주문자가 회원가입 없이 결제했고 주문 확인 이메일만 가지고 있어요. 불량품 반품을 어디에서 시작하나요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | SUFFICIENT | 불량품 반품은 주문 확인 이메일의 주문 상세에서 Return item을 선택해 시작할 수 있고, 사진을 첨부할 수도 있습니다. 불량품은 판매자의 반품 불가 정책에도 불구하고 자격 요건을 충족하면 보호 대상일 수 있지만, 교환은 판매자가 제안할 수 있는 선택지일 뿐 보장되지 않으며 실제 보호 적용 여부는 확인되지 않았습니다. |
| 450/200 | SUFFICIENT | 주문 확인 이메일에서 ‘View order details’ → ‘Return item’을 선택해 불량 사유를 제출하고 사진을 최대 10장 첨부할 수 있으므로 판매자에게 반품을 요청하는 절차의 근거로 쓸 수 있습니다. 불량품은 머니백 개런티 대상일 수 있지만 자격 요건 충족 여부와 불량 상태는 확인되지 않았고, 판매자의 응답 기한은 3영업일이며 특정 환불 결과까지 보장하지는 않습니다. |

- 500/200 · 1. Get help if you bought as a guest (0.4174) / 2. Get help if you bought as a guest (0.3756) / 3. Get help if you bought as a guest (0.3587) / 4. Get help if you bought as a guest (0.3549) / 5. Get help if you bought as a guest (0.3511)
- 450/200 · 1. Get help if you bought as a guest (0.4081) / 2. Get help if you bought as a guest (0.3796) / 3. Get help if you bought as a guest (0.3604) / 4. Get help if you bought as a guest (0.3581) / 5. Get help if you bought as a guest (0.3483)

#### 질문 15

> 반품한 이어폰은 작동 상태만 확인했는데 판매자가 포장을 뜯었다며 환불액에서 상당 부분을 뺐어요. 차감에 이의를 제기할 수 있나요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | SUFFICIENT | 차감에 이의를 제기하고, 반환 상품의 상태나 차감 사유가 납득되지 않으면 판매자와 해결을 시도한 뒤 조건에 해당할 때 eBay에 개입을 요청할 수 있습니다. 다만 판매자는 실제 가치 손실을 회복하기 위해 차감할 수 있고, 포장 개봉만으로 차감이 부당하다고 단정할 수 없으므로 이어폰·구성품 상태와 차감 근거를 확인해야 합니다. |
| 450/200 | SUFFICIENT | 차감액에 이의를 제기하고 eBay에 도움을 요청할 수 있으며, 차감은 반환 상품의 실제 가치 손실을 보전하는 경우로 제한된다는 근거가 있습니다. 다만 작동 확인을 위한 포장 개봉인지 불필요한 취급인지, 이어폰에 실제 가치 손실이 있었는지는 확인되지 않았고, 이의 제기가 차감 취소나 전액 환불을 보장하지는 않습니다. |

- 500/200 · 1. Condition of returned items policy (0.4466) / 2. Condition of returned items policy (0.4290) / 3. Return an item for a refund (0.3884) / 4. How to handle a return request as a seller (0.3857) / 5. Return an item for a refund (0.3844)
- 450/200 · 1. Condition of returned items policy (0.4448) / 2. How to handle a return request as a seller (0.4120) / 3. Condition of returned items policy (0.4111) / 4. Return an item for a refund (0.4090) / 5. Return an item for a refund (0.4028)

#### 질문 16

> 해외에서 산 제품이 도착했을 때 파손돼 반품하려고 해요. 상품 가격과 별도로 낸 관세와 세금도 돌려받을 수 있나요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | SUFFICIENT | 검색된 EIS 약관을 근거로, 상품이 EIS로 배송 중 파손됐고 eBay Money Back Guarantee 청구가 승인되면 eBay에 낸 관세·세금도 환급받을 자격이 있을 수 있다고 요구할 수 있습니다. 다만 EIS 적용 여부, 파손 발생 구간과 청구 승인, 납부처가 확인되지 않았으며 DAP 반품은 원칙적으로 관세·세금 환급 대상이 아니므로 현재 사실만으로 환급을 단정할 수 없습니다. |
| 450/200 | SUFFICIENT | 도착 시 파손된 상품은 판매자의 반품 불가 정책과 무관하게 반품할 수 있다는 요구는 근거가 있습니다. 다만 관세·세금 환급은 EIS 여부, 납부처, 배송 구간 및 승인된 청구 여부에 따라 달라지며, EIS DAP 반품은 원칙적으로 환급되지 않고 예외가 있으므로 현재 사실만으로 환급을 단정할 수 없습니다. |

- 500/200 · 1. eBay International Shipping - Buyer Terms & Conditions (0.3807) / 2. Condition of returned items policy (0.3794) / 3. International purchases and shipping for buyers (0.3652) / 4. eBay International Shipping Seller Terms & Conditions (0.3647) / 5. eBay International Shipping - Buyer Terms & Conditions (0.3572)
- 450/200 · 1. eBay International Shipping - Buyer Terms & Conditions (0.4088) / 2. Return an item for a refund (0.3863) / 3. Condition of returned items policy (0.3859) / 4. eBay International Shipping Seller Terms & Conditions (0.3696) / 5. Return an item for a refund (0.3669)

#### 질문 17

> 예약 주문한 제품이 약속한 달이 지나도 출시되지 않았습니다. 판매자가 배송을 또 미루는데 주문 취소나 환불을 요청할 수 있나요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | SUFFICIENT | 판매자에게 취소를 요청할 수는 있지만 판매자가 수락 여부를 결정하며, 요청 가능 기간과 발송 여부에 제한이 있습니다. 취소나 전액 환불이 자동 보장되는 것은 아니며, 배송 예정일이 지난 뒤 미수령 신고 요건을 충족하면 판매자는 3영업일 내 배송 정보 또는 환불로 응답해야 합니다. |
| 450/200 | INSUFFICIENT | 취소를 요청할 수는 있지만 판매자가 수락하거나 거절할 수 있으므로, 약속한 달이 지나 배송이 미뤄졌다는 사실만으로 취소나 환불이 보장되지는 않습니다. 주문 후 30일 이내이고 발송 전인지, 이미 미도착 신고를 했는지 등이 확인되지 않았으며, 판매자가 취소하면 결제 경로에 따라 환불 방식이 다릅니다. |

- 500/200 · 1. Order cancellation policy (0.4150) / 2. Order cancellation policy (0.3935) / 3. Return an item for a refund (0.3431) / 4. Order cancellation policy (0.3402) / 5. eBay Money Back Guarantee policy (0.3355)
- 450/200 · 1. Order cancellation policy (0.4167) / 2. Order cancellation policy (0.3909) / 3. Order cancellation policy (0.3608) / 4. Get help if you bought as a guest (0.3473) / 5. Return an item for a refund (0.3315)

#### 질문 18

> 물건을 받기 전에 주소가 틀린 걸 알았지만 이미 발송됐어요. 올바른 주소로 보내 달라고 요청하거나 환불받을 수 있나요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | SUFFICIENT | 판매자에게 올바른 주소로 보내 달라고 요청하는 것은 가능하지만, 검색된 정책은 발송 후 취소나 주소 변경·재발송을 보장하지 않으며 원래 주소에서 수령해 보도록 권고합니다. 주소 오류만으로 환불받을 권리는 확인되지 않았고, 예상 배송일이 지난 뒤에도 상품이 미도착이면 신고할 수 있으며 판매자는 3영업일 내 배송 안내·교환 제안·환불을 제공해야 합니다. |
| 450/200 | SUFFICIENT | 판매자에게 주문 페이지에서 연락해 취소 가능 여부를 문의할 수 있지만, 이미 발송됐다면 취소할 수 없다고 안내되어 있고 가능하면 원래 주소에서 수령하는 것이 권장됩니다. 올바른 주소로 재배송이나 주소 오류만을 이유로 한 환불은 보장되지 않으며, 환불·반품 가능성은 판매자 반품 정책과 상품의 실제 문제 여부에 달려 있습니다. |

- 500/200 · 1. eBay Money Back Guarantee policy (0.3381) / 2. Get help if you bought as a guest (0.3334) / 3. Get help if you bought as a guest (0.3321) / 4. Return an item for a refund (0.3253) / 5. Get help if you bought as a guest (0.3237)
- 450/200 · 1. Return an item for a refund (0.3495) / 2. Get help if you bought as a guest (0.3445) / 3. Return an item for a refund (0.3344) / 4. Get help if you bought as a guest (0.3315) / 5. Get help if you bought as a guest (0.3288)

#### 질문 19

> 주문 가격이 900달러인 제품을 판매자에게 반송했고 배송은 완료로 나오지만 서명을 받지 않았어요. 환불 분쟁에서 추적번호만으로 충분한가요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | SUFFICIENT | 검색된 정책을 판매자에게 근거로 제시해 배송 완료 기록만으로는 충분하지 않을 수 있다고 요청할 수 있습니다. 반품비를 부담하는 구매자는 사이트별 기준에 따라 추적 가능한 배송과 주소·날짜 기록을 갖춰야 하며, eBay.com 구매 상품이 750달러 이상이거나 적용 기준을 넘으면 서명 확인이 요구됩니다. 다만 900달러가 세금 포함 가격인지, 구매 사이트와 반송비 부담 주체가 무엇인지 확인되지 않아 추적번호만으로 충분하다고 단정할 수 없습니다. |
| 450/200 | SUFFICIENT | 검색된 정책상 eBay.com에서 상품 총액이 750달러 이상이면 반송 시 서명 확인이 요구될 수 있고, 구매자가 반송비를 부담하는 경우에도 해당 기준 이상이면 서명 확인이 보호 요건입니다. 다만 구매 사이트, 상품 총액 계산과 반송비 부담 여부가 확인되지 않았으며, 배송 완료 추적만으로 환불 분쟁이 해결된다고 정책이 보장하지는 않습니다. |

- 500/200 · 1. eBay Money Back Guarantee policy (0.4979) / 2. Signature confirmation policy (0.4889) / 3. Signature confirmation policy (0.4610) / 4. eBay Money Back Guarantee policy (0.4501) / 5. eBay Money Back Guarantee policy (0.4348)
- 450/200 · 1. eBay Money Back Guarantee policy (0.4972) / 2. Signature confirmation policy (0.4824) / 3. Signature confirmation policy (0.4809) / 4. eBay Money Back Guarantee policy (0.4501) / 5. Signature confirmation policy (0.4416)

#### 질문 20

> 판매자가 플랫폼 밖에서 계좌이체로 일부 환불을 해주겠다고 합니다. 받아도 남은 금액에 대해 사이트에서 분쟁을 계속할 수 있나요?

| 설정 | 모델 판정 | 실제 searchNote |
|---|---|---|
| 500/200 | INSUFFICIENT | 정책 근거로는 판매자가 플랫폼에서 부분 환불을 제안할 수 있고, 3영업일 내 합의하지 못하면 당사자가 플랫폼 개입을 요청할 수 있다고 말할 수 있습니다. 다만 계좌이체로 일부 환불을 받은 뒤 남은 금액에 대한 분쟁을 계속할 수 있는지는 검색된 정책에 명시되어 있지 않으므로 결과를 보장할 수 없습니다. |
| 450/200 | INSUFFICIENT | 검색된 정책으로는 환불 기한까지 판매자가 환불하지 않거나 양측이 해결하지 못한 경우 eBay에 개입을 요청할 수 있고, 요청·사건을 자발적으로 닫으면 보호를 잃을 수 있다는 점을 근거로 들 수 있습니다. 그러나 플랫폼 밖 계좌이체로 일부 환불을 받아도 남은 금액의 분쟁을 계속할 수 있는지, 그 수령이나 사건 상태가 어떻게 처리되는지는 확인되지 않아 이를 보장한다고 단정할 수 없습니다. |

- 500/200 · 1. How to handle a return request as a seller (0.4184) / 2. eBay Money Back Guarantee policy (0.4165) / 3. How to handle a return request as a seller (0.4042) / 4. How to handle a return request as a seller (0.4020) / 5. How to handle a return request as a seller (0.3952)
- 450/200 · 1. eBay Money Back Guarantee policy (0.4023) / 2. eBay Money Back Guarantee policy (0.3869) / 3. Signature confirmation policy (0.3862) / 4. How to handle a return request as a seller (0.3801) / 5. How to handle a return request as a seller (0.3788)
