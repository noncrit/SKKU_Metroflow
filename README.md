# SKKU_Metroflow
## 프로젝트 소개

- 사용자에게 지하철 혼잡도 정보를 제공하는 서비스
- 스냅샷
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d2918bca-e401-4846-a929-c5a67e5469f0/6f129d0c-305c-490d-b566-837f39699b24/image.png)
    

### 혼잡도???

- 텍스트 설명
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d2918bca-e401-4846-a929-c5a67e5469f0/1e065db0-4698-4bb8-8459-56b5353f1e70/image.png)
    
- 그림 예시
    - 여유
        
        ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d2918bca-e401-4846-a929-c5a67e5469f0/d6f092a1-0530-4ddc-96f9-812322894139/image.png)
        
    - 혼잡
        
        ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d2918bca-e401-4846-a929-c5a67e5469f0/d88bd155-7858-41fa-b55b-6073ec7820ef/image.png)
        
    - 아침 급행 열차
        
        ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d2918bca-e401-4846-a929-c5a67e5469f0/6ffb0cfd-ad9c-440c-a311-a98300659929/image.png)
        

## 팀 구성

총 5명 ( 프론트+백 4명, 디자인 총괄 1명)

## 작업 기간

2024.09.23 ~ 10.31 (약 1개월)

### 담당 업무

- PM(프로젝트 기획, 작업 배분, 일정 관리)
- 트러블슈팅 담당
- 프론트 + 백엔드 작업
- 혼잡도 데이터 전 처리 담당
- SVG 기반 지하철 노선도 기능 개발, 즐겨찾기 기능 개발
- `Spring Data JPA`(+`JPQL`),  `Mybatis`를 활용한 ORM 처리 구현
- `Thymeleaf`, `JavaScript` 기반으로 
서버 사이드 렌더링 + AJAX 처리가 가능한 페이지 제작

## 프로젝트 기획

### 초기 기획

- Spring Boot 기반으로 서버를 구축
- Spring Secuirty 기반 권한 관리
- Java에서 지원하는 ORM을 적극적으로 활용
- 렌더링 엔진 - Thymeleaf 선정 - Spring과의 연계성 학습 목적
- Figma 기반으로 초기 기획 진행
    - 실행 흐름, 페이지의 UI/UX 디자인 설계
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d2918bca-e401-4846-a929-c5a67e5469f0/8c0603ea-7a5d-481a-a46b-e3703dd1708c/image.png)
    
