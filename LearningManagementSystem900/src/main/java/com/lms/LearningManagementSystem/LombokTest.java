package com.lms.LearningManagementSystem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LombokTest {
    private String name;

    public static void main(String[] args) {
        LombokTest test = new LombokTest();
        test.setName("Alex");
        System.out.println("Name is: " + test.getName()); // Should print "Alex"
    }
}
