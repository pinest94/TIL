# Batch란?

컴퓨터 자원을 최대로 활용하여 큰 단위의 작업을 일괄 처리하는 것을 말한다. 대부분 처리량이 많고 비 실시간 처리에 사용한다. 예를 들자면 정산, 통계... etc 컴퓨터 자원을 최대로 활용하기 때문에 자원 사용이
낮은 시간대에 배치를 처리하거나 사용되지 않은 또 다른 컴퓨터 자원을 사용한다.

# Spring Batch란?

배치 처리를 하기 위한 Spring Framework 기반 기술이다. Spring에서 지원하는 기술을 적용할 수 있고 스프링 배치의 실행 단위는 Job과 Step이 있다.

## Spring Architecture

* Job은 JobLauncher에 의해 실행
* Job은 배치의 실행 단위를 의미
* Job은 N개의 Step을 실행할 수 있으며, 흐름(Flow)을 관리할 수 있다. (ex. A Step실행 후 조건에 따라 B또는 C Step을 실행하도록 설정 가능)
* Step은 Job의 세부 실행단위이며, N개가 등록되어 실행된다.
* Step의 실행 단위는 크게 2가지로 나눌 수 있다.
    * Chunk : 하나의 큰 덩어리를 n개로 나누어 실행
    * Task : 하나의 작업 기반으로 실행
* Chunk기반 Step은 ItemReader, ItemProcessor, ItemWriter가 있다.
    * 여기서 Item은 배치 처리 대상 객체를 의미한다.
* ItemReader는 배치 처리 대상 객체를 읽어 ItemProcessor 또는 ItemWriter에게 전달한다.
    * 예를 들면, 파일 또는 DB에서 데이터를 읽는다.
* ItemProcessor는 input객체를 output객체로 filtering 또는 processing해서 ItemWriter에게 전달한다.
    * ItemReader에서 읽은 데이터를 수정 또는 ItemWriter대상인지 filtering한다.
    * ItemProcessor는 optional하다.
    * ItemProcessor가 하는 일을 ItemReader또는 ItemWriter가 대신할 수 있다.
* ItemWriter는 배치 처리 대상 객체를 처리한다.
    * DB UPDATE를 하거나, 처리 대상 사용자에게 알림을 보낸다.

### requirement

```
* IntelliJ IDEA
* Windows 10
* Open JDK 8
* Gradle
* Srping Boot 2.x
    -> Spring Batch, Spring JDBC, Spring Data JPA, lombok etc...
* H2 DB
* MySQL DB
```