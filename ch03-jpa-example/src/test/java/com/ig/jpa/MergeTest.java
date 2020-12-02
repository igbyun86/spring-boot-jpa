package com.ig.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MergeTest {

    @Autowired
    private ExamMerge examMerge;

    @Test
    public void mergeTest() {
        Assertions.assertNotNull(examMerge);
        examMerge.merge();

        /*
        member = 회원명변경
        mergeMember = 회원명변경
        em2 contains member = false
        em2 contains mergeMember = true
         */
    }
}