- 이후 Use Case 별 실행 흐름 구분
    - Anonymous User
        
        ![AnonymousUser.PNG](https://prod-files-secure.s3.us-west-2.amazonaws.com/d2918bca-e401-4846-a929-c5a67e5469f0/a6cf8a1b-a617-4a3a-869e-4339ba392df2/AnonymousUser.png)
        
    - Authenticated User
        
        ![AuthenticatedUser.PNG](https://prod-files-secure.s3.us-west-2.amazonaws.com/d2918bca-e401-4846-a929-c5a67e5469f0/1b6514e7-ebca-42e0-9346-0678daccb8ad/AuthenticatedUser.png)
        
    - Admin
        
        ![admin.PNG](https://prod-files-secure.s3.us-west-2.amazonaws.com/d2918bca-e401-4846-a929-c5a67e5469f0/fe836894-8b47-49df-ac2e-6110c5bb2274/admin.png)
        
- 각종 Convention 설정
    - Coding Convention
        
        ## 변수명 대**원칙**
        
        - **카멜 케이스(낙타체 기본 사용)**
        
        ```java
        public class **someDataObject**{
        	Data ajaxData;
        }
        ```
        
        - **메소드 : 동사형**
            - 되도록 어떤 기능을 할 지 메소드 이름에서 예상을 할 수 있도록 설정하기
        
        ```java
        Boolean isDuplicate(){
        ......
        	return true;	
        }
        
        List<someDTO> getSomeList(){
        	.....
        	return List<somtDTO> = elseDTO.getElseDTO(something);
        }
        
        ```
        
        - **변수 : 명사(역할을 알아볼 수 있는 형태)**
            - int a, int x ⇒ loop 카운터 외의 형태로 사용한 것이 보이면 뺨 때려주기
        
        - **if 문 형태**
            - 이 형태를 지키려다 코드 직관성이 떨어지는 경우 → 적당히 형태 바꾸기
            
            ```java
            // Normal if
            if(someCondition){
            	....
            }
            
            // if else
            if(otherCondition){
            	....
            } else {
            	
            } else {
            	...
            }
            
            ```
            
    - DB Naming Convention
        
        ## **1차 DB 네이밍 컨벤션**
        
        - [**[데이터베이스] Database 네이밍 룰 (tistory.com)**](https://12bme.tistory.com/246)
        - **위와 같은 방식으로 하면 JPA를 사용할 때 _가 REPOSITORY에서 인식을 못하기때문에
        2차 DB 네이밍 컨벤션을 사용하기로 함**
        - **또한 JPA에서 엔티티를 생성할 때 @JoinColumn의 name속성에서도 _를 사용하면 인식을 못함**
        
        ## **2차 DB 네이밍 컨벤션**
        
        - **의미는 명확하게 생성**
        - **명사형으로 생성**
        - **_대신에 대문자를 사용하기로 함
        ex) user_id ⇒ userId**
        - _TB, _NM등을 사용하지 않기로 함(_를 사용해야 하기 때문)
        

### 백엔드 사용 스택

- `Spring Boot`
    - 기존 학습했던 Spring MVC와의 차이점을 학습해보고자 선정
- `Spring Security`
    - Session/Cookie 기반 권한 관리로는 보안 처리가 어려울 것 같아
    요청에 대한 필터 처리가 가능한 Spring Security를 사용해보기로 결정
- `MySQL`
    - Oracle 환경과는 어떤 차이점이 있는지 학습하기 위해 채택
- `Spring Data JPA`, `JPQL`, `MyBatis`
    - Dialect -  ORM 관계를 통한 추상적 DB 접근을 사용해보기 위한 목적으로 JPA 사용
    - JPA로 처리하기 까다로운 쿼리는 JPQL을 이용해 처리
    - MyBatis는 JPA와 차이점을 알아보기 위해 일부 서비스에만 사용
    → 쿼리의 결과 면에서는 차이가 없지만, MyBatis는 쿼리 자체에 의존성이 생김
    (Dialect를 통해 사용 DB를 자유롭게 바꾸는 게 불가능)
    → 객체 지향 관점에서는 Dialect - ORM 구조를 활용하는 것이 바람직해 보임

### 프론트엔드 사용 스택

- `Html & CSS`, `Thymeleaf`, `JavaScript`, `Figma`

### 형상 관리

- 버전 관리 : `Git`, `Github`
- 작업 일정 관리 : `Git Project`
- 회의 환경 : `Discord`
- 팀 단위 문서 공유 :  `Notion`
- Git Project - 일정, 이슈 관리
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d2918bca-e401-4846-a929-c5a67e5469f0/c17aeaa0-d5db-465f-b96a-1c9f30a8b036/image.png)
    
- 일정 요약본 - 간트 차트
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d2918bca-e401-4846-a929-c5a67e5469f0/b317bf09-0679-4ef7-bde2-aea48b32d1e3/image.png)
    

### IDE

- `VS Code`
- `IntelliJ`
- 의존성 관리 이슈로 `VS Code` → `IntelliJ` 로 변경

## 작업한 기능 요약 설명

- 혼잡도 데이터 전 처리
    - 1~9 호선 서울 지하철 혼잡도 데이터 DB 작업을 위한 전처리 작업
    - 급행 열차 처리 관련해서 가중치 작업이 필요함
        - 1~8 호선은 일반, 급행 열차 혼잡도가 합쳐진 데이터
        - 9호선은 일반열차와 급행 열차가 분리된 데이터
        - 9호선도 1~8호선 처럼 데이터 통합이 필요함
        - 일반 평균으로 계산하기엔 급행 쪽이 평균적으로 혼잡도가 너무 높음
        - 가중 평균으로 급행쪽에 가중치를 주고 계산 예정
        - 데이터 검증 결과
            - 평균적으로 특정 시간대 급행 쪽이 극단적으로 혼잡도가 높은 경우가 대부분
            - 역보정으로 일반 열차쪽에 1 미만의 가중치를 주는 경우
            일반 열차 혼잡도가 너무 작은 경우가 많아서 평균에 영향이 미미했음
            - 그래서 급행 열차 쪽에 1보다 큰 가중치를 주기로 결정함
            - 1 ~ 2 사이의 가중치를 테스트 해본 결과
            1.5 정도 가중치면 급행 혼잡도의 80~90% 정도까지 평균에 반영이 가능했음
            - 단, 급행 혼잡도가 0 인 경우는 그냥 평균으로 계산되는데,
            이 경우는 일반 열차 혼잡도 역시 10 미만의 낮은 값이기에 넘어가기로 결정
        - 엑셀로 데이터 처리하고 csv 파일로 포맷 변환해서 DB에 넣을 예정
        - column 이름에 ‘:’ 사용 금지, 모두 영어로 변환
        
    - 1~9호선 평일 데이터는 01:00까지, 휴일은 00:00까지 존재함
        - 평일, 휴일 테이블을 다르게 사용해야 함
    - 교통 공사 1~8 호선 데이터는 9호선 데이터와 포맷이 다름
        - 테이블 일관성을 위해 양식 통일
        - 1 ~ 9 호선 각각 분리
        - n호선 상행(평일), n호선 하행(평일), n호선 상행(휴일), n호선 하행(휴일)
        - 역이름, h0530, h0600 ….
        이 형태로 column 이름 통일
- 노선도
    - svg 파일 형식 노선도를 html 내부에 하드 코딩 후 Fragment로 활용
    - 노선도 영역에서만 작동하는 자바 스크립트로 사용자 조작 처리
    - 드래그를 통한 화면 이동, 우측의 리셋, 확대, 축소 모두 JS를 통해 직접 구현
    - 역 이름을 클릭하면 해당 역의 현재 시간 기준 혼잡도를 표기하는 모달 출력
    - AJAX 처리로 한 페이지에서 서비스 구동
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d2918bca-e401-4846-a929-c5a67e5469f0/ed7426f9-8faf-499e-8432-48472088011e/image.png)
    
