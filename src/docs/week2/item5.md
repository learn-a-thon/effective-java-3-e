# Item 5. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라
- 사용하는 자원에 따라 동작이 달라지는 클래스는 정적 유틸리티 클래스나 싱글턴 방식이 적합하지 않다. 
- 의존 객체 주입이란 인스턴스를 생성할 때 필요한 자원을 넘겨주는 방식이다. 
- 이 방식의 변형으로 생성자에 자원 팩터리를 넘겨줄 수 있다. 
- 의존 객체 주입을 사용하면 클래스의 유연성, 재사용성, 테스트 용이성을 개선할 수 있다.


###  팩터리 메서드 패턴
- 구체적으로 어떤 인스턴스를 만들지는 서브 클래스가 정한다.
- 새로운 Product를 제공하는 팩토리를 추가하더라도, 팩토리를 사용하는 클라이언트 코드는 변경할 필요가 없다.


### 스프링 IoC 
- BeanFactory 또는 ApplicationContext 
- 수많은 개발자에게 검증되었으며 자바 표준 스팩(@Inject)도 지원한다. 
- 손쉽게 싱글톤 Scope을 사용할 수 있다. 
- 객체 생성 (Bean) 관련 라이프사이클 인터페이스를 제공한다.