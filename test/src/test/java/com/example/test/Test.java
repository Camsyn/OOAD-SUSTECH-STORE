package com.example.test;


import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.time.LocalDateTime;

public class Test {
    @Data
    static class A{
        int a = 1;
        String name;

        @Override
        public String toString() {
            return "A{" +
                    "a=" + a +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
    public static void main(String[] args) {
        A a = JSON.parseObject("{\"name\": \"陈昆秋\"}", A.class);
        System.out.println(a);
    }
}