- 즐겨 찾기
    - 로그인 한 사용자 대상으로만 작동하는 기능
    - 노선도 서비스에서 로그인한 이용자만 표시되는 UI를 이용해 즐겨찾기 등록/삭제 가능
    (Spring Security + Thymeleaf 를 이용한 동적 렌더링)
    UI를 통해 1번, Spring Security의 filter를 이용해 1번, 총 2번의 권한 검증을 거침
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d2918bca-e401-4846-a929-c5a67e5469f0/114f88ce-a92e-4dc2-9791-4529100515e5/image.png)
    
    - 즐겨찾기로 등록한 역에 대해 특정 날짜, 시간을 지정해 혼잡도를 볼 수 있도록 구현
    - Spring Security를 이용해 유효한 사용자인지 검증
    - Spring Pageable을 이용한 페이징 처리 구현 - 한 화면에 8개의 역 데이터 표시
    - AJAX 처리로 한 페이지에서 서비스 구동
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d2918bca-e401-4846-a929-c5a67e5469f0/63245b79-d704-425c-a176-282bb488f64d/image.png)
    

## 트러블 슈팅 기록

- **처리가 까다로웠던 요소만 따로 정리**
- 리다이렉트 요청 무한 루프 현상
    - 스프링 3.3.3 , 자바 openjdk 17 버전 환경
    - Controller로 구현 시 리다이렉트 루프가 무한으로 돌지만
    - `WebMvcConfigurer` 의 `ViewControllerRegistry` 로 구현하면 정상적으로 돌아가는 현상
    - View 구현은 모두 제대로 되어있음
    - SecurityConfig
        
        ```java
        package com.example.kim.practice.config;
        
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.core.userdetails.User;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.security.provisioning.InMemoryUserDetailsManager;
        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
        import org.springframework.security.crypto.password.PasswordEncoder;
        import org.springframework.security.web.SecurityFilterChain;
        
        @Configuration
        @EnableWebSecurity
        public class SecurityConfig {
        
            @Bean
            public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                        .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/", "/home").permitAll()  // 홈과 루트는 인증 없이 접근 가능
                                .requestMatchers("/login").permitAll()
                                .anyRequest().authenticated()  // 나머지 모든 요청은 인증 필요
                        )
                        .formLogin(formLogin -> formLogin
                                .loginPage("/login")
                                .permitAll()  // 로그인 페이지는 인증 없이 접근 가능
                        )
                        .logout(logout -> logout
                                .permitAll()  // 로그아웃 페이지는 인증 없이 접근 가능
                        );
                return http.build();
            }
        
            @Bean
            public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
            }
        
            @Bean
            public UserDetailsService userDetailsService() {
                InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
                manager.createUser(User.withUsername("user")
                        .password(passwordEncoder().encode("password"))
                        .roles("USER")
                        .build());
                return manager;
            }
        }
        ```
        
    - UserController
        
        ```java
        package com.example.kim.practice.controller;
        
        import org.springframework.web.bind.annotation.GetMapping;
        
        public class UserController {
        
            @GetMapping("/")
            public String home() {
                return "home";
            }
        
            @GetMapping("/hello")
            public String homePage() {
                return "hello";
            }
        
            @GetMapping("/login")
            public String login() {
                return "login";
            }
        
        }
        ```
        
    - MvcConfig
        
        ```java
        package com.example.kim.practice.config;
        
        import org.springframework.context.annotation.Configuration;
        import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
        import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
        
        @Configuration
        public class MvcConfig implements WebMvcConfigurer {
        
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/home").setViewName("home");
                registry.addViewController("/").setViewName("home");
                registry.addViewController("/hello").setViewName("hello");
                registry.addViewController("/login").setViewName("login");
            }
        
        }
        ```
        
    
    - 생각한 가설들
        - Deprecated 된 버전을 참고한 것 아닌지 찾아봄 → 아님, 다 최신 문서 기준 구현임
        - WebSecure 문제? → 그렇다면 `WebMvcConfigurer` 로도 작동이 불가능해야함
    
    해결
    
    - Controller에 @Controller annotation이 없었음…
        
        ```java
        package com.example.kim.practice.controller;
        
        import org.springframework.web.bind.annotation.GetMapping;
        
        //**????????? 있어야 될게 없다...**
        public class UserController {
        
            @GetMapping("/")
            public String home() {
                return "home";
            }
        
            @GetMapping("/hello")
            public String homePage() {
                return "hello";
            }
        
            @GetMapping("/login")
            public String login() {
                return "login";
            }
        
        }
        ```
        
    
    - 리다이렉트 루프가 발생한 과정 분석
        - Spring Security는 인증이 필요한 다른 경로로 요청이 들어올 경우 
        자동으로 로그인 페이지로 리다이렉트를 진행하는데 
        기본 로그인 페이지 접근 URL은 /login 으로 설정되어있다.
        - `SecurityConfig`에서 `/`, `/home`,`/login`  경로는 `requestMatchers` 설정으로 인증 없이 접근할 수 있도록 설정되어 있다.
        - Security 필터 입장에서는 `/`, `/home`,`/login` 에 대해서는 명시적으로
        인증없는 접근이 가능하도록 설정했으니 당연히 이를 허용한다.
        - 하지만 실제로는 @Controller annotation이 누락되어 있어
        해당 경로를 처리할 수 있는 컨트롤러가 전혀 없는 상황이다.
        당연히 요청도 실패한다.
        - Spring은 기본적으로 실패한 요청을 처리할 수 없으므로, 
        보안 설정에 따라 인증을 시도하고, `/login` 페이지로 다시 리다이렉트한다.
        - Spring Security는 이 `/login` 경로로 리다이렉트하지만, `/login` 경로를 처리할 수 있는 컨트롤러가 없으므로 요청은 다시 `/login`으로 리다이렉트되고, 이 과정이 반복되어 무한 리다이렉트 루프가 발생…
        - 즉, 의도치 않게 `requestMatchers`  매핑 선언으로 Spring Security를 속인 셈…
