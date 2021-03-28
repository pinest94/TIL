# JPA(Java Persistence API)

ORM개념을 기반하여 기능을 정의한 인터페이스 모음이다. 현재 자바에서 ORM표준으로 채택되어 있다.

## ORM(Object Relational Mapping)

객체와 관계형 데이터베이스 테이블 관계를 자동으로 맵핑하는 개념이다.

* 만약 ORM개념이 없다면 테이블에 존재하는 데이터를 어떻게 맵핑할 것인가?

## Hibernate

JPA 인터페이스를 구현한 오픈소스이다.

## Spring Data JPA

Spring Framework에서 JPA를 편리하게 사용할 수 있도록 만들어 놓은 것이다.

### 관련 메서드 기능 정리
* save

![jpa-save](https://user-images.githubusercontent.com/31653025/112751694-406ead80-900a-11eb-9981-557db006a419.PNG)

`save`는 insert뿐만아니라 update쿼리를 호출할 수 있다. 위의 코드와 같이 `isNew()` (isNew는 해당 entity를 select하여 조회여부를 파악함 = id가 null인지 확인)메서드를 이용하여 새로운 entity인 경우엔 `persist`메서드(INSERT QUERY수행)를 호출한다. 새로운 entity가 아닌 else문에는 `merge` 메서드를 호출하는데 이 때 UPDATE QUERY를 수행하므로 `save`는 적절한 상황에 **INSERT**, **UPDATE**를 수행할 수 있다.
