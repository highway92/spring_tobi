package com.springbook.tobi.learningtest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer calcSum(String filePath) throws IOException {
        return lineReadTemplate(filePath, new LineCallback<Integer>() {
            public Integer doSomethingWithLine(String line, Integer value) {
                return value + Integer.parseInt(line);
            }
        }, 0);
    }

    public Integer multiple(String filePath) throws IOException {
        return lineReadTemplate(filePath, new LineCallback<Integer>() {
            public Integer doSomethingWithLine(String line, Integer value) {
                return value * Integer.parseInt(line);
            }
        }, 1);
    }

    public String concat(String filePath) throws IOException {
        return lineReadTemplate(filePath, new LineCallback<String>() {
            public String doSomethingWithLine(String line, String value) {
                return line + value;
            }
        },"a");
    }

    public Integer fileReadTemplate(String filePath, BufferedReaderCallback callback) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            int ret = callback.doSomethingWithReader(br);
            return ret;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try{
                    br.close();
                } catch (IOException e) {}
            }
        }
    }

    public <T> T lineReadTemplate(String filePath, LineCallback<T> lineCallback, T initValue) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            T result = initValue;
            String line = null;
            while( (line = br.readLine()) != null ) {
                result = lineCallback.doSomethingWithLine(line,result);
            }
            return result;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {}
            }
        }
    }

    public Calculator(){};
}