- lombok - jackson 라이브러리 사용 시 변수명 변환 문제
    - 이 DTO 클래스를 이용해 컨트롤러에서 ResponseEntity를 이용해
    클라이언트 측에 json을 응답으로 돌려주는 구현
    
    ```java
    package com.metroflow.model.dto;
    
    import lombok.Getter;
    import lombok.Setter;
    
    import java.util.List;
    @Getter
    @Setter
    public class StationInfoResponse {
        private List<SubwayMapInfo> stationInfoList;
        private boolean isFavorite;
    
        // 생성자
        public StationInfoResponse(List<SubwayMapInfo> stationInfoList, boolean isFavorite) {
            this.stationInfoList = stationInfoList;
            this.isFavorite = isFavorite;
        }
    
    }
    ```
    
    - 컨트롤러 코드
        
        ```java
        package com.metroflow.controller;
        
        import com.metroflow.model.dto.StationInfoResponse;
        import com.metroflow.model.dto.SubwayMapInfo;
        import com.metroflow.model.dto.User;
        import com.metroflow.model.service.FavoriteListService;
        import com.metroflow.model.service.SubwayMapService;
        import com.metroflow.model.service.UserService;
        import lombok.RequiredArgsConstructor;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        
        import java.util.List;
        
        @Controller
        @RequiredArgsConstructor
        public class StationInfoController {
        
            @Autowired
            private SubwayMapService subwayMapService;
        
            private final UserService USERSERVICE;
        
            private final FavoriteListService FavoriteListService;
        
            private static final Logger logger = LoggerFactory.getLogger(StationInfoController.class);
            @Autowired
            private UserService userService;
        
            @GetMapping("/station-info")
            public ResponseEntity<StationInfoResponse> getStationInfo(@RequestParam String stationName, Model model) {
                List<SubwayMapInfo> stationInfoList = subwayMapService.getSubwayMapInfo(stationName);
                logger.info("stationInfoList: {}", stationInfoList);
        
                User user = USERSERVICE.getUserObject();
                boolean isFavorite = false;
        
                // 즐겨찾기 등록 유무 판단을 위한 변수
                if( user != null ){
        //            System.out.println("유저 객체 정보 : " + user.getUserId());
                    model.addAttribute("sessionUser", user);
                    isFavorite = FavoriteListService.isFavorite(user.getUserId(), stationName);
                    System.out.println("boolean value : " + isFavorite);
                }
        
                // StationInfoResponse 객체 생성
                StationInfoResponse response = new StationInfoResponse(stationInfoList, isFavorite);
        
                if (!stationInfoList.isEmpty()) {
                    return ResponseEntity.ok(response); // 전체 리스트를 JSON 형태로 반환
                }
                return ResponseEntity.notFound().build();
            }
        }
        
        ```
        
    - 응답받은 측에서 json으로 true, false값을 불러오지 못하길래
    직접 request요청을 뜯어보니 json 필드 이름이 isFavorite이 아닌 Favorite…
    - isFavorite 과 관련 있을거같은 모든 부분을 고쳐가면서 테스트 해봤지만 변화 없음
    - 마지막으로 dto의 lombok이 의심되서 isFavorite → TESTFavorite으로 필드 이름 수정
    - 테스트 해보니 TESTFavorite으로 맞게 변경되었음
    - lombok 네이밍 컨벤션이 원인이었다…
    
    - 추가로 찾아본 내용
        - JavaBeans 규약 : 
        boolean 타입의 필드는 `is` 접두사로 시작하는 getter 메서드를 가져야 한다.
        - `boolean isFavorite;`라는 필드가 있을 경우, 
        Lombok은 `getIsFavorite()` 대신 `isFavorite()` 메서드를 생성
        - 이 때, JSON 직렬화 라이브러리(예: Jackson)는 boolean 필드의 getter 메서드를 
        확인하여  접두사로 인식된 요소(`is`, `get` 등등) 제거 후 나머지를 필드로 사용한다.
        - spring boot의 기본 ResponseEntity 생성 라이브러리는 Jackson이므로…
        위와 같은 현상이 발생한다.
    
    참고 :
    
    https://projectlombok.org/features/GetterSetter
    
     https://ratseno.tistory.com/101
    
