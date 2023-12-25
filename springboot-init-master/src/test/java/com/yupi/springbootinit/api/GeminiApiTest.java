package com.yupi.springbootinit.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeminiApiTest {

    @Test
    void geminiAi() {
        String answer = GeminiApi.GeminiAi(
//                "分析需求: \n" +
//                        "分析网站用户的增长情况\n"+
//                        "原始数据:\n"+
//                        "日期,用户数\n" +
//                        "1号,10\n" +
//                        "2号,20\n" +
//                        "3号,30\n"
                ""
                );
                System.out.println(answer);

    }
}