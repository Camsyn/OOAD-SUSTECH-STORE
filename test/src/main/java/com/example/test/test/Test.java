package com.example.test.test;

import org.springframework.beans.factory.annotation.Autowired;

public class Test {
    static Test2 test2;

    @Autowired
    void setTest2(Test2 test2){
        Test.test2 = test2;
    }

    @Override
    public String toString() {
        return test2.toString();
    }
}