- 타임리프 환경에서 csrf 토큰 처리 구현 문제
    - SpringBoot 환경에서 자바스크립트 측에서 AJAX로 POST 요청을 보내야 하는 상황
    - 코드 부분
        
        ```jsx
        const userConfirmed = confirm("즐겨찾기로 등록하시겠습니까?");
        if (userConfirmed) {
            // POST 요청을 위한 데이터
            const postData = {
                station_id: stationInfo.station_id
            };
        
            try {
                //csrf 토큰 -> 포함 안하면 POST 요청을 SpringSecurity에서 걸러버림
                const csrfToken = document.querySelector('meta[name="_csrf"]').content;
                const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
                console.log("Token : " + csrfToken);
                console.log("csrfHeader : " + csrfHeader);
                // fetch를 이용한 POST 요청
                const FavoriteResponse = await fetch('/addToFavorite', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        [csrfHeader]: csrfToken
                    },
                    body: JSON.stringify(postData) // 데이터 JSON 문자열로 변환
                });
        
                // 응답 상태에 따라 다르게 처리
                // 즐겨찾기 등록 성공시 바뀌어야 할 상태
                // 별 버튼 색상, 별 버튼 연결된 이벤트 리스너, isFavorite 값
                if (FavoriteResponse.ok) {
                    isFavoriteAndChangeStar(true, favoriteStarButton);
                    isFavorite = true;
                    hideModal(favoriteModal);
                    alert("즐겨찾기 등록 완료")
                    const FavoriteResponseData = await FavoriteResponse.json(); // 성공적인 응답이면 JSON으로 변환
                    console.log('Success:', FavoriteResponseData); // 성공적으로 응답을 받았을 때 처리
                    window.location.href = '/home';
                } else {
                    // 오류 처리: 상태 코드에 따라 다른 메시지 출력 가능
                    console.error('Error:', FavoriteResponse.status, FavoriteResponse.statusText);
                }
            } catch (error) {
                console.error('Fetch error:', error); // 오류 처리
            }
        ```
        
    
    - SpringBoot 기준 아래와 같이 html head에 선언하면 토큰에 접근이 가능하다.
        - `<meta name="_csrf" content="${_csrf.token}"/>`
        - `<meta name="_csrf_header" content="${_csrf.headerName}"/>`
    
    ```jsx
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <title>Document</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="/css/menu.css" rel="stylesheet">
        <link href="/css/home.css" rel="stylesheet">
        <link href="/css/subwayMap/subwaymap.css" rel="stylesheet">
        <link href="/css/subwayMap/favorite.css" rel="stylesheet">
    </head>
    ```
    
    - 그런데 아래와 같이 js측에서 접근하려고 결과값을 보니 null이 리턴된 상황…
    
    ```jsx
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
            const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
            console.log("Token : " + csrfToken);
            console.log("csrfHeader : " + csrfHeader);
    ```
    
    - SpringSecurity, 서비스 코드를 살펴봐도 딱히 이상 있는 부분이 없었음
    - Java 측에서 토큰이 제대로 생성되는지 체크해봐도 정상적으로 값이 넘어옴
    
    ```java
    @GetMapping("/checkCSRF")
        public String getCsrfToken(HttpServletRequest request) {
            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            if (csrfToken != null) {
                logger.info("CSRF Token:", csrfToken.getToken());
            } else {
                logger.warn("CSRF NULL");
            }
            return "home";
        }
    ```
    
    - 마지막으로 남은 부분이 타임리프 관련 문제여서 메타 태그를 수정하고 테스트
    → 서비스 정상 작동…
    
    ```html
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        **<meta name="_csrf" th:content="${_csrf.token}"/>
        <meta name="_csrf_header" th:content="${_csrf.headerName}"/>**
        <title>Document</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="/css/menu.css" rel="stylesheet">
        <link href="/css/home.css" rel="stylesheet">
        <link href="/css/subwayMap/subwaymap.css" rel="stylesheet">
        <link href="/css/subwayMap/favorite.css" rel="stylesheet">
    </head>
    <body>
    ```
    
    - 요약하면 렌더링 시점 문제였다…
        - `content` 속성은 정적 HTML 렌더링 이라서 HTML 입장에서는 표현식 값을 받아올 대상을 찾을 수 없다.
        - 당연히 csrf 속성들도 처리를 할 수 없으니 `null` 이 된다.
        - `th:content` 로 선언해야 서버에서 해당 값을 주입 받은 이후에 태그 처리가 이뤄지고
         올바른 서버 사이드 렌더링 구현이 가능하다.
        - 타임리프가 HTML 을 전달받고 서버에서 해당하는 데이터를 동적으로 주입 후 클라이언트에 전달
        → 클라이언트는 주입된 HTML을 받아서 렌더링
        - 위와 같은 과정으로 서버 측(Spring Boot)에서 생성한 CSRF 토큰을 HTML에 주입하게 된다.
        - 의존성 주입과 상당히 유사한 방식으로 동작하는 듯?
