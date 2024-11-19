# MetroFlow
지하철 혼잡도 정보 제공 서비스
## 프로젝트 소개
- 지하철 혼잡도 정보를 제공하는 것을 목표로 한 프로젝트입니다.
- 공공 데이터 포털 API를 데이터를 이용해 혼잡도 예측값을 제공합니다.
### 혼잡도란?
![image](https://github.com/user-attachments/assets/2fe97487-0cdb-4048-947e-257875ea6b1b)

   ### 열차 내부 상황 그림 예시
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
- SpringBoot 3.3.4
- DB : MySQL
- Query : ORM(JPA, JPQL), MyBatis
- View : Thymeleaf
- Design tool : Figma
- IDE : VSCode, IntelliJ
- Version Control : Git
- 개발 일정 관리 : Git Project
- 협업 툴 : Notion, Discord

## 주요 기능
!! 현재 배포를 하지 않은 상태라서, 데이터가 들어가 있지 않아 로컬에서 실행이 불가능합니다.
### 메인화면 노선도
- JS 기반의 화면 조작
- 역 이름 클릭을 통한 혼잡도 정보 보기
<details>
   <summary>메인화면 시연</summary>
   <image src=""></image>
</details>
### 


