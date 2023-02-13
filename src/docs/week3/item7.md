# item 7. 다 쓴 객체 참조를 해제하라. 

## 메모리 누수 
```java
// 코드 7-1 메모리 누수가 일어나는 위치는 어디인가? (36쪽)
public class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if (size == 0)
            throw new EmptyStackException();
        return elements[--size];
    }

    /**
     * 원소를 위한 공간을 적어도 하나 이상 확보한다.
     * 배열 크기를 늘려야 할 때마다 대략 두 배씩 늘린다.
     */
    private void ensureCapacity() {
        if (elements.length == size)
            elements = Arrays.copyOf(elements, 2 * size + 1);
    }

    public static void main(String[] args) {
        Stack stack = new Stack();
        for (String arg : args)
            stack.push(arg);

        while (true)
            System.err.println(stack.pop());
    }
}
```

### 문제점 
- 위의 코드에는 메모리 누수가 존재한다. 
- 이 스택을 사용하는 프로그램은 점차 가비지 컬렉션 활동과 메모리 사용량이 늘어나 결국 성능이 저하될 것이다.
- 상대적으로 드문 경우긴 하지만 심할 때는 디스크 페이징이나 OUTOFMEmoryError를 일으켜 프로그램이 예기치 않게 종료되기도 한다.

### 메모리 누수는 어디서? 
- 위 스택이 커졌다가 줄어들었을 때 스택에서 꺼내진 객체들을 가비지 컬렉터가 화수 하지 않는다.
- 이 스택이 그 객체들의 다 쓴 참조를 여전히 가지고 있기 때문이다.
    - 여기서 다 쓴 참조란 다시 쓰지 않을 참조를 뜻하는데, 스택에서는 element 배열의 "활성 영역" 밖의 참조들이 모두 여기에 해당됨

### 고쳐보자
```java
    public Object pop() {
        if (size == 0)
            throw new EmptyStackException();
        Object result = elements[--size];
        elements[size] = null; // 다 쓴 참조 해제
        return result;
    }
```
- null로 처리하면 참조 해제도 될뿐더러, 나중에 잘못 접근할 경우 NPE 오류를 발생시킬 수 있다. 
    - 오류는 조기에 잡는게 좋음!

### 그런데 모든 객체를 다 null 처리를 해줘야 할까? 
- 다 그럴 필요는 없다, 코드를 더럽게 만들뿐이다. 
  - 다 쓴 참조를 해제하는 가장 좋은 방법은 그 참조를 담은 변수를 유효 범위 밖으로 밀어내는 것이다.
  - 변수의 범위를 최소가 되게 정의했다면 이 일은 자연스럽게 이뤄진다.
- Stack 클래스는 자기 스스로 메모리 관리를 하기 때문에 메모리 누수에 취약한 것 
  - 가비지 컬렉터가 이 오브젝트가 비활성 오브젝트인지 알 방도가 없음
  - 따라서 Stack 클래스에서는 null 처리를 해줘 가비지 컬렉터에게 알려줘야 함
  - 즉, 자기 메모리를 직접 관리하는 클래스라면 프로그래머는 항시 메모리 누수에 주의해야 한다. 

### 캐시 역시 메모리 누수를 일으키는 주범
- 객체 참조를 캐시에 넣고 나서 이 사실을 까맣게 잊은 채 그 객체를 다 쓴뒤로도 한참을 그냥 놔두는 일을 자주 접할 수 있음
  - 캐시 외부에서 키를 참조하는 동안만 캐시가 필요한 상황이라면 WeakHashMap을 사용하여, 자동으로 제거되도록 하자.
  - 백그라운드 스레드를 활용하거나 캐시에 새 엔트리를 추가할 때 부수 작업으로 쓰지 않는 엔트리를 청소해주자.
  - 클라이언트가 콜백(or 리스너)만 등록만 하고 명확히 해지 않는다면 콜백은 계속 쌓여간다, 이럴 땐 콜백을 약한 참조로 저장하면 된다 (WeakHashMap 키로 저장)