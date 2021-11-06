package com.deltabook;

import com.deltabook.controllers.AdminController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private AdminController adminController;

    @Test
    public void contextLoads() {
        assertFalse(Objects.isNull(adminController));
    }

}

