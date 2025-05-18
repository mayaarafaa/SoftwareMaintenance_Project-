package com.lms.learningmanagementsystem;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Getter
@Setter
public class LombokTest {

    private static final Logger logger = LoggerFactory.getLogger(LombokTest.class);
    private String name;

    public static void main(String[] args) {
        LombokTest test = new LombokTest();
        test.setName("Alex");
        logger.info("Name is: " + test.getName()); // Should print "Alex"
    }
}
