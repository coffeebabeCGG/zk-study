package com.cgg.zkstudy;

import com.cgg.zkstudy.dao.ZkStudyRepository;
import com.cgg.zkstudy.entity.ZkStudy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ZkStudyApplicationTests {

    @Autowired
    private ZkStudyRepository zkStudyRepository;

    @Test
    public void testJpa() {

    }

}
