# item9. try-finally보다는 try-with-resources를 사용하라

자바 라이브러리에는 CLOSE 메서드를 호출해 직접 닫아줘야 하는 자원이 많다. 
- Ex) InputStream, OutputStream, java.sql.Connection 
- 이런 자원 중 상당수가 안전망으로 FINALIZER를 활용하고는 있지만 믿음직하지 못하다. 
- 그래서 보통 try-finally를 사용한다.

### try-finally

```java
    static void copy(String src, String dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                byte[] buf = new byte[BUFFER_SIZE];
                int n;
                while ((n = in.read(buf)) >= 0)
                    out.write(buf, 0, n);
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }
```
- 위의 예제에는 문제점이 존재한다ㅔ. 
- 예외는 try 블록과 finally 블록 모두에서 발생할 수 있는데, 만약 readLine에서 예외를 던지면 같은 이유로 close 메서드도 싪패 한다. 
- 이런 상황이라면 두번째 예외가 첫 번째 예외를 완전히 집어삼켜 버려, 스택 추적 내역에 첫번째 예외에 관한 정보는 남지 않게 되어, 실제 시스템에서의 디버깅을 어렵게 한다. 
  - 일반적으로 문제를 진단하려면 첫번째 예외를 확인하고 싶을 것이다.
- 이러한 문제들은 try-with-resources 덕에 모두 해결되었다. 

### try-with-resources
```java
    static void copy(String src, String dst) throws IOException {
        try (InputStream   in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst)) {
        byte[] buf = new byte[BUFFER_SIZE];
        int n;
        while ((n = in.read(buf)) >= 0)
        out.write(buf, 0, n);
        }
    }
```
- 이 구조를 사용하려면 해당 자원이 AutoCloseable  인터페이스를 구현해야 한다.
- 자바 라이브러리와 서드파티 라이브러리들의 수많은 클래스와 인터페이스가 이미 AutoCloseable을 구현하거나 확장해뒀다.
- readLine, close 호출 양쪽에서 예외가 발생하면, close에서 발생한 에외는 숨겨지고 readline에서 발생한 예외가 기록된다.
- 숨겨진 예외들도 그냥 버려지지는 않고, 스택 추적 내역에 '숨겨졌다(Suppressed)'라는 꼬리표를 달고 출력된다.
- 또한 자바 7에서 Throwable에 추가된 getSuppressed 메서드를 이용하면 프로그램 코드에서 가져올 수도 있다.
- 또한 finally 구문도 사용할 수 있다.

