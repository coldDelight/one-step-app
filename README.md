
![OneStep](https://github.com/one-step-team/one-step-app/assets/94586184/adc019f6-d66a-46d6-90cd-72b8dc4944bd "OneStep")
<a href="https://play.google.com/store/apps/details?id=com.colddelight.onestep"><img src="https://play.google.com/intl/ko_kr/badges/static/images/badges/ko_badge_web_generic.png" height="70"></a>

<br>


# OneStep
> 💡 **부위 별 운동 등록, 운동 루틴 설정, 운동 기록 애플리케이션**



### Feature
#### 홈
- 해당 루틴으로 운동한 총 일수 표출
- 루틴에 따른 이번주 운동 계획 및 현황 표출
- 해당 요일에 운동 유무에 따라 오늘의 운동 혹은 휴식일 표출
#### 루틴
- 해당 루틴으로 운동한 총 일수 표출
- 루틴 이름 관리
- 운동 리스트에 운동 이름과 세트 정보(횟수, 무게) 등록 및 수정, 삭제
- 요일 별로 운동과 세트 정보를 등록 및 수정, 삭제
#### 운동
- 등록한 루틴의 정보를 바탕으로 해당 요일의 운동 리스트와 진행 정보 표출
- 운동의 세트 정보를 표출
- 운동 중에도 세트 정보 수정 가능
- 세트가 끝날 때마다 휴식 타이머 표출, 휴식 타이머 또한 시간 조절 가능
#### 기록
- 운동을 한 날짜를 캘린더에 표출
- 해당 날짜 선택 시 그 날의 운동 및 세트 정보들 표출


### ScreenShots
<img src="https://github.com/one-step-team/one-step-app/assets/94586184/4e0f4185-d0df-4847-ba1d-0fdeed0c2b7d" height="360">| <img src="https://github.com/one-step-team/one-step-app/assets/94586184/c1966b8f-0211-4f3b-8573-c7ff1c9feba2" height="360">| <img src="https://github.com/one-step-team/one-step-app/assets/94586184/6f0d89e9-96bd-4d0e-bc95-d27647016f71" height="360"> | <img src="https://github.com/one-step-team/one-step-app/assets/94586184/6e32aac7-31dc-4844-87d3-1b48bc5ce5a1" height="360">|
|-|-|-|-|
| <img src="https://github.com/one-step-team/one-step-app/assets/94586184/6d96b386-f8ca-438a-99e3-c5c9a5bc0407" height="360"> | <img src="https://github.com/one-step-team/one-step-app/assets/94586184/ed1d7f34-2cc5-4046-89cb-28ca1b9257ba" height="360"> |<img src="https://github.com/one-step-team/one-step-app/assets/94586184/4c93b206-40c2-427a-a471-cd830ea0669e" height="360"> |<img src="https://github.com/one-step-team/one-step-app/assets/94586184/b06231b4-bd95-4027-bf9b-c2e0cab19975" height="360">| 

<br>

# Module

```mermaid
graph TD;
    app:::app-->:feature:::feature;
    app:::app-->:core:designsystem;
    :feature-->:core:designsystem;
    :feature-->:core:data;
    :feature-->:core:model;

    :core:data-->:core:model;
    :core:data-->:core:database;
    :core:data-->:core:datastore;


classDef app stroke:#00E489
classDef feature  stroke:#FF8A65
linkStyle 0,1 stroke:#00E489,stroke-width:2px;
linkStyle 2,3,4 stroke:#FF8A65,stroke-width:2px;
```
<br>

# Architecture
**One Step**은 [Android Architecture Guide](https://developer.android.com/topic/architecture) 를 준수합니다.

### Overview
<center>
<img src="https://github.com/one-step-team/one-step-app/assets/94586184/2c6a11c8-f8a2-486f-913f-4147fef80300" width="600px" />
</center>

- Data, UI 총 두 개의 Layer로 구성되어 있습니다.
- [unidirectional data flow](https://developer.android.com/topic/architecture/ui-layer#udf) 를 준수합니다.

    - 상위 Layer는 하위 Layer의 변화에 반응한다.
    - Event는 상위에서 하위 Layer로 이동한다.
    - Data는 하위에서 상위 Layer로 이동한다.

- 데이터 흐름은 streams 통해 표현하며 Kotlin Flow를 사용합니다.

```mermaid
graph TB
Screen:::UI
ViewModel:::UI
Repository:::Data

Database:::Data
DataStore:::Data
Repository --> Database
Repository --> DataStore
ViewModel --> Repository
Screen --> ViewModel

classDef UI stroke-width:2px,stroke:#00E489
classDef Data stroke-width:2px,stroke:#083042

```

<br>

# Development
### Required
| Name | Version |
| --- | --- |
| IDE |   *```Android Studio Giraffe```* | 
| Kotlin |   *```1.9.10```* | 
| MinSdk  |   *```26```* | 
| TargetSdk  |   *```34```* | 


### Libraries
| Name | Version |
| --- | --- |
| Coroutines | *```1.7.3```* |
| Dagger-Hilt | *```2.48.1```* |
| Room | *```2.6.0```* |
| DataStore  | *```1.0.0```* |
| ComposeMaterial3  | *```1.2.0-beta01```* |
| NavigationCompose  | *```2.7.4```* |


> [!NOTE]
> 사용한 라이브러리 세부정보는 [libs.versions.toml](https://github.com/one-step-team/one-step-app/blob/dev/gradle/libs.versions.toml) 를 참고해 주세요.

<br>

# Team

|                                        Android                                         |                                              Android                                               |
|:-------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------:|
| [<img src="https://github.com/coldDelight.png" width="150px"/>](https://github.com/coldDelight) | [<img src="https://github.com/see-ho.png" width="150px"/>](https://github.com/see-ho) | 
| <a href="https://github.com/coldDelight">김찬희                                          |     <a href="https://github.com/see-ho">  이소희                                               | 
