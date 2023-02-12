package com.example.effective.item9.trywithresources;

import java.io.*;

public class Copy {
    private static final int BUFFER_SIZE = 8 * 1024;

    static void copy(String src, String dest) throws IOException {
        try (InputStream in = new FileInputStream(src);
             OutputStream out = new FileOutputStream(dest);) {
            byte[] buf = new byte[BUFFER_SIZE];
            int n;
            while((n = in.read(buf))>= 0) {
                out.write(buf, 0, n);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String src = args[0];
        String dest = args[1];
        copy(src, dest);
    }
}
