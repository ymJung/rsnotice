package com.rsnotice.rsnotice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


@WebMvcTest(NoticeController.class)
class NoticeControllerTest {

    @Test
    public void test() {
        System.out.println("test");
    }
}