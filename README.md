# MetroFlow
지하철 혼잡도 정보 제공 서비스
## 프로젝트 소개
- 지하철 혼잡도 정보를 제공하는 것을 목표로 한 프로젝트입니다.
- 공공 데이터 포털 API를 데이터를 이용해 혼잡도 예측값을 제공합니다.
- 트러블 슈팅 기록 등 상세한 내용은 아래 링크 노션 페이지에 있습니다.   
<a href="https://cuddly-echo-750.notion.site/MetroFlow-e32cab7486ed41f38623de66042df6c5?pvs=74">MetroFlow Project</a>
### 혼잡도란?
![image](https://github.com/user-attachments/assets/2fe97487-0cdb-4048-947e-257875ea6b1b)

   ### 열차 내부 상황 예시
<details>
    <summary>여유</summary>
    <image src="https://github.com/user-attachments/assets/0c339d5d-f518-41f0-9836-e96d1b9c5875"></image>
</details>
<details>
    <summary>혼잡</summary>
    <image src="https://github.com/user-attachments/assets/b1620c37-70fa-4dae-9663-54dfa49443a6"></image>
</details>
<details>
    <summary>아침 출근길 급행 열차(혼잡도 120 이상)</summary>
    <image src="https://github.com/user-attachments/assets/f647a718-14e4-4e61-af23-f04ebf61da1f"></image>
</details>

## 작업 기간
- 2024.09.23 ~ 10.31 (약 1개월)
- API 데이터 수집/전처리, 필요한 기술 스택 학습 기간 포함
<details>
   <summary>일정 요약</summary>
   <image src="https://github.com/user-attachments/assets/a479d549-c270-4632-8f1a-b1a9dbf7e98c"></image>
</details>

## 팀 구성
총 5명(프론트+백 4명, 디자인 총괄 1명)

## 담당 업무
- 프로젝트 기획, 작업 배분, 일정 관리
- 메인 트러블 슈팅 담당
- 혼잡도 데이터 전처리
- SVG 기반 지하철 노선도 구현
- 메인화면, 즐겨찾기 기능 개발
- 혼잡도 데이터 API 처리 구현
- Thymleaf + JavaScript를 활용한 AJAX 기반의 view 제작

## 개발 환경
- Java openjdk 17.0.0.1
- SpringBoot 3.3.4, Spring Security 사용
- DB : MySQL
- Query : ORM(JPA, JPQL), MyBatis
- View 구현 : Thymeleaf, JavaScript
- Design tool : Figma
- IDE : VSCode, IntelliJ
- Version Control : Git
- 개발 일정 관리 : Git Project
- 협업 툴 : Notion, Discord

## 주요 기능
!! 현재 배포를 하지 않은 상태라서, 내부 데이터가 없기에 로컬에서 실행이 불가능합니다 !!   
!! Docker 이미지 제작이 완료되면 배포할 예정입니다 !!

### 메인화면 노선도
- JS 기반의 노선도 화면 조작(확대, 축소, 드래그 이동 등)
- 역 이름 클릭을 통해 혼잡도 정보를 제공하는 모달에 접근 가능
- 모달의 별 버튼을 통해 즐겨찾기 기능에 접근이 가능하지만, 로그인 상태의 이용자에게만 해당 요소가 렌더링됩니다.
<details>
   <summary>메인화면 시연</summary>
   <image src=""></image>
</details>

### 역 검색
- 특정 역의 혼잡도를 검색할 수 있는 기능
- JS 기반의 노선도 화면 조작
- JavaScript, JQuery 기반으로 AJAX 처리
<details>
   <summary>검색 시연</summary>
   <image src=""></image>
</details>

### 즐겨찾기
- 이용자가 즐겨찾기로 등록한 역들의 혼잡도 정보를 제공
- 특정 시점(시간 or 날짜)의 혼잡도 정보를 선택해서 볼 수 있음
<details>
   <summary>즐겨찾기 시연</summary>
   <image src=""></image>
</details>

### 회원 관련 기능
Spring Annotation, Spring Security를 이용해 예외처리 수행
- 로그인   
- 회원 가입
- 내 정보 관리
<details>
   <summary>회원 기능 시연</summary>
   <image src=""></image>
</details>

### 관리자 기능
Spring Security를 이용해 권한 처리, 관리자만 접근이 가능하도록 설정
- 전체 이용자 조회
<details>
   <summary>관리자 기능 시연</summary>
   <image src=""></image>
</details>


