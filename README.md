# selectshop
네이버쇼핑 api를 사용해 검색한 상품을 관심상품으로 등록하고 보여주는 서비스입니다.

관심상품 등록시 희망최저가를 입력하여 최저가이면 최저가 Label이 등록된 상품에 표시하고

폴더를 만들어 상품들을 폴더별로 관리할 수 있습니다.

# 📜기술스택

**HTML5, CSS, JavaScript, Jquery, Thmeleaf,**

**Spring, SpringBoot, Spring Data JPA, Spring Security, Auth2 kakao, h2**

**naver shopping API**

# 🎞️화면 캡처


![selecshop-login](https://user-images.githubusercontent.com/69257201/172308199-b7bf37f8-a3b5-457f-8f80-70f1acdfaf8c.png)
로그인화면

![탐색](https://user-images.githubusercontent.com/69257201/172309155-991ab346-731a-4101-953d-a7c6c458d398.png)
탐색하기 화면


![main](https://user-images.githubusercontent.com/69257201/172309186-9678e45a-f5cc-4c70-9545-7a66ef1a0083.png)
메인화면


![폴더추가](https://user-images.githubusercontent.com/69257201/172309205-6a024a42-cbb6-4f68-84f9-bd2cc8ba314b.png)
폴더 추가화면


![추가상태](https://user-images.githubusercontent.com/69257201/172308370-0450bad0-6605-4179-bca2-45400c12a309.png)
폴더생성 및 상품폴더 등록화면


# DB테이블 - User, Product, Folder

| Product |
| --- |
| id(Long) |
| title(String) |
| image(Stirng) |
| link(String) |
| lprice(int) |
| myprice(int) |
| userId(Long) |
| folderList(list) |

![user-folder has-a](https://user-images.githubusercontent.com/69257201/172309412-e94bc97c-2a80-429f-af78-180d57e33d53.png)


# 🧾주요 기능

- 네이버 쇼핑 API 검색기능
- 관심 상품 등록기능
- 관심상품의 희망 최저가 등록, 업데이트기능
- 관심상품조회(회원용, 관리자용- 권한설정)
- 로그인  - **'**BCrypt 해시함수**'**를 사용해 패스워드를 암호화(암호화알고리즘을 빈으로 등록)
- OAuth 카카오로그인 기능
- 로그아웃
- 페이징
- 폴더 생성(동시 여러개 가능)
- 폴더 전체 조회
- 폴더별 관심상품 조회
- 상품 조회시 해당상품에 폴더 정보 추가

- Top5회원 찾기 (미구현)

# 🔃API설계

[셀렉샵 API](https://www.notion.so/f7bb1ca7662641099fac1f60cc2e343b)
