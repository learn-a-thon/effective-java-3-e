# effective-java-3-e
이펙티브 자바 3판 스터디

# 학습 계획

- 학습은 기본적으로 [이펙티브 자바 Effective Java 3/E](http://www.yes24.com/Product/Goods/65551284) 책으로 진행
    - 이해를 돕기위해 관련 강의 혹은 블로그 참고 가능
- 매주 아이템 7개씩 순차적으로 학습
    - 학습 인증은 1주일 단위로 진행
- 단순히 책의 내용만 읽는 것이 아닌 직접 코드를 작성하는 것을 권장

## 참여 방법

- 스터디 참여 보증금 20000원 입금
- 주차별 학습 인증 실패시 벌금(특별한 사유는 제외)
    - 벌금: 미인증 일수 x 5000원

# 진행 방식

- 스터디에 참여하는 인원은 자신의 `영어 이니셜(Eng Name)` 혹은 `Github Id` 를 이름으로된 **브랜치(branch)** 생성하고 각자 학습한 내용은 **본인의 메인 브랜치**에서 관리한다.
    - 구체적인 진행 방법은 **[학습 진행 방법](#학습-진행-방법)** 을 참고
- 스터디원들은 요청된 PR에 대해 **“잘못된 내용”**, **“보완했으면 하는 내용”**, **“추가 학습이 필요한 내용”** 등을 **적극적으로 리뷰**해준다.
- 개개인의 상황을 고려하여 **선행 학습을 제한하지 않는다.(오히려 권장)**
- 학습과 관련된 Convention은 다음을 참고한다.
    - 학습 시작하기 전, **[Convention > Branch](#branch)** 를 참고하여 **브랜치를 생성한다.**
    - 학습 시작, 생성한 브랜치에서 학습한 내용을 **커밋([Convention > Commit](#commit))하여 기록한다.**
    - 학습 완료, 학습이 끝난 브랜치는 main 브랜치로 **PR([Convention > Pull Request](#pull-request)) 요청**한다.
    - 스터디원들의 확인(Approve)이 된 후에 머지(Merge) 할 수 있다.

## 학습 진행 방법

* 본인 `영어 이니셜(Eng Name)` 혹은 `Github Id` 를 이름으로 된 브랜치 생성
  * 해당 브랜치가 본인의 메인 브랜치가 될 것임

```bash
$ git branch gildong
```

* 학습이 시작된 경우, 본인의 메인 브랜치를 기준으로 학습 브랜치 생성
  * 네이밍은 자유롭게 설정 가능 다만 다른 사람과 겹치지 않아야 함

```bash
$ git branch # 브랜치 목록

* gildong
gildong/week1
...

$ git branch gildong/week2 # 브랜치 생성
$ git branch # 브랜치 목록

* gildong/week2
gildong
gildong/week1
...

*: 현재 설정된 기준 브랜치
```

* 학습이 끝난 경우, 학습 브랜치를 본인의 메인 브랜치로 PR 요청
  * `base: gildong` ← `compare: gildong/week2`

# Convention

### Branch

```
{Branch Name}/item{no} or week

ex)
memberA/item1
```

### Commit

```
[{Branch Name}] yyyy-MM-dd {item-title} {message}

ex)
[memberA] 2000-12-31 item 1. 생성자 대신 정적 팩터리 메서드를 고려하라
```

### Pull Request

```
[{Branch Name}] {item title}

ex)
[memberA] item 1. 생성자 대신 정적 팩터리 메서드를 고려하라
```

### File

- markdown(기본)
- 한글, 영어(소문자만 허용), -(dash), 공백

```
// 학습 문서 파일 형식
{item title}.md

ex)
item 1. 생성자 대신 정적 팩터리 메서드를 고려하라.md
```
