# How to solve jvm heap memory leak in gradle build

### Prerequisites
* Mac OS
* Intellij Ultimate

### Gradle build fail
<img width="600" alt="스크린샷 2021-07-08 오후 11 49 45" src="https://user-images.githubusercontent.com/31653025/124943486-7e3a9380-e047-11eb-8f0f-5e45ff33a37d.png">

Because JVM heap space is exhausted, gradle build is fail... 😢

Sometimes `GC overhead limit exceeded` issue has occurred. 😱

So, I tried a number of ways.

### #Try 1 - Intellij shared memory setting
<img width="1441" alt="스크린샷 2021-07-08 오후 11 58 43" src="https://user-images.githubusercontent.com/31653025/124945200-e3db4f80-e048-11eb-8a7b-303c9acb50fd.png">

`shared build processes heap size` setting in intellij preferences > compiler.

however, this issue unresolved

### #Try 2 - Intellij idea.vmoptions setting
I have no image..

set `-Xmx4096m`in Help > Edit Custom VM Options.

however, this is a real shoveling....

### #Try 3 - gradle.properties setting
I resolved it. 😁

<img width="400" alt="스크린샷 2021-07-09 오전 12 00 15" src="https://user-images.githubusercontent.com/31653025/124947428-c8714400-e04a-11eb-8cc5-fd8e2f287d09.png">

set max heap size in gradle.properties

```
On Windows: C:\Users\<you>\.gradle\gradle.properties
On Mac/Linux: /Users/<you>/.gradle/gradle.properties
```

If you can't find this file, you make this file. so do i.
