# AMU_Back

AMU(음악) 백엔드 서버 - 음악 스트리밍 및 공유 플랫폼

## 📋 프로젝트 소개

AMU_Back은 음악 업로드, 스트리밍, 리뷰, 플레이리스트 관리 기능을 제공하는 음악 플랫폼의 백엔드 REST API 서버입니다.

## 🛠 기술 스택

- **Framework**: Spring Boot 3.3.5
- **Language**: Java 17
- **Build Tool**: Gradle
- **Database**: MySQL
- **ORM**: MyBatis 3.0.1
- **Security**: Spring Security
- **Additional Libraries**:
  - Lombok
  - Jackson (JSON 처리)
  - Spring Validation

## ✨ 주요 기능

### 1. 사용자 관리 (User)
- 회원가입 / 로그인 / 로그아웃
- 프로필 관리 (이미지 업로드 포함)
- 사용자별 업로드 음악 조회
- 사용자별 좋아요 음악 조회
- 사용자별 리뷰 조회
- 사용자별 플레이리스트 조회

### 2. 음악 관리 (Music)
- 음악 파일 업로드 (MP3 + 커버 이미지)
- 음악 정보 관리 (제목, 가사, 재생시간, 장르 등)
- 음악 스트리밍
- 음악 검색
- 장르별 음악 조회
- 정렬 기능 (최신순, 인기순 등)
- 조회수 관리

### 3. 리뷰 시스템
- 음악 리뷰 작성
- 리뷰 조회
- 댓글 수 확인

### 4. 좋아요 (Favorite) 기능
- 음악 좋아요/좋아요 취소
- 좋아요 상태 확인

### 5. 플레이리스트 (Playlist)
- 플레이리스트 조회
- 플레이리스트에 음악 추가
- 플레이리스트 음악 재생

## 📡 API 엔드포인트

### User API (`/user`)
```
POST   /user/signUp          - 회원가입
POST   /user/signIn          - 로그인
POST   /user/signOut         - 로그아웃
GET    /user/current         - 현재 로그인 사용자 정보
POST   /user/updateProfile   - 프로필 업데이트
GET    /user/myUpload/{id}   - 사용자 업로드 음악 목록
GET    /user/myFavorite/{id} - 사용자 좋아요 음악 목록
GET    /user/myReview/{id}   - 사용자 리뷰 목록
GET    /user/myPlaylist/{id} - 사용자 플레이리스트
GET    /user/{artist}        - 아티스트 정보 조회
```

### Music API (`/music`)
```
POST   /music/upload                    - 음악 업로드
GET    /music/getMusic/image/{musicCode} - 음악 커버 이미지 조회
GET    /music/sort/{sortType}           - 정렬된 음악 목록
GET    /music/genre/{genreCode}         - 장르별 음악 목록
POST   /music/delete                    - 음악 삭제
POST   /music/view                      - 조회수 증가
POST   /music/list                      - 음악 목록 조회
POST   /music/listLatest                - 최신 음악 목록
GET    /music/play/{filename}           - 음악 스트리밍
GET    /music/review/{musicCode}        - 음악 리뷰 목록
POST   /music/review/upload             - 리뷰 작성
GET    /music/isLiked/{musicCode}       - 좋아요 상태 확인
POST   /music/like                      - 좋아요 추가
POST   /music/unlike                    - 좋아요 취소
GET    /music/{musicCode}               - 음악 상세 정보
GET    /music/{musicCode}/comments      - 음악 댓글 목록
GET    /music/{musicCode}/commentCounts - 댓글 수 조회
GET    /music/search                    - 음악 검색
```

### Playlist API (`/playlist`)
```
GET    /playlist/getPlaylist     - 플레이리스트 조회
GET    /playlist/play/{filename} - 플레이리스트 음악 재생
POST   /playlist/addMusic        - 플레이리스트에 음악 추가
```

## 🚀 시작하기

### 필수 요구사항

- Java 17 이상
- MySQL 데이터베이스
- Gradle 7.x 이상

### 데이터베이스 설정

1. MySQL에 데이터베이스 생성:
```sql
CREATE DATABASE amu;
CREATE USER 'amu'@'localhost' IDENTIFIED BY 'amu';
GRANT ALL PRIVILEGES ON amu.* TO 'amu'@'localhost';
FLUSH PRIVILEGES;
```

2. 필요한 테이블 생성 (MyBatis 매퍼 참조)

### 파일 저장 경로 설정

`src/main/resources/application.yml` 파일에서 다음 경로를 시스템에 맞게 수정:

```yaml
spring:
  file:
    upload-dir: C:/AMU_asset/profile_img/  # 프로필 이미지 저장 경로
  web:
    resources:
      static-locations: file:///C:/AMU_asset/  # 정적 리소스 경로
```

### 설치 및 실행

1. 저장소 클론:
```bash
git clone https://github.com/yongqyu49/AMU_Back.git
cd AMU_Back
```

2. 빌드:
```bash
./gradlew build
```

3. 애플리케이션 실행:
```bash
./gradlew bootRun
```

또는 빌드된 JAR 파일 실행:
```bash
java -jar build/libs/amu_back-0.0.1-SNAPSHOT.jar
```

4. 서버 접속:
```
http://localhost:8787
```

## ⚙️ 설정

### 포트 설정
기본 포트: `8787`

### 세션 설정
세션 타임아웃: `30분`

### 파일 업로드 제한
- 최대 파일 크기: `10MB`
- 최대 요청 크기: `10MB`

## 📁 프로젝트 구조

```
src/main/java/com/myspringweb/amu_back/
├── AmuBackApplication.java          # 메인 애플리케이션
├── domain/
│   ├── controller/                  # REST 컨트롤러
│   │   ├── MusicController.java
│   │   ├── PlaylistController.java
│   │   └── UserController.java
│   ├── service/                     # 서비스 인터페이스
│   │   ├── MusicService.java
│   │   ├── PlaylistService.java
│   │   └── UserService.java
│   ├── serviceImpl/                 # 서비스 구현
│   ├── dao/                         # 데이터 액세스 객체
│   ├── daoImpl/                     # DAO 구현
│   └── dto/                         # 데이터 전송 객체
│       ├── MusicDTO.java
│       ├── UserDTO.java
│       ├── PlaylistDTO.java
│       ├── ReviewDTO.java
│       ├── FavoriteDTO.java
│       ├── GenreDTO.java
│       └── SearchDTO.java
└── global/
    └── configuration/               # 설정 클래스

src/main/resources/
├── application.yml                  # 애플리케이션 설정
├── configuration.xml                # MyBatis 설정
└── mapper/                          # MyBatis 매퍼 XML
    ├── MusicMapper.xml
    ├── PlaylistMapper.xml
    └── UserMapper.xml
```

## 🔒 보안

- Spring Security를 사용한 인증/인가
- 세션 기반 인증
- 비밀번호 암호화 (PasswordEncoder)

## 🤝 기여하기

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 라이선스

이 프로젝트는 개인 프로젝트입니다.

## 📧 프론트엔드

프로젝트 링크: [https://github.com/yongqyu49/AMU_Front](https://github.com/yongqyu49/AMU_Front)
