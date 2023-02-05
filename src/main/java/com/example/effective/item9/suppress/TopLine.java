package com.example.effective.item9.suppress;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TopLine {

    // 별도 close를 호출해주는 과정이없다.
    static String firstLineOfFile(String path) throws IOException {
        try(BufferedReader br = new BadBufferedReader(new FileReader(path))) {
            return br.readLine();
        }
    }

    // stack tracer에 처음 예외 발생과 숨겨진 예외가 같이 보여진다.
    public static void main(String[] args) throws IOException {
        System.out.println(firstLineOfFile("pom.xml"));
    }
}
