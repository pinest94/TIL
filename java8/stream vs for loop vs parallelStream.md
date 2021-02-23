# For-loop vs Stream vs ParallelStream

### Environment
| OS | CPU | JDK |
|-|-|-|
| Windows 10 | Intel Core i7-9750H CPU 2.60GHz | zulu-1.8.0.275(OPEN JDK) |

### Definition
* Stream이란?
  - Stream은 JDK 8버전에 추가된 기능으로 저장 요소를 하나씩 내부 참조하고 람다식을 적용하여 반복적으로 처리할 수 있도록 해주는 기능이다. 아래의 표에서 알 수 있듯이 for-loop보다 성능이 무조건적으로 좋지는 않다. 하지만, for, if문법을 걷어내어 코드를 읽는데 직관적이기 때문에 가독성이 좋아진다. Stream은 순차 처리이며 ParallelStream이 병렬적으로 처리한다. 병렬적으로 처리하는 경우 속도가 향상되지만 심각한 오류를 발생시킬 수 있다.

### Performance Comparison
* 각 주어진 횟수만큼 더하기 연산을 하며, 단위는 ms이다.
* 100만회

|방법/횟수|1회|2회|3회|4회|5회|6회|7회|8회|9회|10회|평균|
|-|-|-|-|-|-|-|-|-|-|-|-|
|For-Loop|2|1|0|0|0|0|0|0|0|0|0.3|
|Stream|45|3|2|1|0|1|1|0|1|1|5.5|
|Parallel Stream|67|2|0|0|0|1|0|0|0|1|7.1|

* 10억회

|방법/횟수|1회|2회|3회|4회|5회|6회|7회|8회|9회|10회|평균|
|-|-|-|-|-|-|-|-|-|-|-|-|
|For-Loop|264|249|256|279|263|276|275|247|247|247|260.3|
|Stream|440|1344|368|362|364|362|368|366|344|337|465.5|
|Parallel Stream|169|102|83|86|78|74|79|78|76|78|90.3|

### result
* 연산이 많거나 복잡한 경우에 parallelStream을 사용하면 성능의 향상을 기대할 수 있다. 하지만 공유된 thread pool을 사용하기 때문에 잠재적으로 심각한 성능장애를 일으킬 수도 있다. 또한 아직 이유는 모르겠지만 ~~(추후에 알게되면 업데이트 할 예정)~~ Stream사용 시 1344, 45, 67처럼 시간이 크게 튀는 경우가 발생한다. 복잡하거나 많은 연산이 아니면 for-loop를 사용하는 것도 좋을 것 같다.

### reference
* Java 8 in Action
* https://m.blog.naver.com/PostView.nhn?blogId=tmondev&logNo=220945933678&proxyReferer=https:%2F%2Fwww.google.com%2F
* https://dev0101.tistory.com/36
