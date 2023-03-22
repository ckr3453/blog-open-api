# blog-open-api

## 외부 라이브러리 사용

- `io.springfox:springfox-swagger2:2.9.2`, `io.springfox:springfox-swagger-ui:2.9.2`
  - api 테스트를 위해 swagger ui 사용

- `org.apache.httpcomponents:httpclient`
  - 외부 통신용 RestTemplate에 커넥션 풀 설정을 위해 사용
  
- `com.fasterxml.uuid:java-uuid-generator:4.0.1`
  - 대규모 트래픽에 대비하여 엔티티 생성시 id 값을 GeneratedValue가 아닌 uuid를 조합한 값을 사용 
  
## jar 파일 다운로드 링크

둘중 하나 선택해서 다운로드 진행

[search-0.0.1-SNAPSHOT.jar (깃허브)](https://github.com/ckr3453/blog-open-api/blob/master/jar/search-0.0.1-SNAPSHOT.jar)<br/>
[search-0.0.1-SNAPSHOT.jar (구글드라이브)](https://drive.google.com/file/d/18RRfnvXDl77kP0Ql9at0M_-zoY4FVfH4/view?usp=sharing)
  
## API 명세

### 블로그 검색하기

```
GET /v1/search/blog HTTP/1.1
```

카카오에서 제공하는 오픈 API를 통해 블로그를 검색합니다. 만약 카카오 api에 문제가 생겨 블로그 검색에 실패했을 경우 네이버가 제공하는 오픈 api로 블로그를 목록을 검색합니다.

#### Request

|Name|Type|Description|Required|
|---|---|---|---|
|query|String|검색을 원하는 질의어입니다.|O|
|page|String|결과 페이지 번호입니다. 1~50 사이의 값을 넣어야하며, 기본값은 1 입니다.|X|
|size|String|한 페이지에 보여질 숫자입니다. 1~50 사이의 값을 넣어야하며, 기본값은 10 입니다.|X|
|sort|String|결과 문서 정렬 방식입니다. accuracy(정확도순) 또는 recency(최신순)이며, 기본 값은 accuracy 입니다.|X|

#### Response

|Name|Type|Description|
|---|---|---|
|page|Integer|결과 페이지 번호입니다.|
|size|Integer|한 페이지에 보여질 숫자입니다.|
|sort|String|결과 문서 정렬 방식입니다. accuracy(정확도순) 또는 recency(최신순) 입니다.|
|blogs|List|블로그 검색 결과 리스트입니다. 내부 파라미터는 아래에서 추가적으로 설명합니다.|

- blogs

|Name|Type|Description|
|---|---|---|
|title|String|블로그 글 제목입니다.|
|url|String|블로그 글 URL 입니다.|
|contents|String|블로그 글 요약문 입니다.|
|blogName|String|블로그의 이름입니다.|
|postDate|String|블로그 글 작성날짜입니다. (YYYYMMDD 형태로 제공합니다.)|

#### Example

- 요청예

```
curl -v -X GET "http://localhost:8080/v1/search/blog?query=hi"
```

- 응답예

```json
{
    "page": 1,
    "size": 10,
    "sort": "accuracy",
    "total": 807216,
    "blogs": [
        {
            "title": "케이뱅크 <b>Hi</b> teen 계좌, 카드 개설기",
            "url": "http://dgb12.tistory.com/41",
            "contents": "대해서 알아봤다. 원래 케이뱅크는 만 17세 이상 주민등록증 소유자만 계좌 개설이 가능했다. 그러나, 작년 12월 즈음 만 14세~만 18세를 대상으로 한 <b>Hi</b> teen 이라는 이름의 선불전자지급수단(가상계좌)을 런칭했다. 많은 청소년들이 사용하는 카카오뱅크 미니, 리브 Next, 토스유스 등과 같은 방식이다. 휴대폰 번호만...",
            "blogName": "두곰의 글적글적",
            "postDate": "20230217"
        },...
    ]
}
```

### 인기 검색어 목록

```
GET /v1/search/keyword HTTP/1.1
```

[블로그 검색하기 api](#블로그-검색하기)를 통해 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 제공합니다.

#### Response

|Name|Type|Description|
|---|---|---|
|keywords|List|인기 검색어 목록 결과 리스트입니다. 내부 파라미터는 아래에서 설명합니다.|

- keywords

|Name|Type|Description|
|---|---|---|
|keyword|String|검색한 키워드입니다.|
|hits|String|해당 키워드의 검색 횟수 입니다.|

#### Example

- 요청예

```
curl -v -X GET "http://localhost:8080/v1/search/keyword"
```

- 응답예

```json
{
    "keywords": [
        {
            "keyword": "날씨",
            "hits": 22
        },
        {
            "keyword": "벚꽃",
            "hits": 14
        },
        {
            "keyword": "봄축제",
            "hits": 13
        },...
    ]
}
```