- Pageable - 데이터 포맷에 따른 size 설정 문제
    
    ### 문제 상황
    
    pageable을 활용해 DB에서 받아온 즐겨찾기 데이터를 컨트롤러 단에서 페이징 처리하는 중
    테스트 용도로 size =1 로 설정한 채로 1개씩 표시가 되나 테스팅을 해봤지만,
    데이터의 일부만 html에서 렌더링 되는 것을 확인
    
    ### 환경
    
    - SpringBoot 3.3.3
    - Thymeleaf
    - MySQL - jpa, jpql, myBatis
    - 사용한 쿼리는 JPQL 기반의 쿼리
    
    ### 코드 & 분석
    
    - 컨트롤러
        
        ```java
        // 메뉴바에서 a 태그 버튼 눌렀을 경우
            @GetMapping("/goFavoriteList")
            public String goFavoriteList(@RequestParam(defaultValue = "0") int page, Model model){
        
                String favoriteList_user_id = userService.getUserObject().getUserId();
        
                if(favoriteList_user_id != null){
                    // 문제가 된 부분
                    int size = 1;
                    Pageable pageable = PageRequest.of(page, size);
                    Page<FavoriteListPageDto> favoriteListPage = favoriteListService.getFavoriteListByUserId(favoriteList_user_id,pageable);
        
                    // setTime을 인덱스처럼 사용할 예정 (h0530 같은 값으로)
                    String setTime = subwayMapService.isValidTimeFormat(subwayMapService.getCurrentTimeColumn());
                    String dayType = subwayMapService.getCurrentDayType();
        
                    // ampm, 시, 분 을 가져오는 기능
                    // 페이지 최초 접근시에는 현재 기준으로 설정
                    TimeAttributes timeAttributes = isHolidaysService.getCurrentTimeAttributes();
                    model.addAttribute("ampm",timeAttributes.getAmPm());
                    model.addAttribute("hour",timeAttributes.getHour());
                    model.addAttribute("minute",timeAttributes.getMinute());
        
                    // 현재 시간 기준 연, 월, 일을 어트리뷰트로 추가
                    String year = String.valueOf(LocalDate.now().getYear());
                    String month = month = String.format("%02d", LocalDate.now().getMonthValue());
                    String day = String.format("%02d", LocalDate.now().getDayOfMonth());
                    model.addAttribute("year",year);
                    model.addAttribute("month",month);
                    model.addAttribute("day",day);
        
                    // station_id를 이용해 매핑 처리해서 station_id 하위 항목으로 상선, 하선 데이터가 들어가도록 조정
                    // 그룹화 (평일, 휴일 기준으로 매칭되는 데이터만 매핑시킴)
                    Map<Long, List<FavoriteListPageDto>> raw_groupedFavorites = favoriteListPage.getContent().stream()
                            .filter(item -> item.getDayType().equals(dayType)) // dayType이 일치하는 항목만 필터링
                            .collect(Collectors.groupingBy(FavoriteListPageDto::getStationId));
        
                    // station_id 순으로 정렬(오름차순)
                    Map<Long, List<FavoriteListPageDto>> groupedFavorites = new TreeMap<>(raw_groupedFavorites);
                    
                    // Map 확인용 출력
        //            groupedFavorites.forEach((stationId, favorites) -> {
        //                System.out.println("Station ID: " + stationId);
        //                favorites.forEach(favorite -> {
        //                    System.out.println("  getStationLine: " + favorite.getStationLine());
        //                    System.out.println("  getStationId: " + favorite.getStationId());
        //                    System.out.println("  getStationName: " + favorite.getStationName());
        //                    System.out.println("  getDayType: " + favorite.getDayType());
        //                    System.out.println("  getDirectionType: " + favorite.getDirectionType());
        //                    System.out.println();
        //                });
        //            });
        
                    // 모델에 데이터 추가
                    model.addAttribute("setTime", setTime); // 현재 시간 기준 인덱스(h0000 형태)
                    model.addAttribute("groupedFavorites", groupedFavorites);   // 매핑시킨 데이터
                    model.addAttribute("favoriteList", favoriteListPage.getContent()); // 페이지의 내용
                    model.addAttribute("totalPages", favoriteListPage.getTotalPages()); // 총 페이지 수
                    model.addAttribute("currentPage", favoriteListPage.getNumber()); // 현재 페이지 번호
                }
        
                model.addAttribute("sessionUser", userService.getUserObject());
        
                List<NoticeBoard> notices = NOTICEBOARDREPOSITORY.findAll(Sort.by(Sort.Direction.DESC, "boardNo"));
                for (NoticeBoard notice : notices) {
                    String contents = NOTICEBOARDSERVICE.modifyBoardText(notice.getBoardNo());
                    notice.getBoard().setBoardText(contents);
                }
                model.addAttribute("notices", notices);
        
                return "favoriteList/favoriteList";
            }
        ```
        
    - DTO 형태
        
        SubwayTime이 다른 DTO 라서 인터페이스를 통해 구현해 SubwayTime을 통째로 들고 올 수 있게 구현
        
        ```java
        package com.metroflow.model.dto;
        
        // SubwayTime을 통째로 쓸거라서 인터페이스로 선언하고 JPQL 선언 부분에서 맞춰줄 예정
        public interface FavoriteListPageDto {
        
            String getUserId();
            Long getStationId();
            String getStationLine();
            String getStationName();
            String getDirectionType();
            String getDayType();
        
            // SubwayTime 객체 추가
            SubwayTime getSubwayTime();
        
        }
        ```
        
    - html (타임리프 기반) 부분 발췌
        
        ```html
        <div class="favoriteList_container">
                    <div class = "favoriteList-content" th:each = "entry : ${groupedFavorites}">
        
                        <div class = "topItems">
                            <!--아래 th:class에 의해 덮어씌워짐 -->
                            <div class = "line-number-div">
                                <span class="line-number"
                                      th:class="${  entry.value[0].stationLine == '1' ? 'lines line1' :
                                                entry.value[0].stationLine == '2' ? 'lines line2' :
                                                entry.value[0].stationLine == '3' ? 'lines line3' :
                                                entry.value[0].stationLine == '4' ? 'lines line4' :
                                                entry.value[0].stationLine == '5' ? 'lines line5' :
                                                entry.value[0].stationLine == '6' ? 'lines line6' :
                                                entry.value[0].stationLine == '7' ? 'lines line7' :
                                                entry.value[0].stationLine == '8' ? 'lines line8' :
                                                entry.value[0].stationLine == '9' ? 'lines line9' :
                                                'lines default-line'}"
                                      th:text="${entry.value[0].stationLine}"></span>
                            </div>
                            <div class = "station-name-div">
                                <span class="station-name" th:text="${entry.value[0].stationName}"></span>
                            </div>
                            <div class = "delete_favorite_button-div">
                                <button class = "delete_favorite_button"
                                        th:data-delete-target="${entry.value[0].stationId}"
                                        th:onclick="submitDeleteFavorite(event)"
                                        th:text="삭제">
                                </button>
                            </div>
                        </div>
        
                        <div class = "middleItems" th:each="item : ${entry.value}">
                            <span th:text="${item.directionType}"></span>
                            <div class = "status-bar">
                                <div class="bar"
                                     th:class="${item.subwayTime[setTime] != null ?
                         (T(com.metroflow.model.service.CongestionConverter).convertToInt(item.subwayTime[setTime]) <= 33 ? 'bar spacious' :
                          T(com.metroflow.model.service.CongestionConverter).convertToInt(item.subwayTime[setTime]) > 33 && T(com.metroflow.model.service.CongestionConverter).convertToInt(item.subwayTime[setTime]) <= 66 ? 'bar normal' :
                          T(com.metroflow.model.service.CongestionConverter).convertToInt(item.subwayTime[setTime]) > 66 && T(com.metroflow.model.service.CongestionConverter).convertToInt(item.subwayTime[setTime]) <= 99 ? 'bar crowded' :
                          T(com.metroflow.model.service.CongestionConverter).convertToInt(item.subwayTime[setTime]) > 99 ? 'bar overcrowded' :
                          'bar default-congestion') :
                         'bar default-congestion'}">
                                </div>
                            </div>
                            <div class="value-check">
                                <span th:text="${item.subwayTime[setTime]}+'%'"></span>
                            </div>
                        </div>
        
                    </div>
                </div>
        
                <div class="paging-container">
                    <div class="paging">
                         <span th:if="${currentPage > 0}">
                             <a th:href="@{/goFavoriteListWithSetTime(page=${currentPage - 1},year=${year},month=${month},day=${day},ampm=${ampm}, hour=${hour}, minute=${minute})}">이전</a>
                         </span>
        
                        <span th:each="pageNum : ${#numbers.sequence(0, totalPages - 1)}">
                            <a th:href="@{/goFavoriteListWithSetTime(page=${pageNum},year=${year},month=${month},day=${day},ampm=${ampm}, hour=${hour}, minute=${minute})}"
                                th:class="${pageNum == currentPage ? 'active' : ''}"
                                th:text="${pageNum + 1}"></a>
                        </span>
        
                        <span th:if="${currentPage < totalPages - 1}">
                    <a th:href="@{/goFavoriteListWithSetTime(page=${currentPage + 1},year=${year},month=${month},day=${day},ampm=${ampm}, hour=${hour}, minute=${minute})}">다음</a>
                </span>
                    </div>
                </div>
        
        ```
        
    - 사용한 쿼리(JPQL)
        
        ```java
        // 유저 id 기준으로 페이지에 표시 할 즐겨찾기 데이터를 가져오는 쿼리
            @Query("SELECT f.user.userId AS userId, f.station.stationId AS stationId, s.stationLine AS stationLine, s.stationName AS stationName, " +
                    "t.directionType AS directionType, t.dayType AS dayType, ti AS subwayTime " +
                    "FROM FavoriteList f " +
                    "JOIN SubwayStation s ON f.station.stationId = s.stationId " +
                    "JOIN SubwayType t ON t.subwayStation.stationId = f.station.stationId " +
                    "JOIN SubwayTime ti ON ti.typeId = t.typeId " +
                    "WHERE f.user.userId = :userId")
            Page<FavoriteListPageDto> findFavoriteListByUserId(@Param("userId") String userId, Pageable pageable);
        ```
        
    
    일단 멀쩡한 값이 넘어오도록 해야해서 size 값을 1부터 점점 높은 값으로 바꾸다 보니,
    
    size = 4 마다 온전한 데이터 1개가 모두 html 측으로 넘어가는 것을 확인했다.
    
    - size = 32 / 총 8개의 데이터가 한 페이지에 나타날 수 있는 상황
        
        ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d2918bca-e401-4846-a929-c5a67e5469f0/4b164879-205e-42b3-8665-3baeba4f8a02/image.png)
        
    
    왜 하필 4마다 인지 용의자를 찾아야 하는 상황…
    
    ### 1. DTO 필드
    
    DTO에 `String` 5개,  `Long`1개, `SubwayTime`(CustomDTO) 1개, 총 7개의 요소가 있는데
    
    필드 수  자체는 크게 연관 지을만한 요소가 없었다.
    
    혹시 쿼리 결과물 데이터 크기로 인해 잘리는게 아닐까 했지만, Pageable의 기능을 찾아봐도
    
    이렇게 가벼운 쿼리에 대해서는 딱히 데이터 크기에 의해 영향받을 부분이 없었다.
    
    ### 2. 쿼리 결과 데이터
    
    혹시 JPQL 쿼리가 잘못되었나 싶어서 결과물 데이터를 일일이 출력해봤지만, 
    
    데이터는 설계한 대로 정확하게 넘어온 것을 확인했다.
    
    MySQL에서 동일한 쿼리를 작성해서 결과를 출력해보니, 데이터가 4의 배수로 나오는 것을 확인했다.
    
    이 부분에서 드디어 원인을 찾았다…
    
    ### 상황 분석
    
    작성한 쿼리는 컨트롤러에서 현재 세션에 있는 user_id를 주입받아 
    
    MySQL에 전송하기 전 파라미터로 주입하는 방식으로 작동한다.
    
    지하철 환승역은 하나의 지하철 역에 대해 여러 개의 호선이 존재하므로, station_id는 호선마다 다르게 설정되어있는데
    
    우리 프로젝트 기준 하나의 역 데이터에 대해 방향(상선, 하선 or 외선, 내선), 요일 구분(평일 , 휴일) 으로 구분이 이뤄진다.
    
    즐겨찾기 테이블 자체는 단순하게 user_id, station_id, auto-incrementNum 3가지로 이루어진 상태라
    
    순수 즐겨 찾기 테이블에 관해 쿼리를 사용했다면 size = 1로 해놔도 의도대로 작동했겠지만,
    
    실제로 수행한 쿼리에서는 join 시켜서 다른 테이블 데이터(역 정보, 혼잡도 정보, 방향, 요일 구분 등등)도 가져오기에
    
    station_id 하나 당 4개의 데이터가 발생하는 것이 올바른 동작이다.
    
    따라서 size = 4 마다 올바른 데이터 1개가 넘어오는게 당연한 현상이었다…
    
    쿼리 작성할 때 고려해야 할 요소를 하나 더 배웠다…
