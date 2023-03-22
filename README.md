# ✨블로그 검색 서비스
### Description
검색어로 카카오와, 네이버 블로그를 검색해보자

<br>

## 개발환경
- JAVA11
- Spring-boot
- Gradle
- H2, JPA
- Redis

## 추가 라이브러리
- WEBFLUX
  - WebClient 사용을 위해 추가했습니다.
- Redis
  - 동시성 제어를 위해 추가했습니다.

### HOST

```sh
#HOST : localhost:8080
```

## ✨ 결과 OUTPUT JAR 다운 링크

```sh
    java -jar devtel-boot-blog.jar
```
-  git JAR 경로 링크
  - https://github.com/tel407/blog_search_devtel/blob/5c3a723d279d8d3eb60c5af450967814c13d8dd7/jar-output/devtel-boot-blog.jar

-  git JAR 다운 링크
  - https://github.com/tel407/blog_search_devtel/raw/master/jar-output/devtel-boot-blog.jar
 
- Google JAR 다운 링크
- https://drive.google.com/file/d/1eCqtVJm4NlBFtdK7SZDeJAShPW4hxhiu/view?usp=share_link


## ✨구현항목 
- [블로그 검색] 키워드를 통해 블로그를 검색할 수 있어야 합니다.
  - 카카오 블로그 검색 API를 Default로 사용했습니다.
  - vender 구분값을 추가해 원하는 검색 API 를 우선 연결하도록 하였습니다
  - 검색 키워드를 JPA를 활용하여 h2 디비에 저장하였습니다.
- [블로그 검색] 검색 결과에서 Sorting(정확도순, 최신순) 기능을 지원해야 합니다.
- [블로그 검색] 검색 결과는 Pagination 형태로 제공해야 합니다.
  - 카카오,네이버 API의 페이징 값이 상이함을 인지하고 구현하였습니다.
- [블로그 검색] 추후 카카오 API 이외에 새로운 검색 소스가 추가될 수 있음을 고려해야 합니다.
  - Sort상수 Vender상수 등 Enum 으로 상수를 관리해 카카오 및 네이버 등 관리에 용의하게 구현했습니다.
  - Interface 통해 유연, 확장에 용의하게 구현했습니다.
- [인기검색어 목록] 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 제공합니다.
- [인기검색어 목록] 검색어 별로 검색된 횟수도 함께 표기해 주세요.
- [추가] 멀티 모듈 구성 및 모듈간 의존성 제약
  - boot-blog[어플리케이션 모듈]
     - {blogSearchApplication} ,
  - boot-api [API 모듈] 
    - {API} , {Service}, { @TEST #API결과검증}
  - data-blog [DATA 모듈] 
    - {Domain}, {Repository}, { @TEST #동시성 검증}
  - 로 모듈을 나눠 의존성 제약을 두고 의존도를 낮춰 개발하였습니다.
  - @TEST는 각 모듈에서 각각 진행하였습니다.
- [추가] 동시성 이슈가 발생할 수 있는 부분을 염두에 둔 구현 (예시. 키워드 별로 검색된 횟수의 정확도)
  - 동시성을 제어하기 위해 싱글스레드인 Redis 를 이용하였스며 Redis 의 내부적으로 synchronizer.invoke() 하는 increment 를 사용했습니다.
  - 아쉬운 점은 Redis 데이터를 backup 및 동기화 관리를 완성못해 아쉽게나 검색어 조회시 log성 데이터가 DB에 저장되도록 구현했습니다.
- [추가] 카카오 블로그 검색 API에 장애가 발생한 경우, 네이버 블로그 검색 API를 통해 데이터 제공
   - 쓰고자하는 엔진의 순서를 두어 검색 API RequestPrame 중 하나인 vender 및 기본값 이 가장 처음으로 찾고 장애시 Enum 의 순서로 순회하며 연결하도록 구현했습니다.
   - 모든 vender가 실패시 모든 API 실패로 인한 검색 실패를 반환합니다.



<br>
<br>
<br>

## ✨_API 명세서_

### HOST

```sh
#HOST : localhost:8080
```
<br>

### 작업1 : 키워드를 통해 블로그를 검색 
___
```sh
`GET` /v1/api/blog/search
```
```sh
`GET` http://localhost:8080/v1/api/blog/search?keyword=집짓기&vender=kakao&page=2&size=15
```

#### Request  

*#Parameter*  

|Name|Type|Description|Default|Required|
|---|---|---|---|---|
|keyword|String|검색을 원하는 질의어||O|
|sort|String|정렬방식, accuracy(정확도순), recency(최신순)|accuracy|X|
|page|Integer|결과 페이지 번호, 1~50 사이의 값|1|X|
|size|Integer|한 페이지에 보여질 문서 수, 1~50 사이의 값|10|X|
|vender|String|사용하고자 하는 검색 업체[카카오],[네이버] |kakao|X|

<br>

#### Response  

|Name|Type|Description|
|---|---|---|
|totalCount|Integer|검색된 문서 수|
|pageNum|Integer|현재페이지 번호|
|pageSize|Integer|한 페이지에 보여지는 문서 수|
|pageEnd|Boolean|현재 페이지가 마지막 페이지인지 여부|
|blogItemList|List| 블로그 포스팅 정보 배열|

*#blogItemList / 공통 블로그 포스팅 정보 [List]*

|Name|Type|Description|
|---|---|---|
|title|String|블로그 글 제목|
|contents|String|블로그 글 요약|
|url|String|블로그 글 URL|
|blogname|String|블로그의 이름|
|thumbnail|String|검색 시스템에서 추출한 대표 미리보기 이미지 URL|
|datetime|String|블로그 글 작성시간|

<br>

### 작업1 : Sample
#### Request
```sh
`GET` http://localhost:8080/v1/api/blog/search?keyword=집짓기&vender=kakao&page=2&size=15
```

<br>

#### Response
```shell
HTTP/1.1 200
Content-Type: application/json
{
    "status": "Success",
    "data": {
        "totalCount": 788,
        "pageNum": 2,
        "pageSize": 15,
        "pageEnd": false,
        "blogItemList": [
            {
                "title": "단독주택 <b>집</b><b>짓기....법",
                "contents": "한다고 말씀 주셨답니다. 인터넷 상에도 마루...",
                "url": "http://odaily.tistory.com/1193",
                "blogname": "🌷일상을 공유합니다",
                "thumbnail": "https://search3.kakaocdn.net/argon/130x130_85_c/B4pjonEuhxm",
                "datetime": "2023-03-20T22:17:59.000+09:00"
            },
            ....
        ]
    }
}
```

<br>

### 작업2 : 인기 검색어 목록
___


```sh
`GET` /v1/api/blog/keyword/popular
```
```sh
`GET` http://localhost:8080/v1/api/blog/keyword/popular
```
### Response  

*#인기검색어 Top10 [List]*

|Name|Type|Description|
|---|---|---|
|keyword|String|인기검색어|
|score|Integer|누적 검색수|

<br>

### 작업2 : Sample
#### Request
```sh
`GET` http://localhost:8080/v1/api/blog/keyword/popular
```

<br>

#### Response
```shell
HTTP/1.1 200
Content-Type: application/json
{
    "status": "Success",
    "data": [
        {
            "keyword": "책",
            "score": 9
        },
        ...
    ]
}
```
___
