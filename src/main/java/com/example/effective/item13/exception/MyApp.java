package com.example.effective.item13.exception;

/**
 * CheckedException 은 throws 하거나 try/catch 로 처리해야한다.
 * CheckedException 은 복구를 시도할 수 있고 클라이언트에게 알려줄 수 있는 장치로써 사용한다.
 *
 * RuntimeException 은 왜 throws 하거나 try/catch 하도록 강제하지 않을까? => 이 예외는 복구할 수 없는 예외다라고 생각.
 */
public class MyApp {
    public void hello(String name) throws MyException {
        if (name.equals("푸틴")) {
            throw new MyException();
        }

        System.out.println("hello");
    }

    public static void main(String[] args) {
        MyApp myApp = new MyApp();
        try {
            myApp.hello("푸틴");
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}
